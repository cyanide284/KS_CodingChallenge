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
	public void findUnusedVariables(String fileName) {

		// data structure for storing declared variables
		HashMap<String, String> variables = new HashMap<String, String>();
		// data structure for storing all other tokens apart from variables
		HashMap<String, Integer> tokens = new HashMap<String, Integer>();

		HashMap<Integer, String> parsedJS = new JavaScriptParser().readFile(fileName);
		// Using entrySet as HashMap does not have an iterator
		Iterator iter = parsedJS.entrySet().iterator();

		// parsing the code to detect and store declared variables
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
				System.out.println("Unused variable on line " + pair.getValue());
			}

		}
	}

	// Method to find single-lined if else statements not enclosed in a code
	// block
	@Override
	public void findSingleLinedConditionals(String fileName) {
		HashMap<Integer, String> parsedJS = new JavaScriptParser().readFile(fileName);

		// Using entrySet as HashMap does not have an iterator
		Iterator iter = parsedJS.entrySet().iterator();

		while (iter.hasNext()) {
			Map.Entry pair = (Map.Entry) iter.next();
			String currentLine = pair.getValue().toString();
			if (currentLine.contains("if") || currentLine.contains("else")) {
				if (currentLine.trim().substring(0, 2).equals("if")
						|| currentLine.trim().substring(0, 4).equals("else")) {
					System.out.println(pair.getKey() + "." + " " + pair.getValue());
				}
			}
		}
	}

	@Override
	public void findUndeclaredFunctions(String fileName) {

		HashMap<Integer, String> parsedJS = new JavaScriptParser().readFile(fileName);
		//data structure to store declared functions
		HashMap<String, String> declaredFunctions = new HashMap<String, String>();
		//data structure to store called functions
		HashMap<String, String> calledFunctions = new HashMap<String, String>();
		Iterator iter = parsedJS.entrySet().iterator();

		while (iter.hasNext()) {
			Map.Entry pair = (Map.Entry) iter.next();
			String currentLine = pair.getValue().toString();
			
			//detecting declared functions using regexp and storing in data structure
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
			//pattern matching to find called functions and storing them in a data structure
			else if(Pattern.matches(".*(\050.*\051;)", currentLine.trim())){
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

		Iterator iter2 = calledFunctions.entrySet().iterator();
		while (iter2.hasNext()) {
			Map.Entry pair = (Map.Entry) iter2.next();
			System.out.println(pair.getKey() + " " + pair.getValue());
		}

	}

	// Method to find unbalanced or missing braces
	@Override
	public void findUnbalancedBraces(String fileName) {

		// using a stack to store the opening braces '{'
		Stack<Character> stack = new Stack<Character>();

		HashMap<Integer, String> parsedJS = new JavaScriptParser().readFile(fileName);
		Iterator iter = parsedJS.entrySet().iterator();

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
