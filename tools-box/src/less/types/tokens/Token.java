package less.types.tokens;

public class Token
{
	public TokenType type;
	
	public int startIndex;
	public int endIndex;
	
	public int startLine;
	public int endLine;
	
	public Token(TokenType type, int startIndex, int endIndex, int startLine, int endLine)
	{
		this.type = type;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.startLine = startLine;
		this.endLine = endLine;
	}
}
