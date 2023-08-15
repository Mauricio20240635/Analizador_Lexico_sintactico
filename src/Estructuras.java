
import Pila.Pila;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mauri
 */
public class Estructuras {

    Pila pd = new Pila();
    LinkedHashSet<String> terminales = new LinkedHashSet<>();
    LinkedHashSet<String> conjunto = new LinkedHashSet<>();
    String ladoderecho[] = new String[27];
    String[] simbolosnoterminales = new String[14];
    String[] simbolosterminales = new String[60];
    String[] temporal = new String[65];

    public void Leer() throws FileNotFoundException, IOException {
        String linea;
        String[] partes;
        String izquierdo, save, derecho;
        String[] izquierdopartes;

        BufferedReader br = new BufferedReader(new FileReader("Gramatica.txt"));
        while (br.ready()) {
            linea = br.readLine();
            partes = linea.split("→");
            izquierdo = partes[0];
            derecho = partes[1];
            izquierdopartes = izquierdo.split("-");
            save = izquierdopartes[1];
            comparador(save);
            guardarDerecho(derecho);

        }
        br.close();
    }

    public void comparador(String save) {
        boolean repetido = false;

        // Verificar si la palabra está repetida
        for (int i = 0; i < simbolosnoterminales.length; i++) {
            if (save.equals(simbolosnoterminales[i])) {
                repetido = true;

                break;

            }
        }

        if (!repetido) {
            // Encontrar un espacio vacío en el arreglo para guardar la palabra
            for (int i = 0; i < simbolosnoterminales.length; i++) {
                if (simbolosnoterminales[i] == null) {
                    simbolosnoterminales[i] = save.trim();

                    break;
                }
            }

        }
    }

    public void guardarDerecho(String derecho) {
        for (int i = 0; i < ladoderecho.length; i++) {
            if (ladoderecho[i] == null) {
                ladoderecho[i] = derecho;
//          
                break;
            }
        }
    }

    public void comparaTerminales() {
        ArrayList<String> noterminales = new ArrayList<String>(Arrays.asList(simbolosnoterminales));
        String save, temp;
        String[] guardar;
        int contador = 0; // Contador para el arreglo temporal
        int c = 0;
        for (int i = 0; i < ladoderecho.length; i++) {
            save = ladoderecho[i];
            guardar = save.split(" ");

            for (int j = 0; j < guardar.length; j++) {
                temp = guardar[j];
                temporal[contador] = temp; // Guardar la palabra en el arreglo temporal
                contador++; // Incrementar el contador

            }
        }
        for (int b = 0; b < temporal.length; b++) {
            if (temporal[b] != null) {
                //for (int i = 0; i < simbolosnoterminales.length; i++) {
//                    if (!temporal[b].equals(simbolosnoterminales[i])) {
                boolean contiene = !noterminales.contains(temporal[b])&&!temporal[b].equals("")&&!temporal[b].equals("ε");
                if (contiene) {
                    terminales.add(temporal[b]);

                }
                //}
            }
        }
      simbolosterminales = terminales.toArray(new String[18]);
    }

    public String calcularInicio() {
        String inicio = "";
//        for (int i = 0; i < simbolosnoterminales.length; i++) {
        ArrayList<String> noterminales = new ArrayList<String>(Arrays.asList(simbolosnoterminales));
        ArrayList<String> algo=new ArrayList<>();
        ArrayList<String> inicioahorasi=new ArrayList<>();
        String save, temp;
        String[] guardar;
        int contador = 0; // Contador para el arreglo temporal
        int c = 0;
        for (int i = 0; i < ladoderecho.length; i++) {
            save = ladoderecho[i];
            guardar = save.split(" ");

            for (int j = 0; j < guardar.length; j++) {
                temp = guardar[j];
                temporal[contador] = temp; // Guardar la palabra en el arreglo temporal
                contador++; // Incrementar el contador

            }
        }
        ArrayList<String> tempi = new ArrayList<String>(Arrays.asList(this.temporal));
        for (String elemento1 : noterminales) {
            for (String elemento2 : tempi) {
                if(elemento1.equals(elemento2))
                    algo.add(elemento1);
            }
            
        }
        for (String elemento1 : noterminales ) {
             if(!algo.contains(elemento1))
                 inicioahorasi.add(elemento1);
        }
        for (String string : inicioahorasi) {
           inicio=string; 
        }


        return inicio;

    }

    public void Imprimir() {
        boolean f = true;
        int caso = 0;
        Scanner leer = new Scanner(System.in);
        while (f) {
            System.out.println("Elija qué desea imprimir:");
            System.out.println("1. Símbolos terminales");
            System.out.println("2. Símbolos no terminales");
            System.out.println("3. Lado derecho");
            System.out.println("4. Salir del bucle");
            caso = leer.nextInt();
            switch (caso) {
                case 1: {
                    for (int i = 0; i < simbolosterminales.length; i++) {
                        System.out.println(simbolosterminales[i]);   
                    }
                    break;
                }
                case 2: {
                    for (int i = 0; i < simbolosnoterminales.length; i++) {
                        System.out.println(simbolosnoterminales[i]);
                    }
                    break;
                }
                case 3: {
                    for (int i = 0; i < ladoderecho.length; i++) {
                        System.out.println(ladoderecho[i]);
                    }
                    break;
                }

                case 4: {
                    f = false;
                
                }
               
            }
        }
    }
}
