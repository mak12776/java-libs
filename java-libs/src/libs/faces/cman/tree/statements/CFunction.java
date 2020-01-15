
package libs.faces.cman.tree.statements;

import java.util.List;

import libs.faces.cman.interfaces.CStatement;
import libs.faces.cman.tree.others.CArgument;
import libs.faces.cman.tree.others.CSimpleType;

public class CFunction implements CStatement
{
	public boolean isStatic;
	public boolean isInline;

	public CSimpleType returnType;

	public String name;

	public List<CArgument> arguments;
	public List<CStatement> statements;
}
