/*
 * Class: CS 4308 Section 1
 * Term: Spring 2021
 * Names: Matt Gibson,
 * 		Souleymane Camara,
 * 		and Zhuoxing Wang
 * Instructor: Deepa Muralidhar
 * Project: Deliverable 3 Scanner, Parser, and Interpreter - Java
 */

// ScannerParserAndInterpreter.java hosts the functions of the deliverable

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
public class ScannerParserAndInterpreter
{
	// Instance Variables
	
	// String fileName stores the file path given by the user
	public String fileName;
	// String fileString stores the file data found at fileName
	public String fileString;
	
	// initializeFileString sets up fileString using fileName
	public void initializeFileString()
	{
		try
		{
			String str = new String(Files.readAllBytes(Paths.get(fileName)));
			fileString = str;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	// The following four ArrayLists act as lookup tables
	// for keywords, alphanumeric characters, numbers, and
	// syntactical characters to use when separating fileString.
	// The methods each correspond to a list and populate them.
	// The index of each keyword/syntactical character in the
	// ArrayList is used as its unique ID number. This format
	// allows for easy addition of more keywords/syntactical
	// characters in the case of BASIC coverage expansion.
	// NEW: there is now a list for white space
	public List<String> keywords = new ArrayList<String>();
	public void initializeKeywords()
	{
		keywords.add("Module");
		keywords.add("Public");
		keywords.add("as");
		keywords.add("Integer");
		keywords.add("Sub");
		keywords.add("Console");
		keywords.add("ReadLine");
		keywords.add("WriteLine");
		keywords.add("End");
		// New
		keywords.add("Function");
		keywords.add("Dim");
		keywords.add("If");
		keywords.add("Then");
		keywords.add("Else");
		keywords.add("While");
		keywords.add("Exit");
	}
	
	public List<String> lettersAndNumbers = new ArrayList<String>();
	public void initializeLettersAndNumbers()
	{
		lettersAndNumbers.add("0");
		lettersAndNumbers.add("1");
		lettersAndNumbers.add("2");
		lettersAndNumbers.add("3");
		lettersAndNumbers.add("4");
		lettersAndNumbers.add("5");
		lettersAndNumbers.add("6");
		lettersAndNumbers.add("7");
		lettersAndNumbers.add("8");
		lettersAndNumbers.add("9");
		lettersAndNumbers.add("a");
		lettersAndNumbers.add("b");
		lettersAndNumbers.add("c");
		lettersAndNumbers.add("d");
		lettersAndNumbers.add("e");
		lettersAndNumbers.add("f");
		lettersAndNumbers.add("g");
		lettersAndNumbers.add("h");
		lettersAndNumbers.add("i");
		lettersAndNumbers.add("j");
		lettersAndNumbers.add("k");
		lettersAndNumbers.add("l");
		lettersAndNumbers.add("m");
		lettersAndNumbers.add("n");
		lettersAndNumbers.add("o");
		lettersAndNumbers.add("p");
		lettersAndNumbers.add("q");
		lettersAndNumbers.add("r");
		lettersAndNumbers.add("s");
		lettersAndNumbers.add("t");
		lettersAndNumbers.add("u");
		lettersAndNumbers.add("v");
		lettersAndNumbers.add("w");
		lettersAndNumbers.add("x");
		lettersAndNumbers.add("y");
		lettersAndNumbers.add("z");
		lettersAndNumbers.add("A");
		lettersAndNumbers.add("B");
		lettersAndNumbers.add("C");
		lettersAndNumbers.add("D");
		lettersAndNumbers.add("E");
		lettersAndNumbers.add("F");
		lettersAndNumbers.add("G");
		lettersAndNumbers.add("H");
		lettersAndNumbers.add("I");
		lettersAndNumbers.add("J");
		lettersAndNumbers.add("K");
		lettersAndNumbers.add("L");
		lettersAndNumbers.add("M");
		lettersAndNumbers.add("N");
		lettersAndNumbers.add("O");
		lettersAndNumbers.add("P");
		lettersAndNumbers.add("Q");
		lettersAndNumbers.add("R");
		lettersAndNumbers.add("S");
		lettersAndNumbers.add("T");
		lettersAndNumbers.add("U");
		lettersAndNumbers.add("V");
		lettersAndNumbers.add("W");
		lettersAndNumbers.add("X");
		lettersAndNumbers.add("Y");
		lettersAndNumbers.add("Z");
	}

	public List<String> numbers = new ArrayList<String>();
	public void initializeNumbers()
	{
		numbers.add("0");
		numbers.add("1");
		numbers.add("2");
		numbers.add("3");
		numbers.add("4");
		numbers.add("5");
		numbers.add("6");
		numbers.add("7");
		numbers.add("8");
		numbers.add("9");
	}
	
	public List<String> syntax = new ArrayList<String>();
	public void initializeSyntax()
	{
		syntax.add("=");
		syntax.add("(");
		syntax.add(")");
		syntax.add(".");
		syntax.add("+");
		syntax.add("&");
		syntax.add("\n");
		syntax.add("\r");
		syntax.add("\r\n");
		syntax.add(">");
		syntax.add("<");
		syntax.add(",");
		syntax.add("*");
		syntax.add("/");
		syntax.add("-");
	}
	
	public List<String> whiteSpace = new ArrayList<String>();
	public void initializeWhiteSpace()
	{
		whiteSpace.add(" ");
		whiteSpace.add("\t");
	}
	
	// These next seven ArrayLists are used to store categorized identifiers
	public List<Token> moduleVariables = new ArrayList<Token>();
	public List<Token> subVariables = new ArrayList<Token>();
	public List<Token> functionVariables = new ArrayList<Token>();
	public List<Token> integerVariables = new ArrayList<Token>();
	public List<Token> decimalVariables = new ArrayList<Token>();
	public List<Token> stringVariables = new ArrayList<Token>();
	public List<Token> booleanVariables = new ArrayList<Token>();
	
	public List<Integer> integerLog = new ArrayList<Integer>();
	
	// These two lists were added when developing the parser
	// They are used as look up tables to clean up code
	public List<String> allVariableNames = new ArrayList<String>();
	public List<String> functionAndSubNames = new ArrayList<String>();
	
	// Once fileString is separated out, unclassifedTokens stores
	// the separated Strings until they can be categorized
	public List<String> unclassifiedTokens = new ArrayList<String>();
	
	// registeredTokens is to be populated with the final tokenized Strings
	public List<Token> registeredTokens = new ArrayList<Token>();
	
	// parsedStatements is to be populated by the parser
	public List<Statement> parsedStatements = new ArrayList<Statement>();
	
	// The constructor simply takes in an argument for input to fileName
	// and also calls the initialization methods to populate our lookup tables
	public ScannerParserAndInterpreter(String filePathIn)
	{
		fileName = filePathIn;
		initializeFileString();
		
		initializeKeywords();
		initializeLettersAndNumbers();
		initializeNumbers();
		initializeSyntax();
		initializeWhiteSpace();
	}
	
	// scanFile does the first part of the Scanner's job by breaking up
	// fileString and populating unclassifiedTokens
	public List<String> scanFile()
	{
		// String temp stores individual characters as they
		// are combined to make up whole unclassified tokens
		String temp = "";
		
		// currentChar is simply a shortcut variable to avoid having
		// to type fileString.substring(i, i + 1) over and over again
		String currentChar;
		
		// boolean quote indicates if the scanner is inside an
		// String literal indicated by a pair of quotation marks
		boolean quote = false;
		
		// This for loop scans through fileString character by
		// character to store them and break them apart as
		// necessary to populate unclassifiedTokens
		for(int i = 0; i < fileString.length(); i++)
		{
			currentChar = fileString.substring(i, i + 1);
			
			// This first if statement checks for the initial quote mark
			if(currentChar.equals("\"") && !quote)
			{
				quote = true;
				temp += currentChar;
			}
			// This next else if controls navigation inside the String
			else if(quote)
			{
				// This if checks for the end of the quotes
				if(currentChar.equals("\""))
				{
					// At this point the String is stored and quote is reset to false
					quote = false;
					temp += currentChar;
					unclassifiedTokens.add(temp);
					temp = "";
				}
				else
				{
					// Hitting this else indicates that the String's end has not been reached
					temp += currentChar;
				}
			}
			// This else if checks for alphanumeric characters, indicating keyword/identifier
			else if(lettersAndNumbers.indexOf(currentChar) != -1)
			{
				temp += currentChar;
			}
			// This else if checks for decimal points inside floating point numbers
			else if((currentChar.equals(".")) &&
					(numbers.indexOf(fileString.substring(i + 1, i + 2)) != -1))
			{
				temp += currentChar;
			}
			// Anything that reaches this else is ready to be stored as its own String
			else
			{
				// This if controls multi-character tokens
				if(!temp.equals(""))
				{
					unclassifiedTokens.add(temp);
					temp = "";
					unclassifiedTokens.add(currentChar);
				}
				// This else controls single-character tokens
				else
				{
					unclassifiedTokens.add(currentChar);
				}
			}
		}
		
		// The addition of the final String must take place
		// outside the loop to avoid going out of bounds
		unclassifiedTokens.add(temp);
		
		// The cause of an issue where new line characters are
		// duplicated could not be discovered, so duplicates are
		// simply removed here in this for loop
		for(int i = 0; i < unclassifiedTokens.size(); i++)
		{
			if(unclassifiedTokens.get(i).equals("\n"))
			{
				unclassifiedTokens.remove(i);
			}
		}
		
		// Finally, we return the populated unclassifiedTokens 
		return(unclassifiedTokens);
	}
	
	// registerTokens categorizes the Strings from unclassifiedTokens
	public List<Token> registerTokens()
	{
		// lineCounter simply stores the line number for token declarations
		int lineCounter = 1;
		
		// This for loop moves through each String in unclassifiedTokens and
		// populates registeredTokens with a corresponding list of tokens
		for(int i = 0; i < unclassifiedTokens.size(); i++)
		{
			// These two variables are simply here to avoid lengthy retyping
			String currentToken = unclassifiedTokens.get(i);
			String currentInitial = currentToken.substring(0, 1);
			
			// This series of if statements determines the Token type for each String.
			// Comments in each section indicate the type of token that passes through.
			if(lettersAndNumbers.indexOf(currentInitial) != -1)
			{
				// initial is a letter or number
				if(numbers.indexOf(currentInitial) == -1)
				{
					// initial is a letter
					if(keywords.indexOf(currentToken) != -1)
					{
						// token is a keyword
						Token t = new Token(Token.TokenType.KEYWORD, currentToken,
								keywords.indexOf(currentToken), lineCounter);
						registeredTokens.add(t);
					}
					else
					{
						// token is an identifier
						Token t = new Token(Token.TokenType.IDENTIFIER, currentToken,
								-1, lineCounter);
						registeredTokens.add(t);
					}
				}
				else
				{
					// initial is a number
					if(currentToken.contains("."))
					{
						// token is a decimal constant
						Token t = new Token(Token.TokenType.DECIMAL_CONSTANT, currentToken,
								-1, lineCounter);
						registeredTokens.add(t);
					}
					else
					{
						// token is an integer constant
						Token t = new Token(Token.TokenType.INTEGER_CONSTANT, currentToken,
								-1, lineCounter);
						registeredTokens.add(t);
					}
				}
			}
			else if(currentInitial.equals("\""))
			{
				// token is a String literal
				Token t = new Token(Token.TokenType.STRING_LITERAL, currentToken,
						-1, lineCounter);
				registeredTokens.add(t);
			}
			else if(syntax.indexOf(currentToken) != -1)
			{
				// token is syntactical character
				int syntaxID = syntax.indexOf(currentToken);
				if(syntaxID < 1)
				{
					Token t = new Token(Token.TokenType.EQUALS, currentToken,
							syntaxID, lineCounter);
					registeredTokens.add(t);
				}
				else if(syntaxID < 2)
				{
					Token t = new Token(Token.TokenType.LEFT_PARENTHESES, currentToken,
							syntaxID, lineCounter);
					registeredTokens.add(t);
				}
				else if(syntaxID < 3)
				{
					Token t = new Token(Token.TokenType.RIGHT_PARENTHESES, currentToken,
							syntaxID, lineCounter);
					registeredTokens.add(t);
				}
				else if(syntaxID < 4)
				{
					Token t = new Token(Token.TokenType.DOT, currentToken,
							syntaxID, lineCounter);
					registeredTokens.add(t);
				}
				else if(syntaxID < 5)
				{
					Token t = new Token(Token.TokenType.PLUS, currentToken,
							syntaxID, lineCounter);
					registeredTokens.add(t);
				}
				else if(syntaxID < 6)
				{
					Token t = new Token(Token.TokenType.AND, currentToken,
							syntaxID, lineCounter);
					registeredTokens.add(t);
				}
				else if(syntaxID < 9)
				{
					// Java's three different new line characters
					// (\n, \r, and \r\n) are checked for and converted
					// to the same type, \n, for the sake of future convenience 
					Token t = new Token(Token.TokenType.NEW_LINE,
							"\n", syntaxID, lineCounter);
					registeredTokens.add(t);
					lineCounter++;
				}
				else if(syntaxID < 10)
				{
					Token t = new Token(Token.TokenType.GREATER_THAN, currentToken,
							syntaxID, lineCounter);
					registeredTokens.add(t);
				}
				else if(syntaxID < 11)
				{
					Token t = new Token(Token.TokenType.LESS_THAN, currentToken,
							syntaxID, lineCounter);
					registeredTokens.add(t);
				}
				else if(syntaxID < 12)
				{
					Token t = new Token(Token.TokenType.COMMA, currentToken,
							syntaxID, lineCounter);
					registeredTokens.add(t);
				}
				else if(syntaxID < 13)
				{
					Token t = new Token(Token.TokenType.STAR, currentToken,
							syntaxID, lineCounter);
					registeredTokens.add(t);
				}
				else if(syntaxID < 14)
				{
					Token t = new Token(Token.TokenType.SLASH, currentToken,
							syntaxID, lineCounter);
					registeredTokens.add(t);
				}
				else if(syntaxID < 15)
				{
					Token t = new Token(Token.TokenType.MINUS, currentToken,
							syntaxID, lineCounter);
					registeredTokens.add(t);
				}
				else
				{
					// token is and unknown syntactical character
					System.out.println("unknown syntax(" + currentToken
							+ ") at line: " + lineCounter);
				}				
			}
			else if(whiteSpace.indexOf(currentToken) != -1)
			{
				// currentToken is white space (aspace or a tab)
				// No token is added to keep white space out
			}
			else
			{
				// token is completely unknown
				System.out.println("unknown token(" + currentToken
						+ ") at line: " + lineCounter);
			}
		}
		
		// This last for loop checks the nearly finished registeredTokens list
		// and finds unspecified identifiers, then specifies and adds IDs
		for(int i = 0; i < registeredTokens.size(); i++)
		{
			if(registeredTokens.get(i).type.equals(Token.TokenType.IDENTIFIER))
			{
				if(registeredTokens.get(i - 1).lexeme.equals("Module"))
				{
					registeredTokens.get(i).type = Token.TokenType.MODULE_IDENTIFIER;
					allVariableNames.add(registeredTokens.get(i).lexeme);
					if(moduleVariables.indexOf(registeredTokens.get(i)) == -1)
					{
						moduleVariables.add(registeredTokens.get(i));
						registeredTokens.get(i).reference = moduleVariables.size() - 1;
					}
				}
				else if(registeredTokens.get(i - 1).lexeme.equals("Sub"))
				{
					registeredTokens.get(i).type = Token.TokenType.SUB_IDENTIFIER;
					allVariableNames.add(registeredTokens.get(i).lexeme);
					functionAndSubNames.add(registeredTokens.get(i).lexeme);
					if(subVariables.indexOf(registeredTokens.get(i)) == -1)
					{
						subVariables.add(registeredTokens.get(i));
						registeredTokens.get(i).reference = subVariables.size() - 1;
					}
				}
				else if(registeredTokens.get(i - 1).lexeme.equals("Function"))
				{
					registeredTokens.get(i).type = Token.TokenType.FUNCTION_IDENTIFIER;
					allVariableNames.add(registeredTokens.get(i).lexeme);
					functionAndSubNames.add(registeredTokens.get(i).lexeme);
					if(functionVariables.indexOf(registeredTokens.get(i)) == -1)
					{
						functionVariables.add(registeredTokens.get(i));
						registeredTokens.get(i).reference = functionVariables.size() - 1;
					}
				}
				else if(registeredTokens.get(i + 2).lexeme.equals("Integer"))
				{
					registeredTokens.get(i).type = Token.TokenType.INTEGER_IDENTIFIER;
					allVariableNames.add(registeredTokens.get(i).lexeme);
					if(integerVariables.indexOf(registeredTokens.get(i)) == -1)
					{
						integerVariables.add(registeredTokens.get(i));
						registeredTokens.get(i).reference = integerVariables.size() - 1;
						//integerLog.add(Integer.parseInt(registeredTokens.get(i).lexeme));
						//registeredTokens.get(i).reference = integerLog.size() - 1;
						
					}
				}
				else if(registeredTokens.get(i + 2).lexeme.equals("Decimal"))
				{
					registeredTokens.get(i).type = Token.TokenType.DECIMAL_IDENTIFIER;
					allVariableNames.add(registeredTokens.get(i).lexeme);
					if(decimalVariables.indexOf(registeredTokens.get(i)) == -1)
					{
						decimalVariables.add(registeredTokens.get(i));
						registeredTokens.get(i).reference = decimalVariables.size() - 1;
					}
				}
				else if(registeredTokens.get(i + 2).lexeme.equals("String"))
				{
					registeredTokens.get(i).type = Token.TokenType.STRING_IDENTIFIER;
					allVariableNames.add(registeredTokens.get(i).lexeme);
					if(stringVariables.indexOf(registeredTokens.get(i)) == -1)
					{
						stringVariables.add(registeredTokens.get(i));
						registeredTokens.get(i).reference = stringVariables.size() - 1;
					}
				}
				else if(registeredTokens.get(i + 2).lexeme.equals("Boolean"))
				{
					registeredTokens.get(i).type = Token.TokenType.BOOLEAN_IDENTIFIER;
					allVariableNames.add(registeredTokens.get(i).lexeme);
					if(booleanVariables.indexOf(registeredTokens.get(i)) == -1)
					{
						booleanVariables.add(registeredTokens.get(i));
						registeredTokens.get(i).reference = booleanVariables.size() - 1;
					}
				}
			}
		}
		
		// Finally we return the completed registeredTokens ArrayList
		return(registeredTokens);
	}
	
	// parseFind populates parsedStatements with Statement Objects
	// after they have been checked by the logic in parseCheck
	public List<Statement> parseFind()
	{
		List<Token> tempList = new ArrayList<Token>();
		
		for(int i = 0; i < registeredTokens.size(); i++)
		{
			if(registeredTokens.get(i).type.equals(Token.TokenType.NEW_LINE))
			{
				parsedStatements.add(parseCheck(tempList));
				tempList.clear();
			}
			else
			{
				tempList.add(registeredTokens.get(i));
			}
		}
		parsedStatements.add(parseCheck(tempList));
		
		return(parsedStatements);
	}
	
	// parseCheck contains all of the error-checking logic
	// that is used by the parser. It it used by parseFind
	// to check statements for errors and identify them.
	// FYI, this method is extremely long and bulky. You
	// can skip to line 1176 to go past it.
	public Statement parseCheck(List<Token> statementIn)
	{
		List<Token> statement = new ArrayList<Token>();
		
		for(int i = 0; i < statementIn.size(); i++)
		{
			statement.add(statementIn.get(i));
		}
		
		if(statement.get(0).type.equals(Token.TokenType.IDENTIFIER))
		{
			// first token is an identifier
			if(statement.get(1).type.equals(Token.TokenType.EQUALS))
			{
				// second token is equals
				if(statement.size() == 3)
				{
					// size is 3
					if(statement.get(2).type.equals(Token.TokenType.IDENTIFIER))
					{
						// third is identifier
						return(new Statement(statement, 5, -1));
					}
					else if(statement.get(2).type.equals(Token.TokenType.INTEGER_CONSTANT))
					{
						// third is int literal
						return(new Statement(statement, 6, -1));
					}
					else
					{
						// third is unknown
						return(new Statement(statement, 1, 2));
					}
				}
				else if(statement.get(2).lexeme.equals("Console"))
				{
					// third is console
					if(statement.get(3).type.equals(Token.TokenType.DOT))
					{
						// fourth is dot
						if(statement.get(4).lexeme.equals("ReadLine"))
						{
							// fifth is readline
							if(statement.get(5).type.equals(Token.TokenType.LEFT_PARENTHESES))
							{
								// sixth is left parentheses
								if(statement.get(statement.size() - 1).type.equals(Token.TokenType.RIGHT_PARENTHESES))
								{
									// last is right parentheses
									return(new Statement(statement, 8, -1));
								}
								else
								{
									// right parentheses is missing
									return(new Statement(statement, 2, statement.size() - 1));
								}
							}
							else
							{
								// readline is missing
								return(new Statement(statement, 2, 5));
							}
						}
						else
						{
							// left parentheses is missing
							return(new Statement(statement, 2, 4));
						}
					}
					else
					{
						// dot is missing
						return(new Statement(statement, 2, 3));
					}
				}
				else if(functionAndSubNames.indexOf(statement.get(2).lexeme) != -1)
				{
					// third is function/sub call
					if(statement.get(3).type.equals(Token.TokenType.LEFT_PARENTHESES))
					{
						// fourth is left parentheses
						if(statement.get(statement.size() - 1).type.equals(Token.TokenType.RIGHT_PARENTHESES))
						{
							// last is right parentheses
							return(new Statement(statement, 7, -1));
						}
						else
						{
							// right parentheses is missing
							return(new Statement(statement, 3, statement.size() - 1));
						}
					}
					else
					{
						// left parentheses is missing
						return(new Statement(statement, 3, 3));
					}
				}
				else if(statement.size() == 5)
				{
					// size is 5
					if(statement.get(3).lexeme.equals("+"))
					{
						// fourth is plus
						if(statement.get(2).type.equals(Token.TokenType.IDENTIFIER))
						{
							// third is identifier
							if(statement.get(4).type.equals(Token.TokenType.INTEGER_CONSTANT))
							{
								// fifth is constant
								return(new Statement(statement, 15, -1));
							}
							else if(statement.get(4).type.equals(Token.TokenType.IDENTIFIER))
							{
								// fifth is identifier
								return(new Statement(statement, 17, -1));
							}
							else
							{
								// fifth is unknown
								return(new Statement(statement, 4, 4));
							}
						}
						else if(statement.get(2).type.equals(Token.TokenType.INTEGER_CONSTANT))
						{
							// third is constant
							if(statement.get(4).type.equals(Token.TokenType.INTEGER_CONSTANT))
							{
								// fifth is constant
								return(new Statement(statement, 9, -1));
							}
							else if(statement.get(4).type.equals(Token.TokenType.IDENTIFIER))
							{
								// fifth is identifier
								return(new Statement(statement, 16, -1));
							}
							else
							{
								// fifth is unknown
								return(new Statement(statement, 4, 4));
							}
						}
						else
						{
							// third is unknown
							return(new Statement(statement, 4, 2));
						}
					}
					else if(statement.get(3).lexeme.equals("-"))
					{
						// fourth is minus
						if(statement.get(2).type.equals(Token.TokenType.IDENTIFIER))
						{
							// third is identifier
							if(statement.get(4).type.equals(Token.TokenType.INTEGER_CONSTANT))
							{
								// fifth is constant
								return(new Statement(statement, 21, -1));
							}
							else if(statement.get(4).type.equals(Token.TokenType.IDENTIFIER))
							{
								// fifth is identifier
								return(new Statement(statement, 20, -1));
							}
							else
							{
								// fifth is unknown
								return(new Statement(statement, 16, 4));
							}
						}
						else if(statement.get(2).type.equals(Token.TokenType.INTEGER_CONSTANT))
						{
							// third is constant
							if(statement.get(4).type.equals(Token.TokenType.INTEGER_CONSTANT))
							{
								// fifth is constant
								return(new Statement(statement, 22, -1));
							}
							else if(statement.get(4).type.equals(Token.TokenType.IDENTIFIER))
							{
								// fifth is identifier
								return(new Statement(statement, 19, -1));
							}
							else
							{
								// fifth is unknown
								return(new Statement(statement, 16, 4));
							}
						}
						else
						{
							// third is unknown
							return(new Statement(statement, 16, 2));
						}
					}
					else if(statement.get(3).lexeme.equals("*"))
					{
						// fourth is star
						if(statement.get(2).type.equals(Token.TokenType.IDENTIFIER))
						{
							// third is identifier
							if(statement.get(4).type.equals(Token.TokenType.INTEGER_CONSTANT))
							{
								// fifth is constant
								return(new Statement(statement, 25, -1));
							}
							else if(statement.get(4).type.equals(Token.TokenType.IDENTIFIER))
							{
								// fifth is identifier
								return(new Statement(statement, 24, -1));
							}
							else
							{
								// fifth is unknown
								return(new Statement(statement, 17, 4));
							}
						}
						else if(statement.get(2).type.equals(Token.TokenType.INTEGER_CONSTANT))
						{
							// third is constant
							if(statement.get(4).type.equals(Token.TokenType.INTEGER_CONSTANT))
							{
								// fifth is constant
								return(new Statement(statement, 26, -1));
							}
							else if(statement.get(4).type.equals(Token.TokenType.IDENTIFIER))
							{
								// fifth is identifier
								return(new Statement(statement, 23, -1));
							}
							else
							{
								// fifth is unknown
								return(new Statement(statement, 17, 4));
							}
						}
						else
						{
							// third is unknown
							return(new Statement(statement, 17, 2));
						}
					}
					else if(statement.get(3).lexeme.equals("/"))
					{
						// fourth is slash
						if(statement.get(2).type.equals(Token.TokenType.IDENTIFIER))
						{
							// third is identifier
							if(statement.get(4).type.equals(Token.TokenType.INTEGER_CONSTANT))
							{
								// fifth is constant
								return(new Statement(statement, 29, -1));
							}
							else if(statement.get(4).type.equals(Token.TokenType.IDENTIFIER))
							{
								// fifth is identifier
								return(new Statement(statement, 28, -1));
							}
							else
							{
								// fifth is unknown
								return(new Statement(statement, 18, 4));
							}
						}
						else if(statement.get(2).type.equals(Token.TokenType.INTEGER_CONSTANT))
						{
							// third is constant
							if(statement.get(4).type.equals(Token.TokenType.INTEGER_CONSTANT))
							{
								// fifth is constant
								return(new Statement(statement, 30, -1));
							}
							else if(statement.get(4).type.equals(Token.TokenType.IDENTIFIER))
							{
								// fifth is identifier
								return(new Statement(statement, 27, -1));
							}
							else
							{
								// fifth is unknown
								return(new Statement(statement, 18, 4));
							}
						}
						else
						{
							// third is unknown
							return(new Statement(statement, 18, 2));
						}
					}
					else
					{
						// fourth is unknown
						return(new Statement(statement, 5, 3));
					}
				}
				else
				{
					// third is unknown
					return(new Statement(statement, 5, 2));
				}
			}
			else if(functionAndSubNames.indexOf(statement.get(0).lexeme) != -1)
			{
				// first token is a call to a method
				if(statement.get(1).type.equals(Token.TokenType.LEFT_PARENTHESES))
				{
					// left parentheses are in correct place
					if(statement.get(statement.size() - 1).type.equals(Token.TokenType.RIGHT_PARENTHESES))
					{
						// right parentheses are in correct place
						return(new Statement(statement, 13, -1));
					}
					else
					{
						// unexpected end token, right parentheses is missing
						return(new Statement(statement, 6, statement.size() - 1));
					}
				}
				else
				{
					// unexpected second token, left parentheses is missing
					return(new Statement(statement, 6, 1));
				}
			}
			else
			{
				// equals is missing
				return(new Statement(statement, 5, 1));
			}
		}
		else if(statement.get(0).type.equals(Token.TokenType.KEYWORD))
		{
			// first token is a keyword
			String keyword = statement.get(0).lexeme;
			if(keyword.equals("Module"))
			{
				// statement is a module block
				if(statement.get(statement.size() - 1).type.equals(Token.TokenType.MODULE_IDENTIFIER))
				{
					// statement has correct header
					return(new Statement(statement, 1, -1));
				}
				else
				{
					// statement is missing moduleID
					return(new Statement(statement, 7, statement.size() - 1));
				}
			}
			else if(keyword.equals("Function"))
			{
				// first is Function
				if(statement.get(1).type.equals(Token.TokenType.FUNCTION_IDENTIFIER))
				{
					// second is functionID
					if(statement.get(2).type.equals(Token.TokenType.LEFT_PARENTHESES))
					{
						// third is left parentheses
						if(statement.get(statement.size() - 1).lexeme.equals("Integer"))
						{
							// last is data type
							if(statement.get(statement.size() - 2).lexeme.equals("as"))
							{
								// second to last is as
								if(statement.get(statement.size() - 3).type.equals(Token.TokenType.RIGHT_PARENTHESES))
								{
									// third to last is right parentheses
									return(new Statement(statement, 3, -1));
								}
								else
								{
									// missing right parentheses
									return(new Statement(statement, 8, statement.size() - 3));
								}
							}
							else
							{
								// missing as
								return(new Statement(statement, 8, statement.size() - 2));
							}
						}
						else
						{
							// missing data type
							return(new Statement(statement, 8, statement.size() - 1));
						}
					}
					else
					{
						// missing left parentheses
						return(new Statement(statement, 8, 2));
					}
				}
				else
				{
					// statement is missing functionID
					return(new Statement(statement, 8, 1));
				}
			}
			else if(keyword.equals("Dim") || keyword.equals("Public"))
			{
				// statement is an assignment
				if(allVariableNames.indexOf(statement.get(1).lexeme) != -1)
				{
					// second is named variable
					if(statement.get(2).lexeme.equals("as"))
					{
						// third is as
						return(new Statement(statement, 4, -1));
					}
					else
					{
						// as is missing
						return(new Statement(statement, 9, 2));
					}
				}
				else
				{
					// variable name not found
					return(new Statement(statement, 9, 1));
				}
			}
			else if(keyword.equals("If"))
			{
				// statement is an if block
				if(statement.get(1).type.equals(Token.TokenType.LEFT_PARENTHESES))
				{
					// second is left parentheses
					if(statement.get(statement.size() - 1).lexeme.equals("Then"))
					{
						// last is then
						if(statement.get(statement.size() - 2).type.equals(Token.TokenType.RIGHT_PARENTHESES))
						{
							// second to last is right parentheses
							return(new Statement(statement, 10, -1));
						}
						else
						{
							// missing right parentheses
							return(new Statement(statement, 10, statement.size() - 2));
						}
					}
					else
					{
						// missing then
						return(new Statement(statement, 10, statement.size() - 1));
					}
				}
				else
				{
					// missing left parentheses
					return(new Statement(statement, 10, 1));
				}
			}
			else if(keyword.equals("Else"))
			{
				// statement is an else line
				return(new Statement(statement, 10, -1));
			}
			else if(keyword.equals("End"))
			{
				// statement is an end line
				return(new Statement(statement, 14, -1));
			}
			else if(keyword.equals("Sub"))
			{
				// statement is a sub block
				if(statement.get(1).type.equals(Token.TokenType.SUB_IDENTIFIER))
				{
					// second is subID
					if(statement.get(2).type.equals(Token.TokenType.LEFT_PARENTHESES))
					{
						// third is left parentheses
						if(statement.get(statement.size() - 1).type.equals(Token.TokenType.RIGHT_PARENTHESES))
						{
							// third to last is right parentheses
							return(new Statement(statement, 2, -1));
						}
						else
						{
							// missing right parentheses
							return(new Statement(statement, 12, statement.size() - 1));
						}
					}
					else
					{
						// missing left parentheses
						return(new Statement(statement, 12, 2));
					}
				}
				else
				{
					// statement is missing subID
					return(new Statement(statement, 12, 1));
				}
			}
			else if(keyword.equals("While"))
			{
				// statement is a while block
				if(statement.get(1).type.equals(Token.TokenType.LEFT_PARENTHESES))
				{
					// second is left parentheses
					if(statement.get(statement.size() - 1).type.equals(Token.TokenType.RIGHT_PARENTHESES))
					{
						// second to last is right parentheses
						return(new Statement(statement, 11, -1));
					}
					else
					{
						// missing right parentheses
						return(new Statement(statement, 13, statement.size() - 1));
					}
				}
				else
				{
					// missing left parentheses
					return(new Statement(statement, 13, 1));
				}
			}
			else if(keyword.equals("Exit"))
			{
				// statement is an exit line
				return(new Statement(statement, 15, -1));
			}
			else if(keyword.equals("Console"))
			{
				// statement is a console call
				if(statement.get(1).type.equals(Token.TokenType.DOT))
				{
					// second is dot
					if(statement.get(2).lexeme.equals("WriteLine"))
					{
						// third is writeline
						if(statement.get(3).type.equals(Token.TokenType.LEFT_PARENTHESES))
						{
							// fourth is left parentheses 
							if(statement.get(statement.size() - 1).type.equals(Token.TokenType.RIGHT_PARENTHESES))
							{
								// last is right parentheses
								return(new Statement(statement, 12, -1));
							}
							else
							{
								// missing right parentheses
								return(new Statement(statement, 12, statement.size() - 1));
							}
						}
						else
						{
							// left parentheses missing
							return(new Statement(statement, 12, 3));
						}
					}
					else
					{
						// writeline missing
						return(new Statement(statement, 12, 2));
					}
				}
				else
				{
					// dot is missing
					return(new Statement(statement, 12, 1));
				}
			}
			else
			{
				// unknown keyword
				return(new Statement(statement, 15, 0));
			}
		}
		else
		{
			// unexpected first token
			return(new Statement(statement, 15, 0));
		}
	}
	
	// process handles all of the detection and operation
	// control for the interpreter
	public String process()
	{
		// This String stores the Console output for the
		// program as it is interpreted. It is then returned
		// at the end of the method to simulate console output.
		String output = "";
		
		// These two lists store the information for variables
		// in the program. valValues represents the variable's
		// stored value, and varNames represents the variable's
		// name. The variables are stored in the list in order
		// of appearance (for example if a = 5 and appears first,
		// varNames.get(0) is a and varValues.get(0) is 5).
		List<Integer> varValues = new ArrayList<Integer>();
		List<String> varNames = new ArrayList<String>();
		
		// This for loop passes through the List of Statements
		// and searches for relevant statement IDs (anything
		// that affects output and/or variables)
		for(int i = 0; i < parsedStatements.size(); i++)
		{
			// This variable is simply here to avoid lengthy retyping
			Statement curr = parsedStatements.get(i);
			
			// Helper variables to give arithmetic operations cleaner code
			int recipientIndex, leftIndex, rightIndex, leftValue, rightValue;
			
			// WriteLine
			if(curr.parsedID == 12)
			{
				recipientIndex = varNames.indexOf(curr.tokens.get(4).lexeme);
				output += varValues.get(recipientIndex) + "\n";
			}
			// Variable Initialization
			else if(curr.parsedID == 4)
			{
				varNames.add(curr.tokens.get(1).lexeme);
				varValues.add(0);
			}
			// Reassignment
			else if(curr.parsedID == 6)
			{
				recipientIndex = varNames.indexOf(curr.tokens.get(0).lexeme);
				rightValue = Integer.parseInt(curr.tokens.get(2).lexeme);
				varValues.set(recipientIndex, rightValue);
			}
			else if(curr.parsedID == 5)
			{
				recipientIndex = varNames.indexOf(curr.tokens.get(0).lexeme);
				rightIndex = varNames.indexOf(curr.tokens.get(2).lexeme);
				rightValue = varValues.get(rightIndex);
				varValues.set(recipientIndex, rightValue);
			}
			// Addition
			else if(curr.parsedID == 16)
			{
				recipientIndex = varNames.indexOf(curr.tokens.get(0).lexeme);
				leftValue = Integer.parseInt(curr.tokens.get(2).lexeme);
				rightIndex = varNames.indexOf(curr.tokens.get(4).lexeme);
				rightValue = varValues.get(rightIndex);
				varValues.set(recipientIndex, leftValue + rightValue);
			}
			else if(curr.parsedID == 17)
			{
				recipientIndex = varNames.indexOf(curr.tokens.get(0).lexeme);
				leftIndex = varNames.indexOf(curr.tokens.get(2).lexeme);
				leftValue = varValues.get(leftIndex);
				rightIndex = varNames.indexOf(curr.tokens.get(4).lexeme);
				rightValue = varValues.get(rightIndex);
				varValues.set(recipientIndex, leftValue + rightValue);
			}
			else if(curr.parsedID == 18)
			{
				recipientIndex = varNames.indexOf(curr.tokens.get(0).lexeme);
				leftIndex = varNames.indexOf(curr.tokens.get(2).lexeme);
				leftValue = varValues.get(leftIndex);
				rightValue = Integer.parseInt(curr.tokens.get(4).lexeme);
				varValues.set(recipientIndex, leftValue + rightValue);
			}
			else if(curr.parsedID == 9)
			{
				recipientIndex = varNames.indexOf(curr.tokens.get(0).lexeme);
				leftValue = Integer.parseInt(curr.tokens.get(2).lexeme);
				rightValue = Integer.parseInt(curr.tokens.get(4).lexeme);
				varValues.set(recipientIndex, leftValue + rightValue);
			}
			// Subtraction
			else if(curr.parsedID == 19)
			{
				recipientIndex = varNames.indexOf(curr.tokens.get(0).lexeme);
				leftValue = Integer.parseInt(curr.tokens.get(2).lexeme);
				rightIndex = varNames.indexOf(curr.tokens.get(4).lexeme);
				rightValue = varValues.get(rightIndex);
				varValues.set(recipientIndex, leftValue - rightValue);
			}
			else if(curr.parsedID == 20)
			{
				recipientIndex = varNames.indexOf(curr.tokens.get(0).lexeme);
				leftIndex = varNames.indexOf(curr.tokens.get(2).lexeme);
				leftValue = varValues.get(leftIndex);
				rightIndex = varNames.indexOf(curr.tokens.get(4).lexeme);
				rightValue = varValues.get(rightIndex);
				varValues.set(recipientIndex, leftValue - rightValue);
			}
			else if(curr.parsedID == 21)
			{
				recipientIndex = varNames.indexOf(curr.tokens.get(0).lexeme);
				leftIndex = varNames.indexOf(curr.tokens.get(2).lexeme);
				leftValue = varValues.get(leftIndex);
				rightValue = Integer.parseInt(curr.tokens.get(4).lexeme);
				varValues.set(recipientIndex, leftValue - rightValue);
			}
			else if(curr.parsedID == 22)
			{
				recipientIndex = varNames.indexOf(curr.tokens.get(0).lexeme);
				leftValue = Integer.parseInt(curr.tokens.get(2).lexeme);
				rightValue = Integer.parseInt(curr.tokens.get(4).lexeme);
				varValues.set(recipientIndex, leftValue - rightValue);
			}
			// Multiplication
			else if(curr.parsedID == 23)
			{
				recipientIndex = varNames.indexOf(curr.tokens.get(0).lexeme);
				leftValue = Integer.parseInt(curr.tokens.get(2).lexeme);
				rightIndex = varNames.indexOf(curr.tokens.get(4).lexeme);
				rightValue = varValues.get(rightIndex);
				varValues.set(recipientIndex, leftValue * rightValue);
			}
			else if(curr.parsedID == 24)
			{
				recipientIndex = varNames.indexOf(curr.tokens.get(0).lexeme);
				leftIndex = varNames.indexOf(curr.tokens.get(2).lexeme);
				leftValue = varValues.get(leftIndex);
				rightIndex = varNames.indexOf(curr.tokens.get(4).lexeme);
				rightValue = varValues.get(rightIndex);
				varValues.set(recipientIndex, leftValue * rightValue);
			}
			else if(curr.parsedID == 25)
			{
				recipientIndex = varNames.indexOf(curr.tokens.get(0).lexeme);
				leftIndex = varNames.indexOf(curr.tokens.get(2).lexeme);
				leftValue = varValues.get(leftIndex);
				rightValue = Integer.parseInt(curr.tokens.get(4).lexeme);
				varValues.set(recipientIndex, leftValue * rightValue);
			}
			else if(curr.parsedID == 26)
			{
				recipientIndex = varNames.indexOf(curr.tokens.get(0).lexeme);
				leftValue = Integer.parseInt(curr.tokens.get(2).lexeme);
				rightValue = Integer.parseInt(curr.tokens.get(4).lexeme);
				varValues.set(recipientIndex, leftValue * rightValue);
			}
			// Division
			else if(curr.parsedID == 27)
			{
				recipientIndex = varNames.indexOf(curr.tokens.get(0).lexeme);
				leftValue = Integer.parseInt(curr.tokens.get(2).lexeme);
				rightIndex = varNames.indexOf(curr.tokens.get(4).lexeme);
				rightValue = varValues.get(rightIndex);
				varValues.set(recipientIndex, leftValue / rightValue);
			}
			else if(curr.parsedID == 28)
			{
				recipientIndex = varNames.indexOf(curr.tokens.get(0).lexeme);
				leftIndex = varNames.indexOf(curr.tokens.get(2).lexeme);
				leftValue = varValues.get(leftIndex);
				rightIndex = varNames.indexOf(curr.tokens.get(4).lexeme);
				rightValue = varValues.get(rightIndex);
				varValues.set(recipientIndex, leftValue / rightValue);
			}
			else if(curr.parsedID == 29)
			{
				recipientIndex = varNames.indexOf(curr.tokens.get(0).lexeme);
				leftIndex = varNames.indexOf(curr.tokens.get(2).lexeme);
				leftValue = varValues.get(leftIndex);
				rightValue = Integer.parseInt(curr.tokens.get(4).lexeme);
				varValues.set(recipientIndex, leftValue / rightValue);
			}
			else if(curr.parsedID == 30)
			{
				recipientIndex = varNames.indexOf(curr.tokens.get(0).lexeme);
				leftValue = Integer.parseInt(curr.tokens.get(2).lexeme);
				rightValue = Integer.parseInt(curr.tokens.get(4).lexeme);
				varValues.set(recipientIndex, leftValue / rightValue);
			}
		}
		return(output);
	}
}