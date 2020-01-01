package jenerate.statements;

import jenerate.interfaces.JavaExpression;
import jenerate.interfaces.JavaStatement;
import jenerate.modifiers.JavaAccessModifier;

public class JavaVariableDefinition implements JavaStatement
{
	public JavaAccessModifier accessModifier;
	
	public boolean isStatic;
	public boolean isFinal;
	
	public String name;
	
	public JavaExpression value;
}
