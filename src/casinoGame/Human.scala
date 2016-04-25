

package casinoGame

class Human(name: String) extends Player(name){
  
  override def toString() = {
    var holder = "" 
    for(x <- this.getCards){
      holder += x.name + " Value in hand: " + x.inHandValue + ", "
     }
     holder
   }
  
  
}