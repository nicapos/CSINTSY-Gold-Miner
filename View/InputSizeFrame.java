package View;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.GameController;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

public class InputSizeFrame extends JFrame {

    private JPanel inputPanel;
    private JTextField inputText;
    private JButton submit;
    private JLabel header;

    public InputSizeFrame(GameController mainGame)
    {
        this.setTitle("Gold Miner");
        this.setSize(650, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBackground(Color.darkGray);
        
        inputText = new JTextField(1);
        inputText.setBounds(252, 252, 32, 32);
        inputText.setHorizontalAlignment(JTextField.CENTER);
        inputText.setFont(new Font("SansSerif", Font.BOLD, 20));
        inputPanel.add(inputText);

        submit = new JButton("Submit");
        submit.setBounds(284, 252, 96, 32);
        submit.addMouseListener(gMouseAdapter(submit, mainGame));
        inputPanel.add(submit);

        header = new JLabel(new ImageIcon("img/BoardSize.png"));
        header.setBounds(1*64, 1*64, 500, 150);
        inputPanel.add(header);

        this.add(inputPanel);
        this.validate();
    }

    private MouseAdapter gMouseAdapter(JButton button, GameController mainGame)
    {
        return new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
                if(!inputText.getText().isEmpty())
                    mainGame.setGame(Integer.parseInt(inputText.getText()));
            }
        };
    }
}
