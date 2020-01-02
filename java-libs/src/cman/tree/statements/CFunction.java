package cman.tree.statements;

import java.util.List;

import cman.interfaces.CStatement;
import cman.tree.CTypeName;

public class CFunction implements CStatement
{
	public boolean isStatic;
	public boolean isInline;
	
	public CTypeName returnType;
	
	public String name;
	
	public List<CStatement> statements;
	
	public CFunction(boolean isStatic, boolean isInline, CTypeName returnType, String name, List<CStatement> statements)
	{
		this.name = name;
		this.isStatic = isStatic;
		this.isInline = isInline;
		this.returnType = returnType;
		this.statements = statements;
	}
}
