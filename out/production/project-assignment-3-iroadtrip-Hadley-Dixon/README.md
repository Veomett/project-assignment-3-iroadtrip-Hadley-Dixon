# CS 245 (Fall 2023) - Assignment 3 - IRoadTrip

Can you plan the path for a road trip from one country to another?

Change the java source code, but do not change the data files. See Canvas for assignment details.

## Sources used
(1) Graph structure: https://www.geeksforgeeks.org/implementing-generic-graph-in-java/ 

(2) Scanner on .txt: https://www.geeksforgeeks.org/different-ways-reading-text-file-java/

(3) Exceptions: https://www.tutorialspoint.com/javaexamples/exception_method.htm

(4) Scanner on .csv: https://www.youtube.com/watch?v=rj6vyIn90zk

(5) Scanner .tsv: https://codepal.ai/code-generator/query/0pkdvNiV/java-program-read-student-information

(6) Scanner for user input: https://www.theserverside.com/tutorial/Your-top-Java-user-input-strategies

## Initial strategy notes from Prof. Veomett (given in class)
- Read in borders.txt for nodes and adjacent vertices, then use the other files to fill in the distances later. 
- Then you read in .tsv so that if a country has an end date that ended in 2020, I put that country into a hashmap with the country name and the country code. 
- Then when I read in the .csv file, I start putting in the distances to the graph (weights of the edges). 
- Use the hashmap of country name to country code to see if the connection is something in my graph, and if so where do i put it. is US and canada an edge in my graph?

- Challenges I encountered: South Sudan, Kosovo, ireland, UK