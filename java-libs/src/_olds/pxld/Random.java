package olds.pxld;

import pxld.types.ColorMode;
import pxld.types.IntPoint;

public class Random 
{
	// [ low-level functions ]
	
	public static int intRange(int min, int max)
	{
		return min + (int)(Math.random() * (max - min + 1));
	}
	
	public static int intRange(int max)
	{
		return (int)(Math.random() * (max + 1));
	}
	
	public static int intRange(int min, int max, int step)
	{
		return (int)( (Math.random() * ((max - min - 1) / step)) * step ) + min; 
	}
	
	public static double doubleRange(double max)
	{
		return max * Math.random();
	}
	
	public static double doubleRange(double min, double max)
	{
		return min + (Math.random() * (max - min));
	}
	
	public static int byteRange()
	{
		return (int)(Math.random() * 256);
	}
	
	// [ high-level functions ]
	
	public static IntPoint point(int xMin, int yMin, int xMax, int yMax)
	{
		return new IntPoint(intRange(xMin, xMax), intRange(yMin, yMax));
	}
	
	public static Color color(ColorMode mode)
	{
		if (mode == ColorMode.L)
		{
			return new Color(Random.byteRange());
		}
		else if (mode == ColorMode.LA)
		{
			return new Color(Random.byteRange(), 255);
		}
		else if (mode == ColorMode.RGB)
		{
			return new Color(Random.byteRange(), Random.byteRange(), Random.byteRange());
		}
		else if (mode == ColorMode.RGBA)
		{
			return new Color(Random.byteRange(), Random.byteRange(), Random.byteRange(), 255);
		}
		else
		{
			throw new IllegalArgumentException("unsupported color mode: " + String.valueOf(mode));
		}
	}
}
