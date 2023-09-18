import java.util.ArrayList;
import javax.swing.*;
import java.awt.Font;

/**
 * The `GameEngine` class manages the game logic for the Blackjack game.
 */
public class GameEngine {

    // ArrayLists to store dealer and player cards
    ArrayList<Card> dealerCards;
    ArrayList<Card> playerCards;

    // Flags to track game state
    public boolean cardFaceDown;
    public boolean dealerVictory;
    public volatile boolean roundOver;

    // Swing components and game-related objects
    JFrame frameOfGame;
    Deck deckOfCards;
    ComponentOfGame environmentElement;
    ComponentOfGame cardElement;

    JButton hitButton;
    JButton standButton;
    JButton doubleButton;
    JButton exitButton;

    /**
     * Constructs a new game engine for the Blackjack game.
     *
     * @param frame The JFrame associated with the game.
     */
    public GameEngine(JFrame frame) {
        // Initialize the deck and shuffle it
        deckOfCards = new Deck();
        deckOfCards.shuffleDeck();

        dealerCards = new ArrayList<>();
        playerCards = new ArrayList<>();

        environmentElement = new ComponentOfGame(dealerCards, playerCards);
        frameOfGame = frame;

        cardFaceDown = true;
        dealerVictory = true;
        roundOver = false;
    }

    /**
     * Sets up and displays the game interface.
     */
    public void formGame() {
        frameOfGame.setTitle("BLACKJACK");
        frameOfGame.setSize(1130, 665);
        frameOfGame.setLocationRelativeTo(null);
        frameOfGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameOfGame.setResizable(false);

        hitButton = new JButton("HIT");
        hitButton.setBounds(390, 550, 100, 50);
        hitButton.setFont(new Font("Segoe Print", Font.BOLD, 16));
        standButton = new JButton("STAND");
        standButton.setBounds(520, 550, 100, 50);
        standButton.setFont(new Font("Segoe Print", Font.BOLD, 16));
        doubleButton = new JButton("DOUBLE");
        doubleButton.setBounds(650, 550, 100, 50);
        doubleButton.setFont(new Font("Segoe Print", Font.BOLD, 13));
        exitButton = new JButton("EXIT");
        exitButton.setBounds(50, 550, 190, 50);
        exitButton.setFont(new Font("Segoe Print", Font.BOLD, 16));

        frameOfGame.add(hitButton);
        frameOfGame.add(standButton);
        frameOfGame.add(doubleButton);
        frameOfGame.add(exitButton);

        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frameOfGame, "You left the game with " +  Main.currentMoney + "$.");
            System.exit(0);
        });

        environmentElement = new ComponentOfGame(dealerCards, playerCards);
        environmentElement.setBounds(0, 0, 1130, 665);
        frameOfGame.add(environmentElement);
        frameOfGame.setVisible(true);
    }

    /**
     * Starts a new round of the game.
     */
    public void startGame() {

        for(int i = 0; i<2; i++) {
            dealerCards.add(deckOfCards.getCard(i));
        }
        for(int i = 2; i<4; i++) {
            playerCards.add(deckOfCards.getCard(i));
        }
        for (int i = 0; i < 4; i++) {
            deckOfCards.removeCard(0);
        }

        cardElement = new ComponentOfGame(dealerCards, playerCards);
        cardElement.setBounds(0, 0, 1130, 665);
        frameOfGame.add(cardElement);
        frameOfGame.setVisible(true);

        checkHand(dealerCards);
        checkHand(playerCards);

        hitButton.addActionListener(e -> {
            addCard(playerCards);
            checkHand(playerCards);
            if (getSumOfHand(playerCards)<17 && getSumOfHand(dealerCards)<17){
                addCard(dealerCards);
                checkHand(dealerCards);
            }
        });

        doubleButton.addActionListener(e -> {
            addCard(playerCards);
            addCard(playerCards);
            checkHand(playerCards);
            if (getSumOfHand(playerCards)<17 && getSumOfHand(dealerCards)<17){
                addCard(dealerCards);
                checkHand(dealerCards);
            }
        });

        standButton.addActionListener(e -> {
            while (getSumOfHand(dealerCards)<17) {
                addCard(dealerCards);
                checkHand(dealerCards);
            }
            if ((getSumOfHand(dealerCards)<21) && getSumOfHand(playerCards)<21) {
                if(getSumOfHand(playerCards) > getSumOfHand(dealerCards)) {
                    cardFaceDown = false;
                    dealerVictory = false;
                    JOptionPane.showMessageDialog(frameOfGame, Main.userName + " prevails with a stronger hand!");
                    rest();
                    roundOver = true;
                }
                else {
                    cardFaceDown = false;
                    JOptionPane.showMessageDialog(frameOfGame, "Dealer prevails with a stronger hand!");
                    rest();
                    roundOver = true;
                }
            }
        });
    }

    /**
     * Checks the hand of a player or the dealer for Blackjack or bust.
     *
     * @param hand The hand to check (dealer's or player's).
     */
    public void checkHand (ArrayList<Card> hand) {
        if (hand.equals(playerCards)) {
            if(getSumOfHand(hand) == 21){
                cardFaceDown = false;
                dealerVictory = false;
                JOptionPane.showMessageDialog(frameOfGame, Main.userName + " has achieved a Blackjack! " + Main.userName + " is the victor!");
                rest();
                roundOver = true;
            }
            else if (getSumOfHand(hand) > 21) {
                cardFaceDown = false; JOptionPane.showMessageDialog(frameOfGame, Main.userName + " has gone bust! DEALER emerges victorious!");
                rest();
                roundOver = true;
            }
        }
        else {
            if(getSumOfHand(hand) == 21) {
                cardFaceDown = false;
                JOptionPane.showMessageDialog(frameOfGame, "Dealer has achieved a Blackjack! Dealer is the victor!");
                rest();
                roundOver = true;
            }
            else if (getSumOfHand(hand) > 21) {
                cardFaceDown = false;
                dealerVictory = false;
                JOptionPane.showMessageDialog(frameOfGame, "DEALER has gone bust! " + Main.userName + " emerges victorious!");
                rest();
                roundOver = true;
            }
        }
    }

    /**
     * Adds a card to a player's hand.
     *
     * @param hand The player's hand to add the card to.
     */
    public void addCard(ArrayList<Card> hand) {
        hand.add(deckOfCards.getCard(0));
        deckOfCards.removeCard(0);
        cardFaceDown = true;
    }

    /**
     * Checks if a hand contains an Ace.
     *
     * @param hand The hand to check.
     * @return True if the hand contains an Ace, false otherwise.
     */
    public boolean hasAceInHand(ArrayList<Card> hand) {
        for (Card card : hand) {
            if (card.getValueCard() == 11) {
                return true;
            }
        }
        return false;
    }

    /**
     * Counts the number of Aces in a hand.
     *
     * @param hand The hand to count Aces in.
     * @return The number of Aces in the hand.
     */
    public int aceCountInHand(ArrayList<Card> hand){
        int aceCount = 0;
        for (Card card : hand) {
            if (card.getValueCard() == 11) {
                aceCount++;
            }
        }
        return aceCount;
    }

    /**
     * Calculates the sum of a hand with a high Ace.
     *
     * @param hand The hand to calculate the sum for.
     * @return The sum of the hand with a high Ace.
     */
    public int getSumWithHighAce(ArrayList<Card> hand) {
        int handSum = 0;
        for (Card card : hand) {
            handSum = handSum + card.getValueCard();
        }
        return handSum;
    }

    /**
     * Calculates the sum of a hand.
     *
     * @param hand The hand to calculate the sum for.
     * @return The sum of the hand.
     */
    public int getSumOfHand (ArrayList<Card> hand) {
        if(hasAceInHand(hand)) {
            if(getSumWithHighAce(hand) <= 21) {
                return getSumWithHighAce(hand);
            }
            else{
                for (int i = 0; i < aceCountInHand(hand); i++) {
                    int sumOfHand = getSumWithHighAce(hand)-(i+1)*10;
                    if(sumOfHand <= 21) {
                        return sumOfHand;
                    }
                }
            }
        }
        else {
            int sumOfHand = 0;
            for (Card card : hand) {
                sumOfHand = sumOfHand + card.getValueCard();
            }
            return sumOfHand;
        }
        return 22;
    }

    /**
     * Pauses the game briefly (in milliseconds).
     */
    public static void rest() {
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException ignored) {}
    }
}