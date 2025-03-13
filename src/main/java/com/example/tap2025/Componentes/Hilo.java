package com.example.tap2025.Componentes;

public class Hilo extends Thread {

    public Hilo(String nombre) {
        super(nombre);
    }
    @Override
    //Tiempo de vida del hilo
    public void run() {
        super.run();
        for (int i = 1; i <= 10; i++) {
            try{
              sleep((long)(Math.random()*3000));
            } catch (InterruptedException e) {
            }
            System.out.println("El corredor"+ this.getName()+ " llego al KM"+ i);

        }
    }
}
