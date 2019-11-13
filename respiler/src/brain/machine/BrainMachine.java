package brain.machine;

import tools.bytes.BufferUnpackedViews;
import tools.bytes.PackedView;
import tools.types.ByteTest;

public class BrainMachine
{
	public static void compileLines(BufferUnpackedViews lines)
	{
		PackedView view = new PackedView();
		int lnum = 0;
		
		while (lnum < lines.views.length)
		{
			lines.copyViewTo(lnum, view);
			
			view.strip(ByteTest.isBlank);
			
		}
	}
}
