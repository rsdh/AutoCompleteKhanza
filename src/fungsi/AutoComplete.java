/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fungsi;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author rizki999
 */
public class AutoComplete<T> {
    
    private AutoCopleteListener<T> aListener;
    private Timer timer;
    private boolean isHitHenter = false;
    private DefaultComboBoxModel model = new DefaultComboBoxModel();
    private JComboBox cbInput = new JComboBox(model);
    private JTextField txtInput;
    private int delayInMiliSec;
    
    public AutoComplete(JTextField txtInput, int setOverSizePopUp, int delayInMiliSec) {
        this.txtInput = txtInput;
        this.delayInMiliSec = delayInMiliSec;
        this.model = new DefaultComboBoxModel();
        this.cbInput = new JComboBox(model) {
            public Dimension getPreferredSize() {
                return new Dimension(
                        super.getPreferredSize().width + setOverSizePopUp, 
                        0
                );
            }
        };
        setAdjusting(cbInput, false);
        this.cbInput.setSelectedItem(null);
        this.cbInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAdjusting(cbInput)) {
                    if (cbInput.getSelectedItem() != null) {
                        T data = (T)cbInput.getSelectedItem();
                        if (aListener != null)
                            aListener.dataSelcted(data);
                    }
                }
            }
        });

        txtInput.addKeyListener(new KeyAdapter() {
            
            @Override
            public void keyReleased(KeyEvent e) {
                String keyString = ""+e.getKeyChar();
                keyString = keyString.toLowerCase();
                switch (keyString) {
                    case "a" :
                    case "b" :
                    case "c" :
                    case "d" :
                    case "e" :
                    case "f" :
                    case "g" :
                    case "h" :
                    case "i" :
                    case "j" :
                    case "k" :
                    case "l" :
                    case "m" :
                    case "n" :
                    case "o" :
                    case "p" :
                    case "q" :
                    case "r" :
                    case "s" :
                    case "t" :
                    case "u" :
                    case "v" :
                    case "w" :
                    case "x" :
                    case "y" :
                    case "z" :
                    case "1" :
                    case "2" :
                    case "3" :
                    case "4" :
                    case "5" :
                    case "6" :
                    case "7" :
                    case "8" :
                    case "9" :
                    case "0" :
                    case " " :
                    case "-" :
                    case "(" :
                    case ")" :
                        updateList();
                        break;
                    default :
                        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                            updateList();
                            if (txtInput.getText().isEmpty())
                                if (aListener != null)
                                    aListener.textCleared();
                        }
                        break;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                setAdjusting(cbInput, true);
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    e.setSource(cbInput);
                    cbInput.dispatchEvent(e);
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {                        
                        //txtInput.setText(cbInput.getSelectedItem().toString());
                        isHitHenter = true;
                        T data = (T)cbInput.getSelectedItem();
                        if (aListener != null)
                            aListener.dataSelcted(data);
                        cbInput.setPopupVisible(false);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cbInput.setPopupVisible(false);
                }
                setAdjusting(cbInput, false);
            }
        });
        txtInput.setLayout(null);
        txtInput.add(cbInput);
        cbInput.setBounds(
                0, 
                txtInput.getPreferredSize().height,
                txtInput.getPreferredSize().width + setOverSizePopUp, 
                0
        );
    }
    
    private void updateList() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (aListener != null) {
                                if (isHitHenter) {
                                    setAdjusting(cbInput, true);
                                    isHitHenter = false;
                                    model.removeAllElements();
                                    cbInput.setPopupVisible(false);
                                    setAdjusting(cbInput, false);
                                    return;
                                }
                                ArrayList<T> items = aListener.setDataSource();
                                setAdjusting(cbInput, true);
                                model.removeAllElements();
                                if (!txtInput.getText().isEmpty())
                                    for (T item:items)
                                        model.addElement(item);
                                cbInput.setPopupVisible(model.getSize() > 0);
                                setAdjusting(cbInput, false);
                            }
                        }
                    }).start();
                }
            }, 
            delayInMiliSec
        );                
    }
    
    public void setAutoCompleteListener(AutoCopleteListener<T> aListener) {
        this.aListener = aListener;
    }
    
    private boolean isAdjusting(JComboBox cbInput) {
        if (cbInput.getClientProperty("is_adjusting") instanceof Boolean) {
            return (Boolean) cbInput.getClientProperty("is_adjusting");
        }
        return false;
    }

    private void setAdjusting(JComboBox cbInput, boolean adjusting) {
        cbInput.putClientProperty("is_adjusting", adjusting);
    }
    
    public interface AutoCopleteListener<T> {
        public void dataSelcted(T tSelected);
        public ArrayList<T> setDataSource();
        public void textCleared();
    }
    
}
