package view;

import control.ScanSingleton;
import model.Move;

import java.util.Scanner;

public class Tui implements I_Tui {
    boolean wait;
    Scanner scan;
    public Tui(boolean waitForPrompt) {
        wait = waitForPrompt;
        scan = new Scanner(System.in).useDelimiter("(\\b|\\B)");
    }


    @Override
    public void promptPlayer(Move move) {
        System.out.println("Complete the following move, then press any button to continue:\n\t" + move);
        if (wait)
            scan.next();//ScanSingleton.getScanner().next();
    }

    @Override
    public void promptPlayer(String msg) {
        System.out.println(msg);
        if (wait)
            scan.next();//ScanSingleton.getScanner().next();
    }

}
