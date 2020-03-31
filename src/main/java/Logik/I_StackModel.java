package Logik;

/**
 *
 */
public interface I_StackModel {

    I_CardModel getFirst();
    I_CardModel getCardAt(int position);
    I_CardModel getLast();
    boolean canMoveTo(I_CardModel cardModel);
    boolean moveTo(I_StackModel stackModel);
    I_StackModel splitAt(int position);

}
