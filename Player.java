import java.util.Scanner;
/**
* Represents a player and all the actions that one would do in a game of battleship
*
* @author (Tanner Zachem)
* @version (1/30/19)
*/
public class Player
{
	private static int[] shipLengths = {1, 2, 2, 3, 4};
	private char[][] printBoard;
	private char[][] compTestBoard;
	private char[][] compNoShow;
	private static int numRows = 10;
	private static int numCols = 10;
	private Ship[] playerShips;
	private Ship[] compShips;

	/**
 	* The default no-args constructor for the player class.
 	*/
	public Player()
	{

		printBoard = new char[numRows][numCols];
		compTestBoard = new char[numRows][numCols];
		compNoShow = new char[numRows][numCols];

		for(int r = 0; r < printBoard.length; r++)
		{
			for(int c = 0; c < printBoard[r].length; c++)
			{
				//Initialize board here
				printBoard[r][c] = '~';
			}
		}

		for(int r = 0; r < compTestBoard.length; r++)
		{
			for(int c = 0; c < compTestBoard[r].length; c++)
			{
				//Initialize board here
				compTestBoard[r][c] = '~';
			}
		}

		playerShips = new Ship[5];
		compShips = new Ship[5];
		for (int i = 0; i < 5; i++)
		{
			Ship setShip = new Ship(shipLengths[i]);
			playerShips[i] = setShip;
		}

		for (int j = 0; j < 5; j++)
		{
			Ship compSetShip = new Ship(shipLengths[j]);
			compShips[j] = compSetShip;
		}

	}


	/**
	* Prompts the user to choose the coordinate for the start of the ship.
	*/
	public void addPlayerShips()
	{
		int row = 0;
		int col = 0;
		int dir = -1;

		for (int i = 0; i < 5; i++)
		{
			Scanner input = new Scanner(System.in);
			System.out.println("\nShip #" + (i+1) + ": Length:" + playerShips[i].getLength());
			if(i == 0)
			printTheBoard();
			System.out.print("Enter row (0-9) ");
			row  = input.nextInt();


			while(row < 0 || row > 9) //Do not allow invalid inputs
			{
				System.out.println("Row Must Be Between 0 and 9, Try Again!");
				row  = input.nextInt();
			}

			System.out.print("Enter column (0-9) ");
			col = input.nextInt();

			while(col < 0 || col > 9)
			{
				System.out.println("Column Must Be Between 0 and 9, Try Again!");
				col  = input.nextInt();
			}

			System.out.print("Choose direction (0 = VERT, 1 = HORZ) ");
			dir = input.nextInt();

			while(dir != 1 && dir != 0)
			{
				System.out.println("Direction Must Be 0 (for Vertical) or 1 (for Hortizontal), Try Again!!");
				dir = input.nextInt();
			}

			while(humanError(row, col, dir, i)) //Double check for invalid input
			{
				System.out.println("Your Desired Ship Placement Is Flawed, Please Retry");
				System.out.print("Enter row (0-9) ");
				row  = input.nextInt();
				System.out.print("Enter column (0-9) ");
				col = input.nextInt();
				System.out.print("Choose direction (0 = VERT, 1 = HORZ) ");
				dir = input.nextInt();

			}

			playerShips[i].setLoc(row, col);
			playerShips[i].setDir(dir);
			printBoard[row][col] = 'S';
			setBoardShips(row,col,'S',playerShips[i].getLength(),dir);
			printTheBoard();

		}


	}


	 /**
	 * The computer randomly places the start of the ship on the board
	 */
	public void addCompShips()
	{
		int[] cVecArr = new int[10];
		int[] rVecArr = new int[10];
		int length = 0;
		int row = 0;
		int col = 0;
		int dir = -1;
		for(int i = 0; i < 5; i++)
		{
			row = randomLoc();
			col = randomLoc();
			dir = (int)(Math.random()*2);
			for(int j = 0; j < 5; j++)
			{
				if(row != rVecArr[i] && col != cVecArr[i])
				{
					cVecArr[i] = col;
					rVecArr[i] = row;
				}
			}

			while(compError(row,col,dir,i))
			{
				row = randomLoc();
				col = randomLoc();
				dir = (int)(Math.random()*2);
			}
				compShips[i].setLoc(row, col);
				compShips[i].setDir(dir);
				compTestBoard[row][col] = 'S';
				setCompShips(row,col,'S',compShips[i].getLength(),dir);

			}
			printTestComp();
		}


	 /**
	 * Places the player's full-sized ships on the playing board
	 *
	 */
	public void setPlayerBoard()
	{   int length = 0;
		for (int i = 0; i < 5; i++)
		{
			int curRVec = playerShips[i].getRVec();
			int curCVec = playerShips[i].getCVec();
			length = shipLengths[i];
			if(playerShips[i].getDir()==1)
			{
				for(int j = 0; j < shipLengths[i]; j++)
				{
					printBoard[curRVec][curCVec+j] = 'S';
				}
				if(playerShips[i].getDir()==0)
				{
					for(int k = 0; k < shipLengths[i]; k++)
					{
						printBoard[curRVec+k][curCVec] = 'S';
					}

				}


			}
		}
		printTheBoard();
	}

	/**
    * Places the computer's full-sized ships on the playing board
    *
    */
	public void setCompBoard()
	{
		int length = 0;
		for (int i = 0; i < 5; i++)
		{
			int curRVec = compShips[i].getRVec();
			int curCVec = compShips[i].getCVec();
			length = shipLengths[i];
			if(compShips[i].getDir()==0)
			{
				for(int j = 0; j < shipLengths[i]; j++)
				{
						compTestBoard[curRVec][curCVec+j] = 'S';

						for(int k = 0; k < shipLengths[i]; k++)
						{
								compTestBoard[curRVec+k][curCVec] = 'S';
						}
					}
				}
				printTestComp();
			}
		}


	/**
    * Returns a random number bewtween 0 and 9 to act as a coordinate value
    * @return random, a random number bewtween 0 and 9 to act as a coordinate value .
    */
	public int randomLoc()
	{
		int random = (int)(Math.random() * 10);
		return random;
	}



	/**
    * Updates the status of the board to contain the ships
    * @param r the row to set status on
    * @param c the coulmn to set status on
	* @param stat the status of the new coordinates
	* @param l the length of the ship
	* @param d the direction of the ship
    */
	public void setBoardShips(int r, int c, char stat, int l, int d)
	{
		printBoard[r][c] = stat;
		if(d == 0)
		{
			for(int i = 0; i < l; i++)
			{
				printBoard[r+i][c] = 'S';
			}
		}

		if(d == 1 )
		{
			for(int j = 0; j < l; j++)
			{
				printBoard[r][c+j] = 'S';
			}
		}
	}


	/**
    * Prints the player board to the terminal window
    */
	public void printTheBoard()
	{
		System.out.println("\nCurrent Board!\n");
		for(int x = 0; x < 10; x++)
		{
			if(x == 0)
				System.out.print("   ");
			System.out.print(x + "  ");
		}
		System.out.println();
		for(int i = 0; i < printBoard.length; i++)
		{
			for(int j = 0; j < printBoard[i].length; j++)
			{
				if(j == 0)
				System.out.print(i + "  ");
				System.out.print(printBoard[i][j]+ "  ");

			}
			System.out.println();
		}
	}




	/**
	 * Updates the status of the board to contain the ships
	 * @param r the row to set status on
	 * @param c the coulmn to set status on
	 * @param stat the status of the new coordinates
	 * @param l the length of the ship
	 * @param d the direction of the ship
	 */
	public void setCompShips(int r, int c, char stat, int l, int d)
	{
		compTestBoard[r][c] = stat;
		if(d == 0)
		{
			for(int i = 0; i < l; i++)
			{
				compTestBoard[r+i][c] = stat;
			}
		}

		if(d == 1 )
		{
			for(int j = 0; j < l; j++)
			{
				compTestBoard[r][c+j] = stat;
			}
		}
	}


	/**
    * Prints the computer board to the terminal window, omitting ship locations
    */
	public void printTestComp()
	{
		for(int i=0; i<compTestBoard.length; i++)
  			for(int j=0; j<compTestBoard[i].length; j++)
    			compNoShow[i][j]=compTestBoard[i][j];

		System.out.println("\nComputer Board\n");
		for(int x = 0; x < 10; x++)
		{
			if(x == 0)
				System.out.print("   ");
			System.out.print(x + "  ");
		}
		System.out.println();
		for(int i = 0; i < compNoShow.length; i++)
		{
			for(int j = 0; j < compNoShow[i].length; j++)
			{
				if(j == 0)
				System.out.print(i + "  ");

				if(compNoShow[i][j] == 'S')
				{
					compNoShow[i][j] = '~';
				}

				System.out.print(compNoShow[i][j]+ "  ");

			}
			System.out.println();
		}
	}



	/**
    * Determines wheather the player has a ship on a given coordinate
    * @param row, the Y component of the coordinate
    * @param col, the X component of the coordinate
    * @return the truth value of if that coordinate has a ship
    */
	public boolean humanHasShip(int row, int col)
	{
		if(printBoard[row][col] == 'S')
		return true;

		else
		return false;
	}



	/**
	 * Determines wheather the computer has a ship on a given coordinate
	 * @param row, the Y component of the coordinate
	 * @param col, the X component of the coordinate
	 * @return the truth value of if that coordinate has a ship
	 */
	public boolean playerHasShip(int row, int col)
	{
		if(compTestBoard[row][col] == 'S')
		{
			return true;
		}

		else
		return false;
	}



	/**
    * Changes the computer board to reflect the outcome of the player's guess
    * @param r, the Y component of the coordinate
    * @param c,  the X component of the coordinate
	* @param stat, the current status of the coordinate
    */
	public void setHumanMove(int r, int c, int stat)
	{
		if(stat == 1)
		{
			compTestBoard[r][c] = 'X';
		}

		else if(stat == 0)
		{
			compTestBoard[r][c] = 'O';
		}
	}


	/**
	 * Changes the player board to reflect the outcome of the computer's guess
	 * @param r, the Y component of the coordinate
	 * @param c, the X component of the coordinate
	 * @param stat, the current status of the coordinate
	 */
	public void setCompMove(int r, int c, int stat)
	{
		if(stat == 1)
		{
			printBoard[r][c] = 'X';
		}

		else if(stat == 0)
		{
			printBoard[r][c] = 'O';
		}
	}



	/**
    * Returns the status of the computer board at a given coordinate
    * @param r, the Y component of the coordinate
    * @param c, the X component of the coordinate
    * @return the status of the computer board at a given coordinate
    */
	public char checkCompBoard(int r, int c)
	{
		return compTestBoard[r][c];
	}



	/**
	 * Returns the status of the player board at a given coordinate
	 * @param r, the Y component of the coordinate
	 * @param c, the X component of the coordinate
	 * @return the status of the player board at a given coordinate
	 */
	public char checkHumanBoard(int r, int c)
	{
		return printBoard[r][c];
	}


	/**
    * Checks wheather the randomized computer ship placement is valid
    * @param row, the Y component of the coordinate
    * @param col, the X component of the coordinate
	* @param dir, the direction of the ship
	* @param n, the number of the ship
    * @return wheather the random computer choices are valid
    */
	private  boolean compError(int row, int col, int dir, int n)
	{
		int l = compShips[n].getLength();
		if (dir == 0)
		{
			int bad = l + row;

			if (bad > 10)
			{
				return true;
			}
		}

		if (dir == 1)
		{
			int bad = l + col;
			if (bad > 10)
			{
				return true;
			}
		}

		if (dir == 0)
		{
			for (int i = 0; i < l; i++)
			{
				if(compTestBoard[row+i][col] == 'S')
				{
					return true;
				}
			}
		}
		else if (dir == 1)
		{
			for (int i = 0; i < l; i++)
			{
				if(compTestBoard[row][col+i] == 'S')
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks wheather the human ship placement is valid
	 * @param row, the Y component of the coordinate
	 * @param col, the X component of the coordinate
	 * @param dir, the direction of the ship
	 * @param n, the number of the ship
	 * @return wheather the choices are valid
	 */
	private  boolean humanError(int row, int col, int dir, int n)
	{
		int l = playerShips[n].getLength();
		if (dir == 0 && row != 0)
		{
			int bad = l + row;

			if (bad > 10)
			{
				return true;
			}
		}

		if (dir == 1 && col != 0)
		{
			int bad = l + col;
			if (bad > 10)
			{
				return true;
			}
		}

		if (dir == 0)
		{
			for (int i = 0; i < l; i++)
			{
				if(printBoard[row+i][col] == 'S')
				{
					return true;
				}
			}
		}
		 if (dir == 1)
		{
			for (int i = 0; i < l; i++)
			{
				if(printBoard[row][col+i] == 'S')
				{
					return true;
				}
			}
		}
		return false;
	}
}
