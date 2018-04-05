import java.io.IOException;

/**
 * An executable that counts the words in a files and prints out the counts in
 * descending order. You will need to modify this file.
 */
public class WordCount {

    private static void countWords(String file) {
        DataCounter<String> counter = new BinarySearchTree<String>();

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
            System.out.println(c.count + " \t" + c.data);
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
	 
	 
	 
	 
	 
	 
    private static <E> void insertionSort(DataCount<E>[] dataCounts,
                                          int low, int high,
                                          Comparator<DataCount<E>> comparator) {
        for(int i = low + 1; i <= high; i++) {
            DataCount<E> key = dataCounts[i];
            int j = i - 1;
            while(j >= 0 && comparator.compare(key, dataCounts[j]) < 0) {
                dataCounts[j + 1] = dataCounts[j--];
            }
            dataCounts[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        if(args.length != 3) {
            System.out.println("Usage: [-b | -a | -h] [-is | -qs | -ms] <filename>\n");
            System.out.println("-b - Use an Unbalanced BST");
            System.out.println("-a - Use an AVL Tree");
            System.out.println("-h - Use a Hashtable\n");
            System.out.println("-is - Sorts using insertion sort");
            System.out.println("-qs - Sorts using quick sort");
            System.out.println("-ms - Sorts using merge sort");
            return;
        }
}
}
