package com.nfmdev.rpsgame.tui.keymap;

import com.williamcallahan.tui4j.compat.bubbles.help.KeyMap;
import com.williamcallahan.tui4j.compat.bubbles.key.Binding;

public final class HomeKeyMap implements KeyMap {
    public HomeKeyMap() {}

     public Binding quitBinding = new Binding(
        Binding.withKeys("q", "ctrl+c", "esc"),
        Binding.withHelp("q/esc", "quit")
    );

    public Binding up = new Binding(
        Binding.withKeys("up"),
        Binding.withHelp("↑", "previous option")
    );

    public Binding down = new Binding(
        Binding.withKeys(""),
        Binding.withHelp("↓", "next option")
    );

    public Binding select = new Binding(
        Binding.withKeys("enter"),
        Binding.withHelp("Enter ⏎", "select option")
    );

    @Override
    public Binding[] shortHelp() {
        return new Binding[]{up, down, select, quitBinding};
    }

    @Override
    public Binding[][] fullHelp() {
        return new Binding[][]{
            {up, down, select},
            {quitBinding}
        };
    }
}