/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaentropija;

/**
 *
 * @author Aljaz
 */
public class Ovoj {
    
    public Ovoj(String beseda1, String beseda2) {
       this.beseda1 = beseda1;
       this.beseda2 = beseda2;
    }

    public String getBeseda1() { return this.beseda1; }
    public String getBeseda2() { return this.beseda2; }

    private final String beseda1;
    private final String beseda2;
}

/*private static String preberi() throws IOException {
        Document doc = null;
        String zamenjaj, after;
        System.setProperty("file.encoding", "UTF-8");
        String url = "http://lit.ijs.si/cund2.html";
        // številke tudi
        //integer za key
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println("Prišlo je do napake pri branju spletne strani !");
        }

        String body = doc.body().text();
        zamenjaj = body.replaceAll("\\p{Punct}", "").toLowerCase();
        after = zamenjaj.trim().replaceAll(" +", " ");

        after = after.replace("\n", "");
        after = after.replace("\t", "");
        return after;
    }*/