package GameLogic;

public class Player {
    private Color color = Color.UNKNOWN;
    protected void setColor(Color c){
        this.color = c ;
    }
    public Color getColor(){
        return color;
    }

    private int score = 0;
    protected void setScore(int i){
        this.score = i;
    }
    protected void addScore(int i){
        this.score += i;
    }
    public int getScore(){
        return score;
    }
    public boolean isWin(){
        if(score >= 60)return true;
        else return false;
    }

    private boolean status = false;//是否走棋
    protected void changeStatus(){
        status = !status ;
    }
    public boolean getStatus(){
        return status;
    }//true代表轮到他走棋了
}
