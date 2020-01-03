package incomplete.types;

import libs.bytes.Buffer;

public class LinkedBuffer
{
	public static class BufferNode extends Buffer
	{
		public BufferNode next;
		
		public BufferNode(byte[] buffer, int length, BufferNode next)
		{
			super(buffer, length);
			this.next = next;
		}
		
		public BufferNode(int size, BufferNode next)
		{
			super(size);
			this.next = next;
		}
	}
	
	private int additiveSize;
	
	BufferNode first;
	BufferNode last;
	
	public LinkedBuffer(int additiveSize)
	{
		this.first = this.last = null;
		this.additiveSize = additiveSize;
	}
	
	public void append(byte[] buffer, int start, int end)
	{
		if (first == null)
		{
			first = last = new BufferNode(additiveSize, null);
		}
	}
}
