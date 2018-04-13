package synthesizer;

//Make sure this class is public
public class GuitarString {

    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        buffer = new ArrayRingBuffer<>((int)(Math.round(SR / frequency)));
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        for(int i = 0; i < buffer.fillCount(); i++) {
            buffer.dequeue();
        }
        for(int i = 0; i < buffer.capacity(); i++) {
            double r = Math.random() - 0.5;
            buffer.enqueue(r);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm. 
     */
    public void tic() {
        double a = changeToZero(buffer.dequeue());
        double b = changeToZero(buffer.peek());
        double r = DECAY * 0.5 * (a + b);
        buffer.enqueue(r);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return changeToZero(buffer.peek());
    }

    /* If return null, change it to 0. */
    public double changeToZero(Double n) {
        if(n == null) {
            return 0;
        }
        return n;
    }

    /* Iterate the buffer. */
    public void iteratePrint() {
        for (double i: buffer) {
            System.out.println(i);
        }
    }

}
