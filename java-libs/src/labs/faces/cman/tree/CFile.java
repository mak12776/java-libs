
package labs.faces.cman.tree;

import java.util.List;

import labs.faces.cman.Cman;
import labs.faces.cman.interfaces.CMacroOrStatement;

public class CFile
{
	public List<CMacroOrStatement> statements;

	public CFile()
	{
		this.statements = Cman.newList();
	}
}
