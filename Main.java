import javax.swing.*;

public class Main {

    // JFrame instances for the menu and game screens
    public static JFrame frameMenu = new JFrame();
    public static JFrame frameGame = new JFrame();

    // Player scores and current money
    private static int userScore = 0;
    private static int dealerScore = 0;
    public static int currentMoney = 1000;

    // Player name
    public static String userName = "";

    // Game engine instance
    public static GameEngine newGameEngine = new GameEngine(frameGame);

    // Flag to track the first run
    private static boolean firstRun = true;

    // Enumeration for game status
    public enum STATUS {
        STATUS_MENU,
        STATUS_GAME
    }

    // Current game status
    public static STATUS currentStatus = STATUS.STATUS_MENU;

    /**
     * The main entry point of the application.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        if (currentStatus == STATUS.STATUS_MENU) {
            // Start the menu
            startMenu();
        }
    }

    /**
     * Initializes and displays the game menu.
     */
    public static void startMenu() {
        frameMenu.setTitle("BLACKJACK");
        frameMenu.setSize(1130, 665);
        frameMenu.setLocationRelativeTo(null);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setResizable(false);

        Setting componentStart = new Setting();
        frameMenu.add(componentStart);
        frameMenu.setVisible(true);
    }

    /**
     * A thread for continuously refreshing the UI with game statistics.
     */
    public static Thread UIRefreshThread = new Thread(() -> {
        while (true) {
            // Continuously update the UI elements with game statistics
            newGameEngine.environmentElement.refresh(currentMoney, userScore, dealerScore - 1, newGameEngine.cardFaceDown);
        }
    });

    /**
     * A thread for monitoring the game, including user input and round outcomes.
     */
    public static Thread GameMonitorThread = new Thread(() -> {
        while (userName.isEmpty()) {
            // Prompt the user for their name if it's not provided
            userName = JOptionPane.showInputDialog("What's your name?");
        }
        while (true) {
            if (firstRun || newGameEngine.roundOver) {
                if (newGameEngine.dealerVictory) {
                    // Dealer wins the round, update scores and money
                    dealerScore++;
                    currentMoney -= ComponentOfGame.currentBet;
                } else {
                    // Player wins the round, update scores and money
                    userScore++;
                    currentMoney += ComponentOfGame.currentBet * 2;
                }
                // Clear the game screen and initialize a new game
                frameGame.getContentPane().removeAll();
                newGameEngine = new GameEngine(frameGame);
                newGameEngine.formGame();

                firstRun = false;
            }
        }
    });
}
