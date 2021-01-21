//Stadium Box-Office simulation
//MERT DUMANLI 160315002
package SimulationAndModelling_TakeHome;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    static int kapasite = 50508;
    static int k0_rand = 129601;
    static int k_rand = 30;
    static int c_rand = 5;
    static int t = 9;
    static int part;
    static ArrayList<Integer> Kombineli_Yerler = new ArrayList<>();//Toplam kapasite elemanlı bir dizi//50.508
    static int bilet = 0;
    static int tahmin_edilen_kombine_sayısı = 40000;//+-500
    static ArrayList<String> Tribün_İsimleri = new ArrayList<>();
    static ArrayList<Integer> Bilet_Fiyatları = new ArrayList<>();//10 elemanlı bir dizi  
    static ArrayList<Integer> Bilet_Fiyatları2 = new ArrayList<>();//10 elemanlı bir dizi
    static ArrayList<Integer> Doluluk = new ArrayList<>();
    static ArrayList<Integer> Bilet_Geliri = new ArrayList<>();
    static ArrayList<Integer> Boyutlar = new ArrayList<>();
    static ArrayList<Integer> KombineFiyatları = new ArrayList<>();
    static ArrayList<Integer> EldeEdilenGelir = new ArrayList<>();//Tribünlerden elde edilen gelirler
    static ArrayList<Integer> SatılanBiletSayısı = new ArrayList<>();
    static int bedel = 0;
    static int tutar = 0;
    static int önceki_gelir = 0;
    /*
    KOMBINE FİYATLARI
    İç_Doğu_Üst //0     2000TL
    İç_Doğu_Orta //1    3000TL
    İç_Doğu_Alt //2     2000TL
    İç_Batı_Üst //3     2000TL
    İç_Batı_Orta //4    3000TL
    İç_Batı_Alt //5     2000TL
    İç_Kuzey //6        1000TL
    İç_Güney_Sol //7    1500TL
    İç_Güney_Orta //8   1500TL
    Deplasman //9       0TL-YOK
     */
    static ArrayList<Integer> İç_Doğu_Üst = new ArrayList<>();
    static ArrayList<Integer> İç_Doğu_Orta = new ArrayList<>();
    static ArrayList<Integer> İç_Doğu_Alt = new ArrayList<>();
    static ArrayList<Integer> İç_Batı_Üst = new ArrayList<>();
    static ArrayList<Integer> İç_Batı_Orta = new ArrayList<>();
    static ArrayList<Integer> İç_Batı_Alt = new ArrayList<>();
    static ArrayList<Integer> İç_Kuzey = new ArrayList<>();
    static ArrayList<Integer> İç_Güney_Sol = new ArrayList<>();//1/3
    static ArrayList<Integer> İç_Güney_Orta = new ArrayList<>();//1/3
    static ArrayList<Integer> Deplasman = new ArrayList<>();//1/3 İç_Güney_Sağ
    static ArrayList<Integer> İşlemeBaşlangıçZamanı = new ArrayList<>();//Sıradayken işleme alındıkları an.
    static ArrayList<Integer> İşlemeGirmedenBeklemeSüresi = new ArrayList<>();
    static ArrayList<Integer> İşlemBitişSüreleri = new ArrayList<>(); //
    static ArrayList<Integer> BoştaKalmaSüresi = new ArrayList<>();
    static ArrayList<Integer> BoştaKalmaSüresi_Gişeler = new ArrayList<>();
    static ArrayList<Integer> MüşterininHarcadığıZaman = new ArrayList<>();
    static ArrayList<Integer> MüşterilerinBeklemeSüresi = new ArrayList<>();
    static int i, a, b, c, x, y, z, q, size, size0, konum, s, k, k0, konum1, satılan_kombine, gişeno, sum, toti;
    static ArrayList<Integer> GelişSüreleri = new ArrayList<>();//Sıraya Giriş Vakitleri
    static ArrayList<Integer> İşlemSüreleri = new ArrayList<>();
    static ArrayList<Integer> Gişeler = new ArrayList<>();
    static ArrayList<Integer> BiletSayısı = new ArrayList<>();
    static ArrayList<Integer> Tribünler = new ArrayList<>();
    static ArrayList<Integer> Ücret = new ArrayList<>();
    static ArrayList<Integer> GişeIndex1 = new ArrayList<>();
    static ArrayList<Integer> GişeIndex2 = new ArrayList<>();
    static ArrayList<Integer> GişeIndex3 = new ArrayList<>();
    static ArrayList<Integer> GişeIndex4 = new ArrayList<>();
    static ArrayList<Integer> GişeIndex5 = new ArrayList<>();
    static ArrayList<Integer> GişeIndex6 = new ArrayList<>();
    static ArrayList<Integer> GişeIndex7 = new ArrayList<>();
    static ArrayList<Integer> GişeIndex8 = new ArrayList<>();
    static ArrayList<Integer> GişeIndex9 = new ArrayList<>();
    static ArrayList<Integer> GişeIndex10 = new ArrayList<>();
    static int gelişsüresi = 0;
    static int işlemsüresi = 0;

    public static void main(String[] args) throws IOException {
        Form form1 = new Form();
        form1.setVisible(true);
    }

    static void run() throws IOException {
        part = kapasite / 9;
        Random rand = new Random();
        for (i = 0; i < kapasite; i++) {
            Kombineli_Yerler.add(0);//0lar = henüz satılmamış anlamına gelecek.
        }
        //----------------------------------------------------------------------TRIBUN KAPASİTELERİN BELİRLENMESİ
        for (i = 0; i < part; i++) {//DOĞU-BATI
            İç_Doğu_Üst.add(Kombineli_Yerler.get(i));//5612
            İç_Doğu_Orta.add(Kombineli_Yerler.get(i + part));//5612
            İç_Doğu_Alt.add(Kombineli_Yerler.get(i + (2 * part)));//5612
            İç_Batı_Üst.add(Kombineli_Yerler.get(i + (3 * part)));//5612
            İç_Batı_Orta.add(Kombineli_Yerler.get(i + (4 * part)));//5612
            İç_Batı_Alt.add(Kombineli_Yerler.get(i + (5 * part)));//5612
        }
        for (i = 0; i < (3 * part) / 2; i++) {//KUZEY
            İç_Kuzey.add(Kombineli_Yerler.get(i + (6 * part)));//8418
        }
        for (i = 0; i < part / 2; i++) {//GÜNEY
            İç_Güney_Sol.add(Kombineli_Yerler.get(i + (15 * part) / 2));//2806
            İç_Güney_Orta.add(Kombineli_Yerler.get(i + 8 * part));//2806
            Deplasman.add(Kombineli_Yerler.get(i + (17 * part) / 2));//2806
        }
        //----------------------------------------------------------------------
        İşlemSüreleri.addAll(Kombineli_Yerler);
        GelişSüreleri.addAll(Kombineli_Yerler);
        Gişeler.addAll(Kombineli_Yerler);
        BiletSayısı.addAll(Kombineli_Yerler);
        Tribünler.addAll(Kombineli_Yerler);
        Ücret.addAll(Kombineli_Yerler);
//------------------------------------------------------------------------------İŞLEM SÜRELERİNİN SAKLANABİLMESİ İÇİN
        Boyutlar.add(Kombineli_Yerler.size());//50508//TOTAL KOMBİNELİ YERLERİN BOYUTU
        Boyutlar.add(İç_Doğu_Üst.size());
        Boyutlar.add(İç_Doğu_Orta.size());
        Boyutlar.add(İç_Doğu_Alt.size());
        Boyutlar.add(İç_Batı_Üst.size());
        Boyutlar.add(İç_Batı_Orta.size());
        Boyutlar.add(İç_Batı_Alt.size());
        Boyutlar.add(İç_Kuzey.size());
        Boyutlar.add(İç_Güney_Sol.size());
        Boyutlar.add(İç_Güney_Orta.size());
        Boyutlar.add(Deplasman.size());
//------------------------------------------------------------------------------
        KombineFiyatları.add(0);//Boyutlar dizisi ile senkronize olması rahat olması için.
        KombineFiyatları.add(2000);//İç_Doğu_Üst
        KombineFiyatları.add(3000);//İç_Doğu_Orta
        KombineFiyatları.add(2000);//İç_Doğu_Alt
        KombineFiyatları.add(2000);//İç_Batı_Üst
        KombineFiyatları.add(3000);//İç_Batı_Orta
        KombineFiyatları.add(2000);//İç_Batı_Alt
        KombineFiyatları.add(1000);//İç_Kuzey
        KombineFiyatları.add(1500);//İç_Güney_Sol
        KombineFiyatları.add(1500);//İç_Güney_Orta
        KombineFiyatları.add(0);//DEPLASMAN SATIŞI YOK
//------------------------------------------------------------------------------
        //Boyutlar ve KombineFiyatları dizileri ile senkronize olması için EldeEdilenGelir.get(0) = 0; TOTAL SATIŞ GELİRİNİ TUTUYOR.
        for (i = 0; i <= 10; i++) {
            EldeEdilenGelir.add(0);//En başta tüm gelir 0.
        }
//----------------------------------------------------------------------SATIŞA SUNULACAK TOPLAM KOMBİNE MİKTARI
        a = rand.nextInt(2);//Sıfır + , Bir -
        b = rand.nextInt(501);//0-500
        //kombine satışına sunulan tribün sayısı
        Tribün_İsimleri.add("NAMES OF TRIBUNES");
        Tribün_İsimleri.add("EAST TOP");
        Tribün_İsimleri.add("EAST MIDDLE");
        Tribün_İsimleri.add("EAST BOTTOM");
        Tribün_İsimleri.add("WEST TOP");
        Tribün_İsimleri.add("WEST MIDDLE");
        Tribün_İsimleri.add("WEST BOTTOM");
        Tribün_İsimleri.add("NORTH");
        Tribün_İsimleri.add("SOUTH LEFT");
        Tribün_İsimleri.add("SOUTH MIDDLE");
        Tribün_İsimleri.add("AWAY");
        if (a == 0) {
            i = tahmin_edilen_kombine_sayısı + b;
        }
        if (a == 1) {
            i = tahmin_edilen_kombine_sayısı - b;
        }
        if(i<500){
            i = i + 500;
        }//Satılacak kombine sayısı 1-500 arası girilip ve -500 satılacak ihtimali doğarsa ters çevirilip +500 yapılıyor.
        if(i>47702){
            i = 47702;
        }
        satılan_kombine = i;
        //System.out.println("BEKLENEN SATILACAK KOMBİNE SAYISI: " + i);
//------------------------------------------------------------------------------KOMBİNE SATIŞLARI
        //System.out.println("KOMBINE SATIŞLARI BAŞLADI");
        while (i > 0)//toplam satılacak kombine sayısı kadar dönecek.
        {
            //herkes en az 1 bilet alacak.
            k = rand.nextInt(k_rand)+1;//1-30
            k0 = rand.nextInt(k0_rand)+1;
            gişeno = rand.nextInt(10) + 1;//1-10
            c = rand.nextInt(c_rand) + 1;//En az 1 bilet alacak herkes.Bu biletlerin yerleri aynı tribün olmak şartıyla rastgele olabilir.
            while (i < c) {
                c = rand.nextInt(c_rand) + 1;
            }
            x = rand.nextInt(t) + 1;//Koltuğun seçileceği Tribün 1-9
            while (Boyutlar.get(x) < c) {
                x = rand.nextInt(t) + 1;
            }
//------------------------------------------------------------------------------
            switch (x) {//Kombinelerin satışlarının gerçekleştiği kısım
                //--------------------------------------------------------------
                case 1:
                    //İçDoğuÜst Tribünü
                    for (q = 1; q <= c; q++) { //1-5
                        konum = rand.nextInt(İç_Doğu_Üst.size());//0...5611
                        while (İç_Doğu_Üst.get(konum) == 1) {//Dolu old. sürece tekrardan konum aranıyor.
                            konum = rand.nextInt(İç_Doğu_Üst.size());
                        }
                        İç_Doğu_Üst.set(konum, 1);
                        konum1 = İç_Doğu_Üst.size() - 1;
                        if (konum < konum1) {
                            konum1 = konum;
                        }
                    }
                    İşlemSüreleri.set(konum1, k * c);//0-5611
                    GelişSüreleri.set(konum1, k0);
                    Gişeler.set(konum1, gişeno);
                    BiletSayısı.set(konum1, c);
                    Tribünler.set(konum1, 1);
                    size = Boyutlar.get(1);//Koltuk satışı yapıldıktan sonraki İç Doğu Üst tribünündeki boş koltuk sayısı.
                    Boyutlar.set(1, size - c);
                    size0 = Boyutlar.get(0);//Koltuk satışı yapıldıktan sonraki toplam tribündeki boş koltuk sayısı.
                    Boyutlar.set(0, size0 - c);
                    bedel = KombineFiyatları.get(1) * c;//2000TL * adet
                    Ücret.set(konum1, bedel);
                    önceki_gelir = EldeEdilenGelir.get(1);
                    bedel = önceki_gelir + bedel;
                    EldeEdilenGelir.set(1, bedel);
                    i = i - c;
                    break;
                //--------------------------------------------------------------
                case 2:
                    //İçDoğuOrta Tribünü
                    for (q = 1; q <= c; q++) { //1-5
                        konum = rand.nextInt(İç_Doğu_Orta.size());//0...5611
                        while (İç_Doğu_Orta.get(konum) == 1) {//Dolu old. sürece tekrardan konum aranıyor.
                            konum = rand.nextInt(İç_Doğu_Orta.size());
                        }
                        İç_Doğu_Orta.set(konum, 1);
                        konum1 = İç_Doğu_Orta.size() - 1;
                        if (konum < konum1) {
                            konum1 = konum;
                        }
                    }
                    İşlemSüreleri.set(konum1 + part, k * c);//5612-11223
                    GelişSüreleri.set(konum1 + part, k0);
                    Gişeler.set(konum1 + part, gişeno);
                    BiletSayısı.set(konum1 + part, c);
                    Tribünler.set(konum1 + part, 2);
                    size = Boyutlar.get(2);//Koltuk satışı yapıldıktan sonraki İç Doğu Orta tribünündeki boş koltuk sayısı.
                    Boyutlar.set(2, size - c);
                    size0 = Boyutlar.get(0);//Koltuk satışı yapıldıktan sonraki toplam tribündeki boş koltuk sayısı.
                    Boyutlar.set(0, size0 - c);
                    bedel = KombineFiyatları.get(2) * c;//3000TL * adet
                    Ücret.set(konum1 + part, bedel);
                    önceki_gelir = EldeEdilenGelir.get(2);
                    bedel = önceki_gelir + bedel;
                    EldeEdilenGelir.set(2, bedel);
                    i = i - c;
                    break;
                //--------------------------------------------------------------
                case 3:
                    //İçDoğuAlt Tribünü
                    for (q = 1; q <= c; q++) { //1-5
                        konum = rand.nextInt(İç_Doğu_Alt.size());//0...5611
                        while (İç_Doğu_Alt.get(konum) == 1) {//Dolu old. sürece tekrardan konum aranıyor.
                            konum = rand.nextInt(İç_Doğu_Alt.size());
                        }
                        İç_Doğu_Alt.set(konum, 1);
                        konum1 = İç_Doğu_Alt.size() - 1;
                        if (konum < konum1) {
                            konum1 = konum;
                        }
                    }
                    İşlemSüreleri.set(konum1 + (part * 2), k * c);//11224-16835
                    GelişSüreleri.set(konum1 + (part * 2), k0);
                    Gişeler.set(konum1 + (part * 2), gişeno);
                    BiletSayısı.set(konum1 + (part * 2), c);
                    Tribünler.set(konum1 + (part * 2), 3);
                    size = Boyutlar.get(3);//Koltuk satışı yapıldıktan sonraki İç Doğu Alt tribünündeki boş koltuk sayısı.
                    Boyutlar.set(3, size - c);
                    size0 = Boyutlar.get(0);//Koltuk satışı yapıldıktan sonraki toplam tribündeki boş koltuk sayısı.
                    Boyutlar.set(0, size0 - c);
                    bedel = KombineFiyatları.get(3) * c;//2000TL * adet
                    Ücret.set(konum1 + (part * 2), bedel);
                    önceki_gelir = EldeEdilenGelir.get(3);
                    bedel = önceki_gelir + bedel;
                    EldeEdilenGelir.set(3, bedel);
                    i = i - c;
                    break;
                //--------------------------------------------------------------
                case 4:
                    //İçBatıÜst Tribünü
                    for (q = 1; q <= c; q++) { //1-5
                        konum = rand.nextInt(İç_Batı_Üst.size());//0...5611
                        while (İç_Batı_Üst.get(konum) == 1) {//Dolu old. sürece tekrardan konum aranıyor.
                            konum = rand.nextInt(İç_Batı_Üst.size());
                        }
                        İç_Batı_Üst.set(konum, 1);
                        konum1 = İç_Batı_Üst.size() - 1;
                        if (konum < konum1) {
                            konum1 = konum;
                        }
                    }
                    İşlemSüreleri.set(konum1 + (part * 3), k * c);//16836-22447
                    GelişSüreleri.set(konum1 + (part * 3), k0);
                    Gişeler.set(konum1 + (part * 3), gişeno);
                    BiletSayısı.set(konum1 + (part * 3), c);
                    Tribünler.set(konum1 + (part * 3), 4);
                    size = Boyutlar.get(4);//Koltuk satışı yapıldıktan sonraki İç Batı Üst tribünündeki boş koltuk sayısı.
                    Boyutlar.set(4, size - c);
                    size0 = Boyutlar.get(0);//Koltuk satışı yapıldıktan sonraki toplam tribündeki boş koltuk sayısı.
                    Boyutlar.set(0, size0 - c);
                    bedel = KombineFiyatları.get(4) * c;//2000TL * adet
                    Ücret.set(konum1 + (part * 3), bedel);
                    önceki_gelir = EldeEdilenGelir.get(4);
                    bedel = önceki_gelir + bedel;
                    EldeEdilenGelir.set(4, bedel);
                    i = i - c;
                    break;
                //--------------------------------------------------------------
                case 5:
                    //İçBatıOrta Tribünü
                    for (q = 1; q <= c; q++) { //1-5
                        konum = rand.nextInt(İç_Batı_Orta.size());//0...5611
                        while (İç_Batı_Orta.get(konum) == 1) {//Dolu old. sürece tekrardan konum aranıyor.
                            konum = rand.nextInt(İç_Batı_Orta.size());
                        }
                        İç_Batı_Orta.set(konum, 1);
                        konum1 = İç_Batı_Orta.size() - 1;
                        if (konum < konum1) {
                            konum1 = konum;
                        }
                    }
                    İşlemSüreleri.set(konum1 + (part * 4), k * c);//22448-28059
                    GelişSüreleri.set(konum1 + (part * 4), k0);
                    Gişeler.set(konum1 + (part * 4), gişeno);
                    BiletSayısı.set(konum1 + (part * 4), c);
                    Tribünler.set(konum1 + (part * 4), 5);
                    size = Boyutlar.get(5);//Koltuk satışı yapıldıktan sonraki İç Batı Orta tribünündeki boş koltuk sayısı.
                    Boyutlar.set(5, size - c);
                    size0 = Boyutlar.get(0);//Koltuk satışı yapıldıktan sonraki toplam tribündeki boş koltuk sayısı.
                    Boyutlar.set(0, size0 - c);
                    bedel = KombineFiyatları.get(5) * c;//3000TL * adet
                    Ücret.set(konum1 + (part * 4), bedel);
                    önceki_gelir = EldeEdilenGelir.get(5);
                    bedel = önceki_gelir + bedel;
                    EldeEdilenGelir.set(5, bedel);
                    i = i - c;
                    break;
                //--------------------------------------------------------------
                case 6:
                    //İçBatıAlt Tribünü
                    for (q = 1; q <= c; q++) { //1-5
                        konum = rand.nextInt(İç_Batı_Alt.size());//0...5611
                        while (İç_Batı_Alt.get(konum) == 1) {//Dolu old. sürece tekrardan konum aranıyor.
                            konum = rand.nextInt(İç_Batı_Alt.size());
                        }
                        İç_Batı_Alt.set(konum, 1);
                        konum1 = İç_Batı_Alt.size() - 1;
                        if (konum < konum1) {
                            konum1 = konum;
                        }
                    }
                    İşlemSüreleri.set(konum1 + (part * 5), k * c);//28060-33671
                    GelişSüreleri.set(konum1 + (part * 5), k0);
                    Gişeler.set(konum1 + (part * 5), gişeno);
                    BiletSayısı.set(konum1 + (part * 5), c);
                    Tribünler.set(konum1 + (part * 5), 6);
                    size = Boyutlar.get(6);//Koltuk satışı yapıldıktan sonraki İç Batı Alt tribünündeki boş koltuk sayısı.
                    Boyutlar.set(6, size - c);
                    size0 = Boyutlar.get(0);//Koltuk satışı yapıldıktan sonraki toplam tribündeki boş koltuk sayısı.
                    Boyutlar.set(0, size0 - c);
                    bedel = KombineFiyatları.get(6) * c;//2000TL * adet
                    Ücret.set(konum1 + (part * 5), bedel);
                    önceki_gelir = EldeEdilenGelir.get(6);
                    bedel = önceki_gelir + bedel;
                    EldeEdilenGelir.set(6, bedel);
                    i = i - c;
                    break;
                //--------------------------------------------------------------
                case 7:
                    //İç_Kuzey Tribünü
                    for (q = 1; q <= c; q++) { //1-5
                        konum = rand.nextInt(İç_Kuzey.size());//0...8417
                        while (İç_Kuzey.get(konum) == 1) {//Dolu old. sürece tekrardan konum aranıyor.
                            konum = rand.nextInt(İç_Kuzey.size());
                        }
                        İç_Kuzey.set(konum, 1);
                        konum1 = İç_Kuzey.size() - 1;
                        if (konum < konum1) {
                            konum1 = konum;
                        }
                    }
                    İşlemSüreleri.set(konum1 + (part * 6), k * c);//33672-42089
                    GelişSüreleri.set(konum1 + (part * 6), k0);
                    Gişeler.set(konum1 + (part * 6), gişeno);
                    BiletSayısı.set(konum1 + (part * 6), c);
                    Tribünler.set(konum1 + (part * 6), 7);
                    size = Boyutlar.get(7);//Koltuk satışı yapıldıktan sonraki İç Batı Alt tribünündeki boş koltuk sayısı.
                    Boyutlar.set(7, size - c);
                    size0 = Boyutlar.get(0);//Koltuk satışı yapıldıktan sonraki toplam tribündeki boş koltuk sayısı.
                    Boyutlar.set(0, size0 - c);
                    bedel = KombineFiyatları.get(7) * c;//1000TL * adet
                    Ücret.set(konum1 + (part * 6), bedel);
                    önceki_gelir = EldeEdilenGelir.get(7);
                    bedel = önceki_gelir + bedel;
                    EldeEdilenGelir.set(7, bedel);
                    i = i - c;
                    break;
                //--------------------------------------------------------------
                case 8:
                    //İç Güney Sol Tribünü
                    for (q = 1; q <= c; q++) { //1-5
                        konum = rand.nextInt(İç_Güney_Sol.size());//0...2805
                        while (İç_Güney_Sol.get(konum) == 1) {//Dolu old. sürece tekrardan konum aranıyor.
                            konum = rand.nextInt(İç_Güney_Sol.size());
                        }
                        İç_Güney_Sol.set(konum, 1);
                        konum1 = İç_Güney_Sol.size() - 1;
                        if (konum < konum1) {
                            konum1 = konum;
                        }
                    }
                    İşlemSüreleri.set(konum1 + (15 * part) / 2, k * c);//42090-44895
                    GelişSüreleri.set(konum1 + (15 * part) / 2, k0);
                    Gişeler.set(konum1 + (15 * part) / 2, gişeno);
                    BiletSayısı.set(konum1 + (15 * part) / 2, c);
                    Tribünler.set(konum1 + (15 * part) / 2, 8);
                    size = Boyutlar.get(8);//Koltuk satışı yapıldıktan sonraki İç Batı Alt tribünündeki boş koltuk sayısı.
                    Boyutlar.set(8, size - c);
                    size0 = Boyutlar.get(0);//Koltuk satışı yapıldıktan sonraki toplam tribündeki boş koltuk sayısı.
                    Boyutlar.set(0, size0 - c);
                    bedel = KombineFiyatları.get(8) * c;//1500TL * adet
                    Ücret.set(konum1 + (15 * part) / 2, bedel);
                    önceki_gelir = EldeEdilenGelir.get(8);
                    bedel = önceki_gelir + bedel;
                    EldeEdilenGelir.set(8, bedel);
                    i = i - c;
                    break;
                case 9:
                    //İç Güney Orta Tribünü
                    for (q = 1; q <= c; q++) { //1-5
                        konum = rand.nextInt(İç_Güney_Orta.size());//0...2805
                        while (İç_Güney_Orta.get(konum) == 1) {//Dolu old. sürece tekrardan konum aranıyor.
                            konum = rand.nextInt(İç_Güney_Orta.size());
                        }
                        İç_Güney_Orta.set(konum, 1);
                        konum1 = İç_Güney_Orta.size() - 1;
                        if (konum < konum1) {
                            konum1 = konum;
                        }
                    }
                    İşlemSüreleri.set(konum1 + (part * 8), k * c);//44896-47701
                    GelişSüreleri.set(konum1 + (part * 8), k0);
                    Gişeler.set(konum1 + (part * 8), gişeno);
                    BiletSayısı.set(konum1 + (part * 8), c);
                    Tribünler.set(konum1 + (part * 8), 9);
                    size = Boyutlar.get(9);//Koltuk satışı yapıldıktan sonraki İç Batı Alt tribünündeki boş koltuk sayısı.
                    Boyutlar.set(9, size - c);
                    size0 = Boyutlar.get(0);//Koltuk satışı yapıldıktan sonraki toplam tribündeki boş koltuk sayısı.
                    Boyutlar.set(0, size0 - c);
                    bedel = KombineFiyatları.get(9) * c;//1500TL * adet
                    Ücret.set(konum1 + (part * 8), bedel);
                    önceki_gelir = EldeEdilenGelir.get(9);
                    bedel = önceki_gelir + bedel;
                    EldeEdilenGelir.set(9, bedel);
                    i = i - c;
                    break;
                default:
                    System.out.println("ERROR BEKLENMEYEN TRİBUN");
                    break;
            }
        }
        a = 0;
        for (i = 1; i < 10; i++) {
            a = a + EldeEdilenGelir.get(i);
        }
        EldeEdilenGelir.set(0, a);
//------------------------------------------------------------------------------SATILAN KOMBİNELERİN GÜNCELLENMESİ
        Kombineli_Yerler.clear();
        Kombineli_Yerler.addAll(İç_Doğu_Üst);
        Kombineli_Yerler.addAll(İç_Doğu_Orta);
        Kombineli_Yerler.addAll(İç_Doğu_Alt);
        Kombineli_Yerler.addAll(İç_Batı_Üst);
        Kombineli_Yerler.addAll(İç_Batı_Orta);
        Kombineli_Yerler.addAll(İç_Batı_Alt);
        Kombineli_Yerler.addAll(İç_Kuzey);
        Kombineli_Yerler.addAll(İç_Güney_Sol);
        Kombineli_Yerler.addAll(İç_Güney_Orta);
        Kombineli_Yerler.addAll(Deplasman);
//------------------------------------------------------------------------------
        count(Kombineli_Yerler);
        count(İç_Doğu_Üst);
        count(İç_Doğu_Orta);
        count(İç_Doğu_Alt);
        count(İç_Batı_Üst);
        count(İç_Batı_Orta);
        count(İç_Batı_Alt);
        count(İç_Kuzey);
        count(İç_Güney_Sol);
        count(İç_Güney_Orta);
        count(Deplasman);
//------------------------------------------------------------------------------
        delete(GelişSüreleri);
        delete(İşlemSüreleri);
        delete(Gişeler);
        delete(BiletSayısı);
        delete(Tribünler);
        delete(Ücret);
//------------------------------------------------------------------------------
        sorting(GelişSüreleri);
        İşlemeBaşlangıçZamanı.addAll(GelişSüreleri);
        BoştaKalmaSüresi.addAll(GelişSüreleri);//size aynı olması yeterli zaten tüm elemanları değiştireceğim.
//------------------------------------------------------------------------------
        for (i = 0; i < GelişSüreleri.size(); i++) {
            /*if (null != Gişeler.get(i))*/ switch (Gişeler.get(i)) {
                case 1:
                    GişeIndex1.add(i);
                    break;
                case 2:
                    GişeIndex2.add(i);
                    break;
                case 3:
                    GişeIndex3.add(i);
                    break;
                case 4:
                    GişeIndex4.add(i);
                    break;
                case 5:
                    GişeIndex5.add(i);
                    break;
                case 6:
                    GişeIndex6.add(i);
                    break;
                case 7:
                    GişeIndex7.add(i);
                    break;
                case 8:
                    GişeIndex8.add(i);
                    break;
                case 9:
                    GişeIndex9.add(i);
                    break;
                case 10:
                    GişeIndex10.add(i);
                    break;
                default:
                    break;
            }
        }
//------------------------------------------------------------------------------
        sort(GişeIndex1);
        sort(GişeIndex2);
        sort(GişeIndex3);
        sort(GişeIndex4);
        sort(GişeIndex5);
        sort(GişeIndex6);
        sort(GişeIndex7);
        sort(GişeIndex8);
        sort(GişeIndex9);
        sort(GişeIndex10);
//------------------------------------------------------------------------------
        for (i = 0; i < GelişSüreleri.size(); i++) {
            İşlemeGirmedenBeklemeSüresi.add(İşlemeBaşlangıçZamanı.get(i) - GelişSüreleri.get(i));
        }

        for (i = 0; i < GelişSüreleri.size(); i++) {
            İşlemBitişSüreleri.add(İşlemeBaşlangıçZamanı.get(i) + İşlemSüreleri.get(i));
        }
        freetime(GişeIndex1);
        freetime(GişeIndex2);
        freetime(GişeIndex3);
        freetime(GişeIndex4);
        freetime(GişeIndex5);
        freetime(GişeIndex6);
        freetime(GişeIndex7);
        freetime(GişeIndex8);
        freetime(GişeIndex9);
        freetime(GişeIndex10);
//------------------------------------------------------------------------------
        freetime2(GişeIndex1);
        freetime2(GişeIndex2);
        freetime2(GişeIndex3);
        freetime2(GişeIndex4);
        freetime2(GişeIndex5);
        freetime2(GişeIndex6);
        freetime2(GişeIndex7);
        freetime2(GişeIndex8);
        freetime2(GişeIndex9);
        freetime2(GişeIndex10);
        toti = 0;
        for (i = 0; i < 10; i++) {
            toti = toti + BoştaKalmaSüresi_Gişeler.get(i);
        }
//------------------------------------------------------------------------------BİLET SATIŞLARI
        int biletsatışındangelir;
        int oran;
        int gelir = 0;
//------------------------------------------------------------------------------DERBİ MAÇLARI
        Bilet_Fiyatları.add(0);//Boyutlar dizisi ile senkronize olması rahat olması için.
        Bilet_Fiyatları.add(750);//İç_Doğu_Üst
        Bilet_Fiyatları.add(1000);//İç_Doğu_Orta
        Bilet_Fiyatları.add(750);//İç_Doğu_Alt
        Bilet_Fiyatları.add(750);//İç_Batı_Üst
        Bilet_Fiyatları.add(1000);//İç_Batı_Orta
        Bilet_Fiyatları.add(750);//İç_Batı_Alt
        Bilet_Fiyatları.add(250);//İç_Kuzey
        Bilet_Fiyatları.add(500);//İç_Güney_Sol
        Bilet_Fiyatları.add(500);//İç_Güney_Orta
        Bilet_Fiyatları.add(250);//DEPLASMAN 
//------------------------------------------------------------------------------DİĞER MAÇLAR            
        Bilet_Fiyatları2.add(0);//Boyutlar dizisi ile senkronize olması rahat olması için.
        Bilet_Fiyatları2.add(75);//İç_Doğu_Üst
        Bilet_Fiyatları2.add(100);//İç_Doğu_Orta
        Bilet_Fiyatları2.add(75);//İç_Doğu_Alt
        Bilet_Fiyatları2.add(75);//İç_Batı_Üst
        Bilet_Fiyatları2.add(100);//İç_Batı_Orta
        Bilet_Fiyatları2.add(75);//İç_Batı_Alt
        Bilet_Fiyatları2.add(25);//İç_Kuzey
        Bilet_Fiyatları2.add(50);//İç_Güney_Sol
        Bilet_Fiyatları2.add(50);//İç_Güney_Orta
        Bilet_Fiyatları2.add(25);//DEPLASMAN 
        for (i = 0; i < 3; i++) {//Derbi Maçları
            biletsatışındangelir = Boyutlar.get(1) * Bilet_Fiyatları.get(1);
            Bilet_Geliri.add(biletsatışındangelir);
            biletsatışındangelir = Boyutlar.get(2) * Bilet_Fiyatları.get(2);
            Bilet_Geliri.add(biletsatışındangelir);
            biletsatışındangelir = Boyutlar.get(3) * Bilet_Fiyatları.get(3);
            Bilet_Geliri.add(biletsatışındangelir);
            biletsatışındangelir = Boyutlar.get(4) * Bilet_Fiyatları.get(4);
            Bilet_Geliri.add(biletsatışındangelir);
            biletsatışındangelir = Boyutlar.get(5) * Bilet_Fiyatları.get(5);
            Bilet_Geliri.add(biletsatışındangelir);
            biletsatışındangelir = Boyutlar.get(6) * Bilet_Fiyatları.get(6);
            Bilet_Geliri.add(biletsatışındangelir);
            biletsatışındangelir = Boyutlar.get(7) * Bilet_Fiyatları.get(7);
            Bilet_Geliri.add(biletsatışındangelir);
            biletsatışındangelir = Boyutlar.get(8) * Bilet_Fiyatları.get(8);
            Bilet_Geliri.add(biletsatışındangelir);
            biletsatışındangelir = Boyutlar.get(9) * Bilet_Fiyatları.get(9);
            Bilet_Geliri.add(biletsatışındangelir);
            biletsatışındangelir = Boyutlar.get(10) * Bilet_Fiyatları.get(10);
            Bilet_Geliri.add(biletsatışındangelir);
            Doluluk.add(Boyutlar.get(0));
        }

        for (i = 0; i < 14; i++) {
            oran = rand.nextInt(51) + 50;//Bilet satışına sunulan biletlerin ne kadarının satılacağı.//50-100
            biletsatışındangelir = ((Boyutlar.get(1) * oran) / 100) * Bilet_Fiyatları2.get(1);
            Bilet_Geliri.add(biletsatışındangelir);
            biletsatışındangelir = ((Boyutlar.get(2) * oran) / 100) * Bilet_Fiyatları2.get(2);
            Bilet_Geliri.add(biletsatışındangelir);
            biletsatışındangelir = ((Boyutlar.get(3) * oran) / 100) * Bilet_Fiyatları2.get(3);
            Bilet_Geliri.add(biletsatışındangelir);
            biletsatışındangelir = ((Boyutlar.get(4) * oran) / 100) * Bilet_Fiyatları2.get(4);
            Bilet_Geliri.add(biletsatışındangelir);
            biletsatışındangelir = ((Boyutlar.get(5) * oran) / 100) * Bilet_Fiyatları2.get(5);
            Bilet_Geliri.add(biletsatışındangelir);
            biletsatışındangelir = ((Boyutlar.get(6) * oran) / 100) * Bilet_Fiyatları2.get(6);
            Bilet_Geliri.add(biletsatışındangelir);
            biletsatışındangelir = ((Boyutlar.get(7) * oran) / 100) * Bilet_Fiyatları2.get(7);
            Bilet_Geliri.add(biletsatışındangelir);
            biletsatışındangelir = ((Boyutlar.get(8) * oran) / 100) * Bilet_Fiyatları2.get(8);
            Bilet_Geliri.add(biletsatışındangelir);
            biletsatışındangelir = ((Boyutlar.get(9) * oran) / 100) * Bilet_Fiyatları2.get(9);
            Bilet_Geliri.add(biletsatışındangelir);
            biletsatışındangelir = ((Boyutlar.get(10) * oran) / 100) * Bilet_Fiyatları2.get(10);
            Bilet_Geliri.add(biletsatışındangelir);
            Doluluk.add((Boyutlar.get(0) * oran) / 100);
        }

        processtime(GelişSüreleri, İşlemBitişSüreleri);//müşterilerin toplam işlem için harcadıkları süre
        wait_time(GelişSüreleri, İşlemeBaşlangıçZamanı);//müşterilerin işleme başlamadan önce bekledikleri süre
        File file = new File("COMBINED SALES.txt");
        File file2 = new File("MATCH.txt");
        String listString = "";
        String listString2 = "";
        listString = listString + "TOTAL NUMBER OF COMBINED TICKETS SOLD" + ":\t" + SatılanBiletSayısı.get(0) + "\n\n";
        listString = listString + "\t\t\t\t" + "NUMBER OF COMBINED SOLD BY TRIBUNES" + "\n\n";
        listString = listString + Tribün_İsimleri.get(0) + "\t\t\t" + "|\t" + Tribün_İsimleri.get(1) + "\t|\t" + Tribün_İsimleri.get(2) + "\t|\t" + Tribün_İsimleri.get(3) + "\t|\t" + Tribün_İsimleri.get(4) + "\t|\t" + Tribün_İsimleri.get(5) + "\t|\t" + Tribün_İsimleri.get(6) + "\t|\t" + Tribün_İsimleri.get(7) + "\t\t|\t" + Tribün_İsimleri.get(8) + "\t|\t" + Tribün_İsimleri.get(9) + "\t|\t" + Tribün_İsimleri.get(10) + "\t\t\t|\t\n";
        listString = listString + "NUMBER OF COMBINED TICKETS SOLD" + "\t|\t" + SatılanBiletSayısı.get(1) + "\t\t|\t" + SatılanBiletSayısı.get(2) + "\t\t|\t" + SatılanBiletSayısı.get(3) + "\t\t|\t" + SatılanBiletSayısı.get(4) + "\t\t|\t" + SatılanBiletSayısı.get(5) + "\t\t|\t" + SatılanBiletSayısı.get(6) + "\t\t|\t" + SatılanBiletSayısı.get(7) + "\t\t|\t" + SatılanBiletSayısı.get(8) + "\t\t|\t" + SatılanBiletSayısı.get(9) + "\t\t|\t(" + SatılanBiletSayısı.get(10) + ") It wasn't sold." + "\t\t|\t\n\n";
        listString = listString + "\n";
        listString = listString + "TOTAL INCOME FROM COMBINED TICKETS SALES: \t" + EldeEdilenGelir.get(0) + "₺\n\n";
        listString = listString + "GAIN FROM COMBINED TICKETS SALES BY TRIBUNES\t|\t" + Tribün_İsimleri.get(1) + "\t|\t" + Tribün_İsimleri.get(2) + "\t|\t" + Tribün_İsimleri.get(3) + "\t|\t" + Tribün_İsimleri.get(4) + "\t|\t" + Tribün_İsimleri.get(5) + "\t|\t" + Tribün_İsimleri.get(6) + "\t|\t" + Tribün_İsimleri.get(7) + "\t\t|\t" + Tribün_İsimleri.get(8) + "\t|\t" + Tribün_İsimleri.get(9) + "\t\t|\t" + Tribün_İsimleri.get(10) + "\t\t|\n";
        listString = listString + "\t\t\t\t\t\t|\t" + EldeEdilenGelir.get(1) + "₺\t|\t" + EldeEdilenGelir.get(2) + "₺\t|\t" + EldeEdilenGelir.get(3) + "₺\t|\t" + EldeEdilenGelir.get(4) + "₺\t|\t" + EldeEdilenGelir.get(5) + "₺\t|\t" + EldeEdilenGelir.get(6) + "₺\t|\t" + EldeEdilenGelir.get(7) + "₺\t|\t" + EldeEdilenGelir.get(8) + "₺\t|\t" + EldeEdilenGelir.get(9) + "₺\t\t|\t(" + EldeEdilenGelir.get(10) + ")It wasn't sold.\t|\n";
        listString = listString + "\n\nIDLE TIME OF TOLL BOOTHS(SERVERS)" + "\t|\t" + BoştaKalmaSüresi_Gişeler.get(0) + "\t|\t" + BoştaKalmaSüresi_Gişeler.get(1) + "\t|\t" + BoştaKalmaSüresi_Gişeler.get(2) + "\t|\t" + BoştaKalmaSüresi_Gişeler.get(3) + "\t|\t" + BoştaKalmaSüresi_Gişeler.get(4) + "\t|\t" + BoştaKalmaSüresi_Gişeler.get(5) + "\t|\t" + BoştaKalmaSüresi_Gişeler.get(6) + "\t|\t" + BoştaKalmaSüresi_Gişeler.get(7) + "\t|\t" + BoştaKalmaSüresi_Gişeler.get(8) + "\t|\t" + BoştaKalmaSüresi_Gişeler.get(9) + "\t|\n";
        listString = listString + "TOTAL IDLE TIME:\t" + toti + "\n";
        listString = listString + "\n\n\t\t\t\t ---------  Details Below  ---------\n\n";
        listString = listString + "\tRANKING" + "\t|\t" + "ARRIVAL TIME(min)\t|\t" + "SERVICE TIME(min)\t|\t\t" + "NAME OF TRIBUNES\t|\t" + "TOLL BOOTHS\t|\t" + "NUMBER OF TICKETS\t|\t" + "UNIT PRICE(₺)" + "\t|\t" + "FEE(₺)\t\t|\t" + "SERVICE BEGIN(min)" + "\t|\t" + "SERVICE END(min)" + "\t\t|\t" + "WAITING TIME(min)" + "\t|\t" + "SPEND TIMES(min)\t|\t" + "IDLE TIME(min)" + "\t|\n";
        for (i = 0; i < GelişSüreleri.size(); i++) {
            if (Tribünler.get(i) == 7) {
                listString = listString + "\t" + (i + 1) + "\t\t|\t\t" + GelişSüreleri.get(i) + "\t\t|\t\t" + İşlemSüreleri.get(i) + "\t\t|\t\t" + Tribün_İsimleri.get(Tribünler.get(i)) + "\t\t\t|\t   " + Gişeler.get(i) + "\t\t|\t\t" + BiletSayısı.get(i) + "\t\t|\t" + (Ücret.get(i) / BiletSayısı.get(i)) + "\t\t|\t" + Ücret.get(i) + "\t\t|\t\t" + İşlemeBaşlangıçZamanı.get(i) + "\t\t|\t\t" + İşlemBitişSüreleri.get(i) + "\t\t|\t\t" + MüşterilerinBeklemeSüresi.get(i) + "\t\t|\t\t" + MüşterininHarcadığıZaman.get(i) + "\t\t|\t" + BoştaKalmaSüresi.get(i) + "\t\t|\n";
            } else {
                listString = listString + "\t" + (i + 1) + "\t\t|\t\t" + GelişSüreleri.get(i) + "\t\t|\t\t" + İşlemSüreleri.get(i) + "\t\t|\t\t" + Tribün_İsimleri.get(Tribünler.get(i)) + "\t\t|\t   " + Gişeler.get(i) + "\t\t|\t\t" + BiletSayısı.get(i) + "\t\t|\t" + (Ücret.get(i) / BiletSayısı.get(i)) + "\t\t|\t" + Ücret.get(i) + "\t\t|\t\t" + İşlemeBaşlangıçZamanı.get(i) + "\t\t|\t\t" + İşlemBitişSüreleri.get(i) + "\t\t|\t\t" + MüşterilerinBeklemeSüresi.get(i) + "\t\t|\t\t" + MüşterininHarcadığıZaman.get(i) + "\t\t|\t" + BoştaKalmaSüresi.get(i) + "\t\t|\n";
            }
        }
//------------------------------------------------------------------------------
        listString2 = listString2 + "\tNUMBER OF SEATS THAT CAN BE SALE:\t" + Boyutlar.get(0) + "\n\n";
//------------------------------------------------------------------------------
        listString2 = listString2 + "\n\t\t\t\t ---------  Details Below  ---------\n\n";

        listString2 = listString2 + "\tDAILY MATCH TICKET SALES\n\n";

        for (i = 1; i <= 5; i++) {
            listString2 = listString2 + "\t" + i + ".MATCH\n";
            listString2 = listString2 + "\tNUMBER OF TICKETS SOLD:\t" + Doluluk.get(i + 2) + "\n\n";    //3-7        
        }

        listString2 = listString2 + "\t" + "6" + ".MATCH(DERBY)\n";
        listString2 = listString2 + "\tNUMBER OF TICKETS SOLD:\t" + Doluluk.get(0) + "\n\n";

        for (i = 7; i <= 11; i++) {
            listString2 = listString2 + "\t" + i + ".MATCH\n";
            listString2 = listString2 + "\tNUMBER OF TICKETS SOLD:\t" + Doluluk.get(i + 1) + "\n\n";     //8-12      
        }

        listString2 = listString2 + "\t" + "12" + ".MATCH(DERBY)\n";
        listString2 = listString2 + "\tNUMBER OF TICKETS SOLD:\t" + Doluluk.get(1) + "\n\n";

        listString2 = listString2 + "\t" + "13" + ".MATCH(DERBY)\n";
        listString2 = listString2 + "\tNUMBER OF TICKETS SOLD:\t" + Doluluk.get(2) + "\n\n";

        for (i = 14; i <= 17; i++) {
            listString2 = listString2 + "\t" + i + ".MATCH\n";
            listString2 = listString2 + "\tNUMBER OF TICKETS SOLD:\t" + Doluluk.get(i - 1) + "\n\n";    //13-16        
        }
//------------------------------------------------------------------------------
        for (i = 0; i < Bilet_Geliri.size(); i++) {
            gelir = gelir + Bilet_Geliri.get(i);
        }
//------------------------------------------------------------------------------
        listString2 = listString2 + "\n\n\tINCOME FROM SALES:\t" + gelir + "\n\n";
        listString2 = listString2 + "\n\n\t\t\t\t ---------  Details Below  ---------\n\n";
        listString2 = listString2 + "MATCHES" + "\t" + "|\t" + Tribün_İsimleri.get(1) + "\t|\t" + Tribün_İsimleri.get(2) + "\t|\t" + Tribün_İsimleri.get(3) + "\t|\t" + Tribün_İsimleri.get(4) + "\t|\t" + Tribün_İsimleri.get(5) + "\t|\t" + Tribün_İsimleri.get(6) + "\t|\t" + Tribün_İsimleri.get(7) + "\t\t|\t" + Tribün_İsimleri.get(8) + "\t|\t" + Tribün_İsimleri.get(9) + "\t|\t" + Tribün_İsimleri.get(10) + "\t\t|\n";

        listString2 = listString2 + "\n" + "1.Match\t\t|\t" + Bilet_Geliri.get(30) + "\t\t|\t" + Bilet_Geliri.get(31) + "\t\t|\t" + Bilet_Geliri.get(32) + "\t\t|\t" + Bilet_Geliri.get(33) + "\t\t|\t" + Bilet_Geliri.get(34) + "\t\t|\t" + Bilet_Geliri.get(35) + "\t\t|\t" + Bilet_Geliri.get(36) + "\t\t|\t" + Bilet_Geliri.get(37) + "\t\t|\t" + Bilet_Geliri.get(38) + "\t\t|\t" + Bilet_Geliri.get(39) + "\t\t|\n";
        listString2 = listString2 + "\n" + "2.Match\t\t|\t" + Bilet_Geliri.get(40) + "\t\t|\t" + Bilet_Geliri.get(41) + "\t\t|\t" + Bilet_Geliri.get(42) + "\t\t|\t" + Bilet_Geliri.get(43) + "\t\t|\t" + Bilet_Geliri.get(44) + "\t\t|\t" + Bilet_Geliri.get(45) + "\t\t|\t" + Bilet_Geliri.get(46) + "\t\t|\t" + Bilet_Geliri.get(47) + "\t\t|\t" + Bilet_Geliri.get(48) + "\t\t|\t" + Bilet_Geliri.get(49) + "\t\t|\n";
        listString2 = listString2 + "\n" + "3.Match\t\t|\t" + Bilet_Geliri.get(50) + "\t\t|\t" + Bilet_Geliri.get(51) + "\t\t|\t" + Bilet_Geliri.get(52) + "\t\t|\t" + Bilet_Geliri.get(53) + "\t\t|\t" + Bilet_Geliri.get(54) + "\t\t|\t" + Bilet_Geliri.get(55) + "\t\t|\t" + Bilet_Geliri.get(56) + "\t\t|\t" + Bilet_Geliri.get(57) + "\t\t|\t" + Bilet_Geliri.get(58) + "\t\t|\t" + Bilet_Geliri.get(59) + "\t\t|\n";
        listString2 = listString2 + "\n" + "4.Match\t\t|\t" + Bilet_Geliri.get(60) + "\t\t|\t" + Bilet_Geliri.get(61) + "\t\t|\t" + Bilet_Geliri.get(62) + "\t\t|\t" + Bilet_Geliri.get(63) + "\t\t|\t" + Bilet_Geliri.get(64) + "\t\t|\t" + Bilet_Geliri.get(65) + "\t\t|\t" + Bilet_Geliri.get(66) + "\t\t|\t" + Bilet_Geliri.get(67) + "\t\t|\t" + Bilet_Geliri.get(68) + "\t\t|\t" + Bilet_Geliri.get(69) + "\t\t|\n";
        listString2 = listString2 + "\n" + "5.Match\t\t|\t" + Bilet_Geliri.get(70) + "\t\t|\t" + Bilet_Geliri.get(71) + "\t\t|\t" + Bilet_Geliri.get(72) + "\t\t|\t" + Bilet_Geliri.get(73) + "\t\t|\t" + Bilet_Geliri.get(74) + "\t\t|\t" + Bilet_Geliri.get(75) + "\t\t|\t" + Bilet_Geliri.get(76) + "\t\t|\t" + Bilet_Geliri.get(77) + "\t\t|\t" + Bilet_Geliri.get(78) + "\t\t|\t" + Bilet_Geliri.get(79) + "\t\t|\n";
        listString2 = listString2 + "\n" + "6.Match(Derby)\t|\t" + Bilet_Geliri.get(0) + "\t\t|\t" + Bilet_Geliri.get(1) + "\t\t|\t" + Bilet_Geliri.get(2) + "\t\t|\t" + Bilet_Geliri.get(3) + "\t\t|\t" + Bilet_Geliri.get(4) + "\t\t|\t" + Bilet_Geliri.get(5) + "\t\t|\t" + Bilet_Geliri.get(6) + "\t\t|\t" + Bilet_Geliri.get(7) + "\t\t|\t" + Bilet_Geliri.get(8) + "\t\t|\t" + Bilet_Geliri.get(9) + "\t\t|\n";
        listString2 = listString2 + "\n" + "7.Match\t\t|\t" + Bilet_Geliri.get(80) + "\t\t|\t" + Bilet_Geliri.get(81) + "\t\t|\t" + Bilet_Geliri.get(82) + "\t\t|\t" + Bilet_Geliri.get(83) + "\t\t|\t" + Bilet_Geliri.get(84) + "\t\t|\t" + Bilet_Geliri.get(85) + "\t\t|\t" + Bilet_Geliri.get(86) + "\t\t|\t" + Bilet_Geliri.get(87) + "\t\t|\t" + Bilet_Geliri.get(88) + "\t\t|\t" + Bilet_Geliri.get(89) + "\t\t|\n";
        listString2 = listString2 + "\n" + "8.Match\t\t|\t" + Bilet_Geliri.get(90) + "\t\t|\t" + Bilet_Geliri.get(91) + "\t\t|\t" + Bilet_Geliri.get(92) + "\t\t|\t" + Bilet_Geliri.get(93) + "\t\t|\t" + Bilet_Geliri.get(94) + "\t\t|\t" + Bilet_Geliri.get(95) + "\t\t|\t" + Bilet_Geliri.get(96) + "\t\t|\t" + Bilet_Geliri.get(97) + "\t\t|\t" + Bilet_Geliri.get(98) + "\t\t|\t" + Bilet_Geliri.get(99) + "\t\t|\n";
        listString2 = listString2 + "\n" + "9.Match\t\t|\t" + Bilet_Geliri.get(100) + "\t\t|\t" + Bilet_Geliri.get(101) + "\t\t|\t" + Bilet_Geliri.get(102) + "\t\t|\t" + Bilet_Geliri.get(103) + "\t\t|\t" + Bilet_Geliri.get(104) + "\t\t|\t" + Bilet_Geliri.get(105) + "\t\t|\t" + Bilet_Geliri.get(106) + "\t\t|\t" + Bilet_Geliri.get(107) + "\t\t|\t" + Bilet_Geliri.get(108) + "\t\t|\t" + Bilet_Geliri.get(109) + "\t\t|\n";
        listString2 = listString2 + "\n" + "10.Match\t|\t" + Bilet_Geliri.get(110) + "\t\t|\t" + Bilet_Geliri.get(111) + "\t\t|\t" + Bilet_Geliri.get(112) + "\t\t|\t" + Bilet_Geliri.get(113) + "\t\t|\t" + Bilet_Geliri.get(114) + "\t\t|\t" + Bilet_Geliri.get(115) + "\t\t|\t" + Bilet_Geliri.get(116) + "\t\t|\t" + Bilet_Geliri.get(117) + "\t\t|\t" + Bilet_Geliri.get(118) + "\t\t|\t" + Bilet_Geliri.get(119) + "\t\t|\n";
        listString2 = listString2 + "\n" + "11.Match\t|\t" + Bilet_Geliri.get(120) + "\t\t|\t" + Bilet_Geliri.get(121) + "\t\t|\t" + Bilet_Geliri.get(122) + "\t\t|\t" + Bilet_Geliri.get(123) + "\t\t|\t" + Bilet_Geliri.get(124) + "\t\t|\t" + Bilet_Geliri.get(125) + "\t\t|\t" + Bilet_Geliri.get(126) + "\t\t|\t" + Bilet_Geliri.get(127) + "\t\t|\t" + Bilet_Geliri.get(128) + "\t\t|\t" + Bilet_Geliri.get(129) + "\t\t|\n";
        listString2 = listString2 + "\n" + "12.Match(Derby)\t|\t" + Bilet_Geliri.get(10) + "\t\t|\t" + Bilet_Geliri.get(11) + "\t\t|\t" + Bilet_Geliri.get(12) + "\t\t|\t" + Bilet_Geliri.get(13) + "\t\t|\t" + Bilet_Geliri.get(14) + "\t\t|\t" + Bilet_Geliri.get(15) + "\t\t|\t" + Bilet_Geliri.get(16) + "\t\t|\t" + Bilet_Geliri.get(17) + "\t\t|\t" + Bilet_Geliri.get(18) + "\t\t|\t" + Bilet_Geliri.get(19) + "\t\t|\n";
        listString2 = listString2 + "\n" + "13.Match(Derby)\t|\t" + Bilet_Geliri.get(20) + "\t\t|\t" + Bilet_Geliri.get(21) + "\t\t|\t" + Bilet_Geliri.get(22) + "\t\t|\t" + Bilet_Geliri.get(23) + "\t\t|\t" + Bilet_Geliri.get(24) + "\t\t|\t" + Bilet_Geliri.get(25) + "\t\t|\t" + Bilet_Geliri.get(26) + "\t\t|\t" + Bilet_Geliri.get(27) + "\t\t|\t" + Bilet_Geliri.get(28) + "\t\t|\t" + Bilet_Geliri.get(29) + "\t\t|\n";
        listString2 = listString2 + "\n" + "14.Match\t|\t" + Bilet_Geliri.get(130) + "\t\t|\t" + Bilet_Geliri.get(131) + "\t\t|\t" + Bilet_Geliri.get(132) + "\t\t|\t" + Bilet_Geliri.get(133) + "\t\t|\t" + Bilet_Geliri.get(134) + "\t\t|\t" + Bilet_Geliri.get(135) + "\t\t|\t" + Bilet_Geliri.get(136) + "\t\t|\t" + Bilet_Geliri.get(137) + "\t\t|\t" + Bilet_Geliri.get(138) + "\t\t|\t" + Bilet_Geliri.get(139) + "\t\t|\n";
        listString2 = listString2 + "\n" + "15.Match\t|\t" + Bilet_Geliri.get(140) + "\t\t|\t" + Bilet_Geliri.get(141) + "\t\t|\t" + Bilet_Geliri.get(142) + "\t\t|\t" + Bilet_Geliri.get(143) + "\t\t|\t" + Bilet_Geliri.get(144) + "\t\t|\t" + Bilet_Geliri.get(145) + "\t\t|\t" + Bilet_Geliri.get(146) + "\t\t|\t" + Bilet_Geliri.get(147) + "\t\t|\t" + Bilet_Geliri.get(148) + "\t\t|\t" + Bilet_Geliri.get(149) + "\t\t|\n";
        listString2 = listString2 + "\n" + "16.Match\t|\t" + Bilet_Geliri.get(150) + "\t\t|\t" + Bilet_Geliri.get(151) + "\t\t|\t" + Bilet_Geliri.get(152) + "\t\t|\t" + Bilet_Geliri.get(153) + "\t\t|\t" + Bilet_Geliri.get(154) + "\t\t|\t" + Bilet_Geliri.get(155) + "\t\t|\t" + Bilet_Geliri.get(156) + "\t\t|\t" + Bilet_Geliri.get(157) + "\t\t|\t" + Bilet_Geliri.get(158) + "\t\t|\t" + Bilet_Geliri.get(159) + "\t\t|\n";
        listString2 = listString2 + "\n" + "17.Match\t|\t" + Bilet_Geliri.get(160) + "\t\t|\t" + Bilet_Geliri.get(161) + "\t\t|\t" + Bilet_Geliri.get(162) + "\t\t|\t" + Bilet_Geliri.get(163) + "\t\t|\t" + Bilet_Geliri.get(164) + "\t\t|\t" + Bilet_Geliri.get(165) + "\t\t|\t" + Bilet_Geliri.get(166) + "\t\t|\t" + Bilet_Geliri.get(167) + "\t\t|\t" + Bilet_Geliri.get(168) + "\t\t|\t" + Bilet_Geliri.get(169) + "\t\t|\n";

        try (FileWriter dosyaInput = new FileWriter("COMBINED SALES.txt"); BufferedWriter dosyaOutput = new BufferedWriter(dosyaInput)) {
            dosyaOutput.write(listString);
        }
        try (FileWriter dosyaInput2 = new FileWriter("MATCH.txt"); BufferedWriter dosyaOutput2 = new BufferedWriter(dosyaInput2)) {
            dosyaOutput2.write(listString2);
        }
        Desktop desktop = Desktop.getDesktop();
        desktop.open(file2);
        desktop.open(file);
    }

    static void delete(ArrayList<Integer> array) {
        for (i = 0; i < array.size(); i++) {
            if (array.get(i) == 0) {
                array.remove(i);
                i--;
            }
        }
    }

    static void count(ArrayList<Integer> array) {
        c = 0;
        for (i = 0; i < array.size(); i++) {
            if (array.get(i) == 1) {
                c = c + 1;
            }
        }
        SatılanBiletSayısı.add(c);
    }

    static void sorting(ArrayList<Integer> array) {
        int hold, min_index;
        for (i = 0; i < array.size() - 1; i++) {
            min_index = i;
            for (int j = i + 1; j < array.size(); j++) {
                if (array.get(j) < array.get(min_index)) {
                    min_index = j;
                }
            }
            hold = array.get(i);
            array.set(i, array.get(min_index));
            array.set(min_index, hold);

            hold = İşlemSüreleri.get(i);
            İşlemSüreleri.set(i, İşlemSüreleri.get(min_index));
            İşlemSüreleri.set(min_index, hold);

            hold = Gişeler.get(i);
            Gişeler.set(i, Gişeler.get(min_index));
            Gişeler.set(min_index, hold);

            hold = BiletSayısı.get(i);
            BiletSayısı.set(i, BiletSayısı.get(min_index));
            BiletSayısı.set(min_index, hold);

            hold = Tribünler.get(i);
            Tribünler.set(i, Tribünler.get(min_index));
            Tribünler.set(min_index, hold);

            hold = Ücret.get(i);
            Ücret.set(i, Ücret.get(min_index));
            Ücret.set(min_index, hold);
            //------------------------------------------------------------------
        }
    }

    static void sort(ArrayList<Integer> array) {
        for (i = 0; i < array.size() - 1; i++) {
            if (İşlemeBaşlangıçZamanı.get(array.get(i)) + İşlemSüreleri.get(array.get(i)) > İşlemeBaşlangıçZamanı.get(array.get(i + 1))) {
                İşlemeBaşlangıçZamanı.set(array.get(i + 1), İşlemeBaşlangıçZamanı.get(array.get(i)) + İşlemSüreleri.get(array.get(i)));
            }
        }
    }

    static void freetime(ArrayList<Integer> array) {//sistemin boşta kalma süresi
        sum = İşlemeBaşlangıçZamanı.get(array.get(0));
        BoştaKalmaSüresi.set(array.get(0), sum);
        for (i = 0; i < array.size() - 1; i++) {
            sum = İşlemeBaşlangıçZamanı.get(array.get(i + 1)) - İşlemBitişSüreleri.get(array.get(i));
            BoştaKalmaSüresi.set(array.get(i + 1), sum);
        }
    }

    static void freetime2(ArrayList<Integer> array) {
        sum = İşlemeBaşlangıçZamanı.get(array.get(0));
        for (i = 0; i < array.size() - 1; i++) {
            sum = sum + İşlemeBaşlangıçZamanı.get(array.get(i + 1)) - İşlemBitişSüreleri.get(array.get(i));
        }
        BoştaKalmaSüresi_Gişeler.add(sum);
    }

    static void processtime(ArrayList<Integer> array, ArrayList<Integer> array2) {//işlemsüreleri       
        int fark;
        for (i = 0; i < array.size(); i++) {
            fark = array2.get(i) - array.get(i);
            MüşterininHarcadığıZaman.add(fark);
        }
    }

    static void wait_time(ArrayList<Integer> array, ArrayList<Integer> array2) {//müşteriler ne kadar bekledi
        int wait;
        for (i = 0; i < array.size(); i++) {
            wait = array2.get(i) - array.get(i);
            MüşterilerinBeklemeSüresi.add(wait);
        }
    }
}
