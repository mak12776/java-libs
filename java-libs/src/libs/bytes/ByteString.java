package libs.bytes;

import java.io.IOException;
import java.io.InputStream;
import libs.tools.SafeTools;
import libs.tools.types.StringBuilderTools;

public class ByteString
{
	public static final boolean CHECK_BUFFER_START_END = false;
	public static final boolean CHECK_INVALID_INDEX = false;
	
	private byte[] string;
	
	// constructors
	
	public ByteString(byte[] string)
	{
		this.string = string;
	}
	
	public ByteString(byte[] string, int start, int end)
	{
		if (CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(string, start, end);
		
		int stringLength = end = start;
		
		this.string = new byte[stringLength];
		System.arraycopy(string, start, this.string, 0, stringLength);
	}
	
	public ByteString(String charString)
	{
		this.string = charString.getBytes();
	}
	
	// fields functions
	
	public byte get(int index)
	{
		return string[index];
	}
	
	public void set(int index, byte value)
	{
		string[index] = value;
	}
	
	public int length()
	{
		return string.length;
	}
	
	// append strings functions
	
	public void append(byte[] string)
	{
		this.string = ByteArrayTools.concatenate(this.string, string);
	}
	
	public void append(ByteString string)
	{
		this.string = ByteArrayTools.concatenate(this.string, string.string);
	}
	
	// append functions
	
	public int appendFromInputStream(InputStream stream, int size) throws IOException
	{
		byte[] buffer = new byte[size];
		
		size = stream.read(buffer);
		if (size == -1)
			return 0;
		
		byte[] newString = new byte[string.length + size];
		
		System.arraycopy(string, 0, newString, 0, string.length);
		System.arraycopy(buffer, 0, newString, string.length, size);
		
		return 0;
	}
	
	// read from file functions
	
	public static ByteString readInputStream(InputStream stream, int size) throws IOException
	{
		byte[] buffer = new byte[size];
		size = stream.read(buffer);
		
		if (size == -1)
			return null;
		
		return new ByteString(ByteArrayTools.slice(buffer, 0, size));
	}
	
	// copy functions
	
	public ByteString copy()
	{
		return new ByteString(string);
	}
	
	// split line functions
	
	public ByteString splitLine()
	{
		byte[] result = null;
		
		for (int index = 0; index < string.length; index += 1)
		{
			if (string[index] == '\n')
			{
				index += 1;
				result = new byte[index];
				System.arraycopy(string, 0, result, 0, index);
				
				string = ByteArrayTools.slice(string, index, string.length);
				
				break;
			}
			else if (string[index] == '\r')
			{
				string[index] = '\n';
				
				index += 1;
				result = new byte[index];
				System.arraycopy(string, 0, result, 0, index);
				
				if (index != string.length && string[index] == '\n')
					index += 1;
				
				string = ByteArrayTools.slice(string, index, string.length);
				
				break;
			}
		}
		
		if (result == null)
			return null;
		
		return new ByteString(result);
	}
	
	public String toRepresentation()
	{
		StringBuilder builder = new StringBuilder();
		builder.append('"');
		StringBuilderTools.appendReprBytes(builder, string, 0, string.length);
		builder.append('"');
		return builder.toString();
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for (int index = 0; index < string.length; index += 1)
			builder.append((char) string[index]);
		return builder.toString();
	}
}
