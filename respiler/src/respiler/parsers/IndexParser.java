package respiler.parsers;

import respiler.types.tokens.Token;
import respiler.types.tokens.TokenType;
import tools.BytesTools;
import tools.bytes.BufferUnpackedViews;

public class IndexParser
{
	private BufferUnpackedViews bufferLines;
	private int lnum;
	private int index;
	
	private boolean end;
	
	private Token token;
	
	public IndexParser(BufferUnpackedViews bufferLines)
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
			if (lnum == bufferLines.views.length)
			{
				lnum -= 1;
				end = true;
				return;
			}
			index = bufferLines.getStart(lnum);
		}
	}
	
	private void setType(TokenType type)
	{
		token.type = type;
	}
	
	private void setStartIndex()
	{
		token.start = index;
		token.startLine = lnum;
	}
	
	private void setEndIndex()
	{
		token.end = index;
		token.endLine = lnum;
	}
	
	private void setNameTypeCheckKeyword()
	{
		
	}
	
	private boolean checkSymbolOne(char ch, TokenType type)
	{
		if (getByte() == ch)
		{
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
		
		// newline
		
		if (checkSymbolOne('\n', TokenType.NEWLINE))
			return token;
		
		// keyword or name
		
		else if (BytesTools.isLower(getByte()))
		{
			setStartIndex();
			
			incIndex();
			if (end)
			{
				setEndIndex();
				
			}
		}
		
		// name
		
		else if (BytesTools.isUpper(getByte()) || getByte() == '_')
		{
			
		}
		
		return token;
	}
}
