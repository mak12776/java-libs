package libs.machines;

public class Machine
{
	public static final boolean SAFE = true;
	
	private byte[][] buffers;
	private int[] pointers;
	private byte flags;
	
	private int bip;
	private int ip;
	
	public Machine(byte[][] buffers, int [] pointers, byte flags, int bip, int ip)
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
	
	private int readDataBuffer1_Index_ImmediateIndex()
	{
		int index = readInt();
		if (SAFE)
			if ((index < 0) || (index >= dataBuffer1.length))
				throw new RuntimeError(ErrorType.INVALID_BUFFER_INDEX, String.valueOf(index));
		return index;
	}
	
	private int readDataBuffer1_Index_PointersIndex()
	{
		int index = pointers[readPointersIndex()];
		if (SAFE)
			if ((index < 0) || (index >= dataBuffer1.length))
				throw new RuntimeError(ErrorType.INVALID_BUFFER_INDEX, String.valueOf(index));
		return index;
	}
	
	private int readDataBuffer2_Index_ImmediateIndex()
	{
		int index = readInt();
		if (SAFE)
			if ((index < 0) || (index >= dataBuffer2.length))
				throw new RuntimeError(ErrorType.INVALID_BUFFER_INDEX, String.valueOf(index));
		return index;
	}
	
	private int readDataBuffer2_Index_PointersIndex()
	{
		int index = pointers[readPointersIndex()];
		if (SAFE)
			if ((index < 0) || (index >= dataBuffer2.length))
				throw new RuntimeError(ErrorType.INVALID_BUFFER_INDEX, String.valueOf(index));
		return index;
	}
	
	// next data buffer
	
	private void nextDataBuffer1_ImmediateIndex()
	{
		int index = readInt();
		if (SAFE)
			if ((index < 0) || (index >= buffers.length))
				throw new RuntimeError(ErrorType.INVALID_BASE_INDEX, String.valueOf(index));
		dataBuffer1 = buffers[index];
		if (SAFE)
			if (dataBuffer1 == null)
				throw new RuntimeError(ErrorType.NULL_BASE_INDEX_BUFFER, String.valueOf(index));
	}
	
	private void nextDataBuffer1_PointersIndex()
	{
		int index = pointers[readPointersIndex()];
		if (SAFE)
			if ((index < 0) || (index >= buffers.length))
				throw new RuntimeError(ErrorType.INVALID_BASE_INDEX, String.valueOf(index));
		dataBuffer1 = buffers[index];
		if (SAFE)
			if (dataBuffer1 == null)
				throw new RuntimeError(ErrorType.NULL_BASE_INDEX_BUFFER, String.valueOf(index));
	}
	
	private void nextDataBuffer2_ImmediateIndex()
	{
		int index = readInt();
		if (SAFE)
			if ((index < 0) || (index >= buffers.length))
				throw new RuntimeError(ErrorType.INVALID_BASE_INDEX, String.valueOf(index));
		dataBuffer2 = buffers[index];
		if (SAFE)
			if (dataBuffer2 == null)
				throw new RuntimeError(ErrorType.NULL_BASE_INDEX_BUFFER, String.valueOf(index));
	}
	
	private void nextDataBuffer2_PointersIndex()
	{
		int index = pointers[readPointersIndex()];
		if (SAFE)
			if ((index < 0) || (index >= buffers.length))
				throw new RuntimeError(ErrorType.INVALID_BASE_INDEX, String.valueOf(index));
		dataBuffer2 = buffers[index];
		if (SAFE)
			if (dataBuffer2 == null)
				throw new RuntimeError(ErrorType.NULL_BASE_INDEX_BUFFER, String.valueOf(index));
	}
	
	private void swapDataBuffer_Indexes_1_2(int index1, int index2)
	{
		tempByte = dataBuffer1[index1];
		dataBuffer1[index1] = dataBuffer2[index2];
		dataBuffer2[index2] = tempByte;
	}
	
	// checkers
	
	private void checkBaseInstPointer()
	{
		if ((bip < 0) || (bip >= buffers.length))
			throw new RuntimeError(ErrorType.INVALID_BASE_INST_POINTER, String.valueOf(bip));
	}
	
	private void checkInstPointer()
	{
		if ((ip < 0) || (ip >= instBuffer.length))
			throw new RuntimeError(ErrorType.INVALID_INST_POINTER, String.valueOf(ip));
	}

	private void checkInstBuffer()
	{
		if (instBuffer == null)
			throw new RuntimeError(ErrorType.NULL_BASE_INST_POINTER_BUFFER, String.valueOf(bip));
	}
	
	// read inst buffer
	
	// -- instructions start --
	
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
	
	// SWAP MEM8 MEM8
	
	public static final short INST_SWAP__IM_IM_8__IM_IM_8 = 23;
	public static final short INST_SWAP__IM_IM_8__IM_PI_8 = 24;
	public static final short INST_SWAP__IM_IM_8__PI_IM_8 = 25;
	public static final short INST_SWAP__IM_IM_8__PI_PI_8 = 26;
	
	public static final short INST_SWAP__IM_PI_8__IM_IM_8 = 27;
	public static final short INST_SWAP__IM_PI_8__IM_PI_8 = 28;
	public static final short INST_SWAP__IM_PI_8__PI_IM_8 = 29;
	public static final short INST_SWAP__IM_PI_8__PI_PI_8 = 30;
	
	public static final short INST_SWAP__PI_IM_8__IM_IM_8 = 31;
	public static final short INST_SWAP__PI_IM_8__IM_PI_8 = 32;
	public static final short INST_SWAP__PI_IM_8__PI_IM_8 = 33;
	public static final short INST_SWAP__PI_IM_8__PI_PI_8 = 34;
	
	public static final short INST_SWAP__PI_PI_8__IM_IM_8 = 35;
	public static final short INST_SWAP__PI_PI_8__IM_PI_8 = 36;
	public static final short INST_SWAP__PI_PI_8__PI_IM_8 = 37;
	public static final short INST_SWAP__PI_PI_8__PI_PI_8 = 38;
	
	// TEST INDEX LOGIC IM8 EQ/LT/GT MEM8
	
	public static final short INST_TEST_0_SET__IM8__EQ__IM_IM_8 = 39;
	public static final short INST_TEST_0_SET__IM8__EQ__IM_PI_8 = 40;
	public static final short INST_TEST_0_SET__IM8__EQ__PI_IM_8 = 41;
	public static final short INST_TEST_0_SET__IM8__EQ__PI_PI_8 = 42;
	
	public static final short INST_TEST_0_SET__IM8__NE__IM_IM_8 = 43;
	public static final short INST_TEST_0_SET__IM8__NE__IM_PI_8 = 44;
	public static final short INST_TEST_0_SET__IM8__NE__PI_IM_8 = 45;
	public static final short INST_TEST_0_SET__IM8__NE__PI_PI_8 = 46;
	
	public static final short INST_TEST_0_SET__IM8__LT__IM_IM_8 = 47;
	public static final short INST_TEST_0_SET__IM8__LT__IM_PI_8 = 48;
	public static final short INST_TEST_0_SET__IM8__LT__PI_IM_8 = 49;
	public static final short INST_TEST_0_SET__IM8__LT__PI_PI_8 = 50;
	
	public static final short INST_TEST_0_SET__IM8__LE__IM_IM_8 = 51;
	public static final short INST_TEST_0_SET__IM8__LE__IM_PI_8 = 52;
	public static final short INST_TEST_0_SET__IM8__LE__PI_IM_8 = 53;
	public static final short INST_TEST_0_SET__IM8__LE__PI_PI_8 = 54;
	
	public static final short INST_TEST_0_SET__IM8__GT__IM_IM_8 = 55;
	public static final short INST_TEST_0_SET__IM8__GT__IM_PI_8 = 56;
	public static final short INST_TEST_0_SET__IM8__GT__PI_IM_8 = 57;
	public static final short INST_TEST_0_SET__IM8__GT__PI_PI_8 = 58;
	
	public static final short INST_TEST_0_SET__IM8__GE__IM_IM_8 = 59;
	public static final short INST_TEST_0_SET__IM8__GE__IM_PI_8 = 60;
	public static final short INST_TEST_0_SET__IM8__GE__PI_IM_8 = 61;
	public static final short INST_TEST_0_SET__IM8__GE__PI_PI_8 = 62;
	
	public static final short INST_TEST_0_AND__IM8__EQ__IM_IM_8 = 63;
	public static final short INST_TEST_0_AND__IM8__EQ__IM_PI_8 = 64;
	public static final short INST_TEST_0_AND__IM8__EQ__PI_IM_8 = 65;
	public static final short INST_TEST_0_AND__IM8__EQ__PI_PI_8 = 66;
	
	public static final short INST_TEST_0_AND__IM8__NE__IM_IM_8 = 67;
	public static final short INST_TEST_0_AND__IM8__NE__IM_PI_8 = 68;
	public static final short INST_TEST_0_AND__IM8__NE__PI_IM_8 = 69;
	public static final short INST_TEST_0_AND__IM8__NE__PI_PI_8 = 70;
	
	public static final short INST_TEST_0_AND__IM8__LT__IM_IM_8 = 71;
	public static final short INST_TEST_0_AND__IM8__LT__IM_PI_8 = 72;
	public static final short INST_TEST_0_AND__IM8__LT__PI_IM_8 = 73;
	public static final short INST_TEST_0_AND__IM8__LT__PI_PI_8 = 74;
	
	public static final short INST_TEST_0_AND__IM8__LE__IM_IM_8 = 75;
	public static final short INST_TEST_0_AND__IM8__LE__IM_PI_8 = 76;
	public static final short INST_TEST_0_AND__IM8__LE__PI_IM_8 = 77;
	public static final short INST_TEST_0_AND__IM8__LE__PI_PI_8 = 78;
	
	public static final short INST_TEST_0_AND__IM8__GT__IM_IM_8 = 79;
	public static final short INST_TEST_0_AND__IM8__GT__IM_PI_8 = 80;
	public static final short INST_TEST_0_AND__IM8__GT__PI_IM_8 = 81;
	public static final short INST_TEST_0_AND__IM8__GT__PI_PI_8 = 82;
	
	public static final short INST_TEST_0_AND__IM8__GE__IM_IM_8 = 83;
	public static final short INST_TEST_0_AND__IM8__GE__IM_PI_8 = 84;
	public static final short INST_TEST_0_AND__IM8__GE__PI_IM_8 = 85;
	public static final short INST_TEST_0_AND__IM8__GE__PI_PI_8 = 86;
	
	public static final short INST_TEST_0_OR__IM8__EQ__IM_IM_8 = 87;
	public static final short INST_TEST_0_OR__IM8__EQ__IM_PI_8 = 88;
	public static final short INST_TEST_0_OR__IM8__EQ__PI_IM_8 = 89;
	public static final short INST_TEST_0_OR__IM8__EQ__PI_PI_8 = 90;
	
	public static final short INST_TEST_0_OR__IM8__NE__IM_IM_8 = 91;
	public static final short INST_TEST_0_OR__IM8__NE__IM_PI_8 = 92;
	public static final short INST_TEST_0_OR__IM8__NE__PI_IM_8 = 93;
	public static final short INST_TEST_0_OR__IM8__NE__PI_PI_8 = 94;
	
	public static final short INST_TEST_0_OR__IM8__LT__IM_IM_8 = 95;
	public static final short INST_TEST_0_OR__IM8__LT__IM_PI_8 = 96;
	public static final short INST_TEST_0_OR__IM8__LT__PI_IM_8 = 97;
	public static final short INST_TEST_0_OR__IM8__LT__PI_PI_8 = 98;
	
	public static final short INST_TEST_0_OR__IM8__LE__IM_IM_8 = 99;
	public static final short INST_TEST_0_OR__IM8__LE__IM_PI_8 = 100;
	public static final short INST_TEST_0_OR__IM8__LE__PI_IM_8 = 101;
	public static final short INST_TEST_0_OR__IM8__LE__PI_PI_8 = 102;
	
	public static final short INST_TEST_0_OR__IM8__GT__IM_IM_8 = 103;
	public static final short INST_TEST_0_OR__IM8__GT__IM_PI_8 = 104;
	public static final short INST_TEST_0_OR__IM8__GT__PI_IM_8 = 105;
	public static final short INST_TEST_0_OR__IM8__GT__PI_PI_8 = 106;
	
	public static final short INST_TEST_0_OR__IM8__GE__IM_IM_8 = 107;
	public static final short INST_TEST_0_OR__IM8__GE__IM_PI_8 = 108;
	public static final short INST_TEST_0_OR__IM8__GE__PI_IM_8 = 109;
	public static final short INST_TEST_0_OR__IM8__GE__PI_PI_8 = 110;
	
	public static final short INST_TEST_0_XOR__IM8__EQ__IM_IM_8 = 111;
	public static final short INST_TEST_0_XOR__IM8__EQ__IM_PI_8 = 112;
	public static final short INST_TEST_0_XOR__IM8__EQ__PI_IM_8 = 113;
	public static final short INST_TEST_0_XOR__IM8__EQ__PI_PI_8 = 114;
	
	public static final short INST_TEST_0_XOR__IM8__NE__IM_IM_8 = 115;
	public static final short INST_TEST_0_XOR__IM8__NE__IM_PI_8 = 116;
	public static final short INST_TEST_0_XOR__IM8__NE__PI_IM_8 = 117;
	public static final short INST_TEST_0_XOR__IM8__NE__PI_PI_8 = 118;
	
	public static final short INST_TEST_0_XOR__IM8__LT__IM_IM_8 = 119;
	public static final short INST_TEST_0_XOR__IM8__LT__IM_PI_8 = 120;
	public static final short INST_TEST_0_XOR__IM8__LT__PI_IM_8 = 121;
	public static final short INST_TEST_0_XOR__IM8__LT__PI_PI_8 = 122;
	
	public static final short INST_TEST_0_XOR__IM8__LE__IM_IM_8 = 123;
	public static final short INST_TEST_0_XOR__IM8__LE__IM_PI_8 = 124;
	public static final short INST_TEST_0_XOR__IM8__LE__PI_IM_8 = 125;
	public static final short INST_TEST_0_XOR__IM8__LE__PI_PI_8 = 126;
	
	public static final short INST_TEST_0_XOR__IM8__GT__IM_IM_8 = 127;
	public static final short INST_TEST_0_XOR__IM8__GT__IM_PI_8 = 128;
	public static final short INST_TEST_0_XOR__IM8__GT__PI_IM_8 = 129;
	public static final short INST_TEST_0_XOR__IM8__GT__PI_PI_8 = 130;
	
	public static final short INST_TEST_0_XOR__IM8__GE__IM_IM_8 = 131;
	public static final short INST_TEST_0_XOR__IM8__GE__IM_PI_8 = 132;
	public static final short INST_TEST_0_XOR__IM8__GE__PI_IM_8 = 133;
	public static final short INST_TEST_0_XOR__IM8__GE__PI_PI_8 = 134;
	
	public static final short INST_TEST_1_SET__IM8__EQ__IM_IM_8 = 135;
	public static final short INST_TEST_1_SET__IM8__EQ__IM_PI_8 = 136;
	public static final short INST_TEST_1_SET__IM8__EQ__PI_IM_8 = 137;
	public static final short INST_TEST_1_SET__IM8__EQ__PI_PI_8 = 138;
	
	public static final short INST_TEST_1_SET__IM8__NE__IM_IM_8 = 139;
	public static final short INST_TEST_1_SET__IM8__NE__IM_PI_8 = 140;
	public static final short INST_TEST_1_SET__IM8__NE__PI_IM_8 = 141;
	public static final short INST_TEST_1_SET__IM8__NE__PI_PI_8 = 142;
	
	public static final short INST_TEST_1_SET__IM8__LT__IM_IM_8 = 143;
	public static final short INST_TEST_1_SET__IM8__LT__IM_PI_8 = 144;
	public static final short INST_TEST_1_SET__IM8__LT__PI_IM_8 = 145;
	public static final short INST_TEST_1_SET__IM8__LT__PI_PI_8 = 146;
	
	public static final short INST_TEST_1_SET__IM8__LE__IM_IM_8 = 147;
	public static final short INST_TEST_1_SET__IM8__LE__IM_PI_8 = 148;
	public static final short INST_TEST_1_SET__IM8__LE__PI_IM_8 = 149;
	public static final short INST_TEST_1_SET__IM8__LE__PI_PI_8 = 150;
	
	public static final short INST_TEST_1_SET__IM8__GT__IM_IM_8 = 151;
	public static final short INST_TEST_1_SET__IM8__GT__IM_PI_8 = 152;
	public static final short INST_TEST_1_SET__IM8__GT__PI_IM_8 = 153;
	public static final short INST_TEST_1_SET__IM8__GT__PI_PI_8 = 154;
	
	public static final short INST_TEST_1_SET__IM8__GE__IM_IM_8 = 155;
	public static final short INST_TEST_1_SET__IM8__GE__IM_PI_8 = 156;
	public static final short INST_TEST_1_SET__IM8__GE__PI_IM_8 = 157;
	public static final short INST_TEST_1_SET__IM8__GE__PI_PI_8 = 158;
	
	public static final short INST_TEST_1_AND__IM8__EQ__IM_IM_8 = 159;
	public static final short INST_TEST_1_AND__IM8__EQ__IM_PI_8 = 160;
	public static final short INST_TEST_1_AND__IM8__EQ__PI_IM_8 = 161;
	public static final short INST_TEST_1_AND__IM8__EQ__PI_PI_8 = 162;
	
	public static final short INST_TEST_1_AND__IM8__NE__IM_IM_8 = 163;
	public static final short INST_TEST_1_AND__IM8__NE__IM_PI_8 = 164;
	public static final short INST_TEST_1_AND__IM8__NE__PI_IM_8 = 165;
	public static final short INST_TEST_1_AND__IM8__NE__PI_PI_8 = 166;
	
	public static final short INST_TEST_1_AND__IM8__LT__IM_IM_8 = 167;
	public static final short INST_TEST_1_AND__IM8__LT__IM_PI_8 = 168;
	public static final short INST_TEST_1_AND__IM8__LT__PI_IM_8 = 169;
	public static final short INST_TEST_1_AND__IM8__LT__PI_PI_8 = 170;
	
	public static final short INST_TEST_1_AND__IM8__LE__IM_IM_8 = 171;
	public static final short INST_TEST_1_AND__IM8__LE__IM_PI_8 = 172;
	public static final short INST_TEST_1_AND__IM8__LE__PI_IM_8 = 173;
	public static final short INST_TEST_1_AND__IM8__LE__PI_PI_8 = 174;
	
	public static final short INST_TEST_1_AND__IM8__GT__IM_IM_8 = 175;
	public static final short INST_TEST_1_AND__IM8__GT__IM_PI_8 = 176;
	public static final short INST_TEST_1_AND__IM8__GT__PI_IM_8 = 177;
	public static final short INST_TEST_1_AND__IM8__GT__PI_PI_8 = 178;
	
	public static final short INST_TEST_1_AND__IM8__GE__IM_IM_8 = 179;
	public static final short INST_TEST_1_AND__IM8__GE__IM_PI_8 = 180;
	public static final short INST_TEST_1_AND__IM8__GE__PI_IM_8 = 181;
	public static final short INST_TEST_1_AND__IM8__GE__PI_PI_8 = 182;
	
	public static final short INST_TEST_1_OR__IM8__EQ__IM_IM_8 = 183;
	public static final short INST_TEST_1_OR__IM8__EQ__IM_PI_8 = 184;
	public static final short INST_TEST_1_OR__IM8__EQ__PI_IM_8 = 185;
	public static final short INST_TEST_1_OR__IM8__EQ__PI_PI_8 = 186;
	
	public static final short INST_TEST_1_OR__IM8__NE__IM_IM_8 = 187;
	public static final short INST_TEST_1_OR__IM8__NE__IM_PI_8 = 188;
	public static final short INST_TEST_1_OR__IM8__NE__PI_IM_8 = 189;
	public static final short INST_TEST_1_OR__IM8__NE__PI_PI_8 = 190;
	
	public static final short INST_TEST_1_OR__IM8__LT__IM_IM_8 = 191;
	public static final short INST_TEST_1_OR__IM8__LT__IM_PI_8 = 192;
	public static final short INST_TEST_1_OR__IM8__LT__PI_IM_8 = 193;
	public static final short INST_TEST_1_OR__IM8__LT__PI_PI_8 = 194;
	
	public static final short INST_TEST_1_OR__IM8__LE__IM_IM_8 = 195;
	public static final short INST_TEST_1_OR__IM8__LE__IM_PI_8 = 196;
	public static final short INST_TEST_1_OR__IM8__LE__PI_IM_8 = 197;
	public static final short INST_TEST_1_OR__IM8__LE__PI_PI_8 = 198;
	
	public static final short INST_TEST_1_OR__IM8__GT__IM_IM_8 = 199;
	public static final short INST_TEST_1_OR__IM8__GT__IM_PI_8 = 200;
	public static final short INST_TEST_1_OR__IM8__GT__PI_IM_8 = 201;
	public static final short INST_TEST_1_OR__IM8__GT__PI_PI_8 = 202;
	
	public static final short INST_TEST_1_OR__IM8__GE__IM_IM_8 = 203;
	public static final short INST_TEST_1_OR__IM8__GE__IM_PI_8 = 204;
	public static final short INST_TEST_1_OR__IM8__GE__PI_IM_8 = 205;
	public static final short INST_TEST_1_OR__IM8__GE__PI_PI_8 = 206;
	
	public static final short INST_TEST_1_XOR__IM8__EQ__IM_IM_8 = 207;
	public static final short INST_TEST_1_XOR__IM8__EQ__IM_PI_8 = 208;
	public static final short INST_TEST_1_XOR__IM8__EQ__PI_IM_8 = 209;
	public static final short INST_TEST_1_XOR__IM8__EQ__PI_PI_8 = 210;
	
	public static final short INST_TEST_1_XOR__IM8__NE__IM_IM_8 = 211;
	public static final short INST_TEST_1_XOR__IM8__NE__IM_PI_8 = 212;
	public static final short INST_TEST_1_XOR__IM8__NE__PI_IM_8 = 213;
	public static final short INST_TEST_1_XOR__IM8__NE__PI_PI_8 = 214;
	
	public static final short INST_TEST_1_XOR__IM8__LT__IM_IM_8 = 215;
	public static final short INST_TEST_1_XOR__IM8__LT__IM_PI_8 = 216;
	public static final short INST_TEST_1_XOR__IM8__LT__PI_IM_8 = 217;
	public static final short INST_TEST_1_XOR__IM8__LT__PI_PI_8 = 218;
	
	public static final short INST_TEST_1_XOR__IM8__LE__IM_IM_8 = 219;
	public static final short INST_TEST_1_XOR__IM8__LE__IM_PI_8 = 220;
	public static final short INST_TEST_1_XOR__IM8__LE__PI_IM_8 = 221;
	public static final short INST_TEST_1_XOR__IM8__LE__PI_PI_8 = 222;
	
	public static final short INST_TEST_1_XOR__IM8__GT__IM_IM_8 = 223;
	public static final short INST_TEST_1_XOR__IM8__GT__IM_PI_8 = 224;
	public static final short INST_TEST_1_XOR__IM8__GT__PI_IM_8 = 225;
	public static final short INST_TEST_1_XOR__IM8__GT__PI_PI_8 = 226;
	
	public static final short INST_TEST_1_XOR__IM8__GE__IM_IM_8 = 227;
	public static final short INST_TEST_1_XOR__IM8__GE__IM_PI_8 = 228;
	public static final short INST_TEST_1_XOR__IM8__GE__PI_IM_8 = 229;
	public static final short INST_TEST_1_XOR__IM8__GE__PI_PI_8 = 230;
	
	public static final short INST_TEST_2_SET__IM8__EQ__IM_IM_8 = 231;
	public static final short INST_TEST_2_SET__IM8__EQ__IM_PI_8 = 232;
	public static final short INST_TEST_2_SET__IM8__EQ__PI_IM_8 = 233;
	public static final short INST_TEST_2_SET__IM8__EQ__PI_PI_8 = 234;
	
	public static final short INST_TEST_2_SET__IM8__NE__IM_IM_8 = 235;
	public static final short INST_TEST_2_SET__IM8__NE__IM_PI_8 = 236;
	public static final short INST_TEST_2_SET__IM8__NE__PI_IM_8 = 237;
	public static final short INST_TEST_2_SET__IM8__NE__PI_PI_8 = 238;
	
	public static final short INST_TEST_2_SET__IM8__LT__IM_IM_8 = 239;
	public static final short INST_TEST_2_SET__IM8__LT__IM_PI_8 = 240;
	public static final short INST_TEST_2_SET__IM8__LT__PI_IM_8 = 241;
	public static final short INST_TEST_2_SET__IM8__LT__PI_PI_8 = 242;
	
	public static final short INST_TEST_2_SET__IM8__LE__IM_IM_8 = 243;
	public static final short INST_TEST_2_SET__IM8__LE__IM_PI_8 = 244;
	public static final short INST_TEST_2_SET__IM8__LE__PI_IM_8 = 245;
	public static final short INST_TEST_2_SET__IM8__LE__PI_PI_8 = 246;
	
	public static final short INST_TEST_2_SET__IM8__GT__IM_IM_8 = 247;
	public static final short INST_TEST_2_SET__IM8__GT__IM_PI_8 = 248;
	public static final short INST_TEST_2_SET__IM8__GT__PI_IM_8 = 249;
	public static final short INST_TEST_2_SET__IM8__GT__PI_PI_8 = 250;
	
	public static final short INST_TEST_2_SET__IM8__GE__IM_IM_8 = 251;
	public static final short INST_TEST_2_SET__IM8__GE__IM_PI_8 = 252;
	public static final short INST_TEST_2_SET__IM8__GE__PI_IM_8 = 253;
	public static final short INST_TEST_2_SET__IM8__GE__PI_PI_8 = 254;
	
	public static final short INST_TEST_2_AND__IM8__EQ__IM_IM_8 = 255;
	public static final short INST_TEST_2_AND__IM8__EQ__IM_PI_8 = 256;
	public static final short INST_TEST_2_AND__IM8__EQ__PI_IM_8 = 257;
	public static final short INST_TEST_2_AND__IM8__EQ__PI_PI_8 = 258;
	
	public static final short INST_TEST_2_AND__IM8__NE__IM_IM_8 = 259;
	public static final short INST_TEST_2_AND__IM8__NE__IM_PI_8 = 260;
	public static final short INST_TEST_2_AND__IM8__NE__PI_IM_8 = 261;
	public static final short INST_TEST_2_AND__IM8__NE__PI_PI_8 = 262;
	
	public static final short INST_TEST_2_AND__IM8__LT__IM_IM_8 = 263;
	public static final short INST_TEST_2_AND__IM8__LT__IM_PI_8 = 264;
	public static final short INST_TEST_2_AND__IM8__LT__PI_IM_8 = 265;
	public static final short INST_TEST_2_AND__IM8__LT__PI_PI_8 = 266;
	
	public static final short INST_TEST_2_AND__IM8__LE__IM_IM_8 = 267;
	public static final short INST_TEST_2_AND__IM8__LE__IM_PI_8 = 268;
	public static final short INST_TEST_2_AND__IM8__LE__PI_IM_8 = 269;
	public static final short INST_TEST_2_AND__IM8__LE__PI_PI_8 = 270;
	
	public static final short INST_TEST_2_AND__IM8__GT__IM_IM_8 = 271;
	public static final short INST_TEST_2_AND__IM8__GT__IM_PI_8 = 272;
	public static final short INST_TEST_2_AND__IM8__GT__PI_IM_8 = 273;
	public static final short INST_TEST_2_AND__IM8__GT__PI_PI_8 = 274;
	
	public static final short INST_TEST_2_AND__IM8__GE__IM_IM_8 = 275;
	public static final short INST_TEST_2_AND__IM8__GE__IM_PI_8 = 276;
	public static final short INST_TEST_2_AND__IM8__GE__PI_IM_8 = 277;
	public static final short INST_TEST_2_AND__IM8__GE__PI_PI_8 = 278;
	
	public static final short INST_TEST_2_OR__IM8__EQ__IM_IM_8 = 279;
	public static final short INST_TEST_2_OR__IM8__EQ__IM_PI_8 = 280;
	public static final short INST_TEST_2_OR__IM8__EQ__PI_IM_8 = 281;
	public static final short INST_TEST_2_OR__IM8__EQ__PI_PI_8 = 282;
	
	public static final short INST_TEST_2_OR__IM8__NE__IM_IM_8 = 283;
	public static final short INST_TEST_2_OR__IM8__NE__IM_PI_8 = 284;
	public static final short INST_TEST_2_OR__IM8__NE__PI_IM_8 = 285;
	public static final short INST_TEST_2_OR__IM8__NE__PI_PI_8 = 286;
	
	public static final short INST_TEST_2_OR__IM8__LT__IM_IM_8 = 287;
	public static final short INST_TEST_2_OR__IM8__LT__IM_PI_8 = 288;
	public static final short INST_TEST_2_OR__IM8__LT__PI_IM_8 = 289;
	public static final short INST_TEST_2_OR__IM8__LT__PI_PI_8 = 290;
	
	public static final short INST_TEST_2_OR__IM8__LE__IM_IM_8 = 291;
	public static final short INST_TEST_2_OR__IM8__LE__IM_PI_8 = 292;
	public static final short INST_TEST_2_OR__IM8__LE__PI_IM_8 = 293;
	public static final short INST_TEST_2_OR__IM8__LE__PI_PI_8 = 294;
	
	public static final short INST_TEST_2_OR__IM8__GT__IM_IM_8 = 295;
	public static final short INST_TEST_2_OR__IM8__GT__IM_PI_8 = 296;
	public static final short INST_TEST_2_OR__IM8__GT__PI_IM_8 = 297;
	public static final short INST_TEST_2_OR__IM8__GT__PI_PI_8 = 298;
	
	public static final short INST_TEST_2_OR__IM8__GE__IM_IM_8 = 299;
	public static final short INST_TEST_2_OR__IM8__GE__IM_PI_8 = 300;
	public static final short INST_TEST_2_OR__IM8__GE__PI_IM_8 = 301;
	public static final short INST_TEST_2_OR__IM8__GE__PI_PI_8 = 302;
	
	public static final short INST_TEST_2_XOR__IM8__EQ__IM_IM_8 = 303;
	public static final short INST_TEST_2_XOR__IM8__EQ__IM_PI_8 = 304;
	public static final short INST_TEST_2_XOR__IM8__EQ__PI_IM_8 = 305;
	public static final short INST_TEST_2_XOR__IM8__EQ__PI_PI_8 = 306;
	
	public static final short INST_TEST_2_XOR__IM8__NE__IM_IM_8 = 307;
	public static final short INST_TEST_2_XOR__IM8__NE__IM_PI_8 = 308;
	public static final short INST_TEST_2_XOR__IM8__NE__PI_IM_8 = 309;
	public static final short INST_TEST_2_XOR__IM8__NE__PI_PI_8 = 310;
	
	public static final short INST_TEST_2_XOR__IM8__LT__IM_IM_8 = 311;
	public static final short INST_TEST_2_XOR__IM8__LT__IM_PI_8 = 312;
	public static final short INST_TEST_2_XOR__IM8__LT__PI_IM_8 = 313;
	public static final short INST_TEST_2_XOR__IM8__LT__PI_PI_8 = 314;
	
	public static final short INST_TEST_2_XOR__IM8__LE__IM_IM_8 = 315;
	public static final short INST_TEST_2_XOR__IM8__LE__IM_PI_8 = 316;
	public static final short INST_TEST_2_XOR__IM8__LE__PI_IM_8 = 317;
	public static final short INST_TEST_2_XOR__IM8__LE__PI_PI_8 = 318;
	
	public static final short INST_TEST_2_XOR__IM8__GT__IM_IM_8 = 319;
	public static final short INST_TEST_2_XOR__IM8__GT__IM_PI_8 = 320;
	public static final short INST_TEST_2_XOR__IM8__GT__PI_IM_8 = 321;
	public static final short INST_TEST_2_XOR__IM8__GT__PI_PI_8 = 322;
	
	public static final short INST_TEST_2_XOR__IM8__GE__IM_IM_8 = 323;
	public static final short INST_TEST_2_XOR__IM8__GE__IM_PI_8 = 324;
	public static final short INST_TEST_2_XOR__IM8__GE__PI_IM_8 = 325;
	public static final short INST_TEST_2_XOR__IM8__GE__PI_PI_8 = 326;
	
	public static final short INST_TEST_3_SET__IM8__EQ__IM_IM_8 = 327;
	public static final short INST_TEST_3_SET__IM8__EQ__IM_PI_8 = 328;
	public static final short INST_TEST_3_SET__IM8__EQ__PI_IM_8 = 329;
	public static final short INST_TEST_3_SET__IM8__EQ__PI_PI_8 = 330;
	
	public static final short INST_TEST_3_SET__IM8__NE__IM_IM_8 = 331;
	public static final short INST_TEST_3_SET__IM8__NE__IM_PI_8 = 332;
	public static final short INST_TEST_3_SET__IM8__NE__PI_IM_8 = 333;
	public static final short INST_TEST_3_SET__IM8__NE__PI_PI_8 = 334;
	
	public static final short INST_TEST_3_SET__IM8__LT__IM_IM_8 = 335;
	public static final short INST_TEST_3_SET__IM8__LT__IM_PI_8 = 336;
	public static final short INST_TEST_3_SET__IM8__LT__PI_IM_8 = 337;
	public static final short INST_TEST_3_SET__IM8__LT__PI_PI_8 = 338;
	
	public static final short INST_TEST_3_SET__IM8__LE__IM_IM_8 = 339;
	public static final short INST_TEST_3_SET__IM8__LE__IM_PI_8 = 340;
	public static final short INST_TEST_3_SET__IM8__LE__PI_IM_8 = 341;
	public static final short INST_TEST_3_SET__IM8__LE__PI_PI_8 = 342;
	
	public static final short INST_TEST_3_SET__IM8__GT__IM_IM_8 = 343;
	public static final short INST_TEST_3_SET__IM8__GT__IM_PI_8 = 344;
	public static final short INST_TEST_3_SET__IM8__GT__PI_IM_8 = 345;
	public static final short INST_TEST_3_SET__IM8__GT__PI_PI_8 = 346;
	
	public static final short INST_TEST_3_SET__IM8__GE__IM_IM_8 = 347;
	public static final short INST_TEST_3_SET__IM8__GE__IM_PI_8 = 348;
	public static final short INST_TEST_3_SET__IM8__GE__PI_IM_8 = 349;
	public static final short INST_TEST_3_SET__IM8__GE__PI_PI_8 = 350;
	
	public static final short INST_TEST_3_AND__IM8__EQ__IM_IM_8 = 351;
	public static final short INST_TEST_3_AND__IM8__EQ__IM_PI_8 = 352;
	public static final short INST_TEST_3_AND__IM8__EQ__PI_IM_8 = 353;
	public static final short INST_TEST_3_AND__IM8__EQ__PI_PI_8 = 354;
	
	public static final short INST_TEST_3_AND__IM8__NE__IM_IM_8 = 355;
	public static final short INST_TEST_3_AND__IM8__NE__IM_PI_8 = 356;
	public static final short INST_TEST_3_AND__IM8__NE__PI_IM_8 = 357;
	public static final short INST_TEST_3_AND__IM8__NE__PI_PI_8 = 358;
	
	public static final short INST_TEST_3_AND__IM8__LT__IM_IM_8 = 359;
	public static final short INST_TEST_3_AND__IM8__LT__IM_PI_8 = 360;
	public static final short INST_TEST_3_AND__IM8__LT__PI_IM_8 = 361;
	public static final short INST_TEST_3_AND__IM8__LT__PI_PI_8 = 362;
	
	public static final short INST_TEST_3_AND__IM8__LE__IM_IM_8 = 363;
	public static final short INST_TEST_3_AND__IM8__LE__IM_PI_8 = 364;
	public static final short INST_TEST_3_AND__IM8__LE__PI_IM_8 = 365;
	public static final short INST_TEST_3_AND__IM8__LE__PI_PI_8 = 366;
	
	public static final short INST_TEST_3_AND__IM8__GT__IM_IM_8 = 367;
	public static final short INST_TEST_3_AND__IM8__GT__IM_PI_8 = 368;
	public static final short INST_TEST_3_AND__IM8__GT__PI_IM_8 = 369;
	public static final short INST_TEST_3_AND__IM8__GT__PI_PI_8 = 370;
	
	public static final short INST_TEST_3_AND__IM8__GE__IM_IM_8 = 371;
	public static final short INST_TEST_3_AND__IM8__GE__IM_PI_8 = 372;
	public static final short INST_TEST_3_AND__IM8__GE__PI_IM_8 = 373;
	public static final short INST_TEST_3_AND__IM8__GE__PI_PI_8 = 374;
	
	public static final short INST_TEST_3_OR__IM8__EQ__IM_IM_8 = 375;
	public static final short INST_TEST_3_OR__IM8__EQ__IM_PI_8 = 376;
	public static final short INST_TEST_3_OR__IM8__EQ__PI_IM_8 = 377;
	public static final short INST_TEST_3_OR__IM8__EQ__PI_PI_8 = 378;
	
	public static final short INST_TEST_3_OR__IM8__NE__IM_IM_8 = 379;
	public static final short INST_TEST_3_OR__IM8__NE__IM_PI_8 = 380;
	public static final short INST_TEST_3_OR__IM8__NE__PI_IM_8 = 381;
	public static final short INST_TEST_3_OR__IM8__NE__PI_PI_8 = 382;
	
	public static final short INST_TEST_3_OR__IM8__LT__IM_IM_8 = 383;
	public static final short INST_TEST_3_OR__IM8__LT__IM_PI_8 = 384;
	public static final short INST_TEST_3_OR__IM8__LT__PI_IM_8 = 385;
	public static final short INST_TEST_3_OR__IM8__LT__PI_PI_8 = 386;
	
	public static final short INST_TEST_3_OR__IM8__LE__IM_IM_8 = 387;
	public static final short INST_TEST_3_OR__IM8__LE__IM_PI_8 = 388;
	public static final short INST_TEST_3_OR__IM8__LE__PI_IM_8 = 389;
	public static final short INST_TEST_3_OR__IM8__LE__PI_PI_8 = 390;
	
	public static final short INST_TEST_3_OR__IM8__GT__IM_IM_8 = 391;
	public static final short INST_TEST_3_OR__IM8__GT__IM_PI_8 = 392;
	public static final short INST_TEST_3_OR__IM8__GT__PI_IM_8 = 393;
	public static final short INST_TEST_3_OR__IM8__GT__PI_PI_8 = 394;
	
	public static final short INST_TEST_3_OR__IM8__GE__IM_IM_8 = 395;
	public static final short INST_TEST_3_OR__IM8__GE__IM_PI_8 = 396;
	public static final short INST_TEST_3_OR__IM8__GE__PI_IM_8 = 397;
	public static final short INST_TEST_3_OR__IM8__GE__PI_PI_8 = 398;
	
	public static final short INST_TEST_3_XOR__IM8__EQ__IM_IM_8 = 399;
	public static final short INST_TEST_3_XOR__IM8__EQ__IM_PI_8 = 400;
	public static final short INST_TEST_3_XOR__IM8__EQ__PI_IM_8 = 401;
	public static final short INST_TEST_3_XOR__IM8__EQ__PI_PI_8 = 402;
	
	public static final short INST_TEST_3_XOR__IM8__NE__IM_IM_8 = 403;
	public static final short INST_TEST_3_XOR__IM8__NE__IM_PI_8 = 404;
	public static final short INST_TEST_3_XOR__IM8__NE__PI_IM_8 = 405;
	public static final short INST_TEST_3_XOR__IM8__NE__PI_PI_8 = 406;
	
	public static final short INST_TEST_3_XOR__IM8__LT__IM_IM_8 = 407;
	public static final short INST_TEST_3_XOR__IM8__LT__IM_PI_8 = 408;
	public static final short INST_TEST_3_XOR__IM8__LT__PI_IM_8 = 409;
	public static final short INST_TEST_3_XOR__IM8__LT__PI_PI_8 = 410;
	
	public static final short INST_TEST_3_XOR__IM8__LE__IM_IM_8 = 411;
	public static final short INST_TEST_3_XOR__IM8__LE__IM_PI_8 = 412;
	public static final short INST_TEST_3_XOR__IM8__LE__PI_IM_8 = 413;
	public static final short INST_TEST_3_XOR__IM8__LE__PI_PI_8 = 414;
	
	public static final short INST_TEST_3_XOR__IM8__GT__IM_IM_8 = 415;
	public static final short INST_TEST_3_XOR__IM8__GT__IM_PI_8 = 416;
	public static final short INST_TEST_3_XOR__IM8__GT__PI_IM_8 = 417;
	public static final short INST_TEST_3_XOR__IM8__GT__PI_PI_8 = 418;
	
	public static final short INST_TEST_3_XOR__IM8__GE__IM_IM_8 = 419;
	public static final short INST_TEST_3_XOR__IM8__GE__IM_PI_8 = 420;
	public static final short INST_TEST_3_XOR__IM8__GE__PI_IM_8 = 421;
	public static final short INST_TEST_3_XOR__IM8__GE__PI_PI_8 = 422;
	
	public static final short INST_TEST_4_SET__IM8__EQ__IM_IM_8 = 423;
	public static final short INST_TEST_4_SET__IM8__EQ__IM_PI_8 = 424;
	public static final short INST_TEST_4_SET__IM8__EQ__PI_IM_8 = 425;
	public static final short INST_TEST_4_SET__IM8__EQ__PI_PI_8 = 426;
	
	public static final short INST_TEST_4_SET__IM8__NE__IM_IM_8 = 427;
	public static final short INST_TEST_4_SET__IM8__NE__IM_PI_8 = 428;
	public static final short INST_TEST_4_SET__IM8__NE__PI_IM_8 = 429;
	public static final short INST_TEST_4_SET__IM8__NE__PI_PI_8 = 430;
	
	public static final short INST_TEST_4_SET__IM8__LT__IM_IM_8 = 431;
	public static final short INST_TEST_4_SET__IM8__LT__IM_PI_8 = 432;
	public static final short INST_TEST_4_SET__IM8__LT__PI_IM_8 = 433;
	public static final short INST_TEST_4_SET__IM8__LT__PI_PI_8 = 434;
	
	public static final short INST_TEST_4_SET__IM8__LE__IM_IM_8 = 435;
	public static final short INST_TEST_4_SET__IM8__LE__IM_PI_8 = 436;
	public static final short INST_TEST_4_SET__IM8__LE__PI_IM_8 = 437;
	public static final short INST_TEST_4_SET__IM8__LE__PI_PI_8 = 438;
	
	public static final short INST_TEST_4_SET__IM8__GT__IM_IM_8 = 439;
	public static final short INST_TEST_4_SET__IM8__GT__IM_PI_8 = 440;
	public static final short INST_TEST_4_SET__IM8__GT__PI_IM_8 = 441;
	public static final short INST_TEST_4_SET__IM8__GT__PI_PI_8 = 442;
	
	public static final short INST_TEST_4_SET__IM8__GE__IM_IM_8 = 443;
	public static final short INST_TEST_4_SET__IM8__GE__IM_PI_8 = 444;
	public static final short INST_TEST_4_SET__IM8__GE__PI_IM_8 = 445;
	public static final short INST_TEST_4_SET__IM8__GE__PI_PI_8 = 446;
	
	public static final short INST_TEST_4_AND__IM8__EQ__IM_IM_8 = 447;
	public static final short INST_TEST_4_AND__IM8__EQ__IM_PI_8 = 448;
	public static final short INST_TEST_4_AND__IM8__EQ__PI_IM_8 = 449;
	public static final short INST_TEST_4_AND__IM8__EQ__PI_PI_8 = 450;
	
	public static final short INST_TEST_4_AND__IM8__NE__IM_IM_8 = 451;
	public static final short INST_TEST_4_AND__IM8__NE__IM_PI_8 = 452;
	public static final short INST_TEST_4_AND__IM8__NE__PI_IM_8 = 453;
	public static final short INST_TEST_4_AND__IM8__NE__PI_PI_8 = 454;
	
	public static final short INST_TEST_4_AND__IM8__LT__IM_IM_8 = 455;
	public static final short INST_TEST_4_AND__IM8__LT__IM_PI_8 = 456;
	public static final short INST_TEST_4_AND__IM8__LT__PI_IM_8 = 457;
	public static final short INST_TEST_4_AND__IM8__LT__PI_PI_8 = 458;
	
	public static final short INST_TEST_4_AND__IM8__LE__IM_IM_8 = 459;
	public static final short INST_TEST_4_AND__IM8__LE__IM_PI_8 = 460;
	public static final short INST_TEST_4_AND__IM8__LE__PI_IM_8 = 461;
	public static final short INST_TEST_4_AND__IM8__LE__PI_PI_8 = 462;
	
	public static final short INST_TEST_4_AND__IM8__GT__IM_IM_8 = 463;
	public static final short INST_TEST_4_AND__IM8__GT__IM_PI_8 = 464;
	public static final short INST_TEST_4_AND__IM8__GT__PI_IM_8 = 465;
	public static final short INST_TEST_4_AND__IM8__GT__PI_PI_8 = 466;
	
	public static final short INST_TEST_4_AND__IM8__GE__IM_IM_8 = 467;
	public static final short INST_TEST_4_AND__IM8__GE__IM_PI_8 = 468;
	public static final short INST_TEST_4_AND__IM8__GE__PI_IM_8 = 469;
	public static final short INST_TEST_4_AND__IM8__GE__PI_PI_8 = 470;
	
	public static final short INST_TEST_4_OR__IM8__EQ__IM_IM_8 = 471;
	public static final short INST_TEST_4_OR__IM8__EQ__IM_PI_8 = 472;
	public static final short INST_TEST_4_OR__IM8__EQ__PI_IM_8 = 473;
	public static final short INST_TEST_4_OR__IM8__EQ__PI_PI_8 = 474;
	
	public static final short INST_TEST_4_OR__IM8__NE__IM_IM_8 = 475;
	public static final short INST_TEST_4_OR__IM8__NE__IM_PI_8 = 476;
	public static final short INST_TEST_4_OR__IM8__NE__PI_IM_8 = 477;
	public static final short INST_TEST_4_OR__IM8__NE__PI_PI_8 = 478;
	
	public static final short INST_TEST_4_OR__IM8__LT__IM_IM_8 = 479;
	public static final short INST_TEST_4_OR__IM8__LT__IM_PI_8 = 480;
	public static final short INST_TEST_4_OR__IM8__LT__PI_IM_8 = 481;
	public static final short INST_TEST_4_OR__IM8__LT__PI_PI_8 = 482;
	
	public static final short INST_TEST_4_OR__IM8__LE__IM_IM_8 = 483;
	public static final short INST_TEST_4_OR__IM8__LE__IM_PI_8 = 484;
	public static final short INST_TEST_4_OR__IM8__LE__PI_IM_8 = 485;
	public static final short INST_TEST_4_OR__IM8__LE__PI_PI_8 = 486;
	
	public static final short INST_TEST_4_OR__IM8__GT__IM_IM_8 = 487;
	public static final short INST_TEST_4_OR__IM8__GT__IM_PI_8 = 488;
	public static final short INST_TEST_4_OR__IM8__GT__PI_IM_8 = 489;
	public static final short INST_TEST_4_OR__IM8__GT__PI_PI_8 = 490;
	
	public static final short INST_TEST_4_OR__IM8__GE__IM_IM_8 = 491;
	public static final short INST_TEST_4_OR__IM8__GE__IM_PI_8 = 492;
	public static final short INST_TEST_4_OR__IM8__GE__PI_IM_8 = 493;
	public static final short INST_TEST_4_OR__IM8__GE__PI_PI_8 = 494;
	
	public static final short INST_TEST_4_XOR__IM8__EQ__IM_IM_8 = 495;
	public static final short INST_TEST_4_XOR__IM8__EQ__IM_PI_8 = 496;
	public static final short INST_TEST_4_XOR__IM8__EQ__PI_IM_8 = 497;
	public static final short INST_TEST_4_XOR__IM8__EQ__PI_PI_8 = 498;
	
	public static final short INST_TEST_4_XOR__IM8__NE__IM_IM_8 = 499;
	public static final short INST_TEST_4_XOR__IM8__NE__IM_PI_8 = 500;
	public static final short INST_TEST_4_XOR__IM8__NE__PI_IM_8 = 501;
	public static final short INST_TEST_4_XOR__IM8__NE__PI_PI_8 = 502;
	
	public static final short INST_TEST_4_XOR__IM8__LT__IM_IM_8 = 503;
	public static final short INST_TEST_4_XOR__IM8__LT__IM_PI_8 = 504;
	public static final short INST_TEST_4_XOR__IM8__LT__PI_IM_8 = 505;
	public static final short INST_TEST_4_XOR__IM8__LT__PI_PI_8 = 506;
	
	public static final short INST_TEST_4_XOR__IM8__LE__IM_IM_8 = 507;
	public static final short INST_TEST_4_XOR__IM8__LE__IM_PI_8 = 508;
	public static final short INST_TEST_4_XOR__IM8__LE__PI_IM_8 = 509;
	public static final short INST_TEST_4_XOR__IM8__LE__PI_PI_8 = 510;
	
	public static final short INST_TEST_4_XOR__IM8__GT__IM_IM_8 = 511;
	public static final short INST_TEST_4_XOR__IM8__GT__IM_PI_8 = 512;
	public static final short INST_TEST_4_XOR__IM8__GT__PI_IM_8 = 513;
	public static final short INST_TEST_4_XOR__IM8__GT__PI_PI_8 = 514;
	
	public static final short INST_TEST_4_XOR__IM8__GE__IM_IM_8 = 515;
	public static final short INST_TEST_4_XOR__IM8__GE__IM_PI_8 = 516;
	public static final short INST_TEST_4_XOR__IM8__GE__PI_IM_8 = 517;
	public static final short INST_TEST_4_XOR__IM8__GE__PI_PI_8 = 518;
	
	public static final short INST_TEST_5_SET__IM8__EQ__IM_IM_8 = 519;
	public static final short INST_TEST_5_SET__IM8__EQ__IM_PI_8 = 520;
	public static final short INST_TEST_5_SET__IM8__EQ__PI_IM_8 = 521;
	public static final short INST_TEST_5_SET__IM8__EQ__PI_PI_8 = 522;
	
	public static final short INST_TEST_5_SET__IM8__NE__IM_IM_8 = 523;
	public static final short INST_TEST_5_SET__IM8__NE__IM_PI_8 = 524;
	public static final short INST_TEST_5_SET__IM8__NE__PI_IM_8 = 525;
	public static final short INST_TEST_5_SET__IM8__NE__PI_PI_8 = 526;
	
	public static final short INST_TEST_5_SET__IM8__LT__IM_IM_8 = 527;
	public static final short INST_TEST_5_SET__IM8__LT__IM_PI_8 = 528;
	public static final short INST_TEST_5_SET__IM8__LT__PI_IM_8 = 529;
	public static final short INST_TEST_5_SET__IM8__LT__PI_PI_8 = 530;
	
	public static final short INST_TEST_5_SET__IM8__LE__IM_IM_8 = 531;
	public static final short INST_TEST_5_SET__IM8__LE__IM_PI_8 = 532;
	public static final short INST_TEST_5_SET__IM8__LE__PI_IM_8 = 533;
	public static final short INST_TEST_5_SET__IM8__LE__PI_PI_8 = 534;
	
	public static final short INST_TEST_5_SET__IM8__GT__IM_IM_8 = 535;
	public static final short INST_TEST_5_SET__IM8__GT__IM_PI_8 = 536;
	public static final short INST_TEST_5_SET__IM8__GT__PI_IM_8 = 537;
	public static final short INST_TEST_5_SET__IM8__GT__PI_PI_8 = 538;
	
	public static final short INST_TEST_5_SET__IM8__GE__IM_IM_8 = 539;
	public static final short INST_TEST_5_SET__IM8__GE__IM_PI_8 = 540;
	public static final short INST_TEST_5_SET__IM8__GE__PI_IM_8 = 541;
	public static final short INST_TEST_5_SET__IM8__GE__PI_PI_8 = 542;
	
	public static final short INST_TEST_5_AND__IM8__EQ__IM_IM_8 = 543;
	public static final short INST_TEST_5_AND__IM8__EQ__IM_PI_8 = 544;
	public static final short INST_TEST_5_AND__IM8__EQ__PI_IM_8 = 545;
	public static final short INST_TEST_5_AND__IM8__EQ__PI_PI_8 = 546;
	
	public static final short INST_TEST_5_AND__IM8__NE__IM_IM_8 = 547;
	public static final short INST_TEST_5_AND__IM8__NE__IM_PI_8 = 548;
	public static final short INST_TEST_5_AND__IM8__NE__PI_IM_8 = 549;
	public static final short INST_TEST_5_AND__IM8__NE__PI_PI_8 = 550;
	
	public static final short INST_TEST_5_AND__IM8__LT__IM_IM_8 = 551;
	public static final short INST_TEST_5_AND__IM8__LT__IM_PI_8 = 552;
	public static final short INST_TEST_5_AND__IM8__LT__PI_IM_8 = 553;
	public static final short INST_TEST_5_AND__IM8__LT__PI_PI_8 = 554;
	
	public static final short INST_TEST_5_AND__IM8__LE__IM_IM_8 = 555;
	public static final short INST_TEST_5_AND__IM8__LE__IM_PI_8 = 556;
	public static final short INST_TEST_5_AND__IM8__LE__PI_IM_8 = 557;
	public static final short INST_TEST_5_AND__IM8__LE__PI_PI_8 = 558;
	
	public static final short INST_TEST_5_AND__IM8__GT__IM_IM_8 = 559;
	public static final short INST_TEST_5_AND__IM8__GT__IM_PI_8 = 560;
	public static final short INST_TEST_5_AND__IM8__GT__PI_IM_8 = 561;
	public static final short INST_TEST_5_AND__IM8__GT__PI_PI_8 = 562;
	
	public static final short INST_TEST_5_AND__IM8__GE__IM_IM_8 = 563;
	public static final short INST_TEST_5_AND__IM8__GE__IM_PI_8 = 564;
	public static final short INST_TEST_5_AND__IM8__GE__PI_IM_8 = 565;
	public static final short INST_TEST_5_AND__IM8__GE__PI_PI_8 = 566;
	
	public static final short INST_TEST_5_OR__IM8__EQ__IM_IM_8 = 567;
	public static final short INST_TEST_5_OR__IM8__EQ__IM_PI_8 = 568;
	public static final short INST_TEST_5_OR__IM8__EQ__PI_IM_8 = 569;
	public static final short INST_TEST_5_OR__IM8__EQ__PI_PI_8 = 570;
	
	public static final short INST_TEST_5_OR__IM8__NE__IM_IM_8 = 571;
	public static final short INST_TEST_5_OR__IM8__NE__IM_PI_8 = 572;
	public static final short INST_TEST_5_OR__IM8__NE__PI_IM_8 = 573;
	public static final short INST_TEST_5_OR__IM8__NE__PI_PI_8 = 574;
	
	public static final short INST_TEST_5_OR__IM8__LT__IM_IM_8 = 575;
	public static final short INST_TEST_5_OR__IM8__LT__IM_PI_8 = 576;
	public static final short INST_TEST_5_OR__IM8__LT__PI_IM_8 = 577;
	public static final short INST_TEST_5_OR__IM8__LT__PI_PI_8 = 578;
	
	public static final short INST_TEST_5_OR__IM8__LE__IM_IM_8 = 579;
	public static final short INST_TEST_5_OR__IM8__LE__IM_PI_8 = 580;
	public static final short INST_TEST_5_OR__IM8__LE__PI_IM_8 = 581;
	public static final short INST_TEST_5_OR__IM8__LE__PI_PI_8 = 582;
	
	public static final short INST_TEST_5_OR__IM8__GT__IM_IM_8 = 583;
	public static final short INST_TEST_5_OR__IM8__GT__IM_PI_8 = 584;
	public static final short INST_TEST_5_OR__IM8__GT__PI_IM_8 = 585;
	public static final short INST_TEST_5_OR__IM8__GT__PI_PI_8 = 586;
	
	public static final short INST_TEST_5_OR__IM8__GE__IM_IM_8 = 587;
	public static final short INST_TEST_5_OR__IM8__GE__IM_PI_8 = 588;
	public static final short INST_TEST_5_OR__IM8__GE__PI_IM_8 = 589;
	public static final short INST_TEST_5_OR__IM8__GE__PI_PI_8 = 590;
	
	public static final short INST_TEST_5_XOR__IM8__EQ__IM_IM_8 = 591;
	public static final short INST_TEST_5_XOR__IM8__EQ__IM_PI_8 = 592;
	public static final short INST_TEST_5_XOR__IM8__EQ__PI_IM_8 = 593;
	public static final short INST_TEST_5_XOR__IM8__EQ__PI_PI_8 = 594;
	
	public static final short INST_TEST_5_XOR__IM8__NE__IM_IM_8 = 595;
	public static final short INST_TEST_5_XOR__IM8__NE__IM_PI_8 = 596;
	public static final short INST_TEST_5_XOR__IM8__NE__PI_IM_8 = 597;
	public static final short INST_TEST_5_XOR__IM8__NE__PI_PI_8 = 598;
	
	public static final short INST_TEST_5_XOR__IM8__LT__IM_IM_8 = 599;
	public static final short INST_TEST_5_XOR__IM8__LT__IM_PI_8 = 600;
	public static final short INST_TEST_5_XOR__IM8__LT__PI_IM_8 = 601;
	public static final short INST_TEST_5_XOR__IM8__LT__PI_PI_8 = 602;
	
	public static final short INST_TEST_5_XOR__IM8__LE__IM_IM_8 = 603;
	public static final short INST_TEST_5_XOR__IM8__LE__IM_PI_8 = 604;
	public static final short INST_TEST_5_XOR__IM8__LE__PI_IM_8 = 605;
	public static final short INST_TEST_5_XOR__IM8__LE__PI_PI_8 = 606;
	
	public static final short INST_TEST_5_XOR__IM8__GT__IM_IM_8 = 607;
	public static final short INST_TEST_5_XOR__IM8__GT__IM_PI_8 = 608;
	public static final short INST_TEST_5_XOR__IM8__GT__PI_IM_8 = 609;
	public static final short INST_TEST_5_XOR__IM8__GT__PI_PI_8 = 610;
	
	public static final short INST_TEST_5_XOR__IM8__GE__IM_IM_8 = 611;
	public static final short INST_TEST_5_XOR__IM8__GE__IM_PI_8 = 612;
	public static final short INST_TEST_5_XOR__IM8__GE__PI_IM_8 = 613;
	public static final short INST_TEST_5_XOR__IM8__GE__PI_PI_8 = 614;
	
	public static final short INST_TEST_6_SET__IM8__EQ__IM_IM_8 = 615;
	public static final short INST_TEST_6_SET__IM8__EQ__IM_PI_8 = 616;
	public static final short INST_TEST_6_SET__IM8__EQ__PI_IM_8 = 617;
	public static final short INST_TEST_6_SET__IM8__EQ__PI_PI_8 = 618;
	
	public static final short INST_TEST_6_SET__IM8__NE__IM_IM_8 = 619;
	public static final short INST_TEST_6_SET__IM8__NE__IM_PI_8 = 620;
	public static final short INST_TEST_6_SET__IM8__NE__PI_IM_8 = 621;
	public static final short INST_TEST_6_SET__IM8__NE__PI_PI_8 = 622;
	
	public static final short INST_TEST_6_SET__IM8__LT__IM_IM_8 = 623;
	public static final short INST_TEST_6_SET__IM8__LT__IM_PI_8 = 624;
	public static final short INST_TEST_6_SET__IM8__LT__PI_IM_8 = 625;
	public static final short INST_TEST_6_SET__IM8__LT__PI_PI_8 = 626;
	
	public static final short INST_TEST_6_SET__IM8__LE__IM_IM_8 = 627;
	public static final short INST_TEST_6_SET__IM8__LE__IM_PI_8 = 628;
	public static final short INST_TEST_6_SET__IM8__LE__PI_IM_8 = 629;
	public static final short INST_TEST_6_SET__IM8__LE__PI_PI_8 = 630;
	
	public static final short INST_TEST_6_SET__IM8__GT__IM_IM_8 = 631;
	public static final short INST_TEST_6_SET__IM8__GT__IM_PI_8 = 632;
	public static final short INST_TEST_6_SET__IM8__GT__PI_IM_8 = 633;
	public static final short INST_TEST_6_SET__IM8__GT__PI_PI_8 = 634;
	
	public static final short INST_TEST_6_SET__IM8__GE__IM_IM_8 = 635;
	public static final short INST_TEST_6_SET__IM8__GE__IM_PI_8 = 636;
	public static final short INST_TEST_6_SET__IM8__GE__PI_IM_8 = 637;
	public static final short INST_TEST_6_SET__IM8__GE__PI_PI_8 = 638;
	
	public static final short INST_TEST_6_AND__IM8__EQ__IM_IM_8 = 639;
	public static final short INST_TEST_6_AND__IM8__EQ__IM_PI_8 = 640;
	public static final short INST_TEST_6_AND__IM8__EQ__PI_IM_8 = 641;
	public static final short INST_TEST_6_AND__IM8__EQ__PI_PI_8 = 642;
	
	public static final short INST_TEST_6_AND__IM8__NE__IM_IM_8 = 643;
	public static final short INST_TEST_6_AND__IM8__NE__IM_PI_8 = 644;
	public static final short INST_TEST_6_AND__IM8__NE__PI_IM_8 = 645;
	public static final short INST_TEST_6_AND__IM8__NE__PI_PI_8 = 646;
	
	public static final short INST_TEST_6_AND__IM8__LT__IM_IM_8 = 647;
	public static final short INST_TEST_6_AND__IM8__LT__IM_PI_8 = 648;
	public static final short INST_TEST_6_AND__IM8__LT__PI_IM_8 = 649;
	public static final short INST_TEST_6_AND__IM8__LT__PI_PI_8 = 650;
	
	public static final short INST_TEST_6_AND__IM8__LE__IM_IM_8 = 651;
	public static final short INST_TEST_6_AND__IM8__LE__IM_PI_8 = 652;
	public static final short INST_TEST_6_AND__IM8__LE__PI_IM_8 = 653;
	public static final short INST_TEST_6_AND__IM8__LE__PI_PI_8 = 654;
	
	public static final short INST_TEST_6_AND__IM8__GT__IM_IM_8 = 655;
	public static final short INST_TEST_6_AND__IM8__GT__IM_PI_8 = 656;
	public static final short INST_TEST_6_AND__IM8__GT__PI_IM_8 = 657;
	public static final short INST_TEST_6_AND__IM8__GT__PI_PI_8 = 658;
	
	public static final short INST_TEST_6_AND__IM8__GE__IM_IM_8 = 659;
	public static final short INST_TEST_6_AND__IM8__GE__IM_PI_8 = 660;
	public static final short INST_TEST_6_AND__IM8__GE__PI_IM_8 = 661;
	public static final short INST_TEST_6_AND__IM8__GE__PI_PI_8 = 662;
	
	public static final short INST_TEST_6_OR__IM8__EQ__IM_IM_8 = 663;
	public static final short INST_TEST_6_OR__IM8__EQ__IM_PI_8 = 664;
	public static final short INST_TEST_6_OR__IM8__EQ__PI_IM_8 = 665;
	public static final short INST_TEST_6_OR__IM8__EQ__PI_PI_8 = 666;
	
	public static final short INST_TEST_6_OR__IM8__NE__IM_IM_8 = 667;
	public static final short INST_TEST_6_OR__IM8__NE__IM_PI_8 = 668;
	public static final short INST_TEST_6_OR__IM8__NE__PI_IM_8 = 669;
	public static final short INST_TEST_6_OR__IM8__NE__PI_PI_8 = 670;
	
	public static final short INST_TEST_6_OR__IM8__LT__IM_IM_8 = 671;
	public static final short INST_TEST_6_OR__IM8__LT__IM_PI_8 = 672;
	public static final short INST_TEST_6_OR__IM8__LT__PI_IM_8 = 673;
	public static final short INST_TEST_6_OR__IM8__LT__PI_PI_8 = 674;
	
	public static final short INST_TEST_6_OR__IM8__LE__IM_IM_8 = 675;
	public static final short INST_TEST_6_OR__IM8__LE__IM_PI_8 = 676;
	public static final short INST_TEST_6_OR__IM8__LE__PI_IM_8 = 677;
	public static final short INST_TEST_6_OR__IM8__LE__PI_PI_8 = 678;
	
	public static final short INST_TEST_6_OR__IM8__GT__IM_IM_8 = 679;
	public static final short INST_TEST_6_OR__IM8__GT__IM_PI_8 = 680;
	public static final short INST_TEST_6_OR__IM8__GT__PI_IM_8 = 681;
	public static final short INST_TEST_6_OR__IM8__GT__PI_PI_8 = 682;
	
	public static final short INST_TEST_6_OR__IM8__GE__IM_IM_8 = 683;
	public static final short INST_TEST_6_OR__IM8__GE__IM_PI_8 = 684;
	public static final short INST_TEST_6_OR__IM8__GE__PI_IM_8 = 685;
	public static final short INST_TEST_6_OR__IM8__GE__PI_PI_8 = 686;
	
	public static final short INST_TEST_6_XOR__IM8__EQ__IM_IM_8 = 687;
	public static final short INST_TEST_6_XOR__IM8__EQ__IM_PI_8 = 688;
	public static final short INST_TEST_6_XOR__IM8__EQ__PI_IM_8 = 689;
	public static final short INST_TEST_6_XOR__IM8__EQ__PI_PI_8 = 690;
	
	public static final short INST_TEST_6_XOR__IM8__NE__IM_IM_8 = 691;
	public static final short INST_TEST_6_XOR__IM8__NE__IM_PI_8 = 692;
	public static final short INST_TEST_6_XOR__IM8__NE__PI_IM_8 = 693;
	public static final short INST_TEST_6_XOR__IM8__NE__PI_PI_8 = 694;
	
	public static final short INST_TEST_6_XOR__IM8__LT__IM_IM_8 = 695;
	public static final short INST_TEST_6_XOR__IM8__LT__IM_PI_8 = 696;
	public static final short INST_TEST_6_XOR__IM8__LT__PI_IM_8 = 697;
	public static final short INST_TEST_6_XOR__IM8__LT__PI_PI_8 = 698;
	
	public static final short INST_TEST_6_XOR__IM8__LE__IM_IM_8 = 699;
	public static final short INST_TEST_6_XOR__IM8__LE__IM_PI_8 = 700;
	public static final short INST_TEST_6_XOR__IM8__LE__PI_IM_8 = 701;
	public static final short INST_TEST_6_XOR__IM8__LE__PI_PI_8 = 702;
	
	public static final short INST_TEST_6_XOR__IM8__GT__IM_IM_8 = 703;
	public static final short INST_TEST_6_XOR__IM8__GT__IM_PI_8 = 704;
	public static final short INST_TEST_6_XOR__IM8__GT__PI_IM_8 = 705;
	public static final short INST_TEST_6_XOR__IM8__GT__PI_PI_8 = 706;
	
	public static final short INST_TEST_6_XOR__IM8__GE__IM_IM_8 = 707;
	public static final short INST_TEST_6_XOR__IM8__GE__IM_PI_8 = 708;
	public static final short INST_TEST_6_XOR__IM8__GE__PI_IM_8 = 709;
	public static final short INST_TEST_6_XOR__IM8__GE__PI_PI_8 = 710;
	
	public static final short INST_TEST_7_SET__IM8__EQ__IM_IM_8 = 711;
	public static final short INST_TEST_7_SET__IM8__EQ__IM_PI_8 = 712;
	public static final short INST_TEST_7_SET__IM8__EQ__PI_IM_8 = 713;
	public static final short INST_TEST_7_SET__IM8__EQ__PI_PI_8 = 714;
	
	public static final short INST_TEST_7_SET__IM8__NE__IM_IM_8 = 715;
	public static final short INST_TEST_7_SET__IM8__NE__IM_PI_8 = 716;
	public static final short INST_TEST_7_SET__IM8__NE__PI_IM_8 = 717;
	public static final short INST_TEST_7_SET__IM8__NE__PI_PI_8 = 718;
	
	public static final short INST_TEST_7_SET__IM8__LT__IM_IM_8 = 719;
	public static final short INST_TEST_7_SET__IM8__LT__IM_PI_8 = 720;
	public static final short INST_TEST_7_SET__IM8__LT__PI_IM_8 = 721;
	public static final short INST_TEST_7_SET__IM8__LT__PI_PI_8 = 722;
	
	public static final short INST_TEST_7_SET__IM8__LE__IM_IM_8 = 723;
	public static final short INST_TEST_7_SET__IM8__LE__IM_PI_8 = 724;
	public static final short INST_TEST_7_SET__IM8__LE__PI_IM_8 = 725;
	public static final short INST_TEST_7_SET__IM8__LE__PI_PI_8 = 726;
	
	public static final short INST_TEST_7_SET__IM8__GT__IM_IM_8 = 727;
	public static final short INST_TEST_7_SET__IM8__GT__IM_PI_8 = 728;
	public static final short INST_TEST_7_SET__IM8__GT__PI_IM_8 = 729;
	public static final short INST_TEST_7_SET__IM8__GT__PI_PI_8 = 730;
	
	public static final short INST_TEST_7_SET__IM8__GE__IM_IM_8 = 731;
	public static final short INST_TEST_7_SET__IM8__GE__IM_PI_8 = 732;
	public static final short INST_TEST_7_SET__IM8__GE__PI_IM_8 = 733;
	public static final short INST_TEST_7_SET__IM8__GE__PI_PI_8 = 734;
	
	public static final short INST_TEST_7_AND__IM8__EQ__IM_IM_8 = 735;
	public static final short INST_TEST_7_AND__IM8__EQ__IM_PI_8 = 736;
	public static final short INST_TEST_7_AND__IM8__EQ__PI_IM_8 = 737;
	public static final short INST_TEST_7_AND__IM8__EQ__PI_PI_8 = 738;
	
	public static final short INST_TEST_7_AND__IM8__NE__IM_IM_8 = 739;
	public static final short INST_TEST_7_AND__IM8__NE__IM_PI_8 = 740;
	public static final short INST_TEST_7_AND__IM8__NE__PI_IM_8 = 741;
	public static final short INST_TEST_7_AND__IM8__NE__PI_PI_8 = 742;
	
	public static final short INST_TEST_7_AND__IM8__LT__IM_IM_8 = 743;
	public static final short INST_TEST_7_AND__IM8__LT__IM_PI_8 = 744;
	public static final short INST_TEST_7_AND__IM8__LT__PI_IM_8 = 745;
	public static final short INST_TEST_7_AND__IM8__LT__PI_PI_8 = 746;
	
	public static final short INST_TEST_7_AND__IM8__LE__IM_IM_8 = 747;
	public static final short INST_TEST_7_AND__IM8__LE__IM_PI_8 = 748;
	public static final short INST_TEST_7_AND__IM8__LE__PI_IM_8 = 749;
	public static final short INST_TEST_7_AND__IM8__LE__PI_PI_8 = 750;
	
	public static final short INST_TEST_7_AND__IM8__GT__IM_IM_8 = 751;
	public static final short INST_TEST_7_AND__IM8__GT__IM_PI_8 = 752;
	public static final short INST_TEST_7_AND__IM8__GT__PI_IM_8 = 753;
	public static final short INST_TEST_7_AND__IM8__GT__PI_PI_8 = 754;
	
	public static final short INST_TEST_7_AND__IM8__GE__IM_IM_8 = 755;
	public static final short INST_TEST_7_AND__IM8__GE__IM_PI_8 = 756;
	public static final short INST_TEST_7_AND__IM8__GE__PI_IM_8 = 757;
	public static final short INST_TEST_7_AND__IM8__GE__PI_PI_8 = 758;
	
	public static final short INST_TEST_7_OR__IM8__EQ__IM_IM_8 = 759;
	public static final short INST_TEST_7_OR__IM8__EQ__IM_PI_8 = 760;
	public static final short INST_TEST_7_OR__IM8__EQ__PI_IM_8 = 761;
	public static final short INST_TEST_7_OR__IM8__EQ__PI_PI_8 = 762;
	
	public static final short INST_TEST_7_OR__IM8__NE__IM_IM_8 = 763;
	public static final short INST_TEST_7_OR__IM8__NE__IM_PI_8 = 764;
	public static final short INST_TEST_7_OR__IM8__NE__PI_IM_8 = 765;
	public static final short INST_TEST_7_OR__IM8__NE__PI_PI_8 = 766;
	
	public static final short INST_TEST_7_OR__IM8__LT__IM_IM_8 = 767;
	public static final short INST_TEST_7_OR__IM8__LT__IM_PI_8 = 768;
	public static final short INST_TEST_7_OR__IM8__LT__PI_IM_8 = 769;
	public static final short INST_TEST_7_OR__IM8__LT__PI_PI_8 = 770;
	
	public static final short INST_TEST_7_OR__IM8__LE__IM_IM_8 = 771;
	public static final short INST_TEST_7_OR__IM8__LE__IM_PI_8 = 772;
	public static final short INST_TEST_7_OR__IM8__LE__PI_IM_8 = 773;
	public static final short INST_TEST_7_OR__IM8__LE__PI_PI_8 = 774;
	
	public static final short INST_TEST_7_OR__IM8__GT__IM_IM_8 = 775;
	public static final short INST_TEST_7_OR__IM8__GT__IM_PI_8 = 776;
	public static final short INST_TEST_7_OR__IM8__GT__PI_IM_8 = 777;
	public static final short INST_TEST_7_OR__IM8__GT__PI_PI_8 = 778;
	
	public static final short INST_TEST_7_OR__IM8__GE__IM_IM_8 = 779;
	public static final short INST_TEST_7_OR__IM8__GE__IM_PI_8 = 780;
	public static final short INST_TEST_7_OR__IM8__GE__PI_IM_8 = 781;
	public static final short INST_TEST_7_OR__IM8__GE__PI_PI_8 = 782;
	
	public static final short INST_TEST_7_XOR__IM8__EQ__IM_IM_8 = 783;
	public static final short INST_TEST_7_XOR__IM8__EQ__IM_PI_8 = 784;
	public static final short INST_TEST_7_XOR__IM8__EQ__PI_IM_8 = 785;
	public static final short INST_TEST_7_XOR__IM8__EQ__PI_PI_8 = 786;
	
	public static final short INST_TEST_7_XOR__IM8__NE__IM_IM_8 = 787;
	public static final short INST_TEST_7_XOR__IM8__NE__IM_PI_8 = 788;
	public static final short INST_TEST_7_XOR__IM8__NE__PI_IM_8 = 789;
	public static final short INST_TEST_7_XOR__IM8__NE__PI_PI_8 = 790;
	
	public static final short INST_TEST_7_XOR__IM8__LT__IM_IM_8 = 791;
	public static final short INST_TEST_7_XOR__IM8__LT__IM_PI_8 = 792;
	public static final short INST_TEST_7_XOR__IM8__LT__PI_IM_8 = 793;
	public static final short INST_TEST_7_XOR__IM8__LT__PI_PI_8 = 794;
	
	public static final short INST_TEST_7_XOR__IM8__LE__IM_IM_8 = 795;
	public static final short INST_TEST_7_XOR__IM8__LE__IM_PI_8 = 796;
	public static final short INST_TEST_7_XOR__IM8__LE__PI_IM_8 = 797;
	public static final short INST_TEST_7_XOR__IM8__LE__PI_PI_8 = 798;
	
	public static final short INST_TEST_7_XOR__IM8__GT__IM_IM_8 = 799;
	public static final short INST_TEST_7_XOR__IM8__GT__IM_PI_8 = 800;
	public static final short INST_TEST_7_XOR__IM8__GT__PI_IM_8 = 801;
	public static final short INST_TEST_7_XOR__IM8__GT__PI_PI_8 = 802;
	
	public static final short INST_TEST_7_XOR__IM8__GE__IM_IM_8 = 803;
	public static final short INST_TEST_7_XOR__IM8__GE__IM_PI_8 = 804;
	public static final short INST_TEST_7_XOR__IM8__GE__PI_IM_8 = 805;
	public static final short INST_TEST_7_XOR__IM8__GE__PI_PI_8 = 806;
	
	// TEST MEM8 EQ/LT/GT MEM8
	
	
	
	// -- instructions end --
	
	// INST SIZE
	
	public static final int INST_SIZE = Short.BYTES;
	
	// private members
	
	private byte[] instBuffer;
	private byte[] dataBuffer1;
	private byte[] dataBuffer2;
	
	private byte tempByte;
	
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
				nextDataBuffer1_ImmediateIndex();
				dataBuffer1[readDataBuffer1_Index_ImmediateIndex()] = readByte();
				break;
				
			case INST_COPY__IM8__IM_PI_8:
				nextDataBuffer1_ImmediateIndex();
				dataBuffer1[readDataBuffer1_Index_PointersIndex()] = readByte();
				break;
				
			case INST_COPY__IM8__PI_IM_8:
				nextDataBuffer1_PointersIndex();
				dataBuffer1[readDataBuffer1_Index_ImmediateIndex()] = readByte();
				break;
				
			case INST_COPY__IM8__PI_PI_8:
				nextDataBuffer1_PointersIndex();
				dataBuffer1[readDataBuffer1_Index_PointersIndex()] = readByte();
				break;
				
			// COPY MEM8 MEM8
			
			case INST_COPY__IM_IM_8__IM_IM_8:
				nextDataBuffer1_ImmediateIndex();
				nextDataBuffer2_ImmediateIndex();
				dataBuffer1[readDataBuffer1_Index_ImmediateIndex()] = dataBuffer2[readDataBuffer2_Index_ImmediateIndex()];
				break;

			case INST_COPY__IM_IM_8__IM_PI_8:
				nextDataBuffer1_ImmediateIndex();
				nextDataBuffer2_ImmediateIndex();
				dataBuffer1[readDataBuffer1_Index_PointersIndex()] = dataBuffer2[readDataBuffer2_Index_ImmediateIndex()];
				break;

			case INST_COPY__IM_IM_8__PI_IM_8:
				nextDataBuffer1_PointersIndex();
				nextDataBuffer2_ImmediateIndex();
				dataBuffer1[readDataBuffer1_Index_ImmediateIndex()] = dataBuffer2[readDataBuffer2_Index_ImmediateIndex()];
				break;

			case INST_COPY__IM_IM_8__PI_PI_8:
				nextDataBuffer1_PointersIndex();
				nextDataBuffer2_ImmediateIndex();
				dataBuffer1[readDataBuffer1_Index_PointersIndex()] = dataBuffer2[readDataBuffer2_Index_ImmediateIndex()];
				break;

			case INST_COPY__IM_PI_8__IM_IM_8:
				nextDataBuffer1_ImmediateIndex();
				nextDataBuffer2_ImmediateIndex();
				dataBuffer1[readDataBuffer1_Index_ImmediateIndex()] = dataBuffer2[readDataBuffer2_Index_PointersIndex()];
				break;

			case INST_COPY__IM_PI_8__IM_PI_8:
				nextDataBuffer1_ImmediateIndex();
				nextDataBuffer2_ImmediateIndex();
				dataBuffer1[readDataBuffer1_Index_PointersIndex()] = dataBuffer2[readDataBuffer2_Index_PointersIndex()];
				break;

			case INST_COPY__IM_PI_8__PI_IM_8:
				nextDataBuffer1_PointersIndex();
				nextDataBuffer2_ImmediateIndex();
				dataBuffer1[readDataBuffer1_Index_ImmediateIndex()] = dataBuffer2[readDataBuffer2_Index_PointersIndex()];
				break;

			case INST_COPY__IM_PI_8__PI_PI_8:
				nextDataBuffer1_PointersIndex();
				nextDataBuffer2_ImmediateIndex();
				dataBuffer1[readDataBuffer1_Index_PointersIndex()] = dataBuffer2[readDataBuffer2_Index_PointersIndex()];
				break;

			case INST_COPY__PI_IM_8__IM_IM_8:
				nextDataBuffer1_ImmediateIndex();
				nextDataBuffer2_PointersIndex();
				dataBuffer1[readDataBuffer1_Index_ImmediateIndex()] = dataBuffer2[readDataBuffer2_Index_ImmediateIndex()];
				break;

			case INST_COPY__PI_IM_8__IM_PI_8:
				nextDataBuffer1_ImmediateIndex();
				nextDataBuffer2_PointersIndex();
				dataBuffer1[readDataBuffer1_Index_PointersIndex()] = dataBuffer2[readDataBuffer2_Index_ImmediateIndex()];
				break;

			case INST_COPY__PI_IM_8__PI_IM_8:
				nextDataBuffer1_PointersIndex();
				nextDataBuffer2_PointersIndex();
				dataBuffer1[readDataBuffer1_Index_ImmediateIndex()] = dataBuffer2[readDataBuffer2_Index_ImmediateIndex()];
				break;

			case INST_COPY__PI_IM_8__PI_PI_8:
				nextDataBuffer1_PointersIndex();
				nextDataBuffer2_PointersIndex();
				dataBuffer1[readDataBuffer1_Index_PointersIndex()] = dataBuffer2[readDataBuffer2_Index_ImmediateIndex()];
				break;

			case INST_COPY__PI_PI_8__IM_IM_8:
				nextDataBuffer1_ImmediateIndex();
				nextDataBuffer2_PointersIndex();
				dataBuffer1[readDataBuffer1_Index_ImmediateIndex()] = dataBuffer2[readDataBuffer2_Index_PointersIndex()];
				break;

			case INST_COPY__PI_PI_8__IM_PI_8:
				nextDataBuffer1_ImmediateIndex();
				nextDataBuffer2_PointersIndex();
				dataBuffer1[readDataBuffer1_Index_PointersIndex()] = dataBuffer2[readDataBuffer2_Index_PointersIndex()];
				break;

			case INST_COPY__PI_PI_8__PI_IM_8:
				nextDataBuffer1_PointersIndex();
				nextDataBuffer2_PointersIndex();
				dataBuffer1[readDataBuffer1_Index_ImmediateIndex()] = dataBuffer2[readDataBuffer2_Index_PointersIndex()];
				break;

			case INST_COPY__PI_PI_8__PI_PI_8:
				nextDataBuffer1_PointersIndex();
				nextDataBuffer2_PointersIndex();
				dataBuffer1[readDataBuffer1_Index_PointersIndex()] = dataBuffer2[readDataBuffer2_Index_PointersIndex()];
				break;
				
			// SWAP MEM8 MEM8
				
			case INST_SWAP__IM_IM_8__IM_IM_8:
				nextDataBuffer1_ImmediateIndex();
				nextDataBuffer2_ImmediateIndex();
				swapDataBuffer_Indexes_1_2(readDataBuffer1_Index_ImmediateIndex(), readDataBuffer2_Index_ImmediateIndex());

			case INST_SWAP__IM_IM_8__IM_PI_8:
				nextDataBuffer1_ImmediateIndex();
				nextDataBuffer2_ImmediateIndex();
				swapDataBuffer_Indexes_1_2(readDataBuffer1_Index_ImmediateIndex(), readDataBuffer2_Index_PointersIndex());

			case INST_SWAP__IM_IM_8__PI_IM_8:
				nextDataBuffer1_ImmediateIndex();
				nextDataBuffer2_PointersIndex();
				swapDataBuffer_Indexes_1_2(readDataBuffer1_Index_ImmediateIndex(), readDataBuffer2_Index_ImmediateIndex());

			case INST_SWAP__IM_IM_8__PI_PI_8:
				nextDataBuffer1_ImmediateIndex();
				nextDataBuffer2_PointersIndex();
				swapDataBuffer_Indexes_1_2(readDataBuffer1_Index_ImmediateIndex(), readDataBuffer2_Index_PointersIndex());

			case INST_SWAP__IM_PI_8__IM_IM_8:
				nextDataBuffer1_ImmediateIndex();
				nextDataBuffer2_ImmediateIndex();
				swapDataBuffer_Indexes_1_2(readDataBuffer1_Index_PointersIndex(), readDataBuffer2_Index_ImmediateIndex());

			case INST_SWAP__IM_PI_8__IM_PI_8:
				nextDataBuffer1_ImmediateIndex();
				nextDataBuffer2_ImmediateIndex();
				swapDataBuffer_Indexes_1_2(readDataBuffer1_Index_PointersIndex(), readDataBuffer2_Index_PointersIndex());

			case INST_SWAP__IM_PI_8__PI_IM_8:
				nextDataBuffer1_ImmediateIndex();
				nextDataBuffer2_PointersIndex();
				swapDataBuffer_Indexes_1_2(readDataBuffer1_Index_PointersIndex(), readDataBuffer2_Index_ImmediateIndex());

			case INST_SWAP__IM_PI_8__PI_PI_8:
				nextDataBuffer1_ImmediateIndex();
				nextDataBuffer2_PointersIndex();
				swapDataBuffer_Indexes_1_2(readDataBuffer1_Index_PointersIndex(), readDataBuffer2_Index_PointersIndex());

			case INST_SWAP__PI_IM_8__IM_IM_8:
				nextDataBuffer1_PointersIndex();
				nextDataBuffer2_ImmediateIndex();
				swapDataBuffer_Indexes_1_2(readDataBuffer1_Index_ImmediateIndex(), readDataBuffer2_Index_ImmediateIndex());

			case INST_SWAP__PI_IM_8__IM_PI_8:
				nextDataBuffer1_PointersIndex();
				nextDataBuffer2_ImmediateIndex();
				swapDataBuffer_Indexes_1_2(readDataBuffer1_Index_ImmediateIndex(), readDataBuffer2_Index_PointersIndex());

			case INST_SWAP__PI_IM_8__PI_IM_8:
				nextDataBuffer1_PointersIndex();
				nextDataBuffer2_PointersIndex();
				swapDataBuffer_Indexes_1_2(readDataBuffer1_Index_ImmediateIndex(), readDataBuffer2_Index_ImmediateIndex());

			case INST_SWAP__PI_IM_8__PI_PI_8:
				nextDataBuffer1_PointersIndex();
				nextDataBuffer2_PointersIndex();
				swapDataBuffer_Indexes_1_2(readDataBuffer1_Index_ImmediateIndex(), readDataBuffer2_Index_PointersIndex());

			case INST_SWAP__PI_PI_8__IM_IM_8:
				nextDataBuffer1_PointersIndex();
				nextDataBuffer2_ImmediateIndex();
				swapDataBuffer_Indexes_1_2(readDataBuffer1_Index_PointersIndex(), readDataBuffer2_Index_ImmediateIndex());

			case INST_SWAP__PI_PI_8__IM_PI_8:
				nextDataBuffer1_PointersIndex();
				nextDataBuffer2_ImmediateIndex();
				swapDataBuffer_Indexes_1_2(readDataBuffer1_Index_PointersIndex(), readDataBuffer2_Index_PointersIndex());

			case INST_SWAP__PI_PI_8__PI_IM_8:
				nextDataBuffer1_PointersIndex();
				nextDataBuffer2_PointersIndex();
				swapDataBuffer_Indexes_1_2(readDataBuffer1_Index_PointersIndex(), readDataBuffer2_Index_ImmediateIndex());

			case INST_SWAP__PI_PI_8__PI_PI_8:
				nextDataBuffer1_PointersIndex();
				nextDataBuffer2_PointersIndex();
				swapDataBuffer_Indexes_1_2(readDataBuffer1_Index_PointersIndex(), readDataBuffer2_Index_PointersIndex());
				
			}
		}
		
		throw new RuntimeError(ErrorType.END_OF_INST_BUFFER, String.valueOf(ip));
	}
}
