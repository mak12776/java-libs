package respiler;

import respiler.exceptions.ParserException;
import respiler.parsers.OldParser.TokenStream;
import respiler.types.nodes.Node;
import respiler.types.tokens.olds.OldToken;

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
