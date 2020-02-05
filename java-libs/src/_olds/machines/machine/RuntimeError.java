
package _olds.machines.machine;

public class RuntimeError extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	private ErrorType type;
	private String errorMessage;

	public RuntimeError(ErrorType type)
	{
		super(type.toString());
		
		this.type = type;
		this.errorMessage = null;
	}

	public RuntimeError(ErrorType type, String errorMessage)
	{
		super(type + errorMessage);
		
		this.type = type;
		this.errorMessage = errorMessage;
	}
	
	public ErrorType getType()
	{
		return type;
	}
	
	public String getErrorMessage()
	{
		return errorMessage;
	}
	
	@Override
	public String getMessage()
	{
		return super.getMessage();
	}
}
