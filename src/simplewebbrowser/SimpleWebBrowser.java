/*
http://java-tech-world.blogspot.com/2016/01/how-to-load-https-url-from-java-fx.html
 */
package simplewebbrowser;

 import javafx.application.Platform;  
 import javafx.beans.value.ChangeListener;  
 import javafx.beans.value.ObservableValue;  
 import javafx.concurrent.Worker;  
 import javafx.embed.swing.JFXPanel;  
 import javafx.event.EventHandler;  
 import javafx.scene.Scene;  
 import javafx.scene.web.WebEngine;  
 import javafx.scene.web.WebEvent;  
 import javafx.scene.web.WebView;  
 import javax.net.ssl.HttpsURLConnection;  
 import javax.net.ssl.SSLContext;  
 import javax.net.ssl.TrustManager;  
 import javax.net.ssl.X509TrustManager;  
 import javax.swing.*;  
 import java.awt.*;  
 import java.net.MalformedURLException;  
 import java.net.URL;  
 import java.security.GeneralSecurityException;  
 import static javafx.concurrent.Worker.State.FAILED;  
 public class SimpleWebBrowser extends JFrame {  
   private final JFXPanel jfxPanel = new JFXPanel();  
   private WebEngine engine;  
   private final JPanel panel = new JPanel(new BorderLayout());  
   private final JLabel lblStatus = new JLabel();  
   private final JProgressBar progressBar = new JProgressBar();  
   public SimpleWebBrowser() {  
     super();  
     initComponents();  
   }  
   private void initComponents() {  
     createScene();  
     progressBar.setPreferredSize(new Dimension(150, 18));  
     progressBar.setStringPainted(true);  
     JPanel topBar = new JPanel(new BorderLayout(5, 0));  
     topBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));  
     JPanel statusBar = new JPanel(new BorderLayout(5, 0));  
     statusBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));  
     statusBar.add(lblStatus, BorderLayout.CENTER);  
     statusBar.add(progressBar, BorderLayout.EAST);  
     panel.add(topBar, BorderLayout.NORTH);  
     panel.add(jfxPanel, BorderLayout.CENTER);  
     panel.add(statusBar, BorderLayout.SOUTH);  
     getContentPane().add(panel);  
     setPreferredSize(new Dimension(1024, 600));  
     pack();  
   }  
   private void createScene() {  
     Platform.runLater(new Runnable() {  
       @Override  
       public void run() {  
         WebView view = new WebView();  
         engine = view.getEngine();  
         engine.titleProperty().addListener(new ChangeListener<String>() {  
           @Override  
           public void changed(ObservableValue<? extends String> observable, String oldValue, final String newValue) {  
             SwingUtilities.invokeLater(new Runnable() {  
               @Override  
               public void run() {  
                 //SimpleWebBrowser.this.setTitle(newValue);  
                 SimpleWebBrowser.this.setTitle("リモート デスクトップ接続");  
               }  
             });  
           }  
         });  
         engine.setOnStatusChanged(new EventHandler<WebEvent<String>>() {  
           @Override  
           public void handle(final WebEvent<String> event) {  
             SwingUtilities.invokeLater(new Runnable() {  
               @Override  
               public void run() {  
                 lblStatus.setText(event.getData());  
               }  
             });  
           }  
         });  
         engine.getLoadWorker().workDoneProperty().addListener(new ChangeListener<Number>() {  
           @Override  
           public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, final Number newValue) {  
             SwingUtilities.invokeLater(new Runnable() {  
               @Override  
               public void run() {  
                 progressBar.setValue(newValue.intValue());  
               }  
             });  
           }  
         });  
         engine.getLoadWorker()  
             .stateProperty()  
             .addListener(new ChangeListener<Worker.State>() {  
               @Override  
               public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State state, Worker.State state2) {  
                 if(state2 == Worker.State.SUCCEEDED){  
                   System.out.println("Completed");  
                 }  
               }  
             });  
         engine.getLoadWorker()  
             .exceptionProperty()  
             .addListener(new ChangeListener<Throwable>() {  
               public void changed(ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) {  
                 if (engine.getLoadWorker().getState() == FAILED) {  
                   SwingUtilities.invokeLater(new Runnable() {  
                     @Override public void run() {  
                       JOptionPane.showMessageDialog(  
                           panel,  
                           (value != null) ?  
                               engine.getLocation() + "\n" + value.getMessage() :  
                               engine.getLocation() + "\nUnexpected error.",  
                           "Loading error...",  
                           JOptionPane.ERROR_MESSAGE);  
                     }  
                   });  
                 }  
               }  
             });  
         jfxPanel.setScene(new Scene(view));  
       }  
     });  
   }  
   public void loadURL(final String url) {  
     TrustManager[] trustAllCerts = new TrustManager[] {  
         new X509TrustManager() {  
           public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
             return null;  
           }  
           public void checkClientTrusted(  
               java.security.cert.X509Certificate[] certs, String authType) {  
           }  
           public void checkServerTrusted(  
               java.security.cert.X509Certificate[] certs, String authType) {  
           }  
         }  
     };  
     // Install the all-trusting trust manager  
     try {  
       SSLContext sc = SSLContext.getInstance("SSL");  
       sc.init(null, trustAllCerts, new java.security.SecureRandom());  
       HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());  
     } catch (GeneralSecurityException e) {  
     }  
     Platform.runLater(new Runnable() {  
       @Override  
       public void run() {  
         String tmp = toURL(url);  
         if (tmp == null) {  
           tmp = toURL("http://" + url);  
         }  
         engine.load(tmp);  
       }  
     });  
   }  
   private static String toURL(String str) {  
     try {  
       return new URL(str).toExternalForm();  
     } catch (MalformedURLException exception) {  
       return null;  
     }  
   }  
   public static void main(String[] args) {  
     SwingUtilities.invokeLater(new Runnable() {  
       public void run() {  
         SimpleWebBrowser browser = new SimpleWebBrowser(); 
         browser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         ImageIcon imageicon = new ImageIcon(getClass().getResource("/resource/icon/remote.png"));
        //ImageIcon image = new ImageIcon("C:\\Users\\zaf_h\\OneDrive\\デスクトップ\\denpa.PNG");
        // Image icon = new Image(getClass().getResourceAsStream( "C:\\Users\\zaf_h\\OneDrive\\デスクトップ\\denpa.PNG" ));
         browser.setIconImage(imageicon.getImage());
         browser.setVisible(true);  
         // browser.loadURL("https://google.co.jp");
         browser.loadURL("https://kyokuto.work/remote/");  
         try {  
           Thread.sleep(5000);  
         } catch (InterruptedException e) {  
           e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.  
         }  
       }  
     });  
   }  
 }  
