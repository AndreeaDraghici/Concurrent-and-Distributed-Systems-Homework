package com.tema.home.initialimplementation;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import static com.tema.home.initialimplementation.SantaWorkshop.*;

@SuppressWarnings("ALL")
public class ElvesGenerator extends Thread {

    private Industry industry; //frabrica pentru care firul va crea elfii

    public ElvesGenerator(Industry industry) {
        this.industry = industry;
    }

    public void createElf() {
        Random rand = new Random();
        ReentrantLock factoryLock = industry.getFactoryLock();
        // elfii nu se pot misca in timp ce in fabrica este adugat un nou elf
        factoryLock.lock();
        var industryCount = industry.getSize();
        if (industry.numberOfElves() == (industryCount / 2)) {
            factoryLock.unlock();
        } else {
            var X = rand.nextInt(industryCount);
            var Y = rand.nextInt(industryCount);
            ReentrantLock elvesCounterLock = getElvesCounterLock();
            //niciun alt fir nu poate accesa nr de elfi din fabrica
            //acesta este modificat acum
            elvesCounterLock.lock();
            // creaza un nou elf
            ElfPozition elf = new ElfPozition(nrTotalElves, X, Y, industry);
            // adauga elfii in fabrica
            if (industry.addElves(elf)) {
                nrTotalElves += 1;
                System.out.println(MessageFormat.format("Elful cu numarul {0} a fost creat in fabrica {1} cu succes ",
                        elf.getNumber(),
                        industry.getNumber()));
                elvesCounterLock.unlock();
            } else {
                elvesCounterLock.unlock();
            }
            factoryLock.unlock();
        }
    }


    public void run() {
        do {
            Random rand = new Random();
            long millis = rand.nextInt(1000) + 500;
            // Sleeping a random time between 500 and 1000 milliseconds
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                extracted(e);
            }

            // crearea unui elf
            createElf();
        } while (true);
    }

    private void extracted(InterruptedException e) {
        e.printStackTrace();
    }
}
