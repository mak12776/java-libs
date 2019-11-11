package macro;

import java.io.FileInputStream;
import java.io.IOException;

import tools.StreamTools;
import tools.bytes.BufferUnpackedViews;
import tools.bytes.PackedView;
import tools.bytes.UnpackedView;
import tools.exceptions.BaseException;
import tools.types.ByteTest;

public class Compiler 
{	
	public class Settings 
	{
		BufferUnpackedViews views;
		
		public Settings(
				byte[] macroPrefix, byte[] macroSuffix, 
				byte[] variablePrefix, byte[] variableSuffix, 
				byte[] evaluationPrefix, byte[] evaluationSuffix)
		{
			views = BufferUnpackedViews.from(
					macroPrefix, macroSuffix,
					variablePrefix, variableSuffix,
					evaluationPrefix, evaluationSuffix);
		}
	}
	
	private BufferUnpackedViews bufferLines;
	private Settings settings;
	
	public Compiler(FileInputStream stream, Settings settings) throws IOException, BaseException
	{
		this.bufferLines = StreamTools.readBufferLines(stream);
		this.settings = settings;
	}
	
	public boolean checkViewPrefix(UnpackedView view, UnpackedView prefix)
	{
		return (prefix != null) ? view.startsWith(bufferLines.buffer, prefix, settings.buffer) : true;
	}
	
	public boolean checkViewSuffix(UnpackedView view, UnpackedView suffix)
	{
		return (suffix != null) ? view.endsWith(bufferLines.buffer, suffix, settings.buffer) : true;
	}
	
	public boolean checkViewPrefixSuffix(UnpackedView view, UnpackedView prefix, UnpackedView Suffix)
	{
		return checkViewPrefix(view, prefix) && checkViewSuffix(view, Suffix);
	}
	
	public void Compile()
	{
		PackedView view = new PackedView();
		int lnum;
		
		lnum = 0;
		bufferLines.copyViewTo(lnum, view);
		
		
	}
}
