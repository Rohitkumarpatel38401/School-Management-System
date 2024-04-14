import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import lib.*;
import java.sql.*;
public class UpdateStudent implements ActionListener,ItemListener{
    static JFrame frm;
    static Boolean isOpen=false;

    JLabel title,l1,l2,l3,l4,l5,l6,l7,l8;
    JPanel p1,p2,p3,p4,p5,container,table;
    JButton b1,b2;
    JTextField t1,t2,t3,t4,t5;
    JRadioButton r1,r2;
    ButtonGroup btng;
    JComboBox cb,cb1;
    JCheckBox c1,c2,c3;
    String roll;
    UpdateStudent(){
        isOpen=true;
        frm=new JFrame("Update Student");
        frm.setUndecorated(true);
        frm.setSize(500,470);
        frm.setVisible(true);
        frm.setLocationRelativeTo(null);
        frm.setLayout(new BorderLayout());
        p1=new JPanel();
        p2=new JPanel();
        p3=new JPanel();
        title=new JLabel("Update Student");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Monotype Corsiva",Font.ITALIC,38));

        btng=new ButtonGroup();
        r1=new JRadioButton("Male");
        r2=new JRadioButton("Female"); 
        btng.add(r1);btng.add(r2);
        p4=new JPanel();
        p4.setBackground(Color.WHITE);
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

        c1=new JCheckBox("10th");
        c2=new JCheckBox("12th");
        c3=new JCheckBox("Graduate");
        p5=new JPanel();
        p5.setBackground(Color.WHITE);
        p5.add(c1); p5.add(c2); p5.add(c3);

        t1=new JTextField(20);
        t2=new JTextField(20);
        t3=new JTextField(20);
        t4=new JTextField(20);
        t5=new JTextField(20);

        b1=new JButton("Update");
        b1.setBackground(Color.BLUE);
        b1.setForeground(Color.WHITE);
    
        b2=new JButton("Close");
        b2.setForeground(Color.WHITE);
        b2.setBackground(Color.RED);

        p2.setPreferredSize(new Dimension(400,350));
        container=new JPanel(new GridLayout(10,2,10,10));
        container.setPreferredSize(new Dimension(400,400));
        container.setBackground(Color.WHITE);
        container.add(new JLabel("Select Roll Number : "));container.add(cb1);
        container.add(new JLabel("Student Name : ")); container.add(t1);
        container.add(new JLabel("Fathers's Name :")); container.add(t2);
        container.add(new JLabel("Select Gender : ")); container.add(p4);
        container.add(new JLabel("Enter Address : ")); container.add(t3);
        container.add(new JLabel("Phone :")); container.add(t4);
        container.add(new JLabel("Select Course: ")); container.add(cb);
        container.add(new JLabel("Date Of Birth")); container.add(t5);
        container.add(new JLabel("Select Qualification : ")); container.add(p5);

        Color c=new Color(255,20,150);
        p1.setBackground(c);
        p2.setBackground(Color.WHITE);
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
        cb1.addItemListener(this);

        SwingUtilities.updateComponentTreeUI(frm);
    }

    public void itemStateChanged(ItemEvent ie){ 
        if(ie.getSource()==cb1){
            try{
                if(cb1.getSelectedItem()!="Select Roll No"){
                roll=String.valueOf(cb1.getSelectedItem());
                String SQL="Select * from students where id="+roll;
                ResultSet rs=Student.viewStudent(SQL);
                if(rs.next()){
                    t1.setText(rs.getString("name"));
                    t2.setText(rs.getString("f_name"));
                    String gen=rs.getString("gender");
                    if(gen.equals("Male")) r1.setSelected(true);
                    if(gen.equals("Female")) r2.setSelected(true);
                    t3.setText(rs.getString("address"));
                    t4.setText(rs.getString("phone"));
                    String course=rs.getString("Course");
                    cb.setSelectedItem(course);
                    t5.setText(rs.getString("DOB"));
                    String[] arr=rs.getString("qual").split(",");
                    for(int i=0;i<arr.length;i++){
                        if(arr[i].trim().equals("10th")) c1.setSelected(true);
                        if(arr[i].trim().equals("12th")) c2.setSelected(true);
                        if(arr[i].trim().equals("Graduate")) c3.setSelected(true);
                    }
                }
            }
            }catch(Exception ex){
                System.out.println(ex);
            }
        
        }else{
            JOptionPane.showMessageDialog(frm,"Plz select Roll No"); 
        }
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
  
        if(ev.getSource()==b1){//Update
            if(cb1.getSelectedItem()!="Select Roll No"){
                if(JOptionPane.showConfirmDialog(frm,"Are sure want to Update ?")==0){
                    SQL="Update students set name='"+name+"',F_name='"+fname+"',gender='"+gen+"',address='"+add+"',phone='"+phone+"',course='"+course+"',dob='"+dob+"',qual='"+qual+"' where id="+roll;
                    Boolean a=Student.delete_Student(SQL);
                    if(a)
                        JOptionPane.showMessageDialog(frm,"Record Updated Successfully!!");
                    else   
                        JOptionPane.showMessageDialog(frm,"Something went wrong!!");
                }
            }else{
                JOptionPane.showMessageDialog(frm,"Please Select Roll Number!!");
            }
        }
        if(ev.getSource()==b2){
            frm.dispose();
            isOpen=false;
        }
        SwingUtilities.updateComponentTreeUI(frm);
    }
 
} 