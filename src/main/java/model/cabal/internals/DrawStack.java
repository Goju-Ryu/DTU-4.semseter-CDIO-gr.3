package model.cabal.internals;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.checkerframework.checker.nullness.compatqual.NonNullType;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class DrawStack extends StackBase implements I_SolitaireStacks  {
    /**
     * This variable describes which cards have been drawn and which haven't
     * every index higher than this value has not yet ben drawn and the lower ones has.
     * This means that the index always points at the card that is able to be drawn out on the board.
     */
    protected int drawIndex;
    //private  Logger log;

    public DrawStack() {
        this(new LinkedList<>());
    }
    public DrawStack(List<I_CardModel> list) {
        super();

        if (list instanceof LinkedList)
            stack = list;
        else
            stack = new LinkedList<>(list);

        drawIndex = -1;

        var name = getClass().getSimpleName();
        //log = Logger.getLogger(name);
//        try {
//            final String programDir = System.getProperty("user.dir");
//            var logFile = new File(programDir + "/log/drawStack/"+log.getName()+".log");
//            logFile.delete();
//            logFile.getParentFile().mkdirs();
////            log.addHandler(new FileHandler(logFile.getAbsolutePath()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

//-----------  Implementation ----------------------------------------------------------------

    @Override
    public Collection<I_CardModel> popSubset(final int index) throws IllegalMoveException {
        //log.info("popSubset("+index+")");
//        log.info(() -> mySupplier.apply(index, drawIndex));
        if (!canMoveFrom()) {
            throw new IllegalMoveException("Can't move cards out of drawStack");
        }

        var returnCards = getSubset(index);

        if (returnCards.size() > 1)
            throw new IllegalMoveException("A drawstack can only pop one card. You tried to pop from "+index);

        if ( !returnCards.isEmpty() && !returnCards.get(0).isFacedUp() )
            throw new IllegalMoveException("Card at this index: "+index+" has not been turned yet");

        stack.removeAll( returnCards ); //remove the card
        drawIndex--; //lower index to point to the new card that can be drawn
//        log.info("popSubset(index:"+index+", drawIndex:"+drawIndex+") --D " + returnCards);
//        log.info(() ->  mySupplier.apply(index, drawIndex) + "result " + returnCards);
        return returnCards;
    }
    @Override
    public List<I_CardModel> getSubset(final int index) {
//        log.info("getSubset("+index+")");
//        log.info(() ->  mySupplier.apply(index, drawIndex));
        var r = List.of(getCard(index));
//        log.info(() ->  mySupplier.apply(index, drawIndex) + "result " + r);
//        log.info("getSubset(index:"+index+", drawIndex:"+drawIndex+") --D " + r);
        return r;
    }
    @Override
    public boolean add(I_CardModel o) {
        stack.add(getSafeDrawIndex(), o);
        return true;
    }


    // Board Game specifics
    @Override
    public boolean canMoveFrom(final int range) {
        if( range > stack.size() ){
            throw new IllegalArgumentException(
                    "Range was larger than the stack size",
                    new IndexOutOfBoundsException("range: " + range + ", but size is only: " + size())
            );
        }
        return getSubset(range).get(0).isFacedUp();
    }
    @Override
    public boolean canMoveTo(@NonNullType Collection<I_CardModel> cards) {
        return false;
    }
    @Override
    public I_CardModel getCard(final int position) {
//        log.info("getCard(index:"+position+", drawIndex:"+drawIndex+")");
//        log.info(() ->  mySupplier.apply(position, drawIndex));
         var r = stack.get((getSafeDrawIndex() + position  ) % size());
//        log.info("getCard(index:"+position+", drawIndex:"+drawIndex+") --D " + r);
//        log.info(() ->  mySupplier.apply(position, drawIndex) + "result " + r);
        return r;
    }

    @Override
    public I_CardModel getTopCard() {
        if (drawIndex < 0)
            return null;
        return stack.get(drawIndex);
    }
    public I_CardModel turnCard() {
        if (isEmpty())
            throw new NoSuchElementException("Can't turn a card in an empty stack");
        drawIndex = (drawIndex + 1) % size();
        return getCard(0);
    }

// --- // --- // --- // --- // --- // --- // ---  DrawStack specific methods  ----------------------------------------------------------

    @NonNullType
    @Override
    public Iterator<I_CardModel> iterator() {
        var startIndex = getSafeDrawIndex();
        var returnable = stack.subList(startIndex, stack.size());
        returnable.addAll(stack.subList(0, startIndex));
        return returnable.iterator();
    }
    private int getSafeDrawIndex() {
        return Math.max(0, drawIndex);
    }

    private BiFunction<Integer, Integer, String> mySupplier = (inp, drw) -> {
//        var trace = Thread.currentThread().getStackTrace();
        StringWriter sw = new StringWriter();
        new Exception().printStackTrace(new PrintWriter(sw));
        String trace = sw.toString();

        return "Method: " + getClass().getEnclosingMethod() + "\n" +
                "\tinput: " + inp + "\n" +
                "\tdrawIndex: " + drw + "\n" +
                "\n" +
                "StackTrace:\n" + trace;

    };
}
