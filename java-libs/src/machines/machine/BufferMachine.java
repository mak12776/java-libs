
package machines.machine;

public class BufferMachine
{
	private static final boolean SAFE = false;

	private byte[][] buffers;
	private int[] pointers;

	private int bip;
	private int ip;

	public BufferMachine(byte[][] buffers, int[] pointers, int baseInstPointer, int instPointer)
	{
		this.buffers = buffers;
		this.pointers = pointers;

		this.bip = baseInstPointer;
		this.ip = instPointer;
	}

	private byte[] instBuffer;
	private byte[] dataBuffer;

	private byte nextByte()
	{
		return instBuffer[ip++];
	}

	private int nextInt()
	{
		return 	((instBuffer[ip++] & 0xff) << 24) | 
				((instBuffer[ip++] & 0xff) << 16) | 
				((instBuffer[ip++] & 0xff) << 8) | 
				(instBuffer[ip++] & 0xff);
	}

	private void checkPointersIndex(final int index)
	{
		if ((index < 0) || (index >= pointers.length))
			throw new RuntimeError(ErrorType.INVALID_POINTERS_INDEX);
	}
	
	private void checkBuffersIndex(final int index)
	{
		if ((index < 0) || (index >= buffers.length))
			throw new RuntimeError(ErrorType.INVALID_BUFFERS_INDEX);
	}
	
	private void checkDataBuffersIndex(final int index)
	{
		if ((index < 0) || (index >= dataBuffer.length))
			throw new RuntimeError(ErrorType.INVALID_DATA_BUFFER_INDEX);
	}
	
	private void checkNullDataBuffer()
	{
		if (dataBuffer == null)
			throw new RuntimeError(ErrorType.NULL_DATA_POINTER);
	}
	
	private int nextPointersIndex()
	{
		int index = nextInt();
		if (SAFE)
			checkPointersIndex(index);
		return index;
	}

	private int nextBuffersIndex()
	{
		int index = nextInt();
		if (SAFE)
			checkBuffersIndex(index);
		return index;
	}
	
	private void setDataBufferFromBuffersIndex()
	{
		int index;
		index = nextBuffersIndex();
		dataBuffer = buffers[index];
		if (SAFE)
			checkNullDataBuffer();
	}
	
	private int nextBuffersIndexAtPointersIndex()
	{
		int index = nextInt();
		if (SAFE)
			checkPointersIndex(index);
		index = pointers[index];
		if (SAFE)
			checkBuffersIndex(index);
		return index;
	}
	
	private void setDataBufferFromBuffersIndexAtPointersIndex()
	{
		int index;
		index = nextBuffersIndexAtPointersIndex();
		dataBuffer = buffers[index];
		if (SAFE)
			checkNullDataBuffer();
	}
	
	private int nextDataBufferIndex()
	{
		int index = nextInt();
		if (SAFE)
			checkDataBuffersIndex(index);
		return index;
	}
	
	private int nextDataBufferIndexAtPointersIndex()
	{
		int index = nextInt();
		if (SAFE)
			checkPointersIndex(index);
		index = pointers[index];
		if (SAFE)
			checkDataBuffersIndex(index);
		return index;
	}

	// instructions

	/*
	 * symbol 		size 			description 
	 * IM8/32 		8/32	 		immediate 
	 * PI 			32 				pointers index
	 * BI			32				buffers index
	 * 
	 * PI:PI		32 + 32			address at:		buffers [pointers[PI]] [pointers[PI]]
	 * BI:BI		32 + 32			address at:		buffers [buffer index] [buffer index]
	 * 
	 * PI:BI		32 + 32			address at:		...
	 * BI:PI		32 + 32			address at:		...
	 */

	public static final byte INST_NOOP = 						0x00;

	private static final byte BASE1 = 							INST_NOOP;

	public static final byte INST_COPY_IM32_PI = 				BASE1 + 1;
	public static final byte INST_COPY_PI_PI = 					BASE1 + 2;

	private static final byte BASE2 = 							INST_COPY_PI_PI;

	public static final byte INST_COPY__IM8__PI_PI = 			BASE2 + 1;
	public static final byte INST_COPY__IM8__BI_PI = 			BASE2 + 2;
	public static final byte INST_COPY__IM8__PI_BI = 			BASE2 + 3;
	public static final byte INST_COPY__IM8__BI_BI = 			BASE2 + 4;
	
	private static final byte BASE3 = 							INST_COPY__IM8__BI_BI;

	public static final byte INST_COPY__BI_BI__BI_BI =			BASE3 + 5;
	public static final byte INST_COPY__BI_BI__BI_PI =			BASE3 + 6;
	public static final byte INST_COPY__BI_BI__PI_BI =			BASE3 + 7;
	public static final byte INST_COPY__BI_BI__PI_PI =			BASE3 + 8;

	public static final byte INST_COPY__BI_PI__BI_BI =			BASE3 + 9;
	public static final byte INST_COPY__BI_PI__BI_PI =			BASE3 + 10;
	public static final byte INST_COPY__BI_PI__PI_BI =			BASE3 + 11;
	public static final byte INST_COPY__BI_PI__PI_PI =			BASE3 + 12;

	public static final byte INST_COPY__PI_BI__BI_BI =			BASE3 + 13;
	public static final byte INST_COPY__PI_BI__BI_PI =			BASE3 + 14;
	public static final byte INST_COPY__PI_BI__PI_BI =			BASE3 + 15;
	public static final byte INST_COPY__PI_BI__PI_PI =			BASE3 + 16;

	public static final byte INST_COPY__PI_PI__BI_BI =			BASE3 + 17;
	public static final byte INST_COPY__PI_PI__BI_PI =			BASE3 + 18;
	public static final byte INST_COPY__PI_PI__PI_BI =			BASE3 + 19;
	public static final byte INST_COPY__PI_PI__PI_PI =			BASE3 + 20;

	public void run() throws RuntimeError
	{
		short inst;
		byte[] instBuffer;

		if (bip >= buffers.length)
		{
			throw new RuntimeError(ErrorType.INVALID_BASE_INSTRUCTION_POINTER,
					String.valueOf(bip));
		}

		instBuffer = buffers[bip];

		if (instBuffer == null)
		{
			throw new RuntimeError(ErrorType.NULL_INSTRUCTION_POINTER, 
					String.valueOf(bip));
		}

		while (ip < instBuffer.length)
		{
			inst = nextByte();
			switch (inst)
			{
			case INST_NOOP:
				break;

			case INST_COPY_IM32_PI:
				pointers[nextPointersIndex()] = nextInt();
				break;

			case INST_COPY_PI_PI:
				pointers[nextPointersIndex()] = pointers[nextPointersIndex()];
				break;

			case INST_COPY__IM8__PI_PI:
				setDataBufferFromBuffersIndexAtPointersIndex();
				dataBuffer[nextDataBufferIndexAtPointersIndex()] = nextByte();
				break;
				
			case INST_COPY__IM8__BI_BI:
				setDataBufferFromBuffersIndex();
				dataBuffer[nextDataBufferIndex()] = nextByte();
			}
		}
	}
}
