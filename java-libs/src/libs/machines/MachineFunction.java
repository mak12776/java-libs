package libs.machines;

public class MachineFunction
{
	public static class RuntimeError extends RuntimeException
	{
		private static final long serialVersionUID = -6786487987052968506L;
		
		private ErrorType type;
		private String message; 
		
		public RuntimeError(ErrorType type)
		{
			super(type.toString());
			this.type = type;
			this.message = null;
		}
		
		public RuntimeError(ErrorType type, String message)
		{
			super(type + ": " + message);
			this.type = type;
			this.message = message;
		}
		
		public ErrorType getType()
		{
			return type;
		}
		
		public String getErrorMessage()
		{
			return message;
		}
	}
	
	public static enum ErrorType
	{
		// null pointers
		
		NULL_INST_POINTER,
		
		// invalid pointers
		
		INVALID_BASE_INST_POINTER,
		INVALID_INST_POINTER,
		
		INVALID_POINTERS_INDEX,
	}
	
	public static final short INST_NOOP = 0;
	
	public static final short INST_COPY_IM32_PI32 = 1;
	public static final short INST_COPY_PI32_PI32 = 2;
	
	public static final boolean SAFE = true;
	
	// read integers
	
	public static byte readByte(byte[] buffer, final int offset, final ErrorType type, final String message)
	{
		if (SAFE)
			if (offset > buffer.length)
				throw new RuntimeError(type, message);
		
		return buffer[offset];
	}
	
	public static short readShort(byte[] buffer, int offset, final ErrorType type, final String message)
	{
		if (SAFE)
			if (offset + Short.BYTES > buffer.length)
				throw new RuntimeError(type, message);
		
		return (short) (
				((buffer[offset++] & 0xFF) << 8) |
				(buffer[offset] & 0xFF));
	}
	
	public static int readInt(byte[] buffer, int offset, final ErrorType type, final String message)
	{
		if (SAFE)
			if (offset + Integer.BYTES > buffer.length)
				throw new RuntimeError(type, message);
		
		return  ((buffer[offset++] & 0xFF) << 24) |
				((buffer[offset++] & 0xFF) << 16) |
				((buffer[offset++] & 0xFF) << 8) |
				(buffer[offset] & 0xFF);
	}
	
	// ** read types
	
	public static int readIM32(byte[] buffer, int offset)
	{
		return readInt(buffer, offset, ErrorType.INVALID_INST_POINTER, "while reading immediate 32");
	}
	
	public static int readPI32(byte[] buffer, int offset)
	{
		return readInt(buffer, offset, ErrorType.INVALID_INST_POINTER, "while reading pointers index 32");
	}
	
	public static short readInst(byte[] buffer, int offset)
	{
		return readShort(buffer, offset, ErrorType.INVALID_INST_POINTER, "while reading instruction");
	}
	
	// ** checkers **
	
	public static void checkPointersIndex(int[] buffer, final int index)
	{
		if ((index < 0) || (index >= buffer.length))
			throw new RuntimeError(ErrorType.INVALID_POINTERS_INDEX, String.valueOf(index));
	}
	
	public static void checkBufferIndex(byte[] buffer, final int index, final ErrorType type)
	{
		if ((index < 0) || (index >= buffer.length))
			throw new RuntimeError(type, String.valueOf(index));
	}
	
	public static void checkBaseInstPointer(byte[][] buffers, final int bip)
	{
		if ((bip < 0) || (bip > buffers.length))
			throw new RuntimeError(ErrorType.INVALID_BASE_INST_POINTER, String.valueOf(bip));
	}
	
	public static void checkInstPointer(byte[] instBuffer, final int ip)
	{
		if ((ip < 0) || (ip > instBuffer.length))
			throw new RuntimeError(ErrorType.INVALID_INST_POINTER, String.valueOf(ip));
	}
	
	public static void checkInstBuffer(byte[] instBuffer, final int bip)
	{
		if (instBuffer == null)
			throw new RuntimeError(ErrorType.NULL_INST_POINTER, String.valueOf(bip));
	}
	
	// ** run function **
	
	public static void run(byte[][] buffers, int[] pointers, byte flag, int bip, int ip)
	{
		final int instSize = 2;
		short inst;
		
		byte[] instBuffer;
		
		int IM32_0;
		
		int PI32_0;
		int PI32_1;
		
		if (SAFE)
			checkBaseInstPointer(buffers, bip);
		
		instBuffer = buffers[bip];
		
		if (SAFE)
		{
			checkInstBuffer(instBuffer, bip);
			checkInstPointer(instBuffer, ip);
		}
		
		while (ip < instBuffer.length)
		{
			inst = readInst(instBuffer, ip);
			ip += instSize;
			
			switch (inst)
			{
			case INST_NOOP:
				break;
				
			case INST_COPY_IM32_PI32:
				IM32_0 = readIM32(instBuffer, ip);
				ip += Integer.BYTES;
				
				PI32_0 = readPI32(instBuffer, ip);
				ip += Integer.BYTES;
				
				if (SAFE)
					checkPointersIndex(pointers, PI32_0);
				
				pointers[PI32_0] = IM32_0;
				break;
				
			case INST_COPY_PI32_PI32:
				PI32_0 = readPI32(instBuffer, ip);
				ip += Integer.BYTES;
				
				if (SAFE)
					checkPointersIndex(pointers, PI32_0);
				
				PI32_1 = readPI32(instBuffer, ip);;
				ip += Integer.BYTES;
				
				if (SAFE)
					checkPointersIndex(pointers, PI32_1);
				
				pointers[PI32_1] = pointers[PI32_0];
				break;
			}
		}
	}
}
