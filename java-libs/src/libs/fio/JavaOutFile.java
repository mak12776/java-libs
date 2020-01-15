package libs.fio;

import java.io.IOException;
import java.io.OutputStream;

public class JavaOutFile extends OutFile
{
	public JavaOutFile(OutputStream stream)
	{
		super(stream);
	}
	
	public void printLeftBrace() throws IOException
	{
		print("{");
	}
	
	public void printRightBrace() throws IOException
	{
		print("}");
	} 
}
