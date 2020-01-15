
package libs.faces.cman.tree.macros;

import libs.faces.cman.interfaces.CMacro;

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
