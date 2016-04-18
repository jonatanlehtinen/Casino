import scala.collection.mutable._

class Table {
  
  private var cards = Buffer[Card]()
  
  def addCard(card: Card): Unit = this.cards += card
  
  def removeCard(card: Card): Unit = if(this.cards.contains(card)) this.cards -= card
  
  def countSum : Int = {
    
    var count = 0
 
    for(x <- cards){
      count += x.inTableValue
    }
    count
  }
  
   /**
    * Checks which cards are in the card string and returns
    * sum of those cards
    * 
    * @param card Contains cards' names in string
    * @return Sum of wanted cards
    */
  def getCardsValues(card: String) : Int = {  
    
    var holder = Buffer[Card]()
    var cardsInArray = card.split(" ")
    
    for(x <- this.cards){
      for(y <- cardsInArray){
        if(x.name == y) holder += x
      }
    }
    var count = 0
    
    for(x <- holder){
      count += x.inTableValue
    }
    count
  }
   
  /**
   * Removes wanted card from table. This method expects 
   * that in Buffer cards there is a card matching parameter cardText.
   * 
   *@param cardText name of the card 
   *@return removed Card 
   */
  def removeCard(cardText: String) : Card = {
    
    var toBeRemovedIndex = 0
    for(x <-0 until cards.length){
      if(cards(x).name == cardText){
        toBeRemovedIndex = x
      }
    }
    this.cards.remove(toBeRemovedIndex)
  }
      
  def countCards(cardText: String) : Boolean = {
    
    val textInArray = cardText.split("F")
    
    this.countFromText(textInArray(0).split(" ")) == this.countFromText(textInArray(1).drop(2).trim.split(" ")) 
  }
  
  def containsCard(cardText: String) : Boolean =  !cards.forall(_.name != cardText)
  
  def getCards: Buffer[Card] = this.cards
  
  def countFromText(cardsInArray: Array[String]) : Int = {
    
    var count = 0
    for(x <- cardsInArray){
      if(!x.isEmpty) count += x.trim.dropRight(1).toInt     
    }
    count
  }
  
  
  def hasTenOfDiamonds : Boolean = !this.cards.forall(_.inHandValue != 16)
  
  def hasTwoOfSpades : Boolean = !this.cards.forall(_.inHandValue != 15)
  
  
  override def toString() = {
    var holder = ""
    
    for(x <- this.cards){
      holder += x.name + " Value in table " + x.inTableValue + ", "
    }
    holder
  }
  
  
  
  
  
  
  
  
  
}