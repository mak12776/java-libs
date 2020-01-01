package jenetic.ast.others;

import java.util.List;

import jenetic.ast.nodes.JavaType;
import jenetic.interfaces.JavaStatement;
import jenetic.modifiers.JavaAccessModifier;

public class JavaFunction
{
	public JavaAccessModifier accessModifier;
	
	public boolean isStatic;
	public boolean isFinal;
	
	public String name;
	
	public JavaType returnType;
	
	
	public List<JavaStatement> statements;
}
