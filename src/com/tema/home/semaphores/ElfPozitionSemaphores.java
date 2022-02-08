package com.tema.home.semaphores;


import java.text.MessageFormat;
import java.util.concurrent.Semaphore;

public class ElfPozitionSemaphores extends Thread {

    private int number;//numarul elfului
    private int xPozition;//coordonata x curenta a elfului in fabrica
    private int yPozition;//coordonata y curenta a elfului in fabrica
    private static int counter = 0;
    private IndustrySemaphores toyIndustry;//fabrica care contine elful
    private static Semaphore semaphore = new Semaphore(1);

    public ElfPozitionSemaphores(int number, int xPozition, int yPozition, IndustrySemaphores toyIndustry) {
        this.number = number;
        this.xPozition = xPozition;
        this.yPozition = yPozition;
        this.toyIndustry = toyIndustry;
    }

    public void run() {
        do {
            if (Math.abs(xPozition - yPozition) > 1) {
                continue;
            }
            System.out.println(MessageFormat.format("Elful cu numarul {0} se afla la ({1},{2}) in fabrica {3}", number, xPozition, yPozition, number));
            if (!semaphore.tryAcquire()) {
                continue;
            } else {
                counter += 1;
                semaphore.release();
            }
            if (!(counter < toyIndustry.getSize())) {
                continue;
            }
            do {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    extracted(e);
                }
            } while (!(counter >= toyIndustry.getSize()));
        } while (true);
    }

    private void extracted(InterruptedException e) {
        e.printStackTrace();
    }

    /*
     * metoda care ne genereaza pozitia fiecarui elf in fabrica
     */
    public void reportElfPosition() {
        System.out.println(MessageFormat.format("Elful cu numarul {0} se afla la ({1},{2}) in fabrica {3}", number, xPozition, yPozition));
    }

    public int getxPozition() {
        return xPozition;
    }

    public int getyPozition() {
        return yPozition;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


}

