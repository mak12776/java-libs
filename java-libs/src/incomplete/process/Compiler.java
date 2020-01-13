package incomplete.process;

import java.io.FileInputStream;
import java.io.IOException;

import incomplete.process.Compiler.Settings.SettingsKey;
import libs.bytes.BufferViews;
import libs.bytes.ByteTest;
import libs.bytes.ByteView;
import libs.exceptions.BaseException;
import libs.tools.types.StreamTools;

public class Compiler 
{
	public static class Settings
	{
		BufferViews views;
		
		public Settings(
				byte[] macroPrefix, byte[] macroSuffix, 
				byte[] variablePrefix, byte[] variableSuffix, 
				byte[] evaluationPrefix, byte[] evaluationSuffix)
		{
			views = BufferViews.from(
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
		
		public void copyValueTo(SettingsKey key, ByteView view)
		{
			views.copyViewTo(key.index, view);
		}
		
		public ByteView copyValue(SettingsKey key)
		{
			ByteView view = new ByteView();
			
			views.copyViewTo(key.index, view);
			return view;
		}
	}
	
	private BufferViews lines;
	private Settings settings;
	
	public Compiler(FileInputStream stream, Settings settings) throws IOException, BaseException
	{
		this.lines = StreamTools.readLineViews(stream);
		this.settings = settings;
	}
	
	public void Compile()
	{
		ByteView view = new ByteView();
		ByteView prefix = new ByteView();
		ByteView suffix = new ByteView();
		
		int lnum;
		
		lnum = 0;
		
		while (lnum < lines.views.length)
		{
			lines.copyViewTo(lnum, view);
			
			view.strip(ByteTest.Class.isBlank);
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
