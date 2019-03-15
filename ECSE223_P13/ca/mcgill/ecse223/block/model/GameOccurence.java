/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.util.*;

// line 1 "../../../../../Block223Play.ump"
// line 1 "../../../../../Block223StateMachine.ump"
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

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GameOccurence(int aCurrentLevel, Game aGame, BallOccurence aBallOccurence, PaddleOccurence aPaddleOccurence)
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
    setGameState(GameState.Idle);
  }

  public GameOccurence(int aCurrentLevel, Game aGame, Ball aBallForBallOccurence, int aCurrentPaddleLengthForPaddleOccurence, Paddle aPaddleForPaddleOccurence)
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
          getCurrentLevelBlocks();
      	save();
          setGameState(GameState.Paused);
          wasEventProcessed = true;
          break;
        }
        if (!(hasEnoughBlockAssignments()))
        {
        // line 10 "../../../../../Block223StateMachine.ump"
          getCurrentLevelBlocks();
      	placeRandomBlocks();
      	save();
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
        // line 22 "../../../../../Block223StateMachine.ump"
        save();
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
          deleteBlock();
        incrementLevel();
        getCurrentLevelBlocks();
        if(!hasEnoughBlockAssignments()){
        placeRandomBlocks();
        }
        save();
          setGameState(GameState.Paused);
          wasEventProcessed = true;
          break;
        }
        if (isOutOfBounds()&&hasRemainingLives())
        {
        // line 36 "../../../../../Block223StateMachine.ump"
          decrementLifeCount();
      	resetBallPosition();
      	save();
          setGameState(GameState.Paused);
          wasEventProcessed = true;
          break;
        }
        if (isLastBlockHit()&&isLastLevel())
        {
        // line 42 "../../../../../Block223StateMachine.ump"
          createScore();
      	deleteCurrentGame();
      	save();
          setGameState(GameState.Done);
          wasEventProcessed = true;
          break;
        }
        if (isOutOfBounds()&&!(hasRemainingLives()))
        {
        // line 48 "../../../../../Block223StateMachine.ump"
           
      	createScore();
      	deleteCurrentGame();
      	save();
          setGameState(GameState.Done);
          wasEventProcessed = true;
          break;
        }
        if (isBlockHit()&&!(isLastBlockHit()))
        {
        // line 54 "../../../../../Block223StateMachine.ump"
          deleteBlock();
      	changeBallDirection();
          setGameState(GameState.Play);
          wasEventProcessed = true;
          break;
        }
        if (isWallHit()||isPaddleHit())
        {
        // line 59 "../../../../../Block223StateMachine.ump"
          changeBallDirection();
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
    
  }

  // line 70 "../../../../../Block223StateMachine.ump"
   private boolean isBlockHit(){
    
  }

  // line 71 "../../../../../Block223StateMachine.ump"
   private boolean isLastBlockHit(){
    
  }

  // line 72 "../../../../../Block223StateMachine.ump"
   private boolean isLastLevel(){
    
  }

  // line 73 "../../../../../Block223StateMachine.ump"
   private boolean isPaddleHit(){
    
  }

  // line 74 "../../../../../Block223StateMachine.ump"
   private boolean isWallHit(){
    
  }

  // line 75 "../../../../../Block223StateMachine.ump"
   private boolean isOutOfBounds(){
    
  }

  // line 76 "../../../../../Block223StateMachine.ump"
   private boolean hasEnoughBlockAssignments(){
    
  }

  // line 77 "../../../../../Block223StateMachine.ump"
   private boolean hasRemainingLives(){
    
  }

  // line 78 "../../../../../Block223StateMachine.ump"
   private void deleteBlock(){
    
  }

  // line 79 "../../../../../Block223StateMachine.ump"
   private void deleteCurrentGame(){
    
  }

  // line 80 "../../../../../Block223StateMachine.ump"
   private void createScore(){
    
  }

  // line 81 "../../../../../Block223StateMachine.ump"
   private void save(){
    
  }

  // line 82 "../../../../../Block223StateMachine.ump"
   private void getCurrentLevelBlocks(){
    
  }

  // line 83 "../../../../../Block223StateMachine.ump"
   private void placeRandomBlocks(){
    
  }

  // line 84 "../../../../../Block223StateMachine.ump"
   private void incrementLevel(){
    
  }

  // line 85 "../../../../../Block223StateMachine.ump"
   private void decrementLifeCount(){
    
  }

  // line 86 "../../../../../Block223StateMachine.ump"
   private void resetBallPosition(){
    
  }

  // line 87 "../../../../../Block223StateMachine.ump"
   private void changeBallDirection(){
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "gameOccurenceID" + ":" + getGameOccurenceID()+ "," +
            "currentLevel" + ":" + getCurrentLevel()+ "," +
            "nbOfLives" + ":" + getNbOfLives()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "ballOccurence = "+(getBallOccurence()!=null?Integer.toHexString(System.identityHashCode(getBallOccurence())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "paddleOccurence = "+(getPaddleOccurence()!=null?Integer.toHexString(System.identityHashCode(getPaddleOccurence())):"null");
  }
}