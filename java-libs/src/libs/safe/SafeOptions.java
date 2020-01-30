package libs.safe;

public class SafeOptions
{
	
	
	public static class CheckIntegerBytes
	{
		private CheckIntegerBytes() { }
		
		public final boolean get(Class<?> c)
		{
			return false;
		}
		
		public static final boolean buffer = true;
	}
	
	public static class CheckBufferStartEnd
	{
		private CheckBufferStartEnd() { }
		
		public static final boolean buffer = true;
	}
}
