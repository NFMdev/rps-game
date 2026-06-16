package com.nfmdev.rpsgame.tui.model;

import com.nfmdev.rpsgame.config.GameConfig;
import com.nfmdev.rpsgame.game.Move;
import com.nfmdev.rpsgame.game.Rules;
import com.nfmdev.rpsgame.game.Score;
import com.nfmdev.rpsgame.game.machine_move_generator.MachineMoveGenerator;
import com.nfmdev.rpsgame.tui.View;
import com.nfmdev.rpsgame.tui.keymap.GameKeyMap;
import com.williamcallahan.tui4j.compat.bubbles.help.Help;
import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;

public final class GameModel implements Model {
    private final View view;
    private final Rules rules;
    private final MachineMoveGenerator moveGenerator;
    private final Score score;
    private final GameConfig gameConfig;

    public final GameKeyMap keys = new GameKeyMap();
    public final Help help = new Help();

    public GameModel(
        Rules rules,
        MachineMoveGenerator moveGenerator,
        GameConfig gameConfig
    ) {
        this.view = new View();
        this.rules = rules;
        this.moveGenerator = moveGenerator;
        this.score = new Score();
        this.gameConfig = gameConfig;
    }

    @Override
    public Command init() {
        return Command.none();
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage key) {
            switch (key.key()) {
                case "q", "ctrl+c", "esc" -> {
                    return UpdateResult.from(this, Command.quit());
                }
                case "r" -> rules.resolveRound(Move.ROCK, moveGenerator.generateMove());
                case "p" -> rules.resolveRound(Move.PAPER, moveGenerator.generateMove());
                case "s" -> rules.resolveRound(Move.SCISSORS, moveGenerator.generateMove());
            }
        }

        return UpdateResult.from(this);
    }

    @Override
    public String view() {
        return view.renderGameScreen(this);
    }

    public Score getScore() {
        return score;
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }
}