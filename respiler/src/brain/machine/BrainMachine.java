package brain.machine;

import tools.bytes.BufferUnpackedViews;
import tools.bytes.BytesView;
import tools.bytes.PackedView;
import tools.types.ByteTest;

public class BrainMachine
{
	private enum InstCodes
	{	
		MOV(0)
		;
		
		public int index;
		
		private InstCodes(int index)
		{
			this.index = index;
		}
	}
	
	private static BufferUnpackedViews Instructions = BufferUnpackedViews.from(
			"mov",
			"xchg"
			); 
	
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
