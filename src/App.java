import javax.swing.*;
import java.applet.Applet;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

/**
 * Created by windows equals ass on 1/30/2017.
 */
public class App {
    public static void main(String[] args) {
        // Create an instance of our config reader and parse the parameters
        ConfigReader configReader = new ConfigReader();
        Map<String, String> map = configReader.read();
        // Use the parameters to build the jar location we are loading
        String jarLocation = map.get("codebase") + map.get("initial_jar");
        try {
            // Use a URLClassLoader because we are loading classes from a jar at a URL
            URLClassLoader classLoader = new URLClassLoader(new URL[]{new URL(jarLocation)});
            // Use the parameters to get the correct class, usually always "client"
            Class<?> clientClass = classLoader.loadClass(map.get("initial_class").replace(".class", ""));
            // Create a new instance of the class and cast it to an Applet so we can use it
            Applet applet = (Applet) clientClass.newInstance();
            // Create our stub so we can set the AppletStub of the Applet and pass in the parsed parameters
            RSAppletStub appletStub = new RSAppletStub(map);
            // Use our setter to set the Applet in the AppletContext
            appletStub.getAppletContext().setApplet(applet);
            // Set the AppletStub of the Applet
            applet.setStub(appletStub);
            // Turn the key and start the Applet up
            applet.init();
            // Set the size, this can also be done by reading the parameters, but I was too lazy to parse the Int's
            applet.setSize(765, 503);
            // Using our setter, make it so everything knows the Applet is active
            appletStub.setActive(true);

            // Create a JFrame and add the Applet to it
            JFrame frame = new JFrame("Runescape");
            frame.setSize(800, 600);
            JPanel panel = new JPanel();
            frame.add(panel);
            panel.add(applet);
            frame.pack();
            frame.setVisible(true);


            frame.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    System.out.println("mouse was cliced");
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println("pressed");
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    System.out.println("releaed");
                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        } catch (MalformedURLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
