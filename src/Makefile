all:
	jflex exgol.l
	byacc -J exgol.y
	javac -d build Parser.java
	cp grid.exgol build; cd build; java Parser grid.exgol; rm grid.exgol

test:
	javac -d build TestMain.java
	cd build; java TestMain

clean:
	rm -rf build/* ParserVal.java Yylex.java Parser.java
