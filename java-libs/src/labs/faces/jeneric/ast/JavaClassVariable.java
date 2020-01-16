
package libs.faces.jeneric.ast;

import libs.faces.jeneric.interfaces.JavaExpression;
import libs.faces.jeneric.modifiers.JavaAccessModifier;

public class JavaClassVariable
{
	public JavaAccessModifier accessModifier;

	public boolean isStatic;
	public boolean isFinal;

	public String name;

	public JavaExpression value;
}
