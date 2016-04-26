package casinoGame

import java.io.PrintWriter
import scala.collection.mutable._

/**
 * This object saves game as a specific text file
 * 
 */
object SaveGame{
  
  /**
   * this method does all the work
   * @param game game to be saved
   * @param savingName name of the save, also name of the text file
   */
  def saveGivenGame(game: Game, savingName: String) = {
  
  val fileName = ""
  
  val file = new PrintWriter(savingName)
  
  //tries to add all the necessary information
  try {
    file.println(this.playerString(game.getPlayers))
    println(game.getPlayers.mkString(" "))
    file.println("#Deck")
    file.println(game.deck.get.toString())
    file.println("#Table")
    file.println(game.table.get.getCards.mkString("\n"))
    file.println("EOF")
  }
  
  finally {
    file.close()
  }
  
  
  }
  
  //helper method to create wanted string from player's collection
  def getCollection(collection: Buffer[Card]) : String = {
    var returnString = ""
    if(collection.isEmpty){
      returnString  
    }
    else collection.mkString("\n") + "\n"
    
  }
  
  //helper method to create wanted string from players
  def playerString(players: Vector[Player]) : String = {
    
    var returnString = "#Pelaajat\n"
    
    for(x <- players){
      if(x.getCards.isEmpty) returnString += x.name + "\n" + x.getPoints + "\n" + x.isDealer + "\n" + x.isTurn + "\n" + this.getCollection(x.getCollection)
      else returnString += x.name + "\n" + x.getPoints + "\n" + x.getCards.mkString("\n") + "\n" + x.isDealer + "\n" + x.isTurn + "\n" + this.getCollection(x.getCollection)
    }
    
    returnString
  }
    
}
