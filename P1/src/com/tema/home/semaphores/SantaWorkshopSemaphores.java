package com.tema.home.semaphores;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class SantaWorkshopSemaphores {

    public static int nrIndustries;//numarul de fabrici existente
    private IndustrySemaphores[] industries;//toate fabricile existente
    private ElvesGeneratorSemaphores[] generators;//toti elfii existenti
    public static volatile int nrTotalElves = 1;//numarul total de elfi existenti
    private static final ReentrantLock elvesCounterLock = new ReentrantLock();//un lacat pentru numarul de elfi existenti
    private ReindeerSemaphores[] reindeers;//toti renii existenti
    private final GiftSemaphores giftQueue;//mijloc de transfer de cadouri
    private static final Semaphore elfRetireSemaphore = new Semaphore(0);//un semafor pentru odihnirea elfilor

    public void createIndustryMethod() {
        /*
         * creeaza toate fabricile
         * genereaza elfii, renii si incepe executarea lor
         */
        Random rand = new Random();
        nrIndustries = rand.nextInt(4) + 2;
        int nrReindeers = rand.nextInt(10) + 8;
        industries = new IndustrySemaphores[nrIndustries];
        generators = new ElvesGeneratorSemaphores[nrIndustries];
        reindeers = new ReindeerSemaphores[nrReindeers];

        List<String> asList = Arrays.asList(MessageFormat.format("Au fost create {0} fabrici", nrIndustries), MessageFormat.format("Au fost creati {0} reni", nrReindeers));
        for (int i = 0; i < asList.size(); i++) {
            String s = asList.get(i);
            System.out.println(s);
        }
        IntStream.range(0, nrIndustries).forEachOrdered(i -> {
            int number = rand.nextInt(500) + 100;
            industries[i] = new IndustrySemaphores(number, i + 1);
            generators[i] = new ElvesGeneratorSemaphores(industries[i]);
            System.out.println(MessageFormat.format("Fabrica {0} are {1} elfi",
                    i + 1,
                    number));
        });
        IntStream.range(0, nrReindeers).forEachOrdered(i -> {
            reindeers[i] = new ReindeerSemaphores(industries, i + 1, giftQueue);
        });
        IntStream.range(0, nrIndustries).forEachOrdered(i -> {
            generators[i].start();
            industries[i].start();
        });

    }

    public static ReentrantLock getElvesCounterLock() {
        return elvesCounterLock;
    }

    public SantaWorkshopSemaphores(GiftSemaphores giftQueue) {
        this.giftQueue = giftQueue;
    }

    public static Semaphore getElfRetireSemaphore() {
        return elfRetireSemaphore;
    }

}
