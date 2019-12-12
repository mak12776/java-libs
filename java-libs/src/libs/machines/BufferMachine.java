package libs.machines;

public class BufferMachine 
{	
	private byte[] instBuffer;
	private byte[][] buffers;
	
	private int ip;
	private int[] dp;
	
	public BufferMachine(byte[] instBuffer, int startInst, int numberOfBuffers, int numberOfPointers)
	{
		this.instBuffer = instBuffer;
		this.ip = startInst;
		this.buffers = new byte[numberOfBuffers][];
		this.dp = new int[numberOfPointers];
	}
}
