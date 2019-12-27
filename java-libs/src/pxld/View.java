package pxld;

import pxld.types.ColorMode;

public class View implements Picture
{
	private byte[] data;
	private ColorMode mode;
	
	private int width;
	private int height;
	
	private int xStart;
	private int yStart;
	
	private int xEnd;
	private int yEnd;
	
	private int colorWidth;
	
	private int xWidth;
	@SuppressWarnings("unused")
	private int yWidth;
	
	public View(Image image, int xStart, int yStart, int xEnd, int yEnd)
	{
		this.data = image.getData();
		this.mode = image.getColorMode();
		
		this.width = xEnd - xStart;
		this.height = yEnd - yStart;
		
		this.xStart = xStart;
		this.yStart = yStart;
		
		this.xEnd = xEnd;
		this.yEnd = yEnd;
		
		this.colorWidth = image.getColorWidth();
		
		this.xWidth = image.xWidth() * colorWidth;
		this.yWidth = image.yWidth() * xWidth;
	}
	
	// [ low-level info functions ]
	
	public byte[] getData() 
	{
		return data;
	}
	
	@Override
	public ColorMode getColorMode() 
	{
		return mode;
	}
	
	@Override
	public int getColorWidth() 
	{
		return colorWidth;
	}
	
	@Override
	public int width() 
	{
		return width;
	}
	
	@Override
	public int height()
	{
		return height;
	}
	
	public int xStart() 
	{
		return xStart;
	}
	
	public int yStart()
	{
		return yStart;
	}
	
	public int xEnd()
	{
		return xEnd;
	}
	
	public int yEnd()
	{
		return yEnd;
	}
	
	// [ low-level functions ]
	
	@Override
	public void draw(byte[] colorData) 
	{
		for (int y = yStart * xWidth, yEnd2 = yEnd * xWidth; y < yEnd2; y += xWidth)
		{
			for (int x = xStart * colorWidth, xEnd2 = xEnd * colorWidth; x < xEnd2; x += colorWidth)
			{
				for (int c = 0; c < colorWidth; c += 1)
				{
					data[y + x + c] = colorData[c];
				}
			}
		}
	}
	
	@Override
	public void map(MapFunction map) 
	{
		for (int y = yStart * xWidth, yEnd2 = yEnd * xWidth, yIndex = 0;
				y < yEnd2; y += xWidth, yIndex += 1)
		{
			for (int x = xStart * colorWidth, xEnd2 = xEnd * colorWidth, xIndex = 0;
					x < xEnd2; x += colorWidth, xIndex += 1)
			{
				map.map(xIndex, yIndex, data, x + y);
			}
		}
	}
	
	@Override
	public void drawPicture(int x, int y, Picture picture) 
	{
		throw new RuntimeException("incomplete");
	}
}
