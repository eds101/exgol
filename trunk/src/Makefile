ARG=
all:
	jflex exgol.l
	byacc -J exgol.y
	javac -d build Parser.java

test:
	javac -d build TestMain.java
	cd build; java TestMain

run:
	cp $(ARG) build; cd build; java Parser $(ARG); rm $(ARG)

clean:
	rm -rf build/* ParserVal.java Yylex.java Parser.java
