
import tictactoeui.TicTacToeGameBackend;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeUI extends JFrame {

    private JButton[][] buttons;
    private JLabel currentPlayerLabel;
    private JLabel resultLabel;
    private TicTacToeGameBackend game;

    private JButton resetButton; // New button for resetting the game

    public TicTacToeUI() {
        super("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        buttons = new JButton[3][3];
        game = new TicTacToeGameBackend();
        currentPlayerLabel = new JLabel("Current Player: " + game.getCurrentPlayer());
        resultLabel = new JLabel("Game in progress...");

        JPanel gridPanel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 50));
                buttons[i][j].addActionListener(new MoveListener(i, j));
                gridPanel.add(buttons[i][j]);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(currentPlayerLabel);
        infoPanel.add(resultLabel);
        add(infoPanel, BorderLayout.SOUTH);

        // Create the "Reset" button and add ActionListener
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        add(resetButton, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateBoard() {
        char[][] board = game.getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(String.valueOf(board[i][j]));
            }
        }
    }

    private class MoveListener implements ActionListener {

        private int row;
        private int col;

        public MoveListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!game.isGameOver() && game.makeMove(row, col)) {
                updateBoard();
                currentPlayerLabel.setText("Current Player: " + game.getCurrentPlayer());
                if (game.checkWin()) {
                    resultLabel.setText("Player " + game.getCurrentPlayer() + " wins!");
                    gameEnded();
                } else if (game.isBoardFull()) {
                    resultLabel.setText("It's a draw!");
                    gameEnded();
                } else {
                    game.switchPlayer();
                }
            }
        }
    }

    private void gameEnded() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
        if (game.checkWin()) {
            showWinnerMessage("Player " + game.getCurrentPlayer() + " wins!");
        } else if (game.isBoardFull()) {
            showDrawMessage("It's a draw!");
        }
    }

    // Helper method to show a graphical message for the winner
    private void showWinnerMessage(String message) {
        ImageIcon icon = new ImageIcon("winner_icon.png"); // Replace with your winner icon image file path
        JOptionPane.showMessageDialog(this, message, "Winner", JOptionPane.PLAIN_MESSAGE, icon);
    }

    // Helper method to show a graphical message for a draw
    private void showDrawMessage(String message) {
        ImageIcon icon = new ImageIcon("draw_icon.png"); // Replace with your draw icon image file path
        JOptionPane.showMessageDialog(this, message, "Draw", JOptionPane.PLAIN_MESSAGE, icon);
    }

    private void resetGame() {
        // Re-enable buttons and reset the game object
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(true);
                buttons[i][j].setText("");
            }
        }
        game = new TicTacToeGameBackend();
        currentPlayerLabel.setText("Current Player: " + game.getCurrentPlayer());
        resultLabel.setText("Game in progress...");
    }

    // Helper method to show a graphical message using JOptionPane
    private void showGameResult(String message) {
        JOptionPane.showMessageDialog(this, message, "Game Result", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeUI());
    }
}
