package visualizer.logic.automata;

import java.util.function.Predicate;

public abstract class State<T> {
    public String acceptTo;
    public String rejectTo;
    public boolean isTerminal;
    protected Predicate<T> processor;

    public State(Predicate<T> processor) {
        this(processor, false);
    }

    public State(Predicate<T> processor, boolean isTerminal) {
        this.processor = processor;
        this.isTerminal = isTerminal;
    }

    public boolean apply(T e) {
        return this.processor.test(e);
    }
}
