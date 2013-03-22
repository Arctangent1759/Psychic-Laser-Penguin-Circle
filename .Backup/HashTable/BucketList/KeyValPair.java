package HashTable.BucketList;
public class KeyValPair{
	public Object key;
	public Object value;
	public KeyValPair(Object key, Object value){
		this.key=key;
		this.value=value;
	}
	public String toString(){
		return "{"+this.key+" : "+this.value+"}";
	}
}
