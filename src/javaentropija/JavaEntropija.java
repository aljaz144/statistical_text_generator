package javaentropija;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import jdk.nashorn.internal.objects.NativeArray;

/**
 * @author 
 * Aljaz Gaber
 * E5034453
 * 1.letnik MAG TK-UNI
 */

public class JavaEntropija {

    public static double abeceda(String input) {
        return input.chars()
                .distinct()
                .count();
    }

    public static String preberi_file(String file2) throws FileNotFoundException, UnsupportedEncodingException, IOException {

        BufferedReader br = new BufferedReader(new FileReader(file2));
        String everything;
        String after;
        String zamenjaj;

        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } finally {
            br.close();
        }

        zamenjaj = everything.replaceAll("\\p{Punct}", "").toLowerCase();

        after = zamenjaj.replace("\n", "").replace("\r", " ").replace("\t", "").replaceAll(" +", " ");
        System.out.println(after);

        return after;

    }

    private static double prestej_besede(String besedilo) {

        int st = 0;
        String state = "OUT";
        String beseda;

        for (int i = 0; i < besedilo.length(); i++) {

            if (besedilo.charAt(i) == ' ' || besedilo.charAt(i) == '\n' || besedilo.charAt(i) == '\t') {
                state = "OUT";
            } else if (state == "OUT") {
                state = "IN";
                st++;
            }
        }

        return st;
    }

    private static Map<Integer, Ovoj> napolni_map_pogojna_ovoj(Set<String> set) {

        Map<Integer, Ovoj> data = new HashMap<Integer, Ovoj>();
        int i = 0;
        String ena;
        String dva;
        Ovoj bigram;
        String[] splitStr;

        for (String key : set) {

            String str = key;
            splitStr = str.split("\\s+");
            ena = splitStr[0];
            dva = splitStr[1];
            bigram = new Ovoj(ena, dva);

            data.put(i, bigram);

            i++;

        }

        return data;

    }

    private static ArrayList<String> napolni_arraylist_pogojna(List list) {

        String vpis_v_map;
        ArrayList<String> data = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {

            if (i + 1 < list.size()) {

                data.add(((String) list.get(i)) + " " + ((String) list.get(i + 1)));
            } else if (i + 1 == list.size()) {
            }
        }

        return data;

    }

    private static List poisci_besede(String besedilo) {
        List<String> list = new ArrayList<String>();
        String state = null;
        String bes = "";
        int st = 0;
        System.out.println("");
        System.out.println("število znakov: " + besedilo.length());

        String[] words = besedilo.split("\\s+");

        for (int i = 0; i < words.length; i++) {
            list.add(words[i]);
            //System.out.println("izpis besed : " + words[i]);
            //nameToQuantityMap.put(words[i], i);
        }

        return list;
    }

    private static int generiraj(int vrednost) {

        Random r = new Random();
        int randomValue = 0 + r.nextInt(vrednost - 0 + 1);
        return randomValue;

    }

    public static int utezena_izbira(List<String> besede, List<Double> utezi) {

        double utezi_skupaj = 0;

        for (int i = 0; i < utezi.size(); i++) {
            utezi_skupaj += utezi.get(i);
        }

        int random_index = -1;
        double random = Math.random() * utezi_skupaj;

        for (int i = 0; i < besede.size(); ++i) {
            random -= utezi.get(i);

            if (random <= 0.0d) {
                random_index = i;
                break;
            }
        }

        return random_index;
    }

    public static void main(String[] args) throws IOException {

        String vsebina2;
        double stev;
        double pogojna_vejretnost = 0;
        double pogostost;
        double lastna_ver;
        double entropija_enako = 0;
        double prestete_crke = 0;
        double d_abeceda = 0;
        String lokacija_txt = "C:\\Users\\Uporabnik\\desktop\\q.txt";
        int izbira;

        Scanner in = new Scanner(System.in);

        List<String> list = new ArrayList<String>();//hrani celotno besedilo po eno besedo
        List<Double> lastne_ver_arr = new ArrayList<Double>();//hrani vrednosti lastnih verjetnosti
        List<Double> pogojne_ver = new ArrayList<Double>(); //hrani vrednosti pogojnih verjetnosti
        List<String> ovoj = new ArrayList<String>();//hrani celotno besedilo po dve besedi

        vsebina2 = preberi_file(lokacija_txt);
        list = poisci_besede(vsebina2);

        stev = prestej_besede(vsebina2); // število vseh besed
        prestete_crke = abeceda(vsebina2); // število vseh znakov oz. črk

        Set<String> unique = new HashSet<String>(list);
        d_abeceda = unique.size(); // koliko različnih besed vsebuje datoteka

        ovoj = napolni_arraylist_pogojna(list); //bigrami
        Set<String> unique_bigram = new HashSet<String>(ovoj);

        Map<Integer, Ovoj> map_pogojna_bigram = new HashMap<Integer, Ovoj>();
        map_pogojna_bigram = napolni_map_pogojna_ovoj(unique_bigram);

        System.out.println("preštete črke: " + prestete_crke);
        System.out.println("število besed: " + stev);
        System.out.println("število bigramov: " + unique_bigram.size());
        System.out.println("število različnih besed: " + d_abeceda);

        do {

            System.out.println("1. Pogostost besed in ocena lastnih verjenosti besed");
            System.out.println("2. Ocene pogojnih vejretnosti vseh besed v besedilu glede na predhodno besedo in izračun entropije, ko poznamo predhodno besedo");
            System.out.println("3. Entropija na besedo ob predpostavki, da so vse besede enako verjetne");
            System.out.println("4. Entropijo na besedo, ko upoštevamo lastne verjetnosti posameznih besed");
            System.out.println("5. Entropija, ko poznamo predhodno besedo");
            System.out.println("6. Markovov vir");
            System.out.println("7. Izpis bigramov");
            System.out.println("8. Izhod");
            System.out.println("Vpiši željeno izbiro:");

            izbira = in.nextInt();

            switch (izbira) {

                case 1:

                    lastne_ver_arr.clear();

                    for (String key : unique) {

                        pogostost = Collections.frequency(list, key);
                        lastna_ver = pogostost / stev;
                        lastna_ver = Math.round(lastna_ver * 100000);
                        System.out.println(key + ": " + pogostost + " (" + pogostost + "/" + stev + " oz. lastna verjetnost:" + lastna_ver / 100000 + ")");
                        lastne_ver_arr.add(lastna_ver);

                    }

                    break;

                case 2:

                    double A = 0;
                    double B = 0;
                    String beseda1_case2;
                    String beseda2_case2;
                    pogojne_ver.clear();

                    for (Integer key : map_pogojna_bigram.keySet()) {

                        beseda1_case2 = map_pogojna_bigram.get(key).getBeseda1();
                        beseda2_case2 = map_pogojna_bigram.get(key).getBeseda2();

                        A = Collections.frequency(list, beseda1_case2);
                        B = Collections.frequency(ovoj, beseda1_case2 + " " + beseda2_case2);

                        pogojna_vejretnost = B / A;
                        System.out.println(beseda1_case2 + "|" + beseda2_case2 + " : " + pogojna_vejretnost + " A= " + A + " B= " + B + " map_pogojna ");
                        pogojne_ver.add(pogojna_vejretnost);

                        //pogojna_entropija = pogojna_entropija + (-(stev * (pogojna_vejretnost * (Math.log(pogojna_vejretnost) / Math.log(2)))));
                    }

                    System.out.println("pog ver size " + pogojne_ver.size());
                    break;

                case 3:

                    double verjetnost_enako = 0;
                    verjetnost_enako = 1 / stev;
                    entropija_enako = -(stev * (verjetnost_enako * (Math.log(verjetnost_enako) / Math.log(2))));
                    entropija_enako = Math.round(entropija_enako * 100000);
                    System.out.println("Entropija pri enaki verjetnosti besed: " + entropija_enako / 100000 + " bitov");

                    break;

                case 4:

                    double entropija_dejansko = 0;

                    for (String key : unique) {
                        pogostost = Collections.frequency(list, key);
                        lastna_ver = pogostost / stev;
                        entropija_dejansko += ((-(lastna_ver * (Math.log(lastna_ver) / Math.log(2)))));
                    }

                    entropija_dejansko = Math.round(entropija_dejansko * 100000);
                    System.out.println("Entropijo na besedo, ko upoštevamo verjetnosti posameznih besed: " + entropija_dejansko / 100000 + " bitov");

                    break;

                case 5:

                    double pogojna_entropija = 0;
                    double pog_vr = 0;
                    int steve = 0;

                    if (pogojne_ver.isEmpty() == true) {

                        System.out.println("Najprej pritisni opcijo 2 !");

                    } else {

                        System.out.println("velikost " + pogojne_ver.size());
                        for (int i = 0; i < pogojne_ver.size(); i++) {

                            pog_vr = pogojne_ver.get(i);
                            pogojna_entropija += ((-(pog_vr * (Math.log(pog_vr) / Math.log(2)))));
                            steve++;
                        }

                        pogojna_entropija = Math.round(pogojna_entropija * 100000);
                        System.out.println("Entropijo na besedo glede na predhodno besedo: " + pogojna_entropija / 100000 + " bitov " + steve);

                    }
                    break;

                case 6:

                    String beseda1_case9;
                    String beseda2_case9;
                    int st_generiranih = 0;
                    int naslednja_izbira;

                    if (pogojne_ver.isEmpty() == true) {

                        System.out.println("Najprej pritisni opcijo 2 !");

                    } else {

                        System.out.println("Kolikšno število besed se naj generira: ");
                        st_generiranih = in.nextInt();

                        int izbrana_bes = generiraj(map_pogojna_bigram.size() / 2);
                        String prva_beseda = map_pogojna_bigram.get(izbrana_bes).getBeseda1();

                        for (int i = 0; i < st_generiranih; i++) {

                            List<String> druga_bes = new ArrayList<String>();
                            List<Double> verjetnost_markov = new ArrayList<Double>();

                            //System.out.println("izbrana beseda: " + prva_beseda);
                            for (Integer key : map_pogojna_bigram.keySet()) {

                                beseda1_case9 = map_pogojna_bigram.get(key).getBeseda1();
                                beseda2_case9 = map_pogojna_bigram.get(key).getBeseda2();

                                if (beseda1_case9.equals(prva_beseda)) {
                                    druga_bes.add(beseda2_case9);
                                    verjetnost_markov.add(pogojne_ver.get(key));
                                }

                            }

                            System.out.println("Izbrana prva beseda: " + prva_beseda);
                            System.out.println("Sledijo: " + druga_bes + " z verjetnostmi: " + verjetnost_markov);

                            if (druga_bes.size() == 0) {
                                naslednja_izbira = generiraj(map_pogojna_bigram.size() / 2);
                                prva_beseda = map_pogojna_bigram.get(naslednja_izbira).getBeseda1();
                            } else {
                                naslednja_izbira = utezena_izbira(druga_bes, verjetnost_markov);
                                prva_beseda = druga_bes.get(naslednja_izbira);
                            }

                        }
                    }
                    break;

                case 7:

                    Ovoj ovoj_izpis;
                    for (int i = 0; i < map_pogojna_bigram.size(); i++) {

                        ovoj_izpis = map_pogojna_bigram.get(i);
                        System.out.println("Izpis bigramov:");
                        System.out.println(i + ". (" + ovoj_izpis.getBeseda1() + ", " + ovoj_izpis.getBeseda2() + ")");

                    }

                    break;
            }
        } while (izbira != 8);
    }
}
