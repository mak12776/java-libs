
package jeneric.ast;

import jeneric.interfaces.JavaExpression;
import jeneric.modifiers.JavaAccessModifier;

public class JavaClassVariable
{
	public JavaAccessModifier accessModifier;

	public boolean isStatic;
	public boolean isFinal;

	public String name;

	public JavaExpression value;
}
