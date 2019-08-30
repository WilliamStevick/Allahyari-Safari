
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class represents a Minesweeper game.
 *
 * @author WILLIAM STEVICK <WS02593@GEORGIASOUTHERN.EDU>
 */
public class Minesweeper {
	int rows, cols, mines, rounds, score;
	String[][] board, displayBoard;
	String command;
	int dRow, dColm;
	Scanner input;
	int mineRow, mineColm;
    /**
     * Constructs an object instance of the {@link Minesweeper} class using the
     * information provided in <code>seedFile</code>. Documentation about the format
     * of seed files can be found in the <code>project1.pdf</code> file.
     *
     * @param seedFile the seed file used to construct the game
     * @throws FileNotFoundException 
     */
    public Minesweeper(File file) throws FileNotFoundException {

    	// TODO implement
			Scanner seed = new Scanner(file);
			seed.nextLine();
			if (seed.hasNextInt()) {
				String row = seed.next();
				row.trim();
				rows = Integer.parseInt(row);
			}
			else
				printSeedError();
			if (seed.hasNextInt()) {
				String colm = seed.next();
				colm.trim();
				cols = Integer.parseInt(colm);
			}
			else
				printSeedError();
			if (rows > 10 || cols > 10) {
	   			System.out.println("Cannot create a mine field with that many rows and/or columns!");
	   			System.exit(0);
	   		}
			else
				board = new String[rows][cols];
			if (seed.hasNextInt()) {
				String mine = seed.next();
				mine.trim();
				mines = Integer.parseInt(mine);
			}
			else
				printSeedError();
			while (seed.hasNextLine()) {
				if (seed.hasNextInt()) {
					String mineRows = seed.next();
					mineRows.trim();
					mineRow = Integer.parseInt(mineRows);
				}
				else
					printSeedError();
				if (seed.hasNextInt()) {
					String mineCols = seed.next();
					mineCols.trim();
					mineColm = Integer.parseInt(mineCols);
				}
				else
					printSeedError();
				if (mineRow >= rows || mineColm >= cols)
					printSeedError();
				else
					board[mineRow][mineColm] = "M";
			}
			assignNumbers();
		   
}  // Minesweeper

    /**
     * Constructs an object instance of the {@link Minesweeper} class using the
     * <code>rows</code> and <code>cols</code> values as the game grid's number of
     * rows and columns respectively. Additionally, One quarter (rounded up) of the
     * squares in the grid will will be assigned mines, randomly.
     *
     * @param rows the number of rows in the game grid
     * @param cols the number of cols in the game grid
     */
    public Minesweeper(int rows, int cols) {

        // TODO implement
    		this.rows = rows;
    		this.cols = cols;
    		board = new String[rows][cols];
    		mines = (int) Math.ceil((rows * cols) * 0.2);
    		
    	// Check if number of rows/cols is playable
    		if (rows > 10 || cols > 10) {
    			System.out.println("Cannot create a mine field with that many rows and/or columns!");
    			System.exit(0);
    		}
    		
    	// Loop to randomly add mines as "M" on the board (20%)
	    	int randomRow, randomColm;
	    	for(int i = 0; i < mines; i++) {
    			randomRow = (int) Math.round(Math.random()*(rows - 1));
    			randomColm = (int) Math.round(Math.random()*(cols - 1));
	    			if (board[randomRow][randomColm] != "M") {
	    				board[randomRow][randomColm] = "M";
	    			}
	    			else 
	    				i--;
    		}
	    // Assign numbers to board
	    	assignNumbers();
    		
	    // Display the solution
	    /*	for (int i = 0; i < rows; i++) {
    			for (int j = 0; j < cols; j++) {
    				System.out.print(board[i][j] + " ");
    			}
    			System.out.println();
    		}												*/
 
    }
    
    // Methods and Constructors to run game
	    
    	// Display Game Board
		    public void display() {
		    	System.out.println();
		    	System.out.println("Rounds Completed: " + rounds);
		    	System.out.println();
		    	for(int i = 0;i < rows; i++) {
		    		System.out.print(" " + i + " |");
		    		for(int j = 0; j < cols; j++) {
		    			System.out.print(displayBoard[i][j] + "|");
		    		}
		    		System.out.println();
		    	}
		    	System.out.print("    ");
		    	for(int j = 0; j < cols; j++) {
		    		System.out.print(j + " ");
		    	}
		    	System.out.println();
		    	System.out.print("minesweeper-alpha$ ");
		    }
		
		// Print Title
		    public void printTitle() {
		    	System.out.println("        _");
		    	System.out.println("  /\\/\\ (_)_ __   ___  _____      _____  ___ _ __   ___ _ __");
		    	System.out.println(" /    \\| | '_ \\ / _ \\/ __\\ \\ /\\ / / _ \\/ _ \\ '_ \\ / _ \\ '__|");
		    	System.out.println("/ /\\/\\ \\ | | | |  __/\\__ \\\\ V  V /  __/  __/ |_) |  __/ |");
		    	System.out.println("\\/    \\/_|_| |_|\\___||___/ \\_/\\_/ \\___|\\___| .__/ \\___|_|");
		    	System.out.println("                                     ALPHA |_| EDITION");	
		    }
		
		// Add numbers to board
		    public void assignNumbers() {
		    	int count = 0;
    			// Add numbers to interior spaces
		    		for (int i = 1; i < rows - 1; i++) {
		    			for (int j = 1; j < cols - 1; j++) {
		    				if (board[i][j] != "M") {
		    					// Top Surrounding
		    					if (board[i-1][j-1] == "M")
		    						count++;
		    					if (board[i-1][j] == "M")
		    						count++;
		    					if (board[i-1][j+1] == "M")
		    						count++;
		    					// Left and Right Surrounding
		    					if (board[i][j-1] == "M")
		    						count++;
		    					if (board[i][j+1] == "M")
		    						count++;
		    					// Bottom Surrounding
		    					if (board[i+1][j-1] == "M")
		    						count++;
		    					if (board[i+1][j] == "M")
		    						count++;
		    					if (board[i+1][j+1] == "M")
		    						count++;
		    					// Apply count to board
		    					board[i][j] = Integer.toString(count);
		    					count = 0;
		    				}
		    			}
			    	}
			    	
		    	// Assign numbers to edge pieces excluding corners on the board
			    	for (int i = 1; i < cols - 1; i++) {
			    		// Top row
			    			if (board[0][i] != "M") {
			    				// Left and Right Surrounding
		    					if (board[0][i-1] == "M")
		    						count++;
		    					if (board[0][i+1] == "M")
		    						count++;
		    					// Bottom Surrounding
		    					if (board[1][i-1] == "M")
		    						count++;
		    					if (board[1][i] == "M")
		    						count++;
		    					if (board[1][i+1] == "M")
		    						count++;
			    				board[0][i] = Integer.toString(count);
			    				count = 0;
			    			}	
			    		// Bottom Row
			    			if (board[rows - 1][i] != "M") {
			    				// Top Surrounding
		    					if (board[rows-2][i-1] == "M")
		    						count++;
		    					if (board[rows-2][i] == "M")
		    						count++;
		    					if (board[rows-2][i+1] == "M")
		    						count++;
		    					// Left and Right Surrounding
		    					if (board[rows-1][i-1] == "M")
		    						count++;
		    					if (board[rows-1][i+1] == "M")
		    						count++;
			    				board[rows - 1][i] = Integer.toString(count);
			    				count = 0;
			    			}
			    		}
			    	for (int j = 1; j < rows - 1; j++) {
			    		// Left column
			    			if (board[j][0] != "M") {
			    				// Top surrounding
		    					if (board[j-1][0] == "M")
		    						count++;
		    					if (board[j-1][1] == "M")
		    						count++;	
		    					// Right surrounding
		    					if (board[j][1] == "M")
		    						count++;
		    					// Bottom surrounding
		    					if (board[j+1][0] == "M")
		    						count++;
		    					if (board[j+1][1] == "M")
		    						count++;
		    					board[j][0] = Integer.toString(count);
			    				count = 0;
			    			}
			    		// Right column
			    			if (board[j][cols-1] != "M") {
			    				// Top surrounding
		    					if (board[j-1][cols-1] == "M")
		    						count++;
		    					if (board[j-1][cols-2] == "M")
		    						count++;	
		    					// Left surrounding
		    					if (board[j][cols-2] == "M")
		    						count++;
		    					// Bottom surrounding
		    					if (board[j+1][cols-1] == "M")
		    						count++;
		    					if (board[j+1][cols-2] == "M")
		    						count++;
		    					board[j][cols-1] = Integer.toString(count);
			    				count = 0;	
			    			}
			    	}
			    // Assign numbers to the corners on the board
			    		if (board[0][0] != "M") {
			    			// Right surround
			    			if (board[0][1] =="M")
			    				count++;
			    			// Bottom Surround
			    			if (board[1][0] =="M")
			    				count++;
			    			if (board[1][1] =="M")
			    				count++;
			    			board[0][0] = Integer.toString(count);
		    				count = 0;
			    		}	
			    		if (board[0][cols-1] != "M") {
			    			// Left Surround
			    			if (board[0][cols-2] =="M")
			    				count++;
			    			// Bottom Surround
			    			if (board[1][cols-2] =="M")
			    				count++;
			    			if (board[1][cols-1] =="M")
			    				count++;
			    			board[0][cols-1] = Integer.toString(count);
		    				count = 0;
			    		}
			    		if (board[rows-1][0] != "M") {
			    			// Right Surround
			    			if (board[rows-1][1] =="M")
			    				count++;
			    			// Top Surround
			    			if (board[rows-2][0] =="M")
			    				count++;
			    			if (board[rows-2][1] =="M")
			    				count++;
			    			board[rows-1][0] = Integer.toString(count);
		    				count = 0;
			    		}
			    		if (board[rows-1][cols-1] != "M") {
			    			// Left Surround
			    			if (board[rows-1][cols-2] =="M")
			    				count++;
			    			// Top Surround
			    			if (board[rows-2][cols-2] =="M")
			    				count++;
			    			if (board[rows-2][cols-1] =="M")
			    				count++;
			    			board[rows-1][cols-1] = Integer.toString(count);
		    				count = 0;
	    		}
}
		// Get input
		    public String getInput() {
		    	while ("".equals(input.toString()));
		        return input.toString();
		    }
		
		// Seed Error Message
		    public void printSeedError(){
		    	System.out.println();
		    	System.out.println("Cannot create game with FILENAME, because it is not formatted correctly.");
		    	System.out.println();
		    	System.exit(0);
		    }
		    
		// Command Possibilities
		    // Print Help
		    public void printHelp() {
		    	System.out.println();
	    		System.out.println("Commands Available...");
	    		System.out.println(" - Reveal: r/reveal row col");
	    		System.out.println(" - Mark: m/mark row col");
	    		System.out.println(" - Guess: g/guess row col");
	    		System.out.println(" - Help: h/help");
	    		System.out.println(" - Quit: q/quit");
		    }
		    // Assign variables to rows and columns
		    public void readNext() {
		    	if (input.hasNext()) {
					String row1 = input.next();
					row1.trim();
					dRow = Integer.parseInt(row1);
	    		}
	    		if (input.hasNext()) {
					String colm1 = input.next();
					colm1.trim();
					dColm = Integer.parseInt(colm1);
	    		}
		    }
		    public void commands(String a) {
		    	// reveal
		    	if (a.equals("r") || a.equals("reveal")) {
		    		readNext();
		    		displayBoard[dRow][dColm] = board[dRow][dColm];
		    	}
		    	// mark
		    	else if (a.equals("m") || a.equals("mark")) {
		    		readNext();
					displayBoard[dRow][dColm] = "F";
		    	}
		    	// guess
		    	else if (a.equals("g") || a.equals("guess")) {
		    		readNext();
					displayBoard[dRow][dColm] = "?";
		    	}
		    	// help
		    	else if (a.equals("h") || a.equals("help")) {
		    		printHelp();
		    	}
		    	// quit
		    	else if (a.equals("q") || a.equals("quit")) {
		    		System.out.println("TRY AGAIN LATER");
			    	System.out.println("BYE!");
		    	}
		    	// default help
		    	else {
		    		System.out.println();
		    		System.out.println("Command not recongnized!");
		    		printHelp();
		    	}
		    }
		// Lose Message
		    public void loseMessage() {
		    	System.out.println("");
		    	System.out.println("Oh no... You revealed a mine!");
		    	System.out.println("__ _ __ _ _ __ ___ ___ _____ _____ _ __\\");
		    	System.out.println("/ _` |/ _` | '_ ` _ \\ / _ \\ / _ \\ \\ / / _ \\ '__|\\");
		    	System.out.println("| (_| | (_| | | | | | | __/ | (_) \\ V / __/ |\\");
		    	System.out.println("\\__, |\\__,_|_| |_| |_|\\___| \\___/ \\_/ \\___|_|");
		    	System.out.println("|___/");
	    		System.exit(0);
		    }
	    // Check for win
		    public boolean equals(String[][] displayBoard, String[][] board) {
			  int mineCount = 0;
		    	for (int i = 0; i < rows; i++) {
				  for (int j = 0; j < cols; j++) {
					  if (displayBoard[i][j] == "F" && board[i][j] == "M")
						  mineCount++;
					  else if (displayBoard[i][j] == "F")
						  mineCount++;
					  if (displayBoard[i][j] == "?")
						  mineCount++;
					  if (mineCount == mines)
						  return true;
				  }
			  	}
				return false;
		    }
		// Win Message
		    public void winMessage() {
		    	score = (rows * cols) - mines - rounds;
		    	System.out.println();
		    	System.out.println();
		    	System.out.println("CONGRATULATIONS!"); 
		    	System.out.println("YOU HAVE WON!");
		    	System.out.println("SCORE: " + score);
		    	System.out.println();
		    }
     // Minesweeper

    /**
     * Starts the game and execute the game loop.
     */
    public void run() {

        // TODO implement
    		// Title Screen
		    	printTitle();
		// Game Display Loop
		    // Give variables to displayBoard
		    	displayBoard = new String[rows][cols];
		    	for(int i = 0; i < rows; i++) {
		    		for(int j = 0; j < cols; j++) {
		    			displayBoard[i][j] = " ";
		    		}
		    	}
		    // Create Game display	
		    	rounds = 0;
		    	input = new Scanner(System.in);
		    	for(int k = 0; k == rounds; k++) {
		    		display();
		    		
		    	// Win or lose check
			    	if (displayBoard[dRow][dColm] == "M") {
			    		loseMessage();
			    	}
			    	else if (equals(displayBoard,board) == true) {
			    		winMessage();
			    	}
			    	
		    		rounds++;
			    // Scanner for user input
			    	if (input.hasNext()) {	
					    command = input.next();
					    command.trim();
					    commands(command);
					}
					else
					    printHelp();
		    	}
    } // run

    /**
     * The entry point into the program. This main method does implement some logic
     * for handling command line arguments. If two integers are provided as
     * arguments, then a Minesweeper game is created and started with a grid size
     * corresponding to the integers provided and with 10% (rounded up) of the
     * squares containing mines, placed randomly. If a single word string is
     * provided as an argument then it is treated as a seed file and a Minesweeper
     * game is created and started using the information contained in the seed file.
     * If none of the above applies, then a usage statement is displayed and the
     * program exits gracefully.
     *
     * @param args the shell arguments provided to the program
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws FileNotFoundException {

        /*
         * The following switch statement has been designed in such a way that if errors
         * occur within the first two cases, the default case still gets executed. This
         * was accomplished by special placement of the break statements.
         */

        Minesweeper game = null;

        switch (args.length) {

        // random game
        case 2:

            int rows, cols;

            // try to parse the arguments and create a game
            try {
                rows = Integer.parseInt(args[0]);
                cols = Integer.parseInt(args[1]);
                game = new Minesweeper(rows, cols);
                break;
            } catch (NumberFormatException nfe) {
                // line intentionally left blank
            } // try

            // seed file game
        case 1:

            String filename = args[0];
            File file = new File(filename);

            if (file.isFile()) {
                game = new Minesweeper(file);
                break;
            } // if

            // display usage statement
        default:

            System.out.println("Usage: java Minesweeper [FILE]");
            System.out.println("Usage: java Minesweeper [ROWS] [COLS]");
            System.exit(0);

        } // switch

        // if all is good, then run the game
        game.run();

    } // main

} // Minesweeper
