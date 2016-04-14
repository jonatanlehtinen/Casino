import scala.collection.mutable._

class Computer(name: String) extends Player{

  
  def makeMove(table: Table) = {
    
    val tableCardSum = table.countSum
    val cardsInString = this.makeStringFromCards(this.getCards)
    val tableCardsInString = this.makeStringFromCards(table.getCards)
    
    if(this.getHandValueOfCards(cardsInString) == tableCardSum){
      this.takeFromTable(tableCardsInString, table)
      this.addToCollectionFromString(cardsInString)
    }
    else if(this.canTakeWithOneCard(tableCardSum)){
      this.takeFromTable(tableCardsInString, table)
      this.addToCollectionFromString(this.getMatchingCard(tableCardSum).name)
    }
    else if(table.hasDiamondTen && this.hasTen){
      this.takeFromTable("10D", table)
      this.addToCollectionFromString(this.getMatchingCard(10).name)
    }
  }
  
  def hasTen : Boolean = !this.getCards.forall(_.inHandValue != 10)
  
  def canTakeWithOneCard(tableCardSum: Int) : Boolean =  !this.getCards.forall(_.inHandValue != tableCardSum)
  
  /**
   * Finds card from players hand matching table cards' sum,
   * assuming that there is one
   * @param tableCardSum sum of all table cards
   * @return card with which can the table be cleared
   */
  def getMatchingCard(tableCardSum: Int) : Card = this.getCards.find(_.inHandValue == tableCardSum).get
    
    
  def makeStringFromCards(cards: Buffer[Card]) : String = {
    
    var result = ""
    for(x <- cards){
      result += x.name + " "
    }
    result.trim
    
  }
  
    
  
  
  
  
}