
package labs.faces.cface.nodes.statements;

import java.util.List;

import labs.faces.cface.interfaces.CStatement;
import labs.faces.cface.interfaces.CType;
import labs.faces.cface.nodes.others.CArgument;

public class CFunction implements CStatement
{
	public boolean isStatic;
	public boolean isInline;

	public CType returnType;

	public String name;

	public List<CArgument> arguments;
	public List<CStatement> statements;
}
