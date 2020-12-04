
package _olds.machines.analyzer;

public class Analyzer
{
	private byte[] instBuffer;
	private byte[] buffer;
	private int[] data;

	private int ip;
	private int bp;
	private int dp;

	private boolean test;

	// bs: buffer size
	// ds: data size

	public Analyzer(byte[] instBuffer, int dataSize, int startPointer, int initialBufferPointer, int initalDataPointer)
	{
		this.instBuffer = instBuffer;
		this.data = new int[dataSize];
		this.buffer = null;

		this.ip = startPointer;
		this.bp = initialBufferPointer;
		this.dp = initalDataPointer;
	}

	private byte nextByte()
	{
		return instBuffer[ip++];
	}

	private short nextShort()
	{
		return (short) ((instBuffer[ip++] << 16) | (instBuffer[ip++]));
	}

	private int nextInt()
	{
		return (instBuffer[ip++] << 24) | (instBuffer[ip++] << 16) | (instBuffer[ip++] << 8) | (instBuffer[ip++]);
	}

	// instruction

	public static final byte INST_NOOP = 0x00;

	private static final byte BASE1 = INST_NOOP;

	public static final byte INST_COPY_IM32_BP = BASE1 + 1;
	public static final byte INST_COPY_IM32_DP = BASE1 + 2;

	public static final byte INST_COPY_BS_BP = BASE1 + 3;
	public static final byte INST_COPY_DS_DP = BASE1 + 4;

	private static final byte BASE6 = INST_COPY_DS_DP;

	public static final byte INST_COPY_IM8_BPA = BASE6 + 1;
	public static final byte INST_COPY_IM32_DPA = BASE6 + 2;

	public static final byte INST_COPY_DPA_BP = BASE6 + 3;
	public static final byte INST_COPY_BP_DPA = BASE6 + 4;

	// unnecessary: INST_COPY_DPA_DP, INST_COPY_DP_DPA

	private static final byte BASE7 = INST_COPY_BP_DPA;

	public static final byte INST_COPY_IA_BP = BASE7 + 1;
	public static final byte INST_COPY_IA_DP = BASE7 + 2;

	public static final byte INST_COPY_BP_IA = BASE7 + 3;
	public static final byte INST_COPY_DP_IA = BASE7 + 4;

	private static final byte BASE2 = INST_COPY_DP_IA;

	public static final byte INST_TEST_BS_EQ_IM32 = BASE2 + 1;
	public static final byte INST_TEST_BS_NE_IM32 = BASE2 + 2;

	public static final byte INST_TEST_BS_LT_IM32 = BASE2 + 3;
	public static final byte INST_TEST_BS_LE_IM32 = BASE2 + 4;

	public static final byte INST_TEST_BS_GT_IM32 = BASE2 + 5;
	public static final byte INST_TEST_BS_GE_IM32 = BASE2 + 6;

	public static final byte INST_TEST_AND_BS_EQ_IM32 = BASE2 + 7;
	public static final byte INST_TEST_AND_BS_NE_IM32 = BASE2 + 8;

	public static final byte INST_TEST_AND_BS_LT_IM32 = BASE2 + 9;
	public static final byte INST_TEST_AND_BS_LE_IM32 = BASE2 + 10;

	public static final byte INST_TEST_AND_BS_GT_IM32 = BASE2 + 11;
	public static final byte INST_TEST_AND_BS_GE_IM32 = BASE2 + 12;

	public static final byte INST_TEST_OR_BS_EQ_IM32 = BASE2 + 13;
	public static final byte INST_TEST_OR_BS_NE_IM32 = BASE2 + 14;

	public static final byte INST_TEST_OR_BS_LT_IM32 = BASE2 + 15;
	public static final byte INST_TEST_OR_BS_LE_IM32 = BASE2 + 16;

	public static final byte INST_TEST_OR_BS_GT_IM32 = BASE2 + 17;
	public static final byte INST_TEST_OR_BS_GE_IM32 = BASE2 + 18;

	public static final byte INST_TEST_XOR_BS_EQ_IM32 = BASE2 + 19;
	public static final byte INST_TEST_XOR_BS_NE_IM32 = BASE2 + 20;

	public static final byte INST_TEST_XOR_BS_LT_IM32 = BASE2 + 21;
	public static final byte INST_TEST_XOR_BS_LE_IM32 = BASE2 + 22;

	public static final byte INST_TEST_XOR_BS_GT_IM32 = BASE2 + 23;
	public static final byte INST_TEST_XOR_BS_GE_IM32 = BASE2 + 24;

	private static final byte BASE3 = INST_TEST_XOR_BS_GE_IM32;

	public static final byte INST_TEST_DS_EQ_IM32 = BASE3 + 1;
	public static final byte INST_TEST_DS_NE_IM32 = BASE3 + 2;

	public static final byte INST_TEST_DS_LT_IM32 = BASE3 + 3;
	public static final byte INST_TEST_DS_LE_IM32 = BASE3 + 4;

	public static final byte INST_TEST_DS_GT_IM32 = BASE3 + 5;
	public static final byte INST_TEST_DS_GE_IM32 = BASE3 + 6;

	public static final byte INST_TEST_AND_DS_EQ_IM32 = BASE3 + 7;
	public static final byte INST_TEST_AND_DS_NE_IM32 = BASE3 + 8;

	public static final byte INST_TEST_AND_DS_LT_IM32 = BASE3 + 9;
	public static final byte INST_TEST_AND_DS_LE_IM32 = BASE3 + 10;

	public static final byte INST_TEST_AND_DS_GT_IM32 = BASE3 + 11;
	public static final byte INST_TEST_AND_DS_GE_IM32 = BASE3 + 12;

	public static final byte INST_TEST_OR_DS_EQ_IM32 = BASE3 + 13;
	public static final byte INST_TEST_OR_DS_NE_IM32 = BASE3 + 14;

	public static final byte INST_TEST_OR_DS_LT_IM32 = BASE3 + 15;
	public static final byte INST_TEST_OR_DS_LE_IM32 = BASE3 + 16;

	public static final byte INST_TEST_OR_DS_GT_IM32 = BASE3 + 17;
	public static final byte INST_TEST_OR_DS_GE_IM32 = BASE3 + 18;

	public static final byte INST_TEST_XOR_DS_EQ_IM32 = BASE3 + 19;
	public static final byte INST_TEST_XOR_DS_NE_IM32 = BASE3 + 20;

	public static final byte INST_TEST_XOR_DS_LT_IM32 = BASE3 + 21;
	public static final byte INST_TEST_XOR_DS_LE_IM32 = BASE3 + 22;

	public static final byte INST_TEST_XOR_DS_GT_IM32 = BASE3 + 23;
	public static final byte INST_TEST_XOR_DS_GE_IM32 = BASE3 + 24;

	private static final byte BASE4 = INST_TEST_XOR_DS_GE_IM32;

	public static final byte INST_JUMP_A32 = BASE4 + 1;
	public static final byte INST_TJMP_A32 = BASE4 + 2;
	public static final byte INST_FJMP_A32 = BASE4 + 3;

	private static final byte BASE5 = INST_FJMP_A32;

	public static final byte INST_EXIT = BASE5 + 1;

	public void run(byte[] bytes)
	{
		buffer = bytes;
		int jp;

		main_loop: while (ip < instBuffer.length)
		{
			switch (nextByte())
			{
			case INST_NOOP:
				break;

			case INST_EXIT:
				break main_loop;

			// copy IM32 XP

			case INST_COPY_IM32_BP:
				bp = nextInt();
				break;

			case INST_COPY_IM32_DP:
				dp = nextInt();
				break;

			// copy XS XP

			case INST_COPY_BS_BP:
				bp = buffer.length;
				break;

			case INST_COPY_DS_DP:
				dp = data.length;
				break;

			// copy IMX XPA

			case INST_COPY_IM8_BPA:
				buffer[bp] = nextByte();
				break;

			case INST_COPY_IM32_DPA:
				data[dp] = nextInt();
				break;

			// copy [DP] BP

			case INST_COPY_DPA_BP:
				bp = data[dp];
				break;

			// copy BP [DP]

			case INST_COPY_BP_DPA:
				data[dp] = bp;
				break;

			// copy IA XP

			case INST_COPY_IA_BP:
				bp = data[nextInt()];
				break;

			case INST_COPY_IA_DP:
				dp = data[nextInt()];
				break;

			// copy XP IA

			case INST_COPY_BP_IA:
				data[nextInt()] = bp;
				break;

			case INST_COPY_DP_IA:
				data[nextInt()] = dp;
				break;

			// test BS ? IM32

			case INST_TEST_BS_EQ_IM32:
				test = (buffer.length == nextInt());
				break;

			case INST_TEST_BS_NE_IM32:
				test = (buffer.length != nextInt());
				break;

			case INST_TEST_BS_LT_IM32:
				test = (buffer.length < nextInt());
				break;

			case INST_TEST_BS_LE_IM32:
				test = (buffer.length <= nextInt());
				break;

			case INST_TEST_BS_GT_IM32:
				test = (buffer.length > nextInt());
				break;

			case INST_TEST_BS_GE_IM32:
				test = (buffer.length >= nextInt());
				break;

			// test and BS ? IM32

			case INST_TEST_AND_BS_EQ_IM32:
				test &= (buffer.length == nextInt());
				break;

			case INST_TEST_AND_BS_NE_IM32:
				test &= (buffer.length != nextInt());
				break;

			case INST_TEST_AND_BS_LT_IM32:
				test &= (buffer.length < nextInt());
				break;

			case INST_TEST_AND_BS_LE_IM32:
				test &= (buffer.length <= nextInt());
				break;

			case INST_TEST_AND_BS_GT_IM32:
				test &= (buffer.length > nextInt());
				break;

			case INST_TEST_AND_BS_GE_IM32:
				test &= (buffer.length >= nextInt());
				break;

			// test or BS ? IM32

			case INST_TEST_OR_BS_EQ_IM32:
				test |= (buffer.length == nextInt());
				break;

			case INST_TEST_OR_BS_NE_IM32:
				test |= (buffer.length != nextInt());
				break;

			case INST_TEST_OR_BS_LT_IM32:
				test |= (buffer.length < nextInt());
				break;

			case INST_TEST_OR_BS_LE_IM32:
				test |= (buffer.length <= nextInt());
				break;

			case INST_TEST_OR_BS_GT_IM32:
				test |= (buffer.length > nextInt());
				break;

			case INST_TEST_OR_BS_GE_IM32:
				test |= (buffer.length >= nextInt());
				break;

			// test xor BS ? IM32

			case INST_TEST_XOR_BS_EQ_IM32:
				test ^= (buffer.length == nextInt());
				break;

			case INST_TEST_XOR_BS_NE_IM32:
				test ^= (buffer.length != nextInt());
				break;

			case INST_TEST_XOR_BS_LT_IM32:
				test ^= (buffer.length < nextInt());
				break;

			case INST_TEST_XOR_BS_LE_IM32:
				test ^= (buffer.length <= nextInt());
				break;

			case INST_TEST_XOR_BS_GT_IM32:
				test ^= (buffer.length > nextInt());
				break;

			case INST_TEST_XOR_BS_GE_IM32:
				test ^= (buffer.length >= nextInt());
				break;

			// test DS ? IM32

			case INST_TEST_DS_EQ_IM32:
				test = (data.length == nextInt());
				break;

			case INST_TEST_DS_NE_IM32:
				test = (data.length != nextInt());
				break;

			case INST_TEST_DS_LT_IM32:
				test = (data.length < nextInt());
				break;

			case INST_TEST_DS_LE_IM32:
				test = (data.length <= nextInt());
				break;

			case INST_TEST_DS_GT_IM32:
				test = (data.length > nextInt());
				break;

			case INST_TEST_DS_GE_IM32:
				test = (data.length >= nextInt());
				break;

			// test and DS ? IM32

			case INST_TEST_AND_DS_EQ_IM32:
				test &= (data.length == nextInt());
				break;

			case INST_TEST_AND_DS_NE_IM32:
				test &= (data.length != nextInt());
				break;

			case INST_TEST_AND_DS_LT_IM32:
				test &= (data.length < nextInt());
				break;

			case INST_TEST_AND_DS_LE_IM32:
				test &= (data.length <= nextInt());
				break;

			case INST_TEST_AND_DS_GT_IM32:
				test &= (data.length > nextInt());
				break;

			case INST_TEST_AND_DS_GE_IM32:
				test &= (data.length >= nextInt());
				break;

			// test or DS ? IM32

			case INST_TEST_OR_DS_EQ_IM32:
				test |= (data.length == nextInt());
				break;

			case INST_TEST_OR_DS_NE_IM32:
				test |= (data.length != nextInt());
				break;

			case INST_TEST_OR_DS_LT_IM32:
				test |= (data.length < nextInt());
				break;

			case INST_TEST_OR_DS_LE_IM32:
				test |= (data.length <= nextInt());
				break;

			case INST_TEST_OR_DS_GT_IM32:
				test |= (data.length > nextInt());
				break;

			case INST_TEST_OR_DS_GE_IM32:
				test |= (data.length >= nextInt());
				break;

			// test xor DS ? IM32

			case INST_TEST_XOR_DS_EQ_IM32:
				test ^= (data.length == nextInt());
				break;

			case INST_TEST_XOR_DS_NE_IM32:
				test ^= (data.length != nextInt());
				break;

			case INST_TEST_XOR_DS_LT_IM32:
				test ^= (data.length < nextInt());
				break;

			case INST_TEST_XOR_DS_LE_IM32:
				test ^= (data.length <= nextInt());
				break;

			case INST_TEST_XOR_DS_GT_IM32:
				test ^= (data.length > nextInt());
				break;

			case INST_TEST_XOR_DS_GE_IM32:
				test ^= (data.length >= nextInt());
				break;

			// JUMP

			case INST_JUMP_A32:
				ip = nextInt();
				break;

			case INST_TJMP_A32:
				jp = nextInt();
				if (test)
					ip = jp;
				break;

			case INST_FJMP_A32:
				jp = nextInt();
				if (!test)
					ip = jp;
				break;
			}
		}
	}
}
