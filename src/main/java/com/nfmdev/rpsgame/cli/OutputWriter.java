package com.nfmdev.rpsgame.cli;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Objects;

public final class OutputWriter {
    private final PrintWriter writer;

    public OutputWriter(PrintStream stream) {
        this.writer = new PrintWriter(
            Objects.requireNonNull(stream, "Print writer cannot be null"),
            true
        );
    }

    public void print(String message) {
        writer.print(message);
    }

    public void println(String message) {
        writer.println(message);
    }

    public void println() {
        writer.println();
    }
}