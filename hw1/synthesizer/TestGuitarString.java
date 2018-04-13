package synthesizer;


public class TestGuitarString {

    /** Calls tests for GuitarString. */
    public static void main(String[] args) {
        double CONCERT_A = 440.0;
        GuitarString aString = new GuitarString(CONCERT_A);
        aString.pluck();
        aString.iteratePrint();

    }
} 
