package View;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Model.Direction;

public class MainFrame {
    private JFrame gameFrame;
    private GridPanel gridLayer;
    private JPanel menuPanel;
    private int n;

    public MainFrame(int n)
    {
        this.n = n;
        gameFrame = new JFrame("Gold Miner");
        gameFrame.setSize(900,900);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
    }

    public void initializeTiles(char[][] terrain, Direction front)
    {
        gridLayer = new GridPanel(n);
        gridLayer.miner(front, 0,0);
        for(int x = 0; x < n; x++)
            for(int y = 0; y < n; y++)
            {
                gridLayer.addTile(terrain[x][y], x, y);
                gridLayer.addTile('D', x, y);
            }
        System.out.println(front);
        gameFrame.add(gridLayer);
        gameFrame.validate();
    }
    
}
