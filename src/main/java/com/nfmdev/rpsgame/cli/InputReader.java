package com.nfmdev.rpsgame.cli;

import java.util.Objects;
import java.util.Scanner;

public final class InputReader {
    private final Scanner sc;

    public InputReader(Scanner sc) {
        this.sc = Objects.requireNonNull(sc, "Scanner cannot be null");
    }

    public String readLine() {
        return sc.nextLine();
    }
}