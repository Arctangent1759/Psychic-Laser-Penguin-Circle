package HashTable.BucketList.DList;
class DListNode{
	protected Object item;
	protected DListNode next;
	protected DListNode prev;

	/*
	 * Constructs a DListNode object.
	 */
	protected DListNode(){
		this.item=null;
		this.prev=null;
		this.next=null;
	}
	public DListNode(Object item,DListNode prev,DListNode next){
		this.item=item;
		this.prev=prev;
		this.next=next;
	}
}
