import org.junit.Test;
import static org.junit.Assert.*;


/** This class outputs all palindromes in the words file in the current directory. */
public class TestPalindrome {

    public static Palindrome p = new Palindrome();
    public static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testPalindrome() {
        assertEquals(p.isPalindrome(""), true);
        assertEquals(p.isPalindrome("1"), true);

        assertEquals(p.isPalindrome("acbbca"), true);
        assertEquals(p.isPalindrome("zippiz"), true);
        assertEquals(p.isPalindrome("cbcbc"), true);

        assertEquals(p.isPalindrome("abc"), false);
        assertEquals(p.isPalindrome("xyz"), false);
        assertEquals(p.isPalindrome("zipper"), false);
    }

    @Test
    public void testPalindromeOffByOne() {
        assertEquals(p.isPalindrome("", offByOne), true);
        assertEquals(p.isPalindrome("1", offByOne), true);

        assertEquals(p.isPalindrome("acbcdb", offByOne), true);
        assertEquals(p.isPalindrome("cipqjd", offByOne), true);
        assertEquals(p.isPalindrome("cbccd", offByOne), true);
        assertEquals(p.isPalindrome("flake", offByOne), true);

        assertEquals(p.isPalindrome("abc", offByOne), false);
        assertEquals(p.isPalindrome("xyz", offByOne), false);
        assertEquals(p.isPalindrome("zipper", offByOne), false);
    }

}
