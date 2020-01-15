
package main;

import java.io.IOException;

import libs.fio.JavaOutFile;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		String className = "Machine";
		String safeVariableName = "SAFE";
		
		JavaOutFile file = new JavaOutFile(System.out);
		
		file.print("// Hello World");
		file.print();
		file.print("public class " + className);
		file.print("{");
		file.print("	private static final boolean " + safeVariableName + " = false;");
		file.print();
		file.print("	private byte[][] buffers;");
		file.print("	private int[] pointers;");
		file.print("	private boolean test;");
		file.print();
		
		file.print("	// Hello World");
		file.print("	// I'm some one in the middle of world.");
		file.print("	// I can be any one, and any can be me.");
		
		file.print("}");
	}
}
