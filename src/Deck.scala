import scala.collection.mutable.Stack


case class Card(val name: String, val inHandValue: Int, val inTableValue: Int) 

class Deck {
  
  private var deck = Stack[Card]()
  
  def addCards(card: Card) : Unit = deck.push(card)
  
  def takeCard : Card = this.deck.pop()
  
  def canBeTaken : Boolean = this.deck.nonEmpty
  
  def getSize : Int = this.deck.size
  
  override def toString = "This deck contains: " + this.deck.foreach(println(_))
  
}

