/*
Bünyamin Göymen

Kullanılan programlar:
-Netbeans 8.2
-java 8
 */

package montecarlo;

import java.io.IOException;
import java.util.LinkedList;
import jxl.read.biff.BiffException;

public class main {


    public static void main(String[] args) throws IOException, BiffException {
        
        //Öncelikle diğer sınıflardaki metotları kullanabilmemiz için değişkenleri tanımlıyoruz:
        excel excel = new excel(); //Excel dosyasından okuam yazma yapabilmek içim
        others other = new others(); //monteCarlo ve excel işlemleri hariç diğer işlemleri yapabilmek için (minValue ve maxValue hesabı gibi)
        montecarlo montecarlo = new montecarlo(); //Montecarlo hesabı yapabilmek için
        
        LinkedList<Integer> inputValues = excel.readInput(); //İlk olarak excel dosyasındaki değerleri alıyoruz. (3,4,5,6 değerlerimi)
        
        //Gelen girdi verisinde en büyük ve en küçük değerleri hesaplıyoruz.
        //Yani bize verilen örnekte sadece 3,4,5,6 olabilir. Ancak daha sonradan 7,8,9 ya da 2 gibi değerler eklenebilir. Böyle bir durumda sistem min ve max value değerleir değişecektir. Bu sebeple min ve max value dinamik bir yapıya sahip olacaktır.
        //Yani daha sonradan 3,4,5,6 gibi değerler silinip 100,200,300 gibi değerler eklenirse minValue ve maxValue ona göre değişecektir.
        //Varsayılan ödevde minValue=3, maxValue=6 olmalıdır.
        int maxValue  = other.maxValue(inputValues);
        int minValue = other.minValue(inputValues);

        
        LinkedList<Integer> firstFrekans = montecarlo.calculateFrekans(inputValues, maxValue, minValue); // Giriş değerlerine göre frekansı hesaplıyoruz.
        //Frekans bağlı listedsinde değerler sırasıyla tutuluyor. Yani minValue'nin frekansı ilk değer iken, maxValue'nin frekasnı son değer oluyor.
        
        int toplamFrekans = other.ListTopla(firstFrekans); //olasılığı hesaplayabilmek için toplam frekansa ihtiyacımız var. Bu metot bu işlemi yapmaktadır.
    
        LinkedList<Double> olasilik = montecarlo.olasilikHesabi(firstFrekans, toplamFrekans); // Olasılıkları hesaplıyoruz.
        //Frekasn bağlı listesi sıralı olduğu için. Bu bağlı liste de sıralı olacaktır. Yani ilk değer minValue'nin olasılığını tutuyor, son değer de maxValue'nin olasılığını tutuyor.
        
        LinkedList<Double> kumulatif = montecarlo.kumulatifOlasilikHesabi(olasilik, minValue, maxValue);
        
        LinkedList<Integer> firstSira = other.frekansSiralama(firstFrekans, minValue, maxValue);
        
        LinkedList<Integer> newValues = new LinkedList<>();
        LinkedList<Integer> newFrenaks = new LinkedList<>();
        
        while(!montecarlo.dogruluk(firstFrekans, newFrenaks, firstSira, minValue, maxValue)){
            if(newValues.size()/12 >= 50){
                //while döngüsünün sonsuz döngüye girmemesi için 50 yıl hesaplandığı taktirde while döngüsünü durdur diyoruz.
                // newValues baglı listesinde kaç ay hesaplanmışsa o kadar deger vardır. Bu sebeple boyutunu 12 ye bölersek kaç yıl ilerlendiğini hesaplayabiliriz.
                break;
            }
            newValues= montecarlo.yeniDegerHesabi(newValues, kumulatif, minValue, maxValue);
            newFrenaks = montecarlo.calculateFrekans(newValues, maxValue, minValue);
        }
        
        
        //En sonda da excel dosyası olarak çıktı veriyoruz. Bu değer true dönerse yazdırma işlemi başarılı olmuştur, false dönerse de başarısız olmuştur anlmaına geliyor. Ona göre işlem yapıyoruz.
        boolean yazdir = excel.wirteOutput(inputValues, firstFrekans, olasilik, kumulatif, newValues, newFrenaks, toplamFrekans, minValue, maxValue, montecarlo);
        
        //Kaç yıl hesaplandığını da en sona yazıyoruz.
        System.out.println("Hesaplanan yıl: " + newValues.size()/12);
        
        //Eğer çıktı olarak excel dosyası işlemi başarılı olduysa Ekrana konumunu basıyoruz. Başarısız olduysa hata olduğunu ekrana basıyoruz.
        if(yazdir){
            System.out.println("'src/files/output.xls' dosyası çıktı olarak kaydedilmiştir. O dosyaya bakabilirsiniz.");
        }else{
            System.out.println("Çıktı dosyasını yazarken bir hata meydana geldi. Terminal üzerinden verilen çıktılara bakabilirsiniz. Terminal kapandığında çıktı da yok olacaktır.");
        }
    
    }
    
}
