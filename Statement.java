/*
 * Class: CS 4308 Section 1
 * Term: Spring 2021
 * Names: Matt Gibson,
 * 		Souleymane Camara,
 * 		and Zhuoxing Wang
 * Instructor: Deepa Muralidhar
 * Project: Deliverable 3 Scanner, Parser, and Interpreter - Java
 */

// Statement.java acts as a data structure to assist the parser

import java.util.*;

public class Statement
{
	// Instance variables
	
	// tokens stores the statement's tokens
	public List<Token> tokens = new ArrayList<Token>();
	
	// parseID represents the parser's evaluation of the statement
	// Check the written report for a lookup list of the IDs
	// (Also explained in Try.java on lines 29-33
	public int parsedID;
	
	// errorTokenIndex indiciates the token in the statement that
	// is causing errors caught by the parser. A value of -1 indicates
	// that the statement is error-free according to the parser.
	public int errorTokenIndex;
	
	// Standard constructor
	public Statement(List<Token> tokensIn, int parsedIDIn, int errorTokenIndexIn)
	{
		tokens = tokensIn;
		parsedID = parsedIDIn;
		errorTokenIndex = errorTokenIndexIn;
	}
	
	// Standard toString method
	public String toString()
	{
		String str = "\n";
		if(errorTokenIndex != -1)
		{
			str += "\nErrorID: " + parsedID;
			str += " Token#: " + errorTokenIndex;
		}
		else
		{
			str += "StatementID: " + parsedID;
		}
		str += " Line#: " + tokens.get(0).line;
		
		return(str);
	}
}