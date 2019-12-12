package libs.types;

import libs.tools.ByteTools;

public interface ByteTest
{
	public boolean test(byte b);
	
	public static ByteTest isEqual(byte b1)
	{
		return new ByteTest()
		{
			@Override
			public boolean test(byte b)
			{
				return b == b1;
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
	
	public static ByteTest isNull = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return ByteTools.isNull(b);
		}
	};
	
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
	
	public static ByteTest isLetterDigit = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return ByteTools.isLetterDigit(b);
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
