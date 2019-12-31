package libs.types;

import libs.tools.ByteTools;

public interface ByteTest
{
	public boolean test(byte b);
	
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
	
	// class objects
	
	public static ByteTest isNullObject = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return isNull(b);
		}
	};
	
	public static ByteTest isBlankObject = new ByteTest() 
	{
		@Override
		public boolean test(byte b) 
		{
			return isBlank(b);
		}
	};
	
	public static ByteTest isCarriageReturnObject = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return isCarriageReturn(b);
		}
	};
	
	public static ByteTest isNewlineObject = new ByteTest() 
	{
		@Override
		public boolean test(byte b) 
		{
			return isNewline(b);
		}
	};
	
	public static ByteTest isUpperObject = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return isUpper(b);
		}
	};
	
	public static ByteTest isLowerObject = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return isLower(b);
		}
	};
	
	public static ByteTest isLetterObject = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return isLetter(b);
		}
	};
	
	public static ByteTest isLetterDigitObject = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return isLetterDigit(b);
		}
	};
	
	public static ByteTest isDigitObject = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return isDigit(b);
		}
	};
	
	public static ByteTest isHexDigitObject = new ByteTest()
	{
		@Override
		public boolean test(byte b)
		{
			return isHexDigit(b);
		}
	};
}
