import java.io.PrintWriter
import scala.collection.mutable._

object SaveGame{
  
  def saveGivenGame(game: Game, savingName: String) = {
  
  val fileName = ""
  
  val file = new PrintWriter(savingName)
  
  try {
    file.println(this.playerString(game.getPlayers))
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
  
  
  def playerString(players: Vector[Player]) : String = {
    
    var returnString = "#Pelaajat\n"
    
    for(x <- players){
      returnString += x.name + "\n" + x.getPoints + "\n" + x.getCards.mkString("\n") + "\n" + x.isDealer + "\n" + x.isTurn + "\n" + x.getCollection.mkString("\n")
    }
    
    returnString
  }
    
}
