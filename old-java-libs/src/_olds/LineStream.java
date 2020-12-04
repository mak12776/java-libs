
package _olds;

import java.io.EOFException;
import java.io.IOException;

public class LineStream
{
	public ByteStream stream;
	public Line line;
	public int lastIndex;

	public LineStream(ByteStream stream)
	{
		this.stream = stream;
		this.line = null;
		this.lastIndex = 0;
	}

	public Line nextLine() throws IOException
	{
		byte ch;

		if (line == null)
		{
			try
			{
				ch = stream.nextByte();
			} catch (EOFException e)
			{
				return null;
			}

			line = new Line(stream.buffer, stream.index, 0, 1, null);
		} else
		{
			try
			{
				ch = stream.nextByte();
			} catch (EOFException e)
			{
				return null;
			}
			line = line.next = new Line(stream.buffer, stream.index, 1, line.number + 1, null);
		}

		while (true)
		{
			if (ch == '\n')
			{
				return line;
			} else if (ch == '\r')
			{
				stream.setByteAtIndex((byte) '\n');
				try
				{
					ch = stream.nextByte();
				} catch (EOFException e)
				{
					return line;
				}

				if (ch != '\n')
				{
					stream.decIndex();
				}
				return line;
			}
			line.length += 1;
			try
			{
				ch = stream.nextByte();
			} catch (EOFException e)
			{
				return line;
			}
		}

	}
}
