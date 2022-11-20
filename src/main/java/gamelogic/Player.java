package GameLogic;

public class Player {
    private Color color = Color.UNKNOWN;
    void setColor(Color c){
        this.color = c ;
    }
    Color getColor(){
        return color;
    }

    private int score = 0;
    void setScore(int i){
        this.score = i;
    }
    void addScore(int i){
        this.score += i;
    }
    int getScore(){
        return score;
    }
    boolean isWin(){
        if(score >= 60)return true;
        else return false;
    }

    private boolean status = false;//是否走棋
    void changeStatus(){
        status = !status ;
    }
    boolean getStatus(){
        return status;
    }//true代表轮到他走棋了
}
