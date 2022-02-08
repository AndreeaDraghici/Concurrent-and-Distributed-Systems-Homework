package com.tema.home.initialimplementation;

import java.text.MessageFormat;

public class SantaClaus extends Thread {

    private Gift gift;

    public SantaClaus(Gift gift) {
        this.gift = gift;
    }

    public void run() {
        do {
            //Mos Craciun va primi cadouri la nesfarsit
            int gifts = gift.receiveGift();
            System.out.println(MessageFormat.format("Mos Craciun a pus cadoul cu numarul {0} in sacul sau ",
                    gifts));
        } while (true);
    }

}
