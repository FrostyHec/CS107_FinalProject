package GameLogic;


public class Chess implements java.io.Serializable{//小心chess为null时会出的bug

    public Chess(){}

    public Chess(Chess chess){
        rank = chess.getRank();
        score = chess.getScore();
        color = chess.getColor();
        turnOver = chess.isTurnOver();
    }
    private static boolean click = false;
    protected void initClick(){
        click = false;
    }
    public static boolean isClick(){
        return click;
    }

    private int rank;//等级
    protected void setRank(int x){
        rank = x;
    }
    public int getRank(){
        return rank;
    }


    //该棋子移动的可能性
    public int[][] possibleMove(Chess[][] chess,int x,int y){
        int[][] p = new int[4][2];

        //需要按照规则行棋（考虑颜色）
        if(rank == 2){//炮的走法
            //需要隔山打牛，不能直接移动

            p = line(chess,x,y);

            //不能自相残杀,可以吃未翻开的棋子（有可能是自己的）
            int i = 0;
            for(int[] a:p){
                if (a[0] != -1){
                    if(chess[a[0]][a[1]].getColor() == color && chess[a[0]][a[1]].isTurnOver())
                        p[i][0] = (p[i][1] = -1);
                }
                i++;
            }
        }

        else {//除了炮以外其他棋的走法
            for(int i = 0;i<p.length;i++){
                p[i][0] = (p[i][1] = -1);
            }
            if (x + 1 < chess.length) {p[0][0] = x + 1;p[0][1] = y;}
            if (y + 1 < chess[0].length) {p[1][1] = y + 1;p[1][0] = x;}
            if (x - 1 > -1) {p[2][0] = x - 1;p[2][1] = y;}
            if (y - 1 > -1) {p[3][1] = y - 1;p[3][0] = x;}

            //可能值
            if (rank == 1) {//兵只能吃将或兵
                int i = 0;
                for (int[] a : p) {
                    if(a[0] == -1){
                        i++;
                        continue;
                    }
                    if(chess[a[0]][a[1]]==null || chess[a[0]][a[1]].getRank()==7 || chess[a[0]][a[1]].getRank()==1);
                    else p[i][0] = (p[i][1] = -1);
                    i++;
                }
            } else if (rank == 7) {//将不能吃兵
                int i = 0;
                for (int[] a : p) {
                    if(a[0] == -1){
                        i++;
                        continue;
                    }
                    if(chess[a[0]][a[1]]==null);
                    else if (chess[a[0]][a[1]].getRank() == 1)
                        p[i][0] = (p[i][1] = -1);
                    i++;
                }
            } else {//其他子不能吃比自己大的棋子
                int i = 0;
                for (int[] a : p) {
                    if(a[0] == -1){
                        i++;
                        continue;
                    }
                    if(chess[a[0]][a[1]]==null);
                    else if (chess[a[0]][a[1]].getRank() > rank)
                        p[i][0] = (p[i][1] = -1);
                    i++;
                }
            }

            //不能自相残杀或吃未翻开的棋子
            int i = 0;
            for(int[] a:p){
                if (a[0] != -1){
                    if(chess[a[0]][a[1]]==null);
                    else if(chess[a[0]][a[1]].getColor()==color || !chess[a[0]][a[1]].isTurnOver()){
                        p[i][0] = (p[i][1] = -1);
                    }
                }
                i++;
            }
        }


        return p;
    }


    //which camp
    private Color color;

    protected void setColor(Color color){
        this.color = color;
    }
    public Color getColor(){
        return color;
    }


    private int score;//分数
    protected void setScore(int x){
        score = x;
    }
    public int getScore(){
        return score;
    }


    private boolean turnOver = false;
    public void initTurnOver(){
        turnOver = false;
    }

    protected boolean TurnOver(Player player1, Player player2){
        if(!click){
            click = true;
            player1.setColor(this.color);
            Color c = Color.RED;
            if(this.color == c){
                c = Color.BLACK;
            }
            player2.setColor(c);
        }
        if(turnOver)
            return false;//如果已经被翻开，则return false ，不能执行turnover的操作
        else{
            this.turnOver = true;
            return true;
        }
    }
    public boolean isTurnOver(){
        return turnOver;
    }



    private int[][] line(Chess[][] chess,int x,int y){
        int[][]a = new int[4][2];

        int i = x+1;
        if(i>=7)a[0][1] = (a[0][0] = -1);
        else {
            for (; chess[i][y] == null; i++)
                if (i == 7) break;
            if (i == 7) a[0][1] = (a[0][0] = -1);
            else {
                i++;
                for (; chess[i][y] == null; i++)
                    if (i == 7) {
                        i++;
                        break;
                    }
                if (i == 8) a[0][1] = (a[0][0] = -1);
                else {
                    a[0][0] = i;
                    a[0][1] = y;
                }
            }
        }
        i = x-1;
        if(i<=0)a[1][0] = (a[1][1] = -1);
        else {
            for (; chess[i][y] == null; i--)
                if (i == 0) break;
            if (i == 0) a[1][0] = (a[1][1] = -1);
            else {
                i--;
                for (; chess[i][y] == null; i--)
                    if (i == 0) {
                        i--;
                        break;
                    }
                if (i == -1) a[1][0] = (a[1][1] = -1);
                else {
                    a[1][0] = i;
                    a[1][1] = y;
                }
            }
        }

        i = y+1;
        if(i>=3)a[2][0] = (a[2][1] = -1);
        else {
            for (; chess[x][i] == null; i++)
                if (i == 3) break;
            if (i == 3) a[2][0] = (a[2][1] = -1);
            else {
                i++;
                for (; chess[x][i] == null; i++)
                    if (i == 3) {
                        i++;
                        break;
                    }
                }
                if (i == 4) a[2][0] = (a[2][1] = -1);
                else {
                    a[2][0] = x;
                    a[2][1] = i;
                }
            }
        i = y-1;
        if(i<=0)a[3][0] = (a[3][1] = -1);
        else {
            for (; chess[x][i] == null; i--)
                if (i == 0) break;
            if (i == 0) a[3][0] = (a[3][1] = -1);
            else {
                i--;
                for (; chess[x][i] == null; i--)
                    if (i == 0) {
                        i--;
                        break;
                    }
                if (i == -1) a[3][0] = (a[3][1] = -1);
                else {
                    a[3][0] = x;
                    a[3][1] = i;
                }
            }
        }

        return a;
    }

    @Override
    public String toString(){
        String s = "";
        s += String.valueOf(rank);
        s += String.valueOf(isTurnOver());
        s += color.toString();
        return s;
    }
}
