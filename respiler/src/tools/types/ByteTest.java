package tools.types;

import tools.ByteTools;

public interface ByteTest 
{
	public boolean test(byte b);
	
	public default ByteTest not() 
	{
		return new ByteTest()
		{
			@Override
			public boolean test(byte b)
			{
				return !test(b);
			}
		};
	}
	
	public static ByteTest isBlank = new ByteTest() 
	{
		@Override
		public boolean test(byte b) 
		{
			return ByteTools.isBlank(b);
		}
	};
	
	public static ByteTest isCarriageReturn = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return ByteTools.isCarriageReturn(b);
		}
	};
	
	public static ByteTest isNewline = new ByteTest() 
	{
		@Override
		public boolean test(byte b) 
		{
			return ByteTools.isNewline(b);
		}
	};
	
	public static ByteTest isUpper = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return ByteTools.isUpper(b);
		}
	};
	
	public static ByteTest isLower = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return ByteTools.isLower(b);
		}
	};
	
	public static ByteTest isLetter = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return ByteTools.isLetter(b);
		}
	};
	
	public static ByteTest isDigit = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return ByteTools.isDigit(b);
		}
	};
	
	public static ByteTest isHexDigit = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return ByteTools.isHexDigit(b);
		}
	};
}
