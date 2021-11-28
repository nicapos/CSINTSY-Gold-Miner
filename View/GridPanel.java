package View;
import javax.swing.*;

import Controller.GameController;
import Model.Direction;

public class GridPanel extends JPanel {

    private final int TILE_SIZE = 64;
    private JLabel tiles[][];
    private JLabel miner;
    private JLabel gridBg;
    private int n;

    public GridPanel(int n, char[][] terrain, Direction front, GameController mainGame)
    {   
        this.n = n;
        this.gridBg = new JLabel();
        this.setSize(n*TILE_SIZE+200, n*TILE_SIZE+30);
        this.setLayout(null);
        tiles = new JLabel[n][n];
        miner = new JLabel();

        initializeTiles(terrain, front);

    }
    
    private void initializeTiles(char[][] terrain, Direction front)
    {
        this.miner(front, 0,0);
        for(int x = 0; x < n; x++)
            for(int y = 0; y < n; y++)
            {
                this.addTile(terrain[y][x], x, y);

                gridBg = new JLabel(new ImageIcon("img/D.png"));
                gridBg.setBounds(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                this.add(gridBg);
            }
    }

    public void addTile(char type, int x, int y)
    {
        try {
            tiles[x][y] = new JLabel(new ImageIcon("img/"+type+".png"));
            tiles[x][y].setBounds(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
            this.add(tiles[x][y]);
        } catch (ArrayIndexOutOfBoundsException E) {
            System.out.println("ERROR: x or y out of range.");
        }
    }

    public void miner(Direction front, int x, int y)
    {
        try {
            miner = new JLabel(new ImageIcon("img/Miner/"+front+".png"));
            miner.setBounds(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
            this.add(miner);
        } catch (ArrayIndexOutOfBoundsException E) {
            System.out.println("ERROR: x or y out of range.");
        }
    }

    public void updateMiner(char action, Direction front, int x, int y)
    {
        if(action == 'M')
            miner.setIcon(new ImageIcon("img/Miner/"+front+".png"));
        else if(action == 'S')
            miner.setIcon(new ImageIcon("img/Miner/Scan"+front+".png"));
        miner.setSize(TILE_SIZE, TILE_SIZE);
        miner.setLocation(x*TILE_SIZE, y*TILE_SIZE);
    }
}
