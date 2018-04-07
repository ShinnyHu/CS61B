public class OffByN implements CharacterComparator{

    public int n;

    public OffByN(int n) {
        this.n = n;
    }

    @Override
    public boolean equalChars(char x, char y) {
        if(y - x == n || x - y == n) {
            return true;
        }
        return false;
    }
}
