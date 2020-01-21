package libs.types;

import java.io.IOException;
import java.io.InputStream;

import libs.buffers.Buffer;
import libs.buffers.BufferView;

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
		if (buffer.length() == 0)
			if (buffer.readFile(stream) == 0)
				return null;
		
		
		
		return view;
	}
}
