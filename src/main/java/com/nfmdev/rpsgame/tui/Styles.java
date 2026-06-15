package com.nfmdev.rpsgame.tui;

import com.williamcallahan.tui4j.compat.lipgloss.Borders;
import com.williamcallahan.tui4j.compat.lipgloss.Position;
import com.williamcallahan.tui4j.compat.lipgloss.Style;


public final class Styles {
    public static final Style APP_STYLE = Style.newStyle().align(Position.Center);
    public static final Style TITLE_STYLE = Style.newStyle().border(Borders.roundedBorder(), true).padding(0, 1, 0, 1).bold(true);
    public static final Style PANEL_STYLE = Style.newStyle().border(Borders.roundedBorder());
    public static final Style ERROR_STYLE = Style.newStyle().bold(true);
    public static final Style FOOTER_STYLE = Style.newStyle().paddingTop(1).faint(true); 
}