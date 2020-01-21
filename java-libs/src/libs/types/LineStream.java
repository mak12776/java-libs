package libs.types;

import java.io.InputStream;

import libs.bytes.buffers.Buffer;
import libs.bytes.buffers.BufferView;

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
	
	public BufferView next()
	{
		if (buffer.length() == 0)
		{
			
		}
		
		return view;
	}
}
