package cman.tree;

public class CFunction
{
	public String name;
	
	public boolean isStatic;
	public boolean isInline;
	
	public CType returnType;
	
	public CFunction(boolean isStatic, boolean isInline, CType returnType, String name)
	{
		this.name = name;
		this.isStatic = isStatic;
		this.isInline = isInline;
		this.returnType = returnType;
	}
}
