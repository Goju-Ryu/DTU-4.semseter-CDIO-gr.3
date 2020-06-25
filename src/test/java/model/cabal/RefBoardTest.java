package model.cabal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefBoardTest {

    @Test
    void printStandardBoard() {
        var board = new RefBoard(RefBoard.stdBoard);
        System.out.println(RefBoard.stdBoard);

        System.out.println(board.get(E_PileID.BUILDSTACK2).getTopCard());

    }







}