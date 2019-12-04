package libs.incomplete.machines;

public class LittleMachine
{
	private byte[] instBuffer;
	private byte[] fileBuffer;
	
	private int ip;
	
	private int dp;
	private int fp;
	
	private boolean test;
	
	public LittleMachine(byte[] buffer, int start)
	{
		this.instBuffer = buffer;
		this.fileBuffer = null;
		
		this.ip = start;
		this.dp = 0;
		this.fp = 0;
	}
	
	public int run(byte[] file)
	{
		
		return 0;
	}
}
