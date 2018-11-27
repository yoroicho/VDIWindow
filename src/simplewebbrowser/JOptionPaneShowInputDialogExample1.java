/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplewebbrowser;

import javax.swing.*;

/**
 * JOptionPane showInputDialog example #1.
 * A simple showInputDialog example.
 * @author alvin alexander, http://alvinalexander.com
 */
public class JOptionPaneShowInputDialogExample1
{

     JOptionPaneShowInputDialogExample1(){
          // a jframe here isn't strictly necessary, but it makes the example a little more real
    JFrame frame = new JFrame("リモート デスクトップ接続");
 ImageIcon imageicon = new ImageIcon(getClass().getResource("/resource/icon/remote.png"));
 
    // prompt the user to enter their name
   // String name = JOptionPane.showInputDialog(frame, "リモート デスクトップ接続", 0, imageicon, JOptionPane.OK_OPTION. "コンピューター名");
 Object name =JOptionPane.showInputDialog(frame, "コンピューター名","リモート デスクトップ接続" , 1, imageicon, null, null);
 //.showInputDialog(frame, "リモート デスクトップ接続", "コンピューター名", 0, imageicon, JOptionPane.PLAIN_MESSAGE, imageicon);
 //frame.setIconImage(imageicon.getImage());
    // get the user's input. note that if they press Cancel, 'name' will be null
    System.out.printf("The user's name is '%s'.\n", name);
    System.exit(0);
  }
    
    
  public static void main(String[] args)
  {
     JOptionPaneShowInputDialogExample1 inputDialog
             = new JOptionPaneShowInputDialogExample1();
     

  }
  
}
