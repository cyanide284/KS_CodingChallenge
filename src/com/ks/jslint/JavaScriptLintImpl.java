package com.ks.jslint;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

import com.ks.parser.JavaScriptParser;

public class JavaScriptLintImpl implements JavaScriptLintInterface {

	// method to detect unused variables
	@Override
	public void findUnusedVariables(HashMap<Integer,String> parsedJS) {

		// data structure for storing declared variables
		HashMap<String, String> variables = new HashMap<String, String>();
		// data structure for storing all other tokens apart from variables
		HashMap<String, Integer> tokens = new HashMap<String, Integer>();

		// Using entrySet as HashMap does not have an iterator
		Iterator iter = parsedJS.entrySet().iterator();
		
		System.out.println("These variables are declared but unused:");
		/* parsing each entry of the hashmap to detect and store declared variables
		 * key of the map is the line number and value is the code at that line
		*/
		while (iter.hasNext()) {

			Map.Entry pair = (Map.Entry) iter.next();
			String currentLine = pair.getValue().toString();

			if ((currentLine.contains("var") || currentLine.contains("const") || currentLine.contains("let"))) {
				if (currentLine.trim().substring(0, 3).equals("var") || currentLine.trim().substring(0, 3).equals("let")
						|| currentLine.trim().substring(0, 5).equals("const")) {
					String substr[] = currentLine.trim().split(" ");
					for (String s : substr) {
						if (!s.equals("var")) {
							if (s.contains("=")) {
								String subsubstr[] = s.split("=");
								variables.put(subsubstr[0], pair.getKey().toString());
								break;
							} else if (s.contains(";")) {
								String subsubstr[] = s.split(";");
								variables.put(subsubstr[0], pair.getKey().toString());
								break;
							} else {
								variables.put(substr[1], pair.getKey().toString());
								break;
							}

						}

					} // end of for loop

				} // end of if to detect var,let,const

				// storing all other tokens/keywords in another hashmap tokens
			} else {
				String token[] = currentLine.trim().split("=|\\.| ");
				for (String s : token) {
					if (tokens.containsKey(s)) {
						tokens.replace(s, tokens.get(s) + 1);
					} else {
						tokens.put(s, 1);
					}
				}
			}

		} // end of iterating over entryset

		// comparing both hashmaps and checking for unused variables
		Iterator iter2 = variables.entrySet().iterator();
		while (iter2.hasNext()) {
			Map.Entry pair = (Map.Entry) iter2.next();
			// System.out.println(pair.getKey() + " " + pair.getValue());
			if (!tokens.containsKey(pair.getKey())) {
				System.out.println("Unused variable " + pair.getKey() + " on line " + pair.getValue());
			}			
		}
		System.out.println();
	}

	// Method to find single-lined if else statements not enclosed in a code
	// block
	@Override
	public void findSingleLinedConditionals(HashMap<Integer,String> parsedJS) {
		
		// Using entrySet as HashMap does not have an iterator
		Iterator iter = parsedJS.entrySet().iterator();
		
		System.out.println("Please correct the following single-lined conditional statements:");
		System.out.println("Line Number\t Code");
		
		//parsing each entry in the hashmap to locate single-lined conditions
		while (iter.hasNext()) {
			Map.Entry pair = (Map.Entry) iter.next();
			String currentLine = pair.getValue().toString();
			if (currentLine.contains("if") || currentLine.contains("else")) {
				if (currentLine.trim().substring(0, 2).equals("if")
						|| currentLine.trim().substring(0, 4).equals("else")) {
					System.out.println(pair.getKey() + "\t" + pair.getValue());
				}
			}
		}
	}

	//method to find undeclared functions that are being used in the js
	@Override
	public void findUndeclaredFunctions(HashMap<Integer,String> parsedJS) {

		//data structure to store declared functions
		HashMap<String, String> declaredFunctions = new HashMap<String, String>();
		//data structure to store called functions
		HashMap<String, String> calledFunctions = new HashMap<String, String>();
		Iterator iter = parsedJS.entrySet().iterator();

		while (iter.hasNext()) {
			Map.Entry pair = (Map.Entry) iter.next();
			String currentLine = pair.getValue().toString();
			
			/*detecting declared functions using regexp and storing in data structure
			 * case 1: "var ab = function(String abc)"
			 * case 2: "function abc(String abc)"
			 * case 3: "get abc()"
			 * case 4: "set abc()"
			 */
			if (currentLine.trim().matches("(.*)=(.*)function(.*)")) {
				System.out.println("inside 1 " + currentLine);
				String substr[] = currentLine.split("=");
				String subsubstr[] = substr[0].split(" ");
				declaredFunctions.put(pair.getKey().toString(), subsubstr[1]);
			} else if (currentLine.trim().matches("function(.*)")) {
				System.out.println("inside 2nd " + currentLine);
				String substr[] = currentLine.split("[(]");
				String subsubstr[] = substr[0].split(" ");
				declaredFunctions.put(pair.getKey().toString(), subsubstr[1]);
			} else if (currentLine.trim().matches("get (.*)") || currentLine.trim().matches("set (.*)")) {
				System.out.println("inside 3 "+ currentLine);
				String substr[] = currentLine.split("[(]");
				String subsubstr[] = substr[0].split(" ");
				declaredFunctions.put(pair.getKey().toString(), subsubstr[1]);
			} 
			/*pattern matching to find called functions and storing them in a data structure
			 *incomplete implementation right now. for some reason regexp is not working. tried on online testers and it works.
			 *regexp is detecting extra code as well such as }); or 2; 
			 */
			else if(Pattern.matches("(\\(.*\\);)", currentLine.trim())){
				//System.out.println("inside 4 " + currentLine);
				String substr[] = currentLine.split("[(]");
				if(substr[0].contains("=")){
					String subsubstr[] = substr[0].trim().split("=");
					calledFunctions.put(pair.getKey().toString(), subsubstr[1].trim());
				}
				else{
					calledFunctions.put(pair.getKey().toString(),substr[0].trim());
				}
				
			}

		}
		

	}

	// Method to find unbalanced or missing braces
	@Override
	public void findUnbalancedBraces(HashMap<Integer,String> parsedJS) {

		// using a stack to store the opening braces '{'
		Stack<Character> stack = new Stack<Character>();

		Iterator iter = parsedJS.entrySet().iterator();
		
		System.out.println();
		/*
		 * if an open brace is detected it is stored in the stack. on detecting a closing brace
		 * the top element on the stack is popped out.
		 * if a closing bracket is encountered and the stack is empty, it means this is extra closing bracket.
		 * if we reach end of file and there is a extra remaining brace in the stack, that means there is a missing closing bracket
		 * or it is also possible that the opening bracket is extra
		 */
		while (iter.hasNext()) {
			Map.Entry pair = (Map.Entry) iter.next();
			String currentLine = pair.getValue().toString();
			for (int i = 0; i < currentLine.length(); i++) {
				char ch = currentLine.charAt(i);
				if (ch == '{') {
					stack.push(ch);
				} else if (ch == '}') {
					if (stack.isEmpty()) {
						System.out.println("Extra closing bracket on line " + pair.getKey());
					} else {
						stack.pop();
					}
				}
			}

		}
		if (!stack.empty()) {
			System.out.println("Missing curly bracket");
		}

	}

}
