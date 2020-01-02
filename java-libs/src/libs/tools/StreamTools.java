package libs.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import libs.exceptions.BaseException;
import libs.exceptions.BigFileSizeException;
import libs.exceptions.InvalidReadNumberException;
import libs.exceptions.ZeroFileSizeExeption;
import libs.types.View;
import libs.types.bytes.Buffer;
import libs.types.bytes.BufferViews;
import libs.types.bytes.BytesView;

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
		}
		else if (fileSize > Integer.MAX_VALUE)
		{
			throw new BigFileSizeException("file size: " + fileSize);
		}
		
		array = new byte[(int)fileSize];
		
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
	
	public static BytesView[] readLines(Class<?> c, FileInputStream stream) throws IOException, BaseException
	{
		return ByteTools.splitLines(c, readFile(stream));
	}
	
	public static BufferViews readLineViews(FileInputStream stream) throws IOException, BaseException
	{
		BufferViews result = new BufferViews(null, null);
		
		result.buffer = readFile(stream);
		result.views = (View[]) ByteTools.splitLines(View.class, result.buffer);
		return result;
	}
	
	public static BufferViews readLines(String name) throws FileNotFoundException, IOException, BaseException
	{
		return readLineViews(new FileInputStream(name));
	}
}
