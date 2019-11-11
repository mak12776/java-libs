package tools.types;

import tools.ByteTools;

public interface BytesTest
{
	public boolean test(byte[] bytes, int start, int end);
	
	public static BytesTest isBlank = new BytesTest()
	{
		@Override
		public boolean test(byte[] bytes, int start, int end)
		{
			return ByteTools.test(bytes, start, end, ByteTest.isBlank);
		}
	}; 
}
