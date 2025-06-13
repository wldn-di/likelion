public interface Player {
    // @param round   현재 라운드 번호 (1부터 시작) - @return true = 협력(Cooperate), false = 배신(Defect)
    boolean cooperate(int round);

    //상대의 선택을 기록 - @param opponentMove true = 협력, false = 배신
    void recordOpponentMove(boolean opponentMove);
}
