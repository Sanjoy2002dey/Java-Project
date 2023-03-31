package Chatting_Aplication;

import javax.swing.*;                              // for making frame//
import javax.swing.border.*;
import java.awt.*;                                //for color package//
import java.awt.event.*;                           // for ActionlLstener function//
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;


public class Server implements ActionListener {                      //this function is used for mouse clicked//
	
	JTextField text;
	JPanel a1;
	static Box vertical = Box.createVerticalBox();
	static JFrame f=new JFrame();
	static DataOutputStream dout;
	
	Server(){
		f.setLayout(null);                        //Panel-1//       // Frame coding//
		
		JPanel p1=new JPanel();
		p1.setBackground(new Color(7,94,84));
		p1.setBounds(0,0,450,70);                     //{image add on the frame// 
		p1.setLayout(null);
		f.add(p1);
		
		ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/3 (1).png"));
		Image i2 =i1.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT);   //to make the image small//
		ImageIcon i3= new ImageIcon(i2);
		JLabel back = new JLabel(i3);
		back.setBounds(5, 20, 25,25);
		p1.add(back);                            // arrow add on the panel//
		
		back.addMouseListener(new MouseAdapter() {
			                                               // action to click arrow//
			public void mouseClicked(MouseEvent ae) {
				System.exit(0);          //frame will close//
			}
		});
		
		ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons/gaitonde.jpeg"));
		Image i5 =i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon i6= new ImageIcon(i5);
		JLabel profile = new JLabel(i6);
		profile.setBounds(40, 10, 50,50);
		p1.add(profile);
		
		
		ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
		Image i8 =i7.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
		ImageIcon i9= new ImageIcon(i8);
		JLabel video = new JLabel(i9);
		video.setBounds(300, 25 , 20,20);
		p1.add(video);
		
		
		ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("icons/phone (1).png"));
		Image i11 =i10.getImage().getScaledInstance(18, 20, Image.SCALE_DEFAULT);
		ImageIcon i12= new ImageIcon(i11);
		JLabel phone = new JLabel(i12);
		phone.setBounds(350, 25 , 18,20);
		p1.add(phone);
		
		
		ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("icons/3icon (1).png"));
		Image i14 =i13.getImage().getScaledInstance(7, 20, Image.SCALE_DEFAULT);
		ImageIcon i15= new ImageIcon(i14);
		JLabel threedot = new JLabel(i15);
		threedot.setBounds(400, 25 , 7,20);
		p1.add(threedot);
		
		
		JLabel name=new JLabel("Player 1");
		name.setBounds(110,15,120,30);
		name.setForeground(Color.WHITE);
		name.setFont(new Font("Calibri", Font.BOLD, 20));
		p1.add(name);
		
		
		JLabel status=new JLabel("Active Now");
		status.setBounds(110,35,120,30);
		status.setForeground(Color.WHITE);
		status.setFont(new Font("Arial", Font.BOLD, 12));
		p1.add(status);
		
		
		//Panel-2//
		a1=new JPanel();
		a1.setBounds(5, 75, 440,470);
		f.add(a1);
		
		
		text= new JTextField();                         //create TEXT BOX//
		text.setBounds(5,554,310,40);
		text.setFont(new Font("Calibri",Font.PLAIN,16));            //font size setting//
		f.add(text);
		   
		
		JButton send = new JButton("Send");                        //send button position//
		send.setBounds(325,555,110,38);
		send.setBackground(new Color(7,94,84));                    // set color of button//
		send.setForeground(Color.WHITE);                    //set color of the inner send font //
		send.addActionListener(this);                     //click add against send button//
		send.setFont(new Font("Calibri",Font.PLAIN,16));     //set the size of the inner send font//
		f.add(send);
		
		
		
		f.setSize(450,600);
		f.setLocation(200,50);
		f.setUndecorated(true);                              //details of output frame// 
		f.getContentPane().setBackground(Color.WHITE);
		f.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae) {
		try {
			String out=text.getText();
    
			JPanel p2= formatLabel(out);
     
			a1.setLayout(new BorderLayout());                        // to set the text in right side //
    
			JPanel right = new JPanel(new BorderLayout());
			right.add(p2,BorderLayout.LINE_END);                 // TEXT alloted in vertically//
			vertical.add(right);
			vertical.add(Box.createVerticalStrut(15));
    
			a1.add(vertical,BorderLayout.PAGE_START);
    
			dout.writeUTF(out);
    
			text.setText("");                        //automatically empty the text box after send mess//
    
			f.repaint();
			f.invalidate();									//for refresh the text in vertically//
			f.validate();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static JPanel formatLabel(String out) {
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel output =new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
		
		output.setFont(new Font("Arial",Font.PLAIN,18));
		
		output.setBackground(Color.CYAN);          // background color//
		output.setOpaque(true);						//setOpaque is used for displaying//
		output.setBorder(new EmptyBorder(15,15,15,50));
		
		panel.add(output);
		
		Calendar cal=Calendar.getInstance();               // for date ,time  //
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
		
		JLabel time = new JLabel();
		time.setText(sdf.format(cal.getTime()));
		
		panel.add(time);
		return panel;
	}
	
	
	
	public static void main(String[] args) {
		new Server();
	
	
		try {
			ServerSocket skt=new ServerSocket(6001);
			while(true) {
				Socket s=skt.accept();
				DataInputStream din=new DataInputStream(s.getInputStream());
				dout= new DataOutputStream(s.getOutputStream());
				
				while(true) {
					String msg= din.readUTF();
					JPanel panel=formatLabel(msg);
					
					JPanel left=new JPanel(new BorderLayout());
					left.add(panel,BorderLayout.LINE_START);
					vertical.add(left);
					f.validate();
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
