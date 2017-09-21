import java.io.*;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class KnightPlacement {
    static Stack<Integer> r = new Stack<>(); // R1...
    static int prodsk = 8;
    static int  n; // sachmatu lentos dydis
    static int nn; // langeliu skaicius sachmatu lentoje
    static int lenta[][];
    static int cx[] = new int[prodsk + 1]; // cx, cy - produkciju aibe
    static int cy[] = new int [prodsk + 1];
    static boolean yra = false;  // yra-isejimo parametras
    static int counter = 0;
    static int gylis = 0;
    static  String tab = "    "; // itrauka
    static String prodkBusena;
    static String back ="";
    static  BufferedWriter bw;
    static File fout;
    static FileOutputStream fos;

    static int bck = 0;

    public static void inicializuoti(int temp) {
        nn = temp * temp;
        lenta = new int[n + 1][n + 1];
        // suformuojama produkciju aibe
        cx[1] = 2; cy[1] = 1;
        cx[2] = 1; cy[2] = 2;
        cx[3] = -1; cy[3] = 2;
        cx[4] = -2; cy[4] = 1;
        cx[5] = -2; cy[5] = -1;
        cx[6] = -1; cy[6] = -2;
        cx[7] = 1; cy[7] = -2;
        cx[8] = 2; cy[8] = -1;

        // inicializuojama globali duomenu baze

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                lenta[i][j] = 0;
            }
        }

    }

    public static void  eiti(int l, int x, int y) throws IOException {  // l-ejimo numeris, x,y-paskutine zirgo padietis
        String str = String.join("", Collections.nCopies(gylis, "."));
        int k = 0; // produkcijos eiles nr
        int u, v; // nauja zirgo padietis


        do { // perinkimas produkcijos is 8 produkciju aibes
            k++;
            u = x + cx[k];
            v = y + cy[k];

            counter++;
            System.out.println();
            bw.write('\n');
            System.out.print(tab + String.format("%5s", counter) + ") " + str + "R" + k + ". " + "U=" + u  + ", V=" + v + "." + " L=" + l + ".");
            bw.write(tab + String.format("%5s", counter) + ") " + str + "R" + k + ". " + "U=" + u  + ", V=" + v + "." + " L=" + l + ".");

            if ((u >= 1) && (u <= n) && (v >= 1) && (v <= n)) {// tikrinama, ar duomenu baze tenkina produkcijos taikymo salyga.

                if (lenta[u][v] == 0) { // ar langelis laisvas, t.y ar zirgas jame nebuvo

                    lenta[u][v] = l; // nauja zirgo pozicija
                    bck = 0;
                    System.out.print(" Laisva " + " LENTA[" + u + "," + v + "]:=" + l + ".");
                    bw.write(" Laisva " + " LENTA[" + u + "," + v + "]:=" + l + ".");


                    if (l < nn) {  // jei lenta neapeita, tai bandoma daryti ejima
                        gylis++;
                        eiti(l+1, u, v);

                        if (!yra) { // jei buvo pasirinktas blogas kelias grizti atgal
                            lenta[u][v] = 0;
                            --gylis;
                            bck++;
                            //if (bck == 1){
                                System.out.print(" Backtrack.");
                                bw.write(" Backtrack.");
                            //}

                        }

                    }else {
                        yra = true;
                    }
                }else{
                    System.out.print(" Siūlas.");
                    bw.write(" Siūlas.");
                }
            }else{
                System.out.print(" Už krašto.");
                bw.write(" Už krašto.");
            }
        }while(!(yra || (k == 8)));
    }

    /*
    public static void print(int k, String str, int u, int v, int l) throws IOException {

        if(prodkBusena == "Laisva."){
            System.out.println(tab + String.format("%5s", counter) + ") " + str + "R" + k + ". " + "U=" + u  + ", V=" + v + "." + " L=" + l
                    + ". "+ prodkBusena + " LENTA[" + u + "," + v + "]:=" + l + ".");

            bw.write(tab + String.format("%5s", counter) + ") " + str + "R" + k + ". " + "U=" + u  + ", V=" + v + "." + " L=" + l
                    + ". "+ prodkBusena + " LENTA[" + u + "," + v + "]:=" + l + ". \n");
        }else{
            System.out.println(tab + String.format("%5s", counter) + ") " + str + "R" + k + ". " + "U=" + u  + ", V=" + v + "." + " L=" + l
                    + ". "+ prodkBusena + back);
            bw.write(tab + String.format("%5s", counter) + ") " + str + "R" + k + ". " + "U=" + u  + ", V=" + v + "." + " L=" + l
                        + ". "+ prodkBusena + back +  "\n") ;
        }
    }
    */

    public static void spausdintiLenta() throws Exception {
        System.out.println(tab + "Y, V ^");
        bw.write(tab + "Y, V ^" + '\n');
        for(int i = n; i >= 1; i--) {
            System.out.print(String.format(tab + "%4s",i) + " | ");
            bw.write(String.format(tab + "%4s",i) + " | ");
            for(int j = 1; j <= n; j++) {
                System.out.print(String.format("%2s",lenta[j][i]) + " ");
                bw.write(String.format("%2s",lenta[j][i]) + " ");
            }
            System.out.println();
            bw.write('\n');
        }
        System.out.println(tab +"     ---------------------------------> X, U");
        System.out.println();

        bw.write((tab +"     ---------------------------------> X, U"));
        bw.write('\n');


        for(int i = 1; i <= n; i++) {
            if(i ==  1) {
                System.out.print(tab + String.format("%9s", i));
                bw.write(tab + String.format("%9s", i));
            }else {
                System.out.print(String.format("%3s", i));
                bw.write(String.format("%3s", i));
            }
        }
    }

    public static void main(String[] args) throws  Exception{
        long startTime = System.currentTimeMillis();
        fout = new File("out.txt");
        fos = new FileOutputStream(fout);

        bw = new BufferedWriter(new OutputStreamWriter(fos));

        System.out.println("Iveskite sachmatu lentos dydi.");
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();

        if((n > 0) && (n <= 8)) {
            System.out.println("Ivesktite zirgo pradine padeti x ir y kordinates.");

            int x = sc.nextInt() ;
            int y = sc.nextInt() ;

            if((x <= n) && (x > 0) && (y <= n)  && (y > 0)){
                inicializuoti(n);

                lenta[x][y] = 1;

                System.out.println("1 DALIS. Duomenys");
                System.out.println(tab + "1) Lenta " + n  + "x" + n  + "." );
                System.out.println(tab + "2) Pradinė žirgo padėtis X=" + x + ",  Y=" + y + ".  L=" + 1 + ".");

                bw.write("1 DALIS. Duomenys " + '\n');
                bw.write(tab + "1) Lenta " + n  + "x" + n  + "." + '\n' );
                bw.write(tab + "2) Pradinė žirgo padėtis X=" + x + ",  Y=" + y + ".  L=" + 1 + "." + '\n' );

                System.out.println();
                System.out.println("2 DALIS. Vykdymas");

                bw.write('\n');
                bw.write ("2 DALIS. Vykdymas" + '\n');


                eiti(2, x, y);

                System.out.println();
                System.out.println();
                System.out.println("3 DALIS. Rezultatai");

                bw.write('\n');
                bw.write('\n');
                bw.write("3 DALIS. Rezultatai"+ '\n');


                if(yra) {
                    System.out.println(tab + "1) Apėjimas rastas");
                    System.out.println(tab + "2) Apėjimas pseudografika");
                    System.out.println();

                    bw.write(tab + "1) Apėjimas rastas" + '\n');
                    bw.write(tab + "2) Apėjimas pseudografika" + '\n');
                    bw.write('\n');
                    spausdintiLenta();
                }else {
                    System.out.println(tab + "Kelias neegzistuoja.");
                    bw.write(tab + "Kelias neegzistuoja." + '\n');
                }
            }else{
                System.out.println("Ivestos koordinates yra už ribų lentos");
                bw.write("Ivestos koordinates yra už ribų lentos" + '\n');
            }

        }else{
            System.out.println("Lentos dydis gali buti tik [1 - 8]");
            bw.write("Lentos dydis gali buti tik [1 - 8]" + '\n');

        }


        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println();
        System.out.println();
        System.out.println("Vykdymo laikas " + (totalTime /1000)+ "s.");

        bw.write('\n');
        bw.write('\n');
        bw.write("Vykdymo laikas " + (totalTime /1000) + "s.");

        sc.close();
        bw.close();
    }

}
