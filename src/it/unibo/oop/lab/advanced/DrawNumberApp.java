package it.unibo.oop.lab.advanced;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private final DrawNumber model;
    private final List<DrawNumberView> views;

    /**
     * @param configFile
     *            the configuration file path
     * @param views
     *            the views to attach
     */
    public DrawNumberApp(final String configFile, final DrawNumberView... views) {
        /*
         * Side-effect proof
         */
        this.views = Arrays.asList(Arrays.copyOf(views, views.length));
        for (final DrawNumberView view: views) {
            view.setObserver(this);
            view.start();
        }
        final Settings.Builder settingsBuilder = new Settings.Builder();
        try (var contents = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(configFile)))) {
            for (var configLine = contents.readLine(); configLine != null; configLine = contents.readLine()) {
                final String[] lineElements = configLine.split(":");
                if (lineElements.length == 2) {
                    final int value = Integer.parseInt(lineElements[1].trim());
                    if (lineElements[0].contains("max")) {
                        settingsBuilder.setMax(value);
                    } else if (lineElements[0].contains("min")) {
                        settingsBuilder.setMin(value);
                    } else if (lineElements[0].contains("attempts")) {
                        settingsBuilder.setAttempts(value);
                    }
                } else {
                    displayError("I cannot understand \"" + configLine + '"');
                }
            }
        } catch (IOException | NumberFormatException e) {
            displayError(e.getMessage());
        }
        final Settings settings = settingsBuilder.build();
        if (settings.isConsistent()) {
            this.model = new DrawNumberImpl(settings);
        } else {
            displayError("Inconsistent configuration: "
                + "min: " + settings.getMin() + ", "
                + "max: " + settings.getMax() + ", "
                + "attempts: " + settings.getAttempts() + ". Using defaults instead.");
            this.model = new DrawNumberImpl(new Settings.Builder().build());
        }
    }

    private void displayError(final String err) {
        for (final DrawNumberView view: views) {
            view.displayError(err);
        }
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            for (final DrawNumberView view: views) {
                view.result(result);
            }
        } catch (IllegalArgumentException | AttemptsLimitReachedException e) {
            for (final DrawNumberView view: views) {
                view.numberIncorrect();
            }
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @Override
    public void quit() {
        /*
         * A bit harsh. A good application should configure the graphics to exit by
         * natural termination when closing is hit. To do things more cleanly, attention
         * should be paid to alive threads, as the application would continue to persist
         * until the last thread terminates.
         */
        System.exit(0);
    }

    /**
     * @param args
     *            ignored
     * @throws FileNotFoundException 
     */
    public static void main(final String... args) throws FileNotFoundException {
        new DrawNumberApp("config.yml", // res is part of the classpath!
                new DrawNumberViewImpl(),
                new PrintStreamView(System.out),
                new PrintStreamView("output.log"));
    }
}