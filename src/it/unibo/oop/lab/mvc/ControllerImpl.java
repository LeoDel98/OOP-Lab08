package it.unibo.oop.lab.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ControllerImpl implements Controller {
    
    private String currentString;
    private final List<String> history;
    
    public ControllerImpl() {
        this.history = new ArrayList<>();
    }

    @Override
    public void setNextStringToPrint(final String s) throws NullPointerException {
        this.currentString = Objects.requireNonNull(s, "This method does not accept null values.");
        /*try {
            this.currentString = s;
        } catch (final NullPointerException e) {
            String msg = "This method does not accept null values.";
            System.out.println("Eccezione: " + e + " Errore: " + msg);
        }*/
    }

    @Override
    public String getNextStringToPrint() {
        return this.currentString;
    }

    @Override
    public List<String> getHistoryOfPrintedStrings() {
        return this.history;
    }

    @Override
    public void printCurrentString() throws IllegalStateException {
        if (this.currentString == null) {
            throw new IllegalStateException("no string set");
        }
        System.out.println(this.currentString);
        this.history.add(this.currentString);
    }

}
