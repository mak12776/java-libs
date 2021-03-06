
package experimental.less.parsers;

import java.util.Arrays;

import experimental.less.Config;
import experimental.less.exceptions.ParserException;
import experimental.less.types.ErrorType;
import experimental.less.types.tokens.NameToken;
import experimental.less.types.tokens.TokenType;
import experimental.less.types.tokens.olds.OldToken;
import labs.types.buffers.BufferViews;
import labs.types.bytes.ByteTest;

public class OldParser
{
	public interface TokenStream
	{
		OldToken nextToken() throws ParserException;
	}

	public static TokenStream parseBufferLines(BufferViews bufferLines)
	{
		return new TokenStream()
		{
			private int lnum;
			private int index;
			private boolean end;
			private OldToken token;

			{
				this.lnum = 0;
				this.index = bufferLines.views[lnum].start;
				this.token = null;
				this.end = false;
			}

			private void setStartIndex()
			{
				token.startIndex = index;
				token.startLine = lnum;
			}

			private void setEndIndex()
			{
				token.endIndex = index;
				token.endLine = lnum;
			}

			private void setType(TokenType type)
			{
				token.type = type;
			}

			private ParserException newException(ErrorType type)
			{
				return new ParserException(type, token.startIndex, token.startLine, token.endLine, token.endIndex);
			}

			private void setNameTypeCheckKeyword()
			{
				TokenType type;

				if (token.startLine == token.endLine)
				{
					type = Config.searchKeyword(bufferLines.buffer, token.startIndex,
							token.endIndex - token.startIndex);
					if (type == null)
					{
						token = new NameToken(token,
								Arrays.copyOfRange(bufferLines.buffer, token.startIndex, token.endIndex));
					} else
					{
						token.type = type;
					}
					return;
				}
				throw new RuntimeException("a name token or keyword token can be multiline.");
			}

			private void incIndex()
			{
				index += 1;
				if (index == bufferLines.views[lnum].end)
				{
					lnum += 1;
					if (lnum == bufferLines.views.length)
					{
						lnum -= 1;
						end = true;
						return;
					}
					index = bufferLines.views[lnum].start;
				}
			}

			private byte getByte()
			{
				return bufferLines.buffer[index];
			}

			@Override
			public OldToken nextToken() throws ParserException
			{
				token = new OldToken(null, 0, 0, 0, 0);

				if (end)
				{
					return null;
				}

				while (ByteTest.isBlank(getByte()))
				{
					incIndex();
					if (end)
						return null;
				}

				// newline

				if (getByte() == '\n')
				{
					setStartIndex();
					incIndex();
					setEndIndex();

					setType(TokenType.NEWLINE);
					return token;
				}

				// keyword or name

				else if (ByteTest.isLower(getByte()))
				{
					setStartIndex();

					// raw string

					if (getByte() == 'r')
					{
						// TODO write here.
						throw new RuntimeException("incomplete");
					}

					incIndex();
					if (end)
					{
						setEndIndex();
						setNameTypeCheckKeyword();
						return token;
					}

					while (ByteTest.isLower(getByte()))
					{
						incIndex();
						if (end)
						{
							setEndIndex();
							setNameTypeCheckKeyword();
							return token;
						}
					}

					if (ByteTest.isUpper(getByte()) || ByteTest.isDigit(getByte()) || (getByte() == '_'))
					{
						incIndex();
						if (end)
						{
							setEndIndex();

							setType(TokenType.NAME);
							return token;
						}

						while (ByteTest.isLetter(getByte()) || ByteTest.isDigit(getByte()) || (getByte() == '_'))
						{
							incIndex();
							if (end)
								break;
						}

						setEndIndex();

						setType(TokenType.NAME);
						return token;
					}

					setEndIndex();

					setNameTypeCheckKeyword();
					return token;
				}

				// name

				else if (ByteTest.isUpper(getByte()) || (getByte() == '_'))
				{
					setStartIndex();

					incIndex();
					if (end)
					{
						setEndIndex();

						setType(TokenType.NAME);
						return token;
					}

					while (ByteTest.isLetter(getByte()) || ByteTest.isDigit(getByte()) || (getByte() == '_'))
					{
						incIndex();
						if (end)
							break;
					}

					setEndIndex();

					setType(TokenType.NAME);
					return token;
				}

				// string

				else if (getByte() == '"')
				{
					// TODO write here.
					throw new RuntimeException("incomplete");
				}

				// number

				else if (ByteTest.isDigit(getByte()))
				{
					setStartIndex();

					incIndex();
					if (end)
					{
						setEndIndex();

						setType(TokenType.NUMBER);
						return token;
					}

					while (ByteTest.isDigit(getByte()))
					{
						incIndex();
						if (end)
						{
							break;
						}
					}

					setEndIndex();

					setType(TokenType.NUMBER);
					return token;
				}

				// comment & next line

				else if (getByte() == '\\')
				{
					setStartIndex();

					incIndex();
					if (!end)
					{
						// multi-line comment

						if (getByte() == '\\')
						{
							incIndex();

							while (!end)
							{
								if (getByte() == '\\')
								{
									incIndex();

									if (end)
										break;

									if (getByte() == '\\')
									{
										incIndex();
										setEndIndex();

										setType(TokenType.MULTI_LINE_COMMENT);
										return token;
									}
								} else
								{
									incIndex();
								}
							}

							setEndIndex();
							throw newException(ErrorType.UNTERMINATED_MULTI_LINE_COMMENT);
						}

						// next line

						else if (getByte() == '-')
						{
							incIndex();
							setEndIndex();

							setType(TokenType.NEXT_LINE);
							return token;
						}
					}

					setEndIndex();
					throw newException(ErrorType.UNKNOWN_SYMBOL);
				}

				// symbols group 1

				else if (checkSymbolOne(',', TokenType.COMMA))
					return token;

				else if (checkSymbolOne('.', TokenType.DOT))
					return token;

				else if (checkSymbolOne('(', TokenType.L_PAR))
					return token;

				else if (checkSymbolOne(')', TokenType.R_PAR))
					return token;

				else if (checkSymbolOne('{', TokenType.L_BCE))
					return token;

				else if (checkSymbolOne('}', TokenType.R_BCE))
					return token;

				else if (checkSymbolOne('[', TokenType.L_BKT))
					return token;

				else if (checkSymbolOne(']', TokenType.R_BKT))
					return token;

				// symbols group 2

				else if (checkSymbolTwo('=', TokenType.ASSIGN, '=', TokenType.EQ))
					return token;

				else if (checkSymbolTwo('!', TokenType.NOT, '=', TokenType.NE))
					return token;

				else if (checkSymbolFour('<', TokenType.LT, '=', TokenType.LE, '<', TokenType.L_SHIFT, '=',
						TokenType.L_SHIFT_ASSIGN))
					return token;

				else if (checkSymbolFour('>', TokenType.GT, '=', TokenType.GE, '<', TokenType.R_SHIFT, '=',
						TokenType.R_SHIFT_ASSIGN))
					return token;

				else if (checkSymbolThree('&', TokenType.BIT_AND, '&', TokenType.AND, '=', TokenType.BIT_AND_ASSIGN))
					return token;

				else if (checkSymbolThree('|', TokenType.BIT_OR, '|', TokenType.OR, '=', TokenType.BIT_OR_ASSIGN))
					return token;

				else if (checkSymbolThree('^', TokenType.BIT_XOR, '^', TokenType.XOR, '=', TokenType.BIT_XOR_ASSIGN))
					return token;

				else if (checkSymbolOne('~', TokenType.BIT_NOT))
					return token;

				// symbols group 3

				else if (checkSymbolTwo('+', TokenType.ADD, '=', TokenType.ADD_ASSIGN))
					return token;

				else if (checkSymbolTwo('-', TokenType.SUB, '=', TokenType.SUB_ASSIGN))
					return token;

				else if (checkSymbolFour('/', TokenType.DIV, '=', TokenType.DIV_ASSIGN, '/', TokenType.TDIV, '=',
						TokenType.TDIV_ASSIGN))
					return token;

				else if (checkSymbolFour('*', TokenType.MUL, '=', TokenType.MUL_ASSIGN, '*', TokenType.EXP, '=',
						TokenType.EXP_ASSIGN))
					return token;

				else if (checkSymbolTwo('%', TokenType.MOD, '=', TokenType.MOD_ASSIGN))
					return token;

				// error

				else
				{
					setStartIndex();
					incIndex();
					setEndIndex();

					throw newException(ErrorType.INVALID_CHARACTER);
				}
			}

			private boolean checkSymbolOne(char ch, TokenType type)
			{
				if (getByte() == ch)
				{
					setStartIndex();
					incIndex();
					setEndIndex();

					setType(type);
					return true;
				}
				return false;
			}

			private boolean checkSymbolTwo(char ch1, TokenType type1, char ch2, TokenType type2)
			{
				if (getByte() == ch1)
				{
					setStartIndex();

					incIndex();

					if (!end && getByte() == ch2)
					{
						incIndex();
						setEndIndex();

						setType(type2);
						return true;
					}

					setEndIndex();

					setType(type1);
					return true;
				}
				return false;
			}

			private boolean checkSymbolThree(char ch1, TokenType type1, char ch2, TokenType type2, char ch3,
					TokenType type3)
			{
				if (getByte() == ch1)
				{
					setStartIndex();

					incIndex();

					if (!end)
					{
						if (getByte() == ch2)
						{
							incIndex();
							setEndIndex();

							setType(type2);
							return true;
						} else if (getByte() == ch3)
						{
							incIndex();
							setEndIndex();

							setType(type3);
							return true;
						}
					}

					setEndIndex();

					setType(type1);
					return true;
				}
				return false;
			}

			private boolean checkSymbolFour(char ch1, TokenType type1, char ch2, TokenType type2, char ch3,
					TokenType type3, char ch4, TokenType type4)
			{
				if (getByte() == ch1)
				{
					setStartIndex();

					incIndex();

					if (!end)
					{
						if (getByte() == ch2)
						{
							incIndex();
							setEndIndex();

							setType(type2);
							return true;
						}

						else if (getByte() == ch3)
						{
							incIndex();

							if (!end && getByte() == ch4)
							{
								incIndex();
								setEndIndex();

								setType(type4);
								return true;
							}

							setEndIndex();

							setType(type3);
							return true;
						}
					}

					setEndIndex();

					setType(type1);
					return true;
				}

				return false;
			}
		};
	}
}
