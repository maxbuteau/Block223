/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.util.*;

// line 3 "../../../../../Block223.ump"
public class Block223
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Block223 Associations
  private List<User> users;
  private List<Player> players;
  private List<Admin> admins;
  private List<Game> games;
  private List<Level> levels;
  private List<Block> blocks;
  private List<BlockOccurence> blockOccurences;
  private List<Ball> balls;
  private List<Paddle> paddles;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Block223()
  {
    users = new ArrayList<User>();
    players = new ArrayList<Player>();
    admins = new ArrayList<Admin>();
    games = new ArrayList<Game>();
    levels = new ArrayList<Level>();
    blocks = new ArrayList<Block>();
    blockOccurences = new ArrayList<BlockOccurence>();
    balls = new ArrayList<Ball>();
    paddles = new ArrayList<Paddle>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
  }
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }
  /* Code from template association_GetMany */
  public Admin getAdmin(int index)
  {
    Admin aAdmin = admins.get(index);
    return aAdmin;
  }

  public List<Admin> getAdmins()
  {
    List<Admin> newAdmins = Collections.unmodifiableList(admins);
    return newAdmins;
  }

  public int numberOfAdmins()
  {
    int number = admins.size();
    return number;
  }

  public boolean hasAdmins()
  {
    boolean has = admins.size() > 0;
    return has;
  }

  public int indexOfAdmin(Admin aAdmin)
  {
    int index = admins.indexOf(aAdmin);
    return index;
  }
  /* Code from template association_GetMany */
  public Game getGame(int index)
  {
    Game aGame = games.get(index);
    return aGame;
  }

  public List<Game> getGames()
  {
    List<Game> newGames = Collections.unmodifiableList(games);
    return newGames;
  }

  public int numberOfGames()
  {
    int number = games.size();
    return number;
  }

  public boolean hasGames()
  {
    boolean has = games.size() > 0;
    return has;
  }

  public int indexOfGame(Game aGame)
  {
    int index = games.indexOf(aGame);
    return index;
  }
  /* Code from template association_GetMany */
  public Level getLevel(int index)
  {
    Level aLevel = levels.get(index);
    return aLevel;
  }

  public List<Level> getLevels()
  {
    List<Level> newLevels = Collections.unmodifiableList(levels);
    return newLevels;
  }

  public int numberOfLevels()
  {
    int number = levels.size();
    return number;
  }

  public boolean hasLevels()
  {
    boolean has = levels.size() > 0;
    return has;
  }

  public int indexOfLevel(Level aLevel)
  {
    int index = levels.indexOf(aLevel);
    return index;
  }
  /* Code from template association_GetMany */
  public Block getBlock(int index)
  {
    Block aBlock = blocks.get(index);
    return aBlock;
  }

  public List<Block> getBlocks()
  {
    List<Block> newBlocks = Collections.unmodifiableList(blocks);
    return newBlocks;
  }

  public int numberOfBlocks()
  {
    int number = blocks.size();
    return number;
  }

  public boolean hasBlocks()
  {
    boolean has = blocks.size() > 0;
    return has;
  }

  public int indexOfBlock(Block aBlock)
  {
    int index = blocks.indexOf(aBlock);
    return index;
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
  /* Code from template association_GetMany */
  public Ball getBall(int index)
  {
    Ball aBall = balls.get(index);
    return aBall;
  }

  public List<Ball> getBalls()
  {
    List<Ball> newBalls = Collections.unmodifiableList(balls);
    return newBalls;
  }

  public int numberOfBalls()
  {
    int number = balls.size();
    return number;
  }

  public boolean hasBalls()
  {
    boolean has = balls.size() > 0;
    return has;
  }

  public int indexOfBall(Ball aBall)
  {
    int index = balls.indexOf(aBall);
    return index;
  }
  /* Code from template association_GetMany */
  public Paddle getPaddle(int index)
  {
    Paddle aPaddle = paddles.get(index);
    return aPaddle;
  }

  public List<Paddle> getPaddles()
  {
    List<Paddle> newPaddles = Collections.unmodifiableList(paddles);
    return newPaddles;
  }

  public int numberOfPaddles()
  {
    int number = paddles.size();
    return number;
  }

  public boolean hasPaddles()
  {
    boolean has = paddles.size() > 0;
    return has;
  }

  public int indexOfPaddle(Paddle aPaddle)
  {
    int index = paddles.indexOf(aPaddle);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUsers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public User addUser(String aUsername)
  {
    return new User(aUsername, this);
  }

  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    Block223 existingBlock223 = aUser.getBlock223();
    boolean isNewBlock223 = existingBlock223 != null && !this.equals(existingBlock223);
    if (isNewBlock223)
    {
      aUser.setBlock223(this);
    }
    else
    {
      users.add(aUser);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;
    //Unable to remove aUser, as it must always have a block223
    if (!this.equals(aUser.getBlock223()))
    {
      users.remove(aUser);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserAt(User aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Player addPlayer(User aUser, String aPassword, Game aPlayedBy)
  {
    return new Player(aUser, aPassword, this, aPlayedBy);
  }

  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    Block223 existingBlock223 = aPlayer.getBlock223();
    boolean isNewBlock223 = existingBlock223 != null && !this.equals(existingBlock223);
    if (isNewBlock223)
    {
      aPlayer.setBlock223(this);
    }
    else
    {
      players.add(aPlayer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlayer, as it must always have a block223
    if (!this.equals(aPlayer.getBlock223()))
    {
      players.remove(aPlayer);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayerAt(Player aPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addPlayer(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerAt(aPlayer, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAdmins()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Admin addAdmin(User aUser, String aPassword)
  {
    return new Admin(aUser, aPassword, this);
  }

  public boolean addAdmin(Admin aAdmin)
  {
    boolean wasAdded = false;
    if (admins.contains(aAdmin)) { return false; }
    Block223 existingBlock223 = aAdmin.getBlock223();
    boolean isNewBlock223 = existingBlock223 != null && !this.equals(existingBlock223);
    if (isNewBlock223)
    {
      aAdmin.setBlock223(this);
    }
    else
    {
      admins.add(aAdmin);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAdmin(Admin aAdmin)
  {
    boolean wasRemoved = false;
    //Unable to remove aAdmin, as it must always have a block223
    if (!this.equals(aAdmin.getBlock223()))
    {
      admins.remove(aAdmin);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAdminAt(Admin aAdmin, int index)
  {  
    boolean wasAdded = false;
    if(addAdmin(aAdmin))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAdmins()) { index = numberOfAdmins() - 1; }
      admins.remove(aAdmin);
      admins.add(index, aAdmin);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAdminAt(Admin aAdmin, int index)
  {
    boolean wasAdded = false;
    if(admins.contains(aAdmin))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAdmins()) { index = numberOfAdmins() - 1; }
      admins.remove(aAdmin);
      admins.add(index, aAdmin);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAdminAt(aAdmin, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGames()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Game addGame(String aNameOfGame, int aNumberOfBlocks, double aPlayAreaWidth, double aPlayAreaHeight, int aNumberOfLevels, Admin aAdmin, Ball aBall, Paddle aPaddle)
  {
    return new Game(aNameOfGame, aNumberOfBlocks, aPlayAreaWidth, aPlayAreaHeight, aNumberOfLevels, aAdmin, aBall, aPaddle, this);
  }

  public boolean addGame(Game aGame)
  {
    boolean wasAdded = false;
    if (games.contains(aGame)) { return false; }
    Block223 existingBlock223 = aGame.getBlock223();
    boolean isNewBlock223 = existingBlock223 != null && !this.equals(existingBlock223);
    if (isNewBlock223)
    {
      aGame.setBlock223(this);
    }
    else
    {
      games.add(aGame);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGame(Game aGame)
  {
    boolean wasRemoved = false;
    //Unable to remove aGame, as it must always have a block223
    if (!this.equals(aGame.getBlock223()))
    {
      games.remove(aGame);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGameAt(Game aGame, int index)
  {  
    boolean wasAdded = false;
    if(addGame(aGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGames()) { index = numberOfGames() - 1; }
      games.remove(aGame);
      games.add(index, aGame);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGameAt(Game aGame, int index)
  {
    boolean wasAdded = false;
    if(games.contains(aGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGames()) { index = numberOfGames() - 1; }
      games.remove(aGame);
      games.add(index, aGame);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGameAt(aGame, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLevels()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Level addLevel(int aLevelNumber, boolean aIsRandom, Game aGame)
  {
    return new Level(aLevelNumber, aIsRandom, this, aGame);
  }

  public boolean addLevel(Level aLevel)
  {
    boolean wasAdded = false;
    if (levels.contains(aLevel)) { return false; }
    Block223 existingBlock223 = aLevel.getBlock223();
    boolean isNewBlock223 = existingBlock223 != null && !this.equals(existingBlock223);
    if (isNewBlock223)
    {
      aLevel.setBlock223(this);
    }
    else
    {
      levels.add(aLevel);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLevel(Level aLevel)
  {
    boolean wasRemoved = false;
    //Unable to remove aLevel, as it must always have a block223
    if (!this.equals(aLevel.getBlock223()))
    {
      levels.remove(aLevel);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLevelAt(Level aLevel, int index)
  {  
    boolean wasAdded = false;
    if(addLevel(aLevel))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLevels()) { index = numberOfLevels() - 1; }
      levels.remove(aLevel);
      levels.add(index, aLevel);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLevelAt(Level aLevel, int index)
  {
    boolean wasAdded = false;
    if(levels.contains(aLevel))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLevels()) { index = numberOfLevels() - 1; }
      levels.remove(aLevel);
      levels.add(index, aLevel);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLevelAt(aLevel, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBlocks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Block addBlock(int aRgb, int aWorth, Game aGame)
  {
    return new Block(aRgb, aWorth, this, aGame);
  }

  public boolean addBlock(Block aBlock)
  {
    boolean wasAdded = false;
    if (blocks.contains(aBlock)) { return false; }
    Block223 existingBlock223 = aBlock.getBlock223();
    boolean isNewBlock223 = existingBlock223 != null && !this.equals(existingBlock223);
    if (isNewBlock223)
    {
      aBlock.setBlock223(this);
    }
    else
    {
      blocks.add(aBlock);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBlock(Block aBlock)
  {
    boolean wasRemoved = false;
    //Unable to remove aBlock, as it must always have a block223
    if (!this.equals(aBlock.getBlock223()))
    {
      blocks.remove(aBlock);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBlockAt(Block aBlock, int index)
  {  
    boolean wasAdded = false;
    if(addBlock(aBlock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlocks()) { index = numberOfBlocks() - 1; }
      blocks.remove(aBlock);
      blocks.add(index, aBlock);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBlockAt(Block aBlock, int index)
  {
    boolean wasAdded = false;
    if(blocks.contains(aBlock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlocks()) { index = numberOfBlocks() - 1; }
      blocks.remove(aBlock);
      blocks.add(index, aBlock);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBlockAt(aBlock, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBlockOccurences()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BlockOccurence addBlockOccurence(int aXPosition, int aYPosition, Level aLevel, Block aBlock)
  {
    return new BlockOccurence(aXPosition, aYPosition, this, aLevel, aBlock);
  }

  public boolean addBlockOccurence(BlockOccurence aBlockOccurence)
  {
    boolean wasAdded = false;
    if (blockOccurences.contains(aBlockOccurence)) { return false; }
    Block223 existingBlock223 = aBlockOccurence.getBlock223();
    boolean isNewBlock223 = existingBlock223 != null && !this.equals(existingBlock223);
    if (isNewBlock223)
    {
      aBlockOccurence.setBlock223(this);
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
    //Unable to remove aBlockOccurence, as it must always have a block223
    if (!this.equals(aBlockOccurence.getBlock223()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBalls()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Ball addBall(double aMinSpeed, double aIncreasingFactor, Game aGame)
  {
    return new Ball(aMinSpeed, aIncreasingFactor, this, aGame);
  }

  public boolean addBall(Ball aBall)
  {
    boolean wasAdded = false;
    if (balls.contains(aBall)) { return false; }
    Block223 existingBlock223 = aBall.getBlock223();
    boolean isNewBlock223 = existingBlock223 != null && !this.equals(existingBlock223);
    if (isNewBlock223)
    {
      aBall.setBlock223(this);
    }
    else
    {
      balls.add(aBall);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBall(Ball aBall)
  {
    boolean wasRemoved = false;
    //Unable to remove aBall, as it must always have a block223
    if (!this.equals(aBall.getBlock223()))
    {
      balls.remove(aBall);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBallAt(Ball aBall, int index)
  {  
    boolean wasAdded = false;
    if(addBall(aBall))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBalls()) { index = numberOfBalls() - 1; }
      balls.remove(aBall);
      balls.add(index, aBall);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBallAt(Ball aBall, int index)
  {
    boolean wasAdded = false;
    if(balls.contains(aBall))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBalls()) { index = numberOfBalls() - 1; }
      balls.remove(aBall);
      balls.add(index, aBall);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBallAt(aBall, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPaddles()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Paddle addPaddle(double aMinLength, double aMaxLength, Game aGame)
  {
    return new Paddle(aMinLength, aMaxLength, this, aGame);
  }

  public boolean addPaddle(Paddle aPaddle)
  {
    boolean wasAdded = false;
    if (paddles.contains(aPaddle)) { return false; }
    Block223 existingBlock223 = aPaddle.getBlock223();
    boolean isNewBlock223 = existingBlock223 != null && !this.equals(existingBlock223);
    if (isNewBlock223)
    {
      aPaddle.setBlock223(this);
    }
    else
    {
      paddles.add(aPaddle);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePaddle(Paddle aPaddle)
  {
    boolean wasRemoved = false;
    //Unable to remove aPaddle, as it must always have a block223
    if (!this.equals(aPaddle.getBlock223()))
    {
      paddles.remove(aPaddle);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPaddleAt(Paddle aPaddle, int index)
  {  
    boolean wasAdded = false;
    if(addPaddle(aPaddle))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPaddles()) { index = numberOfPaddles() - 1; }
      paddles.remove(aPaddle);
      paddles.add(index, aPaddle);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePaddleAt(Paddle aPaddle, int index)
  {
    boolean wasAdded = false;
    if(paddles.contains(aPaddle))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPaddles()) { index = numberOfPaddles() - 1; }
      paddles.remove(aPaddle);
      paddles.add(index, aPaddle);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPaddleAt(aPaddle, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (users.size() > 0)
    {
      User aUser = users.get(users.size() - 1);
      aUser.delete();
      users.remove(aUser);
    }
    
    while (players.size() > 0)
    {
      Player aPlayer = players.get(players.size() - 1);
      aPlayer.delete();
      players.remove(aPlayer);
    }
    
    while (admins.size() > 0)
    {
      Admin aAdmin = admins.get(admins.size() - 1);
      aAdmin.delete();
      admins.remove(aAdmin);
    }
    
    while (games.size() > 0)
    {
      Game aGame = games.get(games.size() - 1);
      aGame.delete();
      games.remove(aGame);
    }
    
    while (levels.size() > 0)
    {
      Level aLevel = levels.get(levels.size() - 1);
      aLevel.delete();
      levels.remove(aLevel);
    }
    
    while (blocks.size() > 0)
    {
      Block aBlock = blocks.get(blocks.size() - 1);
      aBlock.delete();
      blocks.remove(aBlock);
    }
    
    while (blockOccurences.size() > 0)
    {
      BlockOccurence aBlockOccurence = blockOccurences.get(blockOccurences.size() - 1);
      aBlockOccurence.delete();
      blockOccurences.remove(aBlockOccurence);
    }
    
    while (balls.size() > 0)
    {
      Ball aBall = balls.get(balls.size() - 1);
      aBall.delete();
      balls.remove(aBall);
    }
    
    while (paddles.size() > 0)
    {
      Paddle aPaddle = paddles.get(paddles.size() - 1);
      aPaddle.delete();
      paddles.remove(aPaddle);
    }
    
  }

}