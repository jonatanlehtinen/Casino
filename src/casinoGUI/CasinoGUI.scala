

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

/**
 * This object implements graphical user interface for game's 
 * options and its methods can start new game or load an old one
 */
object CasinoGUI extends SimpleSwingApplication {
      
  //text field for loading game
  val secondTextField = new TextField
  secondTextField.text = "Write the name of your old save here and press enter: "
  secondTextField.horizontalAlignment = Alignment.Center
  secondTextField.editable = false
  secondTextField.background_=(Color.lightGray)
  
  //Text field where old game's name is inputted
  val chooseSaveField = new TextField

  //frame for GamePanel
  val newGameFrame = new Frame 

  //Text field for info 
  val humanPlayerInfo = new TextField
  humanPlayerInfo.peer.setLocation(456, 5)
  humanPlayerInfo.text = "Choose the amount of human players for new game: "
  humanPlayerInfo.editable = false
  
  //Selection of human players
  val humanAmountBox = new ComboBox(0 to 12)
  
  //Text field for info
  val computerPlayerInfo = new TextField
  computerPlayerInfo.peer.setLocation(456, 5)
  computerPlayerInfo.text = "Choose the amount of computer players for new game: "
  computerPlayerInfo.editable = false
  
  //Selection for computer players
  val computerAmountBox = new ComboBox(1 to 12)
  
  val newGameButton = new Button("New Game")
  newGameButton.horizontalAlignment = Alignment.Center
  
  //Panel where all the above is added
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
  
  //Whole layout where all the above is added
  val wholeLayOut = new BoxPanel(Orientation.Horizontal)
  wholeLayOut.background = Color.darkGray
  wholeLayOut.preferredSize_= (new Dimension(450, 450))
  wholeLayOut.contents += this.secondPanel

  //mainframe
  val window = new MainFrame
  window.title = "Casino"
  window.resizable = true
  window.contents = wholeLayOut
  
  //listen to newGameButton and text field or loading game
  listenTo(this.newGameButton, this.chooseSaveField.keys)
  this.reactions += {
    
    case ButtonClicked(newGameButton) =>

      //creates new game based on input from combo boxes
      val newGame = new GamePanel(new Game(Buffer[Computer](), Buffer[Human](), None, None, this.humanAmountBox.selection.item, this.computerAmountBox.selection.item))
      newGameFrame.contents = newGame
      newGameFrame.visible = true
      
    case keyEvent : KeyPressed =>
      
      
      if(keyEvent.key == Key.Enter && keyEvent.source == this.chooseSaveField)
        //tries to load old game based on input
        try{
          val gameData = scala.io.Source.fromFile(this.chooseSaveField.text.toUpperCase()).mkString
          val oldGame = Parser.loadGame(new StringReader(gameData))
          newGameFrame.contents = new GamePanel(oldGame)
          newGameFrame.visible = true
        }
      
        catch {
          case ex : Exception => 
            this.secondTextField.text = "No such file name!"
        }
  }
  
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName) 

  def top = this.window
  
  //method for closing GamePanel
  def closeGameFrame() : Unit = this.newGameFrame.close()
  
}



