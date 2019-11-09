package tests;

import java.io.IOException;

import tools.StreamTools;
import tools.exceptions.BaseException;
import tools.types.BufferLines;

public class ToolsTest extends BaseTest
{
	public static void TestBufferLines(String name)
	{
		try
		{
			BufferLines bufferLines = StreamTools.readBufferLines(name);
			for (int lnum = 0; lnum < bufferLines.lines.length; lnum += 1)
			{
				output.println(lnum + ": [" + bufferLines.getLineString(lnum) + "]");
			}
		}
		catch (IOException | BaseException e) 
		{
			e.printStackTrace();
		}
		
	}
}
