
package labs.faces.cface.tree.statements;

import java.util.List;

import labs.faces.cface.interfaces.CStatement;
import labs.faces.cface.tree.others.CArgument;
import labs.faces.cface.tree.others.CSimpleType;

public class CFunction implements CStatement
{
	public boolean isStatic;
	public boolean isInline;

	public CSimpleType returnType;

	public String name;

	public List<CArgument> arguments;
	public List<CStatement> statements;
}
