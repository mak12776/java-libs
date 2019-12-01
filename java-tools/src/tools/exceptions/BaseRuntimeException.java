package tools.exceptions;

public class BaseRuntimeException extends RuntimeException
{
	private static final long serialVersionUID = 5393788176772081120L;
	
	public BaseRuntimeException(String string) 
	{
		super(string);
	}
}

