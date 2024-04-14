import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import lib.*;
import java.sql.*;
public class AddStudent implements ActionListener{
    static JFrame frm;
    static Boolean isOpen=false;
    JLabel title;
    JPanel p1,p2,p3,p4,p5,container,table;
    JButton b1,b2,b3;
    JTextField t1,t2,t3,t4,t5,t6;
    JRadioButton r1,r2;
    ButtonGroup btng;
    JComboBox cb;
    JCheckBox c1,c2,c3;
    int roll=0;
    AddStudent(){
        isOpen=true;
        frm=new JFrame("Add Student");
        frm.setSize(550,520);
        frm.setUndecorated(true);
        frm.setVisible(true);
        frm.setLocationRelativeTo(null);
        frm.setLayout(new BorderLayout(0,10));
        
        p1=new JPanel();
        p2=new JPanel();
        p3=new JPanel();
        title=new JLabel("Student Registration");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Monotype Corsiva",Font.ITALIC,38));
        
        btng=new ButtonGroup();
        r1=new JRadioButton("Male");
        r2=new JRadioButton("Female"); 
        btng.add(r1);btng.add(r2);
        p4=new JPanel();
        p4.add(r1);p4.add(r2);

        cb=new JComboBox();
        cb.addItem("Please Select");
        try{
            ResultSet rs=Student.viewStudent("select * from course");
            while(rs.next()){
                cb.addItem(String.valueOf(rs.getString("C_Name")));
            }
        }catch(Exception e){
            System.out.println("cb1 : "+e);
        }
    
        c1=new JCheckBox("10th");
        c2=new JCheckBox("12th");
        c3=new JCheckBox("Graduate");
        p5=new JPanel();
        p5.add(c1); p5.add(c2); p5.add(c3);

        t1=new JTextField(20);
        t2=new JTextField(20);
        t3=new JTextField(20);
        t4=new JTextField(20);
        t5=new JTextField(20);

        b1=new JButton("Register");
        b1.setBackground(Color.BLUE);
        b1.setForeground(Color.WHITE);
        b2=new JButton("Reset");
        b3=new JButton("Close");

        int roll=Student.getMaxValue("students","id");
        if(roll==0) roll=101; //initial roll no.
        else roll++;
        t6=new JTextField(String.valueOf(roll));
        t6.setFont(new Font("arial",Font.PLAIN,18));
        t6.setForeground(Color.BLUE);
        t6.setEditable(false);

        p2.setPreferredSize(new Dimension(400,450));
        container=new JPanel(new GridLayout(10,2,10,10));
        container.setPreferredSize(new Dimension(400,450));
        container.add(new JLabel("Roll Number : "));container.add(t6);
        container.add(new JLabel("Enter Full Name : ")); container.add(t1);
        container.add(new JLabel("Father's Name : ")); container.add(t2);
        container.add(new JLabel("Select Gender : ")); container.add(p4);
        container.add(new JLabel("Enter Address : ")); container.add(t3);
        container.add(new JLabel("Enter Mobile Number : ")); container.add(t4);
        container.add(new JLabel("Select Course : ")); container.add(cb);
        container.add(new JLabel("Enter DOB : ")); container.add(t5);
        container.add(new JLabel("Select Qualification :")); container.add(p5);

        Color c=new Color(255,20,150);
        p1.setBackground(c);
        p3.setBackground(c);

        p1.add(title);
        p2.add(container);
        
        p3.add(b1); p3.add(b2); p3.add(b3);
        frm.add(p1,BorderLayout.NORTH);
        frm.add(p2,BorderLayout.CENTER);
        frm.add(p3,BorderLayout.SOUTH);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);

    

        SwingUtilities.updateComponentTreeUI(frm);
    }
    public void actionPerformed(ActionEvent ev){
        // System.out.println("Clicked!!");
        String name,fname,gen,add,phone,course,dob,qual;
        String SQL="";
        
        name=t1.getText();
        fname=t2.getText();
        gen="";
        if(r1.isSelected()) gen="Male";
        if(r2.isSelected()) gen="Female";
        add=t3.getText();
        phone=t4.getText();
        course=String.valueOf(cb.getSelectedItem());
        dob=t5.getText();
        qual="";
        if(c1.isSelected()) qual+="10th,";
        if(c2.isSelected()) qual+="12th, ";
        if(c3.isSelected()) qual+="Graduate";
        
        if(ev.getSource()==b1){//Insert Code Query
            if(name.length()==0 || fname.length()==0 || gen.length()==0 || add.length()==0 || phone.length()==0 || course.length()==0 || dob.length()==0 || qual.length()==0){
                JOptionPane.showMessageDialog(frm,"Please Fill All Detalis !!!");
                return;
            }
            if(phone.length()!=10){
                JOptionPane.showMessageDialog(frm,"Plz. Enter Valid Phone Number 10 Digits !!!");
                return;
            }
            int roll=Student.getMaxValue("students","id");
            if(roll==0) roll=101; //initial roll no.
            else roll++;
            SQL="Insert into students(id,name,f_name,gender,address,phone,course,dob,qual) values("+roll+",'"+name+"','"+fname+"','"+gen+"','"+add+"','"+phone+"','"+course+"','"+dob+"','"+qual+"')";
            Boolean a=Student.add_Student(SQL);
            ResultSet rs=null;
            String fees="";
            try{
                rs=Student.viewStudent("select * from course where c_name='"+course+"'");
                if(rs.next())
                    fees=rs.getString("c_fees");
            }catch(Exception e){
                System.out.println(e);
            }
           
            SQL="Insert into fees values("+roll+",'"+course+"','"+fees+"',' ','"+fees+"',' ',' ')";
            Boolean f=Student.add_Student(SQL);
            if(a && f){
                JOptionPane.showMessageDialog(container,"Registration Success!!");
                frm.dispose();
                new AddStudent();
            }
            else   
                JOptionPane.showMessageDialog(container,"Something Went Wrong!!!");
        }
        if(ev.getSource()==b2){
            clearRecord();
        }
        if(ev.getSource()==b3){
            frm.dispose();  
            isOpen=false;
        }
        
    
        SwingUtilities.updateComponentTreeUI(frm);

    }
    void clearRecord(){
            t1.setText("");
            t2.setText("");
            r1.setSelected(false);
            r2.setSelected(false);
            // btng.setSelected(btng.getSelection(), false);
            t3.setText("");
            t4.setText("");
            cb.setSelectedItem("Please Select");
            t5.setText("");
            c1.setSelected(false);
            c2.setSelected(false);
            c3.setSelected(false);
    }
 public static void main(String args[]){
        new AddStudent();
    }
   
} 