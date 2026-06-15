package com.nfmdev.rpsgame.tui.keymap;

import com.williamcallahan.tui4j.compat.bubbles.help.KeyMap;
import com.williamcallahan.tui4j.compat.bubbles.key.Binding;

public final class GameKeyMap implements KeyMap {
    public GameKeyMap() {}

    public Binding quitBinding = new Binding(
        Binding.withKeys("q", "ctrl+c", "esc"),
        Binding.withHelp("q/esc", "quit")
    );

    public Binding rock = new Binding(
        Binding.withKeys("r"),
        Binding.withHelp("r", "ROCK")
    );

    public Binding paper = new Binding(
        Binding.withKeys("p"),
        Binding.withHelp("p", "PAPER")
    );

    public Binding scissors = new Binding(
        Binding.withKeys("s"),
        Binding.withHelp("s", "SCISSORS")
    );

    @Override
    public Binding[] shortHelp() {
        return new Binding[]{rock, paper, scissors, quitBinding};
    }

    @Override
    public Binding[][] fullHelp() {
        return new Binding[][]{
            {rock, paper, scissors},
            {quitBinding}
        };
    }
}