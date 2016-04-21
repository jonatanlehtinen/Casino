import scala.collection.mutable.Stack


case class Card(val name: String, val inHandValue: Int, val inTableValue: Int) {
  override def toString() = name + " " + inHandValue + " " + inTableValue
}

class Deck {
  
  private var deck = Stack[Card]()
  
  def addCards(card: Card) : Unit = deck.push(card)
  
  def takeCard : Card = this.deck.pop()
  
  def canBeTaken : Boolean = !this.deck.isEmpty
  
  def getSize : Int = this.deck.size
  
  override def toString = this.deck.mkString("\n")
  
}

