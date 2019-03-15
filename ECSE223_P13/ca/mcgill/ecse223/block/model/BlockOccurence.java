/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 34 "../../../../../Block223Play.ump"
public class BlockOccurence
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BlockOccurence Attributes
  private int gridHorizontalPosition;
  private int gridVerticalPosition;

  //BlockOccurence Associations
  private Block block;
  private GameOccurence gameOccurence;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BlockOccurence(int aGridHorizontalPosition, int aGridVerticalPosition, Block aBlock, GameOccurence aGameOccurence)
  {
    gridHorizontalPosition = aGridHorizontalPosition;
    gridVerticalPosition = aGridVerticalPosition;
    boolean didAddBlock = setBlock(aBlock);
    if (!didAddBlock)
    {
      throw new RuntimeException("Unable to create blockOccurence due to block");
    }
    boolean didAddGameOccurence = setGameOccurence(aGameOccurence);
    if (!didAddGameOccurence)
    {
      throw new RuntimeException("Unable to create blockOccurence due to gameOccurence");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setGridHorizontalPosition(int aGridHorizontalPosition)
  {
    boolean wasSet = false;
    gridHorizontalPosition = aGridHorizontalPosition;
    wasSet = true;
    return wasSet;
  }

  public boolean setGridVerticalPosition(int aGridVerticalPosition)
  {
    boolean wasSet = false;
    gridVerticalPosition = aGridVerticalPosition;
    wasSet = true;
    return wasSet;
  }

  public int getGridHorizontalPosition()
  {
    return gridHorizontalPosition;
  }

  public int getGridVerticalPosition()
  {
    return gridVerticalPosition;
  }
  /* Code from template association_GetOne */
  public Block getBlock()
  {
    return block;
  }
  /* Code from template association_GetOne */
  public GameOccurence getGameOccurence()
  {
    return gameOccurence;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBlock(Block aBlock)
  {
    boolean wasSet = false;
    if (aBlock == null)
    {
      return wasSet;
    }

    Block existingBlock = block;
    block = aBlock;
    if (existingBlock != null && !existingBlock.equals(aBlock))
    {
      existingBlock.removeBlockOccurence(this);
    }
    block.addBlockOccurence(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGameOccurence(GameOccurence aGameOccurence)
  {
    boolean wasSet = false;
    if (aGameOccurence == null)
    {
      return wasSet;
    }

    GameOccurence existingGameOccurence = gameOccurence;
    gameOccurence = aGameOccurence;
    if (existingGameOccurence != null && !existingGameOccurence.equals(aGameOccurence))
    {
      existingGameOccurence.removeBlockOccurence(this);
    }
    gameOccurence.addBlockOccurence(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Block placeholderBlock = block;
    this.block = null;
    if(placeholderBlock != null)
    {
      placeholderBlock.removeBlockOccurence(this);
    }
    GameOccurence placeholderGameOccurence = gameOccurence;
    this.gameOccurence = null;
    if(placeholderGameOccurence != null)
    {
      placeholderGameOccurence.removeBlockOccurence(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "gridHorizontalPosition" + ":" + getGridHorizontalPosition()+ "," +
            "gridVerticalPosition" + ":" + getGridVerticalPosition()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "block = "+(getBlock()!=null?Integer.toHexString(System.identityHashCode(getBlock())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "gameOccurence = "+(getGameOccurence()!=null?Integer.toHexString(System.identityHashCode(getGameOccurence())):"null");
  }
}