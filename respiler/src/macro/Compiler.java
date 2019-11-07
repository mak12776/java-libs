package macro;

import java.io.FileInputStream;
import java.io.IOException;

import exceptions.BaseException;
import tools.BufferLines;
import tools.StreamTools;

public class Compiler 
{
	private BufferLines bufferLines;
	
	public Compiler(FileInputStream stream) throws IOException, BaseException
	{
		bufferLines = StreamTools.readLines(stream);
	}
	
	public Compiler(byte[] bytes)
	{
		bufferLines = new BufferLines(bytes, StreamTools.splitLines(bytes));
	}
	
	public void Compile()
	{
		int lnum;
		
		lnum = 0;
		
	}
}
