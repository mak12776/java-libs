package jenerate.expressions;

import jenerate.interfaces.JavaExpression;
import jenerate.modifiers.JavaSymbol;

public class JavaCompare implements JavaExpression
{	
	public JavaSymbol symbol;
	
	public JavaExpression left;
	public JavaExpression right;
}
