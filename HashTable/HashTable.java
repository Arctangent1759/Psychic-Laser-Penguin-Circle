package HashTable;
import HashTable.BucketList.BucketList;
public class HashTable{
	protected BucketList[] chains;
	public HashTable(int numBuckets){
		chains = new BucketList[numBuckets];
		for (int i = 0; i < numBuckets; i++){
			chains[i] = new BucketList(i);
		}
	}
	public void add(Hashable h, Object value){
		chains[h.getHash()%chains.length].addItem(h.getKey(),value);
	}
	public Object get(Hashable h){
		return chains[h.getHash()%chains.length].getValueByKey(h.getKey());
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
		BucketList[] b = t.chains;
		for (int i =0; i < b.length; i++){
			System.out.println(b[i]);
		}
	}
}
class Testhash implements Hashable{
	String s;
	public Testhash(String s){
		this.s=s;
	}
	public int getHash(){
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
