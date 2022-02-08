package com.tema.home.cyclicbarrier;


public class RunAppCyclicBarrier {
    public static void main(String[] args) {
        GiftCyclicBarrier gift = new GiftCyclicBarrier();//creeaza coada de transfer a cadourilor
        SantaClausCyclicBarrier santaClaus = new SantaClausCyclicBarrier(gift);//il creeaza pe Mos Craciun
        SantaWorkshopCyclicBarrier workshop = new SantaWorkshopCyclicBarrier(gift);//creeza atelierul lui Mos Craciun
        workshop.createIndustryMethod();//atelierul incepe sa creeze fabrici
        santaClaus.start();//Mos Craciun incepe sa primeasca cadouri de la reni
    }
}
