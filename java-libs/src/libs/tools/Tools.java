
package libs.tools;

public class Tools
{
	public static String[] unitsName = {
			"B", "KB", "MB", "GB", "TB", "PB", "EB" // 7
	};
	
	public static final String formatSize(long size)
	{
		long unit = 1 << 10;
		int index = 1;
		
		if (size < unit)
			return String.format("%d %s", size, unitsName[0]);
		unit <<= 10;
		while (size >= unit)
		{
			unit <<= 10;
			index += 1;
			if (unit == (1 << 60))
				break;
		}
		unit >>= 10;
			
		long reminder = size % unit;
		
		if (reminder == 0)
			return String.format("%d %s", size / unit, unitsName[index]);
		return String.format("%d%.1f %s", size / unit, (double)reminder / (double)unit, unitsName[index]);
	}
	
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
