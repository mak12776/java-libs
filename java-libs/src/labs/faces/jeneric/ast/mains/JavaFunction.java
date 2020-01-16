
package labs.faces.jeneric.ast.mains;

import java.util.List;

import labs.faces.jeneric.ast.JavaCompoundName;
import labs.faces.jeneric.interfaces.JavaStatement;
import labs.faces.jeneric.modifiers.JavaAccessModifier;

public class JavaFunction
{
	public JavaAccessModifier accessModifier;

	public boolean isStatic;
	public boolean isFinal;

	public String name;

	public JavaCompoundName returnType;

	public List<JavaStatement> statements;
}
