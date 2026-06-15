package com.nfmdev.rpsgame;

import com.nfmdev.rpsgame.tui.RpsModel;
import com.williamcallahan.tui4j.compat.bubbletea.Program;

public final class RpsTuiMain {
    public static void main(String[] args) {
        new Program(new RpsModel()).withAltScreen().run();
    }
}