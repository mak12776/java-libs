package libs.pattern;

public class Pattern
{
	private byte[] codeBuffer;
	private byte[] dataBuffer;
	
	private int ip;
	private int bp;
	
	public Pattern(byte[] buffer, int start)
	{
		this.codeBuffer = buffer;
		this.dataBuffer = null;
		this.ip = start;
		this.bp = 0;
	}
	
	public void match(byte[] buffer, int start, int end)
	{
		
	}
}
