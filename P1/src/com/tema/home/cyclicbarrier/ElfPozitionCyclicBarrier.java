package com.tema.home.cyclicbarrier;

import java.text.MessageFormat;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class ElfPozitionCyclicBarrier extends Thread {
    private int number;//numarul elfului
    private int xPozition;//coordonata x curenta a elfului in fabrica
    private int yPozition;//coordonata y curenta a elfului in fabrica
    private int numberOfGift = 0;
    private IndustryCyclicBarrier industry;//fabrica care contine elful
    private CyclicBarrier elfBarrier;

    //contructorul clasei Elf
    public ElfPozitionCyclicBarrier(int number, int xPozition, int yPozition, IndustryCyclicBarrier toyIndustry, CyclicBarrier elfBarrier) {

        this.number = number;
        this.xPozition = xPozition;
        this.yPozition = yPozition;
        this.industry = toyIndustry;
        this.elfBarrier = elfBarrier;
        this.numberOfGift = numberOfGift;
    }


    /*
     * metoda care ne ajuta sa schimbam pozitia in care un elf se afla.
     * doi elfi nu se pot afla in aceeasi pozitie.
     * @param xPozition coordonata rand
     * @param yPozition coordonata coloana
     */
    public void changeElfPosition(int newXPos, int newYPos) {
        xPozition = newXPos;
        yPozition = newYPos;
    }

    public void run() {
        while (true) {

            if (Math.abs(xPozition - yPozition) <= 1) {
                System.out.println(MessageFormat.format("Elful cu numarul {0} se afla la ({1},{2}) in fabrica {3}", number, xPozition, yPozition, number));
                try {
                    elfBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }

            numberOfGift = numberOfGift + number;

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (SantaWorkshopCyclicBarrier.getElfRetireSemaphore().tryAcquire()) {
                industry.retireElf(this);
                break;
            }
        }
    }

    /*
     * metoda care ne genereaza pozitia fiecarui elf in fabrica
     */
    public void reportElfPosition() {
        System.out.println(MessageFormat.format("Elful cu numarul {0} se afla la ({1},{2}) in fabrica {3}", number, xPozition, yPozition, IndustryCyclicBarrier.getNumber()));
    }

    //metodele de get
    public int getyPozition() {
        return yPozition;
    }

    public int getxPozition() {
        return xPozition;
    }

    public int getNumber() {
        return number;
    }

}