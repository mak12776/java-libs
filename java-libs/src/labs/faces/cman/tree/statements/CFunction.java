
package labs.faces.cman.tree.statements;

import java.util.List;

import labs.faces.cman.interfaces.CStatement;
import labs.faces.cman.tree.others.CArgument;
import labs.faces.cman.tree.others.CSimpleType;

public class CFunction implements CStatement
{
	public boolean isStatic;
	public boolean isInline;

	public CSimpleType returnType;

	public String name;

	public List<CArgument> arguments;
	public List<CStatement> statements;
}
