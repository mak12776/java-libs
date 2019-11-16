package tools.machines;

public class UltraMachine64Bit
{	
	public static int REGISTER_SIZE = 64;
	public static int ADDRESS_SIZE = 32;
	
	public static int NUMBER_OF_REGISTERS = 32;
	
	public static final short MOV_R64_IM64 = 0x0;
	
	public static void run(byte[] buffer, int start, int end)
	{
		long[] registers = new long[NUMBER_OF_REGISTERS];
		int ip;
		
		ip = start;
		while (ip < end)
		{
			if (ip + 1 == end)
			{
				// throw run time error
			}
			
			int instCode = (buffer[ip] << 8) | buffer[ip + 1];
			ip += 2;
			
			switch (instCode)
			{
			case MOV_R64_IM64:
				
			}
		}
	}
}
