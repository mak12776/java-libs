
package incomplete.process;

import java.io.FileInputStream;
import java.io.IOException;

import incomplete.process.Compiler.Settings.SettingsKey;
import libs.exceptions.BaseException;
import libs.tools.io.StreamTools;
import libs.types.buffers.BufferView;
import libs.types.buffers.BufferViews;
import libs.types.bytes.ByteTest;

public class Compiler
{
	public static class Settings
	{
		BufferViews views;

		public Settings(byte[] macroPrefix, byte[] macroSuffix, byte[] variablePrefix, byte[] variableSuffix,
				byte[] evaluationPrefix, byte[] evaluationSuffix)
		{
			views = BufferViews.from(macroPrefix, macroSuffix, variablePrefix, variableSuffix, evaluationPrefix,
					evaluationSuffix,

					"if".getBytes(), "else".getBytes());
		}

		public enum SettingsKey
		{
			macroPrefix(0), macroSuffix(1), variablePrefix(2), variableSuffix(3), evaluationPrefix(4),
			evaluationSuffix(5),

			IF(6), ELSE(7),;

			public final int index;

			private SettingsKey(int index)
			{
				this.index = index;
			}
		}

		public void copyValueTo(SettingsKey key, BufferView view)
		{
			views.copyViewTo(key.index, view);
		}

		public BufferView copyValue(SettingsKey key)
		{
			BufferView view = new BufferView();

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
		BufferView view = new BufferView();
		BufferView prefix = new BufferView();
		BufferView suffix = new BufferView();

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
