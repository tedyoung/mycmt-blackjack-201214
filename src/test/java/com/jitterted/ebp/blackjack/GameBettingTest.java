package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameBettingTest {

  @Test
  public void newGamePlayerBalanceIsZero() throws Exception {
    Game game = new Game();

    assertThat(game.playerBalance())
        .isZero();
  }

  @Test
  public void playerDeposits100ThenBalanceIs100() throws Exception {
    Game game = new Game();

    game.playerDeposits(100);

    assertThat(game.playerBalance())
        .isEqualTo(100);
  }

  @Test
  public void playerWith100BalanceBets50ThenBalanceIs50() throws Exception {
    Game game = new Game();
    game.playerDeposits(100);

    game.playerBets(50);

    assertThat(game.playerBalance())
        .isEqualTo(100 - 50);
  }

  @Test
  public void playerWith100BalanceBets50AndWinsThenBalanceIs150() throws Exception {
    Game game = new Game();
    game.playerDeposits(100);
    game.playerBets(50);

    game.playerWins();

    assertThat(game.playerBalance())
        .isEqualTo(100 - 50 + 100);
  }

  @Test
  public void playerWith80Bets60AndLosesBalanceIs20() throws Exception {
    Game game = new Game();
    game.playerDeposits(80);
    game.playerBets(60);

    game.playerLoses();

    assertThat(game.playerBalance())
        .isEqualTo(80 - 60);
  }

  @Test
  public void playerWith75Bets40AndPushesBalanceIs75() throws Exception {
    Game game = new Game();
    game.playerDeposits(75);
    game.playerBets(40);

    game.playerPushes();

    assertThat(game.playerBalance())
        .isEqualTo(75 - 40 + 40);
  }

  @Test
  public void playerWith400Bets400WinBlackjackBalanceIs1_000() throws Exception {
    Game game = new Game();
    game.playerDeposits(400);
    game.playerBets(400);

    game.playerWinsBlackjack();

    assertThat(game.playerBalance())
        .isEqualTo(400 - 400 + ((int) (400 * 2.5)));
  }


}