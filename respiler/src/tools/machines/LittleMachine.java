package tools.machines;

public class LittleMachine
{
	public static final int NUMBER_OF_DATA_POINTERS = 0xFF;
	public static final int NUMBER_OF_FILE_POINTERS = 0xFF;
	
	public LittleMachine(byte[] buffer)
	{
		this.buffer = buffer;
		this.ip = 0;
		this.dp = new int[NUMBER_OF_DATA_POINTERS];
		this.fileBuffer = null;
		this.fp = new int[NUMBER_OF_FILE_POINTERS];
	}
	
	private byte[] buffer;
	private int ip;
	private int[] dp;
	
	private byte[] fileBuffer;
	private int[] fp;
	
	// instructions
	
	public static final byte INST_NOOP =			0x00;
	
	public static final byte INST_COPY_A32_IP =		0x01;
	
	public static final byte INST_COPY_IP_BP = 		0x03;
	public static final byte INST_COPY_BP_IP =		0x04;
	
	public static final byte INST_COPY_A32_BP =		0x02;
	public static final byte INST_COPY_BP_BP =		0x05;
	
	public static final byte INST_COPY_ = 			0x06; 
	
}
