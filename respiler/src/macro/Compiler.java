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
	
	public boolean checkViewPrefix(BytesView view, BytesView prefix)
	{
		return (prefix != null) ? view.startsWith(bufferLines.buffer, prefix, settings.buffer) : true;
	}
	
	public boolean checkViewSuffix(BytesView view, BytesView suffix)
	{
		return (suffix != null) ? view.endsWith(bufferLines.buffer, suffix, settings.buffer) : true;
	}
	
	public boolean checkViewPrefixSuffix(BytesView view, BytesView prefix, BytesView Suffix)
	{
		return checkViewPrefix(view, prefix) && checkViewSuffix(view, Suffix);
	}
	
	public void Compile()
	{
		int lnum;
		BytesView view;
		
		lnum = 0;
		view = bufferLines.lines[lnum];
		
		if (view.strip(bufferLines.buffer, ByteTest.isBlankClass) != 0)
		{
			if (checkViewPrefixSuffix(view, settings.macroPrefix, settings.macroSuffix))
			{
				if (view.strip(bufferLines.buffer, ByteTest.isBlankClass) == 0)
				{
					view.findNot(bufferLines.buffer, ByteTest.isLetterClass);
				}
			}
		}
	}
}
