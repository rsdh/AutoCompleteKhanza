# AutoCompleteKhanza

Utilitas kecil ini di buat untuk mempermudah penyeleksian data di SIMRS Khanza

Semoga utilitas kecil ini bermanfaat

Ho to use :

Cuman panggil class nya saja seperti :
```java
new AutoComplete<T>( // T Untuk Type Class untuk menampung data
        jTextField1, // Text Box untuk pencarian auto complete
        300,         // tambahan width untuk list item atocompleate dari width jTextField1 semisal jTextField1 width 500 + 300
        800          // delay ketika eksekusi pencarian data nya
   ).setAutoCompleteListener(new AutoComplete.AutoCopleteListener<T>() {
            /* 
            * Method untuk mengseleksi data yanga ada di list ketika event enter dan click data item di list autocomplete
            * method ini di eksekusi ketika user meng enter list dari autocomplete
            */
            @Override
            public void dataSelcted(DataLocale tSelected) {
            
            }
            
            /* 
            * Method ini berfungsi mengset data untuk autocomplete list
            * method ini di eksekusi ketika user berhenti mengetik dari 800ms
            */
            @Override
            public ArrayList<T> setDataSource() {
                ArrayList<T> items = new ArrayList<>();
                
                /*
                *
                * Di sini data source
                */
                
                
                return items;
            }
            
            /* 
            * Method untuk menghapus/mengcancel data yang sudah di seleksi
            * method ini di eksekusi ketika user meng enter list dari autocomplete
            */
            @Override
            public void textCleared() {
                
            }
        });
```
