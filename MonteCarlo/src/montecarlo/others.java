/*
Bünyamin Göymen
02180201041

Kullanılan programlar:
-Netbeans 8.2
-java 8
 */

package montecarlo;

import java.util.LinkedList;

public class others {
    //Diğer işlemleri bu sınıfta yapıyoruz. (min ve max value hesabı gibi)

    //Bu metot gelen bağlı listede en küçük değeri bulur
    public int minValue(LinkedList<Integer> list) {
        //ilk olarak minValue adında bir değişken oluşturup gelen bağlı listenin ilk değerini aktarıyoruz. 
        int minValue = list.get(0);

        //bağlı listnin ikinci elemanından (1.indexinden) başlatıyoruz.
        for (int i = 1; i < list.size(); i++) {

            //Eğer minValeu değeri dönene deeğerden büyükse bu if'e giriyor.
            if (minValue > list.get(i)) {
                //Bu if'e minValeu değer en küçük değildir. Ve minValeu değerini değiştiriyoruz.
                minValue = list.get(i);
            }
        }

        return minValue; //Bulduğumuz minvalue değerini döndürüyoruz.
    }

    //Bu metot gelen bağlı listede en büyük değeri bulur
    public int maxValue(LinkedList<Integer> list) {
        //ilk olarak maxValue adında bir değişken oluşturup gelen bağlı listenin ilk değerini aktarıyoruz.
        int maxValue = list.get(0);

        //bağlı listnin ikinci elemanından (1.indexinden) başlatıyoruz.
        for (int i = 1; i < list.size(); i++) {

            //Eğer macValeu değeri dönene deeğerden kuçukse bu if'e giriyor.
            if (maxValue < list.get(i)) {
                //Bu if'e minValeu değer en büyük değildir. Ve maxValeu değerini değiştiriyoruz.
                maxValue = list.get(i);
            }
        }

        return maxValue;
    }

    //Bu fonksiyon gelenLinkedList'in içeriklerinin toplamını döndürür.
    public int ListTopla(LinkedList<Integer> list) {

        int sum = 0; // ilk oalrak toplı hesaplayacağımız bir değişken oluşturup varsayılan olarak sıfırı atıyoruz.

        //gelen bağl listeyi döndüren bir for düngüsü oluşturuyoruz:
        for (int i = 0; i < list.size(); i++) {
            //Bu döngüde dönen her değeri sum değeri ile topluyoruz. Böylelikle dizideki bütün elemanları toplamış oluyoruz.
            sum += list.get(i);
        }

        return sum; // toplam değerini döndürüyoruz.
    }

    //BubbleShort ile sıralama
    public LinkedList<Integer> frekansSiralama(LinkedList<Integer> frekans, int minValue, int maxValue) {
        /*
        Bu metodun amacı hangi değerin frekansının daha çok veya az olduğunu görmektir. 
        montecarlo da doğrulama kısmında hangi değerin frekansının daha çok olduğuna ihtiyacımız oluyor. B umetot onu sağlıyor
        Örneğin:
        3 değerinden 15 tqne
        4 değerinden 8 tane
        5 değerimdem 18 tane
        6 değerinden 12 tane var
        
        o halde sıralama:
        4<6<3<5 
        şeklinde olacaktır.
        Bu sıralamayı yapan bu metotdur.
        
        Buradaki yapılan işin mantığı:
        -Zaten frekans bağlı listesi minValue ile maxValue arası sıralıdır (Yani ilk değeri 3, ikinci değeri 4, üçüncü değeri 5 ve dördüncü değeri de 6 dır)
        -Biz burada frekans bağlı lsitesini klonlayıp onu sıralıyoruz.
        -Daha sonra oluşturacağımız bağlı listede de valueler mevcut.
        -Frekansın klonlanmış bağlı listesinde yerler değişirken value'leri tutan bağlı listenin de yerlerini değiştiriyoruz.
        -Böylelike sıralı value değerini elde etmiş oluyoruzç
        -Ardından bunu da bağlı liste şekilnde geri döndürüyoruz.
         */

        LinkedList<Integer> sira = new LinkedList<>(); //ilk olarak valueleri sıralaycağımız ve göndereceğimiz bağlı listeyi oluşturuyoruz.
        LinkedList<Integer> siralanmisFrekans = (LinkedList<Integer>) frekans.clone(); // Frekans bağlı listesini klonlayıp farklı bir bağlı liste oluşturuyoruz.
        //Bunun sebebi de frekans'ın yerleirni değiştirirsekk göndermememize rağmen her yerde değişiyor (pointer mantığı). Bu sebeple bağlı listeyi klonlaıyp klon üzeirnde işlem yapıyoruz.

        //ilk olarak sıralancak değerleri sira bağlı listesine aktaracak döngü yapıyoruz:
        for (int i = minValue; i <= maxValue; i++) {
            //min value ile max value arasındaki değerler sıralancağı için o değerler arasonda (min vemax değeler dahil) hepsini sira bağlı listesine ekliyoruz.
            sira.add(i);
        }

        //Buradan sonrası buubleshort algoritmasıdır:
        int n = siralanmisFrekans.size(); //ilk olarak klon olan frekans bağlı listemizin boyurunu n değişkenine atıyoruz.

        //n değişkeni kadar yani frekans bağlı listesinin boyutu kadar bir döngü oluşturuyoruz:
        for (int i = 0; i < n - 1; i++) {
            //ikinci for'umuzu oluşturuyoruz:
            for (int j = 0; j < n - i - 1; j++) {
                if (siralanmisFrekans.get(j) > siralanmisFrekans.get(j + 1)) {
                    //Eğer bu if'e girerse sayıların yerleri değişmesi gerekiyor anlmanıa gelir. 
                    //O zaman klonladığımız frekans bağlı listesinde yerleri değiştiriyoruz:
                    int temp = siralanmisFrekans.get(j);
                    siralanmisFrekans.set(j, siralanmisFrekans.get(j + 1));
                    siralanmisFrekans.set(j + 1, temp);

                    //Aynısını yer değişikliğini sıra bağlı listesinnde de yapıyoruz:
                    int temp2 = sira.get(j);
                    sira.set(j, sira.get(j + 1));
                    sira.set(j + 1, temp2);
                } //if sonu
            }//iç for sonu
        }//for sonu
        
        return sira; // en sonda sıralanmış bağlı listeyi yolluyoruz.
    
    } // metot sonu
    
} //class sonu
