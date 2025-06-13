import java.util.Random;

public class B implements Player{
    final boolean[] opponentHistory;
    int recordIndex = 0;

    public B(int maxRounds) {
        this.opponentHistory = new boolean[10];
    }

    @Override
    public boolean cooperate(int round) {
        if (round == 1) {
            return true;
        }else if (round <= 8) {
            return opponentHistory[round - 2];}
        else
            return false;
    }

    @Override
    public void recordOpponentMove(boolean opponentMove) {
        opponentHistory[recordIndex++] = opponentMove;
    }
}
