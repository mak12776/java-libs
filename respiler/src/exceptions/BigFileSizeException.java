package exceptions;

public class BigFileSizeException extends BaseException 
{
	private static final long serialVersionUID = 7342603766813242107L;
	
	public BigFileSizeException(String string) 
	{
		super(string);
	}
}
