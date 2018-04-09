public class Correlator {
	private static double total1 = 0, total2 = 0, RunningSum =0;
	
	private static void correlator(DataCount<String>[] file1, DataCount<String>[] file2) {
	for (DataCount<String> c : file1)
		total1 += c.count;
	for (DataCount<String> c : file2)
		total2 += c.count;
	for (DataCount<String> c1: file1)
		if (c1.count/total1 <= 0.01 &&  c1.count/total1 >= 0.0001)
			for (DataCount<String> c2: file2)
				if ( c2.count/total2 <= 0.01 &&  c2.count/total2 >= 0.0001)
					if( c1.data.equals(c2.data) )
						RunningSum += Math.pow(  Math.abs(c1.count - c2.count), 2.);
	
	
	}
	    public static void main(String[] args) {
	    	 DataCounter<String> structChoice = null;
	    	 DataCount<String>[] countWords1 = null;
	    	 DataCount<String>[] countWords2 = null;
	        
	         
	        if (args.length != 3) {
	            System.err.println("Usage: java WordCount [ -b | -a | -h ] <filename> <filename>");
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
	       
	       	countWords1 = WordCount.countWords(args[1], structChoice, "skip");
	       	countWords2 = WordCount.countWords(args[2], structChoice, "skip");
	       	correlator(countWords1, countWords2);
	       	System.out.printf("The difference metric between %s%n and %s%n is %.0f", args[1], args[2], RunningSum);
	       

	    }
	}


