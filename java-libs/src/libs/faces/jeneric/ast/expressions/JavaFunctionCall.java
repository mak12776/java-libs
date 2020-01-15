
package libs.faces.jeneric.ast.expressions;

import java.util.List;

import libs.faces.jeneric.ast.JavaCompoundName;
import libs.faces.jeneric.interfaces.JavaExpression;

public class JavaFunctionCall implements JavaExpression
{
	public JavaCompoundName name;
	public List<JavaExpression> arguments;
}
