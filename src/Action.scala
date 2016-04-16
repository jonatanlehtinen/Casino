

/** The class `Action` represents actions that a player may take in a text adventure game.
  * `Action` objects are constructed on the basis of textual commands and are, in effect, 
  * parsers for such commands. An action object is immutable after creation.
  *
  * @param input  a textual in-game command such as "go east" or "rest"
  */
class Action(input: String) {

  private val commandText = input.trim.toUpperCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim

  
  /** Causes the given player to take the action represented by this object, assuming 
    * that the command was understood. Returns a description of what happened as a result 
    * of the action (such as "You go west."). The description is returned in an `Option` 
    * wrapper; if the command was not recognized, `None` is returned. */
  def execute(game: Game) : Boolean = {                             

    if (this.verb == "TAKE") {
      game.takeCard(this.modifiers)
    }
    else if(this.verb == "GIVE") {
      game.giveCard(this.modifiers)
    }
     else {
      false
    }
  }

  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = this.verb + " (modifiers: " + this.modifiers + ")"  

  
}




