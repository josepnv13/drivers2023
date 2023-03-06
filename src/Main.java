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
                System.out.println(" ".repeat(coche.distancia) +(coche.enBoxes > 0 ? "\033[34m" : "\033[32m") + coche.piloto + " ("+"velocidad: "+ coche.velocidad +" desgaste: "+ coche.desgaste +" material rueda:"+ coche.material2+")");

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
        for (int i = 0; i <21; i++) {
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
                primero = coche;
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
    int enBoxes;
    int enpausa;
    static int maxVelocidad;
    boolean tieneFalloDeMotor;
    int material2;
    Random random = new Random();


    public Coche(String piloto) {
        this.piloto = piloto;
    }


    void material (){
        Random random = new Random();
        int aleatorio2= random.nextInt(3);
        material2 = aleatorio2 + 3;
        if (material2==1){
            System.out.println("material soft");


        } else if (material2==2) {
            System.out.println("material medium");


        }else if (material2==3){
            System.out.println("material hard");

        }

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

            if (desgaste >= 150) {
                Random random = new Random();
                int aleatorio = random.nextInt(4);
                enBoxes = aleatorio + 3;
                desgaste = 0;
                this.velocidad = 0;
            }

        }
    }
    public boolean tieneFalloDeMotor() {
        return random.nextInt(10000) <200;
    }

    void mover (){
        distancia += velocidad;

        desgaste += velocidad*2;

        enBoxes--;
        if (enBoxes < 0) {
            enBoxes = 0;
        }

        if (tieneFalloDeMotor()){
            enpausa=150;
            this.velocidad=0;
            desgaste=0;
            System.out.println("\033[0m"+piloto+" tiene un fallo de motor y se detiene!");
        }


    }


}



class ex7 {
    public static void main(String[] args) {

        String[] drivers = new String[]{
                "Max Verstappen", "Logan Sargeant", "Lando Norris",
                "Pierre Gasly", "Sergio Pérez", "Fernando Alonso",
                "Charles Leclerc","Lance Stroll","Kevin Magnussen","Kevin Magnussen","Nyck de Vries",
                "Yuki Tsunoda","Alexander Albon","Guanyu Zhou",
                "Nico Hülkenberg","Esteban Ocon","Lewis Hamilton","Carlos Sainz",
                "George Russell","Valtteri Bottas","Oscar Piastri"
        };
        Carrera carrera = new Carrera(120);
        for(String driver: drivers){
            carrera.inscribir(new Coche(driver));
        }
        carrera.iniciar();
    }
}