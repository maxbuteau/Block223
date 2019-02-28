/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.controller;

// line 37 "../../../../../Block223TransferObjects.ump"
public class TOConstant
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOConstant Attributes
  private int minNrLevels;
  private int maxNrLevels;
  private int playAreaSide;
  private int wallPadding;
  private int columnsPadding;
  private int rowPadding;
  private int minColor;
  private int maxColor;
  private int minPoints;
  private int maxPoints;
  private int size;
  private int ballDiameter;
  private int paddleWidth;
  private int verticalDistance;
  private int maxHorizontalBlocks;
  private int maxVerticalBlocks;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOConstant(int aMinNrLevels, int aMaxNrLevels, int aPlayAreaSide, int aWallPadding, int aColumnsPadding, int aRowPadding, int aMinColor, int aMaxColor, int aMinPoints, int aMaxPoints, int aSize, int aBallDiameter, int aPaddleWidth, int aVerticalDistance, int aMaxHorizontalBlocks, int aMaxVerticalBlocks)
  {
    minNrLevels = aMinNrLevels;
    maxNrLevels = aMaxNrLevels;
    playAreaSide = aPlayAreaSide;
    wallPadding = aWallPadding;
    columnsPadding = aColumnsPadding;
    rowPadding = aRowPadding;
    minColor = aMinColor;
    maxColor = aMaxColor;
    minPoints = aMinPoints;
    maxPoints = aMaxPoints;
    size = aSize;
    ballDiameter = aBallDiameter;
    paddleWidth = aPaddleWidth;
    verticalDistance = aVerticalDistance;
    maxHorizontalBlocks = aMaxHorizontalBlocks;
    maxVerticalBlocks = aMaxVerticalBlocks;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMinNrLevels(int aMinNrLevels)
  {
    boolean wasSet = false;
    minNrLevels = aMinNrLevels;
    wasSet = true;
    return wasSet;
  }

  public boolean setMaxNrLevels(int aMaxNrLevels)
  {
    boolean wasSet = false;
    maxNrLevels = aMaxNrLevels;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlayAreaSide(int aPlayAreaSide)
  {
    boolean wasSet = false;
    playAreaSide = aPlayAreaSide;
    wasSet = true;
    return wasSet;
  }

  public boolean setWallPadding(int aWallPadding)
  {
    boolean wasSet = false;
    wallPadding = aWallPadding;
    wasSet = true;
    return wasSet;
  }

  public boolean setColumnsPadding(int aColumnsPadding)
  {
    boolean wasSet = false;
    columnsPadding = aColumnsPadding;
    wasSet = true;
    return wasSet;
  }

  public boolean setRowPadding(int aRowPadding)
  {
    boolean wasSet = false;
    rowPadding = aRowPadding;
    wasSet = true;
    return wasSet;
  }

  public boolean setMinColor(int aMinColor)
  {
    boolean wasSet = false;
    minColor = aMinColor;
    wasSet = true;
    return wasSet;
  }

  public boolean setMaxColor(int aMaxColor)
  {
    boolean wasSet = false;
    maxColor = aMaxColor;
    wasSet = true;
    return wasSet;
  }

  public boolean setMinPoints(int aMinPoints)
  {
    boolean wasSet = false;
    minPoints = aMinPoints;
    wasSet = true;
    return wasSet;
  }

  public boolean setMaxPoints(int aMaxPoints)
  {
    boolean wasSet = false;
    maxPoints = aMaxPoints;
    wasSet = true;
    return wasSet;
  }

  public boolean setSize(int aSize)
  {
    boolean wasSet = false;
    size = aSize;
    wasSet = true;
    return wasSet;
  }

  public boolean setBallDiameter(int aBallDiameter)
  {
    boolean wasSet = false;
    ballDiameter = aBallDiameter;
    wasSet = true;
    return wasSet;
  }

  public boolean setPaddleWidth(int aPaddleWidth)
  {
    boolean wasSet = false;
    paddleWidth = aPaddleWidth;
    wasSet = true;
    return wasSet;
  }

  public boolean setVerticalDistance(int aVerticalDistance)
  {
    boolean wasSet = false;
    verticalDistance = aVerticalDistance;
    wasSet = true;
    return wasSet;
  }

  public boolean setMaxHorizontalBlocks(int aMaxHorizontalBlocks)
  {
    boolean wasSet = false;
    maxHorizontalBlocks = aMaxHorizontalBlocks;
    wasSet = true;
    return wasSet;
  }

  public boolean setMaxVerticalBlocks(int aMaxVerticalBlocks)
  {
    boolean wasSet = false;
    maxVerticalBlocks = aMaxVerticalBlocks;
    wasSet = true;
    return wasSet;
  }

  public int getMinNrLevels()
  {
    return minNrLevels;
  }

  public int getMaxNrLevels()
  {
    return maxNrLevels;
  }

  public int getPlayAreaSide()
  {
    return playAreaSide;
  }

  public int getWallPadding()
  {
    return wallPadding;
  }

  public int getColumnsPadding()
  {
    return columnsPadding;
  }

  public int getRowPadding()
  {
    return rowPadding;
  }

  public int getMinColor()
  {
    return minColor;
  }

  public int getMaxColor()
  {
    return maxColor;
  }

  public int getMinPoints()
  {
    return minPoints;
  }

  public int getMaxPoints()
  {
    return maxPoints;
  }

  public int getSize()
  {
    return size;
  }

  public int getBallDiameter()
  {
    return ballDiameter;
  }

  public int getPaddleWidth()
  {
    return paddleWidth;
  }

  public int getVerticalDistance()
  {
    return verticalDistance;
  }

  public int getMaxHorizontalBlocks()
  {
    return maxHorizontalBlocks;
  }

  public int getMaxVerticalBlocks()
  {
    return maxVerticalBlocks;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "minNrLevels" + ":" + getMinNrLevels()+ "," +
            "maxNrLevels" + ":" + getMaxNrLevels()+ "," +
            "playAreaSide" + ":" + getPlayAreaSide()+ "," +
            "wallPadding" + ":" + getWallPadding()+ "," +
            "columnsPadding" + ":" + getColumnsPadding()+ "," +
            "rowPadding" + ":" + getRowPadding()+ "," +
            "minColor" + ":" + getMinColor()+ "," +
            "maxColor" + ":" + getMaxColor()+ "," +
            "minPoints" + ":" + getMinPoints()+ "," +
            "maxPoints" + ":" + getMaxPoints()+ "," +
            "size" + ":" + getSize()+ "," +
            "ballDiameter" + ":" + getBallDiameter()+ "," +
            "paddleWidth" + ":" + getPaddleWidth()+ "," +
            "verticalDistance" + ":" + getVerticalDistance()+ "," +
            "maxHorizontalBlocks" + ":" + getMaxHorizontalBlocks()+ "," +
            "maxVerticalBlocks" + ":" + getMaxVerticalBlocks()+ "]";
  }
}