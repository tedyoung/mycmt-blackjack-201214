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

}