package respiler;

import respiler.types.tokens.Token;
import respiler.types.tokens.TokenType;
import tools.BytesTools;
import tools.bytes.BufferUnpackedViews;

public class Parser
{
	private BufferUnpackedViews bufferLines;
	
	private int lnum;
	private int index;
	
	private boolean end;
	
	private Token token;
	
	public Parser(BufferUnpackedViews bufferLines)
	{
		this.bufferLines = bufferLines;
		this.lnum = 0;
		this.index = bufferLines.getStart(lnum);
		this.end = false;
		this.token = null;
	}
	
	private byte getByte()
	{
		return bufferLines.buffer[index];
	}
	
	private void incIndex()
	{
		index += 1;
		if (index == bufferLines.getEnd(lnum))
		{
			lnum += 1;
			if (lnum == bufferLines.getViewsLength())
			{
				lnum -= 1;
				end = true;
				return;
			}
			index = bufferLines.getStart(lnum);
		}
	}
	
	private void setStartIndex()
	{
		token.startIndex = index;
	}
	
	private void setEndIndex()
	{
		token.endIndex = index;
	}
	
	private void setStartEndLine()
	{
		token.startLine = lnum;
		token.endLine = lnum;
	}
	
	private void setType(TokenType type)
	{
		token.type = type;
	}
	
	private boolean checkSingleSymbol(char ch, TokenType type)
	{
		if (getByte() == ch)
		{
			setStartEndLine();
			
			setStartIndex();
			incIndex();
			setEndIndex();
			
			setType(type);
			return true;
		}
		return false;
	}
	
	public Token nextToken()
	{
		if (end)
			return null;
		
		while (BytesTools.isBlank(getByte()))
		{
			incIndex();
			if (end)
				return null;
		}
		
		token = new Token(null, 0, 0, 0, 0);
		
		if (checkSingleSymbol('\n', TokenType.NEWLINE))
			return token;
		
		// check keyword
		
		else if (BytesTools.isLower(getByte()))
		{
			
		}
		
		return null;
	}
}
