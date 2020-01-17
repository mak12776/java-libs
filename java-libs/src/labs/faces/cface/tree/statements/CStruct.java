
package labs.faces.cface.tree.statements;

import java.util.List;

import labs.faces.cface.interfaces.CStatement;
import labs.faces.cface.interfaces.CType;

public class CStruct implements CType, CStatement
{
	public String name;
	public List<CVariable> members;
}
