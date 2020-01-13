
package jeneric.ast.mains;

import java.util.List;

import jeneric.ast.JavaClassVariable;
import jeneric.modifiers.JavaAccessModifier;

public class JavaClass
{
	public JavaAccessModifier accessModifier = JavaAccessModifier.NONE;

	public boolean isAbstract;
	public boolean isStatic;
	public boolean isFinal;

	public String name;

	public List<String> implementsList;
	public List<String> extendsList;

	public List<JavaClassVariable> variables;
	public List<JavaFunction> methods;
}
