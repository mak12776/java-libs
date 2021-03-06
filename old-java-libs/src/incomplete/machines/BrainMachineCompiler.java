
package incomplete.machines;

import labs.types.buffers.BufferView;
import labs.types.buffers.BufferViews;
import labs.types.bytes.ByteTest;

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
		BufferView view = new BufferView();
		BufferView inst = new BufferView();

		int lnum = 0;

		while (lnum < lines.views.length)
		{
			lines.copyViewTo(lnum, view);

			if (view.isEmpty())
				continue;

			while (true)
			{
				if (view.lstrip(ByteTest.Instances.isBlank) && view.isEmpty())
					break;

			}

			lnum += 1;
		}
	}
}
