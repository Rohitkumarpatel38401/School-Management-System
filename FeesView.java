import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import lib.*;
import javax.swing.table.*;

class FeesView{
    JFrame frm;
    JLabel title;
    JTable table;
    JScrollPane sp;
    Font f;
    String data[][]={
        {"1001","Rohit","Programmer"},
        {"1002","Amit","Engineer"},
        {"1003","Sumit","Developer"},
        {"1004","Ramesh","Programmer"}
    };
    String column[]={"Emp_id","Emp_Name","Designation"};
    FeesView(){
        frm=new JFrame("Fees View");
        table=new JTable(data,column);
        table.setEnabled(false);
        table.setForeground(Color.WHITE);
        table.setBackground(Color.BLACK);
        table.setGridColor(Color.CYAN);
        table.setRowHeight(40);
        table.getTableHeader().setDefaultRenderer(new MyCellRenderer());
        sp=new JScrollPane(table);
        frm.add(sp);
        frm.setSize(800,500);
        frm.setVisible(true);
        frm.setLocationRelativeTo(null);

        f=new Font("cursive",Font.PLAIN,20);
        table.setFont(f);
    }
    
    public static void main(String args[]){
        new FeesView();
    }
}
class MyCellRenderer extends JLabel implements TableCellRenderer{
    MyCellRenderer(){
        setFont(new Font("Arial",Font.BOLD,30));
        setBorder(BorderFactory.createEtchedBorder());
        setHorizontalAlignment(JLabel.CENTER);
        setForeground(Color.WHITE);
        setBackground(Color.RED);
        setOpaque(true);
    }
    public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected,boolean hasFocus,int row,int column){
        setText(value.toString());
        return this;
    }
}