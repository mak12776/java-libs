package tools.types;

import tools.BytesTools;

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
				return BytesTools.byteIn(b, bytes);
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
				return BytesTools.byteIn(b, bytes);
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
			return BytesTools.isNull(b);
		}
	};
	
	public static ByteTest isBlank = new ByteTest() 
	{
		@Override
		public boolean test(byte b) 
		{
			return BytesTools.isBlank(b);
		}
	};
	
	public static ByteTest isCarriageReturn = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return BytesTools.isCarriageReturn(b);
		}
	};
	
	public static ByteTest isNewline = new ByteTest() 
	{
		@Override
		public boolean test(byte b) 
		{
			return BytesTools.isNewline(b);
		}
	};
	
	public static ByteTest isUnderScore = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return BytesTools.isUnderScore(b);
		}
	};
	
	public static ByteTest isUpper = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return BytesTools.isUpper(b);
		}
	};
	
	public static ByteTest isLower = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return BytesTools.isLower(b);
		}
	};
	
	public static ByteTest isLetter = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return BytesTools.isLetter(b);
		}
	};
	
	public static ByteTest isLetterDigit = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return BytesTools.isLetterDigit(b);
		}
	};
	
	public static ByteTest isDigit = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return BytesTools.isDigit(b);
		}
	};
	
	public static ByteTest isHexDigit = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return BytesTools.isHexDigit(b);
		}
	};
}
