package synthesizer;

public class TestGuitarString {

	// Plays A- note:
	public static void testPluckTheAString() {
        double CONCERT_A = 440.0;
        GuitarString aString = new GuitarString(CONCERT_A);
        aString.pluck();
        for (int i = 0; i < 50000; i += 1) {
            StdAudio.play(aString.sample());
            aString.tic();
        }
    }

    public static void testTteration() {
    	double CONCERT_A = 440.0;
        GuitarString aString = new GuitarString(CONCERT_A);
        aString.pluck();
        aString.iteratePrint();
    }

    /** Calls tests for GuitarString. */
    public static void main(String[] args) {
    	testPluckTheAString();

    }
} 
