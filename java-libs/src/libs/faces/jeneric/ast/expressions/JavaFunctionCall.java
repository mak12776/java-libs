
package jeneric.ast.expressions;

import java.util.List;

import jeneric.ast.JavaCompoundName;
import jeneric.interfaces.JavaExpression;

public class JavaFunctionCall implements JavaExpression
{
	public JavaCompoundName name;
	public List<JavaExpression> arguments;
}
