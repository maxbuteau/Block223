/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 18 "../../../../../Block223Play.ump"
public class BallOccurence
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BallOccurence Attributes
  private int ballPositionX;
  private int ballPositionY;
  private int ballDirectionX;
  private int ballDirectionY;

  //BallOccurence Associations
  private Ball ball;
  private GameOccurence gameOccurence;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BallOccurence(Ball aBall, GameOccurence aGameOccurence)
  {
    resetBallPositionX();
    resetBallPositionY();
    resetBallDirectionX();
    resetBallDirectionY();
    boolean didAddBall = setBall(aBall);
    if (!didAddBall)
    {
      throw new RuntimeException("Unable to create ballOccurence due to ball");
    }
    if (aGameOccurence == null || aGameOccurence.getBallOccurence() != null)
    {
      throw new RuntimeException("Unable to create BallOccurence due to aGameOccurence");
    }
    gameOccurence = aGameOccurence;
  }

  public BallOccurence(Ball aBall, int aCurrentLevelForGameOccurence, Game aGameForGameOccurence, PaddleOccurence aPaddleOccurenceForGameOccurence)
  {
    resetBallPositionX();
    resetBallPositionY();
    resetBallDirectionX();
    resetBallDirectionY();
    boolean didAddBall = setBall(aBall);
    if (!didAddBall)
    {
      throw new RuntimeException("Unable to create ballOccurence due to ball");
    }
    gameOccurence = new GameOccurence(aCurrentLevelForGameOccurence, aGameForGameOccurence, this, aPaddleOccurenceForGameOccurence);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template attribute_SetDefaulted */
  public boolean setBallPositionX(int aBallPositionX)
  {
    boolean wasSet = false;
    ballPositionX = aBallPositionX;
    wasSet = true;
    return wasSet;
  }

  public boolean resetBallPositionX()
  {
    boolean wasReset = false;
    ballPositionX = getDefaultBallPositionX();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setBallPositionY(int aBallPositionY)
  {
    boolean wasSet = false;
    ballPositionY = aBallPositionY;
    wasSet = true;
    return wasSet;
  }

  public boolean resetBallPositionY()
  {
    boolean wasReset = false;
    ballPositionY = getDefaultBallPositionY();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setBallDirectionX(int aBallDirectionX)
  {
    boolean wasSet = false;
    ballDirectionX = aBallDirectionX;
    wasSet = true;
    return wasSet;
  }

  public boolean resetBallDirectionX()
  {
    boolean wasReset = false;
    ballDirectionX = getDefaultBallDirectionX();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setBallDirectionY(int aBallDirectionY)
  {
    boolean wasSet = false;
    ballDirectionY = aBallDirectionY;
    wasSet = true;
    return wasSet;
  }

  public boolean resetBallDirectionY()
  {
    boolean wasReset = false;
    ballDirectionY = getDefaultBallDirectionY();
    wasReset = true;
    return wasReset;
  }

  public int getBallPositionX()
  {
    return ballPositionX;
  }
  /* Code from template attribute_GetDefaulted */
  public int getDefaultBallPositionX()
  {
    return Game.PLAY_AREA_SIDE/2;
  }

  public int getBallPositionY()
  {
    return ballPositionY;
  }
  /* Code from template attribute_GetDefaulted */
  public int getDefaultBallPositionY()
  {
    return Game.PLAY_AREA_SIDE/2;
  }

  public int getBallDirectionX()
  {
    return ballDirectionX;
  }
  /* Code from template attribute_GetDefaulted */
  public int getDefaultBallDirectionX()
  {
    return 0;
  }

  public int getBallDirectionY()
  {
    return ballDirectionY;
  }
  /* Code from template attribute_GetDefaulted */
  public int getDefaultBallDirectionY()
  {
    return 1;
  }
  /* Code from template association_GetOne */
  public Ball getBall()
  {
    return ball;
  }
  /* Code from template association_GetOne */
  public GameOccurence getGameOccurence()
  {
    return gameOccurence;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBall(Ball aBall)
  {
    boolean wasSet = false;
    if (aBall == null)
    {
      return wasSet;
    }

    Ball existingBall = ball;
    ball = aBall;
    if (existingBall != null && !existingBall.equals(aBall))
    {
      existingBall.removeBallOccurence(this);
    }
    ball.addBallOccurence(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Ball existingBall = ball;
    ball = null;
    if (existingBall != null)
    {
      existingBall.delete();
    }
    GameOccurence existingGameOccurence = gameOccurence;
    gameOccurence = null;
    if (existingGameOccurence != null)
    {
      existingGameOccurence.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "ballPositionX" + ":" + getBallPositionX()+ "," +
            "ballPositionY" + ":" + getBallPositionY()+ "," +
            "ballDirectionX" + ":" + getBallDirectionX()+ "," +
            "ballDirectionY" + ":" + getBallDirectionY()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "ball = "+(getBall()!=null?Integer.toHexString(System.identityHashCode(getBall())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "gameOccurence = "+(getGameOccurence()!=null?Integer.toHexString(System.identityHashCode(getGameOccurence())):"null");
  }
}