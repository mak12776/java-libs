
package libs.faces.cman.tree.macros;

import libs.faces.cman.interfaces.CMacro;

public class CDefine implements CMacro
{
	public String name;
	public String value;

	public CDefine(String name, String value)
	{
		this.name = name;
		this.value = value;
	}
}
