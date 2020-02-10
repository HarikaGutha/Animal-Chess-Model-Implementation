package stacs.arcade.reversi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static stacs.arcade.reversi.ReversiModel.PlayerColour.WHITE;
import static stacs.arcade.reversi.ReversiModel.PlayerColour.BLACK;

class ReversiModelAdditionalTest {
    private ReversiModel model = null;

    @BeforeEach
    void setUp() {
        this.model = new ReversiModel();
    }

    @Test
    void getAt() throws IllegalMoveException {
        this.model.makeMove(BLACK, 3, 3);
        this.model.makeMove(WHITE, 3, 4);
        this.model.makeMove(BLACK, 4, 3);
        this.model.makeMove(WHITE, 4, 4);
        assertEquals(this.model.getAt(4, 3), BLACK);
    }

    @Test
    void makeMove() {
        assertThrows(IllegalMoveException.class, () -> this.model.makeMove(BLACK, -1, 9));
    }

    @Test
    void getNoBlackStones() throws IllegalMoveException {
        this.model.makeMove(BLACK, 3, 3);
        this.model.makeMove(WHITE, 3, 4);
        this.model.makeMove(BLACK, 4, 3);
        this.model.makeMove(WHITE, 4, 4);
        // (4,4) flips to BLACK
        this.model.makeMove(BLACK, 4, 5);
        assertEquals(this.model.getNoBlackStones(), 4);
    }

    @Test
    void getNoWhiteStones() throws IllegalMoveException {
        this.model.makeMove(BLACK, 3, 3);
        this.model.makeMove(WHITE, 3, 4);
        this.model.makeMove(BLACK, 4, 3);
        this.model.makeMove(WHITE, 4, 4);
        //(4,4) flips to BLACK
        this.model.makeMove(BLACK, 4, 5);
        //(4,5) flips to WHITE
        this.model.makeMove(WHITE, 5, 6);
        assertEquals(this.model.getNoWhiteStones(), 3);

    }

    @Test
    void checkCapturingMovesAtTopLeftCornerOfTheBoard() throws IllegalMoveException {
        this.model.makeMove(BLACK, 3, 3);
        this.model.makeMove(WHITE, 3, 4);
        this.model.makeMove(BLACK, 4, 3);
        this.model.makeMove(WHITE, 4, 4);
        this.model.makeMove(BLACK, 2, 2);
        this.model.makeMove(WHITE, 1, 1);
        this.model.makeMove(BLACK, 0, 0);
        assertEquals(this.model.getAt(1, 1), BLACK);
    }

    @Test
    void checkCapturingMovesAtLeftSideOfTheBoard() throws IllegalMoveException {
        this.model.makeMove(BLACK, 3, 3);
        this.model.makeMove(WHITE, 3, 4);
        this.model.makeMove(BLACK, 4, 3);
        this.model.makeMove(WHITE, 4, 4);
        this.model.makeMove(BLACK, 3, 2);
        this.model.makeMove(WHITE, 3, 1);
        this.model.makeMove(BLACK, 3, 0);
        assertEquals(this.model.getAt(3, 1), BLACK);
        //this.model.makeMove(BLACK,3,5);
        //this.model.makeMove(WHITE,2,6);
        //assertEquals(this.model.getAt(3,5), WHITE);
    }

    @Test
    void checkCapturingMovesAtBottomLeftCornerOfTheBoard() throws IllegalMoveException {
        this.model.makeMove(BLACK, 3, 3);
        this.model.makeMove(WHITE, 3, 4);
        this.model.makeMove(BLACK, 4, 3);
        this.model.makeMove(WHITE, 4, 4);
        this.model.makeMove(BLACK, 5, 2);
        this.model.makeMove(WHITE, 6, 1);
        this.model.makeMove(BLACK, 7, 0);
        assertEquals(this.model.getAt(6, 1), BLACK);
    }

    @Test
    void checkCapturingMovesAtTopOfTheBoard() throws IllegalMoveException {
        this.model.makeMove(BLACK, 3, 3);
        this.model.makeMove(WHITE, 3, 4);
        this.model.makeMove(BLACK, 4, 3);
        this.model.makeMove(WHITE, 4, 4);
        this.model.makeMove(BLACK, 2, 3);
        this.model.makeMove(WHITE, 1, 3);
        this.model.makeMove(BLACK, 0, 3);
        assertEquals(this.model.getAt(1, 3), BLACK);
    }

    @Test
    void checkCapturingMovesAtTopRightCornerOfTheBoard() throws IllegalMoveException {
        this.model.makeMove(BLACK, 3, 3);
        this.model.makeMove(WHITE, 3, 4);
        this.model.makeMove(BLACK, 4, 3);
        this.model.makeMove(WHITE, 4, 4);
        this.model.makeMove(BLACK, 2, 5);
        this.model.makeMove(WHITE, 1, 6);
        this.model.makeMove(BLACK, 0, 7);
        assertEquals(this.model.getAt(1, 6), BLACK);
    }

    @Test
    void checkCapturingMovesAtRightSideOfTheBoard() throws IllegalMoveException {
        this.model.makeMove(BLACK, 3, 3);
        this.model.makeMove(WHITE, 3, 4);
        this.model.makeMove(BLACK, 4, 3);
        this.model.makeMove(WHITE, 4, 4);
        this.model.makeMove(BLACK, 4, 5);
        this.model.makeMove(WHITE, 5, 6);
        this.model.makeMove(BLACK, 6, 7);
        assertEquals(this.model.getAt(5, 6), BLACK);
    }
}
