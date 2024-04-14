import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import lib.*;
import java.sql.*;
import java.util.Date;

public class FeesPay implements ActionListener,ItemListener,KeyListener{
    JFrame frm;
    JLabel title,l1;
    JPanel p1,p2,p3,p4,p5,container;
    JButton b1,b2;
    JTextField t1,t2,t3,t4,t5,t6,t7;
    JTextArea t8;
    JRadioButton r1,r2;
    ButtonGroup btng;
    JComboBox cb,cb1;
    String roll;
    String currentDate="";
    String balance;
    String SQL="";
    FeesPay(){
        
        Date d = new Date();
        currentDate=d.getDate()+"/"+(d.getMonth()+1)+"/"+d.getYear();
       
        frm=new JFrame("Pay Fees");
        frm.setSize(750,380);
        frm.setUndecorated(true);
        frm.setVisible(true);
        frm.setResizable(false);
        frm.setLocationRelativeTo(null);
        frm.setLayout(new BorderLayout(10,20));
        p1=new JPanel();
        p2=new JPanel();
        p3=new JPanel();
        title=new JLabel("Fees Payment");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Monotype Corsiva",Font.ITALIC,38));
     
        btng=new ButtonGroup();
        r1=new JRadioButton("Male");
        r2=new JRadioButton("Female"); 
        btng.add(r1);btng.add(r2);
        p4=new JPanel();
        // p4.setBackground(Color.WHITE);
        p4.add(r1);p4.add(r2);

        cb1=new JComboBox();
        cb1.addItem("Select Roll No");

        cb=new JComboBox();
        cb.addItem("Select Course");
        cb.setEnabled(false);
        try{
            ResultSet rs=Student.viewStudent("select id from students");
            while(rs.next()){
                cb1.addItem(String.valueOf(rs.getInt("id")));
            }
            rs=Student.viewStudent("select * from course");
            while(rs.next()){
                cb.addItem(String.valueOf(rs.getString("C_Name")));
            }
        }catch(Exception e){
            System.out.println("cb1 : "+e);
        }
      

        t1=new JTextField(20);
        t2=new JTextField(20);
        t3=new JTextField(20);
        t4=new JTextField(20);
        t5=new JTextField(20);
        t6=new JTextField(20);
        t7=new JTextField(20);
        t8=new JTextArea();

        t2.setEditable(false);
        t3.setEditable(false);
        t4.setEditable(false);
        t6.setEditable(false);
        // t7.setEditable(false);
        r1.setEnabled(false);
        r2.setEnabled(false);
        b1=new JButton("Submit");
        b1.setBackground(Color.BLUE);
        b1.setForeground(Color.WHITE);
     
        b2=new JButton("Cancel");
        b2.setForeground(Color.WHITE);
        b2.setBackground(Color.RED);

        p2.setPreferredSize(new Dimension(750,200));
        container=new JPanel(new GridLayout(5,4,25,15));
        container.setPreferredSize(new Dimension(630,240));
        // container.setBackground(Color.WHITE);
        container.add(new JLabel("Roll Number : ")); container.add(cb1);
        container.add(new JLabel("Student Name : ")); container.add(t2);
        container.add(new JLabel("Father's Name : ")); container.add(t3);
        container.add(new JLabel("Gender")); container.add(p4);
        container.add(new JLabel("Course : ")); container.add(cb);
        container.add(new JLabel("Total : ")); container.add(t4);
        container.add(new JLabel("Enter Amount : ")); container.add(t5);
        container.add(new JLabel("Balance : ")); container.add(t6);
        container.add(new JLabel("Date : ")); container.add(t7);
        container.add(new JLabel("Enter Description : ")); container.add(t8);
        Color c=new Color(255,20,150);
        p1.setBackground(c);
        // p2.setBackground(Color.WHITE);
        p3.setBackground(c);

        p1.add(title);
        p2.add(container);
        
        p3.add(b1);
        p3.add(b2);
        frm.add(p1,BorderLayout.NORTH);
        frm.add(p2,BorderLayout.CENTER);
        frm.add(p3,BorderLayout.SOUTH);

        b1.addActionListener(this);
        b2.addActionListener(this);
        cb.addItemListener(this);
        cb1.addItemListener(this);

        t5.addKeyListener(this);

        SwingUtilities.updateComponentTreeUI(frm);
    }
    
    public void itemStateChanged(ItemEvent ie){ 
        if(ie.getSource()==cb1){
            if(cb1.getSelectedItem()!="Select Roll No"){
            try{
                roll=String.valueOf(cb1.getSelectedItem());
                String SQL="select * from fees where id="+roll;
                ResultSet rs=Student.viewStudent(SQL);
                if(rs.next()){
                    balance=rs.getString("balance");
                }
                if(balance.equals("0")){
                    t5.setText("Already Paid");
                    t5.setEditable(false);
                }else{
                    t5.setEditable(true);
                    t5.setText("");
                }
                t6.setText(balance);
                SQL="Select * from students where id="+roll;
                rs=Student.viewStudent(SQL);
                if(rs.next()){
                    t2.setText(rs.getString("name"));
                    t3.setText(rs.getString("f_name"));
                    String gen=rs.getString("gender");
                    t7.setText(currentDate);
                    if(gen.equals("Male")) r1.setSelected(true);
                    if(gen.equals("Female")) r2.setSelected(true);
                    String course=rs.getString("Course");
                    SQL="Select * from course where c_name='"+course+"'";
                    rs=Student.viewStudent(SQL);
                    if(rs.next()){
                        cb.setSelectedItem(course);
                        t4.setText(String.valueOf(rs.getInt("c_fees")));
                    }else{
                        cb.setSelectedItem("Select Course");
                        t4.setText("");
                    }
                }
            }catch(Exception e){
                System.out.println("Select Roll error : "+e);
            }
        }else{
            JOptionPane.showMessageDialog(frm,"Plz select Roll No"); 
        }
      }  
    }

    public void keyPressed(KeyEvent ke){ }
    public void keyTyped(KeyEvent ke){ }
    public void keyReleased(KeyEvent ke){ 
        int Total=Integer.parseInt(t4.getText());
        int amt=Integer.parseInt(t5.getText());
                   
        int bal=0;
        if(amt%100!=0 && amt>=100){
            JOptionPane.showMessageDialog(frm,"Plz. Enter Amount multiples of 100"); 
        }else{
            bal=Integer.parseInt(balance)-amt;
            if(amt <=Integer.parseInt(balance) )
                t6.setText(String.valueOf(bal));
            else{
                JOptionPane.showMessageDialog(frm,"Plz. Enter Amount Less/Equals of "+balance); 
                t5.setText("");
                t6.setText(balance);
            }
        }
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==b1){
            if(cb1.getSelectedItem()=="Select Roll No"){
                JOptionPane.showMessageDialog(frm,"Plz select Roll Number !!");
                return;
            } 
            if(t5.getText().equals("Already Paid")){
                JOptionPane.showMessageDialog(frm,"Already Paid !!!");
                return;
            }
          
            if(t5.getText().trim().length()!=0){

                String amt=t5.getText();
                String bal=t6.getText();
                String dat=t7.getText();
                String desc=t8.getText();

               
                SQL="update fees set amount=concat(amount,'"+amt+",'),balance='"+bal+"',dates=concat(dates,'"+dat+",'),description=concat(description,'"+desc+",') where id="+roll;
              
                if(Student.delete_Student(SQL)){
                    JOptionPane.showMessageDialog(frm,"Fees Payment Success!");
                    frm.dispose();
                    new FeesPay();
                }else{
                    JOptionPane.showMessageDialog(frm,"Something went wrong!!");
                }
            }else{
                JOptionPane.showMessageDialog(frm,"Plz. Enter Amount!!");
            }
        }
        if(ae.getSource()==b2){
            frm.dispose();
        }
    }
   public static void main(String args[]){
        new FeesPay();
    }
} 