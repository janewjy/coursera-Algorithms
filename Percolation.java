import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // the length of the square grid
    private int N;
    // N*N grid, defalt all closed, false
    private boolean[] grid;
    // virtual top that is connected with the entire top row
    private int top;
    //virtual bottom that is connected with the entire bottom row
    private int bottom;
    // connectivity bwteen sites
    private WeightedQuickUnionUF unionFind;
    // connectivity bwteen sites and track fullness without backwash
    private WeightedQuickUnionUF unionFindIsFull;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N < 1) {
            throw new IllegalArgumentException();
        }
        grid = new boolean[N * N];
        this.N = N;
        top = N * N;
        bottom = N * N + 1;

        unionFind = new WeightedQuickUnionUF(N * N + 2);
        unionFindIsFull = new WeightedQuickUnionUF(N * N + 1);
    }

    // contert 2D index into 1D index
    // i and j are both 1-N, where (1,1) is the upper-left corner
    private int indexConvertor(int i, int j) {
        checkBoundaries(i, j);
        return (i - 1) * N + (j - 1);
    }

    // check if i, j is inside boundaries  [1,N]
    private void checkBoundaries(int i, int j) {
        if (i < 1 || i > N) {
            throw new IndexOutOfBoundsException("row index i out of bounds");
        }
        if (j < 1 || j > N) {
            throw new IndexOutOfBoundsException("row index j out of bounds");
        }

    }

    // is site(i, j) open?
    public boolean isOpen(int i, int j) {
        checkBoundaries(i, j);
        int siteIndex = indexConvertor(i, j);
        return grid[siteIndex];
    }

    // open site(i, j) and connected with its neighbors
    public void open(int i, int j) {
        checkBoundaries(i, j);
        int siteIndex = indexConvertor(i, j);
        if (!grid[siteIndex]) {
            grid[siteIndex] = true;
            if (i == 1) {
                unionFind.union(siteIndex, top);
                unionFindIsFull.union(siteIndex, top);
            }
            if (i == N) {
                unionFind.union(siteIndex, bottom);
            }
            if (j > 1 && isOpen(i, j - 1)) {
                int indexToLeft = indexConvertor(i, j - 1);
                unionFind.union(siteIndex, indexToLeft);
                unionFindIsFull.union(siteIndex, indexToLeft);

            }
            if (j < N && isOpen(i, j + 1)) {
                int indexToRight = indexConvertor(i, j + 1);
                unionFind.union(siteIndex, indexToRight);
                unionFindIsFull.union(siteIndex, indexToRight);

            }
            if (i > 1 && isOpen(i - 1, j)) {
                int indexToUp = indexConvertor(i - 1, j);
                unionFind.union(siteIndex, indexToUp);
                unionFindIsFull.union(siteIndex, indexToUp);

            }
            if (i < N && isOpen(i + 1, j)) {
                int indexToDown = indexConvertor(i + 1, j);
                unionFind.union(siteIndex, indexToDown);
                unionFindIsFull.union(siteIndex, indexToDown);

            }
        }
    }

    // check site(i, j) fullness
    public boolean isFull(int i, int j) {
        checkBoundaries(i, j);
        int siteIndex = indexConvertor(i, j);
        return unionFindIsFull.connected(top, siteIndex);
    }
    // check if the system is percolates
    public boolean percolates() {
        if (N > 1) {
            return unionFind.connected(top, bottom);
        } else {
            return isOpen(N,N);
        }
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(10);
        System.out.println(p.indexConvertor(1, 1));
        p.open(1, 1);
        p.open(1, 2);
        System.out.println(p.isFull(3, 2));

    }

}
