package cman_examples;

import cman.tree.CFile;
import cman.tree.macros.CInclude;
import cman.tree.statements.CFunction;

public class CmanMains
{
	public static void cmanMain(String[] args)
	{
		CFile cFile = new CFile();
		
		cFile.statements.add(new CInclude(true, "stdio.h"));
		
		cFile.statements.add(new CFunction());
	}
}
