package View;
import javax.swing.JFrame;


public class MainFrame extends JFrame{
    private int n;
    private static final int TILE_SIZE = 64;


    public MainFrame(int n)
    {
        this.n = n;
        this.setTitle("Gold Miner");
        this.setSize(n*TILE_SIZE+200, n*TILE_SIZE+30);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
