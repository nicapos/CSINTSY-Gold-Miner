package Controller;
import java.util.ArrayList;

import Model.*;
import View.Dashboard;
import View.GridPanel;
import View.MainFrame;
import View.MenuPanel;

public class GameController {
    private Grid environment;
    private SmartMiner smartMode;
    private RandomMiner randomMode;
    private MainFrame mainLayer;
    private GridPanel gridLayer;
    private MenuPanel menuLayer;  
    private Dashboard gameDash;
    private char mode;
    private ArrayList<Action> actions;
    private int n;

    public GameController(int inputN)
    {
        this.n = inputN;
        environment = new Grid(n, true);

        mainLayer = new MainFrame(n);
        menuLayer = new MenuPanel(n, this);
        gameDash = new Dashboard(n);

        mainLayer.add(menuLayer);
        
    }

    public void initializeGame(char mode)
    {
        this.mode = mode;
        menuLayer.setVisible(false);
        Thread gameThread = new Thread(updateView());
        //mainLayer.add(gameDash); no dashboard yet
        if(mode == 'S')
        {
            smartMode = new SmartMiner(environment);
            smartMode.startSearch();
            actions = smartMode.getStates();
            gridLayer = new GridPanel(n, environment.getMap(), smartMode.getFront());

            mainLayer.add(gridLayer);
            mainLayer.validate();
        }
        else
        {
            randomMode = new RandomMiner(environment);
            randomMode.startSearch();
            actions = randomMode.getStates();
            gridLayer = new GridPanel(n, environment.getMap(), randomMode.getFront());
            
            mainLayer.add(gridLayer);
            mainLayer.validate();
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
                    gameDash.updateDash(actions.get(i));
                    gridLayer.updateMiner('M',actions.get(i).getFront(), actions.get(i).getCol(), actions.get(i).getRow());
                    mainLayer.validate();
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(actions.get(i).didScan())
                    {
                        gridLayer.updateMiner('S',actions.get(i).getFront(), actions.get(i).getCol(), actions.get(i).getRow());
                        mainLayer.validate();
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

    public static void main(String[] args) {
        GameController myGame = new GameController(8);
    }
    
}
