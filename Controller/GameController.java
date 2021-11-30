package Controller;
import java.util.ArrayList;

import Model.*;
import View.GridPanel;
import View.InputSizeFrame;
import View.MainFrame;
import View.MenuFrame;


public class GameController {
    private Grid environment;
    private SmartMiner smartMode;
    private RandomMiner randomMode;
    private MainFrame mainLayer;
    private GridPanel gridLayer;
    private MenuFrame menuLayer;  
    private char mode;
    private ArrayList<Action> actions;
    private int n;
    private InputSizeFrame inputFrame;
    private String minerMessage;

    public GameController()
    {
        inputFrame = new InputSizeFrame(this);
    }

    public void initializeGame(char chosenMode)
    {
        this.mode = chosenMode;
        menuLayer.setVisible(false);
        mainLayer = new MainFrame(n);
        Thread gameThread = new Thread(updateView());
        
        if(mode == 'S')
        {
            smartMode = new SmartMiner(environment);
            gridLayer = new GridPanel(n, environment.getMap(), smartMode.getFront());
            minerMessage = smartMode.startSearch();
            actions = smartMode.getStates();

            mainLayer.add(gridLayer);
            mainLayer.revalidate();
        }
        else
        {
            randomMode = new RandomMiner(environment);
            gridLayer = new GridPanel(n, environment.getMap(), randomMode.getFront());
            minerMessage = randomMode.startSearch();
            actions = randomMode.getStates();
            
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
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(actions.get(i).didScan())
                    {
                        gridLayer.updateMiner('S',actions.get(i).getFront(), actions.get(i).getCol(), actions.get(i).getRow());
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                        e.printStackTrace();
                        }
                    }
                }
                gridLayer.showMessage(minerMessage);
                mainLayer.revalidate();
            }
        };
    }

    public void setGame(int inputN)
    {
        inputFrame.setVisible(false);
        environment = new Grid(inputN, true);
        this.n = environment.getSize();
        menuLayer = new MenuFrame(n, this);

    }
    public static void main(String[] args) {
        GameController myGame = new GameController();
    }
    
}
