package pxld.types;

public class IntPoint 
{
	public int x;
	public int y;
	
	public IntPoint(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}
	
	public void add(int a)
	{
		this.x += a;
		this.y += a;
	}
	
	public void add(int x, int y)
	{
		this.x += x;
		this.y += y;
	}
	
	public IntPoint copy()
	{
		return new IntPoint(x, y);
	}
	
	public boolean isNatural()
	{
		return (this.x > 0) && (this.y > 0);
	}
		
	public void xLimit(int min, int max)
	{
		if (x < min)
		{
			x = min;
		}
		else if (x > max)
		{
			x = max;
		}
	}
	
	public void yLimit(int min, int max)
	{
		if (y < min)
		{
			y = min;
		}
		else if (y > max)
		{
			y = max;
		}
	}
	
	public void limit(int xMin, int yMin, int xMax, int yMax)
	{
		xLimit(xMin, xMax);
		yLimit(yMin, yMax);
	}
}
