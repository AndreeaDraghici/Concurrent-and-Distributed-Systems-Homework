package com.tema.home.initialimplementation;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class SantaWorkshop {

    public static int nrIndustries; //numarul de fabrici existente
    private Industry[] industries; //toate fabricile existente
    private ElvesGenerator generators[];//toti elfii existenti
    public static volatile int nrTotalElves = 1;//numarul total de elfi existenti
    private static final ReentrantLock elvesCounterLock = new ReentrantLock();//un lacat pentru numarul de elfi existenti
    private Reindeer[] reindeers;//toti renii existenti
    private Gift giftQueue;//mijloc de transfer de cadouri
    private static volatile Semaphore elfRetireSemaphore = new Semaphore(0); //un semafor pentru odihnirea elfilor

    public static ReentrantLock getElvesCounterLock() {
        return elvesCounterLock; //returneaza blocarea contorului pentru elfi
    }

    public SantaWorkshop(Gift giftQueue) {
        this.giftQueue = giftQueue;
    }

    public static Semaphore getElfRetireSemaphore() {
        return elfRetireSemaphore;
    }

    public void createIndustryMethod() {
        /*
         * creeaza toate fabricile
         * genereaza elfii, renii si incepe executarea lor
         */
        Random rand = new Random();
        nrIndustries = rand.nextInt(4) + 2;
        int nrReindeers = rand.nextInt(10) + 8;
        industries = new Industry[nrIndustries];
        generators = new ElvesGenerator[nrIndustries];
        reindeers = new Reindeer[nrReindeers];

        List<String> asList = Arrays.asList(MessageFormat.format("Au fost create {0} fabrici", nrIndustries), MessageFormat.format("Au fost creati {0} reni", nrReindeers));
        for (int i = 0; i < asList.size(); i++) {
            String s = asList.get(i);
            System.out.println(s);
        }
        IntStream.range(0, nrIndustries).forEachOrdered(i -> {
            int number = rand.nextInt(500) + 100;
            industries[i] = new Industry(number, i + 1);
            generators[i] = new ElvesGenerator(industries[i]);
            System.out.println(MessageFormat.format("Fabrica {0} are {1} elfi",
                    i + 1,
                    number));
        });
        IntStream.range(0, nrReindeers).forEachOrdered(i -> {
            reindeers[i] = new Reindeer(industries, i + 1, giftQueue);
        });
        IntStream.range(0, nrIndustries).forEachOrdered(i -> {
            generators[i].start();
            industries[i].start();
        });

    }
}
