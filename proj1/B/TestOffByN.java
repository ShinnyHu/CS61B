import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestOffByN {

    public static CharacterComparator offByN = new OffByN(3);

    @Test
    public void trueCase() {
        assertEquals(offByN.equalChars('a', 'd'), true);
        assertEquals(offByN.equalChars('r', 'u'), true);
        assertEquals(offByN.equalChars('w', 'z'), true);
        assertEquals(offByN.equalChars('d', 'a'), true);
    }

    @Test
    public void falseCase() {
        assertEquals(offByN.equalChars('b', 'd'), false);
        assertEquals(offByN.equalChars('c', 'c'), false);
        assertEquals(offByN.equalChars('z', 'a'), false);

    }
}