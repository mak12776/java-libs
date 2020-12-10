package programs;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.commons.lang3.time.StopWatch;

import compression.SegmentedBuffer;
import library.FileTools;
import library.io.ReadMode;
import library.types.SizeManager;

public class Main
{
	public static File dataFolder = new File("D:\\Codes\\data-examples");
	
	public static class ThreadExample extends Thread
	{
		@Override
		public synchronized void start()
		{
			// TODO Auto-generated method stub
			super.start();
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		int dataBits = 8;
		SegmentedBuffer segmentedBuffer = new SegmentedBuffer();
		ByteBuffer fileBuffer;
		
		if (args.length == 1)
			dataBits = Integer.parseInt(args[0]);
		
		for (File file : dataFolder.listFiles((file) -> file.isFile()) )
		{
			fileBuffer = FileTools.read(file.toPath(), ReadMode.NON_DIRECT_BUFFER);
			
			System.out.println("file: " + file.getName() + ", data bits: " + dataBits + ", file size: " + fileBuffer.capacity());
			
			StopWatch stopWatch = StopWatch.createStarted();
			segmentedBuffer.ThreadScanBuffer(SizeManager.doubleSizeManager, dataBits, fileBuffer, 5);
			stopWatch.stop();
			
			System.out.println("time: " + stopWatch.getMessage());
		}
	}
}
