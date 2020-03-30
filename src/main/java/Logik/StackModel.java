package Logik;

/**
 *
 */
public interface StackModel {

    CardModel getFirst();
    CardModel getCardAt(int position);
    CardModel getLast();
    boolean canMoveTo(CardModel cardModel);
    boolean moveTo(StackModel stackModel);
    StackModel splitAt(int position);

}
