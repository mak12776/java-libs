package compression;

import java.nio.ByteBuffer;
import java.util.Arrays;

import library.Tools;
import library.types.SizeManager;

public class SegmentedBuffer
{
	public static int getBytePerBit(int bits)
	{
		return (bits / 8) + ((bits % 8 == 0) ? 0 : 1);
	}
	
	public static class DataCountBuffer
	{
		public SizeManager sizeManager;
		public int suggestedInitialSize;
		
		public int dataBits;
		public int dataSize;
		
		public int size;
		public int length;
		public int dataBlockSize;
		
		public byte[] dataBuffer;
		public int[] countArray;
		
		public DataCountBuffer(SizeManager sizeManager, int suggestedSize, int dataBits)
		{
			this.sizeManager = sizeManager;
			this.suggestedInitialSize = suggestedSize;
			
			this.dataBits = dataBits;
			this.dataSize = getBytePerBit(dataBits);
		}
		
		protected void allocateDataCount()
		{
			this.size = Math.min(this.sizeManager.getNewSize(0), this.suggestedInitialSize);
			this.length = 0;
			
			this.dataBlockSize = (this.dataSize == 1) ? this.size : Math.multiplyExact(this.size, this.dataSize);
			this.dataBuffer = new byte[this.dataBlockSize];
			this.countArray = new int[this.size];
		}
		
		protected void reallocateMore()
		{
			this.size = this.sizeManager.getNewSize(this.size);
			
			this.dataBlockSize = (this.dataSize == 1) ? this.size : Math.multiplyExact(this.size, this.dataSize);
			this.dataBuffer = Arrays.copyOf(this.dataBuffer, this.dataBlockSize);
			this.countArray = Arrays.copyOf(this.countArray, this.size);
		}
		
		protected void increaseDataCount(byte value)
		{
			for (int index = 0; index < length; index += 1)
			{
				if (this.dataBuffer[index] == value)
				{
					this.countArray[index] += 1;
					return;
				}
			}
			
			if (this.length == this.size)
				reallocateMore();
			
			this.dataBuffer[this.length] = value;
			this.countArray[this.length] = 1;
			this.length += 1;
		}
		
		protected void increaseDataCount(byte[] buffer, int offset)
		{
			int lastOffset = this.length * this.dataSize;
			
			for (int fromIndex = 0, toIndex = this.dataSize; fromIndex < lastOffset; fromIndex += this.dataSize, toIndex += this.dataSize)
			{
				if (Arrays.compare(this.dataBuffer, fromIndex, toIndex, buffer, offset, offset + this.dataSize) == 0)
				{
					this.countArray[fromIndex / this.dataSize] += 1;
					return;
				}
			}
			
			if (this.length == this.size)
				reallocateMore();
			
			System.arraycopy(buffer, offset, this.dataBuffer, lastOffset, this.dataSize);
			this.countArray[this.length] = 1;
			this.length += 1;
		}
		
		public void scanBuffer(ByteBuffer buffer)
		{
			if (!buffer.hasArray())
				throw new UnsupportedOperationException("buffer don't support array() method.");
			
			if (dataBits == 8)
			{
				allocateDataCount();
				
				for (int index = 0; index < buffer.capacity(); index += 1)
					increaseDataCount(buffer.get(index));
			}
			if (dataBits % 8 == 0)
			{
				allocateDataCount();
				
				if (buffer.arrayOffset() != 0)
					throw new UnsupportedOperationException("unsupported array offset: " + buffer.arrayOffset());
				
				
				byte[] bufferArray = buffer.array();
				int bufferLength = buffer.capacity();
				
				for (int offset = 0; offset < bufferLength; offset += this.dataSize)
					increaseDataCount(bufferArray, offset);	
			}
			else
				throw new IllegalArgumentException("unsupported data bits: " + dataBits);
		}
	}
	
	public static class DataCountBufferRunnable implements Runnable
	{
		public DataCountBuffer dataCountBuffer;
		public ByteBuffer buffer;
		
		public DataCountBufferRunnable(SizeManager sizeManager, int suggestedSize, int dataBits, ByteBuffer buffer)
		{
			dataCountBuffer = new DataCountBuffer(sizeManager, suggestedSize, dataBits);
			this.buffer = buffer;
		}
		
		@Override
		public void run()
		{
			dataCountBuffer.scanBuffer(buffer);
		}
		
		public static Thread newThread(SizeManager sizeManager, int suggestedSize, int dataBits, ByteBuffer buffer)
		{
			return new Thread(new DataCountBufferRunnable(sizeManager, suggestedSize, dataBits, buffer));
		}
	}
	
	public int bufferBits;
	public int bufferSize;
	
	public int possibleDataNumber;
	public int totalDataCount;
	
	SizeManager sizeManager;
	
	public SegmentedBuffer(SizeManager sizeManager) 
	{ 
		this.sizeManager = sizeManager;
	}
	
	public SegmentedBuffer()
	{
		this(SizeManager.defaultSizeManager);
	}
	
	public void initialize(int dataBits, int bufferSize)
	{
		this.bufferSize = bufferSize;
		this.bufferBits = Math.multiplyExact(this.bufferSize, 8);
		
		try { this.possibleDataNumber = Tools.powExact(2, dataBits); }
		catch (ArithmeticException e) { this.possibleDataNumber = Integer.MAX_VALUE; }
		this.totalDataCount = this.bufferBits / dataBits;
	}
	
	public void ThreadScanBuffer(SizeManager sizeManager, int dataBits, ByteBuffer buffer, int numberOfThreads)
	{
		Thread[] threadArray;
		
		if (numberOfThreads == 0)
			numberOfThreads = Runtime.getRuntime().availableProcessors();
		
		initialize(dataBits, buffer.capacity());
		
		threadArray = new Thread[numberOfThreads];
		
		int dataSize = getBytePerBit(dataBits);
		int shareCount = totalDataCount / numberOfThreads;
		int remainingCount = totalDataCount % numberOfThreads;
		
		int startIndex = 0;
		int endIndex = shareCount + ((remainingCount-- > 0) ? 1 : 0);
		for (int index = 0; index < threadArray.length; index += 1)
		{
			buffer.position(startIndex * dataSize);
			buffer.limit(endIndex * dataSize);
			
			threadArray[index] = DataCountBufferRunnable.newThread(sizeManager, this.possibleDataNumber, dataBits, buffer);
			
			startIndex = endIndex;
			endIndex += shareCount + ((remainingCount-- > 0) ? 1 : 0);
		}
		
		for (Thread thread : threadArray)
			thread.start();
		
		for (Thread thread : threadArray)
		{
			try { thread.join(); }
			catch (InterruptedException e) 
			{
				System.err.println("--- MAIN THREAD ---");
				e.printStackTrace(); 
			}
		}
	}
	
	public void ScanBuffer(int dataBits, ByteBuffer buffer)
	{
		initialize(dataBits, buffer.capacity());
	}
}
