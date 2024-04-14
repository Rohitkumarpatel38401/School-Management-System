import lib.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JButton;
import java.sql.*;
import javax.swing.border.Border;

class ViewAdmitCard implements ActionListener{
    JFrame frm;
    JLabel title;
    JPanel table,p1,p2,container,searchBar;
    JTextField t1;
    JButton b1,b2,b3;
    JComboBox cb;
    JButton buttons[];
    int sid[];
    Boolean feesStatus[];
    JScrollPane sp;
    String SQL;
    static Boolean isOpen=false;

    ViewAdmitCard(){
        frm=new JFrame("Admit Cards");
        frm.setUndecorated(true);
        frm.setVisible(true);
        frm.setLocationRelativeTo(null);
        frm.setExtendedState(frm.MAXIMIZED_BOTH);
        frm.setLayout(new BorderLayout(0,0));
        title=new JLabel("Download Admit Cards");
        title.setFont(new Font("Monotype Corsiva",Font.BOLD,40));
        title.setForeground(Color.WHITE);
        container=new JPanel(new FlowLayout(FlowLayout.CENTER));
        container.setBackground(Color.WHITE);
        table=new JPanel();
        // table.setPreferredSize(new Dimension(1000,500));
        table.setBackground(Color.WHITE);
        Border blackline = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        table.setBorder(blackline);
        sp=new JScrollPane(table);
        p1=new JPanel();
        p2=new JPanel();
        searchBar=new JPanel(new GridLayout(1,2,3,3));
        searchBar.setPreferredSize(new Dimension(1000,35));
        searchBar.setBackground(Color.WHITE);
        t1=new JTextField(20);
        t1.setPreferredSize(new Dimension(100,28));
        b1=new JButton("Search");
        JPanel leftpanel=new JPanel(new GridLayout(1,2,5,5));
        JPanel rightPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT,3,3));
        //////////////////////

        cb=new JComboBox();
        cb.addItem("Please Select Course");
        try{
            ResultSet rs=Student.viewStudent("select * from course");
            while(rs.next()){
                cb.addItem(String.valueOf(rs.getString("C_Name")));
            }
        }catch(Exception e){
            System.out.println("cb1 : "+e);
        }




        /////////////////////
        leftpanel.add(cb);leftpanel.add(new JLabel(""));
        rightPanel.add(new JLabel("Enter Roll Number : "));rightPanel.add(t1);rightPanel.add(b1);
        searchBar.add(leftpanel);searchBar.add(rightPanel);
        container.add(searchBar);
        container.add(table);
        Color clr=new Color(255,20,150);
        p1.setBackground(clr);
        p2.setBackground(clr);

        b2=new JButton("Refresh");
        b2.setBackground(Color.CYAN);
        // b2.setForeground(Color.GRAY);

        b3=new JButton("Close");
        b3.setForeground(Color.RED);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        
        p2.add(b2);p2.add(b3);
        p1.add(title);
        frm.add(p1,BorderLayout.NORTH);
        frm.add(container,BorderLayout.CENTER);
        frm.add(p2,BorderLayout.SOUTH);

        SQL="Select * from students order by id";
        showDataTable(SQL);
        
        SwingUtilities.updateComponentTreeUI(frm);
    }
    void showDataTable(String SQL){
        table.removeAll();

        JPanel rowhead=new JPanel(new GridLayout(1,9,5,5));
        rowhead.setPreferredSize(new Dimension(1000,30));
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
        rowhead.add(new JLabel("Action"));
        
        table.add(rowhead);
        try{
            ResultSet r=Student.viewStudent(SQL);
            int c=0;
            int s=Student.countRow("students","id");
            buttons=new JButton[s];
            sid=new int[s];
            feesStatus=new Boolean[s];
            JPanel row;
            table.setPreferredSize(new Dimension(1002,(s+1)*33 ));
            while(r.next()){
                row=new JPanel(new GridLayout(1,9,5,5));
                row.setPreferredSize(new Dimension(1000,25));
                row.add(new JLabel(""+r.getInt("id")));
                row.add(new JLabel(r.getString("name")));
                row.add(new JLabel(r.getString("f_name")));
                row.add(new JLabel(r.getString("gender")));
                row.add(new JLabel(r.getString("address")));
                row.add(new JLabel(r.getString("phone")));
                row.add(new JLabel(r.getString("course")));
                row.add(new JLabel(r.getString("dob")));
                JButton tb=null;
                ResultSet rs=Student.viewStudent("Select * from fees where id="+r.getInt("id"));
                rs.next();
                if(Integer.parseInt(rs.getString("balance"))==0 ){
                    tb=new JButton("Admit Card");
                    tb.setBackground(Color.YELLOW);
                    feesStatus[c]=true;
                }else{
                    tb=new JButton("Pay Fees");
                    feesStatus[c]=false;
                }
                buttons[c]=tb;
        
                sid[c]=r.getInt("id");
                tb.addActionListener(this);
                row.add(tb);
                table.add(row);
                row.setBackground(Color.WHITE);
                Thread.sleep(100);
                SwingUtilities.updateComponentTreeUI(frm);
                c++;
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
        System.out.println("Clicked ");
        if(e.getSource()==b1){
            if(t1.getText().trim().length()==0){
                JOptionPane.showMessageDialog(frm,"Plz. Enter Roll Number to search");
            }
            SQL="select * from students where id="+Integer.parseInt(t1.getText().trim())+" and course='"+cb.getSelectedItem()+"' order by id";
            showDataTable(SQL);
        }
      
        if(e.getSource()==b2){
            showDataTable("select * from students where course='"+cb.getSelectedItem()+"' order by id");        
        }
        if(e.getSource()==b3){
            frm.dispose();
            isOpen=false;
        }
        for(int i=0;i<buttons.length;i++){
            if(e.getSource()==buttons[i]){
                // System.out.println(sid[i]);
                if(feesStatus[i]){
                   new AdmitCard(sid[i]);
                }else{
                    new FeesPay();
                }
            }
        }
    }

    public static void main(String args[]){
        new ViewAdmitCard();

    }

}
