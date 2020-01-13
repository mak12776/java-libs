
package incomplete.machines;

import libs.bytes.BufferViews;
import libs.bytes.ByteTest;
import libs.bytes.ByteView;

public class BrainMachineCompiler
{
	private static String[] InstNames = new String[] { "mov", "xchg", "bswp", };

	private enum InstCodes
	{
		MOV(0), XCHG(1), BSWP(2),;

		private final int index;

		private InstCodes(int index)
		{
			this.index = index;
		}
	}

	private static BufferViews InstNamesBuffer = BufferViews.from(InstNames);

	public static void compileLines(BufferViews lines)
	{
		ByteView view = new ByteView();
		ByteView inst = new ByteView();

		int lnum = 0;

		while (lnum < lines.views.length)
		{
			lines.copyViewTo(lnum, view);

			if (view.isEmpty())
				continue;

			while (true)
			{
				if (view.lstrip(ByteTest.Class.isBlank) && view.isEmpty())
					break;

			}

			lnum += 1;
		}
	}
}
