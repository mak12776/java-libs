package macro;

import java.io.FileInputStream;
import java.io.IOException;

import tools.StreamTools;
import tools.bytes.PackedBytesView;
import tools.bytes.UnpackedBytesView;
import tools.exceptions.BaseException;
import tools.types.BufferLines;
import tools.types.ByteTest;

public class Compiler 
{
	public static void Compile(PackedBytesView lines[], CompilerSettings settings)
	{
		int lnum;
		PackedBytesView view = new PackedBytesView();
		
		lnum = 0;
		view.set(lines[lnum]);
		
		if (view.strip(ByteTest.isBlank) != 0)
		{
			
		}
	}
	
	private BufferLines bufferLines;
	private CompilerSettings settings;
	
	public Compiler(FileInputStream stream, CompilerSettings settings) throws IOException, BaseException
	{
		this.bufferLines = StreamTools.readBufferLines(stream);
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
		UnpackedBytesView view;
		int lnum;
		
		lnum = 0;
		view = bufferLines.lines[lnum];
	}
}
