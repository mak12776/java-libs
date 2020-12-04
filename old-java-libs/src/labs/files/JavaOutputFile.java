
package labs.files;

import java.io.IOException;
import java.io.OutputStream;

public class JavaOutputFile extends OutputFile
{
	private String tab;
	private int indent;

	public JavaOutputFile(OutputStream stream, String newline, String tab)
	{
		super(stream, newline);
		this.tab = tab;
		this.indent = 0;
	}

	public JavaOutputFile(OutputStream stream)
	{
		this(stream, "\n", "\t");
	}

	// functions

	public void printIndent(String string) throws IOException
	{
		for (int i = 0; i < indent; i += 1)
			stream.write(tab.getBytes());
		stream.write(string.getBytes());
		stream.write(newline.getBytes());
	}

	public void printIndent() throws IOException
	{
		stream.write(newline.getBytes());
	}

	public void printLeftBrace() throws IOException
	{
		print("{");
		indent += 1;
	}

	public void printRightBrace() throws IOException
	{
		indent -= 1;
		print("}");
	}
}
