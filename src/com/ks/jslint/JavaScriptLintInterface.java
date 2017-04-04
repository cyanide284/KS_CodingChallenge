package com.ks.jslint;

public interface JavaScriptLintInterface {
	
	public void findUnusedVariables();
	
	public void findSingleLinedConditionals();
	
	public void findUndeclaredFunctions();
	
	public void findUnbalancedBraces();	

}
