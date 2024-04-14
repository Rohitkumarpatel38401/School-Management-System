import lib.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
class ViewExam implements ActionListener{
    JFrame frm;
    JLabel title;
    JPanel table,p1,p2;
    JButton b1,b2,b3,b4,b5;
    JScrollPane sp;
    String SQL;
    ViewExam(){
        frm=new JFrame("View Exam's");
        frm.setVisible(true);
        frm.setLocationRelativeTo(null);
        frm.setExtendedState(frm.MAXIMIZED_BOTH);
        frm.setLayout(new BorderLayout(0,0));
        title=new JLabel("All Examination Records");
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

        SQL="Select * from exam order by e_id";
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
        rowhead.add(new JLabel("Exam Id"));
        rowhead.add(new JLabel("Exam Title"));
        rowhead.add(new JLabel("Exam Course Name"));
        rowhead.add(new JLabel("Exam Subject"));
        rowhead.add(new JLabel("Exam Timing"));
        rowhead.add(new JLabel("Exam Date"));
        rowhead.add(new JLabel("Exam Description"));
        
        
        table.add(rowhead);
        try{
        ResultSet r=Student.viewStudent(SQL);
          
            int c=0;
            while(r.next()){
                c++;
                JPanel row=new JPanel(new GridLayout(1,9,5,5));
                row.setPreferredSize(new Dimension(1000,25));
                row.add(new JLabel(""+r.getInt("e_id")));
                row.add(new JLabel(r.getString("e_title")));
                row.add(new JLabel(r.getString("e_course")));
                row.add(new JLabel(r.getString("e_subject")));
                row.add(new JLabel(r.getString("e_time")));
                row.add(new JLabel(r.getString("e_date")));
                row.add(new JLabel(r.getString("e_description")));
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
            String input=JOptionPane.showInputDialog(frm,"Enter Exam Id ");      
            SQL="select * from exam where e_id="+input;
            showDataTable(SQL);
        }
        if(e.getSource()==b2){
            new UpdateExam();
        }
        if(e.getSource()==b3){// delete record
            String r=JOptionPane.showInputDialog(frm,"Enter Exam Id to Delete:");
            int i=1;
            if(!r.equals("")) i=JOptionPane.showConfirmDialog(frm,"Are You Sure To Delete this Exam Id : "+r);
            if(i==0){
                String SQL="delete from exam where e_id="+r;
                Boolean a=Student.delete_Student(SQL);
                
              
                if(a){
                    JOptionPane.showMessageDialog(frm,"The Exam Id : "+r+" is Deleted Success !!");
                    frm.dispose();
                    new ViewExam();
                }
                else   
                    JOptionPane.showMessageDialog(frm,"Something Went Wrong!!!");
            }
        }
        if(e.getSource()==b4){
            if(JOptionPane.showConfirmDialog(frm,"Are you sure want to delete all record.?")==0){
                String SQL="delete from Exam";
                Boolean a=Student.delete_Student(SQL);
                if(a){
                    frm.dispose();
                    JOptionPane.showMessageDialog(frm,"All Record Deleted!!!");
                    new ViewExam();
                }else
                    JOptionPane.showMessageDialog(frm,"Something went Wrong!!");
            }
        }
        if(e.getSource()==b5){
            showDataTable("select * from exam order by e_id");
        }
    }
    public static void main(String args[]){
        new ViewExam();
    }

}