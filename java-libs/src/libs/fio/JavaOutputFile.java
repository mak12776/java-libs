package libs.fio;

import java.io.IOException;
import java.io.OutputStream;

public class JavaOutputFile extends OutputFile
{
	public JavaOutputFile(OutputStream stream)
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
