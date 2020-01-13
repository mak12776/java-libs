
package incomplete.machines;

public class ExaMachine
{
	public static final int NUMBER_OF_DATA_POINTERS = 0xFF;

	public ExaMachine(byte[] dataBuffer, int start)
	{
		this.dataBuffer = dataBuffer;
		this.fileBuffer = null;
		this.ip = start;
		this.dp = 0;
		this.fp = 0;
		this.test = false;
	}

	private byte[] dataBuffer;
	private byte[] fileBuffer;
	private int ip;

	private int dp;
	private int fp;

	private boolean test;

	// instructions

	public static final byte INST_NOOP = 0x00;

	public static final byte INST_COPY_A32_DP = 0x01;
	public static final byte INST_COPY_DP_DP = 0x02;

	public static final byte INST_COPY_DA_FA = 0x03;
	public static final byte INST_COPY_FA_DA = 0x04;

	public void run(byte[] fileBuffer)
	{
		this.fileBuffer = fileBuffer;
	}
}
