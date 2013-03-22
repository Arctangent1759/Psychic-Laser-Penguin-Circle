package HashTable.BucketList;
import HashTable.BucketList.DList.DList;
public class BucketList{

	protected DList myList;

	protected int hashVal;

	public BucketList(int hashVal){
		this.hashVal=hashVal;
		this.myList=new DList();
	}
	public void addItem(Object key,Object value){
		myList.pushFront(new KeyValPair(key,value));
	}

	public Object getValueByKey(Object key){
		myList.reset();
		KeyValPair curr=null;
		do{
			curr=(KeyValPair)myList.next();
			if (curr!=null && curr.key.equals(key)){
				return curr.value;
			}
		}while (curr!=null);
		return null;
	}

	public int getHashVal(){
		return this.hashVal;
	}

	public int size(){
		return myList.length();
	}

	public String toString(){
		return myList.toString();
	}

	public static void main(String[] args){
		BucketList b = new BucketList(0);
		b.addItem("cake","A delicious snack");
		b.addItem("lampshade","wisdom");
		b.addItem("alex","chu");
		b.addItem("full","cup");
		b.addItem("table","chair");
		System.out.println(b.getValueByKey("cake"));
		System.out.println(b.getValueByKey("alex"));
		System.out.println(b.getValueByKey("lampshade"));
		System.out.println(b.getValueByKey("full"));
		System.out.println(b.getValueByKey("asdf"));
	}
}
