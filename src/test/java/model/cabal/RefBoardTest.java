package model.cabal;

import org.junit.jupiter.api.Test;

class RefBoardTest {

    @Test
    void printStandardBoard() {
        var board = new RefBoard(RefBoard.stdBoard);
        System.out.println(RefBoard.stdBoard);

        System.out.println(board.get(E_PileID.BUILDSTACK_2).getTopCard());

    }







}