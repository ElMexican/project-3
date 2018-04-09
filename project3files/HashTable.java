import java.util.LinkedList;


public class HashTable implements DataCounter<String> {
    /**
     * Inner class that holds key and value data to be put into hash table.
     * Also holds the count of the value to be used in WordCount and DataCounter implementations
     */
	private class Struct {
		private String value;
		private String key;
		private int count;
		
		public Struct(String value) {
			this.count = 1;
			this.value = value;
			this.key = value;
		}

		public String getValue() {
			return this.value;
		}
		public String getkey() {
			return this.key;
		}
		public int getCount() {
			return count;
		}
		public void incCount() {
			this.count++;;
		}
	}
	
    private static boolean isPrime( int n )
    {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }

	/**
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
    private static int nextPrime( int n )
    {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }
	
	static final int INIT_TABLE_SIZE = 11;	
	static final double INIT_MAX_LAMBDA = 1.5;
	
	private LinkedList<Struct>[] table;
	private int count;
	private int tableSize;
	private double Lambda;
	

	public HashTable() {
	   this(INIT_TABLE_SIZE);
	}
	
	public HashTable(int size) {
		count = 0;
		if (size < INIT_TABLE_SIZE)
			tableSize = INIT_TABLE_SIZE;
		else
			tableSize = nextPrime(size);
		
		intializeTable();
		Lambda = INIT_MAX_LAMBDA;
	}
	
	@SuppressWarnings("unchecked")
	private void intializeTable() {
		table = new LinkedList[tableSize];
		for(int i = 0;i<tableSize;i++) {
			if(table[i] == null)
				table[i] = new LinkedList<Struct>();
		}
	}
	
	/** {@inheritDoc} */
	public DataCount<String>[] getCounts() {
		@SuppressWarnings("unchecked")
		DataCount<String>[] counter =  new DataCount[count];
    	int k = 0;
    	for(int i = 0; i < table.length; i++) {
    		if(!table[i].isEmpty()) {
    			for(int j = 0;j < table[i].size();j++) {
    				counter[k]= new DataCount<String>(table[i].get(j).getValue(), table[i].get(j).getCount());
    				k++;
    			}
    		}
    	}

        return counter;
    }

    /** {@inheritDoc} */
    public int getSize() {
        // TODO Auto-generated method stub
    	return count;
    }

    /** {@inheritDoc} */
    public void incCount(String data) {
        // TODO Auto-generated method stub
    	Struct newStruct = new Struct(data);
		int index = -1;

		if(contains(newStruct.getkey())) {
			newStruct = get(newStruct.getkey());
			newStruct.incCount();
		}
		else {
			index = hashingFunction(newStruct.getkey());
			table[index].add(newStruct);
			count++;
		}
		
		if ( count > Lambda * tableSize )
			reSize();
		
    }
    
    public Struct get(String key) {
		int index = hashingFunction(key);
		Struct targetValue = null;
		for(int i = 0; i < table[index].size(); i++) { //searching linked list at index for key value
			if(table[index].get(i).getkey().equals(key)) {
				targetValue = table[index].get(i);
				break;
			}
		}
		return targetValue;
	}
    
    public Boolean contains(String key) {
		Boolean found = false;
    	int index = hashingFunction(key);
		
    	for(int i = 0; i < table[index].size(); i++) {
			if(table[index].get(i).getkey().equals(key)) {
				found = true;
			}
		}
			
		return found;
    }
	

	
    private int hashingFunction(String key)
	{
		int index;
		
		index = _hashingFunction(key) % tableSize;
		if (index < 0 )
			index += tableSize;
		
		return index;
	}
	
	// helper method to calculate hash
	private int _hashingFunction( String key )
	{
		int index = 0;
		char [] val = key.toCharArray();
		
		for ( int i = 0; i < key.length(); i++ )
			index = 31 * index + val[i];
		
		return index;
	}

	private void reSize()
	{
		// save old list and size then can allocate freely
		LinkedList<Struct>[] oldTable = table;
		int oldTableSize = tableSize;
		
		tableSize = nextPrime(2*oldTableSize);
		
		// allocate a larger, empty array
		intializeTable();
		
		// use the reinsert() algorithm to re-enter old data
		for (int i = 0; i < oldTableSize; i++ )
			for ( int j = 0; j < oldTable[i].size(); j++)
				reInsert( oldTable[i].get(j) );
	}

    //helper function to re-insert data into new table
    private void reInsert(Struct Struct) {
		int index = hashingFunction(Struct.getkey());
		table[index].add(Struct);
    }
    
	public boolean setMaxLambda( double lam )
	{
		if ( lam < .1 || lam > 100.)
			return false;
		Lambda = lam;
		return true;
	}
	
	 /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     * @param n the number to test.
     * @return the result of the test.
     */

}