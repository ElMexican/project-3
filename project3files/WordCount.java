import java.io.IOException;

/**
 * An executable that counts the words in a files and prints out the counts in
 * descending order. You will need to modify this file.
 */
public class WordCount {
	private static double CountTotal = 0;

    static DataCount<String>[] countWords(String file, DataCounter<String> structChoice, String arg) {
        DataCounter<String> counter = structChoice;

        try {
            FileWordReader reader = new FileWordReader(file);
            String word = reader.nextWord();
            while (word != null) {
                counter.incCount(word);
                word = reader.nextWord();
            }
        } catch (IOException e) {
            System.err.println("Error processing " + file + e);
            System.exit(1);
        }

        DataCount<String>[] counts = counter.getCounts();
        sortByDescendingCount(counts);
        for (DataCount<String> c : counts)
            CountTotal += c.count;
        if (arg.equals("-frequency")) {
        	System.out.println("Only printing word with frequency < 1% & > .01% ");
        	for (DataCount<String> c : counts) 
        		if (c.count / CountTotal <= 0.01 && c.count / CountTotal >= 0.0001)
        			System.out.println(c.count + "\t" + c.data);
        }
        else if (arg.equals("-num_unique")) {
        	System.out.println("Unique words: " + counts.length);
        }
        else if (arg.equals("skip")) {
        	;
        }
        else {
        	System.out.println("Invalid 2nd argument");
        	System.exit(1);
        }
		return counts;
        }

    /**
     * TODO Replace this comment with your own.
     * 
     * Sort the count array in descending order of count. If two elements have
     * the same count, they should be in alphabetical order (for Strings, that
     * is. In general, use the compareTo method for the DataCount.data field).
     * 
     * This code uses insertion sort. You should modify it to use a different
     * sorting algorithm. NOTE: the current code assumes the array starts in
     * alphabetical order! You'll need to make your code deal with unsorted
     * arrays.
     * 
     * The generic parameter syntax here is new, but it just defines E as a
     * generic parameter for this method, and constrains E to be Comparable. You
     * shouldn't have to change it.
     * 
     * @param counts array to be sorted.
     */
    private static <E extends Comparable<? super E>> void sortByDescendingCount(
            DataCount<E>[] counts) {
        for (int i = 1; i < counts.length; i++) {
            DataCount<E> x = counts[i];
            int j;
            for (j = i - 1; j >= 0; j--) {
                if (counts[j].count >= x.count) {
                    break;
                }
                counts[j + 1] = counts[j];
            }
            counts[j + 1] = x;
        }
    }

    public static void main(String[] args) {
    	 DataCounter<String> structChoice = null;
        
         
        if (args.length != 3) {
            System.err.println("Usage: java WordCount [ -b | -a | -h ] [ -frequency | -num_unique ] <filename>");
            System.exit(1);
        }
   
        
        switch(args[0]) {
        case "-b":
        	structChoice = new BinarySearchTree<String>();
        	break;
        case "-a":
        	structChoice = new AVLTree<String>();
        	break;
        case "-h":
        	structChoice = new HashTable();
        	break;
        default:
        	System.out.println("Ivalid Choice for first argument");
        	System.exit(1);
        	break;
        }
       
       	countWords(args[2], structChoice, args[1]);
       

    }
}
