
package libs.faces.cman.tree;

import java.util.List;

import libs.faces.cman.Cman;
import libs.faces.cman.interfaces.CMacroOrStatement;

public class CFile
{
	public List<CMacroOrStatement> statements;

	public CFile()
	{
		this.statements = Cman.newList();
	}
}
