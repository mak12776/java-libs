
package labs.types.oop.buffers;

import java.io.FileInputStream;
import java.io.IOException;

public class BufferStream
{
	protected FileInputStream stream;
	protected Buffer buffer;

	public BufferStream(FileInputStream stream, int bufferSize)
	{
		this.stream = stream;
		this.buffer = new Buffer(bufferSize);
	}

	public Buffer next() throws IOException
	{
		if (buffer.readFile(stream) == 0)
			return null;
		return buffer;
	}
}
