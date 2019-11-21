package respiler.types.tokens;

import main.Main;

public class SingleLineToken extends Token
{
	public int lineNumber;
	
	public SingleLineToken(TokenType type, int start, int end, int lineNumber)
	{
		super(type, start, end, lineNumber, 0);
		this.lineNumber = lineNumber;
	}
}
