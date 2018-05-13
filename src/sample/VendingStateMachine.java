package sample;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class VendingStateMachine {

    public interface OnDialogCallback {
        void accept(String message);
    }

    static String[][] states = {
            {"q0", "q1", "q2", "q5d", "q0", "q0", "0",  "false"},
            {"q1", "q2", "q3", "q6d", "q1", "q1", "1", "false"},
            {"q2","q3", "q4", "q7d", "q2" ,"q2","2", "false"},
            {"q3", "q4", "q5d", "q8d", "q3", "q3", "3","false"},
            {"q4", "q5d", "q6d", "q9d", "q4", "q4","4", "false"},
            {"q5d", "q6k", "qkr0","qkr3", "qhr0", "q5k","5", "true", "Kawa czy herbata"},
            {"q6k", "qkr0", "qkr1", "qkr4", "q6k", "q6k","6", "false"},
            {"q5k", "q6k", "qkr0", "qkr3", "q5k", "q5k","5", "false"},
            {"q8d", "q9re", "q10re", "q13re", "qhr3", "qkr1", "8","true", "Kawa czy herbata"},
            {"q6d", "qkr0", "qkr1", "qkr4", "qhr1", "q6k","6", "true", "Kawa czy herbata"},
            {"q7d", "q8re", "q9re", "q12re", "qhr2", "qkr0", "7","true", "Kawa czy herbata"},
            {"q9d", "q10re", "q11re", "q14re", "qhr4", "qkr2", "9","true", "Kawa czy herbata"},
            {"qkr0", "q1", "q2", "q5d", "q0", "q0","0", "true", "Kawa"},
            {"qkr3", "q1", "q2", "q5d", "q0", "q0","0", "true", "Kawa zwaracam 3"},
            {"qhr0", "q1", "q2", "q5d", "q0", "q0", "0","true", "Herbata"},
            {"qkr1", "q1", "q2", "q5d", "q0", "q0","0", "true", "Kawa zwracam 1"},
            {"qkr4", "q1", "q2", "q5d", "q0", "q0", "0","true", "Kawa zwracam 4"},
            {"qkr2", "q1", "q2", "q5d", "q0", "q0","0", "true", "Kawa zwracam 2"},
            {"qhr2", "q1", "q2", "q5d", "q0", "q0", "0","true", "Herbata zwracam 2"},
            {"qhr1", "q1", "q2", "q5d", "q0", "q0", "0","true", "Herbata zwracam 1"},
            {"qhr3", "q1", "q2", "q5d", "q0", "q0", "0","true", "Herbata zwracam 3"},
            {"qhr4", "q1", "q2", "q5d", "q0", "q0","0", "true", "Herbata zwracam 4"},
            {"q14re", "q1", "q2", "q5d", "q0", "q0","0", "true", "Bład zwracam 14"},
            {"q9re", "q1", "q2", "q5d", "q0", "q0","0", "true", "Bład zwracam 9"},
            {"q10re", "q1", "q2", "q5d", "q0", "q0","0", "true", "Bład zwracam 10"},
            {"q11re", "q1", "q2", "q5d", "q0", "q0", "0","true", "Bład zwracam 11"},
            {"q12re", "q1", "q2", "q5d", "q0", "q0","0", "true", "Bład zwracam 12"},
            {"q13re", "q1", "q2", "q5d", "q0", "q0", "0","true", "Bład zwracam 13"},
            {"q8re", "q1", "q2", "q5d", "q0", "q0", "0","true", "Bład zwracam 8"},
    };

    private Map<String, String[]> stateMap;
    private String currentState;
    private OnDialogCallback callback;
    private String history;

    public VendingStateMachine(OnDialogCallback onDialogCallback) {
        stateMap = new HashMap<String, String[]>();
        for (String[] stateDef : states) {
            stateMap.put(stateDef[0], Arrays.copyOfRange(stateDef, 1, 9));
        }
        checkStateMapIntegrity();
        callback = onDialogCallback;
        currentState = "q0";
        history = currentState;
    }

    private void checkStateMapIntegrity() {
        for (String[] stateDef : states) {
            for (int i = 0; i < 6; i++) {
                if (!stateMap.containsKey(stateDef[i])) {
                    throw new RuntimeException("Integrity is compromised on state: " + stateDef[i] + " in state def: " + stateDef[0]);
                }
            }
        }
    }

    void changeState(INPUT input) {
        String[] currentStateDf = stateMap.get(currentState);
        switch (input) {
            case ONE:
                currentState = currentStateDf[0];
                break;
            case TWO:
                currentState = currentStateDf[1];
                break;
            case FIVE:
                currentState = currentStateDf[2];
                break;
            case TEA:
                currentState = currentStateDf[3];
                break;
            case COFFE:
                currentState = currentStateDf[4];
                break;
        }
        if (stateMap.get(currentState)[6].equals("true")) {
            callback.accept(stateMap.get(currentState)[7]);
        }

        history += " " + currentState;
    }

    public String getCurrentCoins() {
        return stateMap.get(currentState)[5];
    }

    public String getHistory() {
        return history;
    }
}
