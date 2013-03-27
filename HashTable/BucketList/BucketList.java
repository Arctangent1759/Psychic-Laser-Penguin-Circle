package HashTable.BucketList;
/*
 * Invariants:
 * 1. length is correct.
 * 2. sentinel.prev="tail"
 * 3. sentinel.next="head"
 */
public class BucketList{
	protected BucketListNode sentinel;
	protected int length;
	protected BucketListNode iterator;

	//Constuctors
	public BucketList(){
		this.length=0;
		this.sentinel = new BucketListNode();
		sentinel.next=sentinel;
		sentinel.prev=sentinel;
		this.iterator=sentinel;
	}

	//Push/Pop
	public void pushFront(Object o){
		sentinel.next=new BucketListNode(o,sentinel,sentinel.next);
		sentinel.next.next.prev=sentinel.next;
		this.length++;
	}
	public void pushBack(Object o){
		sentinel.prev=new BucketListNode(o,sentinel.prev,sentinel);
		sentinel.prev.prev.next=sentinel.prev;
		this.length++;
	}
	public Object popFront(){
		Object out=sentinel.next.item;
		sentinel.next=sentinel.next.next;
		sentinel.next.prev=sentinel;
		this.length--;
		return out;
	}
	public Object popBack(){
		Object out=sentinel.prev.item;
		sentinel.prev=sentinel.prev.prev;
		sentinel.prev.next=sentinel;
		this.length--;
		return out;
	}

	//Itemwise ops
	public Object getItem(int n){
		BucketListNode curr=sentinel.next;
		while (n>0){
			curr=curr.next;
			n--;
		}
		return curr.item;
	}
	public Object popItem(int n){
		BucketListNode curr=sentinel.next;
		while (n>0){
			curr=curr.next;
			n--;
		}
		curr.next.prev=curr.prev;
		curr.prev.next=curr.next;
		this.length--;
		return curr.item;
	}
	public void insertItem(int n, Object o){
		BucketListNode curr=sentinel.next;
		while (n>0){
			curr=curr.next;
			n--;
		}
		curr.prev=new BucketListNode(o,curr.prev,curr);
		curr.prev.prev.next=curr.prev;
		this.length++;
	}
	public void replaceItem(int n, Object o){
		popItem(n);
		insertItem(n,o);
	}

	//Iterators
	public void reset(){
		iterator=sentinel.next;
		if (iterator==sentinel){
			iterator=null;
		}
	
	}
	public Object next(){
		if (iterator==sentinel){
			return null;
		}
		Object out = iterator.item;
		iterator=iterator.next;
		return out;
	}

	//Length accessor
	public int length(){
		return length;
	}

	//JSON-ify string
	public String toString(){
		if (sentinel.next==sentinel){
			return null;
		}
		BucketListNode curr=sentinel.next;
		String out = "[";
		while (true){
			out+=curr.item.toString();
				if (curr.next==sentinel){
					return out+"]";
				}
			out+=", ";
			curr=curr.next;
		}
	}


	//(Mildly comical) Test code
	public static void main(String args[]){
		BucketList d = new BucketList();
		d.pushBack("Potato");
		d.pushBack(1);
		d.pushBack(2);
		d.pushBack(3);
		d.pushFront(5);
		d.pushFront("A toast to not being on fire!");
		print("Should be [\"A toast to not being on fire!\", 5, \"Potato\", 1, 2, 3]: "+d);
		print("Should be \"A toast to not being on fire!\": "+d.popFront());
		print("Should be [5, \"Potato\", 1, 2, 3]: "+d);
		print("Should be 3: "+d.popBack());
		print("Should be [5, \"Potato\", 1, 2]: "+d);
		print("Should be [5, \"Potato\", 1, 2]: "+d);
		print("Should be 1: "+d.getItem(2));
		print("Should be 4: "+d.length());
		print("Should be \"Potato\": "+d.popItem(1));
		print("Should be [5, 1, 2]: "+d);
		d.insertItem(2,4);
		print("Should be [5, 1, 4, 2]: "+d);
		d.replaceItem(2,"Pizza is not a vegetable.");
		print("Should be [5, 1, \"Pizza is not a vegetable.\", 2]: "+d);
		d.reset();
		print(""+d.next());
		print(""+d.next());
		print(""+d.next());
		print(""+d.next());
		print(""+d.next());
		print(""+d.next());
	}
	//Redeeming features of python
	public static void print(String s){
		System.out.println(s);
	}
}
