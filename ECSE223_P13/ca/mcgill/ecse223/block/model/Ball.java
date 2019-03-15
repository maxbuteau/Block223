/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.io.Serializable;
import java.util.*;

// line 82 "../../../../../Block223Persistence.ump"
// line 200 "../../../../../Block223.ump"
public class Ball implements Serializable
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  public static final int BALL_DIAMETER = 10;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Ball Attributes
  private int minBallSpeedX;
  private int minBallSpeedY;
  private double ballSpeedIncreaseFactor;

  //Ball Associations
  private Game game;
  private List<BallOccurence> ballOccurences;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Ball(int aMinBallSpeedX, int aMinBallSpeedY, double aBallSpeedIncreaseFactor, Game aGame)
  {
    minBallSpeedX = aMinBallSpeedX;
    minBallSpeedY = aMinBallSpeedY;
    ballSpeedIncreaseFactor = aBallSpeedIncreaseFactor;
    if (aGame == null || aGame.getBall() != null)
    {
      throw new RuntimeException("Unable to create Ball due to aGame");
    }
    game = aGame;
    ballOccurences = new ArrayList<BallOccurence>();
  }

  public Ball(int aMinBallSpeedX, int aMinBallSpeedY, double aBallSpeedIncreaseFactor, String aNameForGame, int aNrBlocksPerLevelForGame, Admin aAdminForGame, Paddle aPaddleForGame, Block223 aBlock223ForGame)
  {
    minBallSpeedX = aMinBallSpeedX;
    minBallSpeedY = aMinBallSpeedY;
    ballSpeedIncreaseFactor = aBallSpeedIncreaseFactor;
    game = new Game(aNameForGame, aNrBlocksPerLevelForGame, aAdminForGame, this, aPaddleForGame, aBlock223ForGame);
    ballOccurences = new ArrayList<BallOccurence>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMinBallSpeedX(int aMinBallSpeedX)
  {
    boolean wasSet = false;
    minBallSpeedX = aMinBallSpeedX;
    wasSet = true;
    return wasSet;
  }

  public boolean setMinBallSpeedY(int aMinBallSpeedY)
  {
    boolean wasSet = false;
    minBallSpeedY = aMinBallSpeedY;
    wasSet = true;
    return wasSet;
  }

  public boolean setBallSpeedIncreaseFactor(double aBallSpeedIncreaseFactor)
  {
    boolean wasSet = false;
    // line 208 "../../../../../Block223.ump"
    if(!(aBallSpeedIncreaseFactor>0)){
       	throw new RuntimeException("The speed increase factor of the ball must be greater than zero.");
       }
    // END OF UMPLE BEFORE INJECTION
    ballSpeedIncreaseFactor = aBallSpeedIncreaseFactor;
    wasSet = true;
    return wasSet;
  }

  public int getMinBallSpeedX()
  {
    return minBallSpeedX;
  }

  public int getMinBallSpeedY()
  {
    return minBallSpeedY;
  }

  public double getBallSpeedIncreaseFactor()
  {
    return ballSpeedIncreaseFactor;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetMany */
  public BallOccurence getBallOccurence(int index)
  {
    BallOccurence aBallOccurence = ballOccurences.get(index);
    return aBallOccurence;
  }

  public List<BallOccurence> getBallOccurences()
  {
    List<BallOccurence> newBallOccurences = Collections.unmodifiableList(ballOccurences);
    return newBallOccurences;
  }

  public int numberOfBallOccurences()
  {
    int number = ballOccurences.size();
    return number;
  }

  public boolean hasBallOccurences()
  {
    boolean has = ballOccurences.size() > 0;
    return has;
  }

  public int indexOfBallOccurence(BallOccurence aBallOccurence)
  {
    int index = ballOccurences.indexOf(aBallOccurence);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBallOccurences()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BallOccurence addBallOccurence(GameOccurence aGameOccurence)
  {
    return new BallOccurence(this, aGameOccurence);
  }

  public boolean addBallOccurence(BallOccurence aBallOccurence)
  {
    boolean wasAdded = false;
    if (ballOccurences.contains(aBallOccurence)) { return false; }
    Ball existingBall = aBallOccurence.getBall();
    boolean isNewBall = existingBall != null && !this.equals(existingBall);
    if (isNewBall)
    {
      aBallOccurence.setBall(this);
    }
    else
    {
      ballOccurences.add(aBallOccurence);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBallOccurence(BallOccurence aBallOccurence)
  {
    boolean wasRemoved = false;
    //Unable to remove aBallOccurence, as it must always have a ball
    if (!this.equals(aBallOccurence.getBall()))
    {
      ballOccurences.remove(aBallOccurence);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBallOccurenceAt(BallOccurence aBallOccurence, int index)
  {  
    boolean wasAdded = false;
    if(addBallOccurence(aBallOccurence))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBallOccurences()) { index = numberOfBallOccurences() - 1; }
      ballOccurences.remove(aBallOccurence);
      ballOccurences.add(index, aBallOccurence);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBallOccurenceAt(BallOccurence aBallOccurence, int index)
  {
    boolean wasAdded = false;
    if(ballOccurences.contains(aBallOccurence))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBallOccurences()) { index = numberOfBallOccurences() - 1; }
      ballOccurences.remove(aBallOccurence);
      ballOccurences.add(index, aBallOccurence);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBallOccurenceAt(aBallOccurence, index);
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
    while (ballOccurences.size() > 0)
    {
      BallOccurence aBallOccurence = ballOccurences.get(ballOccurences.size() - 1);
      aBallOccurence.delete();
      ballOccurences.remove(aBallOccurence);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "minBallSpeedX" + ":" + getMinBallSpeedX()+ "," +
            "minBallSpeedY" + ":" + getMinBallSpeedY()+ "," +
            "ballSpeedIncreaseFactor" + ":" + getBallSpeedIncreaseFactor()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 85 "../../../../../Block223Persistence.ump"
  private static final long serialVersionUID = 145021574790956578L ;

  
}