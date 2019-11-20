package respiler.types.tokens;

public abstract class Token
{
	public TokenType type;
	
	public int start;
	public int end;
	
	public Token(TokenType type, int start, int end)
	{
		this.type = type;
		this.start = start;
		this.end = end;
	}
	
	public abstract void setLineInfo(int start, int end);
}
