
package labs.faces.cman_examples;

import labs.faces.cman.tree.CFile;
import labs.faces.cman.tree.macros.CDefine;
import labs.faces.cman.tree.macros.CInclude;

public class CmanMains
{
	public static void cmanMain(String[] args)
	{
		CFile cFile = new CFile();

		cFile.statements.add(new CInclude(true, "stdio.h"));
		cFile.statements.add(new CDefine("DEFAULT_NAME", "\"machine\""));

	}
}
