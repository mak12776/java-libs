package tbx.types;

public class Pattern
{
	/*
	 * 	Rules:
	 * 		"."					Matches any character except a newline.
	 * 		"*"					Matches 0 or more (greedy) repetitions of preceding RE.
	 * 		"+"					Matches 1 or more (greedy) repetitions of preceding RE.
	 * 		"?"					Matches 0 or 1 (greedy) repetitions of preceding RE.
	 * 		*?, +?, ??			Non-greedy versions of the previous three special characters.
	 * 		{m, n}				Matches from m to n (greedy) repetitions of preceding RE.
	 * 		{m, n}?				Non-greedy versions of the above.
	 *		"\\"     			Either escapes special characters or signals a special sequence.
	 *		[ ]					Indicates a set of characters.
	 *							A "^" as the first character indicates a complementing set.
	 *		"|"					A|B, creates an RE that will match either A or B.
	 *		(...)				Matches the RE inside the parentheses.
	 *							The contents can be retrieved or matched later in the string.
	 *		(?:...)				Non-grouping version of regular parentheses.
	 *
	 *	Escape characters:
	 *		\number				Matches the contents of the group of the same number.
	 *		\d					Matches any decimal digit; equivalent to the set [0-9].
	 *		\D					Matches any non-digit character; equivalent to the set [^0-9].
	 *		\s					Matches any whitespace character; equivalent to [ \t\n\r\f\v].
	 *		\S					Matches any non-whitespace character; equivalent to [^ \t\n\r\f\v].
	 *		\w					Matches any alphanumeric character; equivalent to [a-zA-Z0-9_].
	 *		\W					Matches the complement of \w.
	 *		\\					Matches a literal backslash.
	 */
	
	public Pattern(String pattern)
	{
		
	}
	
	public Pattern(byte[] buffer)
	{
		
	}
}
