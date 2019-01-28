/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 74 "../../../../../Block223.ump"
public class Ball
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  public static final double DIAMETER = 10;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Ball Attributes
  private double minSpeed;
  private double increasingFactor;

  //Ball Associations
  private Block223 block223;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Ball(double aMinSpeed, double aIncreasingFactor, Block223 aBlock223, Game aGame)
  {
    minSpeed = aMinSpeed;
    increasingFactor = aIncreasingFactor;
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create ball due to block223");
    }
    if (aGame == null || aGame.getBall() != null)
    {
      throw new RuntimeException("Unable to create Ball due to aGame");
    }
    game = aGame;
  }

  public Ball(double aMinSpeed, double aIncreasingFactor, Block223 aBlock223, String aNameOfGameForGame, int aNumberOfBlocksForGame, double aPlayAreaWidthForGame, double aPlayAreaHeightForGame, int aNumberOfLevelsForGame, Admin aAdminForGame, Paddle aPaddleForGame, Block223 aBlock223ForGame)
  {
    minSpeed = aMinSpeed;
    increasingFactor = aIncreasingFactor;
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create ball due to block223");
    }
    game = new Game(aNameOfGameForGame, aNumberOfBlocksForGame, aPlayAreaWidthForGame, aPlayAreaHeightForGame, aNumberOfLevelsForGame, aAdminForGame, this, aPaddleForGame, aBlock223ForGame);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMinSpeed(double aMinSpeed)
  {
    boolean wasSet = false;
    minSpeed = aMinSpeed;
    wasSet = true;
    return wasSet;
  }

  public boolean setIncreasingFactor(double aIncreasingFactor)
  {
    boolean wasSet = false;
    increasingFactor = aIncreasingFactor;
    wasSet = true;
    return wasSet;
  }

  public double getMinSpeed()
  {
    return minSpeed;
  }

  public double getIncreasingFactor()
  {
    return increasingFactor;
  }
  /* Code from template association_GetOne */
  public Block223 getBlock223()
  {
    return block223;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBlock223(Block223 aBlock223)
  {
    boolean wasSet = false;
    if (aBlock223 == null)
    {
      return wasSet;
    }

    Block223 existingBlock223 = block223;
    block223 = aBlock223;
    if (existingBlock223 != null && !existingBlock223.equals(aBlock223))
    {
      existingBlock223.removeBall(this);
    }
    block223.addBall(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Block223 placeholderBlock223 = block223;
    this.block223 = null;
    if(placeholderBlock223 != null)
    {
      placeholderBlock223.removeBall(this);
    }
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "minSpeed" + ":" + getMinSpeed()+ "," +
            "increasingFactor" + ":" + getIncreasingFactor()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "block223 = "+(getBlock223()!=null?Integer.toHexString(System.identityHashCode(getBlock223())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}