package experimental.bytes.strings;

import libs.tools.MathTools;
import libs.tools.SafeTools;
import libs.tools.types.ByteTools;

public class SimpleByteString
{
	private byte[] bytes;
	
	// constructors
	
	public SimpleByteString(byte[] bytes)
	{
		this.bytes = bytes;
	}
	
	public SimpleByteString(String string)
	{
		this.bytes = string.getBytes();
	}
	
	// members functions
	
	public int length()
	{
		return bytes.length;
	}
	
	// private functions
	
	private static final boolean CHECK_ZERO_NEGATIVE_SIZE = false;
	
	private void resize(int size, int offset)
	{
		byte[] newBytes;
		
		if (CHECK_ZERO_NEGATIVE_SIZE)
			SafeTools.checkNegativeZeroIndex(size, "size");
		
		newBytes = new byte[size];
		ByteTools.copy(newBytes, offset, bytes, 0, MathTools.min(bytes.length, size));
		this.bytes = newBytes;
	}
	
	private void extend(int size)
	{
		if (CHECK_ZERO_NEGATIVE_SIZE)
			SafeTools.checkNegativeZeroIndex(size, "size");
		
		resize(bytes.length + size, 0);
	}
	
	private void extendShift(int size)
	{
		if (CHECK_ZERO_NEGATIVE_SIZE)
			SafeTools.checkNegativeZeroIndex(size, "size");
		
		resize(bytes.length + size, size);
	}
	
	// append functions
	
	public void append(byte[] other)
	{
		int offset = bytes.length;
		
		extend(other.length);
		ByteTools.copy(bytes, offset, other, 0, other.length);
	}
	
	public void append(String other)
	{
		append(other.getBytes());
	}
	
	public void append(SimpleByteString other)
	{
		append(other.bytes);
	}
	
	// append left functions
	
	public void appendLeft(byte[] other)
	{
		extendShift(other.length);
		ByteTools.copy(bytes, 0, other, 0, other.length);
	}
	
	public void appendLeft(String other)
	{
		appendLeft(other.getBytes());
	}
	
	public void appendLeft(SimpleByteString other)
	{
		appendLeft(other.bytes);
	}
	
	// to String
	
	@Override
	public String toString()
	{
		return new String(bytes);
	}
}
