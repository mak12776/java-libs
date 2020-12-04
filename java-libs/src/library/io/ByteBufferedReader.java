package library.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import library.FileTools;
import library.exceptions.InvalidReadNumber;

public abstract class ByteBufferedReader
{
	public abstract void read(ByteBuffer buffer);
	public abstract void finish();
	
	public final void readFileChannelAll(FileChannel channel, ReadMode mode) throws IOException
	{
		ByteBuffer buffer = FileTools.readFileChannelAll(channel, mode);
		if (buffer != null)
			read(buffer);
		finish();
	}
	
	public final void readFileChannel(FileChannel channel, ReadMode mode, boolean tryReadAll) throws IOException 
	{
		final int blockSize = 1 << 13; // 2 ^ 13
		if (tryReadAll)
		{
			try
			{
				readFileChannelAll(channel, mode);
			}
			catch (OutOfMemoryError | ArithmeticException e)
			{ }
		}
		
		ByteBuffer buffer;
		long total;
		int readNumber;
		
		if (mode == ReadMode.DIRECT_BUFFER || mode == ReadMode.NON_DIRECT_BUFFER)
		{
			buffer = FileTools.allocateByteBuffer(blockSize, mode == ReadMode.DIRECT_BUFFER);
			
			total = channel.size();
			while (total >= blockSize)
			{
				readNumber = channel.read(buffer, channel.size() - total);
				if (readNumber != blockSize)
					throw new InvalidReadNumber(String.valueOf(readNumber));
				read(buffer);
				total -= blockSize;
			}
			if (total != 0)
			{
				readNumber = channel.read(buffer, channel.size() - total);
				if (readNumber != total)
					throw new InvalidReadNumber(String.valueOf(readNumber));
				read(buffer);
			}
			finish();
		}
		else if (mode == ReadMode.MAPPED_BUFFER)
		{
			total = channel.size();
			while (total >= blockSize)
			{
				buffer = channel.map(MapMode.READ_ONLY, channel.size() - total, blockSize);
				read(buffer);
				total -= blockSize;
			}
			if (total != 0)
			{
				buffer = channel.map(MapMode.READ_ONLY, channel.size() - total, total);
				read(buffer);
			}
			finish();
		}
		else
			throw new IllegalArgumentException("unknown mode: " + mode);
	}
}
