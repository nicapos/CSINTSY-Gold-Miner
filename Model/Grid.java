package Model;
import java.util.Random;

public class Grid {
    private char[][] grid;
    private int n;

    private int nGoldLeft, nPitsLeft, nBeaconsLeft;

    public Grid (int size, boolean randomMap) {
        if (size < 8)
            this.n = 8;
        else if (size > 64)
            this.n = 64;
        else
            this.n = size;

        grid = new char[n][n];

        nGoldLeft = 1;
        nPitsLeft = (int)(n * 0.25);
        nBeaconsLeft = (int)(n * 0.1);
        if (nBeaconsLeft < 1)
            nBeaconsLeft = 1;

        if (randomMap)
            randomizeMap();
    }

    public void addPit(int col, int row) {
        if (nPitsLeft > 0) {
            grid[row][col] = 'P';
            nPitsLeft--;
        }
    }

    public void addBeacon(int col, int row) {
        if (nBeaconsLeft > 0) {
            grid[row][col] = 'B';
            nBeaconsLeft--;
        }
    }

    public void addGold(int col, int row) {
        if (nGoldLeft > 0) {
            grid[row][col] = 'G';
            nGoldLeft--;
        }
    }

    public int getSize() { return n; }

    public boolean tileIsEmpty(int col, int row) {
        try {
            return (grid[row][col] == 0);
        } catch (ArrayIndexOutOfBoundsException e) {
            return true;
        }
    }

    public char getTerrain(int col, int row) { 
        if (tileIsEmpty(col, row)) return 0;
        return grid[row][col];
    }

    public char[][] getMap()
    {
        return this.grid;
    }

    private void randomizeMap () {
        Random rand = new Random();

        // Initialize location of pot of gold
        int goldX, goldY;
        do {
            goldX = rand.nextInt(n);
            goldY = rand.nextInt(n);
        } while (goldX == 0 && goldY == 0);
        addGold(goldX, goldY);

        // Initialize location of beacons
        int m = rand.nextInt(n-1) + 1;

        while (nBeaconsLeft != 0) {
            int rY, rX, bX, bY;
            do {
                rY = rand.nextInt(m+1);
                rX = m - rY;
                bX = rand.nextBoolean() ? rX : -rX;
                bY = rand.nextBoolean() ? rY : -rY;
            } while (!tileIsEmpty((goldX+bX), (goldY+bY)) || !tileIsInBounds((goldX+bX), (goldY+bY)) || (goldX+bX == 0 && goldY+bY == 0));

            addBeacon(goldX+bX, goldY+bY);
        }

        // Initialize location of pits
        while (nPitsLeft != 0) {
            int pitX, pitY;
            do {
                pitX = rand.nextInt(n);
                pitY = rand.nextInt(n);
            } while (!tileIsEmpty(pitX, pitY) || (pitX == 0 && pitY == 0));

            addPit(pitX, pitY);
        }
    }

    public boolean tileIsInBounds(int row, int col) {
        return ((0 <= row && row < n) && (0 <= col && col < n));
    }

    public void printGrid() { // 
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (grid[row][col] == 0)
                    System.out.print("·");
                else
                    System.out.print(grid[row][col]);
            }
            System.out.print("\n");
        }
    }

    public void printGrid(Miner miner) { // 
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (miner.get_col() == col && miner.get_row() == row)
                    System.out.print('M'); 
                else if (grid[row][col] == 0)
                    System.out.print("·");
                else
                    System.out.print(grid[row][col]);
            }
            System.out.print("\n");
        }
    }

    public void printGrid(Node step) { // 
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (step.getCol() == col && step.getRow() == row)
                    System.out.print('M'); 
                else if (grid[row][col] == 0)
                    System.out.print("·");
                else
                    System.out.print(grid[row][col]);
            }
            System.out.print("\n");
        }
    }
}
