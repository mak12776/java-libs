
package labs.faces.cface.nodes;

import java.util.List;

import labs.faces.cface.CFace;
import labs.faces.cface.interfaces.CMacroOrStatement;

public class CFile
{
	public List<CMacroOrStatement> statements;

	public CFile()
	{
		this.statements = CFace.newList();
	}
}
