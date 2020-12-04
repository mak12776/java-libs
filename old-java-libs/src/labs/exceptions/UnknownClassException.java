
package labs.exceptions;

public class UnknownClassException extends BaseRuntimeException
{
	private static final long serialVersionUID = -1939581071465830894L;

	public UnknownClassException(String string)
	{
		super(string);
	}
}
