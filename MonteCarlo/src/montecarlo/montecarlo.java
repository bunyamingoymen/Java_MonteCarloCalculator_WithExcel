/*
Bünyamin Göymen

Kullanılan programlar:
-Netbeans 8.2
-java 8
 */

package montecarlo;

import java.util.LinkedList;
import java.util.Random;

public class montecarlo {
    
    LinkedList<Double> randomNumbers; //random oluşan değerleri tutmak için bir bağlı liste oluşturuyoruz.
    //Bunun global olmasının sebebi de proje tamamlandığında exce ldosyasına yazabilmek için. Aksi taktirde oluşan rnadom sayılar kayboluyor.

    //Bizim yapıcı metodumuz:
    public montecarlo() {
        //Sadece randomsayılar için bağlı listeyi tanımlıyoruz.
        randomNumbers = new LinkedList<>();
    }
    
    
    //Gelen değerlerin frekansını okur ve bunu bir bağlı liste ile gönderir.
    public LinkedList<Integer> calculateFrekans(LinkedList<Integer> list, int maxValue, int minValue) {
        
        //minValue, değelrer arasında en küçük olanı temsil ediyor, maxValue de en büyük olanı temsil ediyor.(3,4,5,6 da minValue =3, maxValue=6 oluyor.)
        
        LinkedList<Integer> frekans = new LinkedList<Integer>(); // ilk olarak frekansları yazacağımız bağlı listeyi oluşturuyoruz.
        
        //minvalue'den başlayıp, maxValue'ye kadar bütün değerlerin kaç tane olduğuna bakmak için bir döngü oluşturuyoruz.
        for (int i = minValue; i <= maxValue; i++) {
            //minValue varsayılan oalrak 3, maxValue varsayılan olarak 6 olduğu için 3 ve 6 dahil 3 ile 6 arasındaki bütün sayıların kaç tane olduğuna bakıyoruz. Bu sebeple döngü minValue'den başlayıp macValue'de bitiyor.
            int sayac = 0; //değerden kaç tane olduğunu sayabilmek için bir sayac değişkeni oluşturuyoruz.
            
            //gelen value değerinin bütün elemanlarını teker teker dönüyoruz. Eğer i değerine rastlarsak sayacı bir arttırıyoruz.
            //Böylelikle gelen valueler arasında isteidğimiz sayının kaç tane oldupunu buluyoruz.
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j) == i) {
                    //Eğer bulunduğumuz eleman i değerine eşit ise bu if'e giriyor ve sayacı arttrıyor.
                    sayac++;
                } //if sonu
            } //iç for sonu
            // iç for bitince listede kaç tane i değeri olduğunu sayac değerinde tutmuş oluyoruz
            //Tuttuğumuz bu sayıyı da oluşturdupumuz bağlı listeye atıyoruz:
            frekans.add(sayac);
            
            //Ayrıca minValue'den başlayıp maxValue'ye doğru sırasıyla gittiğimiz için bağlı listede de değerler sırasıyla tutuluyor.
            //Yani bağlı listedeki ilk değer minVale'nin frekansını (Örneğimizde 3'ün frekansını), ikinci değer minValue+1'in frekansını(Örneğimizde 4'ün frekansını) tutuyor.
            //Böylrliklr bütün sayıların frekansını sırasıyla tutmuş oluyoruz.
            
            //Daha sonra ekrana yazdırıyoruz:
            System.out.println("i: " + i + " , Frekans: " + sayac);

        }//dış for sonu
        
        return frekans; //en sonda oluşturup içini doldurduğumuz bağlı listeyi öndürüyoruz.
    
    }// calculateFrekans metodu sonu

    //Bu metot bütün frekansların olasılıklarını hesaplıyor.
    public LinkedList<Double> olasilikHesabi(LinkedList<Integer> frekans, int toplamFrekans) {
        //calculateFrekans'daki olduğu gibi bu da değerleri sırasıyla hesaplıyor ve bir bağlı listede tutup onu gönderiyor.
        
        LinkedList<Double> olasilik = new LinkedList<>(); //ilk olarak değerleri tutacağımız bir bağlı liste oluşturuyoruz.

        //Bütün frekansları for döngüsü ile dönerek olasılıklarını buluyor: 
        for (int i = 0; i < frekans.size(); i++) {
            //Bu for ile bütün frekansları teker teker dnüyoruz.
            double tmp = (double) frekans.get(i) / toplamFrekans; // geçici değişkende olasılığı hesaplıyoruz.
            olasilik.add(tmp); // hesaplanan olasılığı bağlı listemize aktarıyoruz.
            System.out.println(i + ". olasılık hesabı = " + tmp); //Bulduğumuz değeri ekrana basıyoruz.
        }//for sonu
    
        //Frekans değerleri sıralı olduğu için (3,4,5, nın frekanslıarnı sıralı bir şekilde tutuyor.) olasılıkları hesaplarken de sıralı bir şekilde tutmuş oluyoruz.
        return olasilik; //en sonda olasılıkları tuttuğumuz bağlı listeyi gönderiyoruz.
    
    } //olasilikHesabı metodu sonu

    //Kümülatif olasılığı hesaplayan fonksiyon
    public LinkedList<Double> kumulatifOlasilikHesabi(LinkedList<Double> olasilik, int minValue, int maxValue) {
        
        //ilk olarak kümülatif değerşerini tutacak bir bağlı liste oluşturuyoruz:
        LinkedList<Double> kumulatif = new LinkedList<>();

        //Kümülatif değer de ilk değeri biz ekliyoruz. Çünkü birinci değer zaten sıfır,
        //diğer değerler de kendisinin ve olasılıgın bir önceki değerinin toplamı olacak şekilde bir döngüde hesaplıyoruz.
        kumulatif.add(0.0);//ilk değeri elle ekliyoruz.

        int fark = maxValue - minValue+1; //Kaç tane değer varsa o kadar devam etmeli. Ancak döngü de 1 dem başlamalı ki kümülatif hesap yapıabilsin.
        //Yani fark değeri kaç tane girdimiz olduğu gösteriyor. (3,4,5,6 yı sayarsak fark değeri 4 olmalı.)
        //Bunu yapma sebebimiz de döngü bu fark kadar dönmeli. Yukarıdaki Frekasn hesaplamada minValuede nbaşlayıp maxValue'ye kadar gidiyorduk.Ancak kümülatif hesabı için 1 den başlamalıyız.
        //Ve yine aradaki fark kadar o döngü devam etmeli (Aradaki fark 4 olduğuna göre ve ilk değeri biz belirlediğimize göre toplamda 3 tane kümülatif değer hesaplanacak.)

        //kümülatifi hesaplamak için bir for döngüsü kuruyoruz.
        for (int i = 1; i < fark; i++) {
            //kümülatif hesabı bir önceki kümülatif değer ile bir önceki olasılık değerinin toplamı oluyor.
            double tmp = (double) kumulatif.get(i - 1) + olasilik.get(i - 1); //geçici değişkenimizin içine kümülatif değeri hesaplayıp aktarıyoruz.
            kumulatif.add(tmp);//Bulduğumuz kümülatif degeri oluşturdugumuz bağlı listeye kaydediyoruz.
            System.out.println(i + ". kümülatif olasılık hesabı = " + tmp);
        }//for göngüsü son

        return kumulatif; //En sonda oluşturduğumuz bağlı listeyi gönderiyoruz.
    
    }//Kümülatif olasılık hesapla metodu sonu

    //Bu fonksiyonda random sayılar oluşturup o random sayıların kümülatif eğere göre karşılığını bulup bağlı listeye aktarıyoruz ve o bağlı listeyi döndürüyoruz.
    public LinkedList<Integer> yeniDegerHesabi(LinkedList<Integer> newValues, LinkedList<Double> kumulatif, int minValue, int maxValue) {

        //İlk olarak 12 tane rastgele sayı üretip onu bağlı listeye aktarıyoruz:
        Random random = new Random(); // Random kütüphanesini çağrıyoruz.

        //Random değerler oluşturabilmek için for döngüsünü kullanıyoruz:
        for (int i = 0; i < 12; i++) {
            double tmp = random.nextDouble(); //rastgele bir sayı oluşturuyoruz.
            randomNumbers.add(tmp); // oluşturulan rastgele sayıyı bağlı listeye aktarıyoruz.
            System.out.println(i + ". oluşturulan rastgele sayı: " + tmp); //Bulduğumuz rastgele sayıyı ekrana basıyoruz.
        }
        
        int yil = newValues.size()/12; // yıl hesabı yapıyoruz. 
        //Bunun amacı birden fazla kes bu metodun çağrılabilir olmasından kaynaklı.
        //Yani 1-2 yıl gibi kesin bir süre mevcut değil.
        //Yani yeni oluşan frekans ile bir önceki frekasn uyuşmazsa bu fonksiyon yeniden çağrılıyor ve elemanların karışmaması için yıl hesabı yapılıyor.
        //Eğer daha önce zaten bir yıl hesaplanmamışsa yıl sıfır çıkacağı için döngü de sıfırdan başlayacaktır.
        //Döngünün başlangıç değerini 12 ile çarpmamızın sebebi de bir sonraki yılın devamından başlaması için.Yani eğer 1 yıl hesaplanmışsa 13.ayı hesaplaması gerektiği için yıl*12 yaptığımızda 13'aya geliyor.
        //döngünün bitişi de başlangıç değerinin 12 fazlası oluyor(Bir yılda 12 ay olduğu için).

        for (int j = yil*12; j < yil*12+12; j++) {
            //Eğer gelen rastgele sayı en büyük kümülatif değerden dah büyükse maxValue oluyor (Yani 6 oluyor.)
            //Yani örneğimizde en büyük kümülatif değer 0.833333... çıkıyordu, rastgele gelen değer 0,9 gibi bir değer olursa maxValue yani 6 oluyor.
            //Bu if ile ilk onu kontrol ediyoruz. Yani en büüyk kümülatiften bile büyükse maxValue'yi newValue'ye ekliyoruz.ç
            if (kumulatif.get(kumulatif.size() - 1) < randomNumbers.get(j)) {
                int tmp = maxValue; // maxValue değişkenimizi geçcii değişkene atıyoruz. 
                newValues.add(tmp); //oluşturduğumuz bağlı listeye geçici değişkenimizi ekliyoruz.
                System.out.println("j: " + j + " yeni value: " + tmp);//Daha sonra bu değeri ekrana yazdırıyoruz.
            } else {
                
                //Bu else girerse en büyük kümülatif değerden büyük değildir anlamına geliyor.
                //Yeni bir for döngüsü oluştuşturuyoruz
                //Bu for döngüsü de sıfır dan başlayıp en büyük kümülatif sayının bir eksiğine kadar gidiyor. 
                //Bizim bu döngüdeki amacımız oluşan rastgele sayının hangi kümülatif değerler arasında olduğunu bulmak:
                
                for (int i = 0; i < kumulatif.size() - 1; i++) {
                    //Burada yaptığımız şey, gelen random sayı şu anki kümülatif değerden büyük, bir sonraki kümülatif değerden küöük eşit mi? diye kontrol ediyor. 
                    //Eğer bu if'e girerse random sayı bu değerler arasındadır demek oluyor. Ve ona göre işlem yapıyoruz. 
                    if ((kumulatif.get(i) < randomNumbers.get(j)) && (randomNumbers.get(j) <= kumulatif.get(i + 1))) { 
                        int tmp = minValue + i; //for döngüsü sıfırdan başladığı için değeri eklerken minValue'yi de dahil ediyoruz. Yani eğer i sıfır sa ilk değer arasındadır demek oluyor. O zaman minValue diziye eklenen değer olmalı
                        //Yani eğer i 1 ise 4'ün aralığındayız demek oluyor. 2 ise de 5'in aralığındayız demek oluyor. Yani minValue kadar ekleme yam-pmamız gerekiyor.
                        
                        newValues.add(tmp); // oluşturduğumuz geçici değişkeni yukarıda oluşturudğumuz bağlı listeye aktarıyoruz:
                        
                        //En sonda da random sayının bulunduğu aralığı ekrana yazdırıyoruz:
                        System.out.println("j: " + j + " i: " + i + " yeni value: " + tmp + " kumulatif.get(i): " + kumulatif.get(i) + " kumulatif.get(i + 1): " + kumulatif.get(i + 1));
                    }//iç if sonu
                    
                }//iç for sonu
                
            }// üsteki else sonu
        
        }//üsteki for sonu

        return newValues; //en sonda oluşan yeni değerleri kaydettiğimiz bağlı listeyi döndürüyoruz.
    
    } // yeniDeğerHesabı yaptıgımız metodun sonu 

    //Bulunan frekans değerinin ilk frekans değerime ne kadar benzediğini bulan fonksiyon.
    public boolean dogruluk(LinkedList<Integer> firstFrekans, LinkedList<Integer> newFrekans, LinkedList<Integer> firstSira, int minValue, int maxValue) {
        /*true dönebilmesi için oluşan frekans değerinin, ilk frekans değerine benzemesi en az %50 olmaldır. 
        Yani mesela ilk frekasn değerleri şöyle olsun:
        3: 12
        4: 8
        5: 15
        6: 9
        
        Böyle bir durumda frekans sıralaması: 
        4<6<3<5
        
        olacaktır.
        bu sıralama ile daha sonradan çıkan frekans değerlerinin sıralamasına göre en az iki tane değerim yeri doğru olmalıdır.
        
        Örneğin:
        
        Sonradan hesaplanan frekans değerlerinin sıralaması-> 
        4<6<5<3 olursa doğru olacaktır.
        6<4<3<5 olursa doğru olacaktır.
        4<3<6<5 olursa doğru olacaktır.
        
        4<3<5<6 olursa yanlış olacaktır.
        3<4<5<6 olursa yanlış olacaktır.
        
        Yani sıralamaya göre en az iki tanesinin yeri doğru olmalı.
         */
        
        others other = new others(); //others sınıfındaki metotlara ulaşabilmek için değişken oluşturuyoruz.
        
        //Ratgele sayılarla oluşturduğumuz value'nin sıralamasını öreğniyorz. Yani en çok hangi değerden var, en az hangi değerden var gibi bir sıralamay ulaşıyoruz:
        LinkedList<Integer> newSira = other.frekansSiralama(newFrekans, minValue, maxValue);
        
        int sayac = 0; // bu sayaç newSira'nın yarısına eşit veya daha fazla olursa true dönecek (%50 veya daha fazla başarı olduğu için). Diğer durumda da false dönecek
        
        for (int i = 0; i < newSira.size(); i++) {
            //Gelen firstSıra ile newSıra'yı karşılaştırıyopruz.
            //Ne kadar benzediğine bakıyoruz. Yukarıda da belirttiğimiz gibi en az %50 doğru olduğu taktirde true değerini yolluyoruz.
            if(firstSira.get(i) == newSira.get(i)){
                sayac++; //Eğer sıra aynıysa sayacı arttıroyurz.
            }
        }
        
        /*
            Var olan değerlerin en az yarısı uyuyorsa sayac bu bağlı listelerin boyutunun yarısı kadar olmalıdır.
            Buna gmre de bir if komutu oluşturuyoruz.
            Eğer sayac boyutun yarısıysa true değeri dönderiyor.
        İf'e girmez ise de en aşağıda false değerini dönderiyor.
        */
        
        // Bu if başarım oranının %50 yi geçip geçmediğini kotnrol ediyor.
        //Eğer %50'yi geçmişse if'in içine giriyor ve true dönüyor. Geçmemişse de false dönüyor ve hesaplanmaya devam ediliyor.
        if(sayac >= newSira.size()/2){ // Buradaki /2 kısmı kaldırılırsa başar ıoranı %100 olana kadar devam edecektir.
            return true;
        }
        
        /*
        Not:
         -Genelde 5 yıl ceya daha az yıl hesaplandığında %50 başarı elde ediliyor.
         -%100 başarı için ise 30 yıl'a kadar çıkabiliyor. (Zaten bu metodu çapıran fonksiyonda maksimum 50 yıl işlem yap sistemi var. Aksi taktirde sonsuz döngüye gireliblir)
        */
        return false; // Yukarıdaki if'e girmediğine göre false değri göndeirliyor.
    }
}
