import scala.collection.mutable._
import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.scalatest.Assertions._
import org.scalatest.PrivateMethodTester._

class GameTest extends FlatSpec{
  
  
  "Game's constructors" should " deal right amount of cards in the beginning" in {
    
    val game = new Game(Buffer[Computer](), Buffer[Human](), None, Some(new Table), 3, 3)
      val success1 = game.deck.get.getSize == 52 - 4*4 - 4
      val success2 = game.getPlayers.forall(_.getCards.size == 4) 
      val success3 =  game.table.get.getCards.size == 4
      
      assert(success1, "Deck didn't have right amount of cards!") 
      assert(success2, "Players didn't have right amount of cards!")
      assert(success3, "Table didn't have right amount of cards!")

  }
  
  "Game's changeTurn" should " change players turn" in {
    
    val game = new Game(Buffer[Computer](), Buffer[Human](), None, Some(new Table), 3, 3)
      game.changeTurn()
      game.changeTurn()
      
      assert(game.getPlayers(3).isTurn, "Didn't change turns correctly")
      
      game.changeDealer()
      game.changeDealer()
      
      val success1 = !game.getPlayers(1).isDealer
      val success2 = game.getPlayers(2).isDealer
      val success3 = game.getPlayers(3).isTurn

      
      assert(success1, "Original dealer is still dealer!")
      assert(success2, "Didn't change dealer!")
      assert(success3, "Didn't change turn correctly")

  }
  
 "When taking cards methods " should "  do the correct things" in {
   
    val game = new Game(Buffer[Computer](), Buffer[Human](), None, Some(new Table), 3, 3)
   
   val cardSuits = Vector("H", "S", "D", "C")
   var shouldBeOnTable = ""
   
   for(x <- cardSuits){
     for(y <- 1 to 13){
       if(game.table.get.containsCard(y.toString + x)) shouldBeOnTable = y.toString + x
     }
   }   
   game.getCurrentPlayer.takeFromTable(shouldBeOnTable, game.table.get)
   
   assert(!game.table.get.containsCard(shouldBeOnTable), "Method removeCard didn't act correctly")
 
   assert(game.table.get.countCards("11H 9H 5S FOR 2S 6C 7H 10D"), "Method countCards didn't work correctly")
   assert(game.table.get.countCards("11H   9H   5S   FOR   2S   6C 7H 10D"), "Method countCards didn't work correctly")
   assert(!game.table.get.countCards("11H 2H 5S FOR 2S 6C 7H 10D"), "Method countCards didn't work correctly")
   
 }

  "After adding points method countPoints " should " count right amount of points" in {

    val player = new Computer("player1")
    
    player.addtoCollection(new Card("1S", 14, 1))
    player.addtoCollection(new Card("2S", 15, 2))
    player.addtoCollection(new Card("5S", 5, 5))
    
    assert(player.countPointsAndSpades == 2, "method didn't work correctly")
   
    player.removeCardsAndPoints
    
    player.addtoCollection(new Card("10D", 16, 10))
    player.addtoCollection(new Card("2S", 15, 2))
    player.addtoCollection(new Card("5S", 5, 5))
    player.addtoCollection(new Card("7H", 7, 7))

    assert(player.countPointsAndSpades == 3, "method didn't work correctly")
    assert(player.getSpades == 2, "method didn't work correctly")
    
  }
  
  "Player's methods " should " count cards sum right" in {

    var player = new Computer("player1")
    
    player.addCard(new Card("10D", 16, 10))
    player.addCard(new Card("6D", 6, 6))
    player.addCard(new Card("11D", 11, 11))
    player.addCard(new Card("12S", 12, 12))
    
    assert(player.getHandValueOfCards(player.makeStringFromCards(player.getCards)) == 45, "Method didn't count cards' sum correctly")
    player.removeCard("6D")
    assert(player.getHandValueOfCards(player.makeStringFromCards(player.getCards)) == 39, "Method didn't count cards' sum correctly")

  }
  
  "Computer's artificial intelligence " should " make correct move when told to" in {

    val player = new Computer("player1")
    val table = new Table

      
    player.addCard(new Card("10D", 16, 10))
    player.addCard(new Card("6D", 6, 6))
    player.addCard(new Card("11D", 11, 11))
    player.addCard(new Card("12S", 12, 12))

    assert(player.makeStringFromCards(player.getCards) == "10D 6D 11D 12S", "method didn't produce correct string")
    
    player.removeCard("10D")
 
    assert(player.makeStringFromCards(player.getCards) == "6D 11D 12S", "method didn't produce correct string")

    player.removeCard("6D")
    player.removeCard("11D")
    player.removeCard("12S")

    assert(player.makeStringFromCards(player.getCards) == "", "method didn't produce correct string")
   
    table.addCard(new Card("3S", 3, 3))
    table.addCard(new Card("5H", 5, 5))
    table.addCard(new Card("3D", 3, 3))
   
    player.addCard(new Card("10D", 16, 10))
    player.addCard(new Card("6D", 6, 6))
    player.addCard(new Card("11D", 11, 11))
    player.addCard(new Card("12S", 12, 12))   

    assert(table.countSum == 11, "Method didn't count the sum correctly")
     
    assert(player.canTakeWithOneCard(table.countSum), "Method didn't check correspondece correctly")
    
    player.makeMove(table)
    assert(player.getPoints == 1, "Method didn't add points correctly")
    assert(!player.containsCard("11D"), "Method didn't delete players cards")
    assert(!player.getCollection.forall(_.name != "11D"), "Method didn't add card to collection")
    assert(!player.getCollection.forall(_.name != "3S"), "Method didn't add card to collection")
    assert(!player.getCollection.forall(_.name != "5H"), "Method didn't add card to collection")
    assert(!player.getCollection.forall(_.name != "3D"), "Method didn't add card to collection")
    
    player.removeCard("10D")
    player.removeCard("6D")
    player.removeCard("12S")
    
    player.addCard(new Card("10S",10, 10))
    
    table.addCard(new Card("10D", 16, 10))
    table.addCard(new Card("5S", 5, 5))
    
    player.makeMove(table)
    
    assert(!player.containsCard("10S"), "Method didn't delete players cards after taking card")
    assert(!player.getCollection.forall(_.name != "10D"), "Method didn't add card to collection")
    assert(!player.getCollection.forall(_.name != "10S"), "Method didn't add card to collection")
    
  
  }
  
    "Action class " should " act when commanded to" in {

    val game = new Game(Buffer[Computer](), Buffer[Human](), None, Some(new Table), 3, 3)
    val player = game.getCurrentPlayer
    
    if(game.table.get.getCards.forall(_.name != "3S")) game.table.get.addCard(new Card("3S", 3, 3))
    if(game.table.get.getCards.forall(_.name != "5H")) game.table.get.addCard(new Card("5H", 5, 5))
    if(game.table.get.getCards.forall(_.name != "3D")) game.table.get.addCard(new Card("3D", 3, 3))
    
    if(player.getCards.forall(_.name != "11D")){
      player.addCard(new Card("11D", 11, 11))
    }
    
    val action = new Action("TAKE 3D 3S 5H FOR 11D").execute(game)

    assert(!player.containsCard("11D"), "Didn't remove card from player")
    assert(game.table.get.getCards.forall(_.name != "3D"), "Didn't remove card from table")
    assert(!player.getCollection.forall(_.name != "11D"), "Method didn't add 11D to collection")
    assert(!player.getCollection.forall(_.name != "3S"), "Method didn't add 3S to collection")
    assert(!player.getCollection.forall(_.name != "5H"), "Method didn't add 5H to collection")
    assert(!player.getCollection.forall(_.name != "3D"), "Method didn't add 3D to collection")
    assert(player.getCollection.forall(_.name != "7M"))
    
  }
  
 
   
  
 
  
  
  
}