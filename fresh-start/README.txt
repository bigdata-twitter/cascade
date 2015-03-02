In order to run the code on all files,
In Chunking.java -> Comment lines 8-9
					Uncomment lines 11-26
					Uncomment line 84
					Comment lines 85

In ChunkedChildren.java ->	Comment lines 34-35
							Uncomment lines 37-52
							Uncomment line 55
							Comment lines 56

Step 0 ->	ls rt-rep-data* > files

Step 1 -> 	javac Chunking.java
			java Chunking 1> Chunking_out 2> Chunking_err &

Step 2 ->	javac ChunkedChildren.java
			java ChunkedChildren 1> Chunkedchildren_out 2> Chunkedchildren_err &

Step 3 ->	g++ -std=c++11 MakeTree.cpp -o tree.out
			
Step 4 ->	ls child_list* > files
			delete the lines corresponding to the files that are not needed
			./tree.out 1> tree1.json 2> err1.txt &

Repeat step 4 for all the combinations of child_list that are of interest ie 0-9, 10-19, 20-29, 30-40

The code for the combination of the trees is still to be written. Perhaps it can be better written down in python?