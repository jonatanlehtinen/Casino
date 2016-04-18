import scala.collection.mutable._

class Computer(val name: String) extends Player{

  
  def makeMove(table: Table) : Boolean = {
    
    val tableCardSum = table.countSum
    val cardsInString = this.makeStringFromCards(this.getCards)
    val tableCardsInString = this.makeStringFromCards(table.getCards)
    
    if(this.getHandValueOfCards(cardsInString) == tableCardSum){
      this.takeFromTable(tableCardsInString, table)
      this.addToCollectionFromString(cardsInString)
      true
    }
    else if(this.canTakeWithOneCard(tableCardSum)){
      this.takeFromTable(tableCardsInString, table)
      this.addToCollectionFromString(this.getMatchingCard(tableCardSum).name)
      true
    }
    else if(table.hasTenOfDiamonds && this.hasTen){
      this.takeFromTable("10D", table)
      this.addToCollectionFromString(this.getMatchingCard(10).name)
      true
    }
    else if(table.hasTwoOfSpades && this.hasTwo){
      this.takeFromTable("2S", table)
      this.addToCollectionFromString(this.getMatchingCard(2).name)
      true
    }
    else if(!this.cardToDiscard.isEmpty) {
      table.addCard(this.cardToDiscard.get)
      this.takeCard(this.cardToDiscard.get)
      false
    }
    else {
      table.addCard(this.getCards.head)
      this.takeCard(this.getCards.head)
      false
    }
  }
  
  def hasTen : Boolean = !this.getCards.forall(_.inHandValue != 10)
  
  def hasTwo : Boolean = !this.getCards.forall(_.inHandValue != 2)
  
  def canTakeWithOneCard(tableCardSum: Int) : Boolean =  !this.getCards.forall(_.inHandValue != tableCardSum)
  
  /**
   * Finds card from players hand matching table cards' sum,
   * assuming that there is one
   * @param tableCardSum sum of all table cards
   * @return card with which can the table be cleared
   */
  def getMatchingCard(tableCardSum: Int) : Card = this.getCards.find(_.inHandValue == tableCardSum).get
   
  def cardToDiscard : Option[Card] = this.getCards.find{x => x.inHandValue != 10 && x.inHandValue != 2 && x.inHandValue != 14}
    
    
  def makeStringFromCards(cards: Buffer[Card]) : String = {
    
    var result = ""
    for(x <- cards){
      result += x.name + " "
    }
    result.trim
    
  }
  
  
  
  
  
}