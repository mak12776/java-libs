package jenerate.nodes;

import java.util.List;

public class JavaClass
{
	public JavaAccessModifier accessModifier;
	public String name;
	
	public boolean isAbstract;
	public boolean isFinal;
	
	public List<String> implementsList;
	public List<String> extendsList;
	
	public List<JavaFunction> methods;
	public List<JavaVariable> variables;
}