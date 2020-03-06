package libs.tools.io;

import java.io.IOException;
import java.io.InputStream;

import libs.exceptions.BaseException;
import libs.exceptions.UnknownClassException;
import libs.exceptions.ZeroFileSizeExeption;
import libs.types.buffers.BufferViewInterface;
import libs.types.buffers.mutable.BufferView;
import libs.types.views.View;

public class LinesTools
{
	// count lines from stream

	public static long countLines(InputStream stream, int bufferSize) throws IOException, BaseException
	{
		return countLines(stream, new byte[bufferSize]);
	}
	
	public static long countLines(InputStream stream, byte[] buffer) throws IOException, BaseException
	{
		int readNumber;
		int index;
		int total;
		boolean checkNewline;

		checkNewline = false;
		total = 0;

		readNumber = stream.read(buffer);
		if (readNumber == -1)
			throw new ZeroFileSizeExeption();

		while (true)
		{
			index = 0;

			if (checkNewline && buffer[0] == '\n')
				index += 1;

			while (index < readNumber)
			{
				if (buffer[index] == '\n')
				{
					total += 1;
				} else if (buffer[index] == '\r')
				{
					total += 1;

					index += 1;
					if (index == readNumber)
					{
						checkNewline = true;
						break;
					}

					if (buffer[index] == '\n')
						index += 1;

					continue;
				}
				index += 1;
			}

			readNumber = stream.read(buffer);
			if (readNumber == -1)
			{
				if (buffer[index - 1] != '\n' && buffer[index - 1] != '\r')
					total += 1;
				break;
			}
		}
		return total;
	}
	
	public static int countLines(byte[] buffer, int start, int end)
	{
		int index;
		int total;

		total = 1;

		index = start;
		while (index < end)
		{
			if (buffer[index] == '\r')
			{
				total += 1;

				index += 1;
				if (index == end)
					break;

				if (buffer[index] == '\n')
					index += 1;

				continue;
			} else if (buffer[index] == '\n')
			{
				total += 1;

				index += 1;
				continue;
			}
			index += 1;
		}

		return total;
	}

	// split lines functions
	
	private static void splitLines(byte[] buffer, int bufferStart, int bufferEnd, BufferViewInterface[] lines)
	{
		int lnum;
		int start;
		int end;

		lnum = 0;
		start = bufferStart;

		end = start;
		while (end < bufferEnd)
		{
			if (buffer[end] == '\n')
			{
				end += 1;
				lines[lnum].set(buffer, start, end);
			} else if (buffer[end] == '\r')
			{
				buffer[end] = '\n';

				end += 1;
				lines[lnum].set(buffer, start, end);

				if (end == bufferEnd)
					break;

				if (buffer[end] == '\n')
					end += 1;
			} else
			{
				end += 1;
				continue;
			}

			lnum += 1;
			start = end;
		}

		lines[lnum].set(buffer, start, end);
	}

	public static BufferViewInterface[] splitLines(Class<?> c, byte[] array)
	{
		BufferViewInterface[] result;

		if (c.isAssignableFrom(BufferView.class))
		{
			result = BufferView.newArray(countLines(array, 0, array.length));
		} else if (c.isAssignableFrom(View.class))
		{
			result = View.newArray(countLines(array, 0, array.length));
		} else
		{
			throw new UnknownClassException("unknown class: " + c.getName());
		}

		splitLines(array, 0, array.length, result);

		return result;
	}
}
