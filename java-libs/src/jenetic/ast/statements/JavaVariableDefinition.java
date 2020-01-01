package jenetic.ast.statements;

import jenetic.interfaces.JavaExpression;
import jenetic.interfaces.JavaStatement;
import jenetic.modifiers.JavaAccessModifier;

public class JavaVariableDefinition implements JavaStatement
{
	public JavaAccessModifier accessModifier;
	
	public boolean isStatic;
	public boolean isFinal;
	
	public String name;
	
	public JavaExpression value;
}
