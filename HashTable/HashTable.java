package HashTable;
import DList.*;

/***
*An Enhanced version of hashTable utilizing DList.
*
*
*
*
*
*/
public class HashTable{
	@SuppressWarnings("unchecked")
        public static int numX = 0;
        public static int numY = 0;
        public static int numO  = 0;
	protected DList<Object>[] chains;
	/**
	*  Constructor of the bucket.
*   @param numBuckets represents expected size of hashTable. 
	**/ 
	@SuppressWarnings("unchecked")
	public HashTable(int sizeExpected){
		int prime = sizeExpected;
		if(prime%2 == 0){
			prime++;  
		}
		boolean isPrime = false;
		while(!isPrime){
			for(int i = 2; i<prime;i++){
				if(prime%i == 0){
					isPrime = false;
					prime+=2;
					break;
				}
				else{
					isPrime = true;   
				}
			}
		}
		chains = new DList[prime];
		for (int i = 0; i < prime; i++){
			chains[i] = new DList<Object>();
		}
	}
	/**
	 *  Compresses the hash so it fits inside the bucket.
	 *	@param hash is the hash being compressed.
	 *	@return the compressed hash.
	**/
	public int compress(int hash){
		int compressed = (36*hash+1507)%74531;
		if(compressed<0){
			compressed += 74531;
		}
		compressed = compressed%chains.length;
		return compressed;
	}

	/**
	 *	Adds an entry to the HashTable.
	 *	@param 
	 *
	 *
	**/
	public void add(Object h, Object value){
		int hash = compress(h.hashCode());
		Entry keyValuePair = new Entry();
		keyValuePair.key = h;
		keyValuePair.value = value;
		chains[hash].pushFront(keyValuePair);
	}
        
	public Entry get(Object key){
		int hash = compress(key.hashCode());
		DListNode node = chains[hash].getFront();
		while(node!=null){
			Entry entry = (Entry)node.item();
			if(entry.key().equals(key)){
				return entry;
			}
			node = node.next();
		}
		return null;
	} 
        
         public static String randomBoard(){
            String a = "";
            for(int length = 0; length<8;length++){
                for(int width = 0; width<8;width++){
                    int random = (int)(Math.random()*3);
                    //empty
                    if(random == 0){
                        a = a + "O";
                        numO++;
                    }
                    //white
                    if(random == 1){
                        a = a + "X";
                        numX++;
                    }
                    //black
                    if(random == 2){
                        a = a + "Y";
                        numY++;
                    }
                }
            }
           return a;
        }
        public void countCollisions(){
            int collisions = 0;
            for(int index = 0; index<chains.length;index++){
                if(chains[index].length() == 0){
                    System.out.println("Nothing in bucket on index " + index);
                    continue;
                }
                System.out.println((chains[index].length())-1 + " collisions on index " + index);
                collisions += chains[index].length()-1;
            }
            System.out.println("collisions # is" + collisions);
        }
        
	public static void main(String[] args){
		HashTable t = new HashTable(6);
		Testhash h1 = new Testhash("animal");
		Testhash h2 = new Testhash("bat");
		Testhash h3 = new Testhash("cat");
		Testhash h4 = new Testhash("door");
		Testhash h5 = new Testhash("eels");
		Testhash h6 = new Testhash("foobarzsdf");
		t.add(h1,"gopher");
		t.add(h2,"walrus");
		t.add(h3,"wood chuck");
		t.add(h4,"honey badger");
		t.add(h5,"alpaca");
		t.add(h6,"narwahl");
		System.out.println(t.get(h6));
		System.out.println(t.get(h5));
		System.out.println(t.get(h4));
		System.out.println(t.get(h3));
		System.out.println(t.get(h2));
		System.out.println(t.get(h1));
                int numBoards = 100;
                Testhash2[] a = new Testhash2[numBoards];
                for(int b = 0; b<a.length;b++){
                    System.out.println(randomBoard());
                    a[b] = new Testhash2(randomBoard());
                }
                HashTable q = new HashTable(numBoards);
                for(int c = 0; c<a.length;c++){
                    q.add(a[c], "lulz");
                }
                q.countCollisions();
	}
}

class Testhash{
	String s;
public Testhash(String s){
		this.s=s;
	}
	public int hashCode(){
		int total=0;
		for (int i = 0; i<this.s.length(); i++){
			total+=this.s.charAt(i);
		}
		return total;
	}
	public Object getKey(){
		return s;
	}
}

class Testhash2{
    String s;
public Testhash2(String s){
		this.s=s;
	}
	public int hashCode(){
		int total=0;
                int pow = 1;
		for(int boardSize = 0; boardSize<64; boardSize++){
                    if(s.charAt(boardSize) ==  'X'){
                        total = total + pow*1;
                    }  
                    if(s.charAt(boardSize) == 'Y'){
                        total = total + pow*2;
                    }
                    pow *= 3;
                }
            return total;
	}
	public Object getKey(){
		return s;
	}
}



