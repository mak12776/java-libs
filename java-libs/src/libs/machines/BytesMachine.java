package libs.machines;

public class BytesMachine 
{
	private byte[] instBuffer;
	private byte[] dataBuffer;
	private byte[] fileBuffer;
	
	private int ip;
	private int dp;
	private int fp;
	
	public BytesMachine(byte[] instBuffer, int startPointer, int dataSize)
	{
		this.instBuffer = instBuffer;
		this.dataBuffer = new byte[dataSize];
		this.fileBuffer = null;
		this.ip = startPointer;
		this.dp = 0;
		this.fp = 0;
	}
	
	
	
	// instruction
	
	public static final byte INST_NOOP = 			0x00;
	
	public static final byte INST_COPY_A32_FP = 	0x01;
	public static final byte INST_COPY_A32_DP =		0x02;
	
	
	public void run(byte[] fileBuffer)
	{
		while (ip < instBuffer.length)
		{
			switch (instBuffer[ip])
			{
			case INST_NOOP:
				break;
				
			case INST_COPY_A32_FP:
				break;
			}
			ip += 1;
		}
	}
}
