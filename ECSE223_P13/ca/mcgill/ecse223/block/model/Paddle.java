/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 81 "../../../../../Block223.ump"
public class Paddle
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  public static final double WIDTH = 5;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Paddle Attributes
  private double minLength;
  private double maxLength;

  //Paddle Associations
  private Block223 block223;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Paddle(double aMinLength, double aMaxLength, Block223 aBlock223, Game aGame)
  {
    minLength = aMinLength;
    maxLength = aMaxLength;
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create paddle due to block223");
    }
    if (aGame == null || aGame.getPaddle() != null)
    {
      throw new RuntimeException("Unable to create Paddle due to aGame");
    }
    game = aGame;
  }

  public Paddle(double aMinLength, double aMaxLength, Block223 aBlock223, String aNameOfGameForGame, int aNumberOfBlocksForGame, double aPlayAreaWidthForGame, double aPlayAreaHeightForGame, int aNumberOfLevelsForGame, Admin aAdminForGame, Ball aBallForGame, Block223 aBlock223ForGame)
  {
    minLength = aMinLength;
    maxLength = aMaxLength;
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create paddle due to block223");
    }
    game = new Game(aNameOfGameForGame, aNumberOfBlocksForGame, aPlayAreaWidthForGame, aPlayAreaHeightForGame, aNumberOfLevelsForGame, aAdminForGame, aBallForGame, this, aBlock223ForGame);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMinLength(double aMinLength)
  {
    boolean wasSet = false;
    minLength = aMinLength;
    wasSet = true;
    return wasSet;
  }

  public boolean setMaxLength(double aMaxLength)
  {
    boolean wasSet = false;
    maxLength = aMaxLength;
    wasSet = true;
    return wasSet;
  }

  public double getMinLength()
  {
    return minLength;
  }

  public double getMaxLength()
  {
    return maxLength;
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
      existingBlock223.removePaddle(this);
    }
    block223.addPaddle(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Block223 placeholderBlock223 = block223;
    this.block223 = null;
    if(placeholderBlock223 != null)
    {
      placeholderBlock223.removePaddle(this);
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
            "minLength" + ":" + getMinLength()+ "," +
            "maxLength" + ":" + getMaxLength()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "block223 = "+(getBlock223()!=null?Integer.toHexString(System.identityHashCode(getBlock223())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}