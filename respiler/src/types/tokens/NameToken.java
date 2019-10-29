package types.tokens;

import types.TokenType;
import types.Tools;

public class NameToken extends Token 
{
	byte[] name;
	
	public NameToken(int startLine, int startIndex, int endLine, int endIndex, byte[] name) 
	{
		super(TokenType.NAME, startLine, startIndex, endLine, endIndex);
		this.name = name;
	}
	
	public NameToken(Token token, byte[] name)
	{
		this(token.startLine, token.startIndex, token.endLine, token.endIndex, name);
	}

	@Override
	public String toString() 
	{
		StringBuilder builder = new StringBuilder();
		
		Tools.appendObjects(builder, "Token(", type, ", ");
		super.appendInfo(builder);
		Tools.appendObjects(builder, ", ", Tools.byteArrayToString(name, '"', '"'), ")");
		
		return builder.toString();
	}
}
