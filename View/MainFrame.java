package View;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Model.Direction;

public class MainFrame {
    private JFrame gameFrame;
    private GridPanel gridLayer;
    private JPanel menuPanel;
    private int n;

    public MainFrame(int n, char[][]terrain, Direction front)
    {
        this.n = n;
        gameFrame = new JFrame("Gold Miner");
        gameFrame.setSize(900,900);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);

        gridLayer = new GridPanel(n);
        gridLayer.initializeTiles(terrain, front);

        gameFrame.add(gridLayer);
        gameFrame.validate();
    }
}
