package control;

public interface I_GameController {
    GameResult startGame(String UiChoice);


    /**
     * This class is meant as a data container for the outcome of a game
     */
    class GameResult {
        private final int rounds;
        private final E_Result result;
        private final Throwable exception;

        /**
         * The standard constructor taking the number of rounds and the result of the game
         *
         * @param rounds number of rounds before the game ended
         * @param result the result of the game
         */
        public GameResult(int rounds, E_Result result) {
            this(rounds, result, null);
        }



        /**
         * A constructor for games that ended exceptionally (outside of expected endings)
         *
         * @param rounds number of rounds before the game ended
         * @param exception The exception that ended the game
         */
        public GameResult(int rounds, Throwable exception) {
            this(rounds, E_Result.EXCEPTION, exception);
        }

        /**
         * The base Constructor which lets one set every variable.
         *
         * @param rounds number of rounds before the game ended
         * @param result the result of the game
         * @param exception The exception that ended the game
         */
        protected GameResult(int rounds, E_Result result, Throwable exception) {
            this.rounds = rounds;
            this.result = result;
            this.exception = exception;
        }

        public int getRounds() {
            return rounds;
        }
        public E_Result getResult() {return result;}
        public Throwable getException() {return exception;}
    }

    enum E_Result {
        WON,
        NO_MOVES,
        ENDLESS,
        EXCEPTION
    }
}
