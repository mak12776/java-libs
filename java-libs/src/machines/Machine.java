
package machines;

public class Machine
{
	private static final boolean SAFE = false;

	private byte[][] buffers;
	private int[] pointers;

	private int bip;
	private int ip;

	public Machine(byte[][] buffers, int[] pointers, int baseInstPointer, int instPointer)
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

	private short nextShort()
	{
		return (short) (
				((instBuffer[ip++] & 0xff) << 8) | 
				(instBuffer[ip++] & 0xff)
				);
	}

	private int nextInt()
	{
		return 	((instBuffer[ip++] & 0xff) << 24) | 
				((instBuffer[ip++] & 0xff) << 16) | 
				((instBuffer[ip++] & 0xff) << 8) | 
				(instBuffer[ip++] & 0xff);
	}

	private void checkPointerIndex(final int index)
	{
		if ((index < 0) || (index >= pointers.length))
			throw new MachineRuntimeException(ErrorType.INVALID_POINTER_INDEX);
	}

	private void checkBasePointerIndex(final int index)
	{
		if ((index < 0) || (index >= buffers.length))
			throw new MachineRuntimeException(ErrorType.INVALID_BASE_POINTER_INDEX);
	}

	private int nextPointerIndex()
	{
		int index = nextInt();
		if (SAFE)
			checkPointerIndex(index);
		return index;
	}

	private int nextBasePointerIndex()
	{
		int index = nextInt();
		if (SAFE)
			checkBasePointerIndex(index);
		return index;
	}

	// instructions

	/*
	 * symbol 		size 			description 
	 * IM 			8/16/32/64 		immediate 
	 * PI 			32 				pointer index 32bit
	 * 
	 * M_PI_PI 		64 				memory at [PI:PI]
	 * M_PI_IM32 	64 				memory at [PI:IM32]	
	 * M_IM32_PI 	64				memory at [IM32:PI]
	 * M_PI_IM32 	64 				memory at [PI:IM32]
	 */

	public static final byte INST_NOOP = 0x00;

	private static final byte BASE1 = INST_NOOP;

	public static final byte INST_COPY_IM32_PI = BASE1 + 1;
	public static final byte INST_COPY_PI_PI = BASE1 + 2;

	private static final byte BASE2 = INST_COPY_PI_PI;

	public static final byte INST_COPY__IM8__M_PI_PI = BASE2 + 1;
	public static final byte INST_COPY__IM8__M_PI_IM = BASE2 + 2;
	public static final byte INST_COPY__IM8__M_IM_PI = BASE2 + 3;
	public static final byte INST_COPY__IM8__M_IM_IM = BASE2 + 4;

	public static final byte INST_COPY__M_PI_PI__M_PI_PI = BASE2 + 5;
	public static final byte INST_COPY__M_PI_PI__M_PI_IM = BASE2 + 6;

	public void run() throws MachineRuntimeException
	{
		short inst;
		byte[] instBuffer;

		if (bip >= buffers.length)
		{
			throw new MachineRuntimeException(ErrorType.INVALID_BASE_INSTRUCTION_POINTER,
					"base instruction pointer is out of range: " + bip);
		}

		instBuffer = buffers[bip];

		if (instBuffer == null)
		{
			throw new MachineRuntimeException(ErrorType.INVALID_BASE_INSTRUCTION_POINTER,
					"null buffer pointer: " + bip);
		}

		while (ip < instBuffer.length)
		{
			inst = nextByte();
			switch (inst)
			{
			case INST_NOOP:
				break;

			case INST_COPY_IM32_PI:
				pointers[nextPointerIndex()] = nextInt();
				break;

			case INST_COPY_PI_PI:
				pointers[nextPointerIndex()] = pointers[nextPointerIndex()];
				break;

			case INST_COPY__IM8__M_PI_PI:

			}
		}
	}
}
