package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class HandValueAceTest {

  private static final Suit DUMMY_SUIT = Suit.CLUBS;

  @Test
  public void handWithOneAceTwoCardsIsValuedAt11() throws Exception {
    var cards = List.of(new Card(DUMMY_SUIT, "A"),
                        new Card(DUMMY_SUIT, "5"));
    Hand hand = new Hand(cards);

    assertThat(hand.value())
        .isEqualTo(11 + 5);
  }

  @Test
  public void handWithOneAceAndOtherCardsEqualTo11IsValuedAt1() throws Exception {
    var cards = List.of(new Card(DUMMY_SUIT, "A"),
                        new Card(DUMMY_SUIT, "8"),
                        new Card(DUMMY_SUIT, "3"));
    Hand hand = new Hand(cards);

    assertThat(hand.value())
        .isEqualTo(1 + 8 + 3);
  }

  @Test
  public void handWithOneAceAndOtherCardsEqualTo10IsValuedAt11() throws Exception {
    var cards = List.of(new Card(DUMMY_SUIT, "A"),
                        new Card(DUMMY_SUIT, "10"));
    Hand hand = new Hand(cards);

    assertThat(hand.value())
        .isEqualTo(11 + 10);
  }

}
