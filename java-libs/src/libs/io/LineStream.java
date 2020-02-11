package libs.io;

import java.io.IOException;
import java.io.InputStream;

import libs.buffers.immutable.Buffer;
import libs.buffers.mutable.BufferView;

public class LineStream
{
	private InputStream stream;
	private Buffer buffer;
	private BufferView view;
	
	public LineStream(InputStream stream, int bufferSize)
	{
		this.stream = stream;
		this.buffer = new Buffer(bufferSize);
		this.view = new BufferView();
	}
	
	public BufferView next() throws IOException
	{
		return null;
	}
}
