package model.cabal;

/**
 *
 * This is the enumerator for which card pile we have.
 * The cards in the cabal can only be moved from certain piles, and that wont change.
 * So, these piles can be explained in an enumerator.
 *
 */
public enum E_PileID {

    DRAWSTACK(false),
    SUITSTACK_HEARTS(false),
    SUITSTACK_DIAMONDS(false),
    SUITSTACK_SPADES(false),
    SUITSTACK_CLUBS(false),
    BUILDSTACK_1(true),
    BUILDSTACK_2(true),
    BUILDSTACK_3(true),
    BUILDSTACK_4(true),
    BUILDSTACK_5(true),
    BUILDSTACK_6(true),
    BUILDSTACK_7(true);

    //Is the pile a build stack or not
    private final boolean isBuildStack;

    //Constructor
    E_PileID(boolean isBuildStack) {
        this.isBuildStack = isBuildStack;
    }

    public boolean isBuildStack() {
        return isBuildStack;
    }
}
