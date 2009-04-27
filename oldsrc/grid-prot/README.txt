Basic EXGOL proof of concept only supporting 
grid:=[NUM1][NUM2]

=== Usage ===
./exgol grid.exgol

will display a Java Swing Window with a grid of correct dimensions. Try and edit grid.exgol.

=== Tools ===
JFlex - Java lexical analyser with lex syntax
source file: grid.l

byacc/j - Java Parser Generator with yacc syntax
I simply replaced my default yacc binary with a downloaded byacc binary
source file: grid.y

Additional Java Sources: (Our graphical front-end will be in these files)
- GridPanel.java - Derived JPanel class

=== Compiling and running the compiler ===
yacc -J grid.y
jflex grid.l
javac *.java
java Parser grid.exgol

The central output file is Parser.class. An actual deliverable of the EXGOL compiler would consist of the 
 1 *.class files possibly bundled in a jar file.
 2 The exgol script, possibly re-written to a C application.

