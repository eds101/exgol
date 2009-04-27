all:
	jflex exgol.l
	byacc -J exgol.y
	javac Parser.java

test:
	java Parser grid.exgol
	java Parser testmain.exgol
