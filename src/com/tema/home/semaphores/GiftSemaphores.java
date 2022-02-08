package com.tema.home.semaphores;

public class GiftSemaphores {
    private volatile int head = 0;//capul de coada
    private volatile int tail = 0;//coada cozii
    private final int[] giftsCount = new int[10];//numarul de cadouri care urmeaza sa fie transferate

    //metoda utilizata de Mos Craciun pentru a primi un cadou de la reni
    public synchronized int receiveGift() {
        /* asteptam pana cand buffer ul nu mai este gol */
        if (tail == head) {
            do {
                try {
                    wait();
                } catch (InterruptedException e) {
                    extracted(e);
                }
            } while (tail == head);
        }
        /* primire cadou */
        var gift = giftsCount[head % giftsCount.length];
        head += 1;
        /* notificati ca buffer ul nu este plin */
        notifyAll();
        return gift;
    }

    //metoda utilizata de un ren pentru a transmite un cadou lui Mos Craciun
    public synchronized void giveGift(int gift) {
        if (tail - head == giftsCount.length) {
            do {
                try {
                    wait();
                } catch (InterruptedException e) {
                    extracted(e);
                }
            } while (tail - head == giftsCount.length);
        }
        giftsCount[tail % giftsCount.length] = gift;
        /* adaugare cadou */
        tail += 1;
        notifyAll();
    }

    private void extracted(InterruptedException e) {
        e.printStackTrace();
    }
}
