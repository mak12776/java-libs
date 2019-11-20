package respiler.types.tokens;

import main.Main;

public class SingleLineToken extends Token
{
	public int lineNumber;
	
	public SingleLineToken(TokenType type, int start, int end, int lineNumber)
	{
		super(type, start, end);
		this.lineNumber = lineNumber;
	}
	
	@Override
	public void setLineInfo(int start, int end)
	{
		this.lineNumber = start;
		
		if (Main.SAFE)
		{
			if (start != end)
				throw new IllegalArgumentException("end must be equal to start.");
		}
	}
}
