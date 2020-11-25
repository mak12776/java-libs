package main;

import labs.types.bytes.ByteTest;

public class FileCounter
{
	public static class Info
	{
		public long lines = 1;
		public long chars = 0;
		
		public long words = 0;
		public long digits = 0;
		
		public void count(byte[] buffer, int startIndex, int endIndex)
		{
			int index = startIndex;
			
			while (index < endIndex)
			{
				chars += 1;
				if (buffer[index] == '\n')
				{
					lines += 1;
					index += 1;
				}
				else if (buffer[index] == '\r')
				{
					lines += 1;
					
					index += 1;
					if (index == endIndex)
						return;
					
					if (buffer[index] == '\n')
						index += 1;
				}
				else if (ByteTest.isLetter(buffer[index]) || buffer[index] == '_')
				{
					words += 1;
					
					index += 1;
					if (index == endIndex)
						return;
					
					while (ByteTest.isLetter(buffer[index]) || buffer[index] == '_')
					{
						index += 1;
						if (index == endIndex)
							return;
					}
				}
				else if (ByteTest.isDigit(buffer[index]))
				{
					digits += 1;
					
					index += 1;
					while (ByteTest.isDigit(buffer[index]))
					{
						index += 1;
						if (index == endIndex)
							return;
					}
				}
				else
					index += 1;
			}
		}
	}
}
