package macro;

import java.io.FileInputStream;
import java.io.IOException;

import exceptions.BaseException;
import tools.BufferLines;
import tools.ByteTest;
import tools.BytesView;
import tools.StreamTools;

public class Compiler 
{
	private BufferLines bufferLines;
	private CompilerSettings settings;
	
	public Compiler(FileInputStream stream, CompilerSettings settings) throws IOException, BaseException
	{
		this.bufferLines = StreamTools.readLines(stream);
		this.settings = settings;
	}
	
	public Compiler(byte[] bytes, CompilerSettings settings)
	{
		this.bufferLines = new BufferLines(bytes, StreamTools.splitLines(bytes));
		this.settings = settings;
	}
	
	public void Compile()
	{
		int lnum;
		BytesView view;
		
		lnum = 0;
		view = bufferLines.lines[lnum];
		
		if (view.strip(bufferLines.buffer, ByteTest.isBlankClass) != 0)
		{
			if ()
			{
				
			}
		}
	}
}
