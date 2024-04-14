import java.awt.*;
import javax.swing.*;
public class Splash{
    JFrame frm;
    JLabel image;
    JProgressBar pb;
    Splash(){
        frm=new JFrame();
        frm.setSize(600,360);
        frm.setLayout(null);
        frm.setLocationRelativeTo(null);
        frm.setUndecorated(true);
        frm.setVisible(true);
        frm.setResizable(false);
        image=new JLabel(new ImageIcon("images/splash-pic.png"));
        image.setBounds(0,0,600,360);

        pb=new JProgressBar(0,100);
        pb.setValue(0);
        pb.setForeground(Color.RED);
        pb.setBounds(0,345,600,15);
        frm.add(pb);
        pb.setStringPainted(true);

        frm.add(image);
        frm.setOpacity(1.0f);
        
        SwingUtilities.updateComponentTreeUI(frm);
                
        progressData();


    }
    public void progressData(){
        try{
            int i=0;
            while(i<=100){
                i=i+3;
                pb.setValue(i);
                Thread.sleep(80);
            }
        }catch(Exception e){
            System.out.println("Error: "+e);
        }
        frm.dispose();
    }
    // public static void main(String args[]){
    //     new Splash();
    // }

}
