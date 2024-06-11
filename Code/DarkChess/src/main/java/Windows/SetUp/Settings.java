package Windows.SetUp;

import java.io.*;

public class Settings implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1145141919810L;
    public static final String url = "src/main/resources/Settings/Settings.ser";
    public StartSettings StartSettings = new StartSettings();
    public VisualSettings visualSettings=new VisualSettings();
    public GameSettings gameSettings=new GameSettings();

    public SoundSettings soundSettings=new SoundSettings();
    public static Settings read(String url) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(url))) {
            return (Settings) in.readObject();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Settings not found!");
            c.printStackTrace();
        }
        return null;
    }

    public static void save(Settings settings, String url) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/main/resources/Settings/Settings.ser"))) {
            out.writeObject(settings);
            System.out.print("Serialized data is saved.");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

}
