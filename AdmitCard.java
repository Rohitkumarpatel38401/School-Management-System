import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import lib.*;
import java.sql.*;
import javax.swing.border.Border;
import java.awt.print.*;

public class AdmitCard implements ActionListener{
    static JFrame frm;
    JLabel title;
    JLabel image;

    JPanel p1,p2,p3,p4,container,table;
    JButton b1,b2,b3;
    int roll=0;
    PrinterJob pjob;
    String name,fname,course,gen,img;
    public static Boolean isOpen=false;

    AdmitCard(int id){
        isOpen=true;
        roll=id;
        frm=new JFrame("Admit Card");
        frm.setSize(600,650);
        frm.setUndecorated(true);
        frm.setVisible(true);
        frm.setLocationRelativeTo(null);
        frm.setLayout(new BorderLayout(0,10));
        
        p1=new JPanel();
        p2=new JPanel();
        p3=new JPanel();
        title=new JLabel("Admit Card");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Monotype Corsiva",Font.ITALIC,38));
        Color c=new Color(0, 202, 170);
        
        b1=new JButton("Print-Out");
        b2=new JButton("Cancel");
        b1.setBackground(Color.BLUE);
        b1.setForeground(Color.WHITE);
        b2.setForeground(Color.RED);

        p2.setPreferredSize(new Dimension(550,550));
        container=new JPanel(new GridLayout(5,4,0,0));
        container.setPreferredSize(new Dimension(500,190));
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
        container.add(new JLabel("  Roll Number : "));container.add(new JLabel(""+roll));
        container.add(new JLabel("  Name of Student : "));container.add(new JLabel(name));
        container.add(new JLabel("  Father's Name : "));container.add(new JLabel(fname));
        container.add(new JLabel("  Course  : "));container.add(new JLabel(course));
        container.add(new JLabel("  Gender  : "));container.add(new JLabel(gen));
        container.add(new JLabel("  Image    :  "));container.add(new JLabel("  "));
        
        // UI maintain karne ke liye -----------------------------------
        container.add(new JLabel("  "));container.add(new JLabel("  "));
        container.add(new JLabel("  "));container.add(new JLabel("  "));
        container.add(new JLabel("  "));container.add(new JLabel("  "));
        container.add(new JLabel("  "));container.add(new JLabel("  "));
        // UI maintain karne ke liye -----------------------------------
        
        p1.add(title);
        p2.add(container);

        image=new JLabel(new ImageIcon("images/user.jpg"));
        image.setBounds(425,140,100,110);
        frm.add(image);
    ///////////////////////////////////////////////////////////////////////////
        table=new JPanel();

        table.setLayout(new FlowLayout(FlowLayout.CENTER,0,2));
        table.setPreferredSize(new Dimension(502,150));
        // table.setBackground(Color.WHITE);

        JPanel rowhead=new JPanel(new GridLayout(1,5,5,5));
        rowhead.setPreferredSize(new Dimension(500,25));
        rowhead.setFont(new Font("Arial",Font.BOLD,30));
        rowhead.setBackground(c);
        rowhead.setForeground(Color.WHITE);
        rowhead.add(new JLabel("Serial No"));
        rowhead.add(new JLabel("Subject Name"));
        rowhead.add(new JLabel("Exam Date"));
        rowhead.add(new JLabel("Exam Time"));
        rowhead.add(new JLabel("Description"));

        Border blackline = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        table.setBorder(blackline);
        container.setBorder(blackline);
        table.add(rowhead);
        try{
            ResultSet r=Student.viewStudent("select * from exam where e_course='"+course+"'");
         
            while(r.next()){
                JPanel row=new JPanel(new GridLayout(1,5,5,5));
                row.setPreferredSize(new Dimension(500,25));
                System.out.println("Print"+r.getRow());
                row.add(new JLabel("     "+r.getRow()));
                row.add(new JLabel(r.getString("e_subject")));
                row.add(new JLabel(r.getString("e_date")));
                row.add(new JLabel(r.getString("e_time")));
                row.add(new JLabel(r.getString("e_description")));
                table.add(row);
                // row.setBackground(Color.WHITE);
            }
         
        }catch(Exception e){
            System.out.println("Error in admit card  : "+e);
        }
   
       
        p1.setBackground(c);
        p3.setBackground(c);
        p2.add(table);

        JPanel p5=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p5.setPreferredSize(new Dimension(500,25));
        p5.add(new JLabel("Important (Notice and Instructions) "));
        p2.add(p5);

        JPanel p6=new JPanel(new GridLayout(5,1,0,5));
        p6.setPreferredSize(new Dimension(500,100));
        p6.setBorder(blackline);
    
        
        p6.add(new JLabel("  1). This Admit Card is provisional subject the eligibility."));
        p6.add(new JLabel("  2). This Admit Card is provisional subject the eligibility."));
        p6.add(new JLabel("  3). This Admit Card is provisional subject the eligibility."));
        p6.add(new JLabel("  4). This Admit Card is provisional subject the eligibility."));

        p2.add(p6);
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
//        new AdmitCard(103);
//    }
} 