package tools.types;

public class LinkedBuffer
{
	private static class BufferNode extends Buffer
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
		this.additiveSize = additiveSize;
		this.first = this.last = null;
	}
}
