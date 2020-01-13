
package libs.exceptions;

public class BaseRuntimeException extends RuntimeException
{
	private static final long serialVersionUID = 5393788176772081120L;

	public BaseRuntimeException()
	{
		super();
	}

	public BaseRuntimeException(String string)
	{
		super(string);
	}
}
