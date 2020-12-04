package library;

import java.io.File;

public class SystemTools
{
	public static String getProgramName()
	{
		return new File(SystemTools.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
	}
}
