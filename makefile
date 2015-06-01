JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class: 
	$(JC) $*.java

CLASSES = \
	LeftistTreeNode.java\
	LeftistTree.java\
	BinomialHeapNode.java\
	BinomialHeap.java\
	heap.java\
	
default: classes


classes: $(CLASSES:.java=.class)

all:
	$(JC) $(JFLAGS) $ LeftistTreeNode.java
	$(JC) $(JFLAGS) $ LeftistTree.java
	$(JC) $(JFLAGS) $ BinomialHeapNode.java
	$(JC) $(JFLAGS) $ BinomialHeap.java
	$(JC) $(JFLAGS) $ heap.java
		
clean:
	$(RM)  *.class
