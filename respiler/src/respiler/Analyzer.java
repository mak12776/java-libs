package respiler;

import respiler.Parser.TokenStream;
import respiler.exceptions.ParserException;
import respiler.types.nodes.Node;
import respiler.types.tokens.Token;

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
			private Token token;
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
