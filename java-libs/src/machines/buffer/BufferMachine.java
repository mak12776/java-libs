package machines.buffer;

public class BufferMachine
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
	}
	
	public static final short INST_NOOP = 0;
	
	public static void run(byte[][] buffers, int[] pointers, byte flag, int bip, int ip)
	{
		byte[] instBuffer;
		
		if (bip > buffers.length)
			throw new RuntimeError(ErrorType.INVALID_BASE_INST_POINTER, String.valueOf(bip));
		
		instBuffer = buffers[bip];
		
		if (instBuffer == null)
			throw new RuntimeError(ErrorType.NULL_INST_POINTER, String.valueOf(bip));
		
		if ((ip < 0) || (ip > instBuffer.length))
			throw new RuntimeError(ErrorType.INVALID_INST_POINTER, String.valueOf(ip));
	}
}
