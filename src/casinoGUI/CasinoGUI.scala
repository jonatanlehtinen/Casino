

package casinoGUI

import scala.swing._
import Swing._
import java.io._
import scala.swing.event._
import scala.swing.GridBagPanel.Anchor._
import javax.swing.UIManager
import scala.collection.mutable.Buffer
import java.awt.Color
import casinoGame._

object CasinoGUI extends SimpleSwingApplication {
  
  val computerPlayers = Buffer[Computer]()
    
  val secondTextField = new TextField
  secondTextField.text = "Write the name of your old save here: "
  secondTextField.horizontalAlignment = Alignment.Center
  secondTextField.editable = false
  secondTextField.background_=(Color.lightGray)
  
  val newGameFrame = new Frame 

  val humanPlayerInfo = new TextField
  humanPlayerInfo.peer.setLocation(456, 5)
  humanPlayerInfo.text = "Choose the amount of human players for new game: "
  humanPlayerInfo.editable = false
  
  val computerPlayerInfo = new TextField
  computerPlayerInfo.peer.setLocation(456, 5)
  computerPlayerInfo.text = "Choose the amount of computer players for new game: "
  computerPlayerInfo.editable = false
  
  val computerAmountBox = new ComboBox(1 to 12)
  val chooseSaveField = new TextField
  val humanAmountBox = new ComboBox(0 to 12)
  val newGameButton = new Button("New Game")
  
  val secondPanel = new BoxPanel(Orientation.Vertical)
  secondPanel.background_=(Color.lightGray)
  secondPanel.contents += this.secondTextField
  secondPanel.contents += this.chooseSaveField
  secondPanel.border = Swing.LineBorder(Color.darkGray, 34)
  secondPanel.contents += VStrut(30)
  secondPanel.contents += this.humanPlayerInfo
  secondPanel.contents += this.humanAmountBox 
  secondPanel.contents += this.computerPlayerInfo
  secondPanel.contents += this.computerAmountBox
  secondPanel.contents += VStrut(30)
  secondPanel.contents += this.newGameButton
  secondPanel.preferredSize = new Dimension(10,10)
  secondPanel.visible = true
  
  
  val firstPanel = new BoxPanel(Orientation.Horizontal)
  firstPanel.border = Swing.LineBorder(Color.darkGray, 34)
  firstPanel.background = Color.lightGray
  firstPanel.visible = true
  firstPanel.preferredSize = new Dimension(30, 30)
  
  val wholeLayOut = new BoxPanel(Orientation.Horizontal)
  wholeLayOut.background = Color.darkGray
  wholeLayOut.preferredSize_= (new Dimension(450, 450))
  wholeLayOut.contents += this.firstPanel
  wholeLayOut.contents += this.secondPanel

  val window = new MainFrame
  window.title = "Casino"
  window.resizable = true
  window.contents = wholeLayOut
  
  listenTo(this.newGameButton, this.chooseSaveField.keys)
  this.reactions += {
    case ButtonClicked(newGameButton) =>
      val newGame = new GamePanel(new Game(Buffer[Computer](), Buffer[Human](), None, None, this.humanAmountBox.selection.item, this.computerAmountBox.selection.item))
      newGameFrame.contents = newGame
      newGameFrame.visible = true
    case keyEvent : KeyPressed =>
      if(keyEvent.key == Key.Enter && keyEvent.source == this.chooseSaveField)
        try{
          val gameData = scala.io.Source.fromFile(this.chooseSaveField.text.toUpperCase()).mkString
          val oldGame = Parser.loadGame(new StringReader(gameData))
          //oldGame.goBackOneTurn()
          newGameFrame.contents = new GamePanel(oldGame)
          newGameFrame.visible = true
        }
        catch {
          case ex : Exception => 
            this.secondTextField.text = "No such file name!" + ex
        }
  }
  
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName) 

  def top = this.window
  
  def closeGameFrame() = this.newGameFrame.close()
  
}



