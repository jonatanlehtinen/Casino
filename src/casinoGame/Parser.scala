package casinoGame

import java.io.BufferedReader
import java.io.Reader
import scala.collection.mutable._

/**
 * Object for loading an old game
 * this object assumes text file is saved by
 * object SaveGame 
 */
object Parser {
   
  /**
   * this method does all the actual work, it loads game
   * and returns the game based on input
   * @param input input from where game is loaded
   * @return Game returning old game
   */
  def loadGame(input: Reader) : Game = {
    
    val lineReader = new BufferedReader(input) 
    
    val deck = new Deck
    val table = new Table
    var computerPlayers = Buffer[Computer]()
    var humanPlayers = Buffer[Human]()
    var newPlayer: Option[Player] = None
    
    var readLine = lineReader.readLine()
    
    //creates players first
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
      //return players points
      newPlayer.get.addPoints(readLine.toInt) 
      readLine = lineReader.readLine()
      
      //return players cards
      while(readLine != "true" && readLine != "false"){
        
        var lineSplitted = readLine.split(" ")
        newPlayer.get.addCard(new Card(lineSplitted(0), lineSplitted(1).toInt, lineSplitted(2).toInt))
        readLine = lineReader.readLine()
      }
      //decide whether player is a dealer or not
      newPlayer.get.isDealer = {
        if(readLine == "true"){
          true
        }
        else false
      }
      //decide whether player it't player's turn or not
      readLine = lineReader.readLine()
      newPlayer.get.isTurn = {
        if(readLine == "true"){
          true
        }
        else false
      }
       
      //add cards players collection
      readLine = lineReader.readLine()
      if(!readLine.isEmpty){
        
        while(readLine.head != 'H' && readLine.head != 'C' && readLine.head != '#'){
          
          var lineSplitted = readLine.split(" ")
          newPlayer.get.addtoCollection(new Card(lineSplitted(0), lineSplitted(1).toInt, lineSplitted(2).toInt))
          readLine = lineReader.readLine()
          
          while(readLine.isEmpty){
            readLine = lineReader.readLine()
          }
        }
      }
      
      if(newPlayer.get.isInstanceOf[Computer]){
        computerPlayers += newPlayer.get.asInstanceOf[Computer]
      }
      else humanPlayers += newPlayer.get.asInstanceOf[Human]
      
      while(readLine.isEmpty){
            readLine = lineReader.readLine()
          }
    }
    
    //return cards to deck
    readLine = lineReader.readLine()
    while(!readLine.isEmpty && readLine.head != '#'){
      
      var lineSplitted = readLine.split(" ")
      deck.addCards((new Card(lineSplitted(0), lineSplitted(1).toInt, lineSplitted(2).toInt)))
      readLine = lineReader.readLine()
    }
    
    readLine = lineReader.readLine()
    if(!readLine.isEmpty() && readLine.head == '#'){
      readLine = lineReader.readLine()
    }
    
    //add cards to table
    while(readLine != "EOF"){
      
      var lineSplitted = readLine.split(" ")
      table.addCard((new Card(lineSplitted(0), lineSplitted(1).toInt, lineSplitted(2).toInt)))
      readLine = lineReader.readLine()
    }
    
    //return new game based on the input which was just parsed
    new Game(computerPlayers, humanPlayers, Some(deck), Some(table), 0, 0)
   
  }
    
  
}