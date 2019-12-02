package less.exceptions;

import less.types.ErrorType;
import libs.tools.StringBuilderTools;
import libs.tools.StringTools;

public class ParserException extends Exception 
{
	private static final long serialVersionUID = 2340617995572042501L;
	
	public ErrorType type;
	
	public int startLine;
	public int startIndex;
	
	public int endLine;
	public int endIndex;
	
	public ParserException(ErrorType type, int startLine, int startIndex, int endLine, int endIndex)
	{
		super(StringTools.joinObject(type, ", [", startLine, ", ", endLine, "] [", startIndex, ", ", endIndex, "]"));
		
		this.type = type;
		
		this.startLine = startLine;
		this.startIndex = startIndex;
		this.endLine = endLine;
		this.endIndex = endIndex;
	}
}
