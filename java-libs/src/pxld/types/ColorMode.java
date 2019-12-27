package pxld.types;

public enum ColorMode 
{
	L, LA,
	RGB, RGBA;
	
	public int getWidth()
	{
		if (this == L)
		{
			return 1;
		}
		else if (this == LA)
		{
			return 2;
		}
		else if (this == RGB)
		{
			return 3;
		}
		else if (this == RGBA)
		{
			return 4;
		}
		else
		{
			throw new IllegalArgumentException("unknown mode: " + String.valueOf(this));
		}
	}
	
	public byte getByteCode()
	{
		if (this == ColorMode.RGB)
		{
			return 0x01;
		}
		else if (this == ColorMode.RGBA)
		{
			return 0x03;
		}
		else if (this == ColorMode.L)
		{
			return 0x00;
		}
		else if (this == ColorMode.LA)
		{
			return 0x02;
		}
		else
		{
			throw new IllegalArgumentException("unknown mode: " + String.valueOf(this));
		}
	}
}
