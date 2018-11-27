/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplewebbrowser;

import java.util.Optional;
import java.util.function.Consumer;
import javax.swing.*;

/**
 * JOptionPane showInputDialog example #1. A simple showInputDialog example.
 *
 * @author alvin alexander, http://alvinalexander.com
 */
public class JOptionPaneShowInputDialogURL {
// ImageIcon imageicon = new ImageIcon(getClass().getResource("/resource/icon/remote.png"));

    static String JOptionPaneShowInputDialogURL(ImageIcon imageicon) {
        // a jframe here isn't strictly necessary, but it makes the example a little more real
        JFrame frame = new JFrame("リモート デスクトップ接続");

        // prompt the user to enter their name
        Object name = JOptionPane.showInputDialog(frame, "コンピューター名", "リモート デスクトップ接続", 1, imageicon, null, null);
        //.showInputDialog(frame, "リモート デスクトップ接続", "コンピューター名", 0, imageicon, JOptionPane.PLAIN_MESSAGE, imageicon);
        // get the user's input. note that if they press Cancel, 'name' will be null
        //System.out.printf("The URL is '%s'.\n", name);
        if(name==null){
            // System.out.println("NULL EXIT");
            System.exit(0);
        }
        return name.toString();
        //System.exit(0);
    }

    public static void main(String[] args) {
        JOptionPaneShowInputDialogURL inputDialog
                = new JOptionPaneShowInputDialogURL();

    }

}
