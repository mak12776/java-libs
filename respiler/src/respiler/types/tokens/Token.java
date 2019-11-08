package respiler.types.tokens;

import tools.StringTools;

public class Token 
{
	public TokenType type;
	
	public int startLine;
	public int startIndex;
	
	public int endLine;
	public int endIndex;
	
	public Token(TokenType type, int startLine, int startIndex, int endLine, int endIndex)
	{
		this.type = type;
		
		this.startLine = startLine;
		this.endLine = endLine;
		
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}
	
	public void appendInfo(StringBuilder builder)
	{
		StringTools.appendObjects(builder, "[", startLine, ", ", endLine, "] [", startIndex, ", ", endIndex, "]");
	}
	
	@Override
	public String toString() 
	{
		StringBuilder builder = new StringBuilder();
		
		StringTools.appendObjects(builder, "Token(", type, ", ");
		appendInfo(builder);
		StringTools.appendObjects(builder, ")");
		
		return builder.toString();
	}
}