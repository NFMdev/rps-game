package com.nfmdev.rpsgame.tui;

import com.williamcallahan.tui4j.compat.bubbles.key.Binding;
import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;

public final class TestTuiModel implements Model {
    private final KeyMap keyMap = KeyMap.defaultKeyMap();

    @Override
    public Command init() {
        return Command.none();
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage keyPressMessage) {
            if (Binding.matches(keyPressMessage, keyMap.quit())) {
                return UpdateResult.from(this, Command.quit());
            }
        }

        return UpdateResult.from(this);
    }

    @Override
    public String view() {
        return """
            ===============================================
                          ROCK PAPER SCISSORS              
            ===============================================

            TUI4J runs correctly

            Press q or esc to quit
        """;
    }

}
