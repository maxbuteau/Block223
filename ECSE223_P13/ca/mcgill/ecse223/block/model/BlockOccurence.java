/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 68 "../../../../../Block223.ump"
public class BlockOccurence
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BlockOccurence Attributes
  private int xPosition;
  private int yPosition;

  //BlockOccurence Associations
  private Block223 block223;
  private Level level;
  private Block block;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BlockOccurence(int aXPosition, int aYPosition, Block223 aBlock223, Level aLevel, Block aBlock)
  {
    xPosition = aXPosition;
    yPosition = aYPosition;
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create blockOccurence due to block223");
    }
    boolean didAddLevel = setLevel(aLevel);
    if (!didAddLevel)
    {
      throw new RuntimeException("Unable to create blockOccurence due to level");
    }
    boolean didAddBlock = setBlock(aBlock);
    if (!didAddBlock)
    {
      throw new RuntimeException("Unable to create blockOccurence due to block");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setXPosition(int aXPosition)
  {
    boolean wasSet = false;
    xPosition = aXPosition;
    wasSet = true;
    return wasSet;
  }

  public boolean setYPosition(int aYPosition)
  {
    boolean wasSet = false;
    yPosition = aYPosition;
    wasSet = true;
    return wasSet;
  }

  public int getXPosition()
  {
    return xPosition;
  }

  public int getYPosition()
  {
    return yPosition;
  }
  /* Code from template association_GetOne */
  public Block223 getBlock223()
  {
    return block223;
  }
  /* Code from template association_GetOne */
  public Level getLevel()
  {
    return level;
  }
  /* Code from template association_GetOne */
  public Block getBlock()
  {
    return block;
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
      existingBlock223.removeBlockOccurence(this);
    }
    block223.addBlockOccurence(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setLevel(Level aLevel)
  {
    boolean wasSet = false;
    if (aLevel == null)
    {
      return wasSet;
    }

    Level existingLevel = level;
    level = aLevel;
    if (existingLevel != null && !existingLevel.equals(aLevel))
    {
      existingLevel.removeBlockOccurence(this);
    }
    level.addBlockOccurence(this);
    wasSet = true;
    return wasSet;
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

  public void delete()
  {
    Block223 placeholderBlock223 = block223;
    this.block223 = null;
    if(placeholderBlock223 != null)
    {
      placeholderBlock223.removeBlockOccurence(this);
    }
    Level placeholderLevel = level;
    this.level = null;
    if(placeholderLevel != null)
    {
      placeholderLevel.removeBlockOccurence(this);
    }
    Block placeholderBlock = block;
    this.block = null;
    if(placeholderBlock != null)
    {
      placeholderBlock.removeBlockOccurence(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "xPosition" + ":" + getXPosition()+ "," +
            "yPosition" + ":" + getYPosition()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "block223 = "+(getBlock223()!=null?Integer.toHexString(System.identityHashCode(getBlock223())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "level = "+(getLevel()!=null?Integer.toHexString(System.identityHashCode(getLevel())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "block = "+(getBlock()!=null?Integer.toHexString(System.identityHashCode(getBlock())):"null");
  }
}