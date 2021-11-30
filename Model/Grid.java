package Model;
import java.util.Random;

public class Grid {
    private char[][] grid;
    private int n;

    private int nGoldLeft, nPitsLeft, nBeaconsLeft;
    private int goldRow, goldCol;

    public Grid (int size, boolean randomMap) {
        if (size < 8)
            this.n = 8;
        else if (size > 64)
            this.n = 64;
        else
            this.n = size;

        if (randomMap)
            randomizeMap();
        else
            initializeMap();
    }

    private void initializeMap() {
        grid = new char[n][n];

        nGoldLeft = 1;
        goldCol = -1; 
        goldRow = -1;
        nPitsLeft = (int)(n * 0.25);
        nBeaconsLeft = (int)(n * 0.1);
        if (nBeaconsLeft < 1)
            nBeaconsLeft = 1;
    }

    public boolean addPit(int col, int row) {
        if (nPitsLeft > 0 && tileIsEmpty(col, row)) {
            grid[row][col] = 'P';
            nPitsLeft--;
            System.out.println("Added pit to (" + col + ", " + row + ")");
            return true;
        }
        return false;
    }

    public boolean addBeacon(int col, int row) {
        if (nBeaconsLeft > 0 && tileIsEmpty(col, row)) {
            // beacon must be <n moves away from gold, where n is the grid's size        
            if (nGoldLeft == 1 || (nGoldLeft == 0 && (Math.abs(goldCol-col) + Math.abs(goldRow-row)) < n)) {
                grid[row][col] = 'B';
                nBeaconsLeft--;
                System.out.println("Added beacon to (" + col + ", " + row + ")");
                return true;
            }
        }
        return false;
    }

    public boolean addGold(int col, int row) {
        if (nGoldLeft > 0 && tileIsEmpty(col, row)) {
            grid[row][col] = 'G';
            goldCol = col;
            goldRow = row;
            nGoldLeft--;
            System.out.println("Added gold to (" + col + ", " + row + ")");
            return true;
        }
        return false;
    }

    public void clearTile (int col, int row) {
        char cTerrain = getTerrain(col, row);
        if ( cTerrain != 0 ) {
            grid[row][col] = 0;

            if (cTerrain == 'G') {
                nGoldLeft++;
                goldCol = -1;
                goldRow = -1;
            } else if (cTerrain == 'B')
                nBeaconsLeft++;
            else if (cTerrain == 'P')
                nPitsLeft++;
                 
        }
    }

    public int getSize() { return n; }

    public int getBeaconsLeft() {return nBeaconsLeft;}

    public int getPitsLeft() {return nPitsLeft;}

    public int getGoldLeft(){ return nGoldLeft;}

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
        Boolean createdValidMap;

        do {
            initializeMap();
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

            // check if the gold is surrounded by pits, making the map invalid
            if (   (getTerrain(goldX+1, goldY) == 'P' || !tileIsInBounds(goldX+1, goldY))
                && (getTerrain(goldX-1, goldY) == 'P' || !tileIsInBounds(goldX-1, goldY))
                && (getTerrain(goldX, goldY+1) == 'P' || !tileIsInBounds(goldX, goldY+1))
                && (getTerrain(goldX, goldY-1) == 'P' || !tileIsInBounds(goldX, goldY-1))   ) {
                createdValidMap = false;
                System.out.println("Map is not valid. Regenerating...");
            } else
                createdValidMap = true;
        } while ( !createdValidMap );
    }

    public boolean tileIsInBounds(int col, int row) {
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
