package incomplete.bytes.machine;

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
	public static final byte INST_COPY_XP_XP = 			0x02;
	
	public static final byte INST_TEST_XP_EQ_IM8 =		0x03;
	public static final byte INST_TEST_XP_LT_IM8 =		0x04;
	public static final byte INST_TEST_XP_GT_IM8 =		0x05;
	
	public static final byte INST_TEST_XS_EQ_IM8 =		0x06;
	public static final byte INST_TEST_XS_LT_IM8 =		0x07;
	public static final byte INST_TEST_XS_GT_IM8 =		0x08;
	
	public static final byte INST_TEST_XP_EQ_XS =		0x09;
	public static final byte INST_TEST_XP_LT_XS =		0x09;
	public static final byte INST_TEST_XP_GT_XS =		0x09;
	
	public static final byte INST_MAX_VALUE = 			0x3F;
	
	// masks & bits
	
	public static final byte FIRST_BIT_MASK = 		(byte) 0x80;
	public static final byte SECOND_BIT_MASK = 		(byte) 0x40;
	public static final byte INST_MASK = 			(byte) 0x3F;
	
	public static final byte REG_BUFFER_BIT = 	(byte) 0x00;
	public static final byte REG_DATA_BIT = 	(byte) 0x80;
	
	public static final byte TEST_BIT =			(byte) 0x00;
	public static final byte TEST_NOT_BIT = 	(byte) 0x40;
	
	public void run(byte[] buffer)
	{
		byte inst;
		int rp;
		int rs;
		
		while (ip < instBuffer.length)
		{
			inst = nextByte();
			switch (inst & INST_MASK)
			{
			case INST_NOOP:
				break;
				
			case INST_COPY_IM32_XP:
				if ((FIRST_BIT_MASK & inst) == REG_BUFFER_BIT)
					bp = nextInt();
				else
					dp = nextInt();
				break;
				
			case INST_COPY_XP_XP:
				if ((FIRST_BIT_MASK & inst) == REG_BUFFER_BIT)
					bp = dp;
				else
					dp = bp;
				break;
			
			// XP {EQ, LT, GT} IM8
				
			case INST_TEST_XP_EQ_IM8:
				rp = ((FIRST_BIT_MASK & inst) == REG_BUFFER_BIT) ? bp : dp;
				if ((SECOND_BIT_MASK & inst) == TEST_BIT)
					test = (rp == nextInt());
				else
					test = (rp != nextInt());
				break;
				
			case INST_TEST_XP_LT_IM8:
				rp = ((FIRST_BIT_MASK & inst) == REG_BUFFER_BIT) ? bp : dp;
				if ((SECOND_BIT_MASK & inst) == TEST_BIT)
					test = (rp < nextInt());
				else
					test = (rp >= nextInt());
				break;
				
			case INST_TEST_XP_GT_IM8:
				rp = ((FIRST_BIT_MASK & inst) == REG_BUFFER_BIT) ? bp : dp;
				if ((SECOND_BIT_MASK & inst) == TEST_BIT)
					test = (rp > nextInt());
				else
					test = (rp <= nextInt());
				break;
				
			// XS {EQ, LT, GT} IM8
				
			case INST_TEST_XS_EQ_IM8:
				rp = ((FIRST_BIT_MASK & inst) == REG_BUFFER_BIT) ? buffer.length : data.length;
				if ((SECOND_BIT_MASK & inst) == TEST_BIT)
					test = (rp == nextInt());
				else
					test = (rp != nextInt());
				break;
				
			case INST_TEST_XS_LT_IM8:
				rp = ((FIRST_BIT_MASK & inst) == REG_BUFFER_BIT) ? buffer.length : data.length;
				if ((SECOND_BIT_MASK & inst) == TEST_BIT)
					test = (rp < nextInt());
				else
					test = (rp >= nextInt());
				break;
				
			case INST_TEST_XS_GT_IM8:
				rp = ((FIRST_BIT_MASK & inst) == REG_BUFFER_BIT) ? buffer.length : data.length;
				if ((SECOND_BIT_MASK & inst) == TEST_BIT)
					test = (rp > nextInt());
				else
					test = (rp <= nextInt());
				break;
				
			// XP {EQ, LT, GT} XS
			case INST_TEST_XP_EQ_XS:
				if ((FIRST_BIT_MASK & inst) == REG_BUFFER_BIT)
					test = (bp == buffer.length);
				else
					test = (dp == data.length);
				break;
			}
		}
	}
}
