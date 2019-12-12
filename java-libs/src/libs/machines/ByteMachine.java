package libs.machines;

public class ByteMachine 
{
	private byte[][] buffers;
	
	private int ip;
	private int bip;
	
	private int []dp;
	
	public ByteMachine(byte[][] buffers, int ip, int bip, int[] dp)
	{
		this.buffers = buffers;
		this.ip = ip;
		this.bip = bip;
		this.dp = dp;
	}
	
	public void run()
	{
		
	}
}
