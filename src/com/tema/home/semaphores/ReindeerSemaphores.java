package com.tema.home.semaphores;

import java.text.MessageFormat;
import java.util.Random;

public class ReindeerSemaphores extends Thread {

    private int number;//numarul renului
    private IndustrySemaphores industries[];//toate fabricile existente in atelier
    private GiftSemaphores giftQueue;

    public ReindeerSemaphores(IndustrySemaphores industries[], int number, GiftSemaphores giftQueue) {

        this.industries = industries;
        this.number = number;
        this.giftQueue = giftQueue;
    }

    public void run() {

        do {
            /* primirea cadoului de la fabrica */
            int gift = 0;
            try {
                gift = TransferGiftFromIndustry();
            } catch (Exception e) {
                e.printStackTrace();
            }

            switch (gift) {
                case 0 -> {
                    System.out.println(MessageFormat.format("Renul cu numarul {0} a primit cadoul cu numarul {1} ", number, gift));
                    TransferGiftToSantaClaus(gift);
                }
                default -> throw new IllegalStateException("Unexpected value: " + gift);
            }
            // Sleeping between 10 and 30 milliseconds
            Random rand = new Random();
            long millis = rand.nextInt(30) + 10;
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    //transfera un cadou lui Mos Craciun, il adauga in coada
    private void TransferGiftToSantaClaus(int gift) {
        giftQueue.giveGift(gift);
        System.out.println(MessageFormat.format("Renul cu numarul {0} i-a trimis lui Mos Craciun cadoul cu numarul {1} ", number, gift));
    }

    //intra intr-o fabrica aleatorie din cele deja existente si ia un cadou de acolo
    private int TransferGiftFromIndustry() {
        Random rand = new Random();
        int industry = rand.nextInt(SantaWorkshopSemaphores.nrIndustries);
        /* solicitarea unui cadou de la fabrica aleasa */
        try {
            return industries[industry].getGiftForChristmas();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return industry;
    }

}
