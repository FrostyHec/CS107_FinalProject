package GameLogic;

enum AllChess {

    //red
    General(Color.RED,7,30),
    Advisor1(Color.RED, 6,10),
    Advisor2(Color.RED, 6,10),
    Minister1(Color.RED, 5,5),
    Minister2(Color.RED, 5,5),
    Chariot1(Color.RED, 4,5),
    Chariot2(Color.RED, 4,5),
    Horse1(Color.RED, 3,5),
    Horse2(Color.RED, 3,5),
    Cannon1(Color.RED,2,5),
    Cannon2(Color.RED,2,5),
    Soldier1(Color.RED, 1,1),
    Soldier2(Color.RED, 1,1),
    Soldier3(Color.RED, 1,1),
    Soldier4(Color.RED, 1,1),
    Soldier5(Color.RED, 1,1),

    //black
    general(Color.BLACK,7,30),
    advisor1(Color.BLACK, 6,10),
    advisor2(Color.BLACK, 6,10),
    minister1(Color.BLACK, 5,5),
    minister2(Color.BLACK, 5,5),
    chariot1(Color.BLACK, 4,5),
    chariot2(Color.BLACK, 4,5),
    horse1(Color.BLACK, 3,5),
    horse2(Color.BLACK, 3,5),
    cannon1(Color.BLACK,2,5),
    cannon2(Color.BLACK,2,5),
    soldier1(Color.BLACK, 1,1),
    soldier2(Color.BLACK, 1,1),
    soldier3(Color.BLACK, 1,1),
    soldier4(Color.BLACK, 1,1),
    soldier5(Color.BLACK, 1,1);



    private final int rank;
    private final Color color;
    private final int score;
    AllChess(Color color, int rank, int score){
        this.rank = rank;
        this.color = color;
        this.score = score;
    }

    int getRank(){
        return rank;
    }
    Color getColor(){
        return color;
    }
    int getScore(){return score;}

}

