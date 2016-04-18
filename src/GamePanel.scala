import scala.swing._
import scala.swing.event._
import scala.swing.GridBagPanel.Anchor._
import scala.swing.GridBagPanel.Fill
import javax.swing.UIManager
import scala.collection.mutable.Buffer

class GamePanel(game: Game) extends BoxPanel(Orientation.Vertical) {
 
  
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName) 

  val nextPlayerButton = new Button("Next Player")
  
  val gameInfo = new TextArea(7, 80) {
      editable = false
      wordWrap = true
      lineWrap = true
    }
    val tableInfo = new TextArea(7, 80) {
      editable = false
      wordWrap = true
      lineWrap = true
    }
    val input = new TextField(40) {
      minimumSize = preferredSize
    }
    
    this.updateGameInfo()
    this.updateTableInfo()
    
    this.listenTo(input.keys)
    this.listenTo(this.nextPlayerButton)
    
    
    this.reactions += {
      case keyEvent: KeyPressed =>
        if (keyEvent.source == this.input && keyEvent.key == Key.Enter && this.game.isOver && this.game.getCurrentPlayer.isInstanceOf[Human]) {
          val command = this.input.text.trim
          if (command.nonEmpty) {
            this.input.text = ""
            if(new Action(command).execute(game)){
              game.changeTurn()
              this.pickCardForHuman()
              if(this.game.isOver){
                this.startNewGameOrEnd()
              }
              this.updateGameInfo()
              this.updateTableInfo()
            }
            else {
              this.gameInfo.text = "Incorrect command, you are allowed to use one of the following commands: give x, take x for x, exit or save. " + 
              "Your cards: " + game.getCurrentPlayer.toString()
            }
            println(this.game.getCurrentPlayer.getCards.mkString(" "))
            println(this.game.table.get.toString())
          }
        }
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
        else this.gameInfo.text = "You have to make a move first! Your cards: " + game.getCurrentPlayer.toString()
    }
    
     this.contents += new GridBagPanel { 
      layout += new Label("Game info:")  -> new Constraints(0, 0, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += new Label("Command:")    -> new Constraints(0, 1, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += new Label("Table info:") -> new Constraints(0, 2, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += gameInfo                 -> new Constraints(1, 0, 1, 1, 1, 1, NorthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += input                    -> new Constraints(1, 1, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += tableInfo                -> new Constraints(1, 2, 1, 1, 1, 1, NorthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += nextPlayerButton         -> new Constraints(1, 3, 1, 1, 1, 1, NorthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)

    }
    
    def updateGameInfo() : Unit = {
      if(game.getCurrentPlayer.isInstanceOf[Human]) this.gameInfo.text = "Your cards: " + game.getCurrentPlayer.toString()
      else this.gameInfo.text = game.getCurrentPlayer.name + " is now playing."
    }
    
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
    
    def createPointString() : String = {
      var returnString = ""
      for(x <- this.game.getPlayers){
        returnString += x.name + ": " + x.getPoints + ","
      }
      returnString
    }

    
    def startNewGameOrEnd() : Unit = {
      this.game.giveCardsForLastPlayer()
      this.game.countPointsForPlayers()
      
      if(!this.game.getWinningPlayer.isEmpty && this.game.getWinningPlayer.get.isInstanceOf[Computer]){
        
        Dialog.showMessage(new Label("Game over!"), this.game.getWinningPlayer.get.name + " has won!")
        CasinoGUI.closeGameFrame()
      }
      else if(!this.game.getWinningPlayer.isEmpty && this.game.getWinningPlayer.get.isInstanceOf[Human]){
        
        Dialog.showMessage(new Label("Game over!"), this.game.getWinningPlayer.get.name + " has won!")
        CasinoGUI.closeGameFrame()
      }
      else {
        this.game.getPlayers.foreach(_.removeCardsAndSpades)
        this.game.shuffleAndDeal()
        this.updateAfterGame()
      }
    }
    
    this.preferredSize = new Dimension(600,234)
    
    this.visible = true
      
}