/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.util.*;

// line 26 "../../../../../Block223.ump"
public class Admin extends UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Admin Attributes
  private String password;

  //Admin Associations
  private Block223 block223;
  private List<Game> CreatedBy;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Admin(User aUser, String aPassword, Block223 aBlock223)
  {
    super(aUser);
    password = aPassword;
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create admin due to block223");
    }
    CreatedBy = new ArrayList<Game>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public String getPassword()
  {
    return password;
  }
  /* Code from template association_GetOne */
  public Block223 getBlock223()
  {
    return block223;
  }
  /* Code from template association_GetMany */
  public Game getCreatedBy(int index)
  {
    Game aCreatedBy = CreatedBy.get(index);
    return aCreatedBy;
  }

  public List<Game> getCreatedBy()
  {
    List<Game> newCreatedBy = Collections.unmodifiableList(CreatedBy);
    return newCreatedBy;
  }

  public int numberOfCreatedBy()
  {
    int number = CreatedBy.size();
    return number;
  }

  public boolean hasCreatedBy()
  {
    boolean has = CreatedBy.size() > 0;
    return has;
  }

  public int indexOfCreatedBy(Game aCreatedBy)
  {
    int index = CreatedBy.indexOf(aCreatedBy);
    return index;
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
      existingBlock223.removeAdmin(this);
    }
    block223.addAdmin(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCreatedBy()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Game addCreatedBy(String aNameOfGame, int aNumberOfBlocks, double aPlayAreaWidth, double aPlayAreaHeight, int aNumberOfLevels, Ball aBall, Paddle aPaddle, Block223 aBlock223)
  {
    return new Game(aNameOfGame, aNumberOfBlocks, aPlayAreaWidth, aPlayAreaHeight, aNumberOfLevels, this, aBall, aPaddle, aBlock223);
  }

  public boolean addCreatedBy(Game aCreatedBy)
  {
    boolean wasAdded = false;
    if (CreatedBy.contains(aCreatedBy)) { return false; }
    Admin existingAdmin = aCreatedBy.getAdmin();
    boolean isNewAdmin = existingAdmin != null && !this.equals(existingAdmin);
    if (isNewAdmin)
    {
      aCreatedBy.setAdmin(this);
    }
    else
    {
      CreatedBy.add(aCreatedBy);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCreatedBy(Game aCreatedBy)
  {
    boolean wasRemoved = false;
    //Unable to remove aCreatedBy, as it must always have a admin
    if (!this.equals(aCreatedBy.getAdmin()))
    {
      CreatedBy.remove(aCreatedBy);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCreatedByAt(Game aCreatedBy, int index)
  {  
    boolean wasAdded = false;
    if(addCreatedBy(aCreatedBy))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCreatedBy()) { index = numberOfCreatedBy() - 1; }
      CreatedBy.remove(aCreatedBy);
      CreatedBy.add(index, aCreatedBy);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCreatedByAt(Game aCreatedBy, int index)
  {
    boolean wasAdded = false;
    if(CreatedBy.contains(aCreatedBy))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCreatedBy()) { index = numberOfCreatedBy() - 1; }
      CreatedBy.remove(aCreatedBy);
      CreatedBy.add(index, aCreatedBy);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCreatedByAt(aCreatedBy, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Block223 placeholderBlock223 = block223;
    this.block223 = null;
    if(placeholderBlock223 != null)
    {
      placeholderBlock223.removeAdmin(this);
    }
    for(int i=CreatedBy.size(); i > 0; i--)
    {
      Game aCreatedBy = CreatedBy.get(i - 1);
      aCreatedBy.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "password" + ":" + getPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "block223 = "+(getBlock223()!=null?Integer.toHexString(System.identityHashCode(getBlock223())):"null");
  }
}