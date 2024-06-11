package Windows.GameArea.Extract.Animation;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.util.Duration;

public class ChessAnimation {
    public static void move(Pane Chessboard,int index,int[] targetPositions){
        Node n = Chessboard.getChildren().get(index);
        ImageView i  = (ImageView) n;
        double delta=i.getFitHeight()*0.1;
        i.setX(i.getX()-delta);
        i.setY(i.getY()-delta);
        i.setFitWidth(i.getFitWidth()*1.2);
        i.setFitHeight(i.getFitHeight()*1.2);
        Chessboard.getChildren().remove(index);
        Chessboard.getChildren().add(i);
        Node node=Chessboard.getChildren().get(Chessboard.getChildren().size()-1);

        javafx.scene.shape.Path path=new javafx.scene.shape.Path();
        path.getElements().add(new MoveTo(i.getX()+35,i.getY()+40));
        path.getElements().add(new LineTo(targetPositions[0]+35, targetPositions[1]+40));

        //创建路径转变
        PathTransition pt=new PathTransition();
        pt.setDuration(Duration.millis(400));//设置持续时间400ms
        pt.setPath(path);//设置路径
        pt.setNode(node);//设置物体
        //pt.setOrientation();

        pt.play();
    }
    public static void fade(Pane Chessboard,int index,String toChessType){
        Node n=Chessboard.getChildren().get(index);
        ImageView c=(ImageView) n;

        ImageView c2=new ImageView();
        c2.setId("UnTurnedChess");
        c2.setX(c.getX());
        c2.setY(c.getY());
        c2.setFitWidth(c.getFitWidth());
        c2.setFitHeight(c.getFitHeight());
        Chessboard.getChildren().add(c2);

        c.setId(toChessType);
        Chessboard.getChildren().remove(index);
        Chessboard.getChildren().add(c);

        FadeTransition ft = new FadeTransition(Duration.millis(400),c);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.play();
        FadeTransition ft2 = new FadeTransition(Duration.millis(400),c2);
        ft.setFromValue(0.8);
        ft.setToValue(1);
        ft2.play();

    }
}
