package casinoGame

import scala.collection.mutable.Stack

/**
 * this case class represents one card
 * @param name name of the card for example 4S(four of spades)
 * @param inHandValue value of the card in hand
 * @param inTableValue value of the card in table
 */
case class Card(val name: String, val inHandValue: Int, val inTableValue: Int) {
  override def toString() = name + " " + inHandValue + " " + inTableValue
}

/**
 * this class represents a deck
 */
class Deck {
  
  //holder for the cards
  private var deck = Stack[Card]()
  
  def addCards(card: Card) : Unit = deck.push(card)
  
  def takeCard : Card = this.deck.pop()
  
  def canBeTaken : Boolean = !this.deck.isEmpty
  
  def getSize : Int = this.deck.size
  
  override def toString = this.deck.mkString("\n")
  
}

