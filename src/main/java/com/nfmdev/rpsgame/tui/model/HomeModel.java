package com.nfmdev.rpsgame.tui.model;

import com.nfmdev.rpsgame.tui.View;
import com.nfmdev.rpsgame.tui.keymap.HomeKeyMap;
import com.williamcallahan.tui4j.compat.bubbles.help.Help;
import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;

public final class HomeModel implements Model {

    private final View view;

    public final HomeKeyMap keys = new HomeKeyMap();
    public final Help help = new Help();

    private final String[] MENU_OPTIONS = new String[]{"New Game\n", "Stats\n", "Settings\n","Exit\n"};
    private int selectedOption = 0;

    public HomeModel() {
        this.view = new View();
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
                case "up" -> selectPreviousOption();
                case "down" -> selectNextOption();
                case "enter" -> {
                }
            }
        }

        return UpdateResult.from(this);
    }

    @Override
    public String view() {
        return view.renderHomeScreen(this);
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    public String[] getMenuOptions() {
        return MENU_OPTIONS;
    }

    private void selectPreviousOption() {
        if (selectedOption > 0) this.selectedOption--;
    }

    private void selectNextOption() {
        if (selectedOption < MENU_OPTIONS.length-1) this.selectedOption++;
    }
}