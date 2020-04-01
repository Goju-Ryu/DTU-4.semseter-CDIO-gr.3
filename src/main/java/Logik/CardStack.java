package Logik;

import java.util.List;

public class CardStack implements I_StackModel {

    private List<I_CardModel> cards;

    public I_CardModel getFirst() {
        return getCardAt(0);
    }

    public I_CardModel getCardAt(int position) {
        return cards.get(position);
    }

    public I_CardModel getLast() {
        return getCardAt(cards.size() - 1);
    }

    public boolean canMoveTo(I_CardModel cardModel) {
        return false;
    }

    public boolean moveTo(I_StackModel stackModel) {
        return false;
    }

    public I_StackModel splitAt(int position) {
        return null;
    }
}
