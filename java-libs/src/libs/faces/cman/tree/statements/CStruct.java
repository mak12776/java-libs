
package libs.faces.cman.tree.statements;

import java.util.List;

import libs.faces.cman.interfaces.CStatement;
import libs.faces.cman.interfaces.CType;

public class CStruct implements CType, CStatement
{
	public String name;
	public List<CVariable> members;
}
