package com.nfmdev.rpsgame.tui;

import com.williamcallahan.tui4j.compat.bubbles.help.Help;
import com.williamcallahan.tui4j.compat.bubbles.key.Binding;
import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;

public final class RpsModel implements Model {
    private static final int DEFAULT_TOTAL_ROUNDS = 3;

    private final View view;

    private int currentRound;
    private int totalRounds;
    private int playerWins;
    private int machineWins;

    public final RpsKeyMap keys = new RpsKeyMap();
    public final Help help = new Help();

    public RpsModel() {
        this.view = new View();
        this.currentRound = 1;
        this.totalRounds = DEFAULT_TOTAL_ROUNDS;
        this.playerWins = 0;
        this.machineWins = 0;
    }

    @Override
    public Command init() {
        return Command.none();
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage keyPressMessage) {
            if (Binding.matches(keyPressMessage, keys.quitBinding)) {
                return UpdateResult.from(this, Command.quit());
            }
        }

        return UpdateResult.from(this);
    }

    @Override
    public String view() {
        return view.render(this);
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public void setTotalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
    }

    public int getPlayerWins() {
        return playerWins;
    }

    public void setPlayerWins(int playerWins) {
        this.playerWins = playerWins;
    }
    
    public int getMachineWins() {
        return machineWins;
    }

    public void setMachineWins(int machineWins) {
        this.machineWins = machineWins;
    }
}