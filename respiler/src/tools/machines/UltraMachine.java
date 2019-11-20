package tools.machines;

import tools.exceptions.EndOfBufferException;

public class UltraMachine
{	
	public static int REGISTER_SIZE = 64;
	public static int ADDRESS_SIZE = 32;
	
	public static int NUMBER_OF_REGISTERS = 32;
	
	private byte[] buffer;
	
	private int end;
	
	private long[] registers;
	private int ip;
	
	public UltraMachine(byte[] buffer, int start, int end)
	{
		this.buffer = buffer;
		this.end = end;
		
		this.registers = new long[NUMBER_OF_REGISTERS];
		this.ip = start;
	}
	
	private byte nextByte() throws EndOfBufferException
	{
		if (ip < end)
		{
			return buffer[ip++];
		}
		throw new EndOfBufferException();
	}
	
	private short nextShort() throws EndOfBufferException
	{
		if (ip + 1 < end)
		{
			return (short) ((buffer[ip++] << 8) | (buffer[ip++]));
		}
		throw new EndOfBufferException();
	}
	
	private int nextInt() throws EndOfBufferException
	{
		if (ip + 3 < end)
		{
			return (buffer[ip++] << 24) | (buffer[ip++] << 16) | (buffer[ip++] << 8) | (buffer[ip++]);
		}
		throw new EndOfBufferException();
	}
	
	private long nextLong() throws EndOfBufferException
	{
		if (ip + 7 < end)
		{
			return 	(buffer[ip++] << 56) | (buffer[ip++] << 48) | (buffer[ip++] << 40) | (buffer[ip++] << 32) |
					(buffer[ip++] << 24) | (buffer[ip++] << 16) | (buffer[ip++] << 8) | (buffer[ip++]);
		}
		throw new EndOfBufferException();
	}
	
	private long next(int size) throws EndOfBufferException
	{
		switch (size)
		{
		case 1:
			return nextByte();
			
		case 2:
			return nextShort();
			
		case 4:
			return nextInt();
			
		case 8:
			return nextLong();
			
		default:
			throw new IllegalArgumentException("invalid size: " + size);
		}
	}
	
	// assembly instructions
	
	public static final short INST_NOOP = 0x0;
	public static final short INST_COPY_R8_IM8 = 0x1;
	
	private int getRegisterNum(byte b)
	{
		return b & 0x1F;
	}
	
	private int getRegisterIndex(byte b)
	{
		return (b & 0xE0) >>> 5;
	}
	
	private void copy_R_IM(int size) throws EndOfBufferException
	{
		byte opr1;
		long opr2;
		
		opr1 = nextByte();
		opr2 = next(size);
		
		int num = getRegisterNum(opr1);
		int index = getRegisterIndex(opr1);
		
		
	}
	
	public void run() throws EndOfBufferException
	{
		short inst;
		
		while (true)
		{
			try
			{
				inst = nextShort();
			}
			catch (EndOfBufferException e) 
			{
				break;
			}
			
			switch (inst)
			{
			case INST_NOOP:
				break;

			case INST_COPY_R8_IM8:
			}
		}
	}
}
