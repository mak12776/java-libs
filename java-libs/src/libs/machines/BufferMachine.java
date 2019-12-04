package libs.machines;

public class BufferMachine 
{
	public static final int MAX_NUMBER_OF_POINTERS = 256;
	
	private byte[] instBuffer;
	private byte[][] buffers;
	
	private int ip;
	private int bip;
	
	private int[] bp;
	
	public BufferMachine(byte[] instBuffer, int startInst, int numberOfBuffers, int numberOfPointers)
	{
		this.instBuffer = instBuffer;
		this.ip = startInst;
		this.buffers = new byte[numberOfBuffers][];
		this.bp = new int[numberOfPointers];
	}
	
	
}
