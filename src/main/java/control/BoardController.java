package control;

import data.I_InputDTO;
import data.InputDTO;
import history.GameHistory;
import history.I_GameHistory;
import history.I_GameState;
import model.GameCardDeck;
import model.Move;
import model.cabal.Board;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.UnendingGameException;
import view.I_Tui;

import java.util.*;

import static model.cabal.E_PileID.*;

/**
* This class is for the individual controlls of each Board,
* so all the things you can do to an individual Board.
*
* the diffrence on this and Game controller, is that this only acts in relation
* to one given state of the game where as GameController can play trough an entire game.
* This controller is one Game controller uses everytime it needs to make
* a move
*/
public class BoardController extends AbstractBoardController {

    public BoardController(String uiChoice, I_Tui tui) {
        this(new InputDTO(uiChoice), tui);
    }

    public BoardController(I_InputDTO inputDTO, I_Tui tui) {
        this.inputDTO = inputDTO;
        var deck = new GameCardDeck();
        ArrayList<I_CardModel> drawCards = new ArrayList<>();

        int drawstackCardCount = 4;
        for(int i = 0; i < drawstackCardCount; i++) {
            I_CardModel drawCard = inputDTO.getUsrInput().get("DRAWSTACK");
            drawCards.add(drawCard);
            tui.promptPlayer("currDrawCard: " + drawCard + "\nPlease turn the next card in the drawstack and prompt ...");
        }


        tui.promptPlayer("Type anything followed by a whitespace char, to confirm " +
                         "continuing on from intializing the drawstack to actualy start the game");

        this.boardModel = new Board(inputDTO.getUsrInput(), deck, drawCards);//, drawCards);
        this.history = new GameHistory(boardModel);
    }





}

