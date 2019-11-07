package macro;

import tools.BytesView;

public class Settings 
{
	private byte[] buffer;
	
	public BytesView macroPrefix;
	public BytesView macroSuffix;
	
	public BytesView variablePrefix;
	public BytesView variableSuffix;
	
	public BytesView evaluationPrefix;
	public BytesView evaluationSuffix;
	
	public Settings(
			byte[] macroPrefix, byte[] macroSuffix, 
			byte[] variablePrefix, byte[] variableSuffix, 
			byte[] evaluationPrefix, byte[] evaluationSuffix)
	{
		int length = 
				macroPrefix.length + macroSuffix.length + 
				variablePrefix.length + variableSuffix.length + 
				evaluationPrefix.length + evaluationSuffix.length;
		
		buffer = new byte[length];
	}
}
