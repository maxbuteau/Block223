/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 32 "../../../../../Block223.ump"
public class Player extends UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private String password;

  //Player Associations
  private Block223 block223;
  private Game PlayedBy;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(User aUser, String aPassword, Block223 aBlock223, Game aPlayedBy)
  {
    super(aUser);
    password = aPassword;
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create player due to block223");
    }
    boolean didAddPlayedBy = setPlayedBy(aPlayedBy);
    if (!didAddPlayedBy)
    {
      throw new RuntimeException("Unable to create player due to PlayedBy");
    }
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
  /* Code from template association_GetOne */
  public Game getPlayedBy()
  {
    return PlayedBy;
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
      existingBlock223.removePlayer(this);
    }
    block223.addPlayer(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setPlayedBy(Game aPlayedBy)
  {
    boolean wasSet = false;
    if (aPlayedBy == null)
    {
      return wasSet;
    }

    Game existingPlayedBy = PlayedBy;
    PlayedBy = aPlayedBy;
    if (existingPlayedBy != null && !existingPlayedBy.equals(aPlayedBy))
    {
      existingPlayedBy.removePlayer(this);
    }
    PlayedBy.addPlayer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Block223 placeholderBlock223 = block223;
    this.block223 = null;
    if(placeholderBlock223 != null)
    {
      placeholderBlock223.removePlayer(this);
    }
    Game placeholderPlayedBy = PlayedBy;
    this.PlayedBy = null;
    if(placeholderPlayedBy != null)
    {
      placeholderPlayedBy.removePlayer(this);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "password" + ":" + getPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "block223 = "+(getBlock223()!=null?Integer.toHexString(System.identityHashCode(getBlock223())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "PlayedBy = "+(getPlayedBy()!=null?Integer.toHexString(System.identityHashCode(getPlayedBy())):"null");
  }
}