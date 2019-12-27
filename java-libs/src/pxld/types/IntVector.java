package pxld.types;

public class IntVector
{
	public IntPoint coor1;
	public IntPoint coor2;
	
	public IntVector(IntPoint coor1, IntPoint coor2)
	{
		this.coor1 = coor1;
		this.coor2 = coor2;
	}
	
	public IntVector(int x1, int y1, int x2, int y2)
	{
		this.coor1 = new IntPoint(x1, y1);
		this.coor2 = new IntPoint(x2, y2);
	}
	
	public boolean isNatural()
	{
		return coor1.isNatural() && coor2.isNatural();
	}
	
	public IntVector copy()
	{
		return new IntVector(coor1.x, coor1.y, coor2.x, coor2.y);
	}
	
	public void sortCoordinates()
	{
		int temp;
		
		if (coor2.x < coor1.x)
		{
			temp = coor1.x;
			coor1.x = coor2.x;
			coor2.x = temp;
		}
		
		if (coor2.y < coor1.y)
		{
			temp = coor1.y;
			coor1.y = coor2.y;
			coor2.y = temp;
		}
	}
	
	public void xLimit(int min, int max)
	{
		coor1.xLimit(min, max);
		coor2.xLimit(min, max);
	}
	
	public void yLimit(int min, int max)
	{
		coor1.yLimit(min, max);
		coor2.yLimit(min, max);
	}
	
	public void limit(int xMin, int yMin, int xMax, int yMax)
	{
		coor1.limit(xMin, yMin, xMax, yMax);
		coor2.limit(xMin, yMin, xMax, yMax);
	}
	
	public boolean isOutsideOf(int x1, int y1, int x2, int y2)
	{
		return  ((coor1.x < x1) && (coor2.x < x1)) || ((coor1.x > x2) && (coor2.x > x2)) ||
				((coor1.y < y1) && (coor2.y < y1)) || ((coor1.y > y2) && (coor2.y > y2));
	}
	
	@Override
	public String toString() 
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Vector([");
		builder.append(coor1.x);
		builder.append(", ");
		builder.append(coor1.y);
		builder.append("], [");
		builder.append(coor2.x);
		builder.append(", ");
		builder.append(coor2.y);
		builder.append("])");
		return builder.toString();
	}
}
