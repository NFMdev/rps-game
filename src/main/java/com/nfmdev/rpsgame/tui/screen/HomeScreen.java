package com.nfmdev.rpsgame.tui.screen;

import com.nfmdev.rpsgame.tui.Styles;
import com.nfmdev.rpsgame.tui.keymap.HomeKeyMap;
import com.nfmdev.rpsgame.tui.model.HomeModel;
import com.williamcallahan.tui4j.compat.lipgloss.Position;
import com.williamcallahan.tui4j.compat.lipgloss.join.HorizontalJoinDecorator;
import com.williamcallahan.tui4j.compat.lipgloss.join.VerticalJoinDecorator;

public final class HomeScreen {
    public final HomeKeyMap keys = new HomeKeyMap();

    public String render(HomeModel model) {
        String header = Styles.TITLE_STYLE.render("ROCK PAPER SCISSORS");
        String body = HorizontalJoinDecorator.joinHorizontal(Position.Center, getMenu(model));
        String help = Styles.FOOTER_STYLE.render(model.help.render(model.keys));
        return Styles.APP_STYLE.render(header, "\n", body, help);
    }

    public String getMenu(HomeModel model) {
        String[] options = model.getMenuOptions();
        String[] menu = new String[options.length];

        for (int i = 0; i < options.length; i++) {
            String cursor = i == model.getSelectedOption() ? ">" : " ";
            menu[i] = cursor + options[i];
        }

        return VerticalJoinDecorator.joinVertical(Position.Center, menu);
    }
}