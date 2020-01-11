package jeneric.ast.statements;

import jeneric.interfaces.JavaExpression;
import jeneric.interfaces.JavaStatement;
import jeneric.modifiers.JavaAccessModifier;

public class JavaVariableDefinition implements JavaStatement
{
	public JavaAccessModifier accessModifier;
	
	public boolean isStatic;
	public boolean isFinal;
	
	public String name;
	
	public JavaExpression value;
}
