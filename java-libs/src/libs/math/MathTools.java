
package libs.math;

public class MathTools
{
	private MathTools()
	{
	}

	// limits

	public static int limit(int value, int min, int max)
	{
		return (value < min) ? min : ((value > max) ? max : value);
	}

	public static int limitMaximum(int value, int max)
	{
		return (value > max) ? max : value;
	}

	public static int limitMinimum(int value, int min)
	{
		return (value < min) ? min : value;
	}

	// minimum

	public static int min(int first, int... others)
	{
		int min = first;
		for (int i = 0; i < others.length; i += 1)
		{
			if (others[i] < min)
				min = others[i];
		}
		return min;
	}

	public static float min(float first, float... others)
	{
		float min = first;
		for (int i = 0; i < others.length; i += 1)
		{
			if (others[i] < min)
				min = others[i];
		}
		return min;
	}

	public static double min(double first, double... others)
	{
		double min = first;
		for (int i = 0; i < others.length; i += 1)
		{
			if (others[i] < min)
				min = others[i];
		}
		return min;
	}

	// maximum

	public static int max(int first, int... others)
	{
		int max = first;
		for (int i = 0; i < others.length; i += 1)
		{
			if (others[i] > max)
				max = others[i];
		}
		return max;
	}

	public static double max(double first, double... others)
	{
		double max = first;
		for (int i = 0; i < others.length; i += 1)
		{
			if (others[i] > max)
				max = others[i];
		}
		return max;
	}

	// point calculations

	public static double distance(int x1, int y1, int x2, int y2)
	{
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	public static double inverseDistance(int x1, int y1, int x2, int y2, double max)
	{
		return max - Math.min(distance(x1, y1, x2, y2), max);
	}

	public static double inverseDistanceFraction(int x1, int y1, int x2, int y2, double max)
	{
		return inverseDistance(x1, y1, x2, y2, max) / max;
	}
}
