package incomplete.process;

import java.io.FileInputStream;
import java.io.IOException;

import incomplete.process.Compiler.Settings.SettingsKey;
import libs.exceptions.BaseException;
import libs.tools.StreamTools;
import libs.types.ByteTest;
import libs.types.bytes.BufferUnpackedViews;
import libs.types.bytes.PackedView;

public class Compiler 
{
	public static class Settings
	{
		BufferUnpackedViews views;
		
		public Settings(
				byte[] macroPrefix, byte[] macroSuffix, 
				byte[] variablePrefix, byte[] variableSuffix, 
				byte[] evaluationPrefix, byte[] evaluationSuffix)
		{
			views = BufferUnpackedViews.from(
					macroPrefix, macroSuffix,
					variablePrefix, variableSuffix,
					evaluationPrefix, evaluationSuffix,
					
					"if".getBytes(), 
					"else".getBytes()
					);
		}
		
		public static enum SettingsKey
		{	
			macroPrefix(0), macroSuffix(1),
			variablePrefix(2), variableSuffix(3),
			evaluationPrefix(4), evaluationSuffix(5),
			
			IF(6),
			ELSE(7),
			;

			public final int index;
			
			private SettingsKey(int index)
			{
				this.index = index;
			}
		}
		
		public void copyValueTo(SettingsKey key, PackedView view)
		{
			views.copyViewTo(key.index, view);
		}
		
		public PackedView copyValue(SettingsKey key)
		{
			PackedView view = new PackedView();
			
			views.copyViewTo(key.index, view);
			return view;
		}
	}
	
	private BufferUnpackedViews lines;
	private Settings settings;
	
	public Compiler(FileInputStream stream, Settings settings) throws IOException, BaseException
	{
		this.lines = StreamTools.readLineViews(stream);
		this.settings = settings;
	}
	
	public void Compile()
	{
		PackedView view = new PackedView();
		PackedView prefix = new PackedView();
		PackedView suffix = new PackedView();
		
		int lnum;
		
		lnum = 0;
		
		while (lnum < lines.views.length)
		{
			lines.copyViewTo(lnum, view);
			
			view.strip(ByteTest.isBlank);
			if (!view.isEmpty())
			{
				settings.copyValueTo(SettingsKey.macroPrefix, prefix);
				settings.copyValueTo(SettingsKey.macroSuffix, suffix);
				
				if (view.strip(prefix, suffix))
				{
					if (view.isEmpty())
					{
						
					}
					
					
				}
			}
		}
	}
}
