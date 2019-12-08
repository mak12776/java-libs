package libs.tools;

public class MathTools 
{
	private MathTools() {}
	
	// minimum & maximum
	
	public static class Double
	{
		private Double() {};
		
		public static double min(double a, double... others)
		{
			double min = a;
			for (int i = 0; i < others.length; i += 1)
			{
				if (others[i] < min)
					min = others[i];
			}
			return min;
		}
		
		public static double max(double a, double... others)
		{
			double max = a;
			for (int i = 0; i < others.length; i += 1)
			{
				if (others[i] > max)
					max = others[i];
			}
			return max;
		}
	}
	
	public static class Integer
	{
		public static int min(int a, int... others)
		{
			int min = a;
			for (int i = 0; i < others.length; i += 1)
			{
				if (others[i] < min)
					min = others[i];
			}
			return min;
		}
		
		public static int max(int a, int... others)
		{
			int max = a;
			for (int i = 0; i < others.length; i += 1)
			{
				if (others[i] > max)
					max = others[i];
			}
			return max;
		}
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
