package com.tema.home.initialimplementation;

public class ElfDirection {

    private final boolean[][] industry;
    private final int count;

    public ElfDirection(int count) {

        this.industry = new boolean[count][count];
        this.count = count;

    }

    /*elfii creaza cadouri mutandu-se intr-una din directiile stanga, dreapta, sus, jos */
    private boolean MoveLeft(int X, int Y) {
        boolean result = Y - 1 > 0 && !industry[X][Y - 1];
        return result;

    }

    private boolean MoveDown(int X, int Y) {
        boolean result = X + 1 < count && !industry[X + 1][Y];
        return result;
    }

    private boolean MoveUp(int X, int Y) {
        boolean result = X - 1 > 0 && !industry[X - 1][Y];
        return result;
    }

    private boolean MoveRight(int X, int Y) {
        boolean result = Y + 1 < count && !industry[X][Y + 1];
        return result;
    }

}
