import java.util.Random;
import java.awt.Point;
import java.util.ArrayList;

public class Grid {
    private char[][] grid;
    private int n;

    private Point gold;
    private ArrayList<Point> pits;
    private ArrayList<Point> beacons;

    private int nGoldLeft, nPitsLeft, nBeaconsLeft;

    public Grid (int size, boolean randomMap) {
        this.n = size;
        grid = new char[n][n];

        pits = new ArrayList<Point>();
        beacons = new ArrayList<Point>();

        nGoldLeft = 1;
        nPitsLeft = (int)(n * 0.25);
        nBeaconsLeft = (int)(n * 0.1);
        if (nBeaconsLeft < 1)
            nBeaconsLeft = 1;

        if (randomMap)
            randomizeMap();
    }

    public void addPit(int x, int y) {
        if (nPitsLeft > 0) {
            grid[y][x] = 'P';
            pits.add(new Point(x,y));
            nPitsLeft--;
        }
    }

    public void addBeacon(int x, int y) {
        if (nBeaconsLeft > 0) {
            grid[y][x] = 'B';
            beacons.add(new Point(x,y));
            nBeaconsLeft--;
        }
    }

    public void addGold(int x, int y) {
        if (nGoldLeft > 0) {
            grid[y][x] = 'G';
            gold = new Point(x,y);
            nGoldLeft--;
        }
    }

    public int getSize() { return n; }

    public Point getGold() { return gold; }

    public ArrayList<Point> getPits() { return pits; }

    public ArrayList<Point> getBeacons() { return beacons; }

    public boolean tileIsEmpty(int x, int y) {
        try {
            return (grid[y][x] == 0);
        } catch (ArrayIndexOutOfBoundsException e) {
            return true;
        }
    }

    public char getTerrain(int x, int y) { return grid[x][y]; }

    private void randomizeMap () {
        Random rand = new Random();

        // Initialize location of miner
        grid[0][0] = 'M';

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
            } while (!tileIsEmpty((goldX+bX), (goldY+bY)) || !tileIsInBounds((goldX+bX), (goldY+bY)));

            addBeacon(goldX+bX, goldY+bY);
        }

        // Initialize location of pits
        while (nPitsLeft != 0) {
            int pitX, pitY;
            do {
                pitX = rand.nextInt(n);
                pitY = rand.nextInt(n);
            } while (!tileIsEmpty(pitX, pitY));

            addPit(pitX, pitY);
        }
    }

    public boolean tileIsInBounds(int x, int y) {
        return ((0 <= x && x < n) && (0 <= y && y < n));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        
        Grid otherGrid = (Grid) obj;
        return true; // TODO
        /* EDIT NOTE: need to fix this pa, maybe compare other grid's miner's location and front w/ this grid's location and front? */
    }

    /* EDIT NOTE: following 3 funcs are for debugging onli */
    public void printGrid() {
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                if (grid[x][y] == 0)
                    System.out.print("·");
                else
                    System.out.print(grid[x][y]);
            }
            System.out.print("\n");
        }
    }
}
