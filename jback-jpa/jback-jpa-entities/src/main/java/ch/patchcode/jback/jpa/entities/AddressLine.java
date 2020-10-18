package ch.patchcode.jback.jpa.entities;

import javax.persistence.Embeddable;

@Embeddable
public class AddressLine {

    private int line;

    private String value;

    public static AddressLine of(int line, String value) {

        var result = new AddressLine();
        result.setLine(line);
        result.setValue(value);
        return result;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
