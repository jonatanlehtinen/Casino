import scala.swing._
import Swing._
import scala.swing.event._
import scala.swing.GridBagPanel.Anchor._
import scala.swing.GridBagPanel.Fill
import javax.swing.UIManager
import scala.collection.mutable.Buffer
import java.awt.Color
import java.awt.BorderLayout

object CasinoGUI extends SimpleSwingApplication {
  
  val computerPlayers = Buffer[Computer]()
    
  val secondTextField = new TextField
  secondTextField.text = "Testi"
  secondTextField.background_=(Color.lightGray)

  val continueBox = new ComboBox(1 to 31)

  val emptyBox = new TextField
  emptyBox.peer.setLocation(456, 5)
  emptyBox.text = "moikka"
  emptyBox.editable = false
  
  val computerAmountBox = new ComboBox(1 to 12)
  val humanAmountBox = new ComboBox(1 to 12)
  val newGameButton = new Button("New Game")
  
  val secondPanel = new BoxPanel(Orientation.Vertical)
  secondPanel.background_=(Color.lightGray)
  secondPanel.contents += this.secondTextField
  secondPanel.border = Swing.LineBorder(Color.darkGray, 34)
  secondPanel.contents += this.continueBox
  secondPanel.contents += VStrut(30)
  secondPanel.contents += this.emptyBox
  secondPanel.contents += this.humanAmountBox
  secondPanel.contents += this.computerAmountBox
  secondPanel.contents += VStrut(30)
  secondPanel.contents += this.newGameButton
  secondPanel.preferredSize = new Dimension(10,10)
  secondPanel.visible = true
  
  
  val panelForResults = new TextField
  panelForResults.name = "Results"
  panelForResults.horizontalAlignment = Alignment.Center
  panelForResults.preferredSize = new Dimension(50,50)
  panelForResults.editable = false
  panelForResults.text = "RESULTSMoika"
  panelForResults.background = Color.lightGray
 
  
  val firstPanel = new BoxPanel(Orientation.Horizontal)
  firstPanel.contents += panelForResults
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
  
  listenTo(this.newGameButton)
  this.reactions += {
    case e: ButtonClicked =>
      val newGameFrame = new Frame 
      val newGame = new GamePanel(new Game(Buffer[Computer](), Buffer[Human](), None, None, this.humanAmountBox.selection.item, this.computerAmountBox.selection.item))
      newGameFrame.contents = newGame
      newGameFrame.visible = true
  }
  
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName) 

  def top = this.window
  
  
  
  /**
   * 
   */
  
}