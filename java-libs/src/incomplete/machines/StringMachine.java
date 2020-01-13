
package incomplete.machines;

public class StringMachine
{
	private byte[] codeBuffer;
	private int ip;
	private boolean test;

	public StringMachine(byte[] buffer, int start)
	{
		this.codeBuffer = buffer;
		this.ip = start;
	}

	public void run(byte[] buffer)
	{
		int bp = 0;

	}
}
