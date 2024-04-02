package visualizer.logic.automata;

import java.util.HashMap;
import java.util.Stack;

public class Finite {
    private static class Automata<T, V extends State<T>> {
        private HashMap<String, V> states = new HashMap<>();

        public void put(String name, V state) {
            states.put(name, state);
        }

        public AutomataResult run(T[] inputs) {
            Stack<AutomataRecord> stack = new Stack<>();
            boolean result = false;
            String from = "s0";
            for (int i = 0; i < inputs.length; i++) {
                T input = inputs[i];
                V current = states.get(from);
                if (current.apply(input)) {
                    stack.push(new AutomataRecord(from, current.acceptTo));
                    from = current.acceptTo;
                    if (i == inputs.length - 1 && states.get(from).isTerminal) {
                        result = true;
                    }
                } else {
                    stack.push(new AutomataRecord(from, current.rejectTo));
                    from = current.rejectTo;
                }
            }
            return new AutomataResult(result, stack);
        }
    }

    public static record AutomataRecord(String from, String to) {
        @Override
        public final String toString() {
            return from + " -> " + to;
        }
    }

    public static record AutomataResult(boolean result, Stack<AutomataRecord> records) {
    }

    public static AutomataResult integerInputAutomata(Integer[] inputs) {
        Automata<Integer, IntEq1State> automata = new Automata<Integer, IntEq1State>();
        automata.put("s0", new IntEq1State("s0", "s1"));
        automata.put("s1", new IntEq1State("s2", "s1"));
        automata.put("s2", new IntEq1State("s0", "s1", true));
        return automata.run(inputs);
    }
}
