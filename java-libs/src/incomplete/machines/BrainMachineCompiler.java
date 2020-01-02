package incomplete.machines;

import libs.types.ByteTest;
import libs.types.bytes.BufferViews;
import libs.types.bytes.BufferView;

public class BrainMachineCompiler
{
	private static String[] InstNames = new String[] {
		"mov",
		"xchg",
		"bswp",
	};
	
	private enum InstCodes
	{
		MOV(0), 
		XCHG(1),
		BSWP(2),
		;
		
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
				if (view.lstrip(ByteTest.Class.isBlank) && view.isEmpty())
					break;
				
				
			}
			
			lnum += 1;
		}
	}
}