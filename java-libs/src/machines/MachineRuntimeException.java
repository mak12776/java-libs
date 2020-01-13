package machines;

public class MachineRuntimeException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	private ErrorType type;
	private String message;
	
	public MachineRuntimeException(ErrorType type)
	{
		this.type = type;
		this.message = null;
	}
	
	public MachineRuntimeException(ErrorType type, String message)
	{
		this.type = type;
		this.message = message;
	}
	
	
}
