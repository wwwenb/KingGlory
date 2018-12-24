package com.King;


import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
//import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Wenbiao Tan
 */
public class KingGloryBattle extends JPanel implements ActionListener, KeyListener{


	public static final int WIDTH = 1000+ 50; // 面板宽
	public static final int HEIGHT = 764; // 面板高
	public static final int Maxx= 15;
	public static final int herroSize= 80;
	
	public static BufferedImage background;
	public static BufferedImage people1;
	public static BufferedImage people2;
	public static BufferedImage people3;
	public static BufferedImage people4;
	public static BufferedImage people5;
	public static BufferedImage people6;
	public static BufferedImage bulletImage;
	public static BufferedImage victory;
	public static Timer timer;
	public static Timer timer1;
	public static int index;
	public static int position[][];
	public static Bullet herroBullet[];
	public static String status;
	public static int herroSum;
	//英雄
	public static Assassin assassinA;
	public static Assassin assassinB;
	public static  Fighter fighterA;
	public static  Fighter fighterB;	
	public static  Robbi robbiA;
	public static  Robbi robbiB;
	public static int herroAnum;
	public static int herroBnum;
	public static ServerSocket server;
	public static Socket client;
	public static String operate;
	public static String send;
	static {
		try {
			server= new ServerSocket(8888);
			
			background= ImageIO.read(KingGloryBattle.class.getResource("map.jpg"));
			people1= ImageIO.read(KingGloryBattle.class.getResource("people-LuBan.png"));
			people2= ImageIO.read(KingGloryBattle.class.getResource("people-HouYi.png"));
			people3= ImageIO.read(KingGloryBattle.class.getResource("people-HouZi.png"));
			people4= ImageIO.read(KingGloryBattle.class.getResource("people-YanJian.png"));
			people5= ImageIO.read(KingGloryBattle.class.getResource("people-BianQue.png"));
			people6= ImageIO.read(KingGloryBattle.class.getResource("people-ZhuGe.png"));
			victory= ImageIO.read(KingGloryBattle.class.getResource("victory.png"));
			
			assassinA= new Assassin(600, 55);
			assassinB= new Assassin(700, 50);
			fighterA= new Fighter(50, 550);
			fighterB= new Fighter(800, 80);	
			robbiA= new Robbi(120, 600);
			robbiB= new Robbi(900, 50);
			
			
			assassinA.setImage(people1);
			assassinB.setImage(people2);
			fighterA.setImage(people3);
			fighterB.setImage(people4);
			robbiA.setImage(people5);
			robbiB.setImage(people6);
			
			position= new int[Maxx][Maxx];
			herroBullet= new Bullet[Maxx];
			status= "END";
			herroSum= 2;
			herroAnum= 5;
            herroBnum= 5;
			
			for(int i= 0; i< Maxx; i++)
				herroBullet[i]= new Bullet();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 将图片绘画上窗体
	 */
	public void paint(Graphics g) {
		
		g.drawImage(background, 0, 0, null); // 画背景图
		paintHerro(g);
		paintBullet(g);
		paintVictory(g);
	}
	
	/**
	 * 绘制胜利的图形
	 * @param g  传入绘制的画板
	 */
	public void paintVictory(Graphics g) {
		if(herroAnum== 0||herroBnum== 0) {
			g.drawImage(victory, 0, 0, null);
			status= "END";
		}
			 
	}
	/**
	 * 绘画炸弹
	 * @param g 同一张图片
	 */
	public void paintBullet(Graphics g) {
		for(int i= 0; i< Maxx; i++) {
			if(herroBullet[i].getStillAlive()== 1)
				g.drawImage(herroBullet[i].getImage(), herroBullet[i].getX(), herroBullet[i].getY(), null);
			if(herroBullet[i].timeToBoom== 1) {
				g.drawImage(herroBullet[i].getBoom(), herroBullet[i].getX(), herroBullet[i].getY(), null);
				herroBullet[i].timeToBoom= 0;
			}
		}
		
	}

	/**
	 * 绘画英雄
	 * @param g 同一张图片
	 */
	public void paintHerro(Graphics g) {
		if(assassinA.getIsAlive()== 1)
			g.drawImage(assassinA.getImage(), assassinA.getX(), assassinA.getY(), null);
		if(assassinB.getIsAlive()== 1)
			g.drawImage(assassinB.getImage(), assassinB.getX(), assassinB.getY(), null);
		if(fighterA.getIsAlive()== 1)
			g.drawImage(fighterA.getImage(), fighterA.getX(), fighterA.getY(), null);
		if(fighterB.getIsAlive()== 1)
			g.drawImage(fighterB.getImage(), fighterB.getX(), fighterB.getY(), null);
		if(robbiA.getIsAlive()== 1)
			g.drawImage(robbiA.getImage(), robbiA.getX(), robbiA.getY(), null);
		if(robbiB.getIsAlive()== 1)
			g.drawImage(robbiB.getImage(), robbiB.getX(), robbiB.getY(), null);
	}
	
	
	
//-----------------------------------------------------------------------	
	JFrame frame = new JFrame("王者荣耀");
	JButton button0= new JButton("鲁班");
	JButton button1= new JButton("后裔");
	JButton button2= new JButton("猴子");
	JButton button3= new JButton("杨戬");
	JButton button4= new JButton("扁鹊");
	JButton button5= new JButton("诸葛亮");
	JButton button6= new JButton("Herro6");
	JButton button7= new JButton("Herro7");
	JButton button8= new JButton("Herro8");
	JButton button9= new JButton("Herro9");
	JButton button10= new JButton("START");
	JPanel panel= new JPanel();

    /**
     * 键盘按下
     */
	//KeyAdapter key= new KeyAdapter() {
    public  void keyPressed(KeyEvent e) {
	   if(e.getKeyCode()== KeyEvent.VK_0)
	        index= 0;
	   else if(e.getKeyCode()== KeyEvent.VK_1) {
		   index= 1;
	   }
	   else if(e.getKeyCode()== KeyEvent.VK_2) {
		   index= 2;
	   }
	   else if(e.getKeyCode()== KeyEvent.VK_3) {
		   index= 3;
	   }
	   else if(e.getKeyCode()== KeyEvent.VK_4) {
		   index= 4;
	   }
	   else if(e.getKeyCode()== KeyEvent.VK_5) {
		   index= 5;
	   }
	   else if(e.getKeyCode()== KeyEvent.VK_6) {
		   index= 6;
	   }
	   else if(e.getKeyCode()== KeyEvent.VK_7) {
		   index= 7;
	   }
	   else if(e.getKeyCode()== KeyEvent.VK_8) {
		   index= 8;
	   }
	   else if(e.getKeyCode()== KeyEvent.VK_9) {
		   index= 9;
	   }
	   else if(e.getKeyCode()== KeyEvent.VK_S) {
		    status= "START";
	   }
	   else if(e.getKeyCode()== KeyEvent.VK_W){
		    herroUp();
	   }
	   else if(e.getKeyCode()== KeyEvent.VK_S){
		   herroDown();
	   }
	   else if(e.getKeyCode()== KeyEvent.VK_A){
		   herroLeft();
	   }	  
	   else if(e.getKeyCode()== KeyEvent.VK_D){
		   herroRight();
	   }
   }
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	//};		
	public void herroLeft() {
		
	}
	public void herroRight() {
		
	}
    public void herroUp() {
    	if(index== 0) assassinA.y--;
    	if(index== 1) assassinB.y--;
    	if(index== 2) fighterA.y--;
    	if(index== 3) fighterB.y--;
    	if(index== 4) robbiA.y--;
    	if(index== 5) robbiB.y--;
    }
    public void herroDown() {
    	if(index== 0) assassinA.y++;
    	if(index== 1) assassinB.y++;
    	if(index== 2) fighterA.y++;
    	if(index== 3) fighterB.y++;
    	if(index== 4) robbiA.y++;
    	if(index== 5) robbiB.y++;
    }
	public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
		       //System.out.println("aaaaa");
				if(button0== e.getSource()) {
					
					index= 0;
				}
				else if(button1== e.getSource()) {
					index= 1;
					
				}
				else if(button2== e.getSource()) {
					index= 2;
				}
				else if(button3== e.getSource()) {
					index= 3;
				}
				else if(button4== e.getSource()) {
					index= 4;
				}
				else if(button5== e.getSource()) {
					index= 5;
				}
				else if(button6== e.getSource()) {
					index= 6;
				}
				else if(button7== e.getSource()) {
					index= 7;
				}
				else if(button8== e.getSource()) {
					index= 8;
				}
				else if(button9== e.getSource()) {
					index= 9;
				}
				else if(button10== e.getSource()) {
					status= "START";
				}
				//frame.requestFocus();;
				
	}
	
	/**
	 * 王者荣耀垃圾版主函数
	 *
	 */
    public void Button() {
    	button0.addActionListener(this);button0.setMnemonic(KeyEvent.VK_0);
    	button1.addActionListener(this);button1.setMnemonic(KeyEvent.VK_1);
    	button2.addActionListener(this);button2.setMnemonic(KeyEvent.VK_2);
    	button3.addActionListener(this);button3.setMnemonic(KeyEvent.VK_3);
    	button4.addActionListener(this);button4.setMnemonic(KeyEvent.VK_4);
    	button5.addActionListener(this);button5.setMnemonic(KeyEvent.VK_5);
    	button6.addActionListener(this);button6.setMnemonic(KeyEvent.VK_6);
    	button7.addActionListener(this);button7.setMnemonic(KeyEvent.VK_7);
    	button8.addActionListener(this);button8.setMnemonic(KeyEvent.VK_8);
    	button9.addActionListener(this);button9.setMnemonic(KeyEvent.VK_9);
    	button10.addActionListener(this);button10.setMnemonic(KeyEvent.VK_Q);

    	panel.add(button10);
		panel.add(button0);
		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(button4);
		panel.add(button5);
		panel.add(button6);
		panel.add(button7);
		panel.add(button8);
		panel.add(button9);
		
		
    }
    
    static KingGloryBattle game = new KingGloryBattle(); 
    public void Frame() {
			
			panel.add(game);
			frame.setLayout(new BorderLayout());
			frame.add(panel,BorderLayout.SOUTH);
			frame.add(game, BorderLayout.CENTER);
			frame.addKeyListener(this);
			//frame.add(game); // 将面板添加到JFrame中
			frame.setSize(WIDTH, HEIGHT); // 设置大小
			frame.setAlwaysOnTop(true); // 设置其总在最上
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 默认关闭操作
			frame.setIconImage(new ImageIcon("images/icon.jpg").getImage()); // 设置窗体的图标
			frame.setLocationRelativeTo(null); // 设置窗体初始位置
			//frame.setLocationRelativeTo(frame);
			frame.setVisible(true); //设置窗口可见
            //frame.setFocusable(true);
            
			game.action(); // 启动执行    	
    }
    
    /**
                  * 用户的登陆界面
     */
    public void loginFrame() {
    	   
    	   JFrame login_frame= new JFrame("login");	   
     	   JTextField text= new JTextField();
    	   JPasswordField password= new JPasswordField(); 
    	   JButton Confirm= new JButton("Confirm");
    	   JButton Cancel= new JButton("Cancel");
    	   JLabel user_label= new JLabel("users name:");
    	   JLabel password_label= new JLabel("password:");
    	   
    	   user_label.setBounds(0,  0, 100, 20);
    	   password_label.setBounds(0, 45, 100, 30);
    	   text.setBounds(100, 0, 200, 30);
    	   password.setBounds(100, 45, 200, 30);
    	   Confirm.setBounds(50, 100, 100, 30);
    	   Cancel.setBounds(160, 100, 100, 30);
    	   login_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           login_frame.setBounds(250, 350, 350, 200);
    	   login_frame.setLayout(null);

    	   login_frame.add(user_label);
    	   login_frame.add(password_label);
    	   login_frame.add(text);
    	   login_frame.add(password);
    	   login_frame.add(Confirm);
    	   login_frame.add(Cancel);
    	   login_frame.setVisible(true);
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		   // 面板对象
		
         index= 0; 
         game.loginFrame();
         game.Button();
         game.Frame();
	}
	
	/**
	 * 主要线程函数
	 */
	public void action() {
		MouseAdapter mouse= new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				int x= e.getX();
				int y= e.getY();
				//hero.setPosition(x, y);
			}
			public void mouseClicked(MouseEvent e) {
				int x= e.getX();
				int y= e.getY();
				//status= "START";
                herroMoveTo(x- 40, y- 40);
			}
			
		};
		this.addMouseListener(mouse); // 处理鼠标点击操作
		this.addMouseMotionListener(mouse); // 处理鼠标滑动操作
		
		timer= new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				if(status== "START") {
					  frame.requestFocus();
					  scanHerroAlive();
					  changeTimes();
					  getHerroPosition();
					  checkBullet();
					  canAttack();
					  HerroStep();
					  bulletStep();
				     
					  
				}

				repaint();
			}
			
		},1000, 10);
		
		timer1= new Timer();
		timer1.schedule(new TimerTask() {
			public void run() {

				makeOperate();			
				ServerAchieve();
				Explan();
			}
		},10, 1);
		
		
	}
	
	/**
	 * 生成可传输的命令
	 */
	public void makeOperate() {
		 if(index== 0) {
			 send= Integer.toString(index)+" "+Integer.toString(assassinA.x)+" "+Integer.toString(assassinA.y);
		 }
		 else if(index== 1) {
			 send= Integer.toString(index)+" "+Integer.toString(assassinB.x)+" "+Integer.toString(assassinB.y);
		 }
		 else if(index== 2) {
			 send= Integer.toString(index)+" "+Integer.toString(fighterA.x)+" "+Integer.toString(fighterA.y);
		 }
		 else if(index== 3) {
			 send= Integer.toString(index)+" "+Integer.toString(fighterB.x)+" "+Integer.toString(fighterB.y);
		 }
		 else if(index== 4) {
			 send= Integer.toString(index)+" "+Integer.toString(robbiA.x)+" "+Integer.toString(robbiA.y);
		 }
		 else if(index== 5) {
			 send= Integer.toString(index)+" "+Integer.toString(robbiB.x)+" "+Integer.toString(robbiB.y);
		 }
	}
	
	/**
	 * 解析传输过来的命令并改变相应的数值
	 */
	public void Explan() {
		String [] op= operate.split(" ");
		int heroSocket= Integer.parseInt(op[0]);
		int xSocket= Integer.parseInt(op[1]);
		int ySocket= Integer.parseInt(op[2]);
		if(heroSocket== 0) {
			assassinA.x= xSocket;
			assassinA.y= ySocket;
		}
		else if(heroSocket== 1) {
			assassinB.x= xSocket;
			assassinB.y= ySocket;
		}
		else if(heroSocket== 2) {
			fighterA.x= xSocket;
			fighterA.y= ySocket;
		}
		else if(heroSocket== 3) {
			fighterB.x= xSocket;
			fighterB.y= ySocket;
		}
		else if(heroSocket== 4) {
			robbiA.x= xSocket;
			robbiA.y= ySocket;
		}
		else if(heroSocket== 5) {
			robbiB.x= xSocket;
			robbiB.y= ySocket;
		}
	}
	
	/**
	 * 接受客户端传输过来的命令
	 */
	public void ServerAchieve() {
	      try {
	    	  while(true) {
	    	  makeOperate();
	          client= server.accept();
	          
	          BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
	          //读取客户端发送来的消息
	          String mess = br.readLine(); 
	          operate= mess;
	          BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
	          bw.write(send+"\n"); 
	          bw.flush();
	          
	          if(operate!= null)
	          Explan();
	          client.close();	    		  
	    	  }

	       } catch (IOException e) {
	          e.printStackTrace();
	       }
	}
	public void scanHerroAlive() {
		herroAnum= 0;
		herroBnum= 0;
		if(assassinA.getIsAlive()== 1) herroAnum++;
		if(assassinB.getIsAlive()== 1) herroBnum++;
		if(fighterA.getIsAlive()== 1) herroAnum++;
		if(fighterB.getIsAlive()== 1) herroBnum++;
		if(robbiA.getIsAlive()== 1) herroAnum++;
		if(robbiB.getIsAlive()== 1) herroBnum++;
	}
	public void changeTimes() {
	     assassinA.addTimes();
	     assassinB.addTimes();
	     fighterA.addTimes();
	     fighterB.addTimes();
	     robbiA.addTimes();
	     robbiB.addTimes();	
	}
	static int ignore= 0;
	public void herroAutoMove() {
		ignore++;
		//if(ignore== 50) {
			ignore= 0;
		    if(index!= 0)
		    	assassinA.autoMove();
		    if(index!= 1)
		    	assassinB.autoMove();
		    if(index!= 2)
		    	fighterA.autoMove();
		    if(index!= 3)
		    	fighterB.autoMove();
		    if(index!= 4)
		    	robbiA.autoMove();
		    if(index!= 5)
		    	robbiB.autoMove();			
		//}

	}
	/**
	 * 判断炸弹时候可以爆炸
	 */
	public void checkBullet() {
         for(int i= 0 ;i< Maxx; i++) {
        	 for(int j= 0; j< Maxx; j++) {
    			 if(i!= j&&(i%2!= j%2)&&((herroBullet[i].getX()==position[j][0]+ 15)&&(herroBullet[i].getY()== position[j][1]+ 15))
    					 &&getHerroAlive(j)== 1) {
    				  if(herroBullet[i].stillAlive== 1) {
     					  changeBeAttack(j);
    				  herroBullet[i].stillAlive= 0;
    				  herroBullet[i].timeToBoom= 1;  					  
    				  }
 
    			  
    				  			       
    			 }        		 
        	 }
         }
	}
	/**
	 * 执行炸弹的下一步
	 */
	public void bulletStep() {
		for(int i= 0; i< Maxx; i++) {
			if(herroBullet[i].getStillAlive()== 1) {
				herroBullet[i].step();
				
			}
				
		}
	}
	
   public void changeBeAttack(int i) {
	    if(i== 0) assassinA.beAttack();
	    if(i== 1) assassinB.beAttack();
	    if(i== 2) fighterA.beAttack();
	    if(i== 3) fighterB.beAttack();
	    if(i== 4) robbiA.beAttack();
	    if(i== 5) robbiB.beAttack();
   }
   public int getHerroAlive(int i) {
	    if(i== 0) return assassinA.getIsAlive();
	    if(i== 1) return assassinB.getIsAlive();
	    if(i== 2) return fighterA.getIsAlive();
	    if(i== 3) return fighterB.getIsAlive();
	    if(i== 4) return robbiA.getIsAlive();
	    if(i== 5) return robbiB.getIsAlive();	
	    
	    return -1;
   }
	/**
	 * 判断炸弹是否符合发射条件
	 */
	public void canAttack() {
		for(int i= 0; i< Maxx; i++) {
			for(int j= 0; j< Maxx; j++) {
				if(j!= i&&(i%2!= j% 2)&&(position[i][0]== position[j][0]||position[i][1]== position[j][1])
					&&getHerroAlive(j)== 1&&getHerroAlive(i)== 1) {
					//if(herroBullet[j].getStillAlive()== 0) {
					  herroBullet[i].setStillAlive();
					  herroBullet[i].setIndex(i);
					  herroBullet[i].setBullet(position[i][0], position[i][1]);
					
					  if(position[i][1]== position[j][1])
					  herroBullet[i].setToX(position[i][0]>  position[j][0]? -1: 1);
				      if(position[i][0]== position[j][0])
					  herroBullet[i].setToY(position[i][1]>  position[j][1]? -1: 1);

				}				
			}
		}
	}
	
	/**
	 * 英雄移到
	 * @param x 英雄移动到的X坐标
	 * @param y 英雄移动到的Y坐标
	 */
	public void herroMoveTo(int x, int y) {
		if(index== 0) {
    		assassinA.moveTo(x, y);//assassinA.setStillMove();
		}
		else if(index== 1) {
			assassinB.moveTo(x, y);//assassinB.setStillMove();
		}
		else if(index== 2) {
			fighterA.moveTo(x, y);//fighterA.setStillMove();
		}
		else if(index== 3) {
			fighterB.moveTo(x, y);//fighterB.setStillMove();
		}
		else if(index== 4) {
			robbiA.moveTo(x, y);//robbiA.setStillMove();
		}
		else if(index== 5) {
			robbiB.moveTo(x, y);//robbiB.setStillMove();
		}
	    
	}   
	
	/**
	 * 英雄的下一步
	 */
	public void HerroStep() {
		if(assassinA.getStillMove()== 1) 
			assassinA.step();
		if(assassinB.getStillMove()== 1)
			assassinB.step();
		if(fighterA.getStillMove()== 1)
			fighterA.step();
		if(fighterB.getStillMove()== 1)
			fighterB.step();
		if(robbiA.getStillMove()== 1)
			robbiA.step();
		if(robbiB.getStillMove()== 1)
			robbiB.step();
	}
	/**
	 * 获取英雄的X坐标，Y坐标
	 */
	public void getHerroPosition() {
		  position[0][0]= assassinA.getX(); position[0][1]= assassinA.getY();
		  position[1][0]= assassinB.getX(); position[1][1]= assassinB.getY();
		  position[2][0]= fighterA.getX(); position[2][1]= fighterA.getY();
		  position[3][0]= fighterB.getX(); position[3][1]= fighterB.getY();
		  position[4][0]= robbiA.getX(); position[4][1]= robbiA.getY();
		  position[5][0]= robbiB.getX(); position[5][1]= robbiB.getY();

	}

}