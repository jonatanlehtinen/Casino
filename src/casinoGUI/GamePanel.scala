package casinoGUI

import scala.swing._
import scala.swing.event._
import scala.swing.GridBagPanel.Anchor._
import scala.swing.GridBagPanel.Fill
import javax.swing.UIManager
import scala.collection.mutable.Buffer
import casinoGame._

/**
 * Panel for game's user interface
 * @param game game which this panel controls
 */
class GamePanel(game: Game) extends BoxPanel(Orientation.Vertical) {
 
  
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName) 

  val nextPlayerButton = new Button("Next Player")
  
  //Text area for game info
  val gameInfo = new TextArea(7, 80) {
      editable = false
      wordWrap = true
      lineWrap = true
    }
    //Text area for table info  
    val tableInfo = new TextArea(7, 80) {
      editable = false
      wordWrap = true
      lineWrap = true
    }
    //Text field for user input
    val input = new TextField(40) {
      minimumSize = preferredSize
    }
    
    this.updateGameInfo()
    this.updateTableInfo()
    
    //listen to user input and next player button
    this.listenTo(input.keys, this.nextPlayerButton)
    this.reactions += {
      case keyEvent: KeyPressed =>
        //input is only possible when current player is human
        if (keyEvent.source == this.input && keyEvent.key == Key.Enter && !this.game.isOver && this.game.getCurrentPlayer.isInstanceOf[Human]) {
          
          val command = this.input.text.trim
          
          if (command.nonEmpty) {
            this.input.text = ""
            
            //try execute command given by user input
            if(new casinoGame.Action(command).execute(game)){
              
              if(!command.contains("save")) {

                game.changeTurn()
                this.pickCardForHuman()
                
                  if(this.game.isOver){
                  this.startNewGameOrEnd()
                  }
                
                this.updateGameInfo()
                this.updateTableInfo()
              }
            }
            else {
              this.gameInfo.text = "Incorrect command, you are allowed to use one of the following commands: give x, take x for x, or save x. " + 
              "Your cards: " + game.getCurrentPlayer.toString()
            }
          }
        }
      //button can only be pressed if it's computer's turn
      case buttonEvent: ButtonClicked =>
        
        if(this.game.getCurrentPlayer.isInstanceOf[Computer]) {
          this.game.computerPlayerMakeMove
          this.game.changeTurn()
          this.pickCardForHuman()
          
          if(this.game.isOver){
                this.startNewGameOrEnd()
              }
          
          this.updateGameInfo()
          this.updateTableInfo()
        }
        
        else {
          this.gameInfo.text = "You have to make a move first! Your cards: " + game.getCurrentPlayer.toString()
        }
    }
    
     //add contents to new panel
     this.contents += new GridBagPanel { 
      layout += new Label("Game info:")  -> new Constraints(0, 0, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += new Label("Command:")    -> new Constraints(0, 1, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += new Label("Table info:") -> new Constraints(0, 2, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += gameInfo                 -> new Constraints(1, 0, 1, 1, 1, 1, NorthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += input                    -> new Constraints(1, 1, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += tableInfo                -> new Constraints(1, 2, 1, 1, 1, 1, NorthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += nextPlayerButton         -> new Constraints(1, 3, 1, 1, 1, 1, NorthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)

    }
    
     //Helper method to update info about this game
    def updateGameInfo() : Unit = {
      if(game.getCurrentPlayer.isInstanceOf[Human]) this.gameInfo.text = "Your cards: " + game.getCurrentPlayer.toString()
      else this.gameInfo.text = game.getCurrentPlayer.name + " is now playing."
    }
    
    //Helper method to update info about table
    def updateTableInfo() : Unit = {
      this.tableInfo.text = "Currently on table: " + this.game.table.get.toString()
    }
    
    def updateAfterGame() : Unit = {
      Dialog.showMessage(new Label("Round over"), this.createPointString())
    }
     
    def pickCardForHuman() = {
      if(this.game.getCurrentPlayer.isInstanceOf[Human] && this.game.deck.get.canBeTaken){
        this.game.getCurrentPlayer.addCard(this.game.deck.get.takeCard)
      }
    }
    
    //Helper method to create wanted string from points
    def createPointString() : String = {
      var returnString = ""
      for(x <- this.game.getPlayers){
        returnString += x.name + ": " + x.getPoints + ","
      }
      returnString
    }

    
    //This method decides whether this game is over or just a round is over
    def startNewGameOrEnd() : Unit = {
      this.game.giveCardsForLastPlayer()
      this.game.countPointsForPlayers()
      
      if(!this.game.getWinningPlayer.isEmpty){
        Dialog.showMessage(new Label("Game over!"), this.game.getWinningPlayer.get.name + " has won!")
        CasinoGUI.closeGameFrame()
      }
      //if there's is no player who has more than 16 points new round is started
      else {
        this.game.getPlayers.foreach(_.removeCardsAndSpades)
        this.game.shuffleAndDeal()
        this.updateAfterGame()
      }
    }
    
    this.preferredSize = new Dimension(600,234)
    
    this.visible = true
      
}