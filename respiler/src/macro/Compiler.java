package macro;

import java.io.FileInputStream;
import java.io.IOException;

import exceptions.BaseException;
import tools.ByteTest;
import tools.StreamTools;
import tools.bytes.UnpackedBytesView;
import tools.types.BufferLines;

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
	
	public boolean checkViewPrefix(UnpackedBytesView view, UnpackedBytesView prefix)
	{
		return (prefix != null) ? view.startsWith(bufferLines.buffer, prefix, settings.buffer) : true;
	}
	
	public boolean checkViewSuffix(UnpackedBytesView view, UnpackedBytesView suffix)
	{
		return (suffix != null) ? view.endsWith(bufferLines.buffer, suffix, settings.buffer) : true;
	}
	
	public boolean checkViewPrefixSuffix(UnpackedBytesView view, UnpackedBytesView prefix, UnpackedBytesView Suffix)
	{
		return checkViewPrefix(view, prefix) && checkViewSuffix(view, Suffix);
	}
	
	public void Compile()
	{
		int lnum;
		UnpackedBytesView view;
		
		lnum = 0;
		view = bufferLines.lines[lnum];
		
		if (view.strip(bufferLines.buffer, ByteTest.isBlank) != 0)
		{
			if (checkViewPrefixSuffix(view, settings.macroPrefix, settings.macroSuffix))
			{
				if (view.strip(bufferLines.buffer, ByteTest.isBlank) == 0)
				{
					view.findNot(bufferLines.buffer, ByteTest.isLetter);
				}
			}
		}
	}
}
