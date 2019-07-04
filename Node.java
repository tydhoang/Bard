/************************************************************
 * Node.java
 * Tyler Hoang
 * Node class used to create a new Node to be added to an ArrayList
 ************************************************************/
public class Node {
    String word;
    int frequency;
    
    public Node (String w, int f) { // frequency is initially set to 0 in Bard.java
	word = w;
	frequency = f;
    }
}
