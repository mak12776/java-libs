package olds.pxld;

import java.util.Arrays;

import libs.pxld.types.ColorMode;

public class Color 
{
	private ColorMode mode;
	private byte[] data;
	
	public static final Color RGB_BLACK = new Color(0x00, 0x00, 0x00);
	public static final Color RGB_GRAY = new Color(0x88, 0x88, 0x88);
	public static final Color RGB_WHITE = new Color(0xFF, 0xFF, 0xFF);
	
	public Color(ColorMode mode, byte[] data)
	{
		if (data.length != mode.getWidth())
		{
			throw new IllegalArgumentException("invalid length of data: " + String.valueOf(data.length));
		}
		
		this.mode = mode;
		this.data = data;
	}
	
	public Color(int light)
	{
		mode = ColorMode.L;
		data = new byte[] {(byte)light};
	}
	
	public Color(int light, int alpha)
	{
		mode = ColorMode.LA;
		data = new byte[] {(byte)light, (byte)alpha};
	}
	
	public Color(int red, int green, int blue)
	{
		mode = ColorMode.RGB;
		data = new byte[] {(byte)red, (byte)green, (byte)blue};
	}
	
	public Color(int red, int green, int blue, int alpha)
	{
		mode = ColorMode.RGBA;
		data = new byte[] {(byte)red, (byte)green, (byte)blue, (byte)alpha};
	}
	
	public Color(byte[] color)
	{
		if (color.length == 1)
		{
			mode = ColorMode.L;
		}
		else if (color.length == 2)
		{
			mode = ColorMode.LA;
		}
		else if (color.length == 3)
		{
			mode = ColorMode.RGB;
		}
		else if (color.length == 4)
		{
			mode = ColorMode.RGBA;
		}
		else
		{
			throw new IllegalArgumentException("invalid color length: " + String.valueOf(color));
		}
		data = Arrays.copyOf(color, color.length);
	}
	
	public ColorMode getMode()
	{
		return mode;
	}
	
	public byte[] getData()
	{
		return data;
	}
	
	
	// [ functions ]
	
	private double byteToDouble(byte b)
	{
		return (double)(b & 0xFF) / 225;
	}
	
	private byte RGBToLight(byte red, byte green, byte blue)
	{
		return (byte)(byteToDouble(red) * 0.2126 + byteToDouble(green) * 0.7152 + byteToDouble(blue));
	}
	
	public byte[] convertData(ColorMode mode)
	{
		byte[] result = null;
		
		if (this.mode == mode)
		{
			result = Arrays.copyOf(data, data.length);
		}
		else if (mode == ColorMode.RGB)
		{
			if (this.mode == ColorMode.L || this.mode == ColorMode.LA)
			{
				result = new byte[] {data[0], data[0], data[0]};
			}
			else if (this.mode == ColorMode.RGBA)
			{
				result = Arrays.copyOf(data, 3); 
			}
		}
		else if (mode == ColorMode.RGBA)
		{
			if (this.mode == ColorMode.L)
			{
				result = new byte[] {data[0], data[0], data[0], (byte) 0xFF};
			}
			else if (this.mode == ColorMode.LA)
			{
				result = new byte[] {data[0], data[0], data[0], data[1]};
			}
			else if (this.mode == ColorMode.RGB)
			{
				result = new byte[] {data[0], data[1], data[2], (byte) 0xFF};
			}
		}
		else if (mode == ColorMode.L)
		{
			if (this.mode == ColorMode.RGB || this.mode == ColorMode.RGBA)
			{
				return new byte[] {RGBToLight(data[0], data[1], data[2])};
			}
			else if (this.mode == ColorMode.LA)
			{
				return new byte[] {data[0], (byte) 0xFF};
			}
		}
		else if (mode == ColorMode.LA)
		{
			if (this.mode == ColorMode.RGB)
			{
				return new byte[] {RGBToLight(data[0], data[1], data[2]), (byte) 0xFF};
			}
			else if (this.mode == ColorMode.RGBA)
			{
				return new byte[] {RGBToLight(data[0], data[1], data[2]), (byte) 0xFF};
			}
			else if (this.mode == ColorMode.L)
			{
				return new byte[] {data[0], (byte) 0xFF};
			}
		}
		else
		{
			throw new IllegalArgumentException("unsupported color mode: " + String.valueOf(mode));
		}
		
		return result;
	}
	
	@Override
	public String toString() 
	{
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(mode);
		for (int c = 0; c < data.length; c += 1)
		{
			builder.append(", ");
			builder.append(data[0]);
		}
		builder.append(")");
		return builder.toString();
	}
}
