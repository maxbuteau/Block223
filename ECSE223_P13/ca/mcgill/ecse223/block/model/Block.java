/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.util.*;

// line 60 "../../../../../Block223.ump"
public class Block
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  public static final double SIDE_LENGTH = 20;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Block Attributes
  private int rgb;
  private int worth;

  //Block Associations
  private List<BlockOccurence> blockOccurences;
  private Block223 block223;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Block(int aRgb, int aWorth, Block223 aBlock223, Game aGame)
  {
    rgb = aRgb;
    worth = aWorth;
    blockOccurences = new ArrayList<BlockOccurence>();
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create block due to block223");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create block due to game");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRgb(int aRgb)
  {
    boolean wasSet = false;
    rgb = aRgb;
    wasSet = true;
    return wasSet;
  }

  public boolean setWorth(int aWorth)
  {
    boolean wasSet = false;
    worth = aWorth;
    wasSet = true;
    return wasSet;
  }

  public int getRgb()
  {
    return rgb;
  }

  public int getWorth()
  {
    return worth;
  }
  /* Code from template association_GetMany */
  public BlockOccurence getBlockOccurence(int index)
  {
    BlockOccurence aBlockOccurence = blockOccurences.get(index);
    return aBlockOccurence;
  }

  public List<BlockOccurence> getBlockOccurences()
  {
    List<BlockOccurence> newBlockOccurences = Collections.unmodifiableList(blockOccurences);
    return newBlockOccurences;
  }

  public int numberOfBlockOccurences()
  {
    int number = blockOccurences.size();
    return number;
  }

  public boolean hasBlockOccurences()
  {
    boolean has = blockOccurences.size() > 0;
    return has;
  }

  public int indexOfBlockOccurence(BlockOccurence aBlockOccurence)
  {
    int index = blockOccurences.indexOf(aBlockOccurence);
    return index;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBlockOccurences()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BlockOccurence addBlockOccurence(int aXPosition, int aYPosition, Block223 aBlock223, Level aLevel)
  {
    return new BlockOccurence(aXPosition, aYPosition, aBlock223, aLevel, this);
  }

  public boolean addBlockOccurence(BlockOccurence aBlockOccurence)
  {
    boolean wasAdded = false;
    if (blockOccurences.contains(aBlockOccurence)) { return false; }
    Block existingBlock = aBlockOccurence.getBlock();
    boolean isNewBlock = existingBlock != null && !this.equals(existingBlock);
    if (isNewBlock)
    {
      aBlockOccurence.setBlock(this);
    }
    else
    {
      blockOccurences.add(aBlockOccurence);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBlockOccurence(BlockOccurence aBlockOccurence)
  {
    boolean wasRemoved = false;
    //Unable to remove aBlockOccurence, as it must always have a block
    if (!this.equals(aBlockOccurence.getBlock()))
    {
      blockOccurences.remove(aBlockOccurence);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBlockOccurenceAt(BlockOccurence aBlockOccurence, int index)
  {  
    boolean wasAdded = false;
    if(addBlockOccurence(aBlockOccurence))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlockOccurences()) { index = numberOfBlockOccurences() - 1; }
      blockOccurences.remove(aBlockOccurence);
      blockOccurences.add(index, aBlockOccurence);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBlockOccurenceAt(BlockOccurence aBlockOccurence, int index)
  {
    boolean wasAdded = false;
    if(blockOccurences.contains(aBlockOccurence))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlockOccurences()) { index = numberOfBlockOccurences() - 1; }
      blockOccurences.remove(aBlockOccurence);
      blockOccurences.add(index, aBlockOccurence);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBlockOccurenceAt(aBlockOccurence, index);
    }
    return wasAdded;
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
      existingBlock223.removeBlock(this);
    }
    block223.addBlock(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (aGame == null)
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removeBlock(this);
    }
    game.addBlock(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=blockOccurences.size(); i > 0; i--)
    {
      BlockOccurence aBlockOccurence = blockOccurences.get(i - 1);
      aBlockOccurence.delete();
    }
    Block223 placeholderBlock223 = block223;
    this.block223 = null;
    if(placeholderBlock223 != null)
    {
      placeholderBlock223.removeBlock(this);
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeBlock(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "rgb" + ":" + getRgb()+ "," +
            "worth" + ":" + getWorth()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "block223 = "+(getBlock223()!=null?Integer.toHexString(System.identityHashCode(getBlock223())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}