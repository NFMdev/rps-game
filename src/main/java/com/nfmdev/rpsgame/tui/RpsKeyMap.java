package com.nfmdev.rpsgame.tui;

import com.williamcallahan.tui4j.compat.bubbles.help.KeyMap;
import com.williamcallahan.tui4j.compat.bubbles.key.Binding;

public final class RpsKeyMap implements KeyMap {
    public RpsKeyMap() {}

    public Binding quitBinding = new Binding(
        Binding.withKeys("q", "ctrl+c", "esc"),
        Binding.withHelp("q/esc", "quit")
    );

    @Override
    public Binding[] shortHelp() {
        return new Binding[]{quitBinding};
    }

    @Override
    public Binding[][] fullHelp() {
        return new Binding[][]{
            {quitBinding}
        };
    }
}