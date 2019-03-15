/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/



// line 1 "Block223StateMachine.ump"
public class GameOccurence
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GameOccurence State Machines
  public enum GameState { Idle, Paused, Play, Done }
  private GameState gameState;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GameOccurence()
  {
    setGameState(GameState.Idle);
  }

  //------------------------
  // INTERFACE
  //------------------------

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
        // line 6 "Block223StateMachine.ump"
          createScore();
      	getCurrentLevelBlocks();
      	save();
          setGameState(GameState.Paused);
          wasEventProcessed = true;
          break;
        }
        if (!(hasEnoughBlockAssignments()))
        {
        // line 11 "Block223StateMachine.ump"
          createScore();
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
        // line 24 "Block223StateMachine.ump"
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
        // line 28 "Block223StateMachine.ump"
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
        if (isWallHit()||isPaddleHit())
        {
        // line 38 "Block223StateMachine.ump"
          changeBallDirection();
          setGameState(GameState.Play);
          wasEventProcessed = true;
          break;
        }
        if (isOutOfBounds()&&hasRemainingLives())
        {
        // line 42 "Block223StateMachine.ump"
          decrementLifeCount();
      	resetBallPosition();
      	save();
          setGameState(GameState.Paused);
          wasEventProcessed = true;
          break;
        }
        if (isBlockHit()&&isLastBlockHit()&&isLastLevel())
        {
        // line 48 "Block223StateMachine.ump"
          deleteBlock();
      	deleteCurrentGame();
      	save();
          setGameState(GameState.Done);
          wasEventProcessed = true;
          break;
        }
        if (isOutOfBounds()&&!(hasRemainingLives()))
        {
        // line 54 "Block223StateMachine.ump"
          createScore();
      	deleteCurrentGame();
      	save();
          setGameState(GameState.Done);
          wasEventProcessed = true;
          break;
        }
        if (isBlockHit()&&!(isLastBlockHit()))
        {
        // line 60 "Block223StateMachine.ump"
          deleteBlock();
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

  public void delete()
  {}

  // line 72 "Block223StateMachine.ump"
   private boolean isBlockHit(){
    
  }

  // line 73 "Block223StateMachine.ump"
   private boolean isLastBlockHit(){
    
  }

  // line 74 "Block223StateMachine.ump"
   private boolean isLastLevel(){
    
  }

  // line 75 "Block223StateMachine.ump"
   private boolean isPaddleHit(){
    
  }

  // line 76 "Block223StateMachine.ump"
   private boolean isWallHit(){
    
  }

  // line 77 "Block223StateMachine.ump"
   private boolean isOutOfBounds(){
    
  }

  // line 78 "Block223StateMachine.ump"
   private boolean hasEnoughBlockAssignments(){
    
  }

  // line 79 "Block223StateMachine.ump"
   private boolean hasRemainingLives(){
    
  }

  // line 80 "Block223StateMachine.ump"
   private void deleteBlock(){
    
  }


  /**
   * don't forget to increment score when block is deleted
   */
  // line 81 "Block223StateMachine.ump"
   private void deleteCurrentGame(){
    
  }

  // line 82 "Block223StateMachine.ump"
   private void createScore(){
    
  }

  // line 83 "Block223StateMachine.ump"
   private void save(){
    
  }

  // line 84 "Block223StateMachine.ump"
   private void getCurrentLevelBlocks(){
    
  }

  // line 85 "Block223StateMachine.ump"
   private void placeRandomBlocks(){
    
  }

  // line 86 "Block223StateMachine.ump"
   private void incrementLevel(){
    
  }

  // line 87 "Block223StateMachine.ump"
   private void decrementLifeCount(){
    
  }

  // line 88 "Block223StateMachine.ump"
   private void resetBallPosition(){
    
  }

  // line 89 "Block223StateMachine.ump"
   private void changeBallDirection(){
    
  }

}