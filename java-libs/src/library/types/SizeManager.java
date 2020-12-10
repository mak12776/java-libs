package library.types;

public interface SizeManager
{
	public int getNewSize(int size);
	
	public static final int initialSize = 1 << 13; // 8192
	
	public static SizeManager doubleSizeManager = new SizeManager()
	{
		@Override
		public int getNewSize(int size)
		{
			return (size < initialSize) ? initialSize : Math.multiplyExact(size, 2);
		}
	};
	
	public static SizeManager adderSizeManager = new SizeManager()
	{
		@Override
		public int getNewSize(int size)
		{
			return (size < initialSize) ? initialSize : Math.addExact(size, initialSize);
		}
	};
	
	public static SizeManager defaultSizeManager = doubleSizeManager;
}
