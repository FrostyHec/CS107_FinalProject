package GameLogic;

public class Player implements java.io.Serializable{
    Color color = Color.UNKNOWN;
    public void setColor(Color c){
        this.color = c ;
    }
    public Color getColor(){
        return color;
    }

    int score = 0;
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

    boolean status = false;//是否走棋
    protected void changeStatus(){
        status = !status ;
    }
    public boolean getStatus(){
        return status;
    }//true代表轮到他走棋了

    public int[][] move(Chess[][] chess) throws Exception {
        int[][] move = new int[1][2];
        return move;
    }
}
