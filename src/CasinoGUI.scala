import scala.swing._
import scala.swing.event._
import scala.swing.GridBagPanel.Anchor._
import scala.swing.GridBagPanel.Fill
import javax.swing.UIManager
import scala.collection.mutable.Buffer

object CasinoGUI extends SimpleSwingApplication{
  
  val computerPlayers = Buffer[Computer]()
  
  val game = new Game(computerPlayers, None, None, None, 3)
  
  val top = new Frame
  
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName) 

  /**
   * 
   */
  
}