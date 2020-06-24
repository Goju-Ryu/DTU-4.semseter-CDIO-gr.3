package history;

import com.google.errorprone.annotations.Immutable;
import model.cabal.E_PileID;
import static model.cabal.E_PileID.*;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.I_CardModel;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

@Immutable
public class State extends EnumMap<E_PileID, List<I_CardModel>> implements I_GameState {

    State() {
        super(E_PileID.class);
    }

    State(I_GameState state) {
        super(state);
    }

    public State(Map<E_PileID, List<I_CardModel>> state) {
        super(state.isEmpty() ? new EnumMap<>(E_PileID.class) : state);
    }

    @Override
    public State clone() {
        return (State) super.clone();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("State {\n");
        for(var pileID : E_PileID.values()){
            builder.append("\t" + pileID.name() + ": " + get(pileID) + "\n");
        }
        builder.append("}");

        return builder.toString();
    }
}
