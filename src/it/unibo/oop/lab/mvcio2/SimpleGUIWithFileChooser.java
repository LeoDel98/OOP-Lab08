package it.unibo.oop.lab.mvcio2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import it.unibo.oop.lab.mvcio.Controller;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {

    /*
     * TODO: Starting from the application in mvcio:
     * 
     * 1) Add a JTextField and a button "Browse..." on the upper part of the
     * graphical interface.
     * Suggestion: use a second JPanel with a second BorderLayout, put the panel
     * in the North of the main panel, put the text field in the center of the
     * new panel and put the button in the line_end of the new panel.
     * 
     * 2) The JTextField should be non modifiable. And, should display the
     * current selected file.
     * 
     * 3) On press, the button should open a JFileChooser. The program should
     * use the method showSaveDialog() to display the file chooser, and if the
     * result is equal to JFileChooser.APPROVE_OPTION the program should set as
     * new file in the Controller the file chosen. If CANCEL_OPTION is returned,
     * then the program should do nothing. Otherwise, a message dialog should be
     * shown telling the user that an error has occurred (use
     * JOptionPane.showMessageDialog()).
     * 
     * 4) When in the controller a new File is set, also the graphical interface
     * must reflect such change. Suggestion: do not force the controller to
     * update the UI: in this example the UI knows when should be updated, so
     * try to keep things separated.
     */
    /**
     * TITLE and PROPORTION.
     */
    private static final String TITLE = "My second java graphical interface";
    private static final int PROPORTION = 2;
    private static final String ERROR = "ERROR";

    private final JFrame frame = new JFrame(TITLE);

    /**
     * builds a new {@link SimpleGUI}.
     */
    public SimpleGUIWithFileChooser(final Controller ctrl) {
        /**
         * Panel1
         */
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        /**
         * Panel2
         */
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        panel.add(panel2, BorderLayout.NORTH);
        /**
         * Button Save
         */
        final JButton save = new JButton("Save");
        panel.add(save, BorderLayout.SOUTH);
        /**
         * Button Browse
         */
        final JButton browse = new JButton("Browse...");
        panel2.add(browse, BorderLayout.LINE_END);
        /**
         * TextArea
         */
        final JTextArea textArea = new JTextArea();
        panel.add(textArea);
        /**
         * TextField
         */
        final JTextField textField = new JTextField();
        textField.setEditable(false);
        panel2.add(textField, BorderLayout.CENTER);
        /**
         * Handler save
         */
        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                String s = textArea.getText();
                //System.out.println(s);
                try {
                    ctrl.writeOnFile(s);
                } catch (final IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        /**
         * Handler browse
         */
        browse.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                final JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showSaveDialog(browse);
                if (result == JFileChooser.APPROVE_OPTION) {
                    ctrl.setCurrentFile(fileChooser.getSelectedFile().getName());
                    textField.setText(fileChooser.getSelectedFile().getPath());
                } else if (result == JFileChooser.CANCEL_OPTION) {
                    System.out.println("Nothing happened");
                } else {
                    JOptionPane.showMessageDialog(textField, ERROR);
                }
            }
        });
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    /**
     * Display() method.
     */
    private void display() {
        /*
         * Make the frame half the resolution of the screen. This very method is
         * enough for a single screen setup. In case of multiple monitors, the
         * primary is selected.
         * 
         * In order to deal coherently with multimonitor setups, other
         * facilities exist (see the Java documentation about this issue). It is
         * MUCH better than manually specify the size of a window in pixel: it
         * takes into account the current resolution.
         */
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        frame.setSize(sw / PROPORTION, sh / PROPORTION);
        /*
         * Instead of appearing at (0,0), upper left corner of the screen, this
         * flag makes the OS window manager take care of the default positioning
         * on screen. Results may vary, but it is generally the best choice.
         */
        frame.setLocationByPlatform(true);
        /*
         * OK, ready to pull the frame on screen
         */
        frame.setVisible(true);
    }
    /**
     * Main method.
     * 
     * @param args
     */
    public static void main(final String[] args) {
        final Controller ctrl = new Controller();
        new SimpleGUIWithFileChooser(ctrl).display();
    }
}
