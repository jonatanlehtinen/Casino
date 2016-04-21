import java.io.BufferedReader
import java.io.IOException
import java.io.Reader
import scala.collection.mutable._

object Parser {
   
  def loadGame(input: Reader) : Game = {
    
    val lineReader = new BufferedReader(input) 
    
    var computerPlayers = Buffer[Computer]()
    var humanPlayers = Buffer[Human]()
    var newPlayer: Option[Player] = None
    
    var readLine = lineReader.readLine()
    var markHasNotCome = true

    readLine = lineReader.readLine()
    while(readLine.head != '#'){
      readLine.head match {
        case 'H' => {
          newPlayer = Some(new Human(readLine))
        }
        case _ => {
          newPlayer = Some(new Computer(readLine))
        }
      }
      readLine = lineReader.readLine()
      newPlayer.get.addPoints(readLine.toInt) 
      readLine = lineReader.readLine()
      while(readLine != "true" && readLine != "false"){
        var lineSplitted = readLine.split(" ")
        newPlayer.get.addCard(new Card(lineSplitted(0), lineSplitted(1).toInt, lineSplitted(2).toInt))
        readLine = lineReader.readLine()
      }
      newPlayer.get.isTurn = {
        if(readLine == "true"){
          true
        }
        else false
      }
      readLine = lineReader.readLine()
      newPlayer.get.isDealer = {
        if(readLine == "true"){
          true
        }
        else false
      }
          
      readLine = lineReader.readLine()
      if(!readLine.isEmpty){
        while(readLine.head != 'H' && readLine.head != 'C' && readLine != '#'){
          var lineSplitted = readLine.split(" ")
          newPlayer.get.addtoCollection(new Card(lineSplitted(0), lineSplitted(1).toInt, lineSplitted(2).toInt))
          while(readLine.isEmpty){
            readLine = lineReader.readLine()
          }
        }
      }
      if(newPlayer.get.isInstanceOf[Computer]){
        computerPlayers += newPlayer.get.asInstanceOf[Computer]
      }
      else humanPlayers += newPlayer.get.asInstanceOf[Human]
    }
       
    new Game(computerPlayers, Buffer[Human](), None, Some(new Table), 3, 3)
   
  }
    
  
}