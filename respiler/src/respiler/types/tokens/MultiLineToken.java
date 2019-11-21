package respiler.types.tokens;

public class MultiLineToken extends Token
{
	public int startLineNumber;
	public int endLineNumber;
	
	public MultiLineToken(TokenType type, int start, int end, int startLineNumber, int endLineNumber)
	{
		super(type, start, end, endLineNumber, endLineNumber);
		this.startLineNumber = startLineNumber;
		this.endLineNumber = endLineNumber;
	}
}
