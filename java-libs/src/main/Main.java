package main;

import java.io.IOException;

import jeneric.ast.mains.JavaFunction;
import jeneric.modifiers.JavaAccessModifier;

public class Main 
{
	public static void main(String[] args) throws IOException
	{
		JavaFunction function = new JavaFunction();
		
		function.accessModifier = JavaAccessModifier.PUBLIC;
		function.isStatic = false;
		function.isFinal = false;
		function.name = "appendByte";
	}
}
