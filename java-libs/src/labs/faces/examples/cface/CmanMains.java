
package labs.faces.examples.cface;

import labs.faces.cface.tree.CFile;
import labs.faces.cface.tree.macros.CDefine;
import labs.faces.cface.tree.macros.CInclude;

public class CmanMains
{
	public static void cmanMain(String[] args)
	{
		CFile cFile = new CFile();

		cFile.statements.add(new CInclude(true, "stdio.h"));
		cFile.statements.add(new CDefine("DEFAULT_NAME", "\"machine\""));

	}
}
