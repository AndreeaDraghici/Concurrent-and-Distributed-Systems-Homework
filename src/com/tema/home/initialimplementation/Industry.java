package com.tema.home.initialimplementation;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Industry extends Thread {

    private final int number; //numarul fabricii
    private final int size; //dimensiunea matricei(fabricii)
    private final boolean[][] industry;
    private final ArrayList<ElfPozition> elfArrayList = new ArrayList<>();//lista in care tinem pozitia elfilor
    private final ArrayList<Integer> gifts = new ArrayList<>();//lista cadourilor
    private final ReentrantLock industryLock = new ReentrantLock();//blocarea accesarii fabricii
    private final ReentrantLock elfListLock = new ReentrantLock();//un lacat pentru accesarea listei de elfi
    private final ReentrantLock giftLock = new ReentrantLock();//un lacat pentru accesarea listei de cadouri
    private final Semaphore reindeerSemaphore = new Semaphore(10); //semafor pentru numarul maxim permis de reni in fabrica

    public Industry(int size, int number) {
        this.industry = new boolean[size][size];
        this.number = number;
        this.size = size;
    }

    public int getSize() {
        return size;
    }
    public int getNumber() {
        return number;
    }
    public ReentrantLock getFactoryLock() {
        return industryLock;
    }
    public int numberOfElves() {
        return elfArrayList.size();
    }

    public void run() {
        do {
            /*
            fabrica este de asemenea un fir de executie
            metoda apelata care intreaba elfii existenti de pozitia lor
            */
            reportThePosition();
            /* Sleeping for 3000 milliseconds */
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (true);
    }

    private void reportThePosition() {
        try {
            /*
             elfii nu se pot misca in timp ce isi rapoarteaza pozitia
             renii nu pot primi cadouri in timp ce fabrica le cere elfilor pozitia
            */
            industryLock.lock();
            elfListLock.lock();
            giftLock.lock();
            Iterator<ElfPozition> iterator = elfArrayList.iterator();
            if (iterator.hasNext()) {
                do {
                    ElfPozition elf = iterator.next();
                    elf.reportElfPosition();
                } while (iterator.hasNext());
            }
        } catch (Exception e) {
            System.out.println(MessageFormat.format("Exceptie din industrie: {0}", e.getMessage()));
            extracted(e);
        } finally {
            elfListLock.unlock();
            industryLock.unlock();
            giftLock.unlock();

        }
    }

    /*
     * adauga un elf nou creat in fabrica
     * blocheaza accesarea listei de elfi
     * daca pozitia elfului nu a fost ocupata deja, il adaugam pe lista
     * apoi raportam pozitia actuala si deblocam lista de elfi
     */
    public boolean addElves(ElfPozition elfPozition) {
        var result = false;
        elfListLock.lock();

        var X = elfPozition.getxPozition();
        var Y = elfPozition.getyPozition();

        if (!industry[X][Y]) {
            industry[X][Y] = true;
            elfArrayList.add(elfPozition);
            elfPozition.start();
            elfPozition.reportElfPosition();
            elfListLock.unlock();
            result = true;
        }
        return result;
    }

    /*
     *  renii nu pot primi cadouri in timp ce un elf creeaza un cadou
     *  blocheaza accesarea listei de cadouri
     *  adauga cadoul in lista de cadouri
     *  deblocheaza lista de cadouri
     */
    private void createGiftForChristmas(int gift, int elfNo) {
        try {
            giftLock.lock();
            gifts.add(gift);
            System.out.println(MessageFormat.format("Elful cu numarul: {0} a creat cadoul: {1}", elfNo, gift));
        } catch (Exception e) {
            extracted(e);
        } finally {
            giftLock.lock();
        }
    }

    public int getGiftForChristmas() throws Exception {
        int gift;
        try {
            /* Acquire a reindeer permit */
            reindeerSemaphore.acquire();
            /* doi reni nu pot citi lista de cadouri în acelasi timp */
            giftLock.lock(); //blocheaza accesarea listei de cadouri
            gift = gifts.get(gifts.size() - 1);
            gifts.remove(gifts.size() - 1);
        } finally {
            giftLock.unlock(); //deblocheaza accesul la lista de cadouri
            reindeerSemaphore.release(); //elibereaza un permis de reni
        }
        return gift;
    }

    /*
     * metoda pentru sarcina suplimentara" retragerea unui elf
     * blocheaza accesul la lista de elfi
     * blocheaza accesul la lista fabricii
     * elimina pozitia elfului din lista fabricii
     * deblocheaza accesul la lista cu elfii
     * deblocheaza accesul la lista fabricii
     */
    public void retireElf(ElfPozition elfPozition) {

        try {
            /* modificarea listei elfilor si a pozitiei matricei fabricii */
            elfListLock.lock();//blocheaza accesul la lista de elfi
            industryLock.lock();//blocheaza accesul la lista fabricii
            elfArrayList.remove(elfPozition);//elimina pozitia elfului din lista fabricii
            int X = elfPozition.getxPozition();
            int Y = elfPozition.getyPozition();
            industry[X][Y] = false;
            System.out.println(MessageFormat.format("Elful cu numarul {0} s-a retras din fabrica cu numarul {1}", elfPozition.getNumber(), number));
        } finally {
            elfListLock.unlock();//deblocheaza accesul la lista cu elfii
            industryLock.unlock();//deblocheaza accesul la lista fabricii
        }
    }

    private void extracted(Exception e) {
        e.printStackTrace();
    }
}
