package com.nfmdev.rpsgame.tui;

public final class View {
    public String render(RpsModel model) {
        String mainScreen = """
            ===============================================
            ||              ROCK PAPER SCISSORS          ||
            ===============================================

            Round %d of %d

            Score
            ——————————————————————————————————————————————
            You:    %d
            Rival:  %d

            Choose your move
            [r] Rock
            [p] Paper
            [s] Scissors
        """.formatted(
            model.getCurrentRound(),
            model.getTotalRounds(),
            model.getPlayerWins(),
            model.getMachineWins()
        );
        String help = Styles.FOOTER_STYLE.render(model.help.render(model.keys));
        return Styles.APP_STYLE.render(new String[]{mainScreen, help});
    }
}