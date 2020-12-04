package library;

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
		return isUpper(b) || isLower(b);
	}

	static boolean isLetterDigit(byte b)
	{
		return isLetter(b) || isDigit(b);
	}

	static boolean isHex(byte b)
	{
		return isDigit(b) || (('a' <= b) && (b <= 'f')) || (('A' <= b) && (b <= 'F'));
	}
	
	static boolean isPrintable(byte b)
	{
		return (' ' <= b) && (b <= '~');
	}

	// tests
	
	boolean test(byte b);
}
