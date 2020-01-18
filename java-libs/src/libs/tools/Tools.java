package libs.tools;

public class Tools
{
	public static final long getMask(final int size)
	{
		switch (size)
		{
		case 8:
			return 0xFFL;
		case 16:
			return 0xFFFFL;
		case 32:
			return 0xFFFFFFFFL;
		case 64:
			return 0xFFFFFFFFFFFFFFL;
		default:
			throw new IllegalArgumentException("invalid size: " + size);
		}
	}
}
