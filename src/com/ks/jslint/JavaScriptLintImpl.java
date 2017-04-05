package com.ks.jslint;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import com.ks.parser.JavaScriptParser;

public class JavaScriptLintImpl implements JavaScriptLintInterface {

	// method to detect all declared variables
	@Override
	public HashMap<String, String> detectVariables(String fileName) {
		// data structure for storing declared variables
		HashMap<String, String> variables = new HashMap<String, String>();

		HashMap<Integer, String> parsedJS = new JavaScriptParser().readFile(fileName);
		// Using entrySet as HashMap does not have an iterator
		Iterator iter = parsedJS.entrySet().iterator();

		// parsing the code to detect and store declared variables
		while (iter.hasNext()) {

			Map.Entry pair = (Map.Entry) iter.next();
			String currentLine = pair.getValue().toString();

			if (!currentLine.contains("function")
					&& (currentLine.contains("var") || currentLine.contains("const") || currentLine.contains("let"))) {
				if (currentLine.trim().substring(0, 3).equals("var") || currentLine.trim().substring(0, 3).equals("let")
						|| currentLine.trim().substring(0, 5).equals("const")) {
					String substr[] = currentLine.trim().split(" ");
					for (String s : substr) {
						if (!s.equals("var")) {
							if (s.contains("=")) {
								String subsubstr[] = s.split("=");
								variables.put(pair.getKey().toString(),subsubstr[0]);
								break;
							} else if (s.contains(";")) {
								String subsubstr[] = s.split(";");
								variables.put(pair.getKey().toString(),subsubstr[0]);
								break;
							} else {
								variables.put(pair.getKey().toString(), substr[1]);
								break;
							}

						}

					} // end of for loop

				} // end of if to detect var,let,const

			} // end of outermost if

		} // end of iterating over entryset

		Iterator iter2 = variables.entrySet().iterator();
		while (iter2.hasNext()) {
			Map.Entry pair = (Map.Entry) iter2.next();
			System.out.println(pair.getKey() + " " + pair.getValue());

		}
		return variables;
	}

	@Override
	public void findUnusedVariables(String fileName, HashMap<String, String> variables) {

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
		// TODO Auto-generated method stub

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
