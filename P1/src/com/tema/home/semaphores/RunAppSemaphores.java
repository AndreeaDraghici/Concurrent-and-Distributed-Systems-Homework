package com.tema.home.semaphores;

public class RunAppSemaphores {
    public static void main(String[] args) {

        GiftSemaphores giftQueue = new GiftSemaphores();//creeaza coada de transfer a cadourilor
        SantaClausSemaphores Santa = new SantaClausSemaphores(giftQueue);//il creeaza pe Mos Craciun
        SantaWorkshopSemaphores workshop = new SantaWorkshopSemaphores(giftQueue);//creeza atelierul lui Mos Craciun
        workshop.createIndustryMethod();//atelierul incepe sa creeze fabrici
        Santa.start();//Mos Craciun incepe sa primeasca cadouri de la reni
    }
}
