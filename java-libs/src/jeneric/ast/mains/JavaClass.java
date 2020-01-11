package jeneric.ast.mains;

import java.util.List;

import jeneric.ast.statements.JavaVariableDefinition;
import jeneric.modifiers.JavaAccessModifier;

public class JavaClass
{
	public JavaAccessModifier accessModifier;
	
	public boolean isAbstract;
	public boolean isFinal;
	
	public String name;
	
	public List<String> implementsList;
	public List<String> extendsList;
	
	public List<JavaVariableDefinition> variables;
	public List<JavaFunction> methods;
}