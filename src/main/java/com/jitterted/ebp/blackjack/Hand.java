package com.jitterted.ebp.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.fusesource.jansi.Ansi.ansi;

public class Hand {
  private final List<Card> cards = new ArrayList<>();

  public Hand(List<Card> cards) {
    this.cards.addAll(cards);
  }

  public Hand() {
  }

  void drawCardFrom(Deck deck) {
    cards.add(deck.draw());
  }

  int value() {
    List<Card> hand = cards;
    int handValue = hand
        .stream()
        .mapToInt(Card::rankValue)
        .sum();

    // does the hand contain at least 1 Ace?
    boolean hasAce = hand
        .stream()
        .anyMatch(card -> card.rankValue() == 1);

    // if the total hand value <= 11, then count the Ace as 11 by adding 10
    if (hasAce && handValue <= 11) {
      handValue += 10;
    }

    return handValue;
  }

  Card firstCard() {
    return cards.get(0);
  }

  void display() {
    System.out.println(cards.stream()
                            .map(Card::display)
                            .collect(Collectors.joining(
                               ansi().cursorUp(6).cursorRight(1).toString())));
  }

  boolean isBusted() {
    return value() > 21;
  }

  boolean isLessThanOrEqualTo(int value) {
    return value() <= value;
  }

  String displayValue() {
    return String.valueOf(value());
  }

  boolean pushes(Hand hand) {
    return value() == hand.value();
  }

  boolean beats(Hand hand) {
    return hand.value() < value();
  }

  public boolean valueEquals(int value) {
    return value() == value;
  }
}
