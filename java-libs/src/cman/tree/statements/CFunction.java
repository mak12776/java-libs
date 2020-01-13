
package cman.tree.statements;

import java.util.List;

import cman.interfaces.CStatement;
import cman.tree.others.CArgument;
import cman.tree.others.CSimpleType;

public class CFunction implements CStatement
{
	public boolean isStatic;
	public boolean isInline;

	public CSimpleType returnType;

	public String name;

	public List<CArgument> arguments;
	public List<CStatement> statements;
}
