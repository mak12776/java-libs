package jenetic.ast.others;

import java.util.List;

import jenetic.ast.statements.JavaVariableDefinition;
import jenetic.interfaces.JavaNode;
import jenetic.modifiers.JavaAccessModifier;

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