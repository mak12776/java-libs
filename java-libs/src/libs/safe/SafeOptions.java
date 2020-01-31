package libs.safe;

import libs.buffers.Buffer;
import libs.buffers.BufferQueue;
import libs.exceptions.UnknownClassException;

public class SafeOptions
{
	public static class CheckIntegerBytes
	{
		private CheckIntegerBytes() { }
		
		public static boolean get(Class<?> c)
		{
			if (c.equals(Buffer.class)) return true;
			else if (c.equals(BufferQueue.class)) return true;
			else 
				throw new UnknownClassException(c.getCanonicalName());
		}
	}
	
	public static class CheckBufferStartEnd
	{
		private CheckBufferStartEnd() { }
		
		public static final boolean Buffer = true;
		public static final boolean BufferQueue = true;
	}
}
