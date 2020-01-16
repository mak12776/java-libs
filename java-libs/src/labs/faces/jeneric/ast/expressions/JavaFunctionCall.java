
package labs.faces.jeneric.ast.expressions;

import java.util.List;

import labs.faces.jeneric.ast.JavaCompoundName;
import labs.faces.jeneric.interfaces.JavaExpression;

public class JavaFunctionCall implements JavaExpression
{
	public JavaCompoundName name;
	public List<JavaExpression> arguments;
}
