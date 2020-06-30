
import fungsi.AutoComplete;
import java.awt.Frame;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rizki999
 */
public class Demo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Demo demo = new Demo();
           demo.tampil();
       });
    }
    
    private void tampil() {
        Frame f= new JFrame("Autocomplete Swing Java Demo");  
        JTextField tKode, tCariNama;
        
        tKode=new JTextField();  
        tKode.setBounds(50,100, 200,30);  
        tCariNama=new JTextField();  
        tCariNama.setBounds(50,150, 200,30);  
        
        new AutoComplete<DataLocale>(tCariNama, 300, 800).setAutoCompleteListener(new AutoComplete.AutoCopleteListener<DataLocale>() {
            /* 
            * Method untuk mengseleksi data yanga ada di list ketika event enter dan click data item di list autocomplete
            * method ini di eksekusi ketika user meng enter list dari autocomplete
            */
            @Override
            public void dataSelcted(DataLocale tSelected) {
                tKode.setText(tSelected.id);
                tCariNama.setText(tSelected.nama);
            }
            
            /* 
            * Method ini berfungsi mengset data untuk autocomplete list
            * method ini di eksekusi ketika user berhenti mengetik dari 800ms
            */
            @Override
            public ArrayList<DataLocale> setDataSource() {
                ArrayList<DataLocale> items = new ArrayList<>();
                
                /*
                *
                * Di sini sampel data source nya pake Locale
                */
                Locale list[] = DateFormat.getAvailableLocales();
                int id = 0;
                
                for (Locale aLocale : list)
                    if (("" + (++id)).contains(tCariNama.getText()) || aLocale.getDisplayName().toLowerCase().contains(tCariNama.getText())) // filter data
                        items.add(
                                new DataLocale (
                                        "" + id,
                                      aLocale.getDisplayName()
                                )
                        );
                
                return items;
            }
            
            /* 
            * Method untuk menghapus/mengcancel data yang sudah di seleksi
            * method ini di eksekusi ketika user meng enter list dari autocomplete
            */
            @Override
            public void textCleared() {
                tKode.setText("");
                tCariNama.setText("");
            }
        });
        
        f.add(tKode);
        f.add(tCariNama);
        f.setSize(400,400);  
        f.setLayout(null);          
        f.setVisible(true);          
        tCariNama.requestFocus();
    }
    
    public class DataLocale {
        public DataLocale(String id, String nama) {
            this.id = id;
            this.nama = nama;
        }
        
        public String id;
        public String nama;
        
        /* 
        * Method untuk menghapus/mengcancel data yang sudah di seleksi
        * method ini di eksekusi ketika user meng enter list dari autocomplete
        */
        @Override
        public String toString() {
            return nama + " (" + id + ")";
        }
    }
}
