package Logik;

public class CardStack implements I_StackModel {
    public I_CardModel getFirst() {
        return null;
    }

    public I_CardModel getCardAt(int position) {
        return null;
    }

    public I_CardModel getLast() {
        return null;
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
