import lib.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
class ViewStudent implements ActionListener{
    static JFrame frm;
    static Boolean isOpen=false;
    JLabel title;
    JPanel table,p1,p2;
    JButton b1,b2,b3,b4,b5;
    JScrollPane sp;
    String SQL;
    ViewStudent(){
        isOpen=true;
        frm=new JFrame("View Students");
        frm.setVisible(true);
        frm.setLocationRelativeTo(null);
        frm.setExtendedState(frm.MAXIMIZED_BOTH);
        frm.setLayout(new BorderLayout(0,0));
        title=new JLabel("All Student Records");
        title.setFont(new Font("Arial",Font.BOLD,30));
        title.setForeground(Color.WHITE);
        table=new JPanel();
        table.setLayout(new FlowLayout(FlowLayout.CENTER,0,1));
        sp=new JScrollPane(table);
        p1=new JPanel();
        p2=new JPanel();
        Color clr=new Color(255,20,150);
        p1.setBackground(clr);
        p2.setBackground(clr);
        b1=new JButton("Search");
        b2=new JButton("Update");
        b3=new JButton("Delete");
        b4=new JButton("Delete All");
        b5=new JButton("Reload");
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        p2.add(b1); p2.add(b2); p2.add(b3); p2.add(b4);p2.add(b5);
        p1.add(title);
        frm.add(p1,BorderLayout.NORTH);
        frm.add(table,BorderLayout.CENTER);
        frm.add(p2,BorderLayout.SOUTH);

        SQL="Select * from students order by id";
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
        rowhead.add(new JLabel("Roll No"));
        rowhead.add(new JLabel("Name"));
        rowhead.add(new JLabel("Father Name"));
        rowhead.add(new JLabel("Gender"));
        rowhead.add(new JLabel("Address"));
        rowhead.add(new JLabel("Phone"));
        rowhead.add(new JLabel("Course"));
        rowhead.add(new JLabel("Date Of Birth"));
        rowhead.add(new JLabel("Qualifications"));
        
        table.add(rowhead);
        try{
        ResultSet r=Student.viewStudent(SQL);
          
            int c=0;
            while(r.next()){
                c++;
                JPanel row=new JPanel(new GridLayout(1,9,5,5));
                row.setPreferredSize(new Dimension(1000,25));
                row.add(new JLabel(""+r.getInt("id")));
                row.add(new JLabel(r.getString("name")));
                row.add(new JLabel(r.getString("f_name")));
                row.add(new JLabel(r.getString("gender")));
                row.add(new JLabel(r.getString("address")));
                row.add(new JLabel(r.getString("phone")));
                row.add(new JLabel(r.getString("course")));
                row.add(new JLabel(r.getString("dob")));
                row.add(new JLabel(r.getString("qual")));
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
        if(e.getSource()==b1){
            String input=JOptionPane.showInputDialog(frm,"Enter Roll Number ");      
            SQL="select * from students where id="+input;
            showDataTable(SQL);
        }
        if(e.getSource()==b2){
            new UpdateStudent();
        }
        if(e.getSource()==b3){// delete record
            String r=JOptionPane.showInputDialog(frm,"Enter Roll No To Delete:");
            int i=1;
            if(!r.equals("")) i=JOptionPane.showConfirmDialog(frm,"Are You Sure To Delete this Record Roll: "+r);
            if(i==0){
                String SQL="delete from students where id="+r;
                Boolean a=Student.delete_Student(SQL);
                SQL="delete from fees where id="+r;
                Boolean f=Student.delete_Student(SQL);
                if(a && f){
                    JOptionPane.showMessageDialog(frm,"The Record of Roll No : "+r+" is Deleted Successfully!!");
                    frm.dispose();
                    isOpen=false;
                    new ViewStudent();
                }
                else   
                    JOptionPane.showMessageDialog(frm,"Something Went Wrong!!!");
            }
        }
        if(e.getSource()==b4){
            if(JOptionPane.showConfirmDialog(frm,"Are you sure want to delete all Student record.?")==0){
                String SQL="delete from students";
                Boolean a=Student.delete_Student(SQL);
                if(a){
                    frm.dispose();
                    isOpen=false;
                    JOptionPane.showMessageDialog(frm,"All Students Record Deleted!!!");
                    new ViewStudent();
                }else
                    JOptionPane.showMessageDialog(frm,"Something went Wrong!!");
            }
        }
        if(e.getSource()==b5){
            showDataTable("select * from students order by id");
        }
    }
    public static void main(String args[]){
        new ViewStudent();
    }

}