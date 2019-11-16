package tools.machines;

public class StringMachine
{	
	private static byte global = 0;
	
	public enum InstCodes
	{
		MOV,
		XCHG,
		;
		
		public byte byteCode;
		
		private InstCodes()
		{
			this.byteCode = global;
			global += 1;
		}
	}
	
	public static void run(byte[] byteCodes, int start, int end)
	{
		int ip = start;
		
		while (ip < end)
		{
			switch(byteCodes[ip])
			{
				
			}
		}
	}
}
