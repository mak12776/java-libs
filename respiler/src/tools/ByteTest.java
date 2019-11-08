package tools;

public interface ByteTest 
{
	public boolean test(byte b);
	
	public static boolean isBlank(byte b)
	{
		return (b == ' ') || (b == '\t');
	}
	
	public static ByteTest isBlankClass = new ByteTest() 
	{
		@Override
		public boolean test(byte b) 
		{
			return ByteTest.isBlank(b);
		}
	};
	
	public static boolean isNewline(byte b)
	{
		return (b == '\n');
	}
	
	public static ByteTest isNewlineClass = new ByteTest() 
	{
		@Override
		public boolean test(byte b) 
		{
			return isNewline(b);
		}
	};
	
	public static boolean isLower(byte b)
	{
		return ('a' <= b) && (b <= 'z');
	}
	
	public static boolean isUpper(byte b)
	{
		return ('A' <= b) && (b <= 'Z');
	}
	
	public static boolean isDigit(byte b)
	{
		return ('0' <= b) && (b <= '9');
	}
	
	public static boolean isLetter(byte b)
	{
		return  (('a' <= b) && (b <= 'z')) ||
				(('A' <= b) && (b <= 'Z'));
	}
	
	public static ByteTest isLetterClass = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return isLetter(b);
		}
	};
	
	public static boolean isHexDigit(byte b)
	{
		return  (('0' <= b) && (b <= '9')) ||
				(('a' <= b) && (b <= 'f')) ||
				(('A' <= b) && (b <= 'F'));
	}
}
