/*
 * Class: CS 4308 Section 1
 * Term: Spring 2021
 * Names: Matt Gibson,
 * 		Souleymane Camara,
 * 		and Zhuoxing Wang
 * Instructor: Deepa Muralidhar
 * Project: Deliverable 3 Scanner, Parser, and Interpreter - Java
 */

// Try.java hosts the main method for the project

import java.util.*;

public class Try
{
	// The main method demonstrates the deliverable with a very simple BASIC program
	public static void main(String[] args)
	{
		ScannerParserAndInterpreter b = new ScannerParserAndInterpreter("/Users/Matt/Desktop/Workspace/Input.txt");
		// Make sure you change the file path to the BASIC program accordingly!
		
		// These methods call the scanner, feel free to
		// add print statements to them to see output
		b.scanFile();
		System.out.println("Scanner Output:\n" + b.registerTokens());
		
		// Calling parseFind runs the parser through the file
		// The print statement shows the finalized output
		
		// NOTE: Because the required printing takes up so much
		// space, StatementID/ErrorID indicate the type of expression
		// found by the parser on the cooresponding line. A lookup list
		// for both of these IDs can be found in the written report.
		System.out.println("\nParser Output:\n" + b.parseFind());
		
		// process runs the interpreter through the file
		// The print statement shows the output of the program
		System.out.println("\nInterpreter Output:\n" + b.process());
	}
}