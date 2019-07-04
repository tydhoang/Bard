/************************************************************
 * sortByFrequency.java
 * Tyler Hoang
 * Used to sort an ArrayList of type Node by frequency in descending order
 ************************************************************/
import java.util.*; 
public class sortByFrequency implements Comparator<Node>{

    public int compare(Node a, Node b) {
	if (a.frequency > b.frequency) { // if a is more frequent than b
	    return -1;
	}

	else if (a.frequency < b.frequency) { // if a is less frequent than b
	    return 1;
	}

	else { // if both a and b have the same frequency
	    if (a.word.compareTo(b.word) > 0) { // if a is lexicographically higher than b
		return 1;
	    }
	    else { // if a is lexicographically lower than b
		return -1;
	    }
	}
    }
}
