package model;

public class KolRanking {
    private int rank;
    private String username;
    private double rankingPoint;

    public KolRanking(int rank, String username, double rankingPoint) {
        this.rank = rank;
        this.username = username;
        this.rankingPoint = rankingPoint;
    }


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public double getRankingPoint() {
        return rankingPoint;
    }
    public void setRankingPoint(double rankingPoint) {
        this.rankingPoint = rankingPoint;
    }


    public int getRank() {
        return rank;
    }


    public void setRank(int rank) {
        this.rank = rank;
    }
    


    
    
}
