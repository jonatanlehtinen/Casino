import scala.collection.mutable.Buffer

class Game(var computerPlayers: Buffer[Computer], var human: Buffer[Human], var deck: Option[Deck], var table: Option[Table],humanAmount: Int, computerAmount: Int) {
  
  private var players = Vector[Player]()
  private val cardSuits = Vector("H", "S", "D", "C")
  private var deckHolder = Buffer[Card]()
  private var turnCount = 1
  private var dealerCount = 0
  
  if(this.human.isEmpty && this.computerPlayers.isEmpty){
    
    this.deck = Some(new Deck)
    this.table = Some(new Table)
    
    var nameNumber = 1
    for(x <- 0 until computerAmount){
      this.players = this.players :+ new Computer("Computer " + nameNumber)
     nameNumber += 1
    }
    
    nameNumber = 1
    for(x <- 0 until humanAmount){
      this.players = this.players :+ new Human("Human " + nameNumber)
      nameNumber += 1
    }
           
    this.players = scala.util.Random.shuffle(this.players)
    
    this.shuffleAndDeal()
  }
  
  def shuffleAndDeal(): Unit = {
    for(x <- cardSuits){
   
       for(y <- 1 to 13){
         
         var inHandValue = {
           if(y == 1) 14
           else if(y == 10 && x == "H") 16
           else if(y ==  2 && x == "S") 15
           else y
         }
         this.deckHolder += new Card(y.toString + x, inHandValue, y)
      }
    }

    this.deckHolder = scala.util.Random.shuffle(this.deckHolder)
    this.players(0).isDealer = true
    
    this.deckHolder.foreach(this.deck.get.addCards(_))
    
    for(x <- this.players){
      for(y <- 1 to 4){
        if(this.deck.get.canBeTaken){
        x.addCard(this.deck.get.takeCard)
        }
      }
    }
    
    for(x <- 1 to 4){
      if(this.deck.get.canBeTaken){
      this.table.get.addCard(this.deck.get.takeCard)
      }
    }
    
    players(0).isTurn = true
    
    }
  
  def changeTurn() : Unit = {
    this.players(turnCount%this.players.size).isTurn = false
    this.turnCount += 1
    this.players(turnCount%this.players.size).isTurn = true
  }
  
  def changeDealer(): Unit = {
    
    this.players(dealerCount%this.players.size).isDealer = false
    this.players(turnCount%this.players.size).isTurn = false
    this.dealerCount += 1
    this.turnCount = this.dealerCount
    this.players(dealerCount%this.players.size).isDealer = true
    this.changeTurn()
    
  }
  
  
  var isEnded = false
  
  def getPlayers : Vector[Player] = this.players
  
  def giveCard(card: String) : Boolean = {
    
    val cardInString = card.trim
    if(!this.getCurrentPlayer.getCards.forall(_.name != cardInString)){
      this.table.get.addCard(this.getCurrentPlayer.removeCard(cardInString))
      true
    }
    else false
  }
  
  def takeCard(card: String) : Boolean =  {
    
    val tableCardText = card.toUpperCase.takeWhile(_ != 'F').trim
    val handCardText = card.toUpperCase.reverse.takeWhile(_ != 'R').reverse.trim
    val cardsInArray = handCardText.split(" ")
    val currentPlayer = this.getCurrentPlayer
    
    if(this.tableContainsCards(this.table.get.getCards, tableCardText) &&
       this.handContainsCards(currentPlayer.getCards, handCardText, currentPlayer) && 
       currentPlayer.getHandValueOfCards(handCardText) == this.table.get.getCardsValues(tableCardText)){
      
        currentPlayer.takeFromTable(tableCardText, this.table.get)
        currentPlayer.removeCardsAndAddToCollectin(cardsInArray)
        this.players.foreach(_.lastToTakeCards = false)
        currentPlayer.lastToTakeCards = true
        true
    }
    
    else false
  }
  
  def tableContainsCards(cards: Buffer[Card], cardsInString: String) : Boolean = {
    
    val cardsInArray = cardsInString.split(" ")
    
    cardsInArray.forall(this.table.get.containsCard(_))
  }
  
  def handContainsCards(cards: Buffer[Card], cardsInString: String, player: Player) : Boolean = {
    
    val cardsInArray = cardsInString.split(" ")
    
    cardsInArray.forall(player.containsCard(_))
  }
  
  def getCurrentPlayer = this.players(turnCount%players.size)
 
  
  def isOver : Boolean = !this.players.forall(_.getCards.isEmpty)
  
  def computerPlayerMakeMove = {
    if(this.getCurrentPlayer.asInstanceOf[Computer].makeMove(this.table.get)) {
      this.players.foreach(_.lastToTakeCards = false)
      this.getCurrentPlayer.lastToTakeCards = true
    }
  }
 
 
  
  
  
  
  
  
}