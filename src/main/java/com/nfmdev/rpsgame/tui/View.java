package com.nfmdev.rpsgame.tui;

import com.nfmdev.rpsgame.tui.model.GameModel;
import com.nfmdev.rpsgame.tui.model.HomeModel;
import com.nfmdev.rpsgame.tui.screen.HomeScreen;

public final class View {
    private final HomeScreen homeScreen = new HomeScreen();

    public String renderHomeScreen(HomeModel model) {
        return Styles.APP_STYLE.render(new String[]{homeScreen.render(model)});
    }

    public String render(GameModel model) {
        return Styles.APP_STYLE.render(new String[]{homeScreen.render(null)});
    }
}