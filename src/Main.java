public class Main {
    // Payoff 상수 정의
    private static final int R = 3; // 둘 다 협력
    private static final int T = 5; // 나는 배신, 상대방 협력
    private static final int P = 1; // 둘다 배신
    private static final int S = 0; // 나는 협력, 상대방 배신

    public static void main(String[] args) {
        int rounds = 10; // 라운드 수 고정

        Player playerA = new A(rounds);
        Player playerB = new B(rounds);

        int totalA = 0, totalB = 0;

        for (int round = 1; round <= rounds; round++) {
            boolean choiceA = playerA.cooperate(round);
            boolean choiceB = playerB.cooperate(round);

            // 점수 계산
            if (choiceA && choiceB) {
                totalA += R;
                totalB += R;
            } else if (choiceA && !choiceB) {
                totalA += S;
                totalB += T;
            } else if (!choiceA && choiceB) {
                totalA += T;
                totalB += S;
            } else {
                totalA += P;
                totalB += P;
            }

            // 상대 움직임 기록
            playerA.recordOpponentMove(choiceB);
            playerB.recordOpponentMove(choiceA);

            System.out.printf(
                    "Round %2d: A=%s, B=%s → 점수 A=%d, B=%d%n",
                    round,
                    choiceA ? "협력" : "배신",
                    choiceB ? "협력" : "배신",
                    totalA,
                    totalB
            );
        }

        System.out.println("=== 최종 점수 ===");
        System.out.printf("Player A: %d점%nPlayer B: %d점%n", totalA, totalB);
    }
}