/*
Bünyamin Göymen
02180201041

Kullanılan programlar:
-Netbeans 8.2
-java 8
 */

package montecarlo;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.Number;

public class excel {

    //input excel dosyasındaki verileri okur.
    public LinkedList<Integer> readInput() throws IOException, BiffException {
        LinkedList<Integer> list = new LinkedList<>();
        File DosyaExcel = new File("src/files/input.xls"); //İlk başta dosyayı tanıtıyoruz.
        Workbook CalismaKitabi = Workbook.getWorkbook(DosyaExcel); //Dosyayı bir workbook yani excel dosyayı olarak tanıtıyoruz.
        Sheet ExcelSayfasi = CalismaKitabi.getSheet(0); // Dosyadaki 0.sayfayı tanımlıyoruz.

        int i = 1, j = 1;
        // i değeri sütunları(A,B,C..), j değeri ise satırları(1,2,3..) temsil etmektedir.
        //Alacağımız değer hep B sütununda olacağı için i değeri hep sabit kalacak. j değeri de var olan satır kadar döngü içerisinde artacak.
        //Aynı zamanda j'yi birden başlatıyoruz çünkü en üstteki başlıkları almamalı. (Miktar yazısı gelmemeli). Yani başlığı atlamamızı sağlıyor.

        //Burada kaç satır varsa devam et anlamıan geliyor. Bizim örnekte 48 satır vardı. Ancak daha sonra 36,72 gibi değerlerde olabilir. Bu kodun sadece bu örneğe bağlı kalmasını istemediğim için her şey dinamik şekilde çalışıyor.
        while (j < ExcelSayfasi.getRows()) {
            list.add(Integer.parseInt(ExcelSayfasi.getCell(i, j).getContents()));
            j++;
        } // while son

        return list;
    } // readInput son

    public boolean wirteOutput(LinkedList<Integer> inputValues, LinkedList<Integer> firstFrekans, LinkedList<Double> olasilik, LinkedList<Double> kumulatif, LinkedList<Integer> newValues, LinkedList<Integer> newFrekans, int toplamFrekans, int minValue, int maxValue, montecarlo montecarlo) {
        //JXL kütüphanesindeki excel komutları ile yeni bir excel dosyası oluşturulup içine değerler yazılıyor:
        
        try {
            String fileName = "src/files/output.xls"; //ilk olarak dosyanın konumunu ve adını giriyoruz.
            WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName)); //dosyayı oluşturuyoruz.
            WritableSheet writablesheet1 = workbook.createSheet("Kitap 1", 0); //Bir tane Sayfa oluşturuyoruz
            
            //Buradan sonraki kısım sadece konumlara göre gerekli bilgierin  excel dosyasına yazılmasını sağlıyor. Ödevle alakalı bir kısım olmadığı için fazla yorum satırları eklemeyeceğim.
            
            //ilk olarak başlıkları yazdırıyoruz:
            WritableCell wc = new Label(0, 0, "Ay"); 
            WritableCell wc2 = new Label(1, 0, "Miktar");
            writablesheet1.addCell(wc);
            writablesheet1.addCell(wc2);

            //Program başladığında excel dosyasından gelen verileri yazdırıyoruz.
            for (int i = 1; i <= inputValues.size(); i++) {
                WritableCell wc3 = new Number(0, i, i);
                WritableCell wc4 = new Number(1, i, inputValues.get(i - 1));
                writablesheet1.addCell(wc3);
                writablesheet1.addCell(wc4);
            }

            //Bir sonraki kısım için başlıkları yazdırıyoruz.
            WritableCell wc5 = new Label(3, 0, "Miktar");
            WritableCell wc6 = new Label(4, 0, "Frekans");
            WritableCell wc7 = new Label(5, 0, "Olasılık");
            WritableCell wc8 = new Label(6, 0, "Kümülatif Olasılık");
            writablesheet1.addCell(wc5);
            writablesheet1.addCell(wc6);
            writablesheet1.addCell(wc7);
            writablesheet1.addCell(wc8);

            //frekasn, olasılık ve kümülatif olasılık değerlerini sırasıyla yazıyoruz:
            for (int i = 1; i <= firstFrekans.size(); i++) {
                WritableCell wc9 = new Number(3, i, minValue + i - 1);
                WritableCell wc10 = new Number(4, i, firstFrekans.get(i - 1));
                WritableCell wc11 = new Number(5, i, olasilik.get(i - 1));
                WritableCell wc12 = new Number(6, i, kumulatif.get(i - 1));
                writablesheet1.addCell(wc9);
                writablesheet1.addCell(wc10);
                writablesheet1.addCell(wc11);
                writablesheet1.addCell(wc12);
            }

            //frekans, olaslık ve kümülatif olasılık değerlerinin altındaki kısımları yazıyoruz:
            WritableCell wc13 = new Label(3, firstFrekans.size() + 1, "Toplam");
            WritableCell wc14 = new Number(4, firstFrekans.size() + 1, toplamFrekans);
            WritableCell wc15 = new Number(5, firstFrekans.size() + 1, 1);
            WritableCell wc16 = new Number(6, firstFrekans.size() + 1, 1);
            writablesheet1.addCell(wc13);
            writablesheet1.addCell(wc14);
            writablesheet1.addCell(wc15);
            writablesheet1.addCell(wc16);

            //Yeni rastgele sayı ile oluşan değerler için başlık ekliyoruz:
            WritableCell wc17 = new Label(10, 0, "Ay");
            WritableCell wc18 = new Label(11, 0, "Miktar");
            WritableCell wc19 = new Label(12, 0, "Tahmini Tutar");
            writablesheet1.addCell(wc17);
            writablesheet1.addCell(wc18);
            writablesheet1.addCell(wc19);

            //Yeni oluşan değerleri (Ayı ile birlikte) ve rastgele sayıları yazdırıyoruz.
            for (int i = 1; i <= montecarlo.randomNumbers.size(); i++) {

                WritableCell wc20 = new Number(10, i, i);
                WritableCell wc21 = new Number(11, i, montecarlo.randomNumbers.get(i - 1));
                WritableCell wc22 = new Number(12, i, newValues.get(i - 1));
                writablesheet1.addCell(wc20);
                writablesheet1.addCell(wc21);
                writablesheet1.addCell(wc22);

            }

            //Yeni oluşan frekansı yazmak için başlığı yazıyoruz:
            WritableCell wc23 = new Label(15, 0, "Miktar");
            WritableCell wc24 = new Label(16, 0, "Frekans");
            writablesheet1.addCell(wc23);
            writablesheet1.addCell(wc24);

            //Yeni oluşan frekansları sırasıyla yazdırıyoruz:
            for (int i = 1; i <= newFrekans.size(); i++) {
                WritableCell wc25 = new Number(15, i, minValue + i - 1);
                WritableCell wc26 = new Number(16, i, newFrekans.get(i - 1));
                writablesheet1.addCell(wc25);
                writablesheet1.addCell(wc26);
            }
            
            others other = new others(); // others sınıfındaki metotları kullanmak için bir değişken oluşturuyoruz.
            
            //Ynei oluşan frekansların altındaki değerleir yazdırıyoruz:
            WritableCell wc27 = new Label(15, newFrekans.size() + 1, "Genel Toplam");
            WritableCell wc28 = new Number(16, newFrekans.size() + 1, other.ListTopla(newFrekans)); //burada toplam frekans yazması gerektiği için others sınıfındaki metotdan yararlanıyoruz.
            writablesheet1.addCell(wc27);
            writablesheet1.addCell(wc28);

            //En sonda da dosyayı yaz ve kapat diyoruz:
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            //Buraya düşerse de hangi cathc olduğunu belirtip hatayı ekrana yazdırıyoruz.
            System.out.println("Hata(catch 1): ");
            e.printStackTrace();
            return false; //Hata olduğu için false dönüyoruz
        } catch (WriteException ex) {
            //Buraya düşerse de hangi cathc olduğunu belirtip hatayı ekrana yazdırıyoruz.
            System.out.println("Hata(catch 1): ");
            ex.printStackTrace();
            return false;//Hata olduğu için falzse dönüyoruz:
        }

        return true; //Eğer herhangi bir hata ile karşılaşılmamı ise de true değerini gönderiyoruz.
    }
}
