import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    public static CharacterComparator offByOne = new OffByOne();

    @Test
    public void trueCase() {
        assertEquals(offByOne.equalChars('a', 'b'), true);
        assertEquals(offByOne.equalChars('r','s'), true);
        assertEquals(offByOne.equalChars('y', 'z'), true);
        assertEquals(offByOne.equalChars('b', 'a'), true);
    }

    @Test
    public void falseCase() {
        assertEquals(offByOne.equalChars('b', 'd'), false);
        assertEquals(offByOne.equalChars('c', 'c'), false);
        assertEquals(offByOne.equalChars('z', 'a'), false);
    }

}
