package com.tema.home.initialimplementation;

import java.text.MessageFormat;
import java.util.Random;

public class Reindeer {

    private int number;//numarul renului
    private Industry industries[]; //toate fabricile existente in atelier
    private Gift giftQueue;

    public Reindeer(Industry industries[], int number, Gift giftQueue) {

        this.industries = industries;
        this.number = number;
        this.giftQueue = giftQueue;
    }

    public void run() throws Exception {

        while (true) {
            /* primirea cadoului de la fabrica */
            int gift = TransferGiftFromIndustry();

            switch (gift) {
                case 0:
                    break;
                default:
                    System.out.println(MessageFormat.format("Renul cu numarul {0} a primit cadoul cu numarul {1} ", number, gift));
                    TransferGiftToSantaClaus(gift);
                    break;
            }
            // Sleeping between 10 and 30 milliseconds
            Random rand = new Random();
            long millis = rand.nextInt(30) + 10;
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                extracted(e);
            }
        }
    }

    //transfera un cadou lui Mos Craciun, il adauga in coada
    private void TransferGiftToSantaClaus(int gift) {
        giftQueue.giveGift(gift);
        System.out.println(MessageFormat.format("Renul cu numarul {0} i-a trimis lui Mos Craciun cadoul cu numarul {1} ", number, gift));
    }

    //intra intr-o fabrica aleatorie din cele deja existente si ia un cadou de acolo
    private int TransferGiftFromIndustry() throws Exception {
        Random rand = new Random();
        int industry = rand.nextInt(SantaWorkshop.nrIndustries);
        /* solicitarea unui cadou de la fabrica aleasa */
        return industries[industry].getGiftForChristmas();
    }

    private void extracted(InterruptedException e) {
        e.printStackTrace();
    }
}
