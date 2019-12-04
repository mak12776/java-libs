package libs.machines;

public class BytesMachine 
{
	private byte[] instBuffer;
	private byte[] buffer;
	private int[] data;
	
	private int ip;
	private int bp;
	private int dp;
	
	private boolean test;
	
	//	bs: buffer size
	//	ds:	data size
	
	public BytesMachine(byte[] instBuffer, int dataSize, int startPointer, int initialBufferPointer, int initalDataPointer)
	{
		this.instBuffer = instBuffer;
		this.data = new int[dataSize];
		this.buffer = null;
		
		this.ip = startPointer;
		this.bp = initialBufferPointer;
		this.dp = initalDataPointer;
	}
	
	private byte nextByte()
	{
		return instBuffer[ip++];
	}
	
	private int nextInt()
	{
		return (instBuffer[ip++] << 24) | (instBuffer[ip++] << 16) | (instBuffer[ip++] << 8) | (instBuffer[ip++]);
	}
	
	// instruction
	
	public static final byte INST_NOOP = 				0x00;
	
	public static final byte INST_COPY_IM32_XP = 		0x01;
	
	public static final byte INST_TEST_XP_EQ_IM8 =		0x02;
	public static final byte INST_TEST_XP_NE_IM8 =		0x03;
	public static final byte INST_TEST_XP_LT_IM8 =		0x04;
	public static final byte INST_TEST_XP_LE_IM8 =		0x05;
	
	public static final byte INST_MASK = (byte) 0x7F;
	public static final byte REG_MASK = (byte) 0x80;
	
	public static final byte BUFFER_BIT = 	(byte) 0x00;
	public static final byte DATA_BIT = 	(byte) 0x80;
	
	public void run(byte[] buffer)
	{
		byte inst;
		while (ip < instBuffer.length)
		{
			switch (inst = nextByte())
			{
			case INST_NOOP:
				break;
				
			case INST_COPY_IM32_XP:
				if ((REG_MASK & inst) == BUFFER_BIT)
					bp = nextInt();
				else
					dp = nextInt();
				break;
				
			}
		}
	}
}
