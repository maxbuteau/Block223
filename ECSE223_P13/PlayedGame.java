/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/



// line 1 "Block223States.ump"
public class PlayedGame
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayedGame State Machines
  public enum PlayStatus { Ready, Moving, Paused, GameOver }
  private PlayStatus playStatus;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayedGame()
  {
    setPlayStatus(PlayStatus.Ready);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getPlayStatusFullName()
  {
    String answer = playStatus.toString();
    return answer;
  }

  public PlayStatus getPlayStatus()
  {
    return playStatus;
  }

  public boolean play()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Ready:
        setPlayStatus(PlayStatus.Moving);
        wasEventProcessed = true;
        break;
      case Paused:
        setPlayStatus(PlayStatus.Moving);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean pause()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Moving:
        setPlayStatus(PlayStatus.Paused);
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
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Moving:
        if (hitPaddle())
        {
        // line 12 "Block223States.ump"
          doHitPaddleOrWall();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        if (isOutOfBoundsAndLastLife())
        {
        // line 13 "Block223States.ump"
          doOutOfBounds();
          setPlayStatus(PlayStatus.GameOver);
          wasEventProcessed = true;
          break;
        }
        if (isOutOfBounds())
        {
        // line 14 "Block223States.ump"
          doOutOfBounds();
          setPlayStatus(PlayStatus.Paused);
          wasEventProcessed = true;
          break;
        }
        if (hitLastBlockAndLastLevel())
        {
        // line 15 "Block223States.ump"
          doHitBlock();
          setPlayStatus(PlayStatus.GameOver);
          wasEventProcessed = true;
          break;
        }
        if (hitLastBlock())
        {
        // line 16 "Block223States.ump"
          doHitBlockNextLevel();
          setPlayStatus(PlayStatus.Ready);
          wasEventProcessed = true;
          break;
        }
        if (hitBlock())
        {
        // line 17 "Block223States.ump"
          doHitBlock();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        if (hitWall())
        {
        // line 18 "Block223States.ump"
          doHitPaddleOrWall();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        // line 19 "Block223States.ump"
        doHitNothingAndNotOutOfBounds();
        setPlayStatus(PlayStatus.Moving);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setPlayStatus(PlayStatus aPlayStatus)
  {
    playStatus = aPlayStatus;

    // entry actions and do activities
    switch(playStatus)
    {
      case Ready:
        // line 7 "Block223States.ump"
        doSetup();
        break;
      case GameOver:
        // line 25 "Block223States.ump"
        doGameOver();
        break;
    }
  }

  public void delete()
  {}


  /**
   * Guards
   */
  // line 32 "Block223States.ump"
   private boolean hitPaddle(){
    BouncePoint bp = calculateBouncePointPaddle();
    this.setBounce(bp);
    return bp != null;
  }

  // line 38 "Block223States.ump"
   private BouncePoint calculateBouncePointPaddle(){
    // TODO Auto-generated method stub
	return null;
  }

  // line 43 "Block223States.ump"
   private void bounceBall(){
    // TODO Auto-generated method stub
  }

  // line 48 "Block223States.ump"
   private boolean isOutOfBoundsAndLastLife(){
    // TODO implement
    return false;
  }

  // line 53 "Block223States.ump"
   private boolean isOutOfBounds(){
    // TODO implement
    return false;
  }

  // line 58 "Block223States.ump"
   private boolean hitLastBlockAndLastLevel(){
    // TODO implement
    return false;
  }

  // line 63 "Block223States.ump"
   private boolean hitLastBlock(){
    // TODO implement
    return false;
  }

  // line 68 "Block223States.ump"
   private boolean hitBlock(){
    // TODO implement
    return false;
  }

  // line 73 "Block223States.ump"
   private boolean hitWall(){
    BouncePoint bp = calculateBouncePointWall();
    this.setBounce(bp);
    return bp != null;
  }

  // line 79 "Block223States.ump"
   private BouncePoint calculateBouncePointWall(){
    // TODO Auto-generated method stub
	return null;
  }


  /**
   * Actions
   */
  // line 86 "Block223States.ump"
   private void doSetup(){
    this.resetCurrentBallX();
	   this.resetCurrentBallY();
	   this.resetBallDirectionX();
	   this.resetBallDirectionY();	   
	   this.resetCurrentPaddleX();
	   Game game = this.getGame();
	   Level level = game.getLevel(this.getCurrentLevel() - 1);
	   List<BlockAssignment> assignments = level.getBlockAssignments();
	   
	   for(BlockAssignment assignment : assignments) {
		   PlayedBlockAssignment pblock = new PlayedBlockAssignment(
				   Game.WALL_PADDING + (Block.SIZE + Game.COLUMNS_PADDING) * (assignment.getGridHorizontalPosition() - 1),
				   Game.WALL_PADDING + (Block.SIZE + Game.ROW_PADDING) * (assignment.getGridVerticalPosition() - 1),
				   assignment.getBlock(),
				   this
				   );
	   }
	   
	   int numberOfBlocks = assignments.size();
	   int maxHor = (1+(Game.PLAY_AREA_SIDE-2*Game.WALL_PADDING-Block.SIZE)/(Block.SIZE+Game.COLUMNS_PADDING));
	   int maxVer = (1+(Game.PLAY_AREA_SIDE-Paddle.VERTICAL_DISTANCE-Game.WALL_PADDING-Paddle.PADDLE_WIDTH-Ball.BALL_DIAMETER-Block.SIZE)/(Block.SIZE+Game.ROW_PADDING));
	   int x;
	   int y;
	   
	   while(numberOfBlocks < game.getNrBlocksPerLevel()) {
		   Random rand = new Random();
		   x = rand.nextInt(maxHor);
		   ++x;
		   y = rand.nextInt(maxVer);
		   ++y;
		   
		   BlockAssignment foundAssignment = level.findBlockAssignment(x, y);
		   while(foundAssignment != null) {
			   if(y < maxVer) {
				   if(x < maxHor) x++;
				   if(x >= maxHor) {
					   x = 1;
					   y++;
				   }
			   }
			   
			   else if(y >= maxVer) {
				   x = 1;
				   y = 1;
			   }  
			   foundAssignment = level.findBlockAssignment(x, y);
		   }
		   
		   new PlayedBlockAssignment(
				   x,
				   y,
				   game.getRandomBlock(),
				   this
				   );
	   }
  }

  // line 145 "Block223States.ump"
   private void doHitPaddleOrWall(){
    this.bounceBall();
  }

  // line 149 "Block223States.ump"
   private void doOutOfBounds(){
    // TODO implement
  }

  // line 153 "Block223States.ump"
   private void doHitBlock(){
    int score = this.getScore();
    BouncePoint bounce = this.getBounce();
    PlayedBlockAssignment pblock = bounce.getHitBlock();
    Block block = pblock.getBlock();
    int bscore = block.getPoints();
    this.setScore(score + bscore);
    pblock.delete();
    this.bounceBall();
  }

  // line 164 "Block223States.ump"
   private void doHitBlockNextLevel(){
    // TODO implement
  }

  // line 168 "Block223States.ump"
   private void doHitNothingAndNotOutOfBounds(){
    // TODO implement
  }

  // line 172 "Block223States.ump"
   private void doGameOver(){
    // TODO implement
  }

}