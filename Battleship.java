import java.util.Scanner;
/**
 * Represents the board game battleship, and the gameplay style that it contained
 *
 * @author (Tanner Zachem)
 * @version (1/30/19)
 */
public class Battleship
{
    private static Scanner input = new Scanner(System.in);
	private static boolean lastHit = false;
	private static int playerPoints;
	private static int compPoints;

	/**
    * The begining/creation of a battleship game
    * @param args, string command-line arguments
    */
    public static void main(String[] args)
    {
		playerPoints = 0;
		compPoints = 0;

		System.out.println("\nGet Ready!");
        Player human = new Player(); //begining to set user up
        human.addPlayerShips(); //transfer to player class, place ships
        human.setPlayerBoard(); //set human board
        System.out.println("\nPress Enter To Continue"); //Move to Next Step
        input.nextLine();
        Player computer = new Player(); //create computer
        computer.addCompShips(); //randomize computer ships
		while(playerPoints < 12 && compPoints < 12)
		{
			makePlay(human, computer);
			compGuess(human, computer);
		}

		if(compPoints == 12)
		{
			System.out.println("Computer Wins, You Should Try Again!");
		}

		else
		{
			System.out.println("Humans Are Still Superior, You Win!");
		}
    }



	/**
    * Allows the user to guess on the computers board
    * @param Player human the guessing party
    * @param Player computer the board on which human is guessing
    */
    public static void makePlay(Player human, Player computer)
    {

		computer.printTestComp();
        System.out.println("\nMake your Guess!");
        System.out.println("\nSelect Row (0-9)");
           int row  = input.nextInt();
		   while(row < 0 || row > 9)
		   {
			   System.out.println("Row Must Be Between 0 and 9, Try Again!");
			   row  = input.nextInt();
		   }
            int lastRow = row;
        System.out.println("\nSelect Column (0-9)");
            int col  = input.nextInt();
			while(col < 0 || col > 9)
			{
				System.out.println("Column Must Be Between 0 and 9, Try Again!");
				col  = input.nextInt();
			}
            int lastCol = col;


        if (computer.checkCompBoard(lastRow, lastCol) == 'S')
           {
               computer.setHumanMove(lastRow, lastCol, 1);
			   computer.printTestComp();
			   playerPoints++;
			   System.out.println("WOO!, You Hit! You Have " + playerPoints + " Points!" );

           }

        if(computer.checkCompBoard(lastRow, lastCol) == '~')
        {
		 	computer.setHumanMove(lastRow, lastCol, 0);
			computer.printTestComp();
            System.out.println("You Missed, Try Again!");
        }

    }

	/**
	 * Allows the computer to guess on the humans board
	 * @param Player computer the guessing party
	 * @param Player human the board on which computer is guessing
	 */
	public static void compGuess(Player human, Player computer)
    {
        System.out.println("\nComputer Is Guessing!");
		int row = human.randomLoc();
		int lastRow = row;
		int col = human.randomLoc();
		int lastCol = col;
		char atLoc = human.checkHumanBoard(lastRow, lastCol);
		int storeRow = 0;
		int storeCol = 0;

		while(atLoc == 'X' || atLoc == 'O')
		{
			int c = 0;
			row = human.randomLoc();
			lastRow = row;
			col = human.randomLoc();
			lastCol = col;
			atLoc = human.checkHumanBoard(lastRow, lastCol);
			c++;

			if(c > 15)
			{
				row = human.randomLoc();
				col = human.randomLoc();
				break;
			}
		}

		if(lastHit == true)
		{
			System.out.println("Im Playing Smart");
			int dir = (int)(Math.random() * 4);
			if(dir == 0 && lastRow != 9)
			{
				storeRow = lastRow + 1;
			}
			else if(dir == 1 && lastCol != 9)
			{
				storeCol = lastCol + 1;
			}
			else if(dir == 2 && lastRow != 0)
			{
				storeRow = lastRow - 1;
			}
			else if(dir == 3 && lastCol != 0)
			{
				storeCol = lastCol - 1;
			}
			else
			{
				storeRow = lastRow;
				storeCol = lastCol;
			}

			if(human.checkHumanBoard(storeRow, storeCol) == 'S')
			{
				human.setCompMove(storeRow, storeCol, 1);
				human.printTheBoard();
				compPoints++;
				System.out.println("The Computer Hit At (" + storeRow + ", " + storeCol + "), It Has " + compPoints + " Points!");
				storeRow = lastRow;
				storeCol = lastCol;
				lastHit = true;
				return;

			}

			if(human.checkHumanBoard(storeRow, storeCol) == '~')
			{
   				human.setCompMove(storeRow, storeCol, 0);
   				human.printTheBoard();
   				System.out.println("The Computer Missed At (" + storeRow + ", " + storeCol + ")!" );
   				lastHit = false;
   				return;
   			}

		}

		if(human.checkHumanBoard(lastRow, lastCol) == 'S')
		{
			human.setCompMove(lastRow, lastCol, 1);
			human.printTheBoard();
			System.out.println("The Computer Hit At (" + row + ", " + col + ")!" );
			storeRow = lastRow;
			storeCol = lastCol;
			lastHit = true;
		}

		 if(human.checkHumanBoard(lastRow, lastCol) == '~' || human.checkHumanBoard(lastRow, lastCol) == 'O')
		{
			human.setCompMove(lastRow, lastCol, 0);
			human.printTheBoard();
			System.out.println("The Computer Missed At (" + row + ", " + col + ")!" );
			lastHit = false;
		}

    }

}
