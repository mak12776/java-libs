
package labs.faces.jeneric.ast;

import labs.faces.jeneric.interfaces.JavaExpression;
import labs.faces.jeneric.modifiers.JavaAccessModifier;

public class JavaClassVariable
{
	public JavaAccessModifier accessModifier;

	public boolean isStatic;
	public boolean isFinal;

	public String name;

	public JavaExpression value;
}
