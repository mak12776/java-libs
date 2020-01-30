package libs.math.points;

class DoublePoint
{
	private double x;
	private double y;

	public DoublePoint(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public DoublePoint()
	{
		this.x = 0;
		this.y = 0;
	}

	// add functions

	public void add(double value)
	{
		this.x += value;
		this.y += value;
	}

	public void add(double x, double y)
	{
		this.x += x;
		this.y += y;
	}

	public void add(DoublePoint other)
	{
		this.x += other.x;
		this.y += other.y;
	}

	// sub functions

	public void sub(double value)
	{
		this.x -= value;
		this.y -= value;
	}

	public void sub(double x, double y)
	{
		this.x -= x;
		this.y -= y;
	}

	public void sub(DoublePoint other)
	{
		this.x -= other.x;
		this.y -= other.y;
	}

	// div functions

	public void div(double value)
	{
		this.x /= value;
		this.y /= value;
	}

	public void div(double x, double y)
	{
		this.x /= x;
		this.y /= y;
	}

	public void div(DoublePoint other)
	{
		this.x /= other.x;
		this.y /= other.y;
	}

	// mul functions

	public void mul(double value)
	{
		this.x *= value;
		this.y *= value;
	}

	public void mul(double x, double y)
	{
		this.x *= x;
		this.y *= y;
	}

	public void mul(DoublePoint other)
	{
		this.x *= other.x;
		this.y *= other.y;
	}
}
