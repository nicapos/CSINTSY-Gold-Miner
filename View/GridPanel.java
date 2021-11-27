package View;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Direction;

public class GridPanel extends JPanel {

    private final int TILE_SIZE = 64;
    private JLabel tiles[][];
    private JLabel miner;
    private JLabel gridBg;
    private int n;

    public GridPanel(int n)
    {   
        this.n = n;
        this.gridBg = new JLabel();
        this.setSize(n*64, n*64);
        this.setLayout(null);
        tiles = new JLabel[n][n];
        miner = new JLabel();
    }
    
    public void initializeTiles(char[][] terrain, Direction front)
    {
        this.miner(front, 0,0);
        for(int x = 0; x < n; x++)
            for(int y = 0; y < n; y++)
            {
                this.addTile(terrain[x][y], x, y);

                gridBg = new JLabel(new ImageIcon("img/D.png"));
                gridBg.setBounds(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                this.add(gridBg);
            }
        System.out.println(front);
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

}
