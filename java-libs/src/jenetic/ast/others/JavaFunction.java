package jenetic.ast.others;

import java.util.List;

import jenetic.ast.JavaCompoundName;
import jenetic.interfaces.JavaStatement;
import jenetic.modifiers.JavaAccessModifier;

public class JavaFunction
{
	public JavaAccessModifier accessModifier;
	
	public boolean isStatic;
	public boolean isFinal;
	
	public String name;
	
	public JavaCompoundName returnType;
	
	public List<JavaStatement> statements;
}
