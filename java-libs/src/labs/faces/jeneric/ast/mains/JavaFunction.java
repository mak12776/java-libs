
package libs.faces.jeneric.ast.mains;

import java.util.List;

import libs.faces.jeneric.ast.JavaCompoundName;
import libs.faces.jeneric.interfaces.JavaStatement;
import libs.faces.jeneric.modifiers.JavaAccessModifier;

public class JavaFunction
{
	public JavaAccessModifier accessModifier;

	public boolean isStatic;
	public boolean isFinal;

	public String name;

	public JavaCompoundName returnType;

	public List<JavaStatement> statements;
}
