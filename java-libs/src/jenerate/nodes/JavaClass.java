package jenerate.nodes;

import java.util.List;

import jenerate.modifiers.JavaAccessModifier;
import jenerate.nodes.statements.JavaVariableDefinition;

public class JavaClass
{
	public JavaAccessModifier accessModifier;
	public String name;
	
	public boolean isAbstract;
	public boolean isFinal;
	
	public List<String> implementsList;
	public List<String> extendsList;
	
	public List<JavaVariableDefinition> variables;
	public List<JavaFunction> methods;
}