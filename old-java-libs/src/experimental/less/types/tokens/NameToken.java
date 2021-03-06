
package experimental.less.types.tokens;

import experimental.less.types.tokens.olds.OldToken;
import labs.tools.types.StringBuilderTools;
import labs.tools.types.StringTools;

public class NameToken extends OldToken
{
	byte[] name;

	public NameToken(int startLine, int startIndex, int endLine, int endIndex, byte[] name)
	{
		super(TokenType.NAME, startLine, startIndex, endLine, endIndex);
		this.name = name;
	}

	public NameToken(OldToken token, byte[] name)
	{
		this(token.startLine, token.startIndex, token.endLine, token.endIndex, name);
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		StringBuilderTools.appendObjects(builder, "Token(", type, ", ");
		super.appendInfo(builder);
		StringBuilderTools.appendObjects(builder, ", ", StringTools.byteArrayToString(name, '"', '"'), ")");

		return builder.toString();
	}
}
