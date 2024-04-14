import lib.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.awt.print.*;
class ViewFees implements ActionListener{
    JFrame frm;
    JLabel title;
    JPanel table,p1,p2,p3;
    JButton b1,b2,b3,b4,b5;
    JScrollPane sp;
    String SQL;
    PrinterJob job;
    JButton buttons[];
    int sid[];
    ViewFees(){
        frm=new JFrame("View Fees");
        frm.setVisible(true);
        frm.setLocationRelativeTo(null);
        frm.setExtendedState(frm.MAXIMIZED_BOTH);
        frm.setLayout(new BorderLayout(0,0));
        title=new JLabel("All Fees Records");
        title.setFont(new Font("Arial",Font.BOLD,30));
        title.setForeground(Color.WHITE);
        p2=new JPanel();
        p2.setLayout(new FlowLayout(FlowLayout.CENTER,0,1));
        table=new JPanel();
        table.setPreferredSize(new Dimension(1000,700));
        table.setBackground(Color.WHITE);
        sp=new JScrollPane(table);
        p1=new JPanel();
        p3=new JPanel();
        Color clr=new Color(255,20,150);
        p1.setBackground(clr);
        p3.setBackground(clr);
        b1=new JButton("Print");
        b2=new JButton("Close");
        b1.setBackground(Color.BLUE);
        b1.setForeground(Color.WHITE);
        b2.setForeground(Color.RED);
       
        b1.addActionListener(this);
        b2.addActionListener(this);
       
        p1.add(title);
        p2.add(table);
        p3.add(b1); p3.add(b2); 

        frm.add(p1,BorderLayout.NORTH);
        frm.add(p2,BorderLayout.CENTER);
        frm.add(p3,BorderLayout.SOUTH);

        SQL="Select * from fees order by id";
        showDataTable(SQL);
        
        SwingUtilities.updateComponentTreeUI(frm);
    }
    void showDataTable(String SQL){
        table.removeAll();

        JPanel rowhead=new JPanel(new GridLayout(1,9,5,5));
        rowhead.setPreferredSize(new Dimension(1000,25));
        rowhead.setFont(new Font("Arial",Font.BOLD,30));
        rowhead.setBackground(Color.PINK);
        rowhead.setForeground(Color.WHITE);
        rowhead.add(new JLabel("Fees Id"));
        rowhead.add(new JLabel("Course"));
        rowhead.add(new JLabel("Total"));
        rowhead.add(new JLabel("Amounts"));
        rowhead.add(new JLabel("Balance"));
        rowhead.add(new JLabel("Fees Dates"));
        rowhead.add(new JLabel("View Receipt"));
        
        
        table.add(rowhead);
        try{
          int c=0;
            int s=Student.countRow("fees");
            buttons=new JButton[s+1];
            sid=new int[s+1];
            
            ResultSet r=Student.viewStudent("Select * from fees order by id");
            while(r.next()){
                c++;
                JPanel row=new JPanel(new GridLayout(1,9,5,5));
                row.setPreferredSize(new Dimension(1000,25));
                row.add(new JLabel(""+r.getInt("id")));
                row.add(new JLabel(r.getString("course")));
                row.add(new JLabel(r.getString("total")));
                row.add(new JLabel(r.getString("amount")));
                row.add(new JLabel(r.getString("balance")));
                row.add(new JLabel(r.getString("dates")));
                // row.add(new JLabel(r.getString("description")));
////////////////////////////////////////////////////////////////
                JButton tb=new JButton("View");
            
                buttons[c]=tb;
                sid[c]=r.getInt("id");

                tb.addActionListener(this);
                row.add(tb);
                /////////////////////////////////////////////////

                table.add(row);
                row.setBackground(Color.WHITE);
                Thread.sleep(50);
                SwingUtilities.updateComponentTreeUI(frm);
                
            }
            if(c==0){
                table.remove(rowhead);
                table.add(new JLabel("No have Any Records !!")); 
            }
        }catch(Exception e){
            System.out.println("View Error : "+e);
        }
        SwingUtilities.updateComponentTreeUI(frm);
    }
    public void actionPerformed(ActionEvent e){
        System.out.println("Clicked");
        if(e.getSource()==b1){ // Print
            try{
                job=PrinterJob.getPrinterJob();
                if(job.printDialog()){
                    System.out.println("Printing Start...");
                    job.print();
                    System.out.println("Printing Ended...");
                }else{
                    System.out.println("Abort Printing.!!");
                }
            }catch(PrinterException pe){
                System.out.println("Something went wrong!");
            }
        }

        if(e.getSource()==b2){
            if(JOptionPane.showConfirmDialog(frm,"Are sure want to Close this page!!")==0){
                frm.dispose();
            }
        }
        for(int i=0;i<buttons.length;i++){
            if(e.getSource()==buttons[i]){
                // System.out.println(sid[i]);
                new FeesReceipt(sid[i]);
            }
        }
        
    }
    public static void main(String args[]){
        new ViewFees();
    }

}