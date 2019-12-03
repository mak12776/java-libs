package libs.pattern;

public class Pattern
{
	private byte[] buffer;
	private int ip;
	
	public Pattern(byte[] buffer, int start)
	{
		this.buffer = buffer;
		this.ip = start;
	}
	
	public void match(byte[] buffer, int start, int end)
	{
		
	}
}
