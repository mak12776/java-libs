package tbx.machines;

public class LittleMachine
{
	private byte[] dataBuffer;
	private byte[] fileBuffer;
	
	private int ip;
	
	private int dp;
	private int fp;
	
	private boolean test;
	
	public LittleMachine(byte[] buffer, int start)
	{
		this.dataBuffer = buffer;
		this.fileBuffer = null;
		
		this.ip = start;
		this.dp = 0;
		this.fp = 0;
	}
	
	public void run()
	{
		
	}
}
