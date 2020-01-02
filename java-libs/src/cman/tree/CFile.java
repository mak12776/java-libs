package cman.tree;

import java.util.List;

import cman.Cman;
import cman.interfaces.CMacroOrStatement;

public class CFile
{
	public List<CMacroOrStatement> statements;
	
	public CFile()
	{
		this.statements = Cman.newList();
	}
}
