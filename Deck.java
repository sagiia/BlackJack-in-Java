import java.util.ArrayList;
import java.util.Collections;

public class Deck {

  // ArrayList to store the cards in the deck
  private final ArrayList<Card> deckArray;

  /**
   * Constructs a new deck of 52 playing cards.
   * Initializes the deck with cards for each suit (4 suits) and rank (13 ranks).
   */
  public Deck() {
    deckArray = new ArrayList<>();

    // Loop through each suit (0 to 3) and each rank (0 to 12)
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 13; j++) {
        if (j == 0) {
          // Ace card with a value of 11
          Card card = new Card(i, j, 11);
          deckArray.add(card);
        }
        else if (j >= 10) {
          // Face cards (Jack, Queen, King) with a value of 10
          Card card = new Card(i, j, 10);
          deckArray.add(card);
        }
        else {
          // Number cards with values 2 to 10
          Card card = new Card(i, j, j + 1);
          deckArray.add(card);
        }
      }
    }
  }

  /**
   * Shuffles the deck to randomize the order of cards.
   */
  public void shuffleDeck() {
    Collections.shuffle(deckArray);
  }

  /**
   * Retrieves the card at the specified index in the deck.
   *
   * @param i The index of the card to retrieve.
   * @return The card at the specified index.
   */
  public Card getCard(int i) {
    return deckArray.get(i);
  }

  /**
   * Removes the card at the specified index from the deck.
   *
   * @param i The index of the card to remove.
   */
  public void removeCard(int i) {
    deckArray.remove(i);
  }
}
