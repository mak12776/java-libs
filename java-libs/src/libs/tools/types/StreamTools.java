
package libs.tools.types;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import libs.bytes.BufferViewInterface;
import libs.bytes.buffers.BufferViews;
import libs.exceptions.BaseException;
import libs.exceptions.BigFileSizeException;
import libs.exceptions.InvalidReadNumberException;
import libs.exceptions.ZeroFileSizeExeption;
import libs.tools.others.LinesTools;
import libs.views.View;

public class StreamTools
{
	public static byte[] readFile(FileInputStream stream) throws IOException, BaseException
	{
		long fileSize;
		int readNumber;
		byte[] array;

		fileSize = stream.getChannel().size();

		if (fileSize == 0)
		{
			throw new ZeroFileSizeExeption();
		} else if (fileSize > Integer.MAX_VALUE)
		{
			throw new BigFileSizeException("file size: " + fileSize);
		}

		array = new byte[(int) fileSize];

		readNumber = stream.read(array);
		if (readNumber != fileSize)
		{
			throw new InvalidReadNumberException("read number: " + readNumber + ", file size: " + fileSize);
		}
		return array;
	}

	public static long countLines(byte[] buffer, InputStream stream) throws IOException, BaseException
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

	public static long countLines(int bufferSize, InputStream stream) throws IOException, BaseException
	{
		return countLines(new byte[bufferSize], stream);
	}

	public static byte[] readFile(String name) throws FileNotFoundException, IOException, BaseException
	{
		return readFile(new FileInputStream(name));
	}

	public static BufferViewInterface[] readLines(Class<?> c, FileInputStream stream) throws IOException, BaseException
	{
		return LinesTools.splitLines(c, readFile(stream));
	}

	public static BufferViews readLineViews(FileInputStream stream) throws IOException, BaseException
	{
		BufferViews result = new BufferViews(null, null);

		result.buffer = readFile(stream);
		result.views = (View[]) LinesTools.splitLines(View.class, result.buffer);
		return result;
	}

	public static BufferViews readLines(String name) throws FileNotFoundException, IOException, BaseException
	{
		return readLineViews(new FileInputStream(name));
	}
}
