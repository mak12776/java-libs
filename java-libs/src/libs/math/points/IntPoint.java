package libs.math.points;

class IntPoint
{
	private int x;
	private int y;

	public IntPoint(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public IntPoint()
	{
		this.x = 0;
		this.y = 0;
	}

	// add functions

	public void add(int value)
	{
		this.x += value;
		this.y += value;
	}

	public void add(int x, int y)
	{
		this.x += x;
		this.y += y;
	}

	public void add(IntPoint other)
	{
		this.x += other.x;
		this.y += other.y;
	}

	// sub functions

	public void sub(int value)
	{
		this.x -= value;
		this.y -= value;
	}

	public void sub(int x, int y)
	{
		this.x -= x;
		this.y -= y;
	}

	public void sub(IntPoint other)
	{
		this.x -= other.x;
		this.y -= other.y;
	}

	// div functions

	public void div(int value)
	{
		this.x /= value;
		this.y /= value;
	}

	public void div(int x, int y)
	{
		this.x /= x;
		this.y /= y;
	}

	public void div(IntPoint other)
	{
		this.x /= other.x;
		this.y /= other.y;
	}

	// mul functions

	public void mul(int value)
	{
		this.x *= value;
		this.y *= value;
	}

	public void mul(int x, int y)
	{
		this.x *= x;
		this.y *= y;
	}

	public void mul(IntPoint other)
	{
		this.x *= other.x;
		this.y *= other.y;
	}
}
