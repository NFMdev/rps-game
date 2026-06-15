package com.nfmdev.rpsgame.tui;

import com.williamcallahan.tui4j.compat.bubbles.key.Binding;

public final record KeyMap(
    Binding quit,
    Binding rock,
    Binding paper,
    Binding scissors
) {
    public static KeyMap defaultKeyMap() {
        return new KeyMap(
            new Binding(
                Binding.withKeys("q", "ctrl+c", "esc"),
                Binding.withHelp("q/esc", "quit")
            ),
            new Binding(
                Binding.withKeys("q", "ctrl+c", "esc"),
                Binding.withHelp("q/esc", "quit")
            ),
            new Binding(
                Binding.withKeys("q", "ctrl+c", "esc"),
                Binding.withHelp("q/esc", "quit")
            ),
            new Binding(
                Binding.withKeys("q", "ctrl+c", "esc"),
                Binding.withHelp("q/esc", "quit")
            )
        );
    }
}