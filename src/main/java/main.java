import control.GameController;
import control.I_GameController;
import model.cabal.Board;
import model.cabal.E_PileID;
import static model.cabal.E_PileID.*;
import model.cabal.I_BoardModel;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class main {
    public static void main(String[] args) {
        I_GameController Klondike = new GameController();


        Map<String, I_CardModel> usrInput = new HashMap<>();
        usrInput.put(TURNPILE.name(), new Card(E_CardSuit.HEARTS,1));
        usrInput.put(HEARTSACEPILE.name(), new Card());
        usrInput.put(DIAMONDACEPILE.name(), new Card());
        usrInput.put(SPADESACEPILE.name(), new Card());
        usrInput.put(CLUBSACEPILE.name(), new Card());
        usrInput.put(BUILDSTACK1.name(), new Card(E_CardSuit.HEARTS,2));
        usrInput.put(BUILDSTACK2.name(), new Card(E_CardSuit.HEARTS,3));
        usrInput.put(BUILDSTACK3.name(), new Card(E_CardSuit.HEARTS,4));
        usrInput.put(BUILDSTACK4.name(), new Card(E_CardSuit.HEARTS,5));
        usrInput.put(BUILDSTACK5.name(), new Card(E_CardSuit.HEARTS,6));
        usrInput.put(BUILDSTACK6.name(), new Card(E_CardSuit.HEARTS,7));
        usrInput.put(BUILDSTACK7.name(), new Card(E_CardSuit.HEARTS,8));


        I_BoardModel board = new Board(usrInput);

        System.out.println("Stack 7"+board.getPile(E_PileID.BUILDSTACK7));
        System.out.println("Stack 1"+board.getPile(E_PileID.BUILDSTACK1));
        System.out.println("turn pile:"+board.getPile(E_PileID.TURNPILE));


//        Klondike.startGame("ManGUI");
////        Klondike.startGame("sdsldfndslfnds");
//
//        System.out.println("Hello Main World!");
//        //change
    }
}
