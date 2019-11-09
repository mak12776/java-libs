package macro;

import tools.bytes.UnpackedBytesView;

public class CompilerSettings 
{
	public byte[] buffer;
	
	public UnpackedBytesView macroPrefix;
	public UnpackedBytesView macroSuffix;
	
	public UnpackedBytesView variablePrefix;
	public UnpackedBytesView variableSuffix;
	
	public UnpackedBytesView evaluationPrefix;
	public UnpackedBytesView evaluationSuffix;
	
	public CompilerSettings(
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
