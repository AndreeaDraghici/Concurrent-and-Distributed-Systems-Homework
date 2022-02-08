package com.tema.home.semaphores;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class ElvesGeneratorSemaphores extends Thread {

    private IndustrySemaphores semaphores;

    public ElvesGeneratorSemaphores(IndustrySemaphores semaphores) {
        this.semaphores = semaphores;
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

    private void createElf() {
        Random rand = new Random();
        ReentrantLock factoryLock = semaphores.getIndustryLock();

        // elfii nu se pot misca in timp ce in fabrica este adugat un nou elf
        factoryLock.lock();
        int industrySize = semaphores.getSize();
        if (semaphores.numberOfElves() != industrySize) {
            int xPos = rand.nextInt(industrySize);
            int yPos = rand.nextInt(industrySize);

            ReentrantLock elvesLock = SantaWorkshopSemaphores.getElvesCounterLock();
            elvesLock.lock();

            ElfPozitionSemaphores elfPozitionSemaphores = new ElfPozitionSemaphores(SantaWorkshopSemaphores.nrTotalElves, xPos, yPos, semaphores);
            if (semaphores.addElves(elfPozitionSemaphores)) {
                SantaWorkshopSemaphores.nrTotalElves++;
                System.out.println(MessageFormat.format("Elful cu numarul {0} a fost creat in fabrica {1} cu succes ", elfPozitionSemaphores.getNumber(), semaphores.getNumber()));
            }
            elvesLock.unlock();

        }
        factoryLock.unlock();
    }
}
