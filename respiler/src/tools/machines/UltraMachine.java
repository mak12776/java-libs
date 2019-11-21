package tools.machines;

import tools.exceptions.EndOfBufferException;

public class UltraMachine
{	
	public static int REGISTER_SIZE = 64;
	public static int ADDRESS_SIZE = 32;
	
	public static int NUMBER_OF_REGISTERS = 32;
	
	private long[] registers;
	private int ip;
	
	private byte[] buffer;
	private int end;
	
	private byte IM8;
	private short IM16;
	private int IM32;
	private long IM64;
	
	private byte REG;
	
	private int RegisterIndex;
	private int RegisterNumber;
	
	
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
	
	// assembly instructions
	
	private void getRegister(final int size) throws EndOfBufferException
	{
		REG = nextByte();
		RegisterNumber = REG & 0x1F;
		RegisterIndex = (REG & 0xE0) >>> 5;
		switch (size)
		{
		case 8:
			break;
		case 16:
			if (RegisterIndex > 4)
			{
				
			}
			break;
		}
	}
	
	private void getIM(final int size) throws EndOfBufferException
	{
		switch (size)
		{
		case 8:
			IM8 = nextByte();
		case 16:
			IM16 = nextShort();
		case 32:
			IM32 = nextInt();
		case 64:
			IM64 = nextLong();
		default:
			throw new IllegalArgumentException("invalid size: " + size);
		}
	}
	
	public static final short INST_NOOP = 0x0;
	public static final short INST_COPY_R8_IM8 = 0x1;
	
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
				getRegister();
				getIM(8);
			}
		}
	}
}
