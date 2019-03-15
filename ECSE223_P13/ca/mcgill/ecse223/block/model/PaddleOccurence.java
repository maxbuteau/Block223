/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 27 "../../../../../Block223Play.ump"
public class PaddleOccurence
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PaddleOccurence Attributes
  private int currentPaddleLength;
  private int paddlePositionX;

  //PaddleOccurence Associations
  private Paddle paddle;
  private GameOccurence gameOccurence;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PaddleOccurence(int aCurrentPaddleLength, Paddle aPaddle, GameOccurence aGameOccurence)
  {
    currentPaddleLength = aCurrentPaddleLength;
    paddlePositionX = Game.PLAY_AREA_SIDE/2 - (currentPaddleLength/2);
    boolean didAddPaddle = setPaddle(aPaddle);
    if (!didAddPaddle)
    {
      throw new RuntimeException("Unable to create paddleOccurence due to paddle");
    }
    if (aGameOccurence == null || aGameOccurence.getPaddleOccurence() != null)
    {
      throw new RuntimeException("Unable to create PaddleOccurence due to aGameOccurence");
    }
    gameOccurence = aGameOccurence;
  }

  public PaddleOccurence(int aCurrentPaddleLength, Paddle aPaddle, int aCurrentLevelForGameOccurence, Game aGameForGameOccurence, BallOccurence aBallOccurenceForGameOccurence)
  {
    currentPaddleLength = aCurrentPaddleLength;
    paddlePositionX = Game.PLAY_AREA_SIDE/2 - (currentPaddleLength/2);
    boolean didAddPaddle = setPaddle(aPaddle);
    if (!didAddPaddle)
    {
      throw new RuntimeException("Unable to create paddleOccurence due to paddle");
    }
    gameOccurence = new GameOccurence(aCurrentLevelForGameOccurence, aGameForGameOccurence, aBallOccurenceForGameOccurence, this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCurrentPaddleLength(int aCurrentPaddleLength)
  {
    boolean wasSet = false;
    currentPaddleLength = aCurrentPaddleLength;
    wasSet = true;
    return wasSet;
  }

  public boolean setPaddlePositionX(int aPaddlePositionX)
  {
    boolean wasSet = false;
    paddlePositionX = aPaddlePositionX;
    wasSet = true;
    return wasSet;
  }

  public int getCurrentPaddleLength()
  {
    return currentPaddleLength;
  }

  public int getPaddlePositionX()
  {
    return paddlePositionX;
  }
  /* Code from template association_GetOne */
  public Paddle getPaddle()
  {
    return paddle;
  }
  /* Code from template association_GetOne */
  public GameOccurence getGameOccurence()
  {
    return gameOccurence;
  }
  /* Code from template association_SetOneToMany */
  public boolean setPaddle(Paddle aPaddle)
  {
    boolean wasSet = false;
    if (aPaddle == null)
    {
      return wasSet;
    }

    Paddle existingPaddle = paddle;
    paddle = aPaddle;
    if (existingPaddle != null && !existingPaddle.equals(aPaddle))
    {
      existingPaddle.removePaddleOccurence(this);
    }
    paddle.addPaddleOccurence(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Paddle existingPaddle = paddle;
    paddle = null;
    if (existingPaddle != null)
    {
      existingPaddle.delete();
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
            "currentPaddleLength" + ":" + getCurrentPaddleLength()+ "," +
            "paddlePositionX" + ":" + getPaddlePositionX()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "paddle = "+(getPaddle()!=null?Integer.toHexString(System.identityHashCode(getPaddle())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "gameOccurence = "+(getGameOccurence()!=null?Integer.toHexString(System.identityHashCode(getGameOccurence())):"null");
  }
}