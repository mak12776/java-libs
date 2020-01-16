
package labs.faces.cman.tree.statements;

import java.util.List;

import labs.faces.cman.interfaces.CStatement;
import labs.faces.cman.interfaces.CType;

public class CStruct implements CType, CStatement
{
	public String name;
	public List<CVariable> members;
}
