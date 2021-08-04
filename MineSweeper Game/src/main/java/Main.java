import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        int level = chooseLevel();

        Game newGame = new Game(level);
        newGame.startGame();

        int moves = 0;

        while (!newGame.gameOver()) {
            System.out.println("Current Status of Board: ");

            System.out.println();
            newGame.printBoard(newGame.getGameBoard());


            Coords givenCoord = makeMove(newGame);


            if (moves == 0) {
                while (newGame.isItMine(givenCoord.getX(), givenCoord.getY())) {
                    System.out.println("Sorry bad luck! Try again!");
                    givenCoord = makeMove(newGame);
                }
            }
            newGame.executeTurn(givenCoord.getX(), givenCoord.getY());

            moves++;

            if (!newGame.gameOver() && newGame.getMovesLeft() == 0) {
                System.out.println("You have won!");
                newGame.setOver(true);
                break;
            }
        }
    }

    private static int chooseLevel() {

        Scanner scan = new Scanner(System.in);
        int level;

        System.out.println("Enter the Difficulty Level");
        System.out.println("Press 0 for BEGINNER (9 * 9 Cells and 10 Mines)");
        System.out.println("Press 1 for INTERMEDIATE (16 * 16 Cells and 40 Mines)");
        System.out.println("Press 2 for ADVANCED (24 * 24 Cells and 99 Mines)");

        level = Integer.parseInt(scan.nextLine());

        return level;
    }


    private static Coords makeMove(Game newGame) {
        System.out.println("Enter your move: (row, column) -> ");

        Scanner scanner = new Scanner(System.in);
        try {
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            while (!newGame.isValid(x, y)) {
                System.out.println("The coordinates must be in range of the board");
                x = scanner.nextInt();
                y = scanner.nextInt();
            }

            return new Coords(x, y);
        } catch (Exception e) {
            System.out.println("Enter an integer!");
            System.exit(0);
            return new Coords(-1, -1);
        }
    }
}

