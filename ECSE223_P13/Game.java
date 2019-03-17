/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/


import java.io.Serializable;

// line 41 "Block223Persistence.ump"
public class Game implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 47 "Block223Persistence.ump"
   public static  void reinitializeUniqueName(List<Game> games){
    gamesByName = new HashMap<String, Game>();
		for (Game game : games) {
			gamesByName.put(game.getName(), game);
		}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 44 "Block223Persistence.ump"
  private static final long serialVersionUID = -4718217458307281181L ;

  
}