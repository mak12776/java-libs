package cman_examples;

import cman.tree.CFile;
import cman.tree.macros.CDefine;
import cman.tree.macros.CInclude;

public class CmanMains
{
	public static void cmanMain(String[] args)
	{
		CFile cFile = new CFile();
		
		cFile.statements.add(new CInclude(true, "stdio.h"));
		
		
		cFile.statements.add(new CDefine("", ""));
	}
}
