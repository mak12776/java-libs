
package cman.tree.statements;

import java.util.List;

import cman.interfaces.CStatement;
import cman.interfaces.CType;

public class CStruct implements CType, CStatement
{
	public String name;
	public List<CVariable> members;
}
