package com.tema.home.cyclicbarrier;

import java.text.MessageFormat;

public class SantaClausCyclicBarrier extends Thread {
    private GiftCyclicBarrier gift;

    public SantaClausCyclicBarrier(GiftCyclicBarrier gift) {
        this.gift = gift;
    }

    public void run() {
        do {
            //Mos Craciun va primi cadouri la nesfarsit
            int gifts = gift.receiveGift();
            System.out.println(MessageFormat.format("Mos Craciun a pus cadoul cu numarul {0} in sacul sau ", gifts));
        } while (true);
    }
}
