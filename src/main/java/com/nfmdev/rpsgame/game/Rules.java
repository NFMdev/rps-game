package com.nfmdev.rpsgame.game;

public final class Rules {
    public Result resolveRound(Move playerMove, Move machineMove) {
        if (playerMove == machineMove) return Result.DRAW;

        return playerMove.beats(machineMove) ? Result.PLAYER_WINS : Result.MACHINE_WINS;
    }
}