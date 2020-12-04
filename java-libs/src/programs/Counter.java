package programs;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

import library.ByteTest;
import library.SystemTools;
import library.io.ByteBufferedReader;
import library.io.ReadMode;

public class Counter
{
	public static class BufferedCounter extends ByteBufferedReader
	{
		public long lines;
		public long bytes; 
		
		public long words;
		public long numbers;
		public long symbols;
		public long unprintable;
		
		public static enum STATE { NONE, WORD, NUMBER, CR }
		
		protected STATE state;
		
		public BufferedCounter()
		{
			init();
		}
		
		public void init()
		{
			state = STATE.NONE;
			lines = 1;
			bytes = 0;
			
			words = 0;
			numbers = 0;
			symbols = 0;
			unprintable = 0;
		}
		
		@Override
		public void read(ByteBuffer buffer)
		{
			int index = 0;
			byte value;
			
			bytes += buffer.capacity();
			
			index = 0;
			while (index < buffer.capacity())
			{
				value = buffer.get(index);
				
				switch (state)
				{
				case NONE:
					if (ByteTest.isBlank(value))
					{
						index += 1;
					}
					else if (ByteTest.isLetter(value))
					{
						words += 1;
						state = STATE.WORD;
						
						index += 1;
					}
					else if (ByteTest.isDigit(value))
					{
						numbers += 1;
						state = STATE.NUMBER;
						
						index += 1;
					}
					// symbols = printable - letters - digits - blanks
					else if (ByteTest.isPrintable(value)) 
					{
						symbols += 1;
						index += 1;
					}
					else if (ByteTest.isCarriageReturn(value))
					{
						state = STATE.CR;
						index += 1;
					}
					else if (ByteTest.isNewline(value))
					{
						lines += 1;
						index += 1;
					}
					else // unprintable
					{
						unprintable += 1;
						index += 1;
					}
					break;
					
				case CR:
					if (ByteTest.isNewline(value))
						lines += 1;
					state = STATE.NONE;
					index += 1;	
					break;
					
				case WORD:
					if (ByteTest.isLetter(value))
						index += 1;
					else
						state = STATE.NONE;
					break;
					
				case NUMBER:
					if (ByteTest.isDigit(value))
						index += 1;
					else 
						state = STATE.NONE;
					break;
				}
			}
		}
		
		@Override
		public void finish()
		{ }
	}
	
	public static BufferedCounter countFileChannel(FileChannel channel, ReadMode mode, boolean tryReadAll) throws IOException
	{
		BufferedCounter counter = new BufferedCounter();
		counter.readFileChannel(channel, mode, tryReadAll);
		return counter;
	}
	
	public static final int EXIT_SUCCESS = 0;
	public static final int EXIT_FAILURE = 1;
	
	public static class CountError extends Exception
	{
		private static final long serialVersionUID = 3143783550379309325L;
		
		public CountError(String message)
		{
			super(message);
		}
	}
	
	public static BufferedCounter[] count(String[] paths) throws IOException, CountError
	{
		File files[];
		FileChannel channel;
		BufferedCounter bufferedCounterArray[];
		
		files = new File[paths.length];
		for (int index = 0; index < paths.length; index += 1)
		{
			files[index] = new File(paths[index]);
			if (!files[index].exists())
				throw new CountError("file path not found: " + paths[index]);
		}
		
		bufferedCounterArray = new BufferedCounter[paths.length];
		for (int index = 0; index < paths.length; index += 1)
		{
			if (files[index].isFile())
			{
				channel = FileChannel.open(files[index].toPath(), StandardOpenOption.READ);
				
				bufferedCounterArray[index] = new BufferedCounter();
				bufferedCounterArray[index].readFileChannel(channel, ReadMode.DIRECT_BUFFER, false);
			}
		}
		return bufferedCounterArray;
	}
	
	public static void printUsage()
	{
		System.out.println("usage: " + SystemTools.getProgramName() + " [PATH]...");
	}
	
	public static int main(String[] args)
	{
		if (args.length == 0)
		{
			printUsage();
			return 0;
		}
		
		try
		{
			count(args);
		}
		catch (IOException | CountError e)
		{
			System.err.println("error: " + e.getMessage());
			return 1;
		}
		
		return 0;
	}
}
