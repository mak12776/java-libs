
package cman.tree.macros;

import cman.interfaces.CMacro;

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
