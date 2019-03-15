/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.util.*;

// line 1 "../../../../../Block223StateMachine.ump"
// line 1 "../../../../../Block223Play.ump"
public class GameOccurence
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  public static final int MAX_NUM_OF_LIVES = 3;
  public static final int MIN_NUM_OF_LIVES = 0;
  private static int nextGameOccurenceID = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GameOccurence Attributes
  private int currentLevel;
  private int nbOfLives;

  //Autounique Attributes
  private int gameOccurenceID;

  //GameOccurence State Machines
  public enum GameState { Idle, Paused, Play, Done }
  private GameState gameState;

  //GameOccurence Associations
  private Game game;
  private BallOccurence ballOccurence;
  private PaddleOccurence paddleOccurence;
  private List<BlockOccurence> blockOccurences;
  private Block223 block223;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GameOccurence(int aCurrentLevel, Game aGame, BallOccurence aBallOccurence, PaddleOccurence aPaddleOccurence, Block223 aBlock223)
  {
    currentLevel = aCurrentLevel;
    nbOfLives = MAX_NUM_OF_LIVES;
    gameOccurenceID = nextGameOccurenceID++;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create gameOccurence due to game");
    }
    if (aBallOccurence == null || aBallOccurence.getGameOccurence() != null)
    {
      throw new RuntimeException("Unable to create GameOccurence due to aBallOccurence");
    }
    ballOccurence = aBallOccurence;
    if (aPaddleOccurence == null || aPaddleOccurence.getGameOccurence() != null)
    {
      throw new RuntimeException("Unable to create GameOccurence due to aPaddleOccurence");
    }
    paddleOccurence = aPaddleOccurence;
    blockOccurences = new ArrayList<BlockOccurence>();
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create gameOccurence due to block223");
    }
    setGameState(GameState.Idle);
  }

  public GameOccurence(int aCurrentLevel, Game aGame, Ball aBallForBallOccurence, int aCurrentPaddleLengthForPaddleOccurence, Paddle aPaddleForPaddleOccurence, Block223 aBlock223)
  {
    currentLevel = aCurrentLevel;
    nbOfLives = MAX_NUM_OF_LIVES;
    gameOccurenceID = nextGameOccurenceID++;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create gameOccurence due to game");
    }
    ballOccurence = new BallOccurence(aBallForBallOccurence, this);
    paddleOccurence = new PaddleOccurence(aCurrentPaddleLengthForPaddleOccurence, aPaddleForPaddleOccurence, this);
    blockOccurences = new ArrayList<BlockOccurence>();
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create gameOccurence due to block223");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCurrentLevel(int aCurrentLevel)
  {
    boolean wasSet = false;
    currentLevel = aCurrentLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setNbOfLives(int aNbOfLives)
  {
    boolean wasSet = false;
    nbOfLives = aNbOfLives;
    wasSet = true;
    return wasSet;
  }

  public int getCurrentLevel()
  {
    return currentLevel;
  }

  public int getNbOfLives()
  {
    return nbOfLives;
  }

  public int getGameOccurenceID()
  {
    return gameOccurenceID;
  }

  public String getGameStateFullName()
  {
    String answer = gameState.toString();
    return answer;
  }

  public GameState getGameState()
  {
    return gameState;
  }

  public boolean startGame()
  {
    boolean wasEventProcessed = false;
    
    GameState aGameState = gameState;
    switch (aGameState)
    {
      case Idle:
        if (hasEnoughBlockAssignments())
        {
        // line 6 "../../../../../Block223StateMachine.ump"
          doCreateScore();
      	doGetCurrentLevelBlocks();
          setGameState(GameState.Paused);
          wasEventProcessed = true;
          break;
        }
        if (!(hasEnoughBlockAssignments()))
        {
        // line 11 "../../../../../Block223StateMachine.ump"
          doCreateScore();
      	doGetCurrentLevelBlocks();
      	doPlaceRandomBlocks();
          setGameState(GameState.Paused);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean spaceBarHit()
  {
    boolean wasEventProcessed = false;
    
    GameState aGameState = gameState;
    switch (aGameState)
    {
      case Paused:
        setGameState(GameState.Play);
        wasEventProcessed = true;
        break;
      case Play:
        setGameState(GameState.Paused);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean move()
  {
    boolean wasEventProcessed = false;
    
    GameState aGameState = gameState;
    switch (aGameState)
    {
      case Play:
        if (isBlockHit()&&isLastBlockHit())
        {
        // line 26 "../../../../../Block223StateMachine.ump"
          doDeleteBlock(); 
        doIncrementLevel();
        doGetCurrentLevelBlocks();
        if(!hasEnoughBlockAssignments()){
        doPlaceRandomBlocks();
        }
          setGameState(GameState.Paused);
          wasEventProcessed = true;
          break;
        }
        if (isWallHit())
        {
        // line 36 "../../../../../Block223StateMachine.ump"
          doWallHit();
          setGameState(GameState.Play);
          wasEventProcessed = true;
          break;
        }
        if (isPaddleHit())
        {
        // line 40 "../../../../../Block223StateMachine.ump"
          doPaddleHit();
          setGameState(GameState.Play);
          wasEventProcessed = true;
          break;
        }
        if (isOutOfBounds()&&hasRemainingLives())
        {
        // line 44 "../../../../../Block223StateMachine.ump"
          doDecrementLifeCount();
      	doResetBallPosition();
          setGameState(GameState.Paused);
          wasEventProcessed = true;
          break;
        }
        if (isBlockHit()&&isLastBlockHit()&&isLastLevel())
        {
        // line 50 "../../../../../Block223StateMachine.ump"
          doDeleteBlock();
      	doDeleteCurrentGame();
          setGameState(GameState.Done);
          wasEventProcessed = true;
          break;
        }
        if (isOutOfBounds()&&!(hasRemainingLives()))
        {
        // line 56 "../../../../../Block223StateMachine.ump"
          doDeleteCurrentGame();
          setGameState(GameState.Done);
          wasEventProcessed = true;
          break;
        }
        if (isBlockHit()&&!(isLastBlockHit()))
        {
        // line 61 "../../../../../Block223StateMachine.ump"
          doDeleteBlock();
      	doBlockHit();
          setGameState(GameState.Play);
          wasEventProcessed = true;
          break;
        }
        if (!(isBlockHit()&&isWallHit()&&isPaddleHit()&&isOutOfBounds()))
        {
          setGameState(GameState.Play);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setGameState(GameState aGameState)
  {
    gameState = aGameState;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetOne */
  public BallOccurence getBallOccurence()
  {
    return ballOccurence;
  }
  /* Code from template association_GetOne */
  public PaddleOccurence getPaddleOccurence()
  {
    return paddleOccurence;
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
      existingGame.removeGameOccurence(this);
    }
    game.addGameOccurence(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBlockOccurences()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BlockOccurence addBlockOccurence(int aGridHorizontalPosition, int aGridVerticalPosition, Block aBlock)
  {
    return new BlockOccurence(aGridHorizontalPosition, aGridVerticalPosition, aBlock, this);
  }

  public boolean addBlockOccurence(BlockOccurence aBlockOccurence)
  {
    boolean wasAdded = false;
    if (blockOccurences.contains(aBlockOccurence)) { return false; }
    GameOccurence existingGameOccurence = aBlockOccurence.getGameOccurence();
    boolean isNewGameOccurence = existingGameOccurence != null && !this.equals(existingGameOccurence);
    if (isNewGameOccurence)
    {
      aBlockOccurence.setGameOccurence(this);
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
    //Unable to remove aBlockOccurence, as it must always have a gameOccurence
    if (!this.equals(aBlockOccurence.getGameOccurence()))
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
      existingBlock223.removeGameOccurence(this);
    }
    block223.addGameOccurence(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeGameOccurence(this);
    }
    BallOccurence existingBallOccurence = ballOccurence;
    ballOccurence = null;
    if (existingBallOccurence != null)
    {
      existingBallOccurence.delete();
    }
    PaddleOccurence existingPaddleOccurence = paddleOccurence;
    paddleOccurence = null;
    if (existingPaddleOccurence != null)
    {
      existingPaddleOccurence.delete();
    }
    while (blockOccurences.size() > 0)
    {
      BlockOccurence aBlockOccurence = blockOccurences.get(blockOccurences.size() - 1);
      aBlockOccurence.delete();
      blockOccurences.remove(aBlockOccurence);
    }
    
    Block223 placeholderBlock223 = block223;
    this.block223 = null;
    if(placeholderBlock223 != null)
    {
      placeholderBlock223.removeGameOccurence(this);
    }
  }

  // line 73 "../../../../../Block223StateMachine.ump"
   private boolean isBlockHit(){
    
  }

  // line 74 "../../../../../Block223StateMachine.ump"
   private boolean isLastBlockHit(){
    
  }

  // line 75 "../../../../../Block223StateMachine.ump"
   private boolean isLastLevel(){
    
  }

  // line 76 "../../../../../Block223StateMachine.ump"
   private boolean isPaddleHit(){
    
  }

  // line 77 "../../../../../Block223StateMachine.ump"
   private boolean isWallHit(){
    
  }

  // line 78 "../../../../../Block223StateMachine.ump"
   private boolean isOutOfBounds(){
    
  }

  // line 79 "../../../../../Block223StateMachine.ump"
   private boolean hasEnoughBlockAssignments(){
    
  }

  // line 80 "../../../../../Block223StateMachine.ump"
   private boolean hasRemainingLives(){
    
  }

  // line 81 "../../../../../Block223StateMachine.ump"
   private void doDeleteBlock(){
    
  }


  /**
   * don't forget to increment score when block is deleted //don't forget to update score
   */
  // line 82 "../../../../../Block223StateMachine.ump"
   private void doDeleteCurrentGame(){
    
  }

  // line 83 "../../../../../Block223StateMachine.ump"
   private void doCreateScore(){
    
  }

  // line 84 "../../../../../Block223StateMachine.ump"
   private void doGetCurrentLevelBlocks(){
    
  }

  // line 85 "../../../../../Block223StateMachine.ump"
   private void doPlaceRandomBlocks(){
    
  }

  // line 86 "../../../../../Block223StateMachine.ump"
   private void doIncrementLevel(){
    
  }

  // line 87 "../../../../../Block223StateMachine.ump"
   private void doDecrementLifeCount(){
    
  }

  // line 88 "../../../../../Block223StateMachine.ump"
   private void doResetBallPosition(){
    
  }

  // line 89 "../../../../../Block223StateMachine.ump"
   private void doWallHit(){
    
  }

  // line 90 "../../../../../Block223StateMachine.ump"
   private void doBlockHit(){
    
  }

  // line 91 "../../../../../Block223StateMachine.ump"
   private void doPaddleHit(){
    
  }

  // line 92 "../../../../../Block223StateMachine.ump"
   private Double getNextX(){
    
  }

  // line 93 "../../../../../Block223StateMachine.ump"
   private Double getNextY(){
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "gameOccurenceID" + ":" + getGameOccurenceID()+ "," +
            "currentLevel" + ":" + getCurrentLevel()+ "," +
            "nbOfLives" + ":" + getNbOfLives()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "ballOccurence = "+(getBallOccurence()!=null?Integer.toHexString(System.identityHashCode(getBallOccurence())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "paddleOccurence = "+(getPaddleOccurence()!=null?Integer.toHexString(System.identityHashCode(getPaddleOccurence())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "block223 = "+(getBlock223()!=null?Integer.toHexString(System.identityHashCode(getBlock223())):"null");
  }
}