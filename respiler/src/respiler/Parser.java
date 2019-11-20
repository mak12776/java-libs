package respiler;

import respiler.types.tokens.Token;
import tools.bytes.BufferUnpackedViews;

public class Parser
{
	private BufferUnpackedViews bufferLines;
	private int lnum;
	private int index;
	
	private Token token;
	
	public Parser(BufferUnpackedViews bufferLines)
	{
		this.bufferLines = bufferLines;
		this.lnum = 0;
		this.index = bufferLines.views[lnum].start;
		this.token = null;
	}
	
	public Token nextToken()
	{
		
		
		return token;
	}
}
