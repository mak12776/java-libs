package tbx.exceptions;

public class EndOfBufferException extends BaseException
{
	private static final long serialVersionUID = 5907985065685915684L;

	public EndOfBufferException(String message)
	{
		super(message);
	}
	
	public EndOfBufferException()
	{
		super();
	}
}
