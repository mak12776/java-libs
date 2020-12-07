package library;

import java.io.File;

public class Tools
{
	public static int upperBound(int value, int divisor)
	{
		int remaining = value % divisor;
		if (remaining != 0)
			return Math.addExact(value, divisor - remaining);
		return value;
	}
	
	public static boolean checkIntegerBytes(int bytes)
	{
		return (bytes == Byte.BYTES) || (bytes == Short.BYTES) || (bytes == Integer.BYTES) || (bytes == Long.BYTES);
	}
	
	public static boolean checkIntegerBits(int bits)
	{
		return (bits == Byte.SIZE) || (bits == Short.SIZE) || (bits == Integer.SIZE) || (bits == Long.SIZE);
	}
	
	public static Class<?> getIntegerOfBytes(int bytes)
	{
		switch (bytes)
		{
		case Byte.BYTES: return Byte.class;
		case Short.BYTES: return Short.class;
		case Integer.BYTES: return Integer.class;
		case Long.BYTES: return Long.class;
		default: throw new IllegalArgumentException("unknown bits: " + bytes);
		}
	}
	
	public static Class<?> getIntegerOfBits(int bits)
	{
		switch (bits)
		{
		case Byte.SIZE: return Byte.class;
		case Short.SIZE: return Short.class;
		case Integer.SIZE: return Integer.class;
		case Long.SIZE: return Long.class;
		default: throw new IllegalArgumentException("unknown bits: " + bits);
		}
	}
	
	public static String getProgramName()
	{
		return new File(Tools.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
	}
}
