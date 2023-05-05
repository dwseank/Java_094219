import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;
    public static Random rnd;

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final char TILE_EMPTY = '–';
    public static final char TILE_SHIP = '#';
    public static final char TILE_SHIP_DAMAGED = 'X';
    public static final char TILE_HIT = 'V';
    public static final char TILE_MISS = 'X';

    /**
     * Handles board size input and returns size in int array format.
     * @return int array: [0] = board size x, [1] = board size y
     */
    public static int[] inputBoardSize() {
        System.out.println("Enter the board size");
        String boardSizeInput = scanner.nextLine(); // format: nXm
        return parseInput(boardSizeInput, "X");
    }

    /**
     * Extracts integers from a string using a given delimiter.
     * Used to make usable data in int format from string input.
     * @param str input string
     * @param delimiter used to split the input string
     * @return array of all integers in the input string
     */
    public static int[] parseInput(String str, String delimiter) {
        String[] strings = str.split(delimiter);
        int[] ints = new int[strings.length];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = Integer.parseInt(strings[i]);
        }
        return ints;
    }

    /**
     * Creates a new board with empty tiles.
     * @param x board size X
     * @param y board size Y
     * @return 2D char array with empty tiles
     */
    public static char[][] createBoard(int x, int y) {
        char[][] board = new char[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                board[i][j] = TILE_EMPTY;
            }
        }
        return board;
    }


    /**
     * Handles battleship sizes input and returns ship sizes array.
     * @return array where every element represents a ship defined by its size
     */
    public static int[] inputShipSizes() {
        System.out.println("Enter the battleships sizes");
        String shipSizesInput = scanner.nextLine(); // format: n1Xs1 n2Xs2 ... nkXsk
        int[] shipSizes = parseInput(shipSizesInput, "X| ");
        return parseShips(shipSizes);
    }

    /**
     * Creates an int array to store all ships using their size.
     * Used to make accessing ship sizes simpler.
     * @param shipSizes int array in which every 2 consecutive elements define amount and size
     * @return int array where every element represents a ship defined by its size
     */
    public static int[] parseShips(int[] shipSizes) {
        int shipSizeParameters = 2;
        int amountOfShips = 0;

        for (int i = 0; i < shipSizes.length; i += shipSizeParameters) {
            amountOfShips += shipSizes[i];
        }
        int[] ships = new int[amountOfShips];
        int index = 0;
        for (int i = 0; i < shipSizes.length; i += shipSizeParameters) {
            for (int j = 0; j < shipSizes[i]; j++) {
                ships[index] = shipSizes[i+1];
                index++;
            }
        }
        return ships;
    }


    /**
     * Handles ship location input and returns an int array where every 3 consecutive elements define x, y and orientation of a ship.
     * @param board the board to add ships to
     * @param ships array of ships
     * @param player determines if the action is done by the player
     * @return array of ship locations
     */
    public static int[] inputShipLocations(char[][] board, int[] ships, boolean player) {
        int locationParameters = 3;
        int orientationParameters = 2;
        int boardSizeX = board.length;
        int boardSizeY = board[0].length;
        int[] shipLocations = new int[ships.length * locationParameters];

        if (player) {
            System.out.println("Your current game board:");
            printBoard(board);
        }
        for (int i = 0; i < ships.length; i++) {
            if (player) {
                System.out.println("Enter location and orientation for battleship of size " + ships[i]);
            }

            int[] location = null;
            boolean validLocation = false;
            // Handle input
            while (!validLocation) {
                if (player) {
                    String locationInput = scanner.nextLine(); // format: x, y, orientation
                    location = parseInput(locationInput, ", ");
                } else { // Computer
                    int x = rnd.nextInt(boardSizeX);
                    int y = rnd.nextInt(boardSizeY);
                    int orientation = rnd.nextInt(orientationParameters);
                    location = new int[] {x, y, orientation};
                }
                validLocation = isLocationValid(board, location, ships[i], player);
            }

            addShipToBoard(board, location, ships[i]);
            // Save ship location
            shipLocations[i * locationParameters] = location[0];
            shipLocations[i * locationParameters + 1] = location[1];
            shipLocations[i * locationParameters + 2] = location[2];

            if (player) {
                System.out.println("Your current game board:");
                printBoard(board);
            }
        }
        return shipLocations;
    }

    /**
     * Checks if a ship can be placed in given location.
     * @param board board the check validity of location on
     * @param location requested location
     * @param shipSize size of ship at location
     * @param player determines if the action is done by the player
     * @return true if the location can be used
     */
    public static boolean isLocationValid(char[][] board, int[] location, int shipSize, boolean player) {
        String error = getLocationError(board, location, shipSize);
        if (error.isEmpty()) {
            return true;
        }
        if (player) {
            System.out.println(error);
        }
        return false;
    }

    /**
     * Returns description of location error.
     * @param board board to check validity of location
     * @param location requested location
     * @param shipSize size of ship at location
     * @return string description of error, or empty string if there is no error
     */
    public static String getLocationError(char[][] board, int[] location, int shipSize) {
        int boardSizeX = board.length;
        int boardSizeY = board[0].length;
        int x = location[0];
        int y = location[1];
        int orientation = location[2];

        // Check if valid orientation
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            return "Illegal orientation, try again!";
        }
        // Check if starting tile is in bounds
        if (!isInBounds(boardSizeX, boardSizeY, x, y)) {
            return "Illegal tile, try again!";
        }

        if (orientation == HORIZONTAL) {
            // Check if ship is in bounds
            if (y + shipSize - 1 > boardSizeY - 1) {
                return "Battleship exceeds the boundaries of the board, try again!";
            }
            // Check if ship overlaps another ship
            for (int i = 0; i < shipSize; i++) {
                if (board[x][y + i] == TILE_SHIP) {
                    return "Battleship overlaps another battleship, try again!";
                }
            }
            // Check if ship is too close to another ship
            for (int i = 0; i < shipSize; i++) {
                if (isRadiusOccupied(board, x, y + i)) {
                    return "Adjacent battleship detected, try again!";
                }
            }
        } else { // orientation == VERTICAL
            // Check if ship is in bounds
            if (x + shipSize - 1 > boardSizeX - 1) {
                return "Battleship exceeds the boundaries of the board, try again!";
            }
            // Check if ship overlaps another ship
            for (int i = 0; i < shipSize; i++) {
                if (board[x + i][y] == TILE_SHIP) {
                    return "Battleship overlaps another battleship, try again!";
                }
            }
            // Check if ship is too close to another ship
            for (int i = 0; i < shipSize; i++) {
                if (isRadiusOccupied(board, x + i, y)) {
                    return "Adjacent battleship detected, try again!";
                }
            }
        }
        return "";
    }

    /**
     * Checks if a given location is in the board bounds.
     * @param boardSizeX size X of board
     * @param boardSizeY size Y of board
     * @param x x value of location
     * @param y y value of location
     * @return true if location is within the board bounds
     */
    public static boolean isInBounds(int boardSizeX, int boardSizeY, int x, int y) {
        return x >= 0 && x <= boardSizeX-1 && y >= 0 && y <= boardSizeY-1;
    }

    /**
     * Checks if area around given location is occupied by a ship.
     * @param board the board to check
     * @param x x value of location
     * @param y y value of location
     * @return true if found another ship around location
     */
    public static boolean isRadiusOccupied(char[][] board, int x, int y) {
        int boardSizeX = board.length;
        int boardSizeY = board[0].length;
        int radius = 1;

        for (int i = x - radius; i <= x + radius ; i++) {
            for (int j = y - radius; j <= y + radius; j++) {
                if (isInBounds(boardSizeX, boardSizeY, i, j)) {
                    if (board[i][j] == TILE_SHIP) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Adds a ship to the board.
     * @param board board to add the ship to
     * @param location where to add the ship
     * @param shipSize size of the ship to add
     */
    public static void addShipToBoard(char[][] board, int[] location, int shipSize) {
        int x = location[0];
        int y = location[1];
        int orientation = location[2];

        if (orientation == HORIZONTAL) {
            for (int i = 0; i < shipSize; i++) {
                board[x][y + i] = TILE_SHIP;
            }
        } else { // orientation == VERTICAL
            for (int i = 0; i < shipSize; i++) {
                board[x + i][y] = TILE_SHIP;
            }
        }
    }


    /**
     * Prints the given board.
     * @param board board to print
     */
    public static void printBoard(char[][] board) {
        int boardSizeX = board.length;
        int boardSizeY = board[0].length;
        int maxSpaces = countDigits(boardSizeX - 1);
        int spaces = maxSpaces;

        // Print column numbers
        for (int i = 0; i < spaces; i++)
            System.out.print(" ");
        for (int i = 0; i < boardSizeY; i++)
            System.out.print(" " + i);
        System.out.println();

        // Handle rows
        for (int i = 0; i < boardSizeX; i++) {
            // Print row number
            spaces = maxSpaces - countDigits(i);
            for (int j = 0; j < spaces; j++)
                System.out.print(" ");
            System.out.print(i);
            // Print row
            for (int j = 0; j < boardSizeY; j++)
                System.out.print(" " + board[i][j]);
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Counts the amount of digits in a given number.
     * @param number the number to count its digits
     * @return the amount of digits in the number
     */
    public static int countDigits(int number) {
        if (number == 0) {
            return 1;
        }

        int count = 0;
        while (number != 0) {
            number /= 10;
            count++;
        }
        return count;
    }


    /**
     * Handles attack input and attacks the enemy board.
     * @param guessingBoard attacker's guessing board
     * @param enemyBoard enemy's game board
     * @param player determines if the action is done by the player
     * @return true if hit a ship on enemy board, false if missed
     */
    public static boolean inputAttack(char[][] guessingBoard, char[][] enemyBoard, boolean player) {
        int boardSizeX = guessingBoard.length;
        int boardSizeY = guessingBoard[0].length;
        int x = 0;
        int y = 0;
        boolean validAttack = false;

        if (player) {
            System.out.println("Your current guessing board:");
            printBoard(guessingBoard);
            System.out.println("Enter a tile to attack");
        }
        // Handle input
        while (!validAttack) {
            if (player) {
                String attackLocationInput = scanner.nextLine();
                int[] attackLocation = parseInput(attackLocationInput, ", ");
                x = attackLocation[0];
                y = attackLocation[1];
            } else { // Computer
                x = rnd.nextInt(boardSizeX);
                y = rnd.nextInt(boardSizeY);
            }
            validAttack = isAttackValid(guessingBoard, x, y, player);
        }

        if (!player) {
            System.out.println("The computer attacked (" + x + ", " + y + ")");
        }

        /* Attack result */
        // Miss
        if (enemyBoard[x][y] == TILE_EMPTY) {
            guessingBoard[x][y] = TILE_MISS;
            System.out.println("That is a miss!");
            return false;
        }
        // Hit
        guessingBoard[x][y] = TILE_HIT;
        enemyBoard[x][y] = TILE_SHIP_DAMAGED;
        System.out.println("That is a hit!");
        return true;
    }

    /**
     * Checks if the attack location is valid.
     * @param board the attacked game board
     * @param x x value of attack location
     * @param y y value of attack location
     * @param player determines if the action is done by the player
     * @return true if the attack is valid
     */
    public static boolean isAttackValid(char[][] board, int x, int y, boolean player) {
        int boardSizeX = board.length;
        int boardSizeY = board[0].length;

        // Check if tile is in bounds
        if (!isInBounds(boardSizeX, boardSizeY, x, y)) {
            if (player) {
                System.out.println("Illegal tile, try again!");
            }
            return false;
        }
        // Check if tile was already attacked
        if (board[x][y] != TILE_EMPTY) {
            if (player) {
                System.out.println("Tile already attacked, try again!");
            }
            return false;
        }
        return true;
    }

    /**
     * Counts the number of sunk ships on a given board.
     * @param board board to count sunken ships on
     * @param shipLocations locations of ships on the board
     * @param shipSizes sizes of ships on the board
     * @return number of sunk ships on the board
     */
    public static int countSunkShips(char[][] board, int[] shipLocations, int[] shipSizes) {
        int locationParameters = 3;
        int count = 0;

        for (int i = 0; i < shipLocations.length / locationParameters; i++) {
            int x = shipLocations[i * locationParameters];
            int y = shipLocations[i * locationParameters + 1];
            int orientation = shipLocations[i * locationParameters + 2];
            int shipSize = shipSizes[i];

            boolean shipSunk = true;
            if (orientation == HORIZONTAL) {
                for (int j = 0; j < shipSize; j++) {
                    if (board[x][y + j] != TILE_SHIP_DAMAGED) {
                        shipSunk = false;
                        break;
                    }
                }
            } else { // orientation == VERTICAL
                for (int j = 0; j < shipSize; j++) {
                    if (board[x + j][y] != TILE_SHIP_DAMAGED) {
                        shipSunk = false;
                        break;
                    }
                }
            }

            if (shipSunk) {
                count++;
            }
        }
        return count;
    }


    /** Handles the battleship game. */
    public static void battleshipGame() {
        // Create boards
        int[] boardSize = inputBoardSize(); // [0] = size x, [1] = size y
        char[][] playerGameBoard = createBoard(boardSize[0], boardSize[1]);
        char[][] playerGuessingBoard = createBoard(boardSize[0], boardSize[1]);
        char[][] computerGameBoard = createBoard(boardSize[0], boardSize[1]);
        char[][] computerGuessingBoard = createBoard(boardSize[0], boardSize[1]);

        // Create ships
        int[] ships = inputShipSizes();
        int totalShips = ships.length;
        int playerRemainingShips = totalShips;
        int computerRemainingShips = totalShips;

        // Set ship locations
        int[] playerShipLocations = inputShipLocations(playerGameBoard, ships, true);
        int[] computerShipLocations = inputShipLocations(computerGameBoard, ships, false);

        // Play game
        boolean player = true;
        while (playerRemainingShips > 0 && computerRemainingShips > 0) {
            if (player) {
                boolean attackResult = inputAttack(playerGuessingBoard, computerGameBoard, true);
                if (attackResult) { // Hit a ship on computer's board
                    int remainingShips = totalShips - countSunkShips(computerGameBoard, computerShipLocations, ships);
                    if (remainingShips < computerRemainingShips) { // Sunk the ship
                        computerRemainingShips = remainingShips; // Update remaining ships
                        System.out.println("The computer's battleship has been drowned, "
                                + remainingShips + " more battleships to go!");
                    }
                }
            } else { // Computer
                boolean attackResult = inputAttack(computerGuessingBoard, playerGameBoard, false);
                if (attackResult) { // Hit a ship on player's board
                    int remainingShips = totalShips - countSunkShips(playerGameBoard, playerShipLocations, ships);
                    if (remainingShips < playerRemainingShips) { // Sunk the ship
                        playerRemainingShips = remainingShips; // Update remaining ships
                        System.out.println("Your battleship has been drowned, you have left "
                                + remainingShips + " more battleships!");
                    }
                }
                // Print player's board after computer's turn
                System.out.println("Your current game board:");
                printBoard(playerGameBoard);
            }
            player = !player; // Next turn
        }

        // Game end message
        if (computerRemainingShips == 0) {
            System.out.println("You won the game!");
        } else { // playerRemainingShips == 0
            System.out.println("You lost ):");
        }
    }


    public static void main(String[] args) throws IOException {
        String path = args[0];
        scanner = new Scanner(new File(path));
        int numberOfGames = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Total of " + numberOfGames + " games.");

        for (int i = 1; i <= numberOfGames; i++) {
            scanner.nextLine();
            int seed = scanner.nextInt();
            rnd = new Random(seed);
            scanner.nextLine();
            System.out.println("Game number " + i + " starts.");
            battleshipGame();
            System.out.println("Game number " + i + " is over.");
            System.out.println("------------------------------------------------------------");
        }
        System.out.println("All games are over.");
    }
}