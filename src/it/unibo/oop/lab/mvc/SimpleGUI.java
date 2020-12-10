package it.unibo.oop.lab.mvc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.Caret;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUI {
    /**
     * Proportion and title.
     */
    private static final int PROPORTION = 2;
    private static final String TITLE = "MVC exercise";
    private final JFrame frame = new JFrame(TITLE);
    private final Controller ctrl;

    /*
     * Once the Controller is done, implement this class in such a way that:
     * 
     * 1) I has a main method that starts the graphical application
     * 
     * 2) In its constructor, sets up the whole view
     * 
     * 3) The graphical interface consists of a JTextField in the upper part of the frame, 
     * a JTextArea in the center and two buttons below it: "Print", and "Show history". 
     * SUGGESTION: Use a JPanel with BorderLayout
     * 
     * 4) By default, if the graphical interface is closed the program must exit
     * (call setDefaultCloseOperation)
     * 
     * 5) The behavior of the program is that, if "Print" is pressed, the
     * controller is asked to show the string contained in the text field on standard output. 
     * If "show history" is pressed instead, the GUI must show all the prints that
     * have been done to this moment in the text area.
     * 
     */

    /**
     * builds a new {@link SimpleGUI}.
     * 
     * @param ctrl
     */
    public SimpleGUI(final ControllerImpl controller) {
        
        this.ctrl = controller;
        /**
         * panel
         */
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        /**
         * panel2
         */
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.LINE_AXIS));
        panel.add(panel2, BorderLayout.SOUTH);
        /**
         * JTextField
         */
        final JTextField textField = new JTextField();
        panel.add(textField, BorderLayout.NORTH);
        /**
         * JTextArea
         */
        final JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        panel.add(textArea, BorderLayout.CENTER);
        /**
         * Print Button
         */
        final JButton print = new JButton("Print");
        panel2.add(print);
        /**
         * Show history Button
         */
        final JButton showHistory = new JButton("Show history");
        panel2.add(showHistory);
        /**
         * Print handler
         */
        print.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                String s = textField.getText();
                ctrl.setNextStringToPrint(s);
                ctrl.printCurrentString();
                //System.out.println("List size = " + ctrl.getHistoryOfPrintedStrings().size());
            }
        });
        /**
         * Show history handler
         */
        showHistory.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                for (final String s : ctrl.getHistoryOfPrintedStrings()) {
                    textArea.append(s + "\n");
                }
            }
        });
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    /**
     * display method.
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
    public static void main(String[] args) {
        new SimpleGUI(new ControllerImpl()).display();
    }

}
