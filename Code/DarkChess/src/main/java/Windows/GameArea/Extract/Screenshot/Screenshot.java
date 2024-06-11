package Windows.GameArea.Extract.Screenshot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.StdCallLibrary;

public class Screenshot {
    public static final String defaultUrl = "Userfile/Screenshot";

    public static BufferedImage capture(String windowName) {

        WinDef.RECT ret = new WinDef.RECT();
        WinDef.HWND parentWindow = User32.INSTANCE.FindWindow(null, "DarkChess"/*windowName*/);
        User32.INSTANCE.GetWindowRect(parentWindow, ret);
        Robot rb = null;
        try {
            rb = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        return rb.createScreenCapture(new Rectangle(0,0,600,830));
        //return rb.createScreenCapture(ret.toRectangle());
    }
    public static BufferedImage capture(double absStartX,double absStartY,double width,double length) {
        Robot rb = null;
        try {
            rb = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        return rb.createScreenCapture(new Rectangle((int) absStartX, (int) absStartY, (int) width, (int) length));
        //return rb.createScreenCapture(ret.toRectangle());
    }

    public static void save(BufferedImage image, String url) {
        File outputFile = new File(url + "/" + generateSaveName() + ".png");
        try {
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateSaveName() {
        LocalDateTime t = LocalDateTime.now();
        return t.getYear() + "-" + t.getMonthValue() + "-" + t.getDayOfMonth()
                + "-" + t.getHour() + "-" + t.getMinute() + "-" + t.getSecond();
    }
}
