
package experimental.less.types.tokens.olds;

import experimental.less.types.tokens.TokenType;
import labs.tools.types.StringBuilderTools;

public class OldToken
{
	public TokenType type;

	public int startLine;
	public int startIndex;

	public int endLine;
	public int endIndex;

	public OldToken(TokenType type, int startLine, int startIndex, int endLine, int endIndex)
	{
		this.type = type;

		this.startLine = startLine;
		this.endLine = endLine;

		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	public void appendInfo(StringBuilder builder)
	{
		StringBuilderTools.appendObjects(builder, "[", startLine, ", ", endLine, "] [", startIndex, ", ", endIndex,
				"]");
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		StringBuilderTools.appendObjects(builder, "Token(", type, ", ");
		appendInfo(builder);
		StringBuilderTools.appendObjects(builder, ")");

		return builder.toString();
	}
}
