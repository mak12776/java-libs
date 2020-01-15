
package machines.machine;

public class BufferMachine
{
	private static final boolean SAFE = false;

	private byte[][] buffers;
	private int[] pointers;
	private boolean test;

	private int bip;
	private int ip;

	// constructor
	
	public BufferMachine(byte[][] buffers, int[] pointers, int baseInstPointer, int instPointer)
	{
		this.buffers = buffers;
		this.pointers = pointers;

		this.bip = baseInstPointer;
		this.ip = instPointer;
	}
	
	// internal data members

	private byte[] instBuffer;
	private byte[] dataBuffer;

	// reading inst buffer
	
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
	
	// check: index

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
	
	// check: null pointer
	
	private void checkNullDataBuffer()
	{
		if (dataBuffer == null)
			throw new RuntimeError(ErrorType.NULL_DATA_POINTER);
	}
	
	private void checkNullInstructionBuffer(final int index)
	{
		if (instBuffer == null)
			throw new RuntimeError(ErrorType.NULL_INSTRUCTION_POINTER,
					String.valueOf(index));
	}
	
	// get next pointer
	
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
	
	// set data buffer pointer
	
	private void setDataBufferFromBuffersIndex()
	{
		dataBuffer = buffers[nextBuffersIndex()];
		if (SAFE)
			checkNullDataBuffer();
	}
	
	private void setDataBufferFromBuffersIndexAtPointersIndex()
	{
		dataBuffer = buffers[nextBuffersIndexAtPointersIndex()];
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
	
	// get next data buffer index
	
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
	
	// set byte at specific address
	
	private void setByteAt_PI_PI(byte value)
	{
		setDataBufferFromBuffersIndexAtPointersIndex();
		dataBuffer[nextDataBufferIndexAtPointersIndex()] = value;
	}
	
	private void setByteAt_BI_BI(byte value)
	{
		setDataBufferFromBuffersIndex();
		dataBuffer[nextDataBufferIndex()] = value;
	}
	
	private void setByteAt_BI_PI(byte value)
	{
		setDataBufferFromBuffersIndex();
		dataBuffer[nextDataBufferIndexAtPointersIndex()] = value;
	}
	
	private void setByteAt_PI_BI(byte value)
	{
		setDataBufferFromBuffersIndexAtPointersIndex();
		dataBuffer[nextDataBufferIndex()] = value;
	}
	
	// get byte at specific address
	
	private byte getByteAt_PI_PI()
	{
		setDataBufferFromBuffersIndexAtPointersIndex();
		return dataBuffer[nextDataBufferIndexAtPointersIndex()];
	}
	
	private byte getByteAt_BI_BI()
	{
		setDataBufferFromBuffersIndex();
		return dataBuffer[nextDataBufferIndex()];
	}
	
	private byte getByteAt_BI_PI()
	{
		setDataBufferFromBuffersIndex();
		return dataBuffer[nextDataBufferIndexAtPointersIndex()];
	}
	
	private byte getByteAt_PI_BI()
	{
		setDataBufferFromBuffersIndexAtPointersIndex();
		return dataBuffer[nextDataBufferIndex()];
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
	 * 
	 * MI			32 + 32			memory index: 	PI:PI BI:BI BI:PI PI:BI
	 */

	public static final byte INST_NOOP = 							0x00;

	private static final byte BASE1 = 								INST_NOOP;

	public static final byte INST_COPY_IM32_PI = 					BASE1 + 1;
	public static final byte INST_COPY_PI_PI = 						BASE1 + 2;

	private static final byte BASE2 = 								INST_COPY_PI_PI;
	
	//# for (i = 0; i < 100; i += 1)
	//# 	file.write("	public static final byte INST_COPY__IM8__PI_PI_8 = 				BASE2 + 1;");
	
	//# def write_inst(name, value):
	//# 	file.write(f"	public static final byte {name} = 				{value};");

	public static final byte INST_COPY__IM8__PI_PI_8 = 				BASE2 + 1;
	public static final byte INST_COPY__IM8__BI_PI_8 = 				BASE2 + 2;
	public static final byte INST_COPY__IM8__PI_BI_8 = 				BASE2 + 3;
	public static final byte INST_COPY__IM8__BI_BI_8 = 				BASE2 + 4;
	
	private static final byte BASE3 = 								INST_COPY__IM8__BI_BI_8;

	public static final byte INST_COPY__BI_BI_8__BI_BI_8 =			BASE3 + 1;
	public static final byte INST_COPY__BI_BI_8__BI_PI_8 =			BASE3 + 2;
	public static final byte INST_COPY__BI_BI_8__PI_BI_8 =			BASE3 + 3;
	public static final byte INST_COPY__BI_BI_8__PI_PI_8 =			BASE3 + 4;

	public static final byte INST_COPY__BI_PI_8__BI_BI_8 =			BASE3 + 5;
	public static final byte INST_COPY__BI_PI_8__BI_PI_8 =			BASE3 + 6;
	public static final byte INST_COPY__BI_PI_8__PI_BI_8 =			BASE3 + 7;
	public static final byte INST_COPY__BI_PI_8__PI_PI_8 =			BASE3 + 8;

	public static final byte INST_COPY__PI_BI_8__BI_BI_8 =			BASE3 + 9;
	public static final byte INST_COPY__PI_BI_8__BI_PI_8 =			BASE3 + 10;
	public static final byte INST_COPY__PI_BI_8__PI_BI_8 =			BASE3 + 11;
	public static final byte INST_COPY__PI_BI_8__PI_PI_8 =			BASE3 + 12;

	public static final byte INST_COPY__PI_PI_8__BI_BI_8 =			BASE3 + 13;
	public static final byte INST_COPY__PI_PI_8__BI_PI_8 =			BASE3 + 14;
	public static final byte INST_COPY__PI_PI_8__PI_BI_8 =			BASE3 + 15;
	public static final byte INST_COPY__PI_PI_8__PI_PI_8 =			BASE3 + 16;
	
	private static final byte BASE4 =								INST_COPY__PI_PI_8__PI_PI_8;
	
	//# for opr1 in operands
		//# for opr2 in operands
		//# write('public static final byte {} = {};')
	public static final byte INST_TEST__IM8__EQ__BI_BI_8 =			BASE4 + 1;
		//# end for
	//# end for
	
	private static final byte BASE5 =								INST_TEST__IM8__EQ__BI_BI_8;

	public static final byte INST_TEST__BI_BI_8__EQ__BI_BI_8 =		BASE5 + 1;
	public static final byte INST_TEST__BI_BI_8__EQ__BI_PI_8 =		BASE5 + 2;
	public static final byte INST_TEST__BI_BI_8__EQ__PI_BI_8 =		BASE5 + 3;
	public static final byte INST_TEST__BI_BI_8__EQ__PI_PI_8 =		BASE5 + 4;
	public static final byte INST_TEST__BI_PI_8__EQ__BI_BI_8 =		BASE5 + 5;
	public static final byte INST_TEST__BI_PI_8__EQ__BI_PI_8 =		BASE5 + 6;
	public static final byte INST_TEST__BI_PI_8__EQ__PI_BI_8 =		BASE5 + 7;
	public static final byte INST_TEST__BI_PI_8__EQ__PI_PI_8 =		BASE5 + 8;
	public static final byte INST_TEST__PI_BI_8__EQ__BI_BI_8 =		BASE5 + 9;
	public static final byte INST_TEST__PI_BI_8__EQ__BI_PI_8 =		BASE5 + 10;
	public static final byte INST_TEST__PI_BI_8__EQ__PI_BI_8 =		BASE5 + 11;
	public static final byte INST_TEST__PI_BI_8__EQ__PI_PI_8 =		BASE5 + 12;
	public static final byte INST_TEST__PI_PI_8__EQ__BI_BI_8 =		BASE5 + 13;
	public static final byte INST_TEST__PI_PI_8__EQ__BI_PI_8 =		BASE5 + 14;
	public static final byte INST_TEST__PI_PI_8__EQ__PI_BI_8 =		BASE5 + 15;
	public static final byte INST_TEST__PI_PI_8__EQ__PI_PI_8 =		BASE5 + 16;
	
	public static final byte INST_TEST__BI_BI_8__NE__BI_BI_8 =		BASE5 + 17;
	public static final byte INST_TEST__BI_BI_8__NE__BI_PI_8 =		BASE5 + 18;
	public static final byte INST_TEST__BI_BI_8__NE__PI_BI_8 =		BASE5 + 19;
	public static final byte INST_TEST__BI_BI_8__NE__PI_PI_8 =		BASE5 + 20;
	public static final byte INST_TEST__BI_PI_8__NE__BI_BI_8 =		BASE5 + 21;
	public static final byte INST_TEST__BI_PI_8__NE__BI_PI_8 =		BASE5 + 22;
	public static final byte INST_TEST__BI_PI_8__NE__PI_BI_8 =		BASE5 + 23;
	public static final byte INST_TEST__BI_PI_8__NE__PI_PI_8 =		BASE5 + 24;
	public static final byte INST_TEST__PI_BI_8__NE__BI_BI_8 =		BASE5 + 25;
	public static final byte INST_TEST__PI_BI_8__NE__BI_PI_8 =		BASE5 + 26;
	public static final byte INST_TEST__PI_BI_8__NE__PI_BI_8 =		BASE5 + 27;
	public static final byte INST_TEST__PI_BI_8__NE__PI_PI_8 =		BASE5 + 28;
	public static final byte INST_TEST__PI_PI_8__NE__BI_BI_8 =		BASE5 + 29;
	public static final byte INST_TEST__PI_PI_8__NE__BI_PI_8 =		BASE5 + 30;
	public static final byte INST_TEST__PI_PI_8__NE__PI_BI_8 =		BASE5 + 31;
	public static final byte INST_TEST__PI_PI_8__NE__PI_PI_8 =		BASE5 + 32;
	
	public static final byte INST_TEST__BI_BI_8__GT__BI_BI_8 =		BASE5 + 33;
	public static final byte INST_TEST__BI_BI_8__GT__BI_PI_8 =		BASE5 + 34;
	public static final byte INST_TEST__BI_BI_8__GT__PI_BI_8 =		BASE5 + 35;
	public static final byte INST_TEST__BI_BI_8__GT__PI_PI_8 =		BASE5 + 36;
	public static final byte INST_TEST__BI_PI_8__GT__BI_BI_8 =		BASE5 + 37;
	public static final byte INST_TEST__BI_PI_8__GT__BI_PI_8 =		BASE5 + 38;
	public static final byte INST_TEST__BI_PI_8__GT__PI_BI_8 =		BASE5 + 39;
	public static final byte INST_TEST__BI_PI_8__GT__PI_PI_8 =		BASE5 + 40;
	public static final byte INST_TEST__PI_BI_8__GT__BI_BI_8 =		BASE5 + 41;
	public static final byte INST_TEST__PI_BI_8__GT__BI_PI_8 =		BASE5 + 42;
	public static final byte INST_TEST__PI_BI_8__GT__PI_BI_8 =		BASE5 + 43;
	public static final byte INST_TEST__PI_BI_8__GT__PI_PI_8 =		BASE5 + 44;
	public static final byte INST_TEST__PI_PI_8__GT__BI_BI_8 =		BASE5 + 45;
	public static final byte INST_TEST__PI_PI_8__GT__BI_PI_8 =		BASE5 + 46;
	public static final byte INST_TEST__PI_PI_8__GT__PI_BI_8 =		BASE5 + 47;
	public static final byte INST_TEST__PI_PI_8__GT__PI_PI_8 =		BASE5 + 48;
	
	public static final byte INST_TEST__BI_BI_8__GE__BI_BI_8 =		BASE5 + 49;
	public static final byte INST_TEST__BI_BI_8__GE__BI_PI_8 =		BASE5 + 50;
	public static final byte INST_TEST__BI_BI_8__GE__PI_BI_8 =		BASE5 + 51;
	public static final byte INST_TEST__BI_BI_8__GE__PI_PI_8 =		BASE5 + 52;
	public static final byte INST_TEST__BI_PI_8__GE__BI_BI_8 =		BASE5 + 53;
	public static final byte INST_TEST__BI_PI_8__GE__BI_PI_8 =		BASE5 + 54;
	public static final byte INST_TEST__BI_PI_8__GE__PI_BI_8 =		BASE5 + 55;
	public static final byte INST_TEST__BI_PI_8__GE__PI_PI_8 =		BASE5 + 56;
	public static final byte INST_TEST__PI_BI_8__GE__BI_BI_8 =		BASE5 + 57;
	public static final byte INST_TEST__PI_BI_8__GE__BI_PI_8 =		BASE5 + 58;
	public static final byte INST_TEST__PI_BI_8__GE__PI_BI_8 =		BASE5 + 59;
	public static final byte INST_TEST__PI_BI_8__GE__PI_PI_8 =		BASE5 + 60;
	public static final byte INST_TEST__PI_PI_8__GE__BI_BI_8 =		BASE5 + 61;
	public static final byte INST_TEST__PI_PI_8__GE__BI_PI_8 =		BASE5 + 62;
	public static final byte INST_TEST__PI_PI_8__GE__PI_BI_8 =		BASE5 + 63;
	public static final byte INST_TEST__PI_PI_8__GE__PI_PI_8 =		BASE5 + 64;
	
	public static final byte INST_TEST__BI_BI_8__LT__BI_BI_8 =		BASE5 + 65;
	public static final byte INST_TEST__BI_BI_8__LT__BI_PI_8 =		BASE5 + 66;
	public static final byte INST_TEST__BI_BI_8__LT__PI_BI_8 =		BASE5 + 67;
	public static final byte INST_TEST__BI_BI_8__LT__PI_PI_8 =		BASE5 + 68;
	public static final byte INST_TEST__BI_PI_8__LT__BI_BI_8 =		BASE5 + 69;
	public static final byte INST_TEST__BI_PI_8__LT__BI_PI_8 =		BASE5 + 70;
	public static final byte INST_TEST__BI_PI_8__LT__PI_BI_8 =		BASE5 + 71;
	public static final byte INST_TEST__BI_PI_8__LT__PI_PI_8 =		BASE5 + 72;
	public static final byte INST_TEST__PI_BI_8__LT__BI_BI_8 =		BASE5 + 73;
	public static final byte INST_TEST__PI_BI_8__LT__BI_PI_8 =		BASE5 + 74;
	public static final byte INST_TEST__PI_BI_8__LT__PI_BI_8 =		BASE5 + 75;
	public static final byte INST_TEST__PI_BI_8__LT__PI_PI_8 =		BASE5 + 76;
	public static final byte INST_TEST__PI_PI_8__LT__BI_BI_8 =		BASE5 + 77;
	public static final byte INST_TEST__PI_PI_8__LT__BI_PI_8 =		BASE5 + 78;
	public static final byte INST_TEST__PI_PI_8__LT__PI_BI_8 =		BASE5 + 79;
	public static final byte INST_TEST__PI_PI_8__LT__PI_PI_8 =		BASE5 + 80;
	
	public static final byte INST_TEST__BI_BI_8__LE__BI_BI_8 =		BASE5 + 81;
	public static final byte INST_TEST__BI_BI_8__LE__BI_PI_8 =		BASE5 + 82;
	public static final byte INST_TEST__BI_BI_8__LE__PI_BI_8 =		BASE5 + 83;
	public static final byte INST_TEST__BI_BI_8__LE__PI_PI_8 =		BASE5 + 84;
	public static final byte INST_TEST__BI_PI_8__LE__BI_BI_8 =		BASE5 + 85;
	public static final byte INST_TEST__BI_PI_8__LE__BI_PI_8 =		BASE5 + 86;
	public static final byte INST_TEST__BI_PI_8__LE__PI_BI_8 =		BASE5 + 87;
	public static final byte INST_TEST__BI_PI_8__LE__PI_PI_8 =		BASE5 + 88;
	public static final byte INST_TEST__PI_BI_8__LE__BI_BI_8 =		BASE5 + 89;
	public static final byte INST_TEST__PI_BI_8__LE__BI_PI_8 =		BASE5 + 90;
	public static final byte INST_TEST__PI_BI_8__LE__PI_BI_8 =		BASE5 + 91;
	public static final byte INST_TEST__PI_BI_8__LE__PI_PI_8 =		BASE5 + 92;
	public static final byte INST_TEST__PI_PI_8__LE__BI_BI_8 =		BASE5 + 93;
	public static final byte INST_TEST__PI_PI_8__LE__BI_PI_8 =		BASE5 + 94;
	public static final byte INST_TEST__PI_PI_8__LE__PI_BI_8 =		BASE5 + 95;
	public static final byte INST_TEST__PI_PI_8__LE__PI_PI_8 =		BASE5 + 96;
	
	private static final byte BASE6 =								INST_TEST__PI_PI_8__LE__PI_PI_8;
	
	public static final byte INST_TEST__AND_BI_BI_8__EQ__BI_BI_8 =	BASE5 + 1;

	public void run() throws RuntimeError
	{
		short inst;
		byte[] instBuffer;

		if (SAFE)
		{
			if (bip >= buffers.length)
			{
				throw new RuntimeError(ErrorType.INVALID_BASE_INSTRUCTION_POINTER,
						String.valueOf(bip));
			}
		}
		
		instBuffer = buffers[bip];

		if (SAFE)
			checkNullInstructionBuffer(bip);

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
				
			// copy IM8, MI 8

			case INST_COPY__IM8__PI_PI_8:
				setByteAt_PI_PI(nextByte());
				break;
				
			case INST_COPY__IM8__BI_BI_8:
				setByteAt_BI_BI(nextByte());
				break;
				
			case INST_COPY__IM8__BI_PI_8:
				setByteAt_BI_PI(nextByte());
				break;
				
			case INST_COPY__IM8__PI_BI_8:
				setByteAt_PI_BI(nextByte());
				break;
				
			// copy MI 8, MI 8
				
			case INST_COPY__BI_BI_8__BI_BI_8:
				setByteAt_BI_BI(getByteAt_BI_BI());
				break;

			case INST_COPY__BI_BI_8__BI_PI_8:
				setByteAt_BI_PI(getByteAt_BI_BI());
				break;

			case INST_COPY__BI_BI_8__PI_BI_8:
				setByteAt_PI_BI(getByteAt_BI_BI());
				break;

			case INST_COPY__BI_BI_8__PI_PI_8:
				setByteAt_PI_PI(getByteAt_BI_BI());
				break;
				
			// -

			case INST_COPY__BI_PI_8__BI_BI_8:
				setByteAt_BI_BI(getByteAt_BI_PI());
				break;

			case INST_COPY__BI_PI_8__BI_PI_8:
				setByteAt_BI_PI(getByteAt_BI_PI());
				break;

			case INST_COPY__BI_PI_8__PI_BI_8:
				setByteAt_PI_BI(getByteAt_BI_PI());
				break;

			case INST_COPY__BI_PI_8__PI_PI_8:
				setByteAt_PI_PI(getByteAt_BI_PI());
				break;
				
			// -

			case INST_COPY__PI_BI_8__BI_BI_8:
				setByteAt_BI_BI(getByteAt_PI_BI());
				break;

			case INST_COPY__PI_BI_8__BI_PI_8:
				setByteAt_BI_PI(getByteAt_PI_BI());
				break;

			case INST_COPY__PI_BI_8__PI_BI_8:
				setByteAt_PI_BI(getByteAt_PI_BI());
				break;

			case INST_COPY__PI_BI_8__PI_PI_8:
				setByteAt_PI_PI(getByteAt_PI_BI());
				break;
				
			// -

			case INST_COPY__PI_PI_8__BI_BI_8:
				setByteAt_BI_BI(getByteAt_PI_PI());
				break;

			case INST_COPY__PI_PI_8__BI_PI_8:
				setByteAt_BI_PI(getByteAt_PI_PI());
				break;

			case INST_COPY__PI_PI_8__PI_BI_8:
				setByteAt_PI_BI(getByteAt_PI_PI());
				break;

			case INST_COPY__PI_PI_8__PI_PI_8:
				setByteAt_PI_PI(getByteAt_PI_PI());
				break;
			}
		}
	}
}
