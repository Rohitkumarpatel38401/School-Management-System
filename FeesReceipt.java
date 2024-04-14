import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import lib.*;
import java.sql.*;
import javax.swing.border.Border;
import java.awt.print.*;

public class FeesReceipt implements ActionListener{
    JFrame frm;
    JLabel title;
    JLabel image;

    JPanel p1,p2,p3,p4,container,table;
    JButton b1,b2,b3;
    int roll=0;
    PrinterJob pjob;
    String name,fname,course,gen,img;
    FeesReceipt(int id){
        roll=id;
        frm=new JFrame("Receipt");
        frm.setSize(600,430);
        frm.setUndecorated(true);
        frm.setVisible(true);
        frm.setLocationRelativeTo(null);
        frm.setLayout(new BorderLayout(0,0));
        
        p1=new JPanel();
        p2=new JPanel();
        p3=new JPanel();
        title=new JLabel("Fees Receipt");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Monotype Corsiva",Font.ITALIC,38));
        Color c=new Color(0, 202, 170);
        
        b1=new JButton("Print-Out");
        b2=new JButton("Cancel");
        b1.setBackground(Color.BLUE);
        b1.setForeground(Color.WHITE);
        b2.setForeground(Color.RED);

        p2.setPreferredSize(new Dimension(550,550));
        container=new JPanel(new GridLayout(3,4,0,0));
        container.setPreferredSize(new Dimension(500,100));
        // p2.setBackground(Color.WHITE);
        
        try{
            ResultSet rs=Student.viewStudent("select * from students where id="+roll);
            rs.next();
            name=rs.getString("name");
            fname=rs.getString("f_name");
            gen=rs.getString("gender");
            course=rs.getString("course");
        }catch(Exception e){
            System.out.println("cb2 : "+e);
        }
        container.add(new JLabel("  Receipt Numver : "));container.add(new JLabel(""+roll));
        container.add(new JLabel("  Name of Student : "));container.add(new JLabel(name));
        container.add(new JLabel("  Father's Name : "));container.add(new JLabel(fname));
        container.add(new JLabel("  Course  : "));container.add(new JLabel(course));
        container.add(new JLabel("  Gender  : "));container.add(new JLabel(gen));
        container.add(new JLabel("    "));container.add(new JLabel("  "));
       
        
        p1.add(title);
        p2.add(container);

       
    ///////////////////////////////////////////////////////////////////////////
        table=new JPanel();

        table.setLayout(new FlowLayout(FlowLayout.CENTER,0,2));
        table.setPreferredSize(new Dimension(502,200));
        // table.setBackground(Color.WHITE);

        JPanel rowhead=new JPanel(new GridLayout(1,5,5,5));
        rowhead.setPreferredSize(new Dimension(500,25));
        rowhead.setFont(new Font("Arial",Font.BOLD,30));
        rowhead.setBackground(c);
        rowhead.setForeground(Color.WHITE);
        rowhead.add(new JLabel(" Serial No"));
        rowhead.add(new JLabel("Payment Date"));
        rowhead.add(new JLabel("Payment Mode"));
        rowhead.add(new JLabel("Amount"));

        Border blackline = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        table.setBorder(blackline);
        container.setBorder(blackline);
        table.add(rowhead);
        try{
            ResultSet r=Student.viewStudent("select * from fees where id="+roll);
            r.next();
            
            String[] amounts=r.getString("amount").split(",");
            String[] dates=r.getString("dates").split(",");
            String[] pmtMode=r.getString("description").split(",");
            int totalAmount=0;
            JPanel row;
            int i=0;

            while(i<amounts.length){
                row=new JPanel(new GridLayout(1,4,5,5));
                row.setPreferredSize(new Dimension(500,25));
                row.add(new JLabel("  "+(i+1)));
                row.add(new JLabel( dates[i].trim() ));
                row.add(new JLabel( pmtMode[i].trim() ));
                row.add(new JLabel( amounts[i].trim() +" /-"));

                totalAmount=totalAmount+ Integer.parseInt(amounts[i].trim());
                table.add(row);
                i++;
            }

            row=new JPanel(new GridLayout(2,4,5,5));
            row.setPreferredSize(new Dimension(495,40));
            row.add(new JLabel("To Be Paid:")); row.add(new JLabel( r.getString("total")+ ".00"));
            row.add(new JLabel("Total Paid :")); row.add(new JLabel(totalAmount+".00") );
            
            row.add(new JLabel("Next Due :"));
            if(r.getString("balance").trim().equals("0")){
                row.add(new JLabel("Fully Paid"));
            }else{
                row.add(new JLabel(r.getString("balance")+".00"));
            }
            row.add(new JLabel(" "));
            row.setBorder(blackline);
            table.add(row);
            

        }catch(Exception e){
            System.out.println("Error in admit card  : "+e);
        }
   
       
        p1.setBackground(c);
        p3.setBackground(c);
        p2.add(table);

        p3.add(b1); p3.add(b2);
        frm.add(p1,BorderLayout.NORTH);
        frm.add(p2,BorderLayout.CENTER);
        frm.add(p3,BorderLayout.SOUTH);

        b1.addActionListener(this);
        b2.addActionListener(this);

        SwingUtilities.updateComponentTreeUI(frm);

    }
    
    public void actionPerformed(ActionEvent ev){
        // System.out.println("Clicked!!");
       
        if(ev.getSource()==b1){ // Print
            try{
                pjob=PrinterJob.getPrinterJob();
                if(pjob.printDialog()){
                    System.out.println("Printing Start...");
                    pjob.print();
                    System.out.println("Printing Ended...");
                }else{
                    System.out.println("Abort Printing.!!");
                }
            }catch(PrinterException pe){
                System.out.println("Something went wrong!");
            }
        }
        if(ev.getSource()==b2){
            frm.dispose();
        }
        SwingUtilities.updateComponentTreeUI(frm);
    }
    
//    public static void main(String args[]){
//        new FeesReceipt(104);
//    }
} 