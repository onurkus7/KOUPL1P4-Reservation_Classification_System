
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class Kullanici {

    String kullanici_id = "0";
    String kategori_ismi = "0";
    int rezervasyon_sayisi = 0;
    Rezervasyon ilk = null;
    Rezervasyon son = null;

    public void rezerv_ekle(String yer_id, String rezervasyon_zamani, String enlem, String boylam, String sehir) {
        Rezervasyon r = new Rezervasyon(yer_id, rezervasyon_zamani, enlem, boylam, sehir);
        if (ilk == null) {
            ilk = r;
            son = r;
        } else {
            son.next = r;
            son = r;
        }

    }

    public void Listele() {
        Rezervasyon gecici = ilk;
        while (gecici != null) {
            System.out.println("yed id = " + gecici.yer_id + "  rezervasyon zamani = " + gecici.rezervasyon_zamani + "  Enlem= " + gecici.enlem + "  Boylam= " + gecici.boylam + "  Sehir= " + gecici.sehir);
            gecici = gecici.next;
        }

    }

    public void yer_id_goreListele(String yer) {
        Rezervasyon gecici = ilk;
        while (gecici != null) {
            if (yer.equalsIgnoreCase(gecici.yer_id)) {
                System.out.println(kullanici_id);
            }

            gecici = gecici.next;
        }

    }

}

class Rezervasyon {

    String yer_id;
    String rezervasyon_zamani;
    String enlem;
    String boylam;
    String sehir;
    Rezervasyon next;

    public Rezervasyon(String yer_id, String rezervasyon_zamani, String enlem, String boylam, String sehir) {
        this.yer_id = yer_id;
        this.rezervasyon_zamani = rezervasyon_zamani;
        this.enlem = enlem;
        this.boylam = boylam;
        this.sehir = sehir;
        this.next = null;
    }

}

public class Main {

    public static void main(String[] args) {
        Scanner klavye = new Scanner(System.in);

        ArrayList<String> dosya = new ArrayList<String>();

        //dosya okuyup bilgileri arrayliste atma
        try (Scanner scanner = new Scanner(new FileReader("rezervasyon.txt"))) {
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                dosya.add(s);

            }

        } catch (FileNotFoundException ex) {
            System.out.println("dosya bulunamadi");

        } catch (IOException ex) {
            System.out.println("dosya acilirken bir hata olustu");

        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<Kullanici> k = new ArrayList<Kullanici>();

        for (int i = 0; i < dosya.size(); i++) {
            int bayrak = 0;

            Kullanici x = new Kullanici();
            String[] ayir = dosya.get(i).split(",");

            x.kullanici_id = ayir[0];
            String[] alt_kategori = ayir[6].split(":");
            x.kategori_ismi = alt_kategori[alt_kategori.length - 1];
            x.rezervasyon_sayisi = 1;
            x.rezerv_ekle(ayir[1], ayir[2], ayir[3], ayir[4], ayir[5]);

            for (int m = 0; m < k.size(); m++) {
                if ((k.get(m).kullanici_id.equalsIgnoreCase(x.kullanici_id)) && (k.get(m).kategori_ismi.equalsIgnoreCase(x.kategori_ismi))) {
                    bayrak = 1;
                    break;
                }

            }
            if (bayrak == 1) {

                continue;
            }
            // System.out.println( x.kullanici_id+"  "+ x.kategori_ismi);
            for (int j = i + 1; j < dosya.size(); j++) {
                String[] ayir1 = dosya.get(j).split(",");
                String[] alt_kategori1 = ayir1[6].split(":");

                if (x.kullanici_id.equalsIgnoreCase(ayir1[0]) && x.kategori_ismi.equalsIgnoreCase(alt_kategori1[alt_kategori1.length - 1])) {
                    x.rezervasyon_sayisi++;
                    x.rezerv_ekle(ayir1[1], ayir1[2], ayir1[3], ayir1[4], ayir1[5]);
                }
            }
            k.add(x);

        }

        /*for (int i = 0; i < k.size(); i++) {
            System.out.println(k.get(i).kullanici_id+"  "+k.get(i).rezervasyon_sayisi+" "+k.get(i).kategori_ismi );
             k.get(i).Listele();
            
        }
         */
        while (true) {
            System.out.println("1- Kategori ile ilgili işlemler ");
            System.out.println("2-Kullanıcı ile ilgili işlemler ");
            System.out.println("3-Sorgu ve listeleme islemleri ");
            System.out.println("0- Cikis icin ");
            int secim = klavye.nextInt();
            klavye.nextLine();

            if (secim == 0) {
                break;
            } else if (secim == 1) {
                System.out.println("yapim asamasindaa!");
            } else if (secim == 2) {
                while (true) {
                    System.out.println("1- Kullanıcı ekleme");
                    System.out.println("2-Kullanıcı silme");
                    System.out.println("0-Cikis icin");
                    int secim2 = klavye.nextInt();
                    klavye.nextLine();

                    if (secim2 == 0) {
                        break;
                    } else if (secim2 == 1) {
                        System.out.println("Hangi kategoriye eklensin = ");
                        String isim = klavye.nextLine();
                        System.out.println("Kullanici id");
                        String kullanci = klavye.nextLine();
                        int bayrak = 0;
                        for (int i = 0; i < k.size(); i++) {
                            if (isim.equalsIgnoreCase(k.get(i).kategori_ismi) && kullanci.equalsIgnoreCase(k.get(i).kullanici_id)) {
                                System.out.println("boyle bir kullanici zaten var");
                                bayrak = 1;
                                break;
                            }

                        }
                        if (bayrak == 1) {
                            Kullanici s = new Kullanici();
                            s.kategori_ismi = isim;
                            s.kullanici_id = kullanci;
                            k.add(s);
                        }
                    } else if (secim2 == 2) {
                        while (true) {
                            System.out.println("1.Durumda belli bir kategorinin altında yer alan tüm kullanıcılar silinmelidir. ");
                            System.out.println("2.durumda  belli bir kategori altındaki belli bir kullanıcı bulunarak silinmelidir.");
                            System.out.println("3.durumda  belli bir kategori altındaki belli bir kullanıcı bulunarak silinmelidir.");
                            System.out.println("0 Cikis icin");
                            int secim22 = klavye.nextInt();
                            klavye.nextLine();
                            if (secim22 == 0) {
                                break;

                            } else if (secim22 == 1) {
                                System.out.println("1.Durumda belli bir kategorinin altında yer alan tüm kullanıcılar silinmelidir. ");
                                System.out.println("Alt kategori giriniz=");
                                String isim = klavye.nextLine();
                                ArrayList<Integer> silincek = new ArrayList<Integer>();
                                for (int i = 0; i < k.size(); i++) {
                                    if (isim.equalsIgnoreCase(k.get(i).kategori_ismi)) {
                                        silincek.add(i);
                                    }

                                }
                                for (int i = 0; i < silincek.size(); i++) {
                                    System.out.println(k.get(silincek.get(i) - i).kullanici_id + " kullanici silindi");
                                    k.remove(silincek.get(i) - i);

                                }

                            } else if (secim22 == 2) {
                                System.out.println("2.durumda  belli bir kategori altındaki belli bir kullanıcı bulunarak silinmelidir.");
                                System.out.println("Alt kategori giriniz=");
                                String isim = klavye.nextLine();
                                System.out.println("Silinecek kullanici giriniz");
                                String kullan = klavye.nextLine();
                                for (int i = 0; i < k.size(); i++) {
                                    if (isim.equalsIgnoreCase(k.get(i).kategori_ismi) && kullan.equalsIgnoreCase(k.get(i).kullanici_id)) {
                                        System.out.println("silindi" + k.get(i).kullanici_id);
                                        k.remove(i);
                                        break;
                                    }

                                }
                            } else if (secim22 == 3) {
                                System.out.println("3.durumda  belli bir kategori altındaki belli bir kullanıcı bulunarak silinmelidir.");
                                System.out.println("Kullanici id giriniz= ");
                                String isim = klavye.nextLine();
                                ArrayList<Integer> silincek = new ArrayList<Integer>();
                                for (int i = 0; i < k.size(); i++) {
                                    if (isim.equalsIgnoreCase(k.get(i).kullanici_id)) {
                                        silincek.add(i);
                                    }

                                }
                                for (int i = 0; i < silincek.size(); i++) {
                                    System.out.println(k.get(silincek.get(i) - i).kullanici_id + " kullanici silindi");
                                    k.remove(silincek.get(i) - i);

                                }

                            } else {
                                System.out.println("gecersiz islem");
                            }

                        }

                    }

                }

            } else if (secim == 3) {
                while (true) {
                    System.out.println("1-Kullanıcıya göre kategori listeleme");
                    System.out.println("2-Kategoriye göre kullanıcı listeleme");
                    System.out.println("3-Rezervasyon yerine göre kullanıcı listeleme");
                    System.out.println("4-Kullanıcıya göre rezervasyon listeleme");
                    System.out.println("0-Cikis icin");
                    int secim3 = klavye.nextInt();
                    klavye.nextLine();
                    if (secim3 == 0) {
                        break;
                    } else if (secim3 == 1) {
                        System.out.println("1-Kullanıcıya göre kategori listeleme");
                        System.out.println("Kullanici id giriniz = ");
                        String isim = klavye.nextLine();

                        for (int i = 0; i < k.size(); i++) {
                            if (k.get(i).kullanici_id.equalsIgnoreCase(isim)) {
                                System.out.println(k.get(i).kategori_ismi);
                            }

                        }
                    } else if (secim3 == 2) {
                        System.out.println("2-Kategoriye göre kullanıcı listeleme");
                        System.out.println("En alt kategori giriniz = ");
                        String isim = klavye.nextLine();
                        for (int i = 0; i < k.size(); i++) {
                            if (k.get(i).kategori_ismi.equalsIgnoreCase(isim)) {
                                System.out.println(k.get(i).kullanici_id);
                            }

                        }
                    } else if (secim3 == 3) {
                        System.out.println("3-Rezervasyon yerine göre kullanıcı listeleme");
                        System.out.println("Rezervasyon yer id giriniz = ");
                        String isim = klavye.nextLine();
                        for (int i = 0; i < k.size(); i++) {
                            k.get(i).yer_id_goreListele(isim);

                        }

                    } else if (secim3 == 4) {
                        System.out.println("4-Kullanıcıya göre rezervasyon listeleme");
                        System.out.println("Kullanici id giriniz  = ");
                        String isim = klavye.nextLine();
                        for (int i = 0; i < k.size(); i++) {
                            if (isim.equalsIgnoreCase(k.get(i).kullanici_id)) {
                                k.get(i).Listele();
                            }

                        }

                    } else {
                        System.out.println("gecersiz islem");
                    }

                }
            } else {

                System.out.println("gecersiz islem");

            }

        }

    }

}
