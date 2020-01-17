
package labs.faces.cface.tree.macros;

import labs.faces.cface.interfaces.CMacro;

public class CInclude implements CMacro
{
	public boolean isGlobal;
	public String name;

	public CInclude(boolean isGlobal, String name)
	{
		this.isGlobal = isGlobal;
		this.name = name;
	}
}
