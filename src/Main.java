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
                if (coche.enBoxes>=120){
                    System.out.println("\033[31m ".repeat(coche.distancia)  + coche.piloto + " ("+"velocidad: "+ coche.velocidad +" desgaste: "+ coche.desgaste +" material rueda:"+")"+"\033[0m");

                } else {
                    System.out.print(" ".repeat(coche.distancia) +(coche.enBoxes > 0 ? "\033[34m" : "\033[32m") + coche.piloto + " ("+"velocidad: "+ coche.velocidad +" desgaste: "+ coche.desgaste +" material rueda: ");
                    coche.materialRueda();
                    System.out.print(" )");
                    System.out.println( );
                }

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
    int[] fiabilidadMotor;
    Random random = new Random();


    public Coche(String piloto, int[]fiabilidadMotor) {
        this.fiabilidadMotor= fiabilidadMotor;
        this.piloto = piloto;
    }


    void materialRueda (){
        Random random = new Random();
        int aleatorio2= random.nextInt(4);
        material2 = aleatorio2;
        if (material2==1 ){
            System.out.print("material soft");
            desgaste += velocidad*3.5;


        } else if (material2==0) {
            System.out.print("material soft");
            desgaste += velocidad*3.5;

        } else if (material2==2) {
            System.out.print("material medium");
            desgaste += velocidad*2.5;


        }else if (material2==3){
            System.out.print("material hard");
            desgaste += velocidad*1.5;

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

            if (desgaste >= 100) {
                Random random = new Random();
                int aleatorio = random.nextInt(4);
                enBoxes = aleatorio + 3;
                desgaste = 0;
                this.velocidad = 0;
            }

        }
    }
    public boolean tieneFalloDeMotor() {

        for (int i = 0; i <21; i++) {

            if(fiabilidadMotor==1){

                return random.nextInt(1000000) <100;

            } else if (fiabilidadMotor==2){

                return random.nextInt(100000) <100;

            } else if (fiabilidadMotor==3) {

                return random.nextInt(10000) <100;

            } else if (fiabilidadMotor==4) {

                return random.nextInt(1000) <100;

            }

        }

        return tieneFalloDeMotor;
    }

    void mover (){
        distancia += velocidad;


        enBoxes--;
        if (enBoxes < 0) {
            enBoxes = 0;
        }

        if (tieneFalloDeMotor()){
            enBoxes= 120;
            this.velocidad = 0;
            desgaste=0;
            for (int i = 0; i <200; i++) {

                enBoxes++;

            }
            System.out.println("\033[0m"+piloto+" tiene un fallo de motor y se detiene!");
        }


    }


}



class ex7 {
    public static void main(String[] args) {

        int[] fallos = new int[]{ 1 , 3 , 2 , 2 , 1 , 1 , 1, 1 ,2 ,4 ,3 ,2 ,4 , 3 ,1 , 1 , 1, 1, 1, 2 ,2
        };

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
            carrera.inscribir(new Coche(driver,fallos));
        }
        carrera.iniciar();
    }
}