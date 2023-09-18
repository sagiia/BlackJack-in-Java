import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics2D;

public class Card {
  // Variables to store the suit, rank, and value of the card
  private final int suitCard;
  private final int rankCard;
  private final int valueCard;

  /**
   * Constructs a new card with the specified suit, rank, and value.
   *
   * @param suit  The suit of the card (0 to 3).
   * @param rank  The rank of the card (0 to 12).
   * @param value The value of the card (1 to 11).
   */
  public Card(int suit, int rank, int value) {
    suitCard = suit;
    rankCard = rank;
    valueCard = value;
  }

  /**
   * Gets the value of the card.
   *
   * @return The value of the card.
   */
  public int getValueCard() {
    return valueCard;
  }

  /**
   * Prints a 2D representation of the card on the graphics context.
   *
   * @param graphics2D       The Graphics2D object used for rendering.
   * @param dealerStatusTurn Indicates whether it's the dealer's turn.
   * @param cardFaceDown     Indicates whether the card should be face down.
   * @param cardNumber       The position of the card in the layout.
   * @throws IOException     If there's an error reading card images.
   */
  public void print2DCard(Graphics2D graphics2D, boolean dealerStatusTurn, boolean cardFaceDown, int cardNumber) throws IOException {

    BufferedImage cardSheet = ImageIO.read(new File("files/cardSheet.png"));
    int widthIMG = 950;
    int heightIMG = 392;

    BufferedImage[][] cardImage = new BufferedImage[4][13];
    BufferedImage backOfACard = ImageIO.read(new File("files/backOfCard.png"));

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 13; j++) {
        cardImage[i][j] = cardSheet.getSubimage(j * widthIMG/13, i*heightIMG/4, widthIMG/13, heightIMG/4);
      }
    }

    int yPos;
    if (dealerStatusTurn) {
      yPos = 75;
    }
    else {
      yPos = 400;
    }

    int xPos = 500 + 75 * cardNumber;

    if (cardFaceDown) {
      // Render the back of the card when it's face down
      graphics2D.drawImage(backOfACard, xPos, yPos, null );
    }
    else {
      // Render the card image based on suit and rank
      graphics2D.drawImage(cardImage[suitCard][rankCard], xPos, yPos, null);
    }
  }
}
