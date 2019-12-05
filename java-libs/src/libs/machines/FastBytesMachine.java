package libs.machines;

public class FastBytesMachine 
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
	
	public FastBytesMachine(byte[] instBuffer, int dataSize, int startPointer, int initialBufferPointer, int initalDataPointer)
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
	
	// bits
	
	public static final byte B_BIT = 		(byte) 0x00;
	public static final byte D_BIT = 		(byte) 0x80;
	
	public static final byte P_BIT =		(byte) 0x00;
	public static final byte S_BIT =		(byte) 0x40;
	
	public static final byte AND_BIT =		(byte) 0x00;
	public static final byte OR_BIT =		(byte) 0x20;
	
	public static final byte BP_BIT =		B_BIT | P_BIT;
	public static final byte DP_BIT =		D_BIT | P_BIT;
	
	public static final byte BS_BIT =		B_BIT | S_BIT;
	public static final byte DS_BIT =		D_BIT | S_BIT;
	
	
	public static final byte TEST_BIT = 	(byte) 0x00;
	public static final byte NOT_BIT = 		(byte) 0x20;
	
	// instruction
	
	public static final byte INST_NOOP = 				0x00;
	
	public static final byte INST_COPY_IM32_XP =		0x01;
	public static final byte INST_COPY_IMX_XPA = 		0x03;
	
	public static final byte INST_TEST_XX_EQ_IM32 =		0x04;
	public static final byte INST_TEST_XX_LT_IM32 =		0x05;
	public static final byte INST_TEST_XX_GT_IM32 =		0x06;
	
	public static final byte INST_TEST_XL_XX_EQ_IM32 =	0x07;
	public static final byte INST_TEST_XL_XX_LT_IM32 =	0x08;
	public static final byte INST_TEST_XL_XX_GT_IM32 =	0x09;
	
	public static final byte INST_TEST_XS_EQ_XP =		0x0A;
	public static final byte INST_TEST_XS_GT_XP =		0x0B;
	public static final byte INST_TEST_XS_LT_XP =		0x0C;
	
	public static final byte INST_TEST_XPA_EQ_IMX =		0x0D;
	
	public void run(byte[] bytes)
	{		
		buffer = bytes;
		while (ip < instBuffer.length)
		{
			switch (nextByte())
			{
			case INST_NOOP:
				break;
				
			// INST_COPY_IM32_XP
				
			case INST_COPY_IM32_XP | B_BIT:
				bp = nextInt();
				break;
				
			case INST_COPY_IM32_XP | D_BIT:
				dp = nextInt();
				break;
				
			// INST_COPY_IMX_XPA
				
			case INST_COPY_IMX_XPA | B_BIT:
				buffer[bp] = nextByte();
				break;
				
			case INST_COPY_IMX_XPA | D_BIT:
				data[dp] = nextInt();
				break;
			
			// INST_TEST_XX_EQ_IM32 | BP

			case INST_TEST_XX_EQ_IM32 | BP_BIT | TEST_BIT:
				test = (bp == nextInt());
				break;
				
			case INST_TEST_XX_EQ_IM32 | BP_BIT | NOT_BIT:
				test = (bp != nextInt());
				break;
				
			case INST_TEST_XX_LT_IM32 | BP_BIT | TEST_BIT:
				test = (bp < nextInt());
				break;
				
			case INST_TEST_XX_LT_IM32 | BP_BIT | NOT_BIT:
				test = (bp >= nextInt());
				break;
				
			case INST_TEST_XX_GT_IM32 | BP_BIT | TEST_BIT:
				test = (bp > nextInt());
				break;
				
			case INST_TEST_XX_GT_IM32 | BP_BIT | NOT_BIT:
				test = (bp <= nextInt());
				break;
				
			// INST_TEST_XX_EQ_IM32 | DP
				
			case INST_TEST_XX_EQ_IM32 | DP_BIT | TEST_BIT:
				test = (dp == nextInt());
				break;
				
			case INST_TEST_XX_EQ_IM32 | DP_BIT | NOT_BIT:
				test = (dp != nextInt());
				break;
				
			case INST_TEST_XX_LT_IM32 | DP_BIT | TEST_BIT:
				test = (dp < nextInt());
				break;
				
			case INST_TEST_XX_LT_IM32 | DP_BIT | NOT_BIT:
				test = (dp >= nextInt());
				break;
				
			case INST_TEST_XX_GT_IM32 | DP_BIT | TEST_BIT:
				test = (dp > nextInt());
				break;
				
			case INST_TEST_XX_GT_IM32 | DP_BIT | NOT_BIT:
				test = (dp <= nextInt());
				break;
			
			// INST_TEST_XX_EQ_IM32 | BS
				
			case INST_TEST_XX_EQ_IM32 | BS_BIT | TEST_BIT:
				test = (buffer.length == nextInt());
				break;
				
			case INST_TEST_XX_EQ_IM32 | BS_BIT | NOT_BIT:
				test = (buffer.length != nextInt());
				break;
				
			case INST_TEST_XX_LT_IM32 | BS_BIT | TEST_BIT:
				test = (buffer.length < nextInt());
				break;
				
			case INST_TEST_XX_LT_IM32 | BS_BIT | NOT_BIT:
				test = (buffer.length >= nextInt());
				break;
				
			case INST_TEST_XX_GT_IM32 | BS_BIT | TEST_BIT:
				test = (buffer.length > nextInt());
				break;
				
			case INST_TEST_XX_GT_IM32 | BS_BIT | NOT_BIT:
				test = (buffer.length <= nextInt());
				break;
				
			// INST_TEST_XX_EQ_IM32 | DS
				
			case INST_TEST_XX_EQ_IM32 | DS_BIT | TEST_BIT:
				test = (data.length == nextInt());
				break;
				
			case INST_TEST_XX_EQ_IM32 | DS_BIT | NOT_BIT:
				test = (data.length != nextInt());
				break;
				
			case INST_TEST_XX_LT_IM32 | DS_BIT | TEST_BIT:
				test = (data.length < nextInt());
				break;
				
			case INST_TEST_XX_LT_IM32 | DS_BIT | NOT_BIT:
				test = (data.length >= nextInt());
				break;
				
			case INST_TEST_XX_GT_IM32 | DS_BIT | TEST_BIT:
				test = (data.length > nextInt());
				break;
				
			case INST_TEST_XX_GT_IM32 | DS_BIT | NOT_BIT:
				test = (data.length <= nextInt());
				break;
				
			
			}
		}
	}
}