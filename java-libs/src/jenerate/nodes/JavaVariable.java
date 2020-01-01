package jenerate.nodes;

import jenerate.nodes.expressions.JavaExpression;

public class JavaVariable
{
	public JavaAccessModifier accessModifier;
	
	public boolean isStatic;
	public boolean isFinal;
	
	public String name;
	
	public JavaExpression value;
}
