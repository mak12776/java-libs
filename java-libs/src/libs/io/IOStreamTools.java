
package libs.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import libs.buffers.mutable.BufferViews;
import libs.exceptions.BaseException;
import libs.exceptions.BigFileSizeException;
import libs.exceptions.InvalidReadNumberException;
import libs.exceptions.ZeroFileSizeExeption;
import libs.interfaces.BufferViewInterface;
import libs.views.View;

public class IOStreamTools
{
	// read file functions
	
	public static byte[] readFile(FileInputStream stream) throws IOException, BaseException
	{
		long fileSize;
		int readNumber;
		byte[] array;

		fileSize = stream.getChannel().size();

		if (fileSize == 0)
			throw new ZeroFileSizeExeption();
		else if (fileSize > Integer.MAX_VALUE)
			throw new BigFileSizeException("file size: " + fileSize);

		array = new byte[(int) fileSize];

		readNumber = stream.read(array);
		if (readNumber != fileSize)
		{
			throw new InvalidReadNumberException("read number: " + readNumber + ", file size: " + fileSize);
		}
		return array;
	}
	
	public static byte[] readFile(String name) throws FileNotFoundException, IOException, BaseException
	{
		return readFile(new FileInputStream(name));
	}

	// read lines functions
	
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
