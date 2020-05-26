package com.shawn.touchstone.tdd;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class TicTacToeSpec {

    @Rule
    public ExpectedException ee = ExpectedException.none();

    private TicTacToe ticTacToe;

    @Before
    public void createTicTacToe() {
        ticTacToe = new TicTacToe();
    }
    /*
    A piece can be placed on any empty space of a 3×3 board.
     */
    @Test
    public void whenXOutsideBoardThenRuntimeExp() {
        ee.expect(RuntimeException.class);
        ticTacToe.play(4, 1);
    }

    @Test
    public void whenYOutsideBoardThenRuntimeExp() {
        ee.expect(RuntimeException.class);
        ticTacToe.play(1, 4);
    }

    @Test
    public void whenOccupiedThenRuntimeException() {
        ticTacToe.play(1, 1);
        ee.expect(RuntimeException.class);
        ticTacToe.play(1, 1);
    }

    @Test
    public void whenPlayedThenBoardShouldOccupied() {
        ticTacToe.play(1, 1);
        ticTacToe.play(2, 2);
        char[][] board = ticTacToe.getBoard();
        assertThat(board[0][0], not('-'));
        assertThat(board[1][1], not('-'));
    }

    /**
     * There should be a way to find out which player should play next.
     */
    @Test
    public void whenTheFirstTurnThenXShouldBeNext() {
        char next = ticTacToe.nextPlayer();
        assertThat(next, is('X'));
    }

    @Test
    public void whenXIsLastPlayerThenOShouldBeNext() {
        ticTacToe.play(1, 1);
        char next = ticTacToe.nextPlayer();
        assertThat(next, is('O'));
    }

    @Test
    public void whenOIsLastPlayerThenXShouldBeNext() {
        ticTacToe.play(1, 1);
        ticTacToe.play(2, 2);
        char next = ticTacToe.nextPlayer();
        assertThat(next, is('X'));
    }

    /**
     * A player wins by being the first to connect a line of friendly pieces from one side or corner of the board to the other.
     */
    @Test
    public void whenPlayThenNoWinner() {
        String winner = ticTacToe.play(1, 1);
        assertThat(winner, is("No winner"));
    }

    /**
     *  X X X
     *  O O -
     *  - - -
     */
    @Test
    public void whenPlayAndWholeHorizontalLineThenWinner() {
        ticTacToe.play(1, 1);
        ticTacToe.play(1, 2);
        ticTacToe.play(2, 1);
        ticTacToe.play(2, 2);
        String winner = ticTacToe.play(3, 1);
        assertThat(winner, is("X is the winner"));
    }

    /**
     *  X O -
     *  X O -
     *  X - -
     */
    @Test
    public void whenPlayAndWholeVerticalLineThenWinner() {
        ticTacToe.play(1, 1);
        ticTacToe.play(2, 1);
        ticTacToe.play(1, 2);
        ticTacToe.play(2, 2);
        String winner = ticTacToe.play(1, 3);
        assertThat(winner, is("X is the winner"));
    }

    /**
     *  X - -
     *  O X -
     *  O - X
     */
    @Test
    public void whenPlayAndWholeDiagonalThenWinner() {
        ticTacToe.play(1, 1);
        ticTacToe.play(1, 2);
        ticTacToe.play(2, 2);
        ticTacToe.play(3, 1);
        String winner = ticTacToe.play(3, 3);
        assertThat(winner, is("X is the winner"));
    }
    /**
     *  X O X
     *  X X O
     *  O X O
     */
    @Test
    public void whenAllBoxOccupiedAndNoWinnerThenReturnDraw() {
        ticTacToe.play(1, 1); //X
        ticTacToe.play(1, 2); // O
        ticTacToe.play(2, 1); // X
        ticTacToe.play(3, 1); // O
        ticTacToe.play(2, 2); //X
        ticTacToe.play(2, 3); // O
        ticTacToe.play(3, 2); // X
        ticTacToe.play(3, 3); // O
        String winner = ticTacToe.play(1, 3); // O
        assertThat(winner, is("it's a draw"));
    }
}
