package tools.machines;

public class Pattern
{
	private byte[] codeBuffer;
	private byte[] dataBuffer;
	
	private int ip;
	private int dp;
	
	public Pattern(byte[] buffer, int start)
	{
		this.codeBuffer = buffer;
		this.ip = start;
	}
	
	private static final byte INST_NOOP = 0;
	private static final byte INST_COPY = 1; 
	
	public void run()
	{
		
	}
}
