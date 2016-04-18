

class Human(val name: String) extends Player{
  
  override def toString() = {
    var holder = "" 
    for(x <- this.getCards){
      holder += x.name + " Value in hand: " + x.inHandValue + ", "
     }
     holder
   }
  
  
}