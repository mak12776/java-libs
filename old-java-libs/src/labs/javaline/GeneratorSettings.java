
package labs.javaline;

public class GeneratorSettings
{
	public String statementPrefix;
	public String statementSuffix;

	public String commentPrefix;
	public String commentSuffix;

	public GeneratorSettings(String statementPrefix, String statementSuffix, String commentPrefix, String commentSuffix)
	{
		this.statementPrefix = statementPrefix;
		this.statementSuffix = statementSuffix;
		this.commentPrefix = commentPrefix;
		this.commentSuffix = commentSuffix;
	}
}
