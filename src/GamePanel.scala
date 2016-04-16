import scala.swing._
import scala.swing.event._
import scala.swing.GridBagPanel.Anchor._
import scala.swing.GridBagPanel.Fill
import javax.swing.UIManager
import scala.collection.mutable.Buffer

class GamePanel(game: Game) extends BoxPanel(Orientation.Vertical) {
 
  println(game.getCurrentPlayer.getCards.mkString(" "))
  println(game.table.get.getCards.mkString(" "))
  println(game.getCurrentPlayer.isInstanceOf[Human])
  
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName) 

  
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
    this.listenTo(input.keys)
    val turnCounter = new Label
    
    this.reactions += {
      case keyEvent: KeyPressed =>
        if (keyEvent.source == this.input && keyEvent.key == Key.Enter && this.game.isOver && this.game.getCurrentPlayer.isInstanceOf[Human]) {
          val command = this.input.text.trim
          if (command.nonEmpty) {
            this.input.text = ""
            if(new Action(command).execute(game)){
              game.changeTurn()
            }
            println(game.getCurrentPlayer.getCards.mkString(" "))
            println(game.table.get.getCards.mkString(" "))
          }
        }
    }
    
     this.contents += new GridBagPanel { 
      layout += new Label("Game info:")  -> new Constraints(0, 0, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += new Label("Command:")    -> new Constraints(0, 1, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += new Label("Table info:") -> new Constraints(0, 2, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += turnCounter              -> new Constraints(0, 3, 2, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += gameInfo                 -> new Constraints(1, 0, 1, 1, 1, 1, NorthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += input                    -> new Constraints(1, 1, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += tableInfo                -> new Constraints(1, 2, 1, 1, 1, 1, NorthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)
    }
    
    
     
    this.preferredSize = new Dimension(600,234)
    
    this.visible = true
    
    def playTurn(string: String) = ???
  
}