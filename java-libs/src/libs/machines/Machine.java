package libs.machines;

public class Machine
{
	private byte[][] buffers;
	private int[] dp;
	
	private int bip;
	private int ip;
	
	public Machine(byte[][] buffers, int[] pointers, int baseInstPointer, int instPointer)
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
	
	public static final short INST_NOOP = 			0x00;
	
	private static final short BASE1 = 				INST_NOOP;
	
	public static final short INST_COPY_IM32_DPI = 	BASE1 + 1;
	public static final short INST_COPY_DPI_DPI = 	BASE1 + 2;
	
	private static final short BASE2 = 				INST_COPY_DPI_DPI;
	
	
	public void run() throws MachineRuntimeException
	{
		short inst;
		byte[] instBuffer;
		
		while (bip < buffers.length)
		{
			instBuffer = buffers[bip];
			if (instBuffer == null)
			{
				throw new MachineRuntimeException(
						ErrorType.INVALID_BASE_INSTRUCTION_POINTER,
						"null buffer pointer: " + bip);
			}
			
			while (ip < instBuffer[bip])
			{
				
			}
		}
		
		throw new MachineRuntimeException(
				ErrorType.INVALID_BASE_INSTRUCTION_POINTER,
				"base instruction pointer is out of range: " + bip);
	}
}
