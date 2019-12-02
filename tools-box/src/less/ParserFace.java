package less;

public abstract class ParserFace<Token, TokenType>
{
	protected boolean end;
	
	protected abstract void incIndex();
	
	protected abstract void setStartIndex();
	protected abstract void setEndIndex();
	
	protected abstract void setStartEndLine();
	
	protected abstract void setStartLine();
	protected abstract void setEndLine();
	
	protected abstract void setType(TokenType type);
	
	public abstract Token nextToken();
}
