
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Analizador_Lexico {

    private static final String[] PALABRAS_RESERVADAS = {"class", "float", "int", "read", "write"};
    private static final List<Character> CARACTERES_SIMPLES = new ArrayList<>();
    ArrayList<String> simbolos = new ArrayList();
    String[] simbol = new String[50];

    static {
        CARACTERES_SIMPLES.add(';');
        CARACTERES_SIMPLES.add('=');
        CARACTERES_SIMPLES.add('+');
        CARACTERES_SIMPLES.add('-');
        CARACTERES_SIMPLES.add('*');
        CARACTERES_SIMPLES.add('(');
        CARACTERES_SIMPLES.add(')');
        CARACTERES_SIMPLES.add('{');
        CARACTERES_SIMPLES.add('}');
        CARACTERES_SIMPLES.add(',');
    }

    public String[] analizadorLexicoFromFile(String filePath) {
        List<String> tokens = new ArrayList<>();
        StringBuilder codigoFuente = new StringBuilder();
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // Leer línea por línea y construir el código fuente
            while (scanner.hasNextLine()) {
                codigoFuente.append(scanner.nextLine());
                codigoFuente.append("\n");
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + filePath);

        }

        tokens = analizadorLexico(codigoFuente.toString());

        // Convertir la lista de tokens a un arreglo de strings
        String[] tokensArray = new String[tokens.size()];
        tokensArray = tokens.toArray(tokensArray);

        return tokensArray;
    }

    public List<String> analizadorLexico(String codigoFuente) {
        List<String> tokens = new ArrayList<>();
        StringBuilder lexema = new StringBuilder();
        String palabra;
        int estado = 0;

        for (int i = 0; i < codigoFuente.length(); i++) {
            char caracter = codigoFuente.charAt(i);

            switch (estado) {
                case 0:
                    if (esLetra(caracter)) {
                        lexema.append(caracter);
                        estado = 1;
                    } else if (esDigito(caracter)) {
                        lexema.append(caracter);
                        estado = 2;
                    } else if (esCaracterSimple(caracter)) {
                        lexema.append(caracter);
                        estado = 3;
                    } else if (caracter == '.') {
                        lexema.append(caracter);
                        estado = 4;
                    }
                    break;
                case 1: // Identificador
                    if (esLetra(caracter) || esDigito(caracter) || caracter == '_') {
                        lexema.append(caracter);
                    } else {
                        palabra = lexema.toString();
                        if (esPalabraReservada(palabra)) {
                            tokens.add("Palabra reservada: " + palabra);
//                            if(palabra.equals("class")||palabra.equals("write")||palabra.equals("read"))
//                            simbolos.add(palabra);
//                            else if(palabra.equals("int")||palabra.equals("float"))
//                                simbolos.add("tipo");
                            simbolos.add(palabra);

                        } else {
                            tokens.add("Identificador: " + palabra);
                            simbolos.add("id");
                            for (int j = 0; j < simbol.length; j++) {
                                if (simbol[j] == null) {
                                    simbol[j] = "id";
                                }
                            }
                        }
                        lexema.setLength(0);
                        estado = 0;
                        i--;
                    }
                    break;
                case 2: // Número entero
                    if (esDigito(caracter)) {
                        lexema.append(caracter);
                    } else {
                        tokens.add("Número entero: " + lexema.toString());
                        lexema.setLength(0);
                        estado = 0;
                        i--;
                        simbolos.add("enteros");
                        for (int j = 0; j < simbol.length; j++) {
                            if (simbol[j] == null) {
                                simbol[j] = "int";
                            }
                        }
                    }
                    break;
                case 3: // Caracter simple
                    tokens.add("Caracter simple: " + lexema.toString());
                    simbolos.add(lexema.toString());
                    for (int j = 0; j < simbol.length; j++) {
                        if (simbol[j] == null) {
                            simbol[j] = lexema.toString();
                        }
                    }
                    lexema.setLength(0);
                    estado = 0;
                    i--;

                    break;
                case 4: // Número de punto flotante
                    if (esDigito(caracter)) {
                        lexema.append(caracter);
                        estado = 5;
                    } else {
                        // Error: Se esperaba al menos un dígito después del punto
                        tokens.add("Error: número de punto flotante inválido");
                        lexema.setLength(0);
                        estado = 0;
                        i--;
                    }
                    break;
                case 5: // Número de punto flotante (parte decimal)
                    if (esDigito(caracter)) {
                        lexema.append(caracter);
                    } else {
                        tokens.add("Número de punto flotante: " + lexema.toString());
                        simbolos.add("reales");
                        for (int j = 0; j < simbol.length; j++) {
                            if (simbol[j] == null) {
                                simbol[j] = lexema.toString();
                            }
                        }
                        lexema.setLength(0);
                        estado = 0;
                        i--;
                    }
                    break;
            }
        }

        // Verificar el estado final
        if (estado == 1) {
            palabra = lexema.toString();
            if (esPalabraReservada(palabra)) {
                tokens.add("Palabra reservada: " + palabra);
            } else {
                tokens.add("Identificador: " + palabra);
            }
        } else if (estado == 2) {
            tokens.add("Número entero: " + lexema.toString());
        } else if (estado == 4 || estado == 5) {
            tokens.add("Error: número de punto flotante inválido");
        }

        return tokens;
    }

    private static boolean esLetra(char caracter) {
        return Character.isLetter(caracter);
    }

    private static boolean esDigito(char caracter) {
        return Character.isDigit(caracter);
    }

    private static boolean esCaracterSimple(char caracter) {
        return CARACTERES_SIMPLES.contains(caracter);
    }

    private static boolean esPalabraReservada(String palabra) {
        for (String reservada : PALABRAS_RESERVADAS) {
            if (reservada.equals(palabra)) {
                return true;
            }
        }
        return false;
    }

    public void imprime() {
        for (String simbolo : simbolos) {
            System.out.println(simbolo);
        }
    }
}
