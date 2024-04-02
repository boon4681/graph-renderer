package visualizer.logic.automata;

public class IntEq1State extends State<Integer> {
    public IntEq1State(String acceptTo, String rejectTo) {
        this(acceptTo, rejectTo, false);
    }

    public IntEq1State(String acceptTo, String rejectTo, boolean isTerminal) {
        super((e) -> e == 1, isTerminal);
        this.acceptTo = acceptTo;
        this.rejectTo = rejectTo;
    }
}
