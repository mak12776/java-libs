
package libs.faces.jeneric.ast.statements.control;

import java.util.List;

import libs.faces.jeneric.interfaces.JavaExpression;
import libs.faces.jeneric.interfaces.JavaStatement;

public class JavaIf
{
	public JavaExpression condition;
	public List<JavaStatement> statements;
}
