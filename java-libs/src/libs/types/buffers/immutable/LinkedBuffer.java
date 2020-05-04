
package libs.types.buffers.immutable;

public class LinkedBuffer
{
	private static class BufferNode
	{
		byte[] buffer;
		BufferNode next;
	}

	BufferNode first;
	BufferNode last;

	int bufferSize;
	int firstIndex;
	int lastIndex;

	public LinkedBuffer(int bufferSize)
	{
		this.bufferSize = bufferSize;
		this.first = this.last = null;
		this.firstIndex = this.lastIndex = 0;
	}
}
