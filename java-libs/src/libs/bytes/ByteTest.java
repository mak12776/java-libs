
package libs.bytes;

import static libs.bytes.ByteTest.isBlank;
import static libs.bytes.ByteTest.isCarriageReturn;
import static libs.bytes.ByteTest.isDigit;
import static libs.bytes.ByteTest.isHexDigit;
import static libs.bytes.ByteTest.isLetter;
import static libs.bytes.ByteTest.isLetterDigit;
import static libs.bytes.ByteTest.isLower;
import static libs.bytes.ByteTest.isNewline;
import static libs.bytes.ByteTest.isNull;
import static libs.bytes.ByteTest.isUpper;

public interface ByteTest
{
	boolean test(byte b);
	
	public default byte[] getTestData()
	{
		byte[] array;
		int size;
		int index;
		
		size = 0;
		for (int value = 0; value < 256; value += 1)
			if (test((byte) value)) 
				size += 1;
		
		array = new byte[size];
		
		index = 0;
		for (int value = 0; value < 256; value += 1)
			if (test((byte) value)) 
				array[index] = (byte) value;
		
		return array;
	}

	// functions

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

	static ByteTest isEqual(byte ch)
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

	static ByteTest isEqual(char ch)
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

	static ByteTest inBytes(byte[] bytes)
	{
		return new ByteTest()
		{
			@Override
			public boolean test(byte b)
			{
				return ByteTools.testAny(bytes, 0, bytes.length, b);
			}
		};
	}

	static ByteTest inString(String string)
	{
		byte[] bytes = string.getBytes();

		return new ByteTest()
		{
			@Override
			public boolean test(byte b)
			{
				return ByteTools.testAny(bytes, 0, bytes.length, b);
			}
		};
	}

	// class operations

	default ByteTest not()
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

	default ByteTest or(ByteTest test)
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

	default ByteTest and(ByteTest test)
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
