/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.io.Serializable;
import java.util.*;

// line 87 "../../../../../Block223Persistence.ump"
// line 215 "../../../../../Block223.ump"
public class Paddle implements Serializable
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  public static final int PADDLE_WIDTH = 5;
  public static final int VERTICAL_DISTANCE = 30;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Paddle Attributes
  private int maxPaddleLength;
  private int minPaddleLength;

  //Paddle Associations
  private Game game;
  private List<PaddleOccurence> paddleOccurences;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Paddle(int aMaxPaddleLength, int aMinPaddleLength, Game aGame)
  {
    maxPaddleLength = aMaxPaddleLength;
    minPaddleLength = aMinPaddleLength;
    if (aGame == null || aGame.getPaddle() != null)
    {
      throw new RuntimeException("Unable to create Paddle due to aGame");
    }
    game = aGame;
    paddleOccurences = new ArrayList<PaddleOccurence>();
  }

  public Paddle(int aMaxPaddleLength, int aMinPaddleLength, String aNameForGame, int aNrBlocksPerLevelForGame, Admin aAdminForGame, Ball aBallForGame, Block223 aBlock223ForGame)
  {
    maxPaddleLength = aMaxPaddleLength;
    minPaddleLength = aMinPaddleLength;
    game = new Game(aNameForGame, aNrBlocksPerLevelForGame, aAdminForGame, aBallForGame, this, aBlock223ForGame);
    paddleOccurences = new ArrayList<PaddleOccurence>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMaxPaddleLength(int aMaxPaddleLength)
  {
    boolean wasSet = false;
    // line 227 "../../../../../Block223.ump"
    if(aMaxPaddleLength<1 || aMaxPaddleLength>Game.PLAY_AREA_SIDE){
       	throw new RuntimeException("The maximum length of the paddle must be greater than zero and less than or equal to "+Game.PLAY_AREA_SIDE+".");
       }
    // END OF UMPLE BEFORE INJECTION
    maxPaddleLength = aMaxPaddleLength;
    wasSet = true;
    return wasSet;
  }

  public boolean setMinPaddleLength(int aMinPaddleLength)
  {
    boolean wasSet = false;
    // line 222 "../../../../../Block223.ump"
    if(aMinPaddleLength<1){
       	throw new RuntimeException("The minimum length of the paddle must be greater than zero.");
       }
    // END OF UMPLE BEFORE INJECTION
    minPaddleLength = aMinPaddleLength;
    wasSet = true;
    return wasSet;
  }

  public int getMaxPaddleLength()
  {
    return maxPaddleLength;
  }

  public int getMinPaddleLength()
  {
    return minPaddleLength;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetMany */
  public PaddleOccurence getPaddleOccurence(int index)
  {
    PaddleOccurence aPaddleOccurence = paddleOccurences.get(index);
    return aPaddleOccurence;
  }

  public List<PaddleOccurence> getPaddleOccurences()
  {
    List<PaddleOccurence> newPaddleOccurences = Collections.unmodifiableList(paddleOccurences);
    return newPaddleOccurences;
  }

  public int numberOfPaddleOccurences()
  {
    int number = paddleOccurences.size();
    return number;
  }

  public boolean hasPaddleOccurences()
  {
    boolean has = paddleOccurences.size() > 0;
    return has;
  }

  public int indexOfPaddleOccurence(PaddleOccurence aPaddleOccurence)
  {
    int index = paddleOccurences.indexOf(aPaddleOccurence);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPaddleOccurences()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public PaddleOccurence addPaddleOccurence(int aCurrentPaddleLength, GameOccurence aGameOccurence)
  {
    return new PaddleOccurence(aCurrentPaddleLength, this, aGameOccurence);
  }

  public boolean addPaddleOccurence(PaddleOccurence aPaddleOccurence)
  {
    boolean wasAdded = false;
    if (paddleOccurences.contains(aPaddleOccurence)) { return false; }
    Paddle existingPaddle = aPaddleOccurence.getPaddle();
    boolean isNewPaddle = existingPaddle != null && !this.equals(existingPaddle);
    if (isNewPaddle)
    {
      aPaddleOccurence.setPaddle(this);
    }
    else
    {
      paddleOccurences.add(aPaddleOccurence);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePaddleOccurence(PaddleOccurence aPaddleOccurence)
  {
    boolean wasRemoved = false;
    //Unable to remove aPaddleOccurence, as it must always have a paddle
    if (!this.equals(aPaddleOccurence.getPaddle()))
    {
      paddleOccurences.remove(aPaddleOccurence);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPaddleOccurenceAt(PaddleOccurence aPaddleOccurence, int index)
  {  
    boolean wasAdded = false;
    if(addPaddleOccurence(aPaddleOccurence))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPaddleOccurences()) { index = numberOfPaddleOccurences() - 1; }
      paddleOccurences.remove(aPaddleOccurence);
      paddleOccurences.add(index, aPaddleOccurence);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePaddleOccurenceAt(PaddleOccurence aPaddleOccurence, int index)
  {
    boolean wasAdded = false;
    if(paddleOccurences.contains(aPaddleOccurence))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPaddleOccurences()) { index = numberOfPaddleOccurences() - 1; }
      paddleOccurences.remove(aPaddleOccurence);
      paddleOccurences.add(index, aPaddleOccurence);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPaddleOccurenceAt(aPaddleOccurence, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
    for(int i=paddleOccurences.size(); i > 0; i--)
    {
      PaddleOccurence aPaddleOccurence = paddleOccurences.get(i - 1);
      aPaddleOccurence.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "maxPaddleLength" + ":" + getMaxPaddleLength()+ "," +
            "minPaddleLength" + ":" + getMinPaddleLength()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 90 "../../../../../Block223Persistence.ump"
  private static final long serialVersionUID = 6895123766580505451L ;

  
}