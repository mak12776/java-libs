package libs.types;

import libs.tools.ByteTools;

public interface ByteTest
{
	public boolean test(byte b);
	
	// functions
	
	public static boolean isNull(byte b)
	{
		return (b == '\0');
	}
	
	public static boolean isBlank(byte b)
	{
		return (b == ' ') || (b == '\t');
	}
	
	public static boolean isNewline(byte b)
	{
		return (b == '\n');
	}
	
	public static boolean isCarriageReturn(byte b)
	{
		return (b == '\r');
	}

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
	
	public static boolean isLetterDigit(byte b)
	{
		return  (('a' <= b) && (b <= 'z')) ||
				(('A' <= b) && (b <= 'Z')) ||
				(('0' <= b) && (b <= '9'));
	}
	
	public static boolean isHexDigit(byte b)
	{
		return  (('0' <= b) && (b <= '9')) ||
				(('a' <= b) && (b <= 'f')) ||
				(('A' <= b) && (b <= 'F'));
	}

	// objects
	
	public static class Class
	{
		public static ByteTest isNull = new ByteTest()
		{
			@Override
			public boolean test(byte b)
			{
				return isNull(b);
			}
		};
		
		public static ByteTest isBlank = new ByteTest() 
		{
			@Override
			public boolean test(byte b) 
			{
				return isBlank(b);
			}
		};
		
		public static ByteTest isCarriageReturn = new ByteTest()
		{
			@Override
			public boolean test(byte b)
			{
				return isCarriageReturn(b);
			}
		};
		
		public static ByteTest isNewline = new ByteTest() 
		{
			@Override
			public boolean test(byte b) 
			{
				return isNewline(b);
			}
		};
		
		public static ByteTest isUpper = new ByteTest()
		{
			@Override
			public boolean test(byte b)
			{
				return isUpper(b);
			}
		};
		
		public static ByteTest isLower = new ByteTest()
		{
			@Override
			public boolean test(byte b)
			{
				return isLower(b);
			}
		};
		
		public static ByteTest isLetter = new ByteTest()
		{
			@Override
			public boolean test(byte b)
			{
				return isLetter(b);
			}
		};
		
		public static ByteTest isLetterDigit = new ByteTest()
		{
			@Override
			public boolean test(byte b)
			{
				return isLetterDigit(b);
			}
		};
		
		public static ByteTest isDigit = new ByteTest()
		{
			@Override
			public boolean test(byte b)
			{
				return isDigit(b);
			}
		};
		
		public static ByteTest isHexDigit = new ByteTest()
		{
			@Override
			public boolean test(byte b)
			{
				return isHexDigit(b);
			}
		};
	}
	
	// static functions
	
	public static ByteTest isEqual(byte ch)
	{
		return new ByteTest()
		{
			@Override
			public boolean test(byte b)
			{
				return b == ch;
			}
		};
	}
	
	public static ByteTest isEqual(char ch)
	{
		byte b1 = (byte) ch;
		
		return new ByteTest()
		{
			@Override
			public boolean test(byte b)
			{
				return b == b1;
			}
		};
	}
	
	public static ByteTest inBytes(byte[] bytes)
	{
		return new ByteTest()
		{
			@Override
			public boolean test(byte b)
			{
				return ByteTools.byteIn(b, bytes);
			}
		};
	}
	
	public static ByteTest inString(String string)
	{
		byte[] bytes = string.getBytes();
		
		return new ByteTest()
		{
			@Override
			public boolean test(byte b)
			{
				return ByteTools.byteIn(b, bytes);
			}
		}; 
	}
	
	// class operations
	
	public default ByteTest not()
	{
		ByteTest self = this;
		
		return new ByteTest()
		{
			@Override
			public boolean test(byte b)
			{
				return !self.test(b);
			}
		};
	}
	
	public default ByteTest or(ByteTest test)
	{
		ByteTest self = this;
		
		return new ByteTest()
		{
			@Override
			public boolean test(byte b)
			{
				return self.test(b) || test.test(b);
			}
		};
	}
	
	public default ByteTest and(ByteTest test)
	{
		ByteTest self = this;
		
		return new ByteTest()
		{
			@Override
			public boolean test(byte b)
			{
				return self.test(b) && test.test(b);
			}
		};
	}
}
