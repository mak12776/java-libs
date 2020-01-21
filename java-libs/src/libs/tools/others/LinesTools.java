package libs.tools.others;

import libs.buffers.BufferView;
import libs.buffers.BufferViewInterface;
import libs.exceptions.UnknownClassException;
import libs.views.View;

public class LinesTools
{
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
