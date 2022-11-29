package Windows.SetUp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class NormalSettings implements java.io.Serializable {
    public static final String url = "src/main/resources/Settings/Settings.ser";
    public StartMenu startMenu = new StartMenu();

    public static NormalSettings readSettings(String url) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(url))) {
            return (NormalSettings) in.readObject();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Settings not found!");
            c.printStackTrace();
        }
        return null;
    }


}
