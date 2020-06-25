package view;

import model.Move;

public interface I_Tui {
    void promptPlayer(Move move);

    void promptPlayer(String msg);
}
