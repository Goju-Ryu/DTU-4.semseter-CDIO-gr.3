package model.cabal;

/**
 *
 * This is the enumerator for which card pile we have.
 * The cards in the cabal can only be moved from certain piles, and that wont change.
 * So, these piles can be explained in an enumerator.
 *
 */
public enum E_PileID {

    TURNPILE(false,"The turned card pile"),
    HEARTSACEPILE(false,"The ace pile for Hearts"),
    DIAMONDACEPILE(false,"The ace pile for Diamond"),
    SPADESACEPILE(false,"The ace pile for Spades"),
    CLUBSACEPILE(false,"The ace pile for Clubs"),
    BUILDSTACK1(true,"The first build stack"),
    BUILDSTACK2(true,"The second build stack"),
    BUILDSTACK3(true,"The third build stack"),
    BUILDSTACK4(true,"The fourth build stack"),
    BUILDSTACK5(true,"The fifth build stack"),
    BUILDSTACK6(true,"The sixth build stack"),
    BUILDSTACK7(true,"The seventh build stack");

    //Is the pile a build stack or not
    private final boolean isBuildStack;

    //A description of the pile
    private final String pileIDText;

    //Constructor (Needs to be private)
    private E_PileID(boolean isBuildStack, String pileIDText) {
        this.isBuildStack = isBuildStack;
        this.pileIDText = pileIDText;
    }

//---------------------------------Getters------------------------------------------------------------------------------

    public boolean isBuildStack() {
        return isBuildStack;
    }

    public String getPileIDText() {
        return pileIDText;
    }
}