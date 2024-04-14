import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import lib.*;
public class Home implements ActionListener{
    JFrame frm;
    JButton b1,b2,b3,b4,b5;
    JPanel header,container,footer,p1,p2,p3,p4;
    JLabel title,l1,l2,l3;
    JMenuBar mBar;
    JMenu student,fees,exam,admintCard,result,aboutUs;
    JMenuItem add_stu,view_stu,update_stu;
    JMenuItem pay_fees,view_fees;
    JMenuItem add_exam,view_exam,update_exam;
    JMenuItem view_admitCard;
    Home(){
        frm=new JFrame("Home Page");
        frm.setVisible(true);
        frm.setLocationRelativeTo(null);
        frm.setResizable(false);
        frm.setExtendedState(frm.MAXIMIZED_BOTH);
        frm.getContentPane().setBackground(Color.WHITE);
        frm.setLayout(new BorderLayout(0,0));

        mBar=new JMenuBar();

        student=new JMenu("Student");
        fees=new JMenu("Fees");
        exam=new JMenu("Exam");
        admintCard=new JMenu("AdmitCard");
        result=new JMenu("Result");
        aboutUs=new JMenu("About Us");

        mBar.add(student);
        mBar.add(fees);
        mBar.add(exam);
        mBar.add(admintCard);
        mBar.add(result);
        mBar.add(aboutUs);
        frm.setJMenuBar(mBar);  

        add_stu=new JMenuItem("Add Student");
        view_stu=new JMenuItem("View Student");
        update_stu=new JMenuItem("Update Student");
        student.add(add_stu); student.add(view_stu); student.add(update_stu);   

        pay_fees=new JMenuItem("Pay Fees");
        view_fees=new JMenuItem("View");
        fees.add(pay_fees);fees.add(view_fees);

        add_exam=new JMenuItem("Add Exam");
        view_exam=new JMenuItem("View Exam");
        update_exam=new JMenuItem("Update Exam");
        exam.add(add_exam); exam.add(view_exam);  exam.add(update_exam);      

        view_admitCard=new JMenuItem("Download Admit Card");
        admintCard.add(view_admitCard);

        header=new JPanel();
        container=new JPanel(new FlowLayout());
        footer=new JPanel();

        Color c=new Color(255,20,150);
        header.setBackground(c);
        container.setBackground(Color.WHITE);
        footer.setBackground(c);

        frm.add(header,BorderLayout.NORTH);
        frm.add(container,BorderLayout.CENTER);
        frm.add(footer,BorderLayout.SOUTH);

        title=new JLabel("School Management System");
        title.setFont(new Font("Arial",Font.BOLD,40));
        title.setForeground(Color.WHITE);

        JLabel ft=new JLabel("@Copyright 2024");
        ft.setFont(new Font("Arial",Font.PLAIN,17));
        ft.setForeground(Color.WHITE);
        header.add(title);
        footer.add(ft);
        JLabel bannerImg=new JLabel(new ImageIcon("images/banner.jpg"));
        container.add(bannerImg);
        
        add_stu.addActionListener(this);
        view_stu.addActionListener(this);
        update_stu.addActionListener(this);
        pay_fees.addActionListener(this);
        view_fees.addActionListener(this);
        add_exam.addActionListener(this);
        view_exam.addActionListener(this);
        update_exam.addActionListener(this);

        view_admitCard.addActionListener(this);
        

        SwingUtilities.updateComponentTreeUI(frm);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    public void actionPerformed(ActionEvent ae){
        // System.out.println("Click!!");
        if(ae.getSource()==add_stu){
            if(AddStudent.isOpen==false){
            new AddStudent();
            }else{
                AddStudent.frm.setVisible(true);
            }
        }
        if(ae.getSource()==view_stu){
            if(ViewStudent.isOpen==false){
                new ViewStudent();
            }else{
                ViewStudent.frm.setVisible(true);
            }
        }
        if(ae.getSource()==update_stu){
            new UpdateStudent();
        }
        if(ae.getSource()==pay_fees){
            new FeesPay();
        }
        if(ae.getSource()==view_fees){
            new ViewFees();
        }
        if(ae.getSource()==add_exam){
            new AddExam();
        }
        if(ae.getSource()==view_exam){
            new ViewExam();
        }
        if(ae.getSource()==update_exam){
            new UpdateExam();
        }
        if(ae.getSource()==view_admitCard){
            new ViewAdmitCard();
        }
    }
    public static void main(String args[]){
        new Home();
    }
   
}