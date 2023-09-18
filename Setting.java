import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Represents the main menu of the Blackjack game.
 * Extends JComponent and implements ActionListener to handle button clicks.
 */
public class Setting extends JComponent implements ActionListener {

    private final JButton playButton = new JButton("PLAY");
    private final JButton exitButton = new JButton("EXIT");
    private final JButton helpButton = new JButton("HELP");
    private final JButton aboutButton = new JButton("ABOUT");
    private static BufferedImage wallpaperImage;

    /**
     * Initializes the Setting component by adding action listeners to buttons.
     */
    public Setting() {
        playButton.addActionListener(this);
        exitButton.addActionListener(this);
        helpButton.addActionListener(this);
        aboutButton.addActionListener(this);
    }

    /**
     * Paints the main menu graphics, including the background image and text.
     *
     * @param graphics The Graphics object used for rendering.
     */
    public void paintComponent(Graphics graphics) {

        Graphics2D graphics2D = (Graphics2D) graphics;

        try {
            wallpaperImage = ImageIO.read(new File("files/startWallpaper.jpg"));
        } catch (IOException ignored) {
        }

        graphics2D.drawImage(wallpaperImage, 0, 0, null);

        graphics2D.setFont(new Font("Segoe Print", Font.BOLD, 100));
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString("Welcome", 365, 100);
        graphics2D.drawString("to", 510, 180);
        graphics2D.drawString("BLACKJACK!", 240, 260);

        graphics2D.setFont(new Font("ArSegoe Print", Font.BOLD, 20));
        graphics2D.drawString("ⒸSagi Menahem", 950, 620);

        playButton.setBounds(485, 300, 150, 80);
        exitButton.setBounds(485, 400, 150, 80);
        helpButton.setBounds(80, 450, 150, 80);
        aboutButton.setBounds(900, 450, 150, 80);

        playButton.setFont(new Font("Segoe Print", Font.BOLD, 40));
        exitButton.setFont(new Font("Segoe Print", Font.BOLD, 40));
        helpButton.setFont(new Font("Segoe Print", Font.BOLD, 40));
        aboutButton.setFont(new Font("Segoe Print", Font.BOLD, 30));

        super.add(playButton);
        super.add(exitButton);
        super.add(helpButton);
        super.add(aboutButton);
    }

    /**
     * Handles button clicks and performs corresponding actions.
     *
     * @param e The ActionEvent representing the button click.
     */
    public void actionPerformed(ActionEvent e) {
        JButton selectedButton = (JButton) e.getSource();

        if (selectedButton == exitButton) {
            // Exit the application
            System.exit(0);
        } else if (selectedButton == playButton) {
            // Start the game and threads for game monitoring and UI refreshing
            Main.currentStatus = Main.STATUS.STATUS_GAME;
            Main.frameMenu.dispose();
            Main.UIRefreshThread.start();
            Main.GameMonitorThread.start();
        } else if (selectedButton == helpButton) {
            // Display game tutorial
            JOptionPane.showMessageDialog(this, """
                            Blackjack is a popular card game played in casinos worldwide.
                            The objective is to beat the dealer by having a hand value closer to 21 without exceeding it.
                            A standard deck of 52 playing cards is used,
                            with each card having a rank (e.g., Ace, 2, 3, Jack, Queen, King) and a suit (e.g., Hearts, Diamonds, Clubs, Spades).
                            In Blackjack, card values are straightforward: number cards are worth their face value,
                            face cards (Jack, Queen, King) are worth 10 points each, and Aces can be worth 1 or 11 points.
                            In a simple text-based game, one player competes against the dealer.
                            The game proceeds by dealing two cards to the player and two cards to the dealer, one face-up and one face-down.
                            The player decides to "hit" (take another card) or "stand" (keep their current hand).
                            If the player's hand exceeds 21 points (bust), they lose.
                            Once the player stands, the dealer's hidden card is revealed, and the dealer must hit until their hand value is at least 17.
                            The winner is determined based on the final hand values, with the player winning if their hand is closer to 21 than the dealer's without busting.
                            If both have the same value, it's a tie.""", "BLACKJACK TUTORIAL",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (selectedButton == aboutButton) {
            // Display information about the game
            JOptionPane.showMessageDialog(this, "This game was created by Sagi Menahem in 2016 for learning the use of SWING.", "ABOUT", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
