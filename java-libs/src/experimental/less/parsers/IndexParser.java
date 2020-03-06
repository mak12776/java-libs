
package experimental.less.parsers;

import experimental.less.types.tokens.Token;
import experimental.less.types.tokens.TokenType;
import libs.types.buffers.mutable.BufferViews;
import libs.types.bytes.ByteTest;

public class IndexParser
{
	private BufferViews bufferLines;
	private int lnum;
	private int index;

	private boolean end;

	private Token token;

	public IndexParser(BufferViews bufferLines)
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
		token.startIndex = index;
		token.startLine = lnum;
	}

	private void setEndIndex()
	{
		token.endIndex = index;
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

		while (ByteTest.isBlank(getByte()))
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

		else if (ByteTest.isLower(getByte()))
		{
			setStartIndex();

			incIndex();
			if (end)
			{
				setEndIndex();

			}
		}

		// name

		else if (ByteTest.isUpper(getByte()) || getByte() == '_')
		{

		}

		return token;
	}
}
