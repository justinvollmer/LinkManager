package app;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class InputObject implements Serializable {
    @Serial
    private static final long serialVersionUID = -6151682804935689639L;
    private String input;
    public InputObject(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InputObject inputText = (InputObject) o;
        return Objects.equals(input, inputText.input);
    }

    @Override
    public int hashCode() {
        return Objects.hash(input);
    }
}
