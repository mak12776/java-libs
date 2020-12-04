package library.exceptions;

public class InvalidReadNumber extends RuntimeException
{
	private static final long serialVersionUID = 7239753457380450569L;

	public InvalidReadNumber(String message)
	{
		super(message);
	}
}
