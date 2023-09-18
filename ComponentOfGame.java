import javax.swing.JComponent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import java.io.*;

/**
 * Represents the game component responsible for rendering game elements and handling user input.
 * Extends JComponent and implements MouseListener to handle mouse events.
 */
public class ComponentOfGame extends JComponent implements MouseListener {
    // Images for background, icons, and money representation
    public BufferedImage wallpaperImage;
    public BufferedImage icon;
    public BufferedImage money_icon;

    // Lists to hold cards for the dealer and player
    private final ArrayList<Card> dealerCards;
    private final ArrayList<Card> playerCards;

    // Point totals for dealer and player
    private int dealerPoint;
    private int playerPoint;

    // Flag to determine whether the dealer's card is face down
    public boolean CardFaceDown = true;
    public static boolean betPlaced = false;

    // Current player's money and bet amount
    private int currentMoney;
    public static int currentBet;

    /**
     * Initializes the game component with dealer and player hands.
     *
     * @param dealerHand The ArrayList of cards representing the dealer's hand.
     * @param playerHand The ArrayList of cards representing the player's hand.
     */
    public ComponentOfGame(ArrayList<Card> dealerHand, ArrayList<Card> playerHand) {
        dealerCards = dealerHand;
        playerCards = playerHand;
        dealerPoint = 0;
        playerPoint = 0;
        currentMoney = 1000;
        addMouseListener(this);
    }

    /**
     * Paints the game graphics, including the background, cards, and game information.
     *
     * @param g The Graphics object used for rendering.
     */
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        try {
            wallpaperImage = ImageIO.read(new File("files/wallpaper.png"));
            icon = ImageIO.read(new File("files/blackjack.png"));
            money_icon = ImageIO.read(new File("files/money.png"));
        } catch (IOException ignored) {
        }

        graphics2D.drawImage(wallpaperImage, 0, 0, null);
        graphics2D.drawImage(icon, 510, 200, null);
        graphics2D.drawImage(money_icon, 850, 300, null);
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("CSegoe Print", Font.BOLD, 30));
        graphics2D.drawString("Dealer", 515, 50);
        graphics2D.drawString(Main.userName, 515, 380);
        graphics2D.drawString("Dealer Points: ", 50, 250);
        graphics2D.drawString(Integer.toString(dealerPoint), 300, 250);
        graphics2D.drawString(Main.userName + "'s Points: ", 50, 300);
        graphics2D.drawString(Integer.toString(playerPoint), 300, 300);
        graphics2D.setFont(new Font("CSegoe Print", Font.BOLD, 20));
        graphics2D.drawString("To start each round,", 825, 250);
        graphics2D.drawString("Just click the dollar button", 800, 275);
        graphics2D.setFont(new Font("Segoe Print", Font.BOLD, 20));
        graphics2D.drawString("CURRENT Money: " + currentMoney, 800, 500);

        try {
            for (int i = 0; i < dealerCards.size(); i++) {
                if (i == 0) {
                    dealerCards.get(i).print2DCard(graphics2D, true, CardFaceDown, i);
                } else {
                    dealerCards.get(i).print2DCard(graphics2D, true, false, i);
                }
            }
        } catch (IOException ignored) {
        }

        try {
            for (int i = 0; i < playerCards.size(); i++) {
                playerCards.get(i).print2DCard(graphics2D, false, false, i);
            }
        } catch (IOException ignored) {
        }
    }

    /**
     * Refreshes the game component with updated game data.
     *
     * @param cMoney The current player's money balance.
     * @param uScore The player's score.
     * @param dScore The dealer's score.
     * @param fDown  Whether the dealer's card is face down.
     */
    public void refresh(int cMoney, int uScore, int dScore, boolean fDown) {
        currentMoney = cMoney;
        playerPoint = uScore;
        dealerPoint = dScore;
        CardFaceDown = fDown;
        this.repaint();
    }

    /**
     * Handles mouse button press events.
     *
     * @param e The MouseEvent representing the mouse button press.
     */
    public void mousePressed(MouseEvent e) {
        int mousePosX = e.getX();
        int mousePosY = e.getY();

        if (mousePosX >= 850 && mousePosX <= 1000 && mousePosY >= 300 && mousePosY <= 450) {
            betPlaced = true;
            String[] options = new String[]{"15", "30", "50", "70", "100"};
            int response = JOptionPane.showOptionDialog(null, "Please provide your bet amount!", "BETTING",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (response == 0) {
                currentBet = 15;
                currentMoney -= 15;
            } else if (response == 1) {
                currentBet = 30;
                currentMoney -= 30;
            } else if (response == 2) {
                currentBet = 50;
                currentMoney -= 50;
            } else if (response == 3) {
                currentBet = 70;
                currentMoney -= 70;
            } else if (response == 4) {
                currentBet = 100;
                currentMoney -= 100;
            } else {
                currentBet = 1;
                currentMoney -= 1;
            }
            // Start the game after placing the bet
            Main.newGameEngine.startGame();
        }
    }

    // Unused mouse event methods
    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }
}
