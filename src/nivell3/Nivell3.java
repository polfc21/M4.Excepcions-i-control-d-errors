package nivell3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Nivell3 {
    public static void main(String[] args) {
        //FASE 1. Inicialitzem les variables que utilitzarem en el programa.
        int bitllet500, bitllet200, bitllet100, bitllet50, bitllet20, bitllet10,
                bitllet5, moneda2, moneda1, preuTotal;
        String[] plats = new String[10];
        int[] preus = new int[10];
        //FASE 2. Omplim els arrays, mostrem els arrays i preguntem que volen menjar.
        omplirArrays(plats, preus);
        mostrarArrays(plats, preus);
        ArrayList<String> platsDemanats = demanarPlats(plats);
        //FASE 3. Calcularem el preu total comparant la llista amb l'array per comprovar que els plats demanats existeixen.
        //Mostrem el preu total de la comanda i els billets amb els que s'haurà de pagar
        preuTotal = calcularPreu(plats, preus, platsDemanats);
        System.out.println("El preu total de la comanda és de " + preuTotal);
        if (preuTotal > 0){
            bitllet500 = preuTotal/500;
            System.out.println(bitllet500 + " bitllets de 500");
            preuTotal %= 500;
            bitllet200 = preuTotal/200;
            System.out.println(bitllet200 + " bitllets de 200");
            preuTotal %= 200;
            bitllet100 = preuTotal/100;
            System.out.println(bitllet100 + " bitllets de 100");
            preuTotal %= 100;
            bitllet50 = preuTotal/50;
            System.out.println(bitllet50 + " bitllets de 50");
            preuTotal %= 50;
            bitllet20 = preuTotal/20;
            System.out.println(bitllet20 + " bitllets de 20");
            preuTotal %= 20;
            bitllet10 = preuTotal/10;
            System.out.println(bitllet10 + " bitllets de 10");
            preuTotal %= 10;
            bitllet5 = preuTotal/5;
            System.out.println(bitllet5 + " bitllets de 5");
            preuTotal %= 5;
            moneda2 = preuTotal/2;
            System.out.println(moneda2 + " monedes de 2");
            preuTotal %= 2;
            moneda1 = preuTotal/1;
            System.out.println(moneda1 + " monedes de 1");
        }
    }
    //Mètode que s'encarrega d'omplir els arrays plats i preus mitjançant un HashMap
    public static void omplirArrays(String[] arrayPlats, int[] arrayPreus){
        int contador = 0;
        HashMap<String, Integer> preuPlat = new HashMap<String, Integer>();
        preuPlat.put("Bufalina", 12);
        preuPlat.put("4 Formaggi", 12);
        preuPlat.put("Pino Daniele", 18);
        preuPlat.put("Margherita", 9);
        preuPlat.put("Prosciutto", 10);
        preuPlat.put("Parmiggiana", 11);
        preuPlat.put("Al Tonno", 13);
        preuPlat.put("Carbonara", 11);
        preuPlat.put("Massimo Troise", 17);
        preuPlat.put("Ortolana", 12);
        for (Map.Entry<String, Integer> entry: preuPlat.entrySet()) {
            try {
                if (contador < arrayPlats.length){
                    arrayPlats[contador] = entry.getKey();
                    arrayPreus[contador] = entry.getValue();
                    contador++;
                } else throw new IntroduccioPlatsException("Error. Carta ja plena.");
            }catch (IntroduccioPlatsException e){
                e.getMessage();
                e.printStackTrace();
            }
        }
    }
    //Mètode que mostra el nom del plat i el preu corresponent
    public static void mostrarArrays(String[] arrayPlats, int[] arrayPreus){
        System.out.println("---------------------------------------");
        System.out.println("MENÚ");
        System.out.println("---------------------------------------");
        for (int i = 0; i < arrayPlats.length; i++){
            System.out.println("Nom plat: " + arrayPlats[i] + " - Preu: " + arrayPreus[i] + "€");
        }
        System.out.println("---------------------------------------");
    }
    //Mètode que retorna una llista amb els plats demanats pel client
    public static ArrayList<String> demanarPlats(String[] arrayPlats){
        int opcioDemanar;
        ArrayList<String> platsDemanats = new ArrayList<String>();
        String nomPlat;
        do {
            try{
                nomPlat = introduirPlat();
                int filaPlatArray = registrePlat(nomPlat, arrayPlats);
                if (filaPlatArray < arrayPlats.length) platsDemanats.add(nomPlat);
                else throw new RevisioPlatsException("Error. Plat incorrecte.");
            }catch (RevisioPlatsException e){
                e.getMessage();
                e.printStackTrace();
            }finally {
                opcioDemanar = preguntarUsuari();
            }
        }while(opcioDemanar == 1);
        return platsDemanats;
    }
    //Mètode que pregunta a l'usuari si vol seguir demanant plats o no
    public static int preguntarUsuari() {
        Scanner sc = new Scanner(System.in);
        boolean preguntar = true;
        int opcio = -1;
        while (preguntar){
            try {
                System.out.print("Vols seguir demanant plats?");
                System.out.println("Introdueix 1:Si / 0:No");
                if (sc.hasNextInt()) {
                    opcio = sc.nextInt();
                    if (opcio == 0 || opcio == 1) preguntar = false;
                    else throw new RevisioTipusException("Error. Valor incorrecte.");
                } else throw new RevisioTipusException("Error. Valor incorrecte.");
            } catch (RevisioTipusException e) {
                e.getMessage();
                e.printStackTrace();
            } finally {
                sc.nextLine();
            }
        }
        return opcio;
    }
    //Mètode que s'encarrega d'introduir el nom d'un plat i retornar-lo.
    public static String introduirPlat(){
        Scanner sc = new Scanner(System.in);
        String nomPlat;
        System.out.println("Introdueix el nom del plat que vols demanar i prem intro.");
        nomPlat = sc.nextLine();
        return nomPlat;
    }
    //Mètode que s'encarrega de calcular el preu total que tindrà la comanda
    public static int calcularPreu(String[] arrayPlats, int[] arrayPreus, ArrayList<String> llistaPlatsDemanats){
        int preuComanda = 0, filaPlatArray;
        String nomPlat;
        for (int i = 0; i < llistaPlatsDemanats.size(); i++){
            nomPlat = llistaPlatsDemanats.get(i);
            filaPlatArray = registrePlat(nomPlat, arrayPlats);
            preuComanda += arrayPreus[filaPlatArray];
        }
        return preuComanda;
    }
    //Mètode que retorna la fila de l'array en la que es troba el plat passat per paràmetre. Si no el troba, retorna la mida de l'array.
    public static int registrePlat(String nomPlat, String[] arrayPlats){
        int filaPlat = 0;
        while (filaPlat < arrayPlats.length){
            if (arrayPlats[filaPlat].equals(nomPlat)) return filaPlat;
            else filaPlat++;
        }
        return filaPlat;
    }
}
