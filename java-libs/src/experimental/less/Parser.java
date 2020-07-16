
package experimental.less;

import experimental.less.types.tokens.Token;
import experimental.less.types.tokens.TokenType;
import libs.types.buffers.BufferViews;
import libs.types.bytes.ByteTest;

public class Parser
{
	private BufferViews bufferLines;

	private int lnum;
	private int index;

	private boolean end;

	private Token token;

	public Parser(BufferViews bufferLines)
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

	private void incIndexWhile(ByteTest test)
	{
		while (true)
		{
			incIndex();
			if (end)
				return;

			if (!test.test(getByte()))
				return;
		}
	}

	private boolean checkTokenWhile(ByteTest test, TokenType type)
	{
		if (test.test(getByte()))
		{
			setStartEndLine();
			setStartIndex();

			incIndexWhile(test);

			setEndIndex();
			setType(type);

			return true;
		}
		return false;
	}

	private static final ByteTest isUpperUnderscore = ByteTest.Class.isUpper.or(ByteTest.isEqual('_'));

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

		// check newline

		if (checkSingleSymbol('\n', TokenType.NEWLINE))
			return token;

		// check keyword

		else if (ByteTest.isLower(getByte()))
		{
			setStartEndLine();
			setStartIndex();

			incIndexWhile(ByteTest.Class.isLower);

			setEndIndex();
		}

		// name

		// number

		else if (checkTokenWhile(ByteTest.Class.isDigit, TokenType.NUMBER))
			return token;

		//

		return null;
	}
}
