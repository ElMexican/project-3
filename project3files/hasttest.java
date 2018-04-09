 public class SeparateChainingHashTable<AnyType>
 {
	/**
	* Construct the hash table.
	*/
	public SeparateChainingHashTable( )
	{
	this( DEFAULT_TABLE_SIZE );
	}

	/**
	* Construct the hash table.
	* @param size approximate table size.
	*/
	public SeparateChainingHashTable( int size )
	{
	theLists = new LinkedList[ nextPrime( size ) ];
	for( int i = 0; i < theLists.length; i++ )
	theLists[ i ] = new LinkedList<>( );
	}

	/**
	* Make the hash table logically empty.
	*/
	public void makeEmpty( )
	{
	for( int i = 0; i < theLists.length; i++ )
	theLists[ i ].clear( );
	currentSize = 0;
	}
 
	/**
	* Find an item in the hash table.
	* @param x the item to search for.
	* @return true if x is not found.
	*/
	public boolean contains( AnyType x )
	{
	List<AnyType> whichList = theLists[ myhash( x ) ];
	return whichList.contains( x );
	}
	
	/**
	* Insert into the hash table. If the item is
	* already present, then do nothing.
	* @param x the item to insert.
	*/
	public void insert( AnyType x )
	{
	List<AnyType> whichList = theLists[ myhash( x ) ];
	if( !whichList.contains( x ) )
	{
	whichList.add( x );
	
	// Rehash; see Section 5.5
	if( ++currentSize > theLists.length )
	rehash( );
	}
	}
	
	/**
	* Remove from the hash table.
	* @param x the item to remove.
	*/
	public void remove( AnyType x )
	{
	List<AnyType> whichList = theLists[ myhash( x ) ];
	if( whichList.contains( x ) )
	{
	whichList.remove( x );
	currentSize--;
	}
	}
	


	private static final int DEFAULT_TABLE_SIZE = 101;

	private List<AnyType> [ ] theLists;
	private int currentSize;

	/**
	* Rehashing for quadratic probing hash table.
	*/
	private void rehash( )
	{
	HashEntry<AnyType> [ ] oldArray = array;
	
	// Create a new double-sized, empty table
	allocateArray( nextPrime( 2 * oldArray.length ) );
	currentSize = 0;
	
	// Copy table over
	for( int i = 0; i < oldArray.length; i++ )
	if( oldArray[ i ] != null && oldArray[ i ].isActive )
	insert( oldArray[ i ].element );
	}
	
	/*	*
	* Rehashing for separate chaining hash table.
	*/
	private void rehash( )
	{
	List<AnyType> [ ] oldLists = theLists;
	
	// Create new double-sized, empty table
	theLists = new List[ nextPrime( 2 * theLists.length ) ];
	for( int j = 0; j < theLists.length; j++ )
	theLists[ j ] = new LinkedList<>( );
	
	// Copy table over
	currentSize = 0;
	for( int i = 0; i < oldLists.length; i++ )
	for( AnyType item : oldLists[ i ] )
	insert( item );
	}

	private int myhash( AnyType x )
	{
	int hashVal = x.hashCode( );
	
	hashVal %= theLists.length;
	if( hashVal < 0 )
	hashVal += theLists.length;
	
	return hashVal;
	}

	private static int nextPrime( int n )
	{
		int k, candidate, loopLim;
		
		// loop doesn't work for 2 or 3
		if ( n <= 2 )
			return 2;
		else if ( n == 3 )
			return 3;
		
		for ( candidate = (n%2 == 0)? n+1 : n ; true ; candidate += 2)
		{
			// all primes > 3 are of the form 6k +/- 1
			loopLim = (int)( (Math.sqrt((double)candidate) + 1)/6);
			
			// we know it is odd. check for divisibility by 3
			if ( candidate % 3 == 0)
				continue;
			
			// now we can check for divisibility by 6k +/1 1 up to sqrt
			for ( k = 1; k <= loopLim; k++ )
			{
				if (candidate % (6*k - 1) == 0)
					break;
				if (candidate % (6*k + 1) == 0)
					break;
			}
			if ( k > loopLim)
				return candidate;
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

        return true; }
 }