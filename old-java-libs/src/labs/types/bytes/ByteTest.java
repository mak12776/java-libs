
package labs.types.bytes;

public interface ByteTest
{
	// static functions

	static boolean isNull(byte b)
	{
		return (b == '\0');
	}

	static boolean isBlank(byte b)
	{
		return (b == ' ') || (b == '\t');
	}

	static boolean isNewline(byte b)
	{
		return (b == '\n');
	}

	static boolean isCarriageReturn(byte b)
	{
		return (b == '\r');
	}

	static boolean isLower(byte b)
	{
		return ('a' <= b) && (b <= 'z');
	}

	static boolean isUpper(byte b)
	{
		return ('A' <= b) && (b <= 'Z');
	}

	static boolean isDigit(byte b)
	{
		return ('0' <= b) && (b <= '9');
	}

	static boolean isLetter(byte b)
	{
		return (('a' <= b) && (b <= 'z')) || (('A' <= b) && (b <= 'Z'));
	}

	static boolean isLetterDigit(byte b)
	{
		return (('a' <= b) && (b <= 'z')) || (('A' <= b) && (b <= 'Z')) || (('0' <= b) && (b <= '9'));
	}

	static boolean isHexDigit(byte b)
	{
		return (('0' <= b) && (b <= '9')) || (('a' <= b) && (b <= 'f')) || (('A' <= b) && (b <= 'F'));
	}

	// objects
	
	boolean test(byte b);

	public static class Instances
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
}
