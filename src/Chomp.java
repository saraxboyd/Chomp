/**
 * Filename: Chomp.java
 * This is Chomp, a game where two players take turns eating a cookie from a board.
 * the cookies to the right and below the cookie that the player chooses to remove
 * are also removed. Players play until the only cookie left of the board is the one
 * in the top left corner because that cookie is poisoned. The player forced to eat 
 * that cookie looses.
 * 
 * @author Sara Boyd
 * @version Last Motified: September 30, 2017
 * 
 * Resources: Discrete Mathematics and Its Applications 7th edition by Kenneth Rosen for the rules of Chomp
 * 			http://www.java67.com/2014/10/how-to-create-and-initialize-two-dimensional-array-java-example.html 
 * 			to learn about 2D arrays
 * 
 * I have acted with honesty and integrity in producing this work
 * and am unaware of anyone who has not. 
 * Sara Boyd
 */
import java.util.*;
public class Chomp {
	public static void main (String[] args){
		int run; //Holds the value of how many columns the player wants
		int rise; //Holds the value of how many rows the player wants
		Scanner sc = new Scanner(System.in); 

		instructions(); //Calls method to print game instructions
		
		System.out.println("Enter the size of the board (LXH):");
		rise = sc.nextInt(); //number of columns for the board
		run = sc.nextInt(); //number of rows for the board 

		Chomp Game = new Chomp(run, rise);
		Game.constructBoard(); 
		Game.printBoard();

		boolean validMove = false; //true if player can make move and false if they cannot 
		boolean nextMove = false; //true if another move can be made false if one cannot
		int player = 1; //keeps track of player number 
		while (!nextMove){ //while there are still moves that can be made 
			do{
				//if the player count is odd, it is player 1's turn
				if(player%2 == 1){
					System.out.println("Player 1's turn:");
				}
				//if the player count is even, it is player 2's turn
				else{ 
					System.out.println("Player 2's turn:");
				}
				
				//get the coordinates of the cookie that the player wants to eat
				//first the x-coordinate, then the y-coordinate  
				run = sc.nextInt();
				rise = sc.nextInt();
				//checks to see if the cookie can be 'eaten'
				//if not, it will re-print the board, the do-while loop is entered, and it will prompt the 
				//same player for a new cookie coordinate
				validMove = Game.eatCookie(rise, run);
				Game.printBoard();
			}
			while(!validMove);
			//the cookie coordinate that the player entered was valid, so the player count is increased and
			//it checks to see if that was the last move that could be made before a player is forced to eat the
			//poisoned cookie
			player++;
			//if more move can be made, the while loop is entered again
			nextMove = Game.lastMove();
		}
		//if the player count is an odd number, then player 2 wins since player has already been increased 
		//before exiting the while loop
		if(player%2 == 1){
			System.out.println("Player 2 wins");
		}
		//if it is even otherwise player 1 wins
		else{
			System.out.println("Player 1 wins");
		}

		sc.close();
	}
	//Global variables
	int height, //height
	length;		//length
	public static final int DEFAULT_LENGTH = 5;
	public static final int DEFAULT_HEIGHT = 5;
	boolean[][] board; //2D array of new board
	
	/**
	 * Default constructor will set the length and height of the board to 5
	 */
	public Chomp(){
		this(DEFAULT_LENGTH, DEFAULT_HEIGHT);
	}

	/**
	 * Overloaded constructor for class Chomp
	 * @param l assigns the desired length of the board to its length if it is greater than 0
	 * @param h assigns the desired height of the board to its height if it is greater than 0
	 */
	public Chomp(int l, int h){
		//if the height or the length the user enters is less than 1, that dimension will be set to 5 so the players
		//can still play the game if they enter an invalid dimension for the board
		if (!(l >= 0)){
			l = DEFAULT_LENGTH;
		}
		if (!(h >= 0)){
			h = DEFAULT_HEIGHT;
		}
		//the desired dimensions of the board are set to the actual dimensions of the board
		length = l;
		height = h;

	}
	
	/**
	 * Constructs a board using a 2D array and the dimensions set in the constructor
	 */
	public void constructBoard(){
		board = new boolean[length][height];
		//each element in the 2D array is set to true
		for (int i = 0; i < length; i++){
			for (int j = 0; j < height; j++){
				board[i][j] = true;
			}
		}
		//the element in the top left of the board is set to false since it is poisoned and no player should eat it
		board[0][0] = false;
	}
	
	/**
	 * Prints out the current state of the board
	 */
	public void printBoard(){
		System.out.println("Cookie Board: ");
		for (int i = 0; i < length; i++){
			for (int j = 0; j < height; j++){
				//if the position of the array is true, there is an edible cookie there, so it prints an 'O'
				if (board[i][j] == true){
					System.out.print("O ");
				}
				//if the position of the array is the top left corner, it is the poisoned cookie, so prints a 'P'
				else if(i==0 && j==0){
					System.out.print("P ");
				}
				//if the position of the array is false, that cookie has already been eaten, so prints an 'X'
				else{
						System.out.print("X ");
				}
			}
			//returns to the next line when it comes to the end of the row
			System.out.println("");
		}
	}
	
	/**
	 * Takes the coordinate of the cookie that the player wants to eat an updated the status of the board
	 * @param run, how many columns over the desired cookie is (column count starts at 1)
	 * @param rise, how many rows down the desired cookie is (row count starts at 1)
	 * @return true if the cookie coordinate given is within the bounds of the grid, greater than 0, and hasn't 
	 * 			already been taken away, otherwise the cookie could not be removed and it returns false
	 */
	public boolean eatCookie(int run, int rise){
		//if either cookie coordinate is beyond the bounds of the board, return false
		if((run) > length || (rise) > height){
			return false;
			}
		//if either cookie coordinate is less than 1, return false
		else if (run <= 0 || rise <= 0 ){
			return false;
		}
		//if the cookie has already been taken away from the board, return false
		else if (board[run-1][rise-1] == false){
			return false;
		}
		//the value of the cookie coordinate is true and within the bounds of the gird
		else{
			//loops through the rows and columns at and to the left and below the cookie coordinate
			for (int i = run-1; i < length; i++){
				for (int j = rise-1; j < height; j++){
					//if the cookie at each position is not already false, change it to false
					if (board[i][j] == true){
						board[i][j] = false;
					}
				}
			}
			//cookie removal was successful, so return true
			return true;
		}
	}
	
	/**
	 * Checks to see if players can make at least one more move
	 * @return true if all the elements in the 2D array are false, and false if there is at least one element that is true
	 */
	public boolean lastMove(){
		//loops through each element in the 2D array
		for (int i = 0; i < length; i++){
			for (int j = 0; j < height; j++){
				//if the value at this element is true, break and return false since the next player can choose 
				//this coordinate as their next move
				if (board[i][j] == true){
					return false;
				}
			}
		}
		//the loop has determined that all the elements in the 2D array are false, therefore no more moves can be made,
		//so method returns true
		return true;
	}
	
	/**
	 * Prints out the rules of Chomp
	 */
	public static void instructions(){
		System.out.println("Chomp");
		System.out.println("Rules: Two players take turns eating a cookie from the board.");
		System.out.println("For each cookie eaten, all the cookies to the right of and below that");
		System.out.println("cookie are also taken away from the board. The cookie at the top left of");
		System.out.println("the board is poisoned. The object of the game is to clear the board and");
		System.out.println("not be the player forced to eat the poisoned cookie.");
		System.out.println();
		System.out.println("P - Poisoned cookie");
		System.out.println("O - Cookie that can be eaten");
		System.out.println("X - Cookie has already been eaten");
		System.out.println();
	}
}
