public class A implements Player {

        private final boolean[] opponentHistory;
        private int recordIndex = 0;
        private boolean revengeMode = false;

        public A(int maxRounds) {
            this.opponentHistory = new boolean[maxRounds];
        }

        @Override
        public boolean cooperate(int round) {
            if (round == 1 || round == 10) return false; // 1 & 10라운드 확정 배신
            if (round == 2) return true; //2라운드 협력(간보기용)

            //협력률 & 배신률 저장
            int defectCount = 0;
            int coopCount = 0;
            for (int i = 0; i < round - 1; i++) {
                if (opponentHistory[i]) coopCount++;
                else defectCount++;
            }
            double defectionRate = (double) defectCount / (round - 1);
            double cooperationRate = (double) coopCount / (round - 1);

            // 배신률 40%이상 진입 시 복수모드 진입
            if (defectionRate > 0.4) revengeMode = true;

            // 연속 협력 시 복수모드 해제
            if (revengeMode && round >= 3 &&
                    opponentHistory[round - 2] && opponentHistory[round - 3]) {
                revengeMode = false;
            }

            // Alternator(T F T F ... F) 대응
            if (revengeMode && round >= 4) {
                boolean a = opponentHistory[round - 2];
                boolean b = opponentHistory[round - 3];
                boolean c = opponentHistory[round - 4];
                if (a != b && c == a) {
                    return a;  // Alternator에 동기화
                }
            }
            // 과도한 협력률 탐지 → 배신
            if (cooperationRate >= 0.7) {
                return false;
            }
            // 복수모드에서는 배신
            if (revengeMode) return false;

            // 기본 전략: Tit-for-Tat
            return opponentHistory[round - 2];
        }
        @Override
        public void recordOpponentMove(boolean opponentMove) {
            opponentHistory[recordIndex++] = opponentMove;
        }
}
/*
[목표]
-최소 30점 이상 확보
-다양한 전략 알고리즘(Prober, Grim, Tit-for-Tat 등)에 취약하지 않은 안정적인 알고리즘 설계

[라운드 고정 전략]
1라운드와 10라운드는 무조건 배신
-상대가 초반·후반에 협력을 유도해 고득점을 노리는 전략(Prober, Suspicious TFT 등)을 견제
-심리적 선제타격 효과
2라운드는 협력
-협력 유도형 알고리즘에 신뢰 시그널 제공

[상대 분석 기반 판단 전략]
1. 배신률 분석 (Defection Rate > 40%)
-일정 비율 이상 배신하는 상대는 협력 유도가 어려운 상대로 판단, Grim Trigger / Prober 유형 탐지 및 대응
2. Alternator 패턴 탐지
-패턴이 교차 협력/배신(C → D → C → D) 형태인 경우 Alternator로 간주, 해당 경우엔 패턴 동기화 협력/배신으로 대응 → 점수 손실 최소화
3. 전략적 협력률 역이용
-협력률이 일정 수준 이상인 경우(예: > 50%)에도 의심이 가능하다고 판단, “협력이 진짜 협력일 수도 있지만, 내 협력을 유도한 뒤 배신하려는 미끼일 가능성.”
-지나치게 협력적인 상대에 대한 역공 시도, 선제 배신으로 기습 가능
4. 복수 모드 활성화 조건 및 전환
-배신률 > 40%, 또는 연속 2회 배신 시 복수모드 진입, Alternator(면 동기화)를 제외하고 배신 유지
5. 게임 후반 유연성 고려
복수 모드에서도 상대 협력률이 회복되는 경우엔 복수모드 해제 → 상대와의 협력 회복 여지 확보

 */