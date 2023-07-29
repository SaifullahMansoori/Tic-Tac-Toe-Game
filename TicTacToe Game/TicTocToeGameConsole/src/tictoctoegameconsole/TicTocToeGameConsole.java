import java.util.Scanner;

public class TicTocToeGameConsole {
    private static final int BOARD_SIZE = 3;
    private static final char EMPTY = ' ';
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';

    private char[][] board;
    private char currentPlayer;

    public TicTocToeGameConsole() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
        currentPlayer = PLAYER_X;
    }

    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    private void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print("| " + board[i][j] + " ");
            }
            System.out.println("|");
            System.out.println("-------------");
        }
    }

    private boolean makeMove(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE || board[row][col] != EMPTY) {
            System.out.println("Invalid move! Try again.");
            return false;
        }
        board[row][col] = currentPlayer;
        return true;
    }

    private boolean checkWin() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                return true; // Row win
            }
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) {
                return true; // Column win
            }
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true; // Diagonal win (top-left to bottom-right)
        }
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true; // Diagonal win (top-right to bottom-left)
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        int row, col;
        boolean isGameOver = false;

        System.out.println("Welcome to Tic Tac Toe!");
        printBoard();

        while (!isGameOver) {
            System.out.println("Player " + currentPlayer + ", it's your turn.");
            System.out.print("Enter row (0-" + (BOARD_SIZE - 1) + "): ");
            row = scanner.nextInt();
            System.out.print("Enter column (0-" + (BOARD_SIZE - 1) + "): ");
            col = scanner.nextInt();

            if (makeMove(row, col)) {
                printBoard();
                if (checkWin()) {
                    System.out.println("Player " + currentPlayer + " wins!");
                    isGameOver = true;
                } else if (isBoardFull()) {
                    System.out.println("It's a draw!");
                    isGameOver = true;
                } else {
                    switchPlayer();
                }
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
       TicTocToeGameConsole game = new TicTocToeGameConsole();
        game.play();
    }
}
