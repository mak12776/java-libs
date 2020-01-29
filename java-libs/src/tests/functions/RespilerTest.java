
package tests.functions;

import java.io.IOException;

import experimental.less.exceptions.ParserException;
import experimental.less.parsers.OldParser;
import experimental.less.parsers.OldParser.TokenStream;
import experimental.less.types.tokens.TokenType;
import experimental.less.types.tokens.olds.OldToken;
import libs.buffers.BufferViews;
import libs.exceptions.BaseException;
import libs.io.IOTools;

public class RespilerTest extends BaseTest
{
	public static String codeFileName = "code.rest";

	public static void TestAnalyzer()
	{
		BufferViews bufferLines = null;
		TokenStream stream = null;

		try
		{
			bufferLines = IOTools.readLines(codeFileName);
			stream = OldParser.parseBufferLines(bufferLines);

			int lnum = 0;

			OldToken token;
			while ((token = stream.nextToken()) != null)
			{
				while (lnum <= token.startLine || (token.type == TokenType.MULTI_LINE_COMMENT && lnum <= token.endLine))
				{
					output.println(bufferLines.getLineString(lnum));
					lnum += 1;
				}
				output.println(token);
			}
		} catch (IOException | BaseException e)
		{
			e.printStackTrace();
		} catch (ParserException e)
		{
			System.out.println((char) bufferLines.buffer[e.startIndex]);
			e.printStackTrace();
		}
	}
}
