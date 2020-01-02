package cman.tree.statements;

import java.util.List;

import cman.interfaces.CStatement;
import cman.tree.others.CTypeName;

public class CFunction implements CStatement
{
	public boolean isStatic;
	public boolean isInline;
	
	public CTypeName returnType;
	
	public String name;
	
	public List<CStatement> statements;
}
