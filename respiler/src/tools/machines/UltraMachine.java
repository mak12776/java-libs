package tools.machines;

import tools.exceptions.InvalidByteCodeException;

public class UltraMachine
{
	
	public static int REGISTER_SIZE = 64;
	public static int ADDRESS_SIZE = 32;
	
	public static int NUMBER_OF_REGISTERS = 32;
	
	public UltraMachine(int bufferSize)
	{
		this.buffer = new byte[bufferSize];
		
		this.registers = new long[NUMBER_OF_REGISTERS];
		this.ip = 0;
	}
	
	private byte[] buffer;
	
	private long[] registers;
	private int ip;
	
	public UltraMachine(byte[] buffer, int start)
	{
		this.buffer = buffer;
		
		this.registers = new long[NUMBER_OF_REGISTERS];
		this.ip = start;
	}
	
	public byte[] getBuffer()
	{
		return buffer;
	}
	
	public int getIP()
	{
		return ip;
	}
	
	public long[] getRegisters()
	{
		return registers;
	}
	
	private static final boolean SAFE = false;
	
	private long IM;
	private byte REG;
	private int ADDR;
	
	private int regNum;
	private int regIndex;

	private void checkSize(final int size)
	{
		if ((size != 8) && (size != 16) && (size != 32) && (size != 64))
			throw new IllegalArgumentException("invalid size: " + size);
	}
	
	private long next(int size)
	{
		if (SAFE)
			checkSize(size);
		
		long value = 0;
		
		size /= 8;
		for (int i = 0; i < size; i += 1)
		{
			value <<= 8;
			value = value | (buffer[ip++]);
		}
		
		return value;
	}
	
	private void write(int offset, int size, long value)
	{
		if (SAFE)
			checkSize(size);
		
		size /= 8;
		for (int index = size - 1; index >= 0; index -= 1)
		{
			buffer[offset + index] = (byte) (value & 0xFF);
			value >>>= 8;
		}
	}
	
	private long read(int offset, int size)
	{
		if (SAFE)
			checkSize(size);
		
		long value = 0;
		
		size /= 8;
		for (int index = size - 1; index >= 0; index -= 1)
		{
			value <<= 8;
			value = value | (buffer[offset + index] & 0xFF);
		}
		
		return value;
	}
	
	private final long getMask(final int size)
	{
		switch (size)
		{
		case 8:
			return 0xFFL;
		case 16:
			return 0xFFFFL;
		case 32:
			return 0xFFFFFFFFL;
		case 64:
			return 0xFFFFFFFFFFFFFFL;
		default:
			throw new IllegalArgumentException("invalid size: " + size);
		}
	}
	
	// assembly instructions
	
	private void nextIm(final int size)
	{
		IM = next(size);
	}

	private void nextImAddr()
	{
		ADDR = (int) next(32);
	}
	
	private void nextRegAddr()
	{
		ADDR = (int) registers[(int) next(8) & 0x1F];
	}
	
	private void nextReg(final int size) throws InvalidByteCodeException
	{
		if (SAFE)
			checkSize(size);
		
		REG = (byte) next(8);
		regNum = REG & 0x1F;
		regIndex = (REG & 0xE0) >>> 5;

		if ((regIndex != 8) && regIndex > (64 / size))
			throw new InvalidByteCodeException("invalid register operand index: " + regIndex);
	}
	
	public static final short INST_NOOP = 			0x0;		
	
	public static final short INST_COPY_R8_IM8 = 	0x1;
	public static final short INST_COPY_R16_IM16 = 	0x2;
	public static final short INST_COPY_R32_IM32 = 	0x3;
	public static final short INST_COPY_R64_IM64 = 	0x4;
	
	private void copyRegIm(int size) throws InvalidByteCodeException
	{
		nextReg(size);
		nextIm(size);
		
		registers[regNum] = (registers[regNum] & ~(getMask(size) << regIndex)) | (IM << regIndex);
	}
	
	public static final short INST_COPY_RA_IM8 = 	0x5;
	public static final short INST_COPY_RA_IM16 =	0x6;
	public static final short INST_COPY_RA_IM32 = 	0x7;
	public static final short INST_COPY_RA_IM64 = 	0x8;
	
	private void copyRegAddrIm(int size)
	{
		nextRegAddr();
		nextIm(size);
		
		write(ADDR, size, IM);
	}
	
	public static final short INST_COPY_IA_IM8 = 	0x9;
	public static final short INST_COPY_IA_IM16 = 	0xA;
	public static final short INST_COPY_IA_IM32 = 	0xB;
	public static final short INST_COPY_IA_IM64 = 	0xC;
	
	private void copyImAddrIm(int size)
	{
		nextImAddr();
		nextIm(size);
		
		write(ADDR, size, IM);
	}
	
	public static final short INST_EXIT =			0xFF;
	
	public void run() throws InvalidByteCodeException
	{
		main_loop:
		while (ip < buffer.length)
		{
			switch ((short) next(16))
			{
			case INST_NOOP:
				break;
				

			case INST_COPY_R8_IM8:
				copyRegIm(8);
				break;
				
			case INST_COPY_R16_IM16:
				copyRegIm(16);
				break;
				
			case INST_COPY_R32_IM32:
				copyRegIm(32);
				break;
				
			case INST_COPY_R64_IM64:
				copyRegIm(64);
				break;
				
				
			case INST_COPY_RA_IM8:
				copyRegAddrIm(8);
				break;
				
			case INST_COPY_RA_IM16:
				copyRegAddrIm(16);
				break;
				
			case INST_COPY_RA_IM32:
				copyRegAddrIm(32);
				break;
				
			case INST_COPY_RA_IM64:
				copyRegAddrIm(64);
				break;
				
				
			case INST_COPY_IA_IM8:
				copyImAddrIm(8);
				break;
				
			case INST_COPY_IA_IM16:
				copyImAddrIm(16);
				break;
				
			case INST_COPY_IA_IM32:
				copyImAddrIm(32);
				break;
				
			case INST_COPY_IA_IM64:
				copyImAddrIm(64);
				break;
				
				
			case INST_EXIT:
				break main_loop;
			}
		}
	}
}
