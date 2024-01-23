MANIFEST-PATH = ./manifest.txt
CLASS-FOLDER = ./bin
TARGET-FILE = program.jar

JAVA-CLASSES = ./src/org/michaelb/lab3/*.java src/org/michaelb/lab3/story/*.java

JC = javac
JC-FLAGS = -d $(CLASS-FOLDER) 

JAR = jar
JRE-FLAGS = cmf $(MANIFEST-PATH) $(TARGET-FILE) $(CLASS-FOLDER)

RM = rm
RM-FLAGS = -rf

all: $(TARGET-FILE)

$(TARGET-FILE): bin
	$(JAR) $(JRE-FLAGS)

bin:
	$(JC) $(JC-FLAGS) $(JAVA-CLASSES)

clean:
	$(RM) $(RM-FLAGS) $(CLASS-FOLDER)
