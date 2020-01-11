package jeneric.ast.mains;

import java.util.List;

import jeneric.ast.JavaCompoundName;
import jeneric.interfaces.JavaStatement;
import jeneric.modifiers.JavaAccessModifier;

public class JavaFunction
{
	public JavaAccessModifier accessModifier;
	
	public boolean isStatic;
	public boolean isFinal;
	
	public String name;
	
	public JavaCompoundName returnType;
	
	public List<JavaStatement> statements;
	
	
}
