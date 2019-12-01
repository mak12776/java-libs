package tools.machines;

import tools.bytes.PackedView;

public class BytesMachine
{
	private byte[] dataBuffer;
	private byte[] fileBuffer;
	
	private int ip;
	
	
	public BytesMachine(byte[] buffer, int start)
	{
		this.dataBuffer = buffer;
		this.ip = start;
	}
	
	private static final byte INST_NOOP = 0;
	private static final byte INST_COPY = 1; 
	
	public void run()
	{
		
	}
}
