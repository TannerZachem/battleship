/**
* Represents a ship to place on the board
*
* @author (Tanner Zachem)
* @version (1/29/19)
*/
public class Ship
{
    private int rVec;
    private int cVec;
    private int length;
    private int dir;
    private static int notSet = -1;
    private static int horz = 0;
    private static int vert = 1;


    /**
     * The default constructor for the Ship class
     * @param l, the length of the current ship
     */
	public Ship(int l)
    {
        length = l;
        rVec = -1;
        cVec = -1;
        dir = notSet;
    }

	/**
    * Places the front of the ship at a given coordinate
    * @param r, the Y component of the coordinate
    * @param c, the X component of the coordinate
    */
    public void setLoc(int r, int c)
    {
        rVec = r;
        cVec = c;
    }


	/**
    * Sets the direction of the ship
    * @param d, the directon of the ship
    */
    public void setDir(int d)
    {
        if(dir != notSet && dir != horz && dir != vert)
        {
            System.out.println("Direction must be 0, or 1!");
        }
        else
        {
            dir = d;
        }
    }

	/**
    * Returns the Y component of the location
    * @return the Y component of the location
    */
	public int getRVec()
    {
        return rVec;
    }


    /**
     * Returns the X component of the location
     * @return the X component of the location
     */
    public int getCVec()
    {
        return cVec;
    }

	/**
    * Returns the length of a given ship
    * @return the length of a given ship
    */
    public int getLength()
    {
        return length;
    }

    /**
     * Returns the direction of a given ship
     * @return the direction of a given ship
     */
    public int getDir()
    {
        return dir;
    }

}
