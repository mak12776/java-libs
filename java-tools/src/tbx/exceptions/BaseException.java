package tbx.exceptions;

public class BaseException extends Exception
{
	private static final long serialVersionUID = 5393788176772081120L;
	
	public BaseException()
	{
		super();
	}
	
	public BaseException(String string) 
	{
		super(string);
	}
}
