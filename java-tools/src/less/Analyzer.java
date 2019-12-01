package less;

import less.exceptions.ParserException;
import less.parsers.OldParser.TokenStream;
import less.types.nodes.Node;
import less.types.tokens.olds.OldToken;

public class Analyzer 
{
	public interface NodeStream
	{
		public Node nextNode() throws ParserException;
	}
	
	public static NodeStream analyzeTokenStream(TokenStream stream)
	{
		return new NodeStream() 
		{
			private OldToken token;
			private boolean end;
			
			{
				token = null;
				end = false;
			}
			
			private void nextToken() throws ParserException
			{
				token = stream.nextToken();
				if (token == null)
					end = true;
			}
			
			@Override
			public Node nextNode() throws ParserException
			{
				nextNode();
				
				return null;
			}
		};
	}
}
