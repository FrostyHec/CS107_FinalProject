package Windows.GameArea.Extract.Animation;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ScreenShotAnimation {
    public static void show(Pane mainPane,int lastTime) {
        Rectangle c=new Rectangle();
        c.setX(0);
        c.setY(0);
        c.setWidth(mainPane.getWidth());
        c.setHeight(mainPane.getHeight());
        c.setFill(Color.WHITE);
        Platform.runLater(()->mainPane.getChildren().add(c));
        FadeTransition ft = new FadeTransition(Duration.millis(lastTime/5), c);
        ft.setFromValue(0.7);
        ft.setToValue(1);
        ft.play();
        new Thread(()->{
            try {
                Thread.sleep(lastTime/2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            FadeTransition ft2 = new FadeTransition(Duration.millis(lastTime/2), c);
            ft2.setFromValue(1);
            ft2.setToValue(0);
            ft2.play();
        }).start();
        new Thread(()->{
            try {
                Thread.sleep(lastTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
           Platform.runLater(()->mainPane.getChildren().remove(c));
        }).start();
    }
}
