JFLAGS = -g
JC = javac
JVM= java
FILE=
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java
CLASSES = \
	assignmentP3.java \
	DataStructures.java \
	FileHandler.java \
	Output.java \
	Parser.java \
	Dictionary.java \
	DocInfo.java \
	Postings.java \
	Populate.java
    
MAIN = Main

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN)

clean:
	$(RM) *.class
