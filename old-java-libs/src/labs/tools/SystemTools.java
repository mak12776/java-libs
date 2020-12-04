
package labs.tools;

import java.io.File;

public class SystemTools
{
	public static final String JDK_180_202 = "C:\\Program Files\\Java\\jdk1.8.0_202";

	public static void setJavaHome(String path)
	{
		System.setProperty("java.home", path);
	}
}
