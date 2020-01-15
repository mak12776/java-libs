
package cman.tree.statements.control;

import java.util.List;

import cman.interfaces.CExpression;
import cman.interfaces.CStatement;

public class CIf implements CStatement
{
	public CExpression test;
	public List<CStatement> statements;
}
