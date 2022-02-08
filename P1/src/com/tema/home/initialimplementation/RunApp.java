package com.tema.home.initialimplementation;

public class RunApp {
    public static void main(String[] args) {
        Gift gift = new Gift(); //creeaza coada de transfer a cadourilor
        SantaClaus santaClaus = new SantaClaus(gift); //il creeaza pe Mos Craciun
        SantaWorkshop workshop = new SantaWorkshop(gift); //creeza atelierul lui Mos Craciun
        workshop.createIndustryMethod();//atelierul incepe sa creeze fabrici
        santaClaus.start();//Mos Craciun incepe sa primeasca cadouri de la reni
    }
}
