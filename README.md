# KS_CodingChallenge
Kingsmen Apprenticeship Coding Challenge

Build Instructions:

1) Install Java 8

2) On Windows and Linux, install the latest JRE 8 from Oracle.

3) On Mac OS X, download and install JDK 8.

4) Clone the source repository from Github.

5) On the command line, enter: git clone https://github.com/cyanide284/KS_CodingChallenge.git
You can probably use Git for Windows or Git for Mac instead of the command line.

6) When building for the first time on Windows and Linux, you might need an internet connection, because additional files might be downloaded.

7) Open a terminal/console/command prompt, change to the directory where you cloned and type:
javac JSLint.java

8) Then type:
java JSLint

Reference: https://github.com/processing/processing/wiki/Build-Instructions

The program takes the filename (complete path) of the javascript file as the parameter.
eg) "D:\\user\\directory\\KS_CodingChallenge\\sample.js"

The source code consists of 1 interface and 3 classes. The interface JavaScriptLintInterface contains signature of methods which are used for JavaScript validations. The class JavaScriptLintImpl implements these methods to achieve the required tasks. Another class JavaScriptParser parses the JS file to output its content line by line into a HashMap to keep track of line numbers. JSLint is the class with the main method which is the entry point of the program.

