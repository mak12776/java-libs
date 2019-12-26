package libs.machines;

public class BufferMachine
{
	private byte[][] buffers;
	private int[] dp;
	
	private int bip;
	private int ip;
	
	public BufferMachine(byte[][] buffers, int[] pointers, int baseInstPointer, int instPointer)
	{
		this.buffers = buffers;
		this.dp = pointers;
		
		this.bip = baseInstPointer;
		this.ip = instPointer;
	}
	
	private byte nextByte()
	{
		return this.buffers[bip][ip++];
	}
	
	private short nextShort()
	{
		return 	(short) (
				((this.buffers[bip][ip++] & 0xff) << 8) | 
				(this.buffers[bip][ip++] & 0xff)
				);
	}
	
	private int nextInt()
	{
		return 	((this.buffers[bip][ip++] & 0xff) << 24) | 
				((this.buffers[bip][ip++] & 0xff) << 16) |
				((this.buffers[bip][ip++] & 0xff) << 8) | 
				(this.buffers[bip][ip++] & 0xff);
	}
	
	// instructions
	
	public final byte INST_NOOP = 0x00;
	
	public void run()
	{
		byte inst;
		
		while (true)
		{
			inst = nextByte();
			
		}
	}
}
