package com.tema.home.initialimplementation;

import java.text.MessageFormat;

import static com.tema.home.initialimplementation.SantaWorkshop.getElfRetireSemaphore;

public class ElfPozition extends Thread {
    private int number; //numarul elfului
    private int xPozition; //coordonata x curenta a elfului in fabrica
    private int yPozition; //coordonata y curenta a elfului in fabrica
    private Industry toyIndustry; //fabrica care contine elful

    //contructorul clasei Elf
    public ElfPozition(int number, int xPozition, int yPozition, Industry toyIndustry) {

        this.number = number;
        this.xPozition = xPozition;
        this.yPozition = yPozition;
        this.toyIndustry = toyIndustry;
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
        new ElfDirection(xPozition);
        new ElfDirection(yPozition);
    }


    /*
     * metoda care ne genereaza pozitia curenta a  fiecarui elf in fabrica
     */
    public void reportElfPosition() {
        System.out.println(MessageFormat.format("Elful cu numarul {0} se afla la ({1},{2}) in fabrica {3}",
                number, xPozition, yPozition, toyIndustry.getNumber()));
        return;
    }


    public void run() {
        do {
            /* semafor pe care elfii incearca sa-l achizitioneze pentru “a primi
            permisiunea sa se retraga”.*/
            getElfRetireSemaphore().release();
            // Sleeping 50 milliseconds
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                extracted(e);
            }
        } while (true);
    }

    private void extracted(InterruptedException e) {
        e.printStackTrace();
    }

    //metodele de get si set pentru encapsularea datelor noastre
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
