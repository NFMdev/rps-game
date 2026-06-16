package com.nfmdev.rpsgame.tui.screen;

import com.nfmdev.rpsgame.tui.Styles;
import com.nfmdev.rpsgame.tui.keymap.GameKeyMap;
import com.nfmdev.rpsgame.tui.model.GameModel;

public final class GameScreen {
    public final GameKeyMap keys = new GameKeyMap();

    public String render(GameModel model) {
        String header = "Round %d / %d — Player Score %d | Rival Score %d".formatted(
            model.getScore().getCurrentRound(),
            model.getGameConfig().rounds(),
            model.getScore().getPlayerScore(),
            model.getScore().getMachineScore()
        );

        return Styles.APP_STYLE.render(header);
    }
}