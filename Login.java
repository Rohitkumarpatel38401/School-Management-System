import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import lib.*;
import java.sql.*;
public class Login implements ActionListener{
    
    JFrame frm;
    JLabel title,l1,l2,l3,captcha;
    JTextField t1,t3;
    JPasswordField t2;
    JButton b1,b2,b3,b4,b5;
    JPanel container,p1,p2,p3,p4;
    Login(){
        new Splash();
        frm=new JFrame("Login");
        frm.setUndecorated(true);

        frm.setSize(385,350);
        frm.setVisible(true);
        frm.setLocationRelativeTo(null);
        frm.setLayout(new BorderLayout());
        container=new JPanel();
        container.setLayout(null);
        container.setBackground(Color.WHITE);
        frm.add(container);
        

        title=new JLabel(new ImageIcon("images/signin-icon.JPG"));
        title.setBounds(155,15,80,80);
        container.add(title);
        p1=new JPanel(new GridLayout(3,2,0,20));
        p1.setBounds(25,115,335,150);
        p1.setBackground(Color.WHITE);
        container.add(p1);
        
        t1=new JTextField(20);
        t2=new JPasswordField(20);
        t3=new JTextField(10);
        l1=new JLabel("Username : ");
        l2=new JLabel("Password : ");
        l3=new JLabel("Captcha : ");
        
        Font f=new Font("Arial",Font.PLAIN,15);
        l1.setFont(f);l2.setFont(f);l3.setFont(f);
        b1=new JButton(new ImageIcon("images/refresh-icon.png"));
        b1.setPreferredSize(new Dimension(26,25));
        p1.add(l1);  p1.add(t1);
        p1.add(l2);  p1.add(t2);
        
        captcha=new JLabel("09234");
        captcha.setFont(new Font("Arial",Font.ITALIC,25));
        captcha.setPreferredSize(new Dimension(80,34));
        captcha.setForeground(Color.PINK);
        p2=new JPanel(new FlowLayout(FlowLayout.CENTER,10,0));
        p2.setBackground(Color.WHITE);
        p2.add(l3);p2.add(captcha);
        p1.add(p2);

        p3=new JPanel(new FlowLayout(FlowLayout.RIGHT,13,0));
        p3.setBackground(Color.WHITE);
        t3.setPreferredSize(new Dimension(80,34));
      
        p3.add(b1); p3.add(t3);
        p1.add(p3);

        p4=new JPanel(new FlowLayout(FlowLayout.CENTER,6,2));
        p4.setBackground(Color.WHITE);
        p4.setBounds(25,285,350,30);
        b2=new JButton("Login");
      
        b3=new JButton("Close");
        b4=new JButton("SignUp");
        b5=new JButton("Forgot");
        p4.add(b2);p4.add(b4);p4.add(b5);p4.add(b3);
        b2.setForeground(Color.WHITE);
        b2.setBackground(Color.DARK_GRAY);
        b3.setForeground(Color.WHITE);
        b3.setBackground(Color.RED);
        

        container.add(p4);
     

        setCaptcha();
        SwingUtilities.updateComponentTreeUI(frm);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
       
    }
    public void setCaptcha(){
        Random rand = new Random();
        int rand_int1 = rand.nextInt(99999);
        captcha.setText(""+rand_int1);
    }
    public void actionPerformed(ActionEvent ae){
        System.out.println("Clicked");
        if(ae.getSource()==b1){
             setCaptcha();
        }
        if(ae.getSource()==b2){
            if(t3.getText().equals("")){
                JOptionPane.showMessageDialog(frm,"Plz. Enter Captcha !!");
            }
            else if(!captcha.getText().equals(t3.getText())){
                JOptionPane.showMessageDialog(frm,"Invalid Captcha !!");
            }else{
                String SQL="select * from login where uname='"+t1.getText()+"' and pass='"+t2.getText()+"'";
                System.out.println(SQL);
                if(DBConnect.isExist(SQL)){
                    frm.dispose();
                    new Home();
                }else{
                    JOptionPane.showMessageDialog(frm,"Incorrect Username or Password!!");
                   
                }
            }
        }
        if(ae.getSource()==b3){
            frm.dispose();
        }
    }
    public static void main(String args[]){
        
        new Login();
    }
}



