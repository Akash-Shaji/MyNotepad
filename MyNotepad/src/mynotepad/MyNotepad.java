package mynotepad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import javax.swing.undo.*;
import java.io.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;


public class MyNotepad extends JFrame implements ActionListener{
    
    JTextArea area;
    String text;
    UndoManager undoManager;
            
    MyNotepad()
    {
        setTitle("MyNotepad");
        ImageIcon notepadIcon = new ImageIcon(ClassLoader.getSystemResource("MyNotepad/Icons/logo.png"));
        Image icon  = notepadIcon.getImage();
        setIconImage(icon);
        
        undoManager = new UndoManager();
        JMenuBar menubar = new JMenuBar();
        
        JMenu file = new JMenu("File");
        file.setFont(new Font("SERIF", Font.PLAIN, 12));
        
        JMenuItem newdoc = new JMenuItem("New");
        newdoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newdoc.addActionListener(this);
        file.add(newdoc);
        
        JMenuItem open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        open.addActionListener(this);
        file.add(open);
        
        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.addActionListener(this);
        file.add(save);
        
        JMenuItem print = new JMenuItem("Print");
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        print.addActionListener(this);
        file.add(print);
        
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(this);
        file.add(exit);
        

        menubar.add(file);
        
        
        JMenu edit = new JMenu("Edit");
        edit.setFont(new Font("SERIF", Font.PLAIN, 12));
        
        JMenuItem copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copy.addActionListener(this);
        edit.add(copy);
        
        JMenuItem paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        paste.addActionListener(this);
        edit.add(paste);
        
        JMenuItem cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        cut.addActionListener(this);
        edit.add(cut);
        
        JMenuItem selectAll = new JMenuItem("Select All");
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        selectAll.addActionListener(this);
        edit.add(selectAll);
        
        JMenuItem undo = new JMenuItem("Undo");
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        undo.addActionListener(this);
        edit.add(undo);
        

        menubar.add(edit);
        
        setJMenuBar(menubar);
        
        area = new JTextArea();
        add(area);
        area.setFont(new Font("Serif", Font.PLAIN, 16));
        area.setLineWrap(true);
        area.setBackground(Color.WHITE);
        area.setWrapStyleWord(true);
        
        JMenu help = new JMenu("Help");
        help.setFont(new Font("SERIF", Font.PLAIN, 12));
        
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(this);
        help.add(about);
        
        menubar.add(help);
        
        area.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });
        
        JScrollPane pane = new JScrollPane(area);
        add(pane);
        setBounds(400, 100, 600, 500);
        setVisible(true);
        
        
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getActionCommand().equals("New"))
        {
            area.setText("");
        }
        else if (ae.getActionCommand().equals("Open"))
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
            chooser.addChoosableFileFilter(restrict);
            
            int action = chooser.showOpenDialog(this);
            
            if(action != JFileChooser.APPROVE_OPTION)
            {
                return;
            }
            
            File file = chooser.getSelectedFile();
            try
            {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                area.read(reader, null);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        } 
        else if (ae.getActionCommand().equals("Save")) {
            JFileChooser saveas = new JFileChooser();
            saveas.setApproveButtonText("Save");

            int action = saveas.showOpenDialog(this);
            if (action != JFileChooser.APPROVE_OPTION) 
            {
                return;
            }

            File filename = new File(saveas.getSelectedFile() + ".txt");
            BufferedWriter outFile = null;
            try 
            {
                outFile = new BufferedWriter(new FileWriter(filename));
                area.write(outFile); 
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            } 
            finally 
            {
                if (outFile != null) 
                {
                    try 
                    {
                        outFile.close();
                    }
                    catch (IOException e) 
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

        else if (ae.getActionCommand().equals("Print"))
        {
            try 
            {
                area.print();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            
        }  
        else if (ae.getActionCommand().equals("Exit"))
        {
            System.exit(0);    
        } 
        else if (ae.getActionCommand().equals("Copy"))
        {
            text = area.getSelectedText();
        }
        else if (ae.getActionCommand().equals("Paste"))
        {
            area.insert(text, area.getCaretPosition());
        }
        else if (ae.getActionCommand().equals("Cut"))
        {
            text = area.getSelectedText();
            area.replaceRange("",area.getSelectionStart(), area.getSelectionEnd());
        }
        else if (ae.getActionCommand().equals("Select All"))
        {
            area.selectAll();
        }
        else if (ae.getActionCommand().equals("Undo")) 
        {
            try 
            {
                if (undoManager.canUndo()) 
                {
                    undoManager.undo();
                }
            } 
            catch (CannotUndoException ex) 
            {
                ex.printStackTrace();
            }
        }
        else if (ae.getActionCommand().equals("About"))
        {
            new About().setVisible(true);
        }
        
    }
    public static void main(String[] args) {
        new MyNotepad();
    }
    
}
