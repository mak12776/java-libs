package libs.types;

import java.io.FileInputStream;

public class BufferStream implements Stream<Buffer> 
{
	private FileInputStream stream;
	private Buffer buffer;
	private boolean getNext;
	
	public BufferStream(FileInputStream stream, int bufferSize)
	{
		this.stream = stream;
		this.buffer = new Buffer(bufferSize);
	}
	
	@Override
	public boolean hasNext()
	{
		return false;
	}
	
	@Override
	public Buffer next()
	{
		return null;
	}
}
