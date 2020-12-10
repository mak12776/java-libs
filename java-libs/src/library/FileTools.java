package library;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import library.exceptions.InvalidReadNumber;
import library.io.ReadMode;

public class FileTools
{
	public static ByteBuffer allocateByteBuffer(int size, boolean direct)
	{
		return (direct) ? ByteBuffer.allocateDirect(size) : ByteBuffer.allocate(size);
	}
	
	public static ByteBuffer read(FileChannel channel, ReadMode mode) throws IOException, InvalidReadNumber
	{
		int size;

		size = Math.toIntExact(channel.size());
		if (size == 0)
			return null;
		
		if (mode == ReadMode.DIRECT_BUFFER || mode == ReadMode.NON_DIRECT_BUFFER)
		{
			ByteBuffer buffer;
			int readNumber;
			
			buffer = allocateByteBuffer(size, mode == ReadMode.DIRECT_BUFFER);
			
			readNumber = channel.read(buffer, 0);
			if (readNumber != size)
				throw new InvalidReadNumber(String.valueOf(readNumber));
			
			return buffer;
		}
		else if (mode == ReadMode.MAPPED_BUFFER)
			return channel.map(MapMode.READ_ONLY, 0, size);
		else
			throw new IllegalArgumentException("unknown mode: " + mode);
	}
	
	public static ByteBuffer read(Path path, ReadMode mode) throws IOException
	{
		return read(FileChannel.open(path, StandardOpenOption.READ), mode);
	}
}
