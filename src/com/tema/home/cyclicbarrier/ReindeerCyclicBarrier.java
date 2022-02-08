package com.tema.home.cyclicbarrier;

import java.text.MessageFormat;
import java.util.Random;

public class ReindeerCyclicBarrier extends Thread {

    private int number;//numarul renului
    private IndustryCyclicBarrier industries[];//toate fabricile existente in atelier
    private GiftCyclicBarrier giftQueue;

    public ReindeerCyclicBarrier(IndustryCyclicBarrier industries[], int number, GiftCyclicBarrier giftQueue) {

        this.industries = industries;
        this.number = number;
        this.giftQueue = giftQueue;
    }

    public void run() {

        while (true) {
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
        int industry = rand.nextInt(SantaWorkshopCyclicBarrier.nrIndustries);
        /* solicitarea unui cadou de la fabrica aleasa */
        return industries[industry].getGiftForChristmas();
    }

    private void extracted(InterruptedException e) {
        e.printStackTrace();
    }

}
