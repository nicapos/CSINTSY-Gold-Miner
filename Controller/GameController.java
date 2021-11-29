package Controller;
import java.util.ArrayList;

import Model.*;
import View.GridPanel;
import View.InputSizeFrame;
import View.MainFrame;
import View.MenuPanel;

public class GameController {
    private Grid environment;
    private SmartMiner smartMode;
    private RandomMiner randomMode;
    private MainFrame mainLayer;
    private GridPanel gridLayer;
    private MenuPanel menuLayer;  
    private char mode;
    private ArrayList<Action> actions;
    private int n;
    private InputSizeFrame inputFrame;

    public GameController()
    {
        inputFrame = new InputSizeFrame(this);
        

    }

    public void initializeGame(char mode)
    {
        this.mode = mode;
        menuLayer.setVisible(false);
        Thread gameThread = new Thread(updateView());
        
        if(mode == 'S')
        {
            smartMode = new SmartMiner(environment);
            smartMode.startSearch();
            actions = smartMode.getStates();
            gridLayer = new GridPanel(n, environment.getMap(), smartMode.getFront());

            mainLayer.add(gridLayer);
            mainLayer.revalidate();
        }
        else
        {
            randomMode = new RandomMiner(environment);
            randomMode.startSearch();
            actions = randomMode.getStates();
            gridLayer = new GridPanel(n, environment.getMap(), randomMode.getFront());
            
            mainLayer.add(gridLayer);
            mainLayer.revalidate();
        }
        gameThread.start(); 
    }

    public Thread updateView()
    {   
        return new Thread(){
            public void run()
            {
                for(int i = 0; i < actions.size()-1; i++)
                {
                    gridLayer.updateDash(actions.get(i));
                    gridLayer.updateMiner('M',actions.get(i).getFront(), actions.get(i).getCol(), actions.get(i).getRow());
                    mainLayer.revalidate();
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(actions.get(i).didScan())
                    {
                        gridLayer.updateMiner('S',actions.get(i).getFront(), actions.get(i).getCol(), actions.get(i).getRow());
                        mainLayer.revalidate();
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                        e.printStackTrace();
                        }
                    }
                }
            }
        };
    }

    public void setGame(int inputN)
    {
        inputFrame.setVisible(false);
        this.n = inputN;
        environment = new Grid(n, true);

        mainLayer = new MainFrame(n);
        menuLayer = new MenuPanel(n, this);

        mainLayer.add(menuLayer);
        mainLayer.revalidate();
    }
    public static void main(String[] args) {
        GameController myGame = new GameController();
    }
    
}
