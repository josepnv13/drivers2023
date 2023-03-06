import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Carrera {
    List<Coche> coches = new ArrayList<>();
    int distancia;
    Random random = new Random();

    public Carrera(int distancia) {
        this.distancia = distancia;
        Coche.maxVelocidad=distancia/15;
    }

    void iniciar(){
        while(true) {
            System.out.println("\033[0m" + "-".repeat(distancia));
            coches.forEach(coche -> {
                coche.acelerar(Coche.maxVelocidad /2-random.nextInt(Coche.maxVelocidad));
                coche.mover();
                System.out.println(" ".repeat(coche.distancia) +(coche.enBoxes > 0 ? "\033[31m" : "\033[32m") + coche.piloto + " ("+"velocidad: "+ coche.velocidad +" desgaste: "+ coche.desgaste + ")");

            });

            Coche primero = primeraPosicion();

            if (primero.distancia >= distancia) {
                imprimirPodium();
                return;
            }
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }
    }

    private void imprimirPodium() {
        for (int i = 0; i <3; i++) {
            Coche primero = primeraPosicion();
            System.out.println(i+1 +". " + primero.piloto);
            coches.remove(primero);
        }
    }
    void inscribir(Coche coche){
        coches.add(coche);
    }

    Coche primeraPosicion(){

        Coche primero = coches.get(0);
        for (Coche coche:coches) {
            if (coche.distancia > primero.distancia){
                primero=coche;
            }

        }

        return primero;

    }
}

class Coche {
    String piloto;
    int distancia;
    int velocidad;
    int desgaste;
    int enBoxes;   // 0
    static int maxVelocidad;
    boolean tieneFalloDeMotor;
    Random random = new Random();


    public Coche(String piloto) {
        this.piloto = piloto;

    }

    void acelerar(int velocidad){
        if (enBoxes > 0){

        } else {
            this.velocidad += velocidad - desgaste/90;

            if (this.velocidad > maxVelocidad) {
                this.velocidad = maxVelocidad;
            }

            if (this.velocidad < 1) {
                this.velocidad = 1;
            }

            if (desgaste >= 200) {
                Random random = new Random();
                int aleatorio = random.nextInt(4);
                enBoxes = aleatorio + 3;
                desgaste = 0;
                this.velocidad = 0;
            }
        }
    }
    public boolean tieneFalloDeMotor() {
        return random.nextInt(10000) <2; // 10% de probabilidad de fallo de motor
    }

    void mover (){
        distancia += velocidad;

        desgaste += velocidad*2;

        enBoxes--;
        if (enBoxes < 0) {
            enBoxes = 0;
        }
        if (tieneFalloDeMotor()){
            this.velocidad=0;
            System.out.println(piloto + " tiene un fallo de motor y se detiene!");
        }

    }


}



class ex7 {
    public static void main(String[] args) {

        Carrera carrera = new Carrera(120);
        carrera.inscribir(new Coche("Max Verstappen"));
        carrera.inscribir(new Coche("Logan Sargeant"));
        carrera.inscribir(new Coche("Lando Norris"));
        carrera.inscribir(new Coche("Pierre Gasly"));
        carrera.inscribir(new Coche("Sergio Pérez"));
        carrera.inscribir(new Coche("Fernando Alonso"));
        carrera.inscribir(new Coche("Charles Leclerc"));
        carrera.inscribir(new Coche("Lance Stroll"));
        carrera.inscribir(new Coche("Kevin Magnussen"));
        carrera.inscribir(new Coche("Nyck de Vries"));
        carrera.inscribir(new Coche("Yuki Tsunoda"));
        carrera.inscribir(new Coche("Alexander Albon"));
        carrera.inscribir(new Coche("Guanyu Zhou"));
        carrera.inscribir(new Coche("Nico Hülkenberg"));
        carrera.inscribir(new Coche("Esteban Ocon"));
        carrera.inscribir(new Coche("Lewis Hamilton"));
        carrera.inscribir(new Coche("Carlos Sainz"));
        carrera.inscribir(new Coche("George Russell"));
        carrera.inscribir(new Coche("Valtteri Bottas"));
        carrera.inscribir(new Coche("Oscar Piastri"));



        carrera.iniciar();

    }
}