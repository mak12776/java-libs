package tests;

import java.io.IOException;

import exceptions.BaseException;
import tools.StreamTools;
import tools.types.BufferLines;

public class ToolsTest
{
	public static void TestBufferLines(String name)
	{
		try
		{
			BufferLines bufferLines = StreamTools.readBufferLines(name);
			
			
		}
		catch (IOException | BaseException e) 
		{
			e.printStackTrace();
		}
		
	}
}
