public class Game {
    private static int SIDE;
    private static int MINES;

    char[][] gameBoard;
    char[][] playerBoard;

    private static int[][] mineCords;
    private static boolean gameOver;
    private static int moveLeft;

    public Game(int level) {
        switch (level) {
            case 0:
                SIDE = 9;
                MINES = 10;
                break;
            case 1:
                SIDE = 16;
                MINES = 40;
                break;
            case 2:
                SIDE = 24;
                MINES = 99;
                break;
        }
    }

    public void startGame() {
        gameBoard = new char[SIDE][SIDE];
        playerBoard = new char[SIDE][SIDE];
        mineCords = new int[MINES][2];

        initBoards(gameBoard);
        initBoards(playerBoard);
        addMines(mineCords, gameBoard);

        moveLeft = SIDE * SIDE - MINES;
        gameOver = false;
    }

    public void printBoard(char[][] board) {
        System.out.print("    ");
        for (int i = 0; i < SIDE; i++)
            System.out.print(i + " ");

        System.out.println();

        for (int i = 0; i < SIDE; i++) {
            if (i > 9) System.out.print(i + "  ");
            else System.out.print(i + "   ");

            for (int j = 0; j < SIDE; j++) {
                if (j == 10) System.out.print(" " + board[i][j] + "  ");
                else if (j > 10) System.out.print(board[i][j] + "  ");
                else System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public char[][] getGameBoard() {
        return gameBoard;
    }

    public int[][] getMinesCoordinates() {
        return mineCords;
    }

    public int getMovesLeft() {
        return moveLeft;
    }

    public boolean isItMine(int row, int col) {
        return gameBoard[row][col] == '*';
    }

    public boolean gameOver() {
        return gameOver;
    }

    public void setOver(boolean bool) {
        gameOver = bool;
    }

    public void executeTurn(int row, int col) {

        if (playerBoard[row][col] != '-') {
            gameOver = false;
            return;
        }


        if (gameBoard[row][col] == '*') {
            playerBoard[row][col] = '*';


            for (int i = 0; i < MINES; i++)
                playerBoard[mineCords[i][0]][mineCords[i][1]] = '*';
            printBoard(playerBoard);

            System.out.println("\nYou lost!");
            gameOver = true;
        } else {
            int adjCount = findAdjacentMines(row, col);


            moveLeft--;
            playerBoard[row][col] = (char) (adjCount + '0');


            if (adjCount == 0) {
                if (isValid(row - 1, col) && !isItMine(row - 1, col))
                    executeTurn(row - 1, col);

                if (isValid(row + 1, col) && !isItMine(row + 1, col))
                    executeTurn(row + 1, col);

                if (isValid(row, col - 1) && !isItMine(row, col - 1))
                    executeTurn(row, col - 1);

                if (isValid(row, col + 1) && !isItMine(row, col + 1))
                    executeTurn(row, col + 1);

                if (isValid(row - 1, col + 1) && !isItMine(row - 1, col + 1))
                    executeTurn(row - 1, col + 1);

                if (isValid(row - 1, col - 1) && !isItMine(row - 1, col - 1))
                    executeTurn(row - 1, col - 1);

                if (isValid(row + 1, col - 1) && !isItMine(row + 1, col - 1))
                    executeTurn(row + 1, col - 1);

                if (isValid(row + 1, col + 1) && !isItMine(row + 1, col + 1))
                    executeTurn(row + 1, col + 1);
            }

            gameOver = false;
        }
    }

    private int findAdjacentMines(int row, int col) {

        int count = 0;


        if (isValid(row - 1, col) && isItMine(row - 1, col))
            count++;

        if (isValid(row + 1, col) && isItMine(row + 1, col))
            count++;

        if (isValid(row, col - 1) && isItMine(row, col - 1))
            count++;

        if (isValid(row, col + 1) && isItMine(row, col + 1))
            count++;

        if (isValid(row - 1, col + 1) && isItMine(row - 1, col + 1))
            count++;

        if (isValid(row - 1, col - 1) && isItMine(row - 1, col - 1))
            count++;

        if (isValid(row + 1, col - 1) && isItMine(row + 1, col - 1))
            count++;

        if (isValid(row + 1, col + 1) && isItMine(row + 1, col + 1))
            count++;

        return count;
    }


    private void addMines(int[][] cords, char[][] board) {
        int index = 0;
        while (index < MINES) {
            int x = (int) (Math.random() * (SIDE + 1));
            int y = (int) (Math.random() * (SIDE + 1));

            if (isValid(x, y))
                if (gameBoard[x][y] == '-') {
                    cords[index][0] = x;
                    cords[index][1] = y;

                    board[x][y] = '*';
                    index++;
                }
        }
    }

    public boolean isValid(int row, int col) {
        return (row >= 0) && (row < SIDE) && (col >= 0) && (col < SIDE);
    }

    private void initBoards(char[][] board) {
        for (int i = 0; i < SIDE; i++)
            for (int j = 0; j < SIDE; j++)
                board[i][j] = '-';
    }
}
