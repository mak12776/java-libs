package respiler;

import respiler.types.tokens.Token;
import tools.bytes.BufferUnpackedViews;
import tools.bytes.PackedView;
import tools.types.ByteTest;

public class Parser
{
	private BufferUnpackedViews bufferLines;
	private int lnum;
	private PackedView view;
	
	public Parser(BufferUnpackedViews bufferLines)
	{
		this.bufferLines = bufferLines;
		this.lnum = 0;
		this.view = new PackedView();
		
		bufferLines.copyViewTo(lnum, view);
	}
	
	public Token nextToken()
	{
		if (view.lstrip(ByteTest.isBlank) && view.isEmpty())
			return null;
		
		
		
		return null;
	}
}
