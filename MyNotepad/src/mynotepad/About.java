package mynotepad;

import java.awt.Image;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;

public class About extends JFrame implements ActionListener{
    
    About()
    {
        setBounds(400, 100, 600, 500);
        setLayout(null);
        
        ImageIcon notepadIcon = new ImageIcon(ClassLoader.getSystemResource("MyNotepad/Icons/logo.png"));
        Image icon  = notepadIcon.getImage().getScaledInstance(300, 200, Image.SCALE_DEFAULT);
        ImageIcon iconFinal = new ImageIcon(icon);
        JLabel headerIcon = new JLabel(iconFinal);
        headerIcon.setBounds(100, 0, 400, 250);
        add(headerIcon);
        
        JLabel text = new JLabel("<html><div style='text-align: center;'>This is MyNotepad<br>It is a clone of Windows Notepad<br>Version: 0.0.1</div></html>");
        text.setBounds(170, 200, 250, 200);
        text.setFont(new Font("SERIF", Font.PLAIN, 18));
        add(text);
        
        JButton b1 = new JButton("Ok");
        b1.setBounds(240, 400, 100, 25);
        b1.addActionListener(this);
        add(b1);
        
        setVisible(true);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        this.setVisible(false);
    }
}
