package control;

import java.util.Scanner;

/**
Made this class to asure we always use the same scanner object.
 */
public class ScanSingleton {
    private static Scanner sc = null;

    public static Scanner  getScanner(){
        if(sc == null){
            sc = new Scanner(System.in);
        }
        return sc;
    }

}
