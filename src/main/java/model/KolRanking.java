package model;

public class KolRanking {
    private String username;
    private double rankingPoint;

    public KolRanking(String username, double rankingPoint) {
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

    
    
}
