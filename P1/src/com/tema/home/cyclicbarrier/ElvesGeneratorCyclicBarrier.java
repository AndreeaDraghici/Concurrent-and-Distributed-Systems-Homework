package com.tema.home.cyclicbarrier;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;


public class ElvesGeneratorCyclicBarrier extends Thread {

    private final IndustryCyclicBarrier industryCyclicBarrier; //frabrica pentru care firul va care elfii
    private final CyclicBarrier elfBarrier;

    public ElvesGeneratorCyclicBarrier(IndustryCyclicBarrier industryCyclicBarrier) {
        this.industryCyclicBarrier = industryCyclicBarrier;
        elfBarrier = new CyclicBarrier(industryCyclicBarrier.getSize());
    }

    public void run() {
        do {
            Random rand = new Random();
            long millis = rand.nextInt(1000) + 500;
            // Sleeping a random time between 500 and 1000 milliseconds
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // crearea unui elf
            createElf();
        } while (true);
    }

    public void createElf() {

        Random rand = new Random();
        ReentrantLock factoryLock = industryCyclicBarrier.getFactoryLock();

        // elfii nu se pot misca in timp ce in fabrica este adugat un nou elf
        factoryLock.lock();
        int industrySize = industryCyclicBarrier.getSize();
        if (industryCyclicBarrier.numberOfElves() != industrySize) {
            int xPos = rand.nextInt(industrySize);
            int yPos = rand.nextInt(industrySize);

            ReentrantLock elvesLock = SantaWorkshopCyclicBarrier.getElvesCounterLock();
            elvesLock.lock();

            ElfPozitionCyclicBarrier cyclicBarrier = new ElfPozitionCyclicBarrier(SantaWorkshopCyclicBarrier.nrTotalElves, xPos, yPos, industryCyclicBarrier, elfBarrier);
            if (!industryCyclicBarrier.addElves(cyclicBarrier)) {
            } else {
                SantaWorkshopCyclicBarrier.nrTotalElves++;
                System.out.println(MessageFormat.format("Elful cu numarul {0} a fost creat in fabrica {1} cu succes ",
                        cyclicBarrier.getNumber(),
                        IndustryCyclicBarrier.getNumber()));
            }
            elvesLock.unlock();

        }
        factoryLock.unlock();
    }
}
