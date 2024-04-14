import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import lib.*;
import java.sql.*;
public class UpdateExam implements ActionListener, ItemListener{
    JFrame frm;
    JLabel title;
    JPanel p1,p2,p3,p4,container,table;
    JButton b1,b2,b3;
    JTextField t1,t2,t3;
    JTextArea tr;
    JRadioButton r1,r2;
    ButtonGroup btng;
    JComboBox cb1,cb2,cb3;
    int roll=0;
    UpdateExam(){
        frm=new JFrame("Update Exam");
        frm.setSize(550,470);
        frm.setUndecorated(true);
        frm.setVisible(true);
        frm.setLocationRelativeTo(null);
        frm.setLayout(new BorderLayout(0,10));
        
        p1=new JPanel();
        p2=new JPanel();
        p3=new JPanel();
        title=new JLabel("Update Exam");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Monotype Corsiva",Font.ITALIC,38));
        
        btng=new ButtonGroup();
        r1=new JRadioButton("Mon(10AM-1PM)");
        r2=new JRadioButton("Ev(2PM-5PM)"); 
        r1.setFont(new Font("arial",Font.BOLD,12));
        r2.setFont(new Font("arial",Font.BOLD,12));
        btng.add(r1);btng.add(r2);
        p4=new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        p4.add(r1);p4.add(r2);
        
        cb1=new JComboBox();
        cb1.addItem("Please Select");
     
        cb2=new JComboBox();
        cb2.addItem("Please Select");
        try{
            ResultSet rs=Student.viewStudent("select e_id from exam");
            while(rs.next()){
                cb1.addItem(String.valueOf(rs.getInt("e_id")));
            }
            rs=Student.viewStudent("select * from course");
            while(rs.next()){
                cb2.addItem(String.valueOf(rs.getString("C_Name")));
            }
        }catch(Exception e){
            System.out.println("cb2 : "+e);
        }
        cb3=new JComboBox();
        cb3.addItem("Please Select");
         
        t1=new JTextField(20);
        t2=new JTextField(20);
        t3=new JTextField(20);
        tr=new JTextArea(2,10);
    
    

        b1=new JButton("Update");
        b1.setBackground(Color.BLUE);
        b1.setForeground(Color.WHITE);
        b2=new JButton("Reset");
        b3=new JButton("Close");
        b3.setForeground(Color.RED);

        int e_id=Student.getMaxValue("exam","e_id");
        if(e_id==0 ) e_id=1; //initial Exam Id
        else e_id++;
        t1=new JTextField(String.valueOf(e_id));
        t1.setFont(new Font("arial",Font.PLAIN,18));
        t1.setForeground(Color.BLUE);
        t1.setEditable(false);

        p2.setPreferredSize(new Dimension(450,350));
        container=new JPanel(new GridLayout(7,2,0,10));
        container.setPreferredSize(new Dimension(450,350));
        container.add(new JLabel("Select Exam ID : "));container.add(cb1);
        container.add(new JLabel("Exam Title : ")); container.add(t2);
        container.add(new JLabel("Exam Course : ")); container.add(cb2);
        container.add(new JLabel("Exam Subject : ")); container.add(cb3);
        container.add(new JLabel("Exam Timing : ")); container.add(p4);
        container.add(new JLabel("Exam Date : ")); container.add(t3);
        container.add(new JLabel("Exam Description : ")); container.add(tr);
       

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

        cb1.addItemListener(this);
        cb2.addItemListener(this);
    

        SwingUtilities.updateComponentTreeUI(frm);
    }
    public void itemStateChanged(ItemEvent ie){
        System.out.println("Changed");
        if(ie.getSource()==cb1){
            String id=String.valueOf(cb1.getSelectedItem());
            if( id!="Please Select" ){
                String e_id=String.valueOf(cb1.getSelectedItem());
               try{
                ResultSet rs=Student.viewStudent("select * from exam where e_id="+e_id);
                if(rs.next()){
                    t2.setText(rs.getString("e_title"));
                    cb2.setSelectedItem(rs.getString("e_course"));
                    cb3.setSelectedItem(rs.getString("e_subject"));
                    if(rs.getString("e_time").equals("10AM to 1PM")) r1.setSelected(true);
                    if(rs.getString("e_time").equals("2PM to 5PM")) r2.setSelected(true);
                    t3.setText(rs.getString("e_date"));
                    tr.setText(rs.getString("e_description"));
                }
               }catch(Exception e){
                   System.out.println("Error : "+e);
               }
            }
        }
        if(ie.getSource()==cb2){
            String course=String.valueOf(cb2.getSelectedItem());
            if( course!="Please Select" ){
                cb3.removeAllItems();
                cb3.addItem("Please Select");
                try{
                ResultSet rs=Student.viewStudent("select * from subjects where sub_c_name='"+course+"'");
                while(rs.next()){
                    cb3.addItem(String.valueOf(rs.getString("sub_name")));
            }
        }catch(Exception e){
            System.out.println("cb3 error : "+e);
        }
            }
        }
    }
    public void actionPerformed(ActionEvent ev){
        // System.out.println("Clicked!!");
        String eid,et,ecourse,esubject,timing,edate,edesc;
        String SQL="";
        eid=t1.getText();
        et=t2.getText();
        ecourse=String.valueOf(cb2.getSelectedItem());
        esubject=String.valueOf(cb3.getSelectedItem());        
        timing="";
        if(r1.isSelected()) timing="10AM to 1PM";
        if(r2.isSelected()) timing="2PM to 5PM";
        edate=t3.getText();
        edesc=tr.getText();

        String id=String.valueOf(cb1.getSelectedItem());

        if(ev.getSource()==b1){//Update Code 
            if(id=="Please Select"){
                JOptionPane.showMessageDialog(frm,"Please Select Exam Id!!!");
                return;
            }
            if(et.trim().length()==0 || ecourse=="Please Select" || esubject=="Please Select" || timing.length()==0 || edate.length()==0 || edesc.length()==0){
                JOptionPane.showMessageDialog(frm,"Please Fill all Details");
            }else{
                if(JOptionPane.showConfirmDialog(frm,"Are Sure want to update ?")==0){
                    SQL="update exam set e_title='"+et+"', e_course='"+ecourse+"', e_subject='"+esubject+"', e_time='"+timing+"', e_date='"+edate+"', e_description='"+edesc+"' where e_id="+id;
                    Boolean f=Student.add_Student(SQL);
                    if(f){
                        JOptionPane.showMessageDialog(frm,"Exam Updated Success !!!");
                        // frm.dispose();
                    }else{
                        JOptionPane.showMessageDialog(frm,"Something went wrong!!!");
                    }   
                } 
            }
        }
        if(ev.getSource()==b2){
            clearRecord();
        }
        if(ev.getSource()==b3){
            frm.dispose();
        }
        SwingUtilities.updateComponentTreeUI(frm);
    }
    void clearRecord(){
            
            t2.setText("");
            r1.setSelected(false);
            r2.setSelected(false);
            t3.setText("");
            tr.setText("");
            cb1.setSelectedItem("Please Select");
            cb2.setSelectedItem("Please Select");
            cb3.setSelectedItem("Please Select");
    }
   public static void main(String args[]){
       new UpdateExam();
   }
} 