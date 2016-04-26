

package casinoGame

/**
 * class for Human player, needed to authenticate which type of player is playing
 * and this class doesn't need same methods than Computer class
 * @param name name of the player
 */
class Human(name: String) extends Player(name){
  
  override def toString() = {
    var holder = "" 
    for(x <- this.getCards){
      holder += x.name + " Value in hand: " + x.inHandValue + ", "
     }
     holder
   }
  
  
}