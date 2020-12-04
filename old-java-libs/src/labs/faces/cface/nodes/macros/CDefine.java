
package labs.faces.cface.nodes.macros;

import labs.faces.cface.interfaces.CMacro;

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
