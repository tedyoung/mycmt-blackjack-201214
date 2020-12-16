package com.jitterted.ebp.blackjack;

import org.fusesource.jansi.Ansi;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {

  private final Deck deck;

   private final Hand dealerHand = new Hand();
   private final Hand playerHand = new Hand();

  public static void main(String[] args) {
    displayWelcomeScreen();

    playGame();

    resetScreen();
  }

  private static void resetScreen() {
    System.out.println(ansi().reset());
  }

  private static void playGame() {
    Game game = new Game();
    game.initialDeal();
    game.play();
  }

  private static void displayWelcomeScreen() {
    System.out.println(ansi()
                           .bgBright(Ansi.Color.WHITE)
                           .eraseScreen()
                           .cursor(1, 1)
                           .fgGreen().a("Welcome to")
                           .fgRed().a(" JitterTed's")
                           .fgBlack().a(" BlackJack"));
  }

  public Game() {
    deck = new Deck();
  }

  public void initialDeal() {
    // Everyone gets two cards to start
    dealRoundOfCards();
    dealRoundOfCards();
  }

  private void dealRoundOfCards() {
    // Deal to players first because that's the rule of Blackjack
    playerHand.drawCardFrom(deck);
    dealerHand.drawCardFrom(deck);
  }

  public void play() {
    boolean playerBusted = playerPlays();

    dealerPlays(playerBusted);

    displayFinalGameState();

    determineOutcome(playerBusted);
  }

  private void determineOutcome(boolean playerBusted) {
    if (playerBusted) {
      System.out.println("You Busted, so you lose.  💸");
    } else if (dealerHand.isBusted()) {
      System.out.println("Dealer went BUST, Player wins! Yay for you!! 💵");
    } else if (playerHand.beats(dealerHand)) {
      System.out.println("You beat the Dealer! 💵");
    } else if (dealerHand.pushes(playerHand)) {
      System.out.println("Push: The house wins, you Lose. 💸");
    } else {
      System.out.println("You lost to the Dealer. 💸");
    }
  }

  private boolean playerPlays() {
    // get Player's decision: hit until they stand, then they're done (or they go bust)
    boolean playerBusted = false;
    while (!playerBusted) {
      displayGameState();
      String playerChoice = inputFromPlayer().toLowerCase();
      if (playerChoice.startsWith("s")) {
        break;
      }
      if (playerChoice.startsWith("h")) {
        playerHand.drawCardFrom(deck);
        if (playerHand.isBusted()) {
          playerBusted = true;
        }
      } else {
        System.out.println("You need to [H]it or [S]tand");
      }
    }
    return playerBusted;
  }

  private void dealerPlays(boolean playerBusted) {
    // Dealer makes its choice automatically based on a simple heuristic (<=16, hit, 17>stand)
    if (!playerBusted) {
      while (dealerHand.isLessThanOrEqualTo(16)) {
        dealerHand.drawCardFrom(deck);
      }
    }
  }

  private String inputFromPlayer() {
    System.out.println("[H]it or [S]tand?");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }

  private void displayGameState() {
    System.out.print(ansi().eraseScreen().cursor(1, 1));
    System.out.println("Dealer has: ");
    System.out.println(dealerHand.firstCard().display()); // first card is Face Up

    // second card is the hole card, which is hidden
    displayBackOfCard();

    System.out.println();
    System.out.println("Player has: ");
    playerHand.display();
    System.out.println(" (" + playerHand.displayValue() + ")");
  }

  private void displayBackOfCard() {
    System.out.print(
        ansi()
            .cursorUp(7)
            .cursorRight(12)
            .a("┌─────────┐").cursorDown(1).cursorLeft(11)
            .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
            .a("│░ J I T ░│").cursorDown(1).cursorLeft(11)
            .a("│░ T E R ░│").cursorDown(1).cursorLeft(11)
            .a("│░ T E D ░│").cursorDown(1).cursorLeft(11)
            .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
            .a("└─────────┘"));
  }

  private void displayFinalGameState() {
    System.out.print(ansi().eraseScreen().cursor(1, 1));
    System.out.println("Dealer has: ");
    dealerHand.display();
    System.out.println(" (" + dealerHand.displayValue() + ")");

    System.out.println();
    System.out.println("Player has: ");
    playerHand.display();
    System.out.println(" (" + playerHand.displayValue() + ")");
  }

}
