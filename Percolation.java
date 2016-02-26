import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] grid;
    private int N;
    private int top;
    private int bottom;
    private WeightedQuickUnionUF uf;


    public Percolation(int N) {
        if (N < 1) {
            throw new IllegalArgumentException();
        }
        grid = new boolean[N * N];
        this.N = N;
        top = N * N;
        bottom = N * N + 1;

        uf = new WeightedQuickUnionUF(N * N + 2);
    }

    private int indexConvertor(int i, int j) {
        checkBoundaries(i, j);
        return (i - 1) * N + (j - 1);
    }

    private void checkBoundaries(int i, int j) {
        if (i < 1 || i > N) {
            throw new IndexOutOfBoundsException("row index i out of bounds");
        }
        if (j < 1 || j > N) {
            throw new IndexOutOfBoundsException("row index j out of bounds");
        }

    }
    public boolean isOpen(int i, int j) {
        checkBoundaries(i, j);
        int siteIndex = indexConvertor(i, j);
        return grid[siteIndex];
    }

    public void open(int i, int j) {
        checkBoundaries(i, j);
        int siteIndex = indexConvertor(i, j);
        if (!grid[siteIndex]) {
            grid[siteIndex] = true;
            if (i == 1) {
                uf.union(siteIndex, top);
            }
            // if (i == N) {
            //     uf.union(siteIndex, bottom);
            // }
            if (j > 1 && isOpen(i, j - 1)) {
                int indexToLeft = indexConvertor(i, j - 1);
                uf.union(siteIndex, indexToLeft);
            }
            if (j < N && isOpen(i, j + 1)) {
                int indexToRight = indexConvertor(i, j + 1);
                uf.union(siteIndex, indexToRight);
            }
            if (i > 1 && isOpen(i - 1, j)) {
                int indexToUp = indexConvertor(i - 1, j);
                uf.union(siteIndex, indexToUp);
            }
            if (i < N && isOpen(i + 1, j)) {
                int indexToDown = indexConvertor(i + 1, j);
                uf.union(siteIndex, indexToDown);
            }
        }
    }

    public boolean isFull(int i, int j) {
        checkBoundaries(i, j);
        int siteIndex = indexConvertor(i, j);
        return uf.connected(top, siteIndex);
    }

    public boolean percolates() {
        for (int j = 0; j < N; j++) {
            if (isOpen(N, j + 1) && uf.connected(top, (N - 1) * N + j)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(10);
        System.out.println(p.indexConvertor(1, 1));
        p.open(1, 1);
        p.open(1, 2);
        System.out.println(p.isFull(3, 2));

    }

}
