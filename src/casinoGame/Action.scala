package casinoGame

/**
 * Action class represents action made by human player
 */
class Action(input: String) {

  private val commandText = input.trim.toUpperCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim

  //with this method action is executed
  def execute(game: Game) : Boolean = {                             

    if (this.verb == "TAKE") {
      game.takeCard(this.modifiers)
    }
    else if(this.verb == "GIVE") {
      game.giveCard(this.modifiers)
    }
    else if(this.verb == "SAVE") {
      SaveGame.saveGivenGame(game, modifiers)
      true
    }
     else {
      false
    }
  }

  override def toString = this.verb + " (modifiers: " + this.modifiers + ")"  

  
}




