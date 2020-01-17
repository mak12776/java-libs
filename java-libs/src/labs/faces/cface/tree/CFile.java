
package labs.faces.cface.tree;

import java.util.List;

import labs.faces.cface.Cman;
import labs.faces.cface.interfaces.CMacroOrStatement;

public class CFile
{
	public List<CMacroOrStatement> statements;

	public CFile()
	{
		this.statements = Cman.newList();
	}
}
