/**
 *
 *	Net represents a single network
 *
**/

package Board;

import Constants.Constants;

public class Net{
	protected int player;
	protected int length;
	protected boolean complete;

	/**
	 *
	 *	Constructs a new network
	 *	@param player specifies the player that the network belongs to
	 *	@param length sepcifies the length of the network
	 *
	**/
	public Net(int player,int length,boolean complete) {
		this.player=player;
		this.length=length;
		this.complete=complete;
	}
	public String toString(){
		String color;
		if (player==Constants.BLACK){
			color="black";
		}else{
			color="white";
		}
		return "{player: "+color+" , length: "+length+" , complete: "+complete+"}";
	}
}
