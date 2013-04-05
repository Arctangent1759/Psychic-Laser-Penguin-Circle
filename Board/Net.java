/**
 *
 *	Net represents a single network
 *
**/

package Board;

import Constants.Constants;

public class Net{
	protected int player; //The player to which this networks pertains

	protected int length; //The length of the network

	protected boolean complete; //Whether the network is complete or not.
								//Note: A network of length 6 can be incomplete if it does not bridge the two goals

	/**
	 *
	 *	Constructs a new network descriptor
	 *	@param player specifies the player that the network belongs to
	 *	@param length sepcifies the length of the network
	 *
	**/
	public Net(int player,int length,boolean complete) {
		this.player=player;
		this.length=length;
		this.complete=complete;
	}
	
	/**
	 *
	 *	Gets the player who has this network
	 *	@return the player to which this network pertains
	 *
	**/
	public int getPlayer() {
		return player;
	}
	
	/**
	 *
	 *	Gets the length of this network.
	 *
	 *	Note: (length==6) does not necessarily mean that the network 
	 *	is complete. There can be a network that is of length 6 but
	 *	does not bridge the two goals, and therefore, is not a winning 
	 *	network.
	 *
	 *	@return the length of this network
	 *
	**/
	public int getLength() {
		return length;
	}
	
	/**
	 *
	 * 	Gets whether the network is complete, that is its length is at 
	 * 	least 6 and it bridges the two sides.
	 *
	 * 	The (length==6) rule is enforced by Board.
	 *
	 * 	@return whether the network is complete.
	 *
	**/
	public boolean isComplete() {
		return complete;
	}
	/**
	 *
	 *	Returns the string representation of this network.
	 *	Used for debugging purposes.
	 *
	 *	@return the string representation of this network.
	 *
	**/
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
