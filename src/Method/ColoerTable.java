/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Method;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Home-PC
 */
public class ColoerTable extends DefaultTableCellRenderer {
private Component Componente;
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Componente= super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
//To change body of generated methods, choose Tools | Templates.
      
        if(row%2==0){
            
            Componente.setBackground(Color.red);
        }else{
            
             Componente.setBackground(Color.blue);
        }
      return Componente;

    }

}
