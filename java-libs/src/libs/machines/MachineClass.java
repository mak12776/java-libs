package libs.machines;

public class MachineClass
{
	public static final boolean SAFE = true;
	
	private byte[][] buffers;
	private int[] pointers;
	private byte flags;
	
	private int bip;
	private int ip;
	
	public MachineClass(byte[][] buffers, int [] pointers, byte flags, int bip, int ip)
	{
		this.buffers = buffers;
		this.pointers = pointers;
		this.flags = flags;
		
		this.bip = bip;
		this.ip = ip;
	}
	
	public static enum ErrorType
	{
		NULL_BASE_INST_POINTER_BUFFER,
		NULL_BASE_INDEX_BUFFER,
		
		INVALID_BASE_INST_POINTER,
		INVALID_INST_POINTER,
		
		INVALID_POINTERS_INDEX,
		INVALID_BASE_INDEX,
		INVALID_BUFFER_INDEX,
		
		END_OF_INST_BUFFER,
	}
	
	public static class RuntimeError extends RuntimeException
	{
		private static final long serialVersionUID = -6843848457917850934L;
		
		private ErrorType type;
		private String message;
		
		public RuntimeError(ErrorType type, String message)
		{
			super(type + ": " + message);
			this.type = type;
			this.message = message;
		}
		
		public RuntimeError(ErrorType type)
		{
			super(type.toString());
			this.type = type;
			this.message = null;
		}
		
		public String getErrorMessage()
		{
			return this.message;
		}
		
		public ErrorType getErrorType()
		{
			return this.type;
		}
	}
	
	// inst buffer readers
	
	private byte readByte()
	{
		return instBuffer[ip++];
	}
	
	private short readShort()
	{
		return (short) (
				((instBuffer[ip++] & 0xFF) << (8 * 1)) | 
				((instBuffer[ip++] & 0xFF) << (8 * 0))
				);
	}
	
	private int readInt()
	{
		return 	((instBuffer[ip++] & 0xFF) << (8 * 3)) | 
				((instBuffer[ip++] & 0xFF) << (8 * 2)) |
				((instBuffer[ip++] & 0xFF) << (8 * 1)) |
				((instBuffer[ip++] & 0xFF) << (8 * 0));
	}
	
	// data readers
	
	private int readPointersIndex()
	{
		int index = readInt();
		if (SAFE)
			if ((index < 0) || (index >= pointers.length))
				throw new RuntimeError(ErrorType.INVALID_POINTERS_INDEX, String.valueOf(index));
		return index;
	}
	
	private int readDataBufferIndex_ImmediateIndex()
	{
		int index = readInt();
		if (SAFE)
			if ((index < 0) || (index >= dataBuffer.length))
				throw new RuntimeError(ErrorType.INVALID_BUFFER_INDEX, String.valueOf(index));
		return index;
	}
	
	private int readDataBufferIndex_PointersIndex()
	{
		int index = pointers[readPointersIndex()];
		if (SAFE)
			if ((index < 0) || (index >= dataBuffer.length))
				throw new RuntimeError(ErrorType.INVALID_BUFFER_INDEX, String.valueOf(index));
		return index;
	}
	
	private int readSecondDataBufferIndex_ImmediateIndex()
	{
		int index = readInt();
		if (SAFE)
			if ((index < 0) || (index >= secondDataBuffer.length))
				throw new RuntimeError(ErrorType.INVALID_BUFFER_INDEX, String.valueOf(index));
		return index;
	}
	
	private int readSecondDataBufferIndex_PointersIndex()
	{
		int index = pointers[readPointersIndex()];
		if (SAFE)
			if ((index < 0) || (index >= secondDataBuffer.length))
				throw new RuntimeError(ErrorType.INVALID_BUFFER_INDEX, String.valueOf(index));
		return index;
	}
	
	// next data buffer
	
	private void nextDataBuffer_ImmediateIndex()
	{
		int index = readInt();
		if (SAFE)
			if ((index < 0) || (index >= buffers.length))
				throw new RuntimeError(ErrorType.INVALID_BASE_INDEX, String.valueOf(index));
		dataBuffer = buffers[index];
		if (SAFE)
			if (dataBuffer == null)
				throw new RuntimeError(ErrorType.NULL_BASE_INDEX_BUFFER, String.valueOf(index));
	}
	
	private void nextDataBuffer_PointersIndex()
	{
		int index = pointers[readPointersIndex()];
		if (SAFE)
			if ((index < 0) || (index >= buffers.length))
				throw new RuntimeError(ErrorType.INVALID_BASE_INDEX, String.valueOf(index));
		dataBuffer = buffers[index];
		if (SAFE)
			if (dataBuffer == null)
				throw new RuntimeError(ErrorType.NULL_BASE_INDEX_BUFFER, String.valueOf(index));
	}
	
	private void nextSecondDataBuffer_ImmediateIndex()
	{
		int index = readInt();
		if (SAFE)
			if ((index < 0) || (index >= buffers.length))
				throw new RuntimeError(ErrorType.INVALID_BASE_INDEX, String.valueOf(index));
		secondDataBuffer = buffers[index];
		if (SAFE)
			if (secondDataBuffer == null)
				throw new RuntimeError(ErrorType.NULL_BASE_INDEX_BUFFER, String.valueOf(index));
	}
	
	private void nextSecondDataBuffer_PointersIndex()
	{
		int index = pointers[readPointersIndex()];
		if (SAFE)
			if ((index < 0) || (index >= buffers.length))
				throw new RuntimeError(ErrorType.INVALID_BASE_INDEX, String.valueOf(index));
		secondDataBuffer = buffers[index];
		if (SAFE)
			if (secondDataBuffer == null)
				throw new RuntimeError(ErrorType.NULL_BASE_INDEX_BUFFER, String.valueOf(index));
	}
	
	// checkers
	
	private void checkBaseInstPointer()
	{
		if ((bip < 0) || (bip >= buffers.length))
			throw new RuntimeError(ErrorType.INVALID_BASE_INST_POINTER, String.valueOf(bip));
	}
	
	private void checkInstBuffer()
	{
		if (instBuffer == null)
			throw new RuntimeError(ErrorType.NULL_BASE_INST_POINTER_BUFFER, String.valueOf(bip));
	}
	
	private void checkInstPointer()
	{
		if ((ip < 0) || (ip >= instBuffer.length))
			throw new RuntimeError(ErrorType.INVALID_INST_POINTER, String.valueOf(ip));
	}
	
	// read inst buffer
	
	private byte[] instBuffer;
	private byte[] dataBuffer;
	private byte[] secondDataBuffer;
	
	// instructions
	
	// NOOP
	
	public static final short INST_NOOP = 0;
	
	// COPY IM/PI PI
	
	public static final short INST_COPY_IM32_PI32 = 1;
	public static final short INST_COPY_PI32_PI32 = 2;
	
	// COPY IM8 MEM8
	
	public static final short INST_COPY__IM8__IM_IM_8 = 3;
	public static final short INST_COPY__IM8__IM_PI_8 = 4;
	public static final short INST_COPY__IM8__PI_IM_8 = 5;
	public static final short INST_COPY__IM8__PI_PI_8 = 6;
	
	// COPY MEM8 MEM8
	
	public static final short INST_COPY__IM_IM_8__IM_IM_8 = 7;
	public static final short INST_COPY__IM_IM_8__IM_PI_8 = 8;
	public static final short INST_COPY__IM_IM_8__PI_IM_8 = 9;
	public static final short INST_COPY__IM_IM_8__PI_PI_8 = 10;

	public static final short INST_COPY__IM_PI_8__IM_IM_8 = 11;
	public static final short INST_COPY__IM_PI_8__IM_PI_8 = 12;
	public static final short INST_COPY__IM_PI_8__PI_IM_8 = 13;
	public static final short INST_COPY__IM_PI_8__PI_PI_8 = 14;

	public static final short INST_COPY__PI_IM_8__IM_IM_8 = 15;
	public static final short INST_COPY__PI_IM_8__IM_PI_8 = 16;
	public static final short INST_COPY__PI_IM_8__PI_IM_8 = 17;
	public static final short INST_COPY__PI_IM_8__PI_PI_8 = 18;

	public static final short INST_COPY__PI_PI_8__IM_IM_8 = 19;
	public static final short INST_COPY__PI_PI_8__IM_PI_8 = 20;
	public static final short INST_COPY__PI_PI_8__PI_IM_8 = 21;
	public static final short INST_COPY__PI_PI_8__PI_PI_8 = 22;
	
	// INST SIZE
	
	public static final int INST_SIZE = Short.BYTES;
	
	// run method
	
	public void run()
	{
		short inst;
		
		if (SAFE)
			checkBaseInstPointer();
		
		instBuffer = buffers[bip];
		
		if (SAFE)
		{
			checkInstBuffer();
			checkInstPointer();
		}
		
		while (ip < instBuffer.length + INST_SIZE)
		{
			inst = readShort();
			
			switch (inst)
			{
			case INST_NOOP:
				break;
				
			case INST_COPY_IM32_PI32:
				pointers[readPointersIndex()] = readInt(); 
				break;
				
			case INST_COPY_PI32_PI32:
				pointers[readPointersIndex()] = pointers[readPointersIndex()];
				break;
				
			// COPY IM8 MEM8
				
			case INST_COPY__IM8__IM_IM_8:
				nextDataBuffer_ImmediateIndex();
				dataBuffer[readDataBufferIndex_ImmediateIndex()] = readByte();
				break;
				
			case INST_COPY__IM8__IM_PI_8:
				nextDataBuffer_ImmediateIndex();
				dataBuffer[readDataBufferIndex_PointersIndex()] = readByte();
				break;
				
			case INST_COPY__IM8__PI_IM_8:
				nextDataBuffer_PointersIndex();
				dataBuffer[readDataBufferIndex_ImmediateIndex()] = readByte();
				break;
				
			case INST_COPY__IM8__PI_PI_8:
				nextDataBuffer_PointersIndex();
				dataBuffer[readDataBufferIndex_PointersIndex()] = readByte();
				break;
				
			// COPY MEM8 MEM8
			
			case INST_COPY__IM_IM_8__IM_IM_8:
				nextDataBuffer_ImmediateIndex();
				nextSecondDataBuffer_ImmediateIndex();
				dataBuffer[readDataBufferIndex_ImmediateIndex()] = secondDataBuffer[readSecondDataBufferIndex_ImmediateIndex()];
				break;

			case INST_COPY__IM_IM_8__IM_PI_8:
				nextDataBuffer_ImmediateIndex();
				nextSecondDataBuffer_ImmediateIndex();
				dataBuffer[readDataBufferIndex_PointersIndex()] = secondDataBuffer[readSecondDataBufferIndex_ImmediateIndex()];
				break;

			case INST_COPY__IM_IM_8__PI_IM_8:
				nextDataBuffer_PointersIndex();
				nextSecondDataBuffer_ImmediateIndex();
				dataBuffer[readDataBufferIndex_ImmediateIndex()] = secondDataBuffer[readSecondDataBufferIndex_ImmediateIndex()];
				break;

			case INST_COPY__IM_IM_8__PI_PI_8:
				nextDataBuffer_PointersIndex();
				nextSecondDataBuffer_ImmediateIndex();
				dataBuffer[readDataBufferIndex_PointersIndex()] = secondDataBuffer[readSecondDataBufferIndex_ImmediateIndex()];
				break;

			case INST_COPY__IM_PI_8__IM_IM_8:
				nextDataBuffer_ImmediateIndex();
				nextSecondDataBuffer_ImmediateIndex();
				dataBuffer[readDataBufferIndex_ImmediateIndex()] = secondDataBuffer[readSecondDataBufferIndex_PointersIndex()];
				break;

			case INST_COPY__IM_PI_8__IM_PI_8:
				nextDataBuffer_ImmediateIndex();
				nextSecondDataBuffer_ImmediateIndex();
				dataBuffer[readDataBufferIndex_PointersIndex()] = secondDataBuffer[readSecondDataBufferIndex_PointersIndex()];
				break;

			case INST_COPY__IM_PI_8__PI_IM_8:
				nextDataBuffer_PointersIndex();
				nextSecondDataBuffer_ImmediateIndex();
				dataBuffer[readDataBufferIndex_ImmediateIndex()] = secondDataBuffer[readSecondDataBufferIndex_PointersIndex()];
				break;

			case INST_COPY__IM_PI_8__PI_PI_8:
				nextDataBuffer_PointersIndex();
				nextSecondDataBuffer_ImmediateIndex();
				dataBuffer[readDataBufferIndex_PointersIndex()] = secondDataBuffer[readSecondDataBufferIndex_PointersIndex()];
				break;

			case INST_COPY__PI_IM_8__IM_IM_8:
				nextDataBuffer_ImmediateIndex();
				nextSecondDataBuffer_PointersIndex();
				dataBuffer[readDataBufferIndex_ImmediateIndex()] = secondDataBuffer[readSecondDataBufferIndex_ImmediateIndex()];
				break;

			case INST_COPY__PI_IM_8__IM_PI_8:
				nextDataBuffer_ImmediateIndex();
				nextSecondDataBuffer_PointersIndex();
				dataBuffer[readDataBufferIndex_PointersIndex()] = secondDataBuffer[readSecondDataBufferIndex_ImmediateIndex()];
				break;

			case INST_COPY__PI_IM_8__PI_IM_8:
				nextDataBuffer_PointersIndex();
				nextSecondDataBuffer_PointersIndex();
				dataBuffer[readDataBufferIndex_ImmediateIndex()] = secondDataBuffer[readSecondDataBufferIndex_ImmediateIndex()];
				break;

			case INST_COPY__PI_IM_8__PI_PI_8:
				nextDataBuffer_PointersIndex();
				nextSecondDataBuffer_PointersIndex();
				dataBuffer[readDataBufferIndex_PointersIndex()] = secondDataBuffer[readSecondDataBufferIndex_ImmediateIndex()];
				break;

			case INST_COPY__PI_PI_8__IM_IM_8:
				nextDataBuffer_ImmediateIndex();
				nextSecondDataBuffer_PointersIndex();
				dataBuffer[readDataBufferIndex_ImmediateIndex()] = secondDataBuffer[readSecondDataBufferIndex_PointersIndex()];
				break;

			case INST_COPY__PI_PI_8__IM_PI_8:
				nextDataBuffer_ImmediateIndex();
				nextSecondDataBuffer_PointersIndex();
				dataBuffer[readDataBufferIndex_PointersIndex()] = secondDataBuffer[readSecondDataBufferIndex_PointersIndex()];
				break;

			case INST_COPY__PI_PI_8__PI_IM_8:
				nextDataBuffer_PointersIndex();
				nextSecondDataBuffer_PointersIndex();
				dataBuffer[readDataBufferIndex_ImmediateIndex()] = secondDataBuffer[readSecondDataBufferIndex_PointersIndex()];
				break;

			case INST_COPY__PI_PI_8__PI_PI_8:
				nextDataBuffer_PointersIndex();
				nextSecondDataBuffer_PointersIndex();
				dataBuffer[readDataBufferIndex_PointersIndex()] = secondDataBuffer[readSecondDataBufferIndex_PointersIndex()];
				break;
				
			// SWAP MEM8 MEM8
				
			
				
			}
		}
		
		throw new RuntimeError(ErrorType.END_OF_INST_BUFFER, String.valueOf(ip));
	}
}
