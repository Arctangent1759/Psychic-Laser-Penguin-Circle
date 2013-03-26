
package DList;
public class DListNode<T>{
	protected DListNode<T> prev;
	protected DListNode<T> next;
	protected T item;
	protected boolean isSentinel;
	public DListNode(T obj, DListNode<T> prev, DListNode<T> next, boolean isSentinel){
		this.prev=prev;
		this.next=next;
		this.item=obj;
		this.isSentinel=isSentinel;
	}
	public T item(){
		return item;
	}
	public DListNode<T> prev(){
		if (prev.isSentinel){
			return null;
		}
		return prev;
	}
	public DListNode<T> next(){
		if (next.isSentinel){
			return null;
		}
		return next;
	}
}
