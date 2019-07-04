/************************************************************
 * Bard.java
 * Tyler Hoang
 * Prints out the user's requested word of specified length and rank from shakespeare.txt
 *
 * Program creates an array of ArrayLists of type Node (Node being the word and its respective frequency)
 * The indexes of the array correspond to the length of the words in each ArrayList. Each ArrayList contains
 * Nodes of the words of the respective length. Each ArrayList is sorted from most frequent to least frequent.
 ************************************************************/
import java.util.*;
import java.io.*;

public class Bard {

    public static void main (String[] args) throws IOException, FileNotFoundException {
	int length = 0; // Will be used to determine the longest word in shakespeare.txt (it's 36)
        Scanner shakespeareIn = new Scanner(new File("shakespeare.txt")); // this Scanner is specifically for shakespeare.txt
        while (shakespeareIn.hasNextLine()) {
            String line = shakespeareIn.nextLine().trim() + " ";
            for (int i = 0; i < line.length(); i++) { // iterate through each character of each line of shakespeare.txt
                char ch = line.charAt(i);
                if ((ch == '?') || (ch == ',') || (ch == '.') || (ch == '!') || (ch == ':') || (ch == ';') || (ch == '[') || (ch == ']')) {
                    line = line.replace(ch, ' '); // remove any of the specified characters and replace with a whitespace
                }
            }
            String[] sentence = line.split("\\s+"); // now all the words in the line are tokenized
            for (int i = 0; i < sentence.length; i++) {
		if (length < sentence[i].length()) { 
		    length = sentence[i].length(); // set length to the be the length of the longest word
		}
            }
        }
	ArrayList<Node>[] wordLengths = new ArrayList[length]; // create an array of ArrayLists of type Node (essentially creating a hashtable)
	for (int i = 0; i < wordLengths.length; i++) {
	    wordLengths[i] = new ArrayList<Node>(); // create an ArrayList of type Node for every element in wordLengths[]
	}
	Scanner newShakespeareIn = new Scanner(new File("shakespeare.txt")); // this Scanner is used to scan through shakespeare.txt again
	while(newShakespeareIn.hasNextLine()) {
	    String line = newShakespeareIn.nextLine().trim() + " ";
	    for (int i = 0; i < line.length(); i++) { // iterate through each character of each line of shakespeare.txt
                char ch = line.charAt(i);
                if ((ch == '?') || (ch == ',') || (ch == '.') || (ch == '!') || (ch == ':') || (ch == ';') || (ch == '[') || (ch == ']')) {
                    line = line.replace(ch, ' '); // remove any of the specified characters and replace with whitespace
                }
            }
            line = line.toLowerCase(); // set line to be all lowercase
            String[] sentence = line.split("\\s+"); // now all the words in the line are tokenized
	    for (int i = 0; i < sentence.length; i++) { // iterate through each word of the current sentence
		boolean inTable = false; // will be used to see if the current word is already in the array of ArrayLists
		Node n = new Node(sentence[i], 0); // create a new Node with the word being the current word and the frequency set to 0
		int wordLength = sentence[i].length(); // set wordLength to the length of the current word of the current sentence
	
		if (wordLength == 0) { // if the length of the current word is 0, move onto the next word
		    continue;
		}

		// The indexes of the array directly correspond to the lengths of the words contained in each respective ArrayList
		// For example, wordLengths[5] contains an ArrayList of the words of length 6 (due to the way indexes work, the length is +1)

		if (wordLengths[wordLength - 1].size() == 0) { // if the ArrayList for this word length is empty
		    wordLengths[wordLength - 1].add(n); // add this word to the ArrayList
		    continue;
		}

		else{ // if the ArrayList for the length of this word is not empty and the length of the word is not 0
		    for (int j = 0; j < wordLengths[wordLength - 1].size(); j++) { // iterate through this ArrayList
			if (wordLengths[wordLength - 1].get(j).word.equals(n.word)) { // if this word is already in this ArrayList
			    wordLengths[wordLength - 1].get(j).frequency++; // increment the word's (Node's) frequency by 1
			    inTable = true; // inTable is set to true since the word was already in the ArrayList
			    break;
			}
		    }
		    if (inTable == false) { // if the word was not found in the ArrayList
			wordLengths[wordLength - 1].add(n); // add the word to the ArrayList
		    }
		}		
	    }
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////
	// shakespeare.txt will no longer be used from this point forward. All the information we need is
	// now in the array of ArrayLists.
	//////////////////////////////////////////////////////////////////////////////////////////////////

	// sort each ArrayList in the array
	for (int i = 0; i < wordLengths.length; i++ ) {
	    Collections.sort(wordLengths[i], new sortByFrequency());
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////
	// now look at the user inputs
	//////////////////////////////////////////////////////////////////////////////////////////////////

	if (args.length < 2) { // if the user does not include an input and output file
	    System.out.println("Usage: java –jar Bard.jar <input file> <output file>");
	    System.exit(1);
	}

	// open files
	Scanner in = new Scanner(new File(args[0]));
	PrintWriter out = new PrintWriter(new FileWriter(args[1]));

	// scans the next line of the input file and splits the numbers into individual tokens,
	// which will then become the elements of the input array 
	while (in.hasNextLine()) {
	    String line = in.nextLine().trim(); // remove any leading and trailing white space
	    String[] token = line.split("\\s+"); // split the string by the white spaces
	    int[] input = new int[token.length]; // create an int array for the user inputs
	    for (int i = 0; i < token.length; i++) {
		input[i] = Integer.parseInt(token[i]); // converts the tokens from strings to ints
	    }

	    int userLength = input[0]; // set userLength to the first number of the current input
	    int userRank = input[1]; // set userRank to the second number of the current input

	    if (wordLengths.length < userLength) { // if the user's requested length is more than the length of the longest word
	                                           // if the request length is > 36
		out.println("-");
		continue;
	    }

	    if (wordLengths[userLength - 1].size() == 0) { // if the ArrayList for the requested length is empty
		out.println("-");
		continue;
	    }

	    if (wordLengths[userLength - 1].size() <= userRank) { // if the requested rank is larger than the size of the ArrayList
		                                                  // for example: if the requested rank is 7, and the ArrayList only has 5 words
		out.println("-");
		continue;
	    }

	    else{ // if the user's requested length and requested rank are within the dimensions of the array and respective ArrayList
		out.println((wordLengths[userLength - 1].get(userRank)).word); // print the word from the respective ArrayList
	    }
	}

	// close all the Scanners
	in.close();
	out.close();
	shakespeareIn.close();
	newShakespeareIn.close();
    }
}
