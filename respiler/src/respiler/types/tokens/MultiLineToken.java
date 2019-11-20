package respiler.types.tokens;

public class MultiLineToken extends Token
{
	public int lineNumberStart;
	public int lineNumberEnd;
	
	public MultiLineToken(TokenType type, int start, int end, int lineNumberStart, int lineNumberEnd)
	{
		super(type, start, end);
		this.lineNumberStart = lineNumberStart;
		this.lineNumberEnd = lineNumberEnd;
	}
	
	@Override
	public void setLineInfo(int start, int end)
	{
		this.lineNumberStart = start;
		this.lineNumberEnd = end;
	}
}
