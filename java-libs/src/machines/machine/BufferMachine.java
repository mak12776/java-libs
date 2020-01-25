
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

	private short nextShort()
	{
		return (short) (
				((instBuffer[ip++] & 0xFF) << 8) | 
				(instBuffer[ip++] & 0xFF)
			);
	}
	
	private int nextInt()
	{
		return 	((instBuffer[ip++] & 0xff) << 24) | 
				((instBuffer[ip++] & 0xff) << 16) | 
				((instBuffer[ip++] & 0xff) << 8) | 
				(instBuffer[ip++] & 0xff);
	}
	
	// check: index
	
	private void checkBaseInstructionPointer()
	{
		if ((bip < 0) || (bip >= buffers.length))
			throw new RuntimeError(
					ErrorType.INVALID_BASE_INSTRUCTION_POINTER,
					String.valueOf(bip));
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
	
	private void checkDataBufferIndex(final int index)
	{
		if ((index < 0) || (index >= dataBuffer.length))
			throw new RuntimeError(ErrorType.INVALID_DATA_BUFFER_INDEX);
	}
	
	// check: null pointer
	
	private void checkNullInstructionBuffer(final int index)
	{
		if (instBuffer == null)
			throw new RuntimeError(ErrorType.NULL_INSTRUCTION_POINTER,
					String.valueOf(index));
	}
	
	private void checkNullDataBuffer()
	{
		if (dataBuffer == null)
			throw new RuntimeError(ErrorType.NULL_DATA_POINTER);
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

	// get next data buffer index
	
	private int nextDataBufferIndex()
	{
		int index = nextInt();
		if (SAFE)
			checkDataBufferIndex(index);
		return index;
	}
	
	private int nextDataBufferIndexAtPointersIndex()
	{
		int index = nextInt();
		if (SAFE)
			checkPointersIndex(index);
		index = pointers[index];
		if (SAFE)
			checkDataBufferIndex(index);
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
	
	// NOOP

	public static final short INST_NOOP =								0x0;

	// COPY IM32, PI
	// COPY PI, PI

	public static final short INST_COPY_IM32_PI32 =						0x1;
	public static final short INST_COPY_PI32_PI32 =						0x2;

	// COPY IM8, MI8

	public static final short INST_COPY__IM8__BI_BI_8 =					0x3;
	public static final short INST_COPY__IM8__BI_PI_8 =					0x4;
	public static final short INST_COPY__IM8__PI_BI_8 =					0x5;
	public static final short INST_COPY__IM8__PI_PI_8 =					0x6;

	// COPY MI8, MI8

	public static final short INST_COPY__BI_BI_8__BI_BI_8 =				0x7;
	public static final short INST_COPY__BI_BI_8__BI_PI_8 =				0x8;
	public static final short INST_COPY__BI_BI_8__PI_BI_8 =				0x9;
	public static final short INST_COPY__BI_BI_8__PI_PI_8 =				0xa;

	public static final short INST_COPY__BI_PI_8__BI_BI_8 =				0xb;
	public static final short INST_COPY__BI_PI_8__BI_PI_8 =				0xc;
	public static final short INST_COPY__BI_PI_8__PI_BI_8 =				0xd;
	public static final short INST_COPY__BI_PI_8__PI_PI_8 =				0xe;

	public static final short INST_COPY__PI_BI_8__BI_BI_8 =				0xf;
	public static final short INST_COPY__PI_BI_8__BI_PI_8 =				0x10;
	public static final short INST_COPY__PI_BI_8__PI_BI_8 =				0x11;
	public static final short INST_COPY__PI_BI_8__PI_PI_8 =				0x12;

	public static final short INST_COPY__PI_PI_8__BI_BI_8 =				0x13;
	public static final short INST_COPY__PI_PI_8__BI_PI_8 =				0x14;
	public static final short INST_COPY__PI_PI_8__PI_BI_8 =				0x15;
	public static final short INST_COPY__PI_PI_8__PI_PI_8 =				0x16;

	// SWAP MI8, MI8

	public static final short INST_SWAP__BI_BI_8__BI_BI_8 =				0x17;
	public static final short INST_SWAP__BI_BI_8__BI_PI_8 =				0x18;
	public static final short INST_SWAP__BI_BI_8__PI_BI_8 =				0x19;
	public static final short INST_SWAP__BI_BI_8__PI_PI_8 =				0x1a;

	public static final short INST_SWAP__BI_PI_8__BI_BI_8 =				0x1b;
	public static final short INST_SWAP__BI_PI_8__BI_PI_8 =				0x1c;
	public static final short INST_SWAP__BI_PI_8__PI_BI_8 =				0x1d;
	public static final short INST_SWAP__BI_PI_8__PI_PI_8 =				0x1e;

	public static final short INST_SWAP__PI_BI_8__BI_BI_8 =				0x1f;
	public static final short INST_SWAP__PI_BI_8__BI_PI_8 =				0x20;
	public static final short INST_SWAP__PI_BI_8__PI_BI_8 =				0x21;
	public static final short INST_SWAP__PI_BI_8__PI_PI_8 =				0x22;

	public static final short INST_SWAP__PI_PI_8__BI_BI_8 =				0x23;
	public static final short INST_SWAP__PI_PI_8__BI_PI_8 =				0x24;
	public static final short INST_SWAP__PI_PI_8__PI_BI_8 =				0x25;
	public static final short INST_SWAP__PI_PI_8__PI_PI_8 =				0x26;

	// TEST IM8, MI8

	public static final short INST_TEST__IM8__EQ__BI_BI_8 =				0x27;
	public static final short INST_TEST__IM8__EQ__BI_PI_8 =				0x28;
	public static final short INST_TEST__IM8__EQ__PI_BI_8 =				0x29;
	public static final short INST_TEST__IM8__EQ__PI_PI_8 =				0x2a;

	public static final short INST_TEST__IM8__NE__BI_BI_8 =				0x2b;
	public static final short INST_TEST__IM8__NE__BI_PI_8 =				0x2c;
	public static final short INST_TEST__IM8__NE__PI_BI_8 =				0x2d;
	public static final short INST_TEST__IM8__NE__PI_PI_8 =				0x2e;

	public static final short INST_TEST__IM8__LT__BI_BI_8 =				0x2f;
	public static final short INST_TEST__IM8__LT__BI_PI_8 =				0x30;
	public static final short INST_TEST__IM8__LT__PI_BI_8 =				0x31;
	public static final short INST_TEST__IM8__LT__PI_PI_8 =				0x32;

	public static final short INST_TEST__IM8__LE__BI_BI_8 =				0x33;
	public static final short INST_TEST__IM8__LE__BI_PI_8 =				0x34;
	public static final short INST_TEST__IM8__LE__PI_BI_8 =				0x35;
	public static final short INST_TEST__IM8__LE__PI_PI_8 =				0x36;

	public static final short INST_TEST__IM8__GT__BI_BI_8 =				0x37;
	public static final short INST_TEST__IM8__GT__BI_PI_8 =				0x38;
	public static final short INST_TEST__IM8__GT__PI_BI_8 =				0x39;
	public static final short INST_TEST__IM8__GT__PI_PI_8 =				0x3a;

	public static final short INST_TEST__IM8__GE__BI_BI_8 =				0x3b;
	public static final short INST_TEST__IM8__GE__BI_PI_8 =				0x3c;
	public static final short INST_TEST__IM8__GE__PI_BI_8 =				0x3d;
	public static final short INST_TEST__IM8__GE__PI_PI_8 =				0x3e;

	public static final short INST_TEST__AND__IM8__EQ__BI_BI_8 =		0x3f;
	public static final short INST_TEST__AND__IM8__EQ__BI_PI_8 =		0x40;
	public static final short INST_TEST__AND__IM8__EQ__PI_BI_8 =		0x41;
	public static final short INST_TEST__AND__IM8__EQ__PI_PI_8 =		0x42;

	public static final short INST_TEST__AND__IM8__NE__BI_BI_8 =		0x43;
	public static final short INST_TEST__AND__IM8__NE__BI_PI_8 =		0x44;
	public static final short INST_TEST__AND__IM8__NE__PI_BI_8 =		0x45;
	public static final short INST_TEST__AND__IM8__NE__PI_PI_8 =		0x46;

	public static final short INST_TEST__AND__IM8__LT__BI_BI_8 =		0x47;
	public static final short INST_TEST__AND__IM8__LT__BI_PI_8 =		0x48;
	public static final short INST_TEST__AND__IM8__LT__PI_BI_8 =		0x49;
	public static final short INST_TEST__AND__IM8__LT__PI_PI_8 =		0x4a;

	public static final short INST_TEST__AND__IM8__LE__BI_BI_8 =		0x4b;
	public static final short INST_TEST__AND__IM8__LE__BI_PI_8 =		0x4c;
	public static final short INST_TEST__AND__IM8__LE__PI_BI_8 =		0x4d;
	public static final short INST_TEST__AND__IM8__LE__PI_PI_8 =		0x4e;

	public static final short INST_TEST__AND__IM8__GT__BI_BI_8 =		0x4f;
	public static final short INST_TEST__AND__IM8__GT__BI_PI_8 =		0x50;
	public static final short INST_TEST__AND__IM8__GT__PI_BI_8 =		0x51;
	public static final short INST_TEST__AND__IM8__GT__PI_PI_8 =		0x52;

	public static final short INST_TEST__AND__IM8__GE__BI_BI_8 =		0x53;
	public static final short INST_TEST__AND__IM8__GE__BI_PI_8 =		0x54;
	public static final short INST_TEST__AND__IM8__GE__PI_BI_8 =		0x55;
	public static final short INST_TEST__AND__IM8__GE__PI_PI_8 =		0x56;

	public static final short INST_TEST__OR__IM8__EQ__BI_BI_8 =			0x57;
	public static final short INST_TEST__OR__IM8__EQ__BI_PI_8 =			0x58;
	public static final short INST_TEST__OR__IM8__EQ__PI_BI_8 =			0x59;
	public static final short INST_TEST__OR__IM8__EQ__PI_PI_8 =			0x5a;

	public static final short INST_TEST__OR__IM8__NE__BI_BI_8 =			0x5b;
	public static final short INST_TEST__OR__IM8__NE__BI_PI_8 =			0x5c;
	public static final short INST_TEST__OR__IM8__NE__PI_BI_8 =			0x5d;
	public static final short INST_TEST__OR__IM8__NE__PI_PI_8 =			0x5e;

	public static final short INST_TEST__OR__IM8__LT__BI_BI_8 =			0x5f;
	public static final short INST_TEST__OR__IM8__LT__BI_PI_8 =			0x60;
	public static final short INST_TEST__OR__IM8__LT__PI_BI_8 =			0x61;
	public static final short INST_TEST__OR__IM8__LT__PI_PI_8 =			0x62;

	public static final short INST_TEST__OR__IM8__LE__BI_BI_8 =			0x63;
	public static final short INST_TEST__OR__IM8__LE__BI_PI_8 =			0x64;
	public static final short INST_TEST__OR__IM8__LE__PI_BI_8 =			0x65;
	public static final short INST_TEST__OR__IM8__LE__PI_PI_8 =			0x66;

	public static final short INST_TEST__OR__IM8__GT__BI_BI_8 =			0x67;
	public static final short INST_TEST__OR__IM8__GT__BI_PI_8 =			0x68;
	public static final short INST_TEST__OR__IM8__GT__PI_BI_8 =			0x69;
	public static final short INST_TEST__OR__IM8__GT__PI_PI_8 =			0x6a;

	public static final short INST_TEST__OR__IM8__GE__BI_BI_8 =			0x6b;
	public static final short INST_TEST__OR__IM8__GE__BI_PI_8 =			0x6c;
	public static final short INST_TEST__OR__IM8__GE__PI_BI_8 =			0x6d;
	public static final short INST_TEST__OR__IM8__GE__PI_PI_8 =			0x6e;

	public static final short INST_TEST__XOR__IM8__EQ__BI_BI_8 =		0x6f;
	public static final short INST_TEST__XOR__IM8__EQ__BI_PI_8 =		0x70;
	public static final short INST_TEST__XOR__IM8__EQ__PI_BI_8 =		0x71;
	public static final short INST_TEST__XOR__IM8__EQ__PI_PI_8 =		0x72;

	public static final short INST_TEST__XOR__IM8__NE__BI_BI_8 =		0x73;
	public static final short INST_TEST__XOR__IM8__NE__BI_PI_8 =		0x74;
	public static final short INST_TEST__XOR__IM8__NE__PI_BI_8 =		0x75;
	public static final short INST_TEST__XOR__IM8__NE__PI_PI_8 =		0x76;

	public static final short INST_TEST__XOR__IM8__LT__BI_BI_8 =		0x77;
	public static final short INST_TEST__XOR__IM8__LT__BI_PI_8 =		0x78;
	public static final short INST_TEST__XOR__IM8__LT__PI_BI_8 =		0x79;
	public static final short INST_TEST__XOR__IM8__LT__PI_PI_8 =		0x7a;

	public static final short INST_TEST__XOR__IM8__LE__BI_BI_8 =		0x7b;
	public static final short INST_TEST__XOR__IM8__LE__BI_PI_8 =		0x7c;
	public static final short INST_TEST__XOR__IM8__LE__PI_BI_8 =		0x7d;
	public static final short INST_TEST__XOR__IM8__LE__PI_PI_8 =		0x7e;

	public static final short INST_TEST__XOR__IM8__GT__BI_BI_8 =		0x7f;
	public static final short INST_TEST__XOR__IM8__GT__BI_PI_8 =		0x80;
	public static final short INST_TEST__XOR__IM8__GT__PI_BI_8 =		0x81;
	public static final short INST_TEST__XOR__IM8__GT__PI_PI_8 =		0x82;

	public static final short INST_TEST__XOR__IM8__GE__BI_BI_8 =		0x83;
	public static final short INST_TEST__XOR__IM8__GE__BI_PI_8 =		0x84;
	public static final short INST_TEST__XOR__IM8__GE__PI_BI_8 =		0x85;
	public static final short INST_TEST__XOR__IM8__GE__PI_PI_8 =		0x86;

	// TEST MI8, MI8

	public static final short INST_TEST__BI_BI_8__EQ__BI_BI_8 =			0x87;
	public static final short INST_TEST__BI_BI_8__EQ__BI_PI_8 =			0x88;
	public static final short INST_TEST__BI_BI_8__EQ__PI_BI_8 =			0x89;
	public static final short INST_TEST__BI_BI_8__EQ__PI_PI_8 =			0x8a;
	public static final short INST_TEST__BI_PI_8__EQ__BI_BI_8 =			0x8b;
	public static final short INST_TEST__BI_PI_8__EQ__BI_PI_8 =			0x8c;
	public static final short INST_TEST__BI_PI_8__EQ__PI_BI_8 =			0x8d;
	public static final short INST_TEST__BI_PI_8__EQ__PI_PI_8 =			0x8e;

	public static final short INST_TEST__PI_BI_8__EQ__BI_BI_8 =			0x8f;
	public static final short INST_TEST__PI_BI_8__EQ__BI_PI_8 =			0x90;
	public static final short INST_TEST__PI_BI_8__EQ__PI_BI_8 =			0x91;
	public static final short INST_TEST__PI_BI_8__EQ__PI_PI_8 =			0x92;
	public static final short INST_TEST__PI_PI_8__EQ__BI_BI_8 =			0x93;
	public static final short INST_TEST__PI_PI_8__EQ__BI_PI_8 =			0x94;
	public static final short INST_TEST__PI_PI_8__EQ__PI_BI_8 =			0x95;
	public static final short INST_TEST__PI_PI_8__EQ__PI_PI_8 =			0x96;

	public static final short INST_TEST__BI_BI_8__NE__BI_BI_8 =			0x97;
	public static final short INST_TEST__BI_BI_8__NE__BI_PI_8 =			0x98;
	public static final short INST_TEST__BI_BI_8__NE__PI_BI_8 =			0x99;
	public static final short INST_TEST__BI_BI_8__NE__PI_PI_8 =			0x9a;
	public static final short INST_TEST__BI_PI_8__NE__BI_BI_8 =			0x9b;
	public static final short INST_TEST__BI_PI_8__NE__BI_PI_8 =			0x9c;
	public static final short INST_TEST__BI_PI_8__NE__PI_BI_8 =			0x9d;
	public static final short INST_TEST__BI_PI_8__NE__PI_PI_8 =			0x9e;

	public static final short INST_TEST__PI_BI_8__NE__BI_BI_8 =			0x9f;
	public static final short INST_TEST__PI_BI_8__NE__BI_PI_8 =			0xa0;
	public static final short INST_TEST__PI_BI_8__NE__PI_BI_8 =			0xa1;
	public static final short INST_TEST__PI_BI_8__NE__PI_PI_8 =			0xa2;
	public static final short INST_TEST__PI_PI_8__NE__BI_BI_8 =			0xa3;
	public static final short INST_TEST__PI_PI_8__NE__BI_PI_8 =			0xa4;
	public static final short INST_TEST__PI_PI_8__NE__PI_BI_8 =			0xa5;
	public static final short INST_TEST__PI_PI_8__NE__PI_PI_8 =			0xa6;

	public static final short INST_TEST__BI_BI_8__LT__BI_BI_8 =			0xa7;
	public static final short INST_TEST__BI_BI_8__LT__BI_PI_8 =			0xa8;
	public static final short INST_TEST__BI_BI_8__LT__PI_BI_8 =			0xa9;
	public static final short INST_TEST__BI_BI_8__LT__PI_PI_8 =			0xaa;
	public static final short INST_TEST__BI_PI_8__LT__BI_BI_8 =			0xab;
	public static final short INST_TEST__BI_PI_8__LT__BI_PI_8 =			0xac;
	public static final short INST_TEST__BI_PI_8__LT__PI_BI_8 =			0xad;
	public static final short INST_TEST__BI_PI_8__LT__PI_PI_8 =			0xae;

	public static final short INST_TEST__PI_BI_8__LT__BI_BI_8 =			0xaf;
	public static final short INST_TEST__PI_BI_8__LT__BI_PI_8 =			0xb0;
	public static final short INST_TEST__PI_BI_8__LT__PI_BI_8 =			0xb1;
	public static final short INST_TEST__PI_BI_8__LT__PI_PI_8 =			0xb2;
	public static final short INST_TEST__PI_PI_8__LT__BI_BI_8 =			0xb3;
	public static final short INST_TEST__PI_PI_8__LT__BI_PI_8 =			0xb4;
	public static final short INST_TEST__PI_PI_8__LT__PI_BI_8 =			0xb5;
	public static final short INST_TEST__PI_PI_8__LT__PI_PI_8 =			0xb6;

	public static final short INST_TEST__BI_BI_8__LE__BI_BI_8 =			0xb7;
	public static final short INST_TEST__BI_BI_8__LE__BI_PI_8 =			0xb8;
	public static final short INST_TEST__BI_BI_8__LE__PI_BI_8 =			0xb9;
	public static final short INST_TEST__BI_BI_8__LE__PI_PI_8 =			0xba;
	public static final short INST_TEST__BI_PI_8__LE__BI_BI_8 =			0xbb;
	public static final short INST_TEST__BI_PI_8__LE__BI_PI_8 =			0xbc;
	public static final short INST_TEST__BI_PI_8__LE__PI_BI_8 =			0xbd;
	public static final short INST_TEST__BI_PI_8__LE__PI_PI_8 =			0xbe;

	public static final short INST_TEST__PI_BI_8__LE__BI_BI_8 =			0xbf;
	public static final short INST_TEST__PI_BI_8__LE__BI_PI_8 =			0xc0;
	public static final short INST_TEST__PI_BI_8__LE__PI_BI_8 =			0xc1;
	public static final short INST_TEST__PI_BI_8__LE__PI_PI_8 =			0xc2;
	public static final short INST_TEST__PI_PI_8__LE__BI_BI_8 =			0xc3;
	public static final short INST_TEST__PI_PI_8__LE__BI_PI_8 =			0xc4;
	public static final short INST_TEST__PI_PI_8__LE__PI_BI_8 =			0xc5;
	public static final short INST_TEST__PI_PI_8__LE__PI_PI_8 =			0xc6;

	public static final short INST_TEST__BI_BI_8__GT__BI_BI_8 =			0xc7;
	public static final short INST_TEST__BI_BI_8__GT__BI_PI_8 =			0xc8;
	public static final short INST_TEST__BI_BI_8__GT__PI_BI_8 =			0xc9;
	public static final short INST_TEST__BI_BI_8__GT__PI_PI_8 =			0xca;
	public static final short INST_TEST__BI_PI_8__GT__BI_BI_8 =			0xcb;
	public static final short INST_TEST__BI_PI_8__GT__BI_PI_8 =			0xcc;
	public static final short INST_TEST__BI_PI_8__GT__PI_BI_8 =			0xcd;
	public static final short INST_TEST__BI_PI_8__GT__PI_PI_8 =			0xce;

	public static final short INST_TEST__PI_BI_8__GT__BI_BI_8 =			0xcf;
	public static final short INST_TEST__PI_BI_8__GT__BI_PI_8 =			0xd0;
	public static final short INST_TEST__PI_BI_8__GT__PI_BI_8 =			0xd1;
	public static final short INST_TEST__PI_BI_8__GT__PI_PI_8 =			0xd2;
	public static final short INST_TEST__PI_PI_8__GT__BI_BI_8 =			0xd3;
	public static final short INST_TEST__PI_PI_8__GT__BI_PI_8 =			0xd4;
	public static final short INST_TEST__PI_PI_8__GT__PI_BI_8 =			0xd5;
	public static final short INST_TEST__PI_PI_8__GT__PI_PI_8 =			0xd6;

	public static final short INST_TEST__BI_BI_8__GE__BI_BI_8 =			0xd7;
	public static final short INST_TEST__BI_BI_8__GE__BI_PI_8 =			0xd8;
	public static final short INST_TEST__BI_BI_8__GE__PI_BI_8 =			0xd9;
	public static final short INST_TEST__BI_BI_8__GE__PI_PI_8 =			0xda;
	public static final short INST_TEST__BI_PI_8__GE__BI_BI_8 =			0xdb;
	public static final short INST_TEST__BI_PI_8__GE__BI_PI_8 =			0xdc;
	public static final short INST_TEST__BI_PI_8__GE__PI_BI_8 =			0xdd;
	public static final short INST_TEST__BI_PI_8__GE__PI_PI_8 =			0xde;

	public static final short INST_TEST__PI_BI_8__GE__BI_BI_8 =			0xdf;
	public static final short INST_TEST__PI_BI_8__GE__BI_PI_8 =			0xe0;
	public static final short INST_TEST__PI_BI_8__GE__PI_BI_8 =			0xe1;
	public static final short INST_TEST__PI_BI_8__GE__PI_PI_8 =			0xe2;
	public static final short INST_TEST__PI_PI_8__GE__BI_BI_8 =			0xe3;
	public static final short INST_TEST__PI_PI_8__GE__BI_PI_8 =			0xe4;
	public static final short INST_TEST__PI_PI_8__GE__PI_BI_8 =			0xe5;
	public static final short INST_TEST__PI_PI_8__GE__PI_PI_8 =			0xe6;

	public static final short INST_TEST__AND__BI_BI_8__EQ__BI_BI_8 =	0xe7;
	public static final short INST_TEST__AND__BI_BI_8__EQ__BI_PI_8 =	0xe8;
	public static final short INST_TEST__AND__BI_BI_8__EQ__PI_BI_8 =	0xe9;
	public static final short INST_TEST__AND__BI_BI_8__EQ__PI_PI_8 =	0xea;
	public static final short INST_TEST__AND__BI_PI_8__EQ__BI_BI_8 =	0xeb;
	public static final short INST_TEST__AND__BI_PI_8__EQ__BI_PI_8 =	0xec;
	public static final short INST_TEST__AND__BI_PI_8__EQ__PI_BI_8 =	0xed;
	public static final short INST_TEST__AND__BI_PI_8__EQ__PI_PI_8 =	0xee;

	public static final short INST_TEST__AND__PI_BI_8__EQ__BI_BI_8 =	0xef;
	public static final short INST_TEST__AND__PI_BI_8__EQ__BI_PI_8 =	0xf0;
	public static final short INST_TEST__AND__PI_BI_8__EQ__PI_BI_8 =	0xf1;
	public static final short INST_TEST__AND__PI_BI_8__EQ__PI_PI_8 =	0xf2;
	public static final short INST_TEST__AND__PI_PI_8__EQ__BI_BI_8 =	0xf3;
	public static final short INST_TEST__AND__PI_PI_8__EQ__BI_PI_8 =	0xf4;
	public static final short INST_TEST__AND__PI_PI_8__EQ__PI_BI_8 =	0xf5;
	public static final short INST_TEST__AND__PI_PI_8__EQ__PI_PI_8 =	0xf6;

	public static final short INST_TEST__AND__BI_BI_8__NE__BI_BI_8 =	0xf7;
	public static final short INST_TEST__AND__BI_BI_8__NE__BI_PI_8 =	0xf8;
	public static final short INST_TEST__AND__BI_BI_8__NE__PI_BI_8 =	0xf9;
	public static final short INST_TEST__AND__BI_BI_8__NE__PI_PI_8 =	0xfa;
	public static final short INST_TEST__AND__BI_PI_8__NE__BI_BI_8 =	0xfb;
	public static final short INST_TEST__AND__BI_PI_8__NE__BI_PI_8 =	0xfc;
	public static final short INST_TEST__AND__BI_PI_8__NE__PI_BI_8 =	0xfd;
	public static final short INST_TEST__AND__BI_PI_8__NE__PI_PI_8 =	0xfe;

	public static final short INST_TEST__AND__PI_BI_8__NE__BI_BI_8 =	0xff;
	public static final short INST_TEST__AND__PI_BI_8__NE__BI_PI_8 =	0x100;
	public static final short INST_TEST__AND__PI_BI_8__NE__PI_BI_8 =	0x101;
	public static final short INST_TEST__AND__PI_BI_8__NE__PI_PI_8 =	0x102;
	public static final short INST_TEST__AND__PI_PI_8__NE__BI_BI_8 =	0x103;
	public static final short INST_TEST__AND__PI_PI_8__NE__BI_PI_8 =	0x104;
	public static final short INST_TEST__AND__PI_PI_8__NE__PI_BI_8 =	0x105;
	public static final short INST_TEST__AND__PI_PI_8__NE__PI_PI_8 =	0x106;

	public static final short INST_TEST__AND__BI_BI_8__LT__BI_BI_8 =	0x107;
	public static final short INST_TEST__AND__BI_BI_8__LT__BI_PI_8 =	0x108;
	public static final short INST_TEST__AND__BI_BI_8__LT__PI_BI_8 =	0x109;
	public static final short INST_TEST__AND__BI_BI_8__LT__PI_PI_8 =	0x10a;
	public static final short INST_TEST__AND__BI_PI_8__LT__BI_BI_8 =	0x10b;
	public static final short INST_TEST__AND__BI_PI_8__LT__BI_PI_8 =	0x10c;
	public static final short INST_TEST__AND__BI_PI_8__LT__PI_BI_8 =	0x10d;
	public static final short INST_TEST__AND__BI_PI_8__LT__PI_PI_8 =	0x10e;

	public static final short INST_TEST__AND__PI_BI_8__LT__BI_BI_8 =	0x10f;
	public static final short INST_TEST__AND__PI_BI_8__LT__BI_PI_8 =	0x110;
	public static final short INST_TEST__AND__PI_BI_8__LT__PI_BI_8 =	0x111;
	public static final short INST_TEST__AND__PI_BI_8__LT__PI_PI_8 =	0x112;
	public static final short INST_TEST__AND__PI_PI_8__LT__BI_BI_8 =	0x113;
	public static final short INST_TEST__AND__PI_PI_8__LT__BI_PI_8 =	0x114;
	public static final short INST_TEST__AND__PI_PI_8__LT__PI_BI_8 =	0x115;
	public static final short INST_TEST__AND__PI_PI_8__LT__PI_PI_8 =	0x116;

	public static final short INST_TEST__AND__BI_BI_8__LE__BI_BI_8 =	0x117;
	public static final short INST_TEST__AND__BI_BI_8__LE__BI_PI_8 =	0x118;
	public static final short INST_TEST__AND__BI_BI_8__LE__PI_BI_8 =	0x119;
	public static final short INST_TEST__AND__BI_BI_8__LE__PI_PI_8 =	0x11a;
	public static final short INST_TEST__AND__BI_PI_8__LE__BI_BI_8 =	0x11b;
	public static final short INST_TEST__AND__BI_PI_8__LE__BI_PI_8 =	0x11c;
	public static final short INST_TEST__AND__BI_PI_8__LE__PI_BI_8 =	0x11d;
	public static final short INST_TEST__AND__BI_PI_8__LE__PI_PI_8 =	0x11e;

	public static final short INST_TEST__AND__PI_BI_8__LE__BI_BI_8 =	0x11f;
	public static final short INST_TEST__AND__PI_BI_8__LE__BI_PI_8 =	0x120;
	public static final short INST_TEST__AND__PI_BI_8__LE__PI_BI_8 =	0x121;
	public static final short INST_TEST__AND__PI_BI_8__LE__PI_PI_8 =	0x122;
	public static final short INST_TEST__AND__PI_PI_8__LE__BI_BI_8 =	0x123;
	public static final short INST_TEST__AND__PI_PI_8__LE__BI_PI_8 =	0x124;
	public static final short INST_TEST__AND__PI_PI_8__LE__PI_BI_8 =	0x125;
	public static final short INST_TEST__AND__PI_PI_8__LE__PI_PI_8 =	0x126;

	public static final short INST_TEST__AND__BI_BI_8__GT__BI_BI_8 =	0x127;
	public static final short INST_TEST__AND__BI_BI_8__GT__BI_PI_8 =	0x128;
	public static final short INST_TEST__AND__BI_BI_8__GT__PI_BI_8 =	0x129;
	public static final short INST_TEST__AND__BI_BI_8__GT__PI_PI_8 =	0x12a;
	public static final short INST_TEST__AND__BI_PI_8__GT__BI_BI_8 =	0x12b;
	public static final short INST_TEST__AND__BI_PI_8__GT__BI_PI_8 =	0x12c;
	public static final short INST_TEST__AND__BI_PI_8__GT__PI_BI_8 =	0x12d;
	public static final short INST_TEST__AND__BI_PI_8__GT__PI_PI_8 =	0x12e;

	public static final short INST_TEST__AND__PI_BI_8__GT__BI_BI_8 =	0x12f;
	public static final short INST_TEST__AND__PI_BI_8__GT__BI_PI_8 =	0x130;
	public static final short INST_TEST__AND__PI_BI_8__GT__PI_BI_8 =	0x131;
	public static final short INST_TEST__AND__PI_BI_8__GT__PI_PI_8 =	0x132;
	public static final short INST_TEST__AND__PI_PI_8__GT__BI_BI_8 =	0x133;
	public static final short INST_TEST__AND__PI_PI_8__GT__BI_PI_8 =	0x134;
	public static final short INST_TEST__AND__PI_PI_8__GT__PI_BI_8 =	0x135;
	public static final short INST_TEST__AND__PI_PI_8__GT__PI_PI_8 =	0x136;

	public static final short INST_TEST__AND__BI_BI_8__GE__BI_BI_8 =	0x137;
	public static final short INST_TEST__AND__BI_BI_8__GE__BI_PI_8 =	0x138;
	public static final short INST_TEST__AND__BI_BI_8__GE__PI_BI_8 =	0x139;
	public static final short INST_TEST__AND__BI_BI_8__GE__PI_PI_8 =	0x13a;
	public static final short INST_TEST__AND__BI_PI_8__GE__BI_BI_8 =	0x13b;
	public static final short INST_TEST__AND__BI_PI_8__GE__BI_PI_8 =	0x13c;
	public static final short INST_TEST__AND__BI_PI_8__GE__PI_BI_8 =	0x13d;
	public static final short INST_TEST__AND__BI_PI_8__GE__PI_PI_8 =	0x13e;

	public static final short INST_TEST__AND__PI_BI_8__GE__BI_BI_8 =	0x13f;
	public static final short INST_TEST__AND__PI_BI_8__GE__BI_PI_8 =	0x140;
	public static final short INST_TEST__AND__PI_BI_8__GE__PI_BI_8 =	0x141;
	public static final short INST_TEST__AND__PI_BI_8__GE__PI_PI_8 =	0x142;
	public static final short INST_TEST__AND__PI_PI_8__GE__BI_BI_8 =	0x143;
	public static final short INST_TEST__AND__PI_PI_8__GE__BI_PI_8 =	0x144;
	public static final short INST_TEST__AND__PI_PI_8__GE__PI_BI_8 =	0x145;
	public static final short INST_TEST__AND__PI_PI_8__GE__PI_PI_8 =	0x146;

	public static final short INST_TEST__OR__BI_BI_8__EQ__BI_BI_8 =		0x147;
	public static final short INST_TEST__OR__BI_BI_8__EQ__BI_PI_8 =		0x148;
	public static final short INST_TEST__OR__BI_BI_8__EQ__PI_BI_8 =		0x149;
	public static final short INST_TEST__OR__BI_BI_8__EQ__PI_PI_8 =		0x14a;
	public static final short INST_TEST__OR__BI_PI_8__EQ__BI_BI_8 =		0x14b;
	public static final short INST_TEST__OR__BI_PI_8__EQ__BI_PI_8 =		0x14c;
	public static final short INST_TEST__OR__BI_PI_8__EQ__PI_BI_8 =		0x14d;
	public static final short INST_TEST__OR__BI_PI_8__EQ__PI_PI_8 =		0x14e;

	public static final short INST_TEST__OR__PI_BI_8__EQ__BI_BI_8 =		0x14f;
	public static final short INST_TEST__OR__PI_BI_8__EQ__BI_PI_8 =		0x150;
	public static final short INST_TEST__OR__PI_BI_8__EQ__PI_BI_8 =		0x151;
	public static final short INST_TEST__OR__PI_BI_8__EQ__PI_PI_8 =		0x152;
	public static final short INST_TEST__OR__PI_PI_8__EQ__BI_BI_8 =		0x153;
	public static final short INST_TEST__OR__PI_PI_8__EQ__BI_PI_8 =		0x154;
	public static final short INST_TEST__OR__PI_PI_8__EQ__PI_BI_8 =		0x155;
	public static final short INST_TEST__OR__PI_PI_8__EQ__PI_PI_8 =		0x156;

	public static final short INST_TEST__OR__BI_BI_8__NE__BI_BI_8 =		0x157;
	public static final short INST_TEST__OR__BI_BI_8__NE__BI_PI_8 =		0x158;
	public static final short INST_TEST__OR__BI_BI_8__NE__PI_BI_8 =		0x159;
	public static final short INST_TEST__OR__BI_BI_8__NE__PI_PI_8 =		0x15a;
	public static final short INST_TEST__OR__BI_PI_8__NE__BI_BI_8 =		0x15b;
	public static final short INST_TEST__OR__BI_PI_8__NE__BI_PI_8 =		0x15c;
	public static final short INST_TEST__OR__BI_PI_8__NE__PI_BI_8 =		0x15d;
	public static final short INST_TEST__OR__BI_PI_8__NE__PI_PI_8 =		0x15e;

	public static final short INST_TEST__OR__PI_BI_8__NE__BI_BI_8 =		0x15f;
	public static final short INST_TEST__OR__PI_BI_8__NE__BI_PI_8 =		0x160;
	public static final short INST_TEST__OR__PI_BI_8__NE__PI_BI_8 =		0x161;
	public static final short INST_TEST__OR__PI_BI_8__NE__PI_PI_8 =		0x162;
	public static final short INST_TEST__OR__PI_PI_8__NE__BI_BI_8 =		0x163;
	public static final short INST_TEST__OR__PI_PI_8__NE__BI_PI_8 =		0x164;
	public static final short INST_TEST__OR__PI_PI_8__NE__PI_BI_8 =		0x165;
	public static final short INST_TEST__OR__PI_PI_8__NE__PI_PI_8 =		0x166;

	public static final short INST_TEST__OR__BI_BI_8__LT__BI_BI_8 =		0x167;
	public static final short INST_TEST__OR__BI_BI_8__LT__BI_PI_8 =		0x168;
	public static final short INST_TEST__OR__BI_BI_8__LT__PI_BI_8 =		0x169;
	public static final short INST_TEST__OR__BI_BI_8__LT__PI_PI_8 =		0x16a;
	public static final short INST_TEST__OR__BI_PI_8__LT__BI_BI_8 =		0x16b;
	public static final short INST_TEST__OR__BI_PI_8__LT__BI_PI_8 =		0x16c;
	public static final short INST_TEST__OR__BI_PI_8__LT__PI_BI_8 =		0x16d;
	public static final short INST_TEST__OR__BI_PI_8__LT__PI_PI_8 =		0x16e;

	public static final short INST_TEST__OR__PI_BI_8__LT__BI_BI_8 =		0x16f;
	public static final short INST_TEST__OR__PI_BI_8__LT__BI_PI_8 =		0x170;
	public static final short INST_TEST__OR__PI_BI_8__LT__PI_BI_8 =		0x171;
	public static final short INST_TEST__OR__PI_BI_8__LT__PI_PI_8 =		0x172;
	public static final short INST_TEST__OR__PI_PI_8__LT__BI_BI_8 =		0x173;
	public static final short INST_TEST__OR__PI_PI_8__LT__BI_PI_8 =		0x174;
	public static final short INST_TEST__OR__PI_PI_8__LT__PI_BI_8 =		0x175;
	public static final short INST_TEST__OR__PI_PI_8__LT__PI_PI_8 =		0x176;

	public static final short INST_TEST__OR__BI_BI_8__LE__BI_BI_8 =		0x177;
	public static final short INST_TEST__OR__BI_BI_8__LE__BI_PI_8 =		0x178;
	public static final short INST_TEST__OR__BI_BI_8__LE__PI_BI_8 =		0x179;
	public static final short INST_TEST__OR__BI_BI_8__LE__PI_PI_8 =		0x17a;
	public static final short INST_TEST__OR__BI_PI_8__LE__BI_BI_8 =		0x17b;
	public static final short INST_TEST__OR__BI_PI_8__LE__BI_PI_8 =		0x17c;
	public static final short INST_TEST__OR__BI_PI_8__LE__PI_BI_8 =		0x17d;
	public static final short INST_TEST__OR__BI_PI_8__LE__PI_PI_8 =		0x17e;

	public static final short INST_TEST__OR__PI_BI_8__LE__BI_BI_8 =		0x17f;
	public static final short INST_TEST__OR__PI_BI_8__LE__BI_PI_8 =		0x180;
	public static final short INST_TEST__OR__PI_BI_8__LE__PI_BI_8 =		0x181;
	public static final short INST_TEST__OR__PI_BI_8__LE__PI_PI_8 =		0x182;
	public static final short INST_TEST__OR__PI_PI_8__LE__BI_BI_8 =		0x183;
	public static final short INST_TEST__OR__PI_PI_8__LE__BI_PI_8 =		0x184;
	public static final short INST_TEST__OR__PI_PI_8__LE__PI_BI_8 =		0x185;
	public static final short INST_TEST__OR__PI_PI_8__LE__PI_PI_8 =		0x186;

	public static final short INST_TEST__OR__BI_BI_8__GT__BI_BI_8 =		0x187;
	public static final short INST_TEST__OR__BI_BI_8__GT__BI_PI_8 =		0x188;
	public static final short INST_TEST__OR__BI_BI_8__GT__PI_BI_8 =		0x189;
	public static final short INST_TEST__OR__BI_BI_8__GT__PI_PI_8 =		0x18a;
	public static final short INST_TEST__OR__BI_PI_8__GT__BI_BI_8 =		0x18b;
	public static final short INST_TEST__OR__BI_PI_8__GT__BI_PI_8 =		0x18c;
	public static final short INST_TEST__OR__BI_PI_8__GT__PI_BI_8 =		0x18d;
	public static final short INST_TEST__OR__BI_PI_8__GT__PI_PI_8 =		0x18e;

	public static final short INST_TEST__OR__PI_BI_8__GT__BI_BI_8 =		0x18f;
	public static final short INST_TEST__OR__PI_BI_8__GT__BI_PI_8 =		0x190;
	public static final short INST_TEST__OR__PI_BI_8__GT__PI_BI_8 =		0x191;
	public static final short INST_TEST__OR__PI_BI_8__GT__PI_PI_8 =		0x192;
	public static final short INST_TEST__OR__PI_PI_8__GT__BI_BI_8 =		0x193;
	public static final short INST_TEST__OR__PI_PI_8__GT__BI_PI_8 =		0x194;
	public static final short INST_TEST__OR__PI_PI_8__GT__PI_BI_8 =		0x195;
	public static final short INST_TEST__OR__PI_PI_8__GT__PI_PI_8 =		0x196;

	public static final short INST_TEST__OR__BI_BI_8__GE__BI_BI_8 =		0x197;
	public static final short INST_TEST__OR__BI_BI_8__GE__BI_PI_8 =		0x198;
	public static final short INST_TEST__OR__BI_BI_8__GE__PI_BI_8 =		0x199;
	public static final short INST_TEST__OR__BI_BI_8__GE__PI_PI_8 =		0x19a;
	public static final short INST_TEST__OR__BI_PI_8__GE__BI_BI_8 =		0x19b;
	public static final short INST_TEST__OR__BI_PI_8__GE__BI_PI_8 =		0x19c;
	public static final short INST_TEST__OR__BI_PI_8__GE__PI_BI_8 =		0x19d;
	public static final short INST_TEST__OR__BI_PI_8__GE__PI_PI_8 =		0x19e;

	public static final short INST_TEST__OR__PI_BI_8__GE__BI_BI_8 =		0x19f;
	public static final short INST_TEST__OR__PI_BI_8__GE__BI_PI_8 =		0x1a0;
	public static final short INST_TEST__OR__PI_BI_8__GE__PI_BI_8 =		0x1a1;
	public static final short INST_TEST__OR__PI_BI_8__GE__PI_PI_8 =		0x1a2;
	public static final short INST_TEST__OR__PI_PI_8__GE__BI_BI_8 =		0x1a3;
	public static final short INST_TEST__OR__PI_PI_8__GE__BI_PI_8 =		0x1a4;
	public static final short INST_TEST__OR__PI_PI_8__GE__PI_BI_8 =		0x1a5;
	public static final short INST_TEST__OR__PI_PI_8__GE__PI_PI_8 =		0x1a6;

	public static final short INST_TEST__XOR__BI_BI_8__EQ__BI_BI_8 =	0x1a7;
	public static final short INST_TEST__XOR__BI_BI_8__EQ__BI_PI_8 =	0x1a8;
	public static final short INST_TEST__XOR__BI_BI_8__EQ__PI_BI_8 =	0x1a9;
	public static final short INST_TEST__XOR__BI_BI_8__EQ__PI_PI_8 =	0x1aa;
	public static final short INST_TEST__XOR__BI_PI_8__EQ__BI_BI_8 =	0x1ab;
	public static final short INST_TEST__XOR__BI_PI_8__EQ__BI_PI_8 =	0x1ac;
	public static final short INST_TEST__XOR__BI_PI_8__EQ__PI_BI_8 =	0x1ad;
	public static final short INST_TEST__XOR__BI_PI_8__EQ__PI_PI_8 =	0x1ae;

	public static final short INST_TEST__XOR__PI_BI_8__EQ__BI_BI_8 =	0x1af;
	public static final short INST_TEST__XOR__PI_BI_8__EQ__BI_PI_8 =	0x1b0;
	public static final short INST_TEST__XOR__PI_BI_8__EQ__PI_BI_8 =	0x1b1;
	public static final short INST_TEST__XOR__PI_BI_8__EQ__PI_PI_8 =	0x1b2;
	public static final short INST_TEST__XOR__PI_PI_8__EQ__BI_BI_8 =	0x1b3;
	public static final short INST_TEST__XOR__PI_PI_8__EQ__BI_PI_8 =	0x1b4;
	public static final short INST_TEST__XOR__PI_PI_8__EQ__PI_BI_8 =	0x1b5;
	public static final short INST_TEST__XOR__PI_PI_8__EQ__PI_PI_8 =	0x1b6;

	public static final short INST_TEST__XOR__BI_BI_8__NE__BI_BI_8 =	0x1b7;
	public static final short INST_TEST__XOR__BI_BI_8__NE__BI_PI_8 =	0x1b8;
	public static final short INST_TEST__XOR__BI_BI_8__NE__PI_BI_8 =	0x1b9;
	public static final short INST_TEST__XOR__BI_BI_8__NE__PI_PI_8 =	0x1ba;
	public static final short INST_TEST__XOR__BI_PI_8__NE__BI_BI_8 =	0x1bb;
	public static final short INST_TEST__XOR__BI_PI_8__NE__BI_PI_8 =	0x1bc;
	public static final short INST_TEST__XOR__BI_PI_8__NE__PI_BI_8 =	0x1bd;
	public static final short INST_TEST__XOR__BI_PI_8__NE__PI_PI_8 =	0x1be;

	public static final short INST_TEST__XOR__PI_BI_8__NE__BI_BI_8 =	0x1bf;
	public static final short INST_TEST__XOR__PI_BI_8__NE__BI_PI_8 =	0x1c0;
	public static final short INST_TEST__XOR__PI_BI_8__NE__PI_BI_8 =	0x1c1;
	public static final short INST_TEST__XOR__PI_BI_8__NE__PI_PI_8 =	0x1c2;
	public static final short INST_TEST__XOR__PI_PI_8__NE__BI_BI_8 =	0x1c3;
	public static final short INST_TEST__XOR__PI_PI_8__NE__BI_PI_8 =	0x1c4;
	public static final short INST_TEST__XOR__PI_PI_8__NE__PI_BI_8 =	0x1c5;
	public static final short INST_TEST__XOR__PI_PI_8__NE__PI_PI_8 =	0x1c6;

	public static final short INST_TEST__XOR__BI_BI_8__LT__BI_BI_8 =	0x1c7;
	public static final short INST_TEST__XOR__BI_BI_8__LT__BI_PI_8 =	0x1c8;
	public static final short INST_TEST__XOR__BI_BI_8__LT__PI_BI_8 =	0x1c9;
	public static final short INST_TEST__XOR__BI_BI_8__LT__PI_PI_8 =	0x1ca;
	public static final short INST_TEST__XOR__BI_PI_8__LT__BI_BI_8 =	0x1cb;
	public static final short INST_TEST__XOR__BI_PI_8__LT__BI_PI_8 =	0x1cc;
	public static final short INST_TEST__XOR__BI_PI_8__LT__PI_BI_8 =	0x1cd;
	public static final short INST_TEST__XOR__BI_PI_8__LT__PI_PI_8 =	0x1ce;

	public static final short INST_TEST__XOR__PI_BI_8__LT__BI_BI_8 =	0x1cf;
	public static final short INST_TEST__XOR__PI_BI_8__LT__BI_PI_8 =	0x1d0;
	public static final short INST_TEST__XOR__PI_BI_8__LT__PI_BI_8 =	0x1d1;
	public static final short INST_TEST__XOR__PI_BI_8__LT__PI_PI_8 =	0x1d2;
	public static final short INST_TEST__XOR__PI_PI_8__LT__BI_BI_8 =	0x1d3;
	public static final short INST_TEST__XOR__PI_PI_8__LT__BI_PI_8 =	0x1d4;
	public static final short INST_TEST__XOR__PI_PI_8__LT__PI_BI_8 =	0x1d5;
	public static final short INST_TEST__XOR__PI_PI_8__LT__PI_PI_8 =	0x1d6;

	public static final short INST_TEST__XOR__BI_BI_8__LE__BI_BI_8 =	0x1d7;
	public static final short INST_TEST__XOR__BI_BI_8__LE__BI_PI_8 =	0x1d8;
	public static final short INST_TEST__XOR__BI_BI_8__LE__PI_BI_8 =	0x1d9;
	public static final short INST_TEST__XOR__BI_BI_8__LE__PI_PI_8 =	0x1da;
	public static final short INST_TEST__XOR__BI_PI_8__LE__BI_BI_8 =	0x1db;
	public static final short INST_TEST__XOR__BI_PI_8__LE__BI_PI_8 =	0x1dc;
	public static final short INST_TEST__XOR__BI_PI_8__LE__PI_BI_8 =	0x1dd;
	public static final short INST_TEST__XOR__BI_PI_8__LE__PI_PI_8 =	0x1de;

	public static final short INST_TEST__XOR__PI_BI_8__LE__BI_BI_8 =	0x1df;
	public static final short INST_TEST__XOR__PI_BI_8__LE__BI_PI_8 =	0x1e0;
	public static final short INST_TEST__XOR__PI_BI_8__LE__PI_BI_8 =	0x1e1;
	public static final short INST_TEST__XOR__PI_BI_8__LE__PI_PI_8 =	0x1e2;
	public static final short INST_TEST__XOR__PI_PI_8__LE__BI_BI_8 =	0x1e3;
	public static final short INST_TEST__XOR__PI_PI_8__LE__BI_PI_8 =	0x1e4;
	public static final short INST_TEST__XOR__PI_PI_8__LE__PI_BI_8 =	0x1e5;
	public static final short INST_TEST__XOR__PI_PI_8__LE__PI_PI_8 =	0x1e6;

	public static final short INST_TEST__XOR__BI_BI_8__GT__BI_BI_8 =	0x1e7;
	public static final short INST_TEST__XOR__BI_BI_8__GT__BI_PI_8 =	0x1e8;
	public static final short INST_TEST__XOR__BI_BI_8__GT__PI_BI_8 =	0x1e9;
	public static final short INST_TEST__XOR__BI_BI_8__GT__PI_PI_8 =	0x1ea;
	public static final short INST_TEST__XOR__BI_PI_8__GT__BI_BI_8 =	0x1eb;
	public static final short INST_TEST__XOR__BI_PI_8__GT__BI_PI_8 =	0x1ec;
	public static final short INST_TEST__XOR__BI_PI_8__GT__PI_BI_8 =	0x1ed;
	public static final short INST_TEST__XOR__BI_PI_8__GT__PI_PI_8 =	0x1ee;

	public static final short INST_TEST__XOR__PI_BI_8__GT__BI_BI_8 =	0x1ef;
	public static final short INST_TEST__XOR__PI_BI_8__GT__BI_PI_8 =	0x1f0;
	public static final short INST_TEST__XOR__PI_BI_8__GT__PI_BI_8 =	0x1f1;
	public static final short INST_TEST__XOR__PI_BI_8__GT__PI_PI_8 =	0x1f2;
	public static final short INST_TEST__XOR__PI_PI_8__GT__BI_BI_8 =	0x1f3;
	public static final short INST_TEST__XOR__PI_PI_8__GT__BI_PI_8 =	0x1f4;
	public static final short INST_TEST__XOR__PI_PI_8__GT__PI_BI_8 =	0x1f5;
	public static final short INST_TEST__XOR__PI_PI_8__GT__PI_PI_8 =	0x1f6;

	public static final short INST_TEST__XOR__BI_BI_8__GE__BI_BI_8 =	0x1f7;
	public static final short INST_TEST__XOR__BI_BI_8__GE__BI_PI_8 =	0x1f8;
	public static final short INST_TEST__XOR__BI_BI_8__GE__PI_BI_8 =	0x1f9;
	public static final short INST_TEST__XOR__BI_BI_8__GE__PI_PI_8 =	0x1fa;
	public static final short INST_TEST__XOR__BI_PI_8__GE__BI_BI_8 =	0x1fb;
	public static final short INST_TEST__XOR__BI_PI_8__GE__BI_PI_8 =	0x1fc;
	public static final short INST_TEST__XOR__BI_PI_8__GE__PI_BI_8 =	0x1fd;
	public static final short INST_TEST__XOR__BI_PI_8__GE__PI_PI_8 =	0x1fe;

	public static final short INST_TEST__XOR__PI_BI_8__GE__BI_BI_8 =	0x1ff;
	public static final short INST_TEST__XOR__PI_BI_8__GE__BI_PI_8 =	0x200;
	public static final short INST_TEST__XOR__PI_BI_8__GE__PI_BI_8 =	0x201;
	public static final short INST_TEST__XOR__PI_BI_8__GE__PI_PI_8 =	0x202;
	public static final short INST_TEST__XOR__PI_PI_8__GE__BI_BI_8 =	0x203;
	public static final short INST_TEST__XOR__PI_PI_8__GE__BI_PI_8 =	0x204;
	public static final short INST_TEST__XOR__PI_PI_8__GE__PI_BI_8 =	0x205;
	public static final short INST_TEST__XOR__PI_PI_8__GE__PI_PI_8 =	0x206;

	public void run() throws RuntimeError
	{
		short inst;

		if (SAFE)
			checkBaseInstructionPointer();
		
		instBuffer = buffers[bip];

		if (SAFE)
			checkNullInstructionBuffer(bip);

		while (ip < instBuffer.length)
		{
			inst = nextShort();
			switch (inst)
			{
			default:
				throw new RuntimeError(
						ErrorType.UNKNOWN_INSTRUCTION, 
						Integer.toHexString(inst));
			
			case INST_NOOP:
				break;

			case INST_COPY_IM32_PI32:
				pointers[nextPointersIndex()] = nextInt();
				break;

			case INST_COPY_PI32_PI32:
				pointers[nextPointersIndex()] = pointers[nextPointersIndex()];
				break;
				
			// copy IM8, MI8

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
				
			// copy MI8, MI8
				
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
				
			// SWAP MI8, MI8
				
			case INST_SWAP__BI_BI_8__BI_BI_8:
				
				break;
				
			}
		}
	}
}
