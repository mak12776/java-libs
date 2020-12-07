package compression;

import java.nio.ByteBuffer;
import library.Tools;

public class SegmentedBuffer
{
	public int dataBits;
	public int dataSize;
	
	public int bufferBits;
	public int bufferSize;
	
	public SegmentedBuffer() { }
	
	public static void ScanBuffer(int dataBits, ByteBuffer buffer)
	{
		SegmentedBuffer result = new SegmentedBuffer();
		
		result.bufferSize = buffer.capacity();
		result.bufferBits = Math.multiplyExact(result.bufferSize, 8);
		
		result.dataBits = dataBits;
		result.dataSize = Tools.upperBound(dataBits, 8);
		
		
	}
	
}
