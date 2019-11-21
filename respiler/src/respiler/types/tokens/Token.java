package respiler.types.tokens;

public class Token
{
	public TokenType type;
	
	public int start;
	public int end;
	
	public int startLine;
	public int endLine;
	
	public Token(TokenType type, int start, int end, int startLine, int endLine)
	{
		this.type = type;
		this.start = start;
		this.end = end;
		this.startLine = startLine;
		this.endLine = endLine;
	}
}
