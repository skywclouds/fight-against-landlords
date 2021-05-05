package game;
/*
 * 斗地主小游戏
 * Author: Henry Wang
 * Date: 2021-4-22
 * */


import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**主类
 * */
public class Main {
	/**主窗体
	 * */
	static JFrame jf = null;
	/**对话框
	 * */
	static JDialog jd = null;
	/**显示对方出的牌的按钮数组
	 * */
	static JButton[] jb1 = new JButton[20];
	/**显示玩家拥有的牌的按钮数组
	 * */
	static JButton[] jb2 = new JButton[20];
	/**控制选中牌的鼠标事件接口数组
	 * */
	static ActionListener ac[] = new ActionListener[20];
	/**npc1
	 * */
	static Player p1 = new Player();
	/**npc2
	 * */
	static Player p2 = new Player();
	/**玩家
	 * */
	static Player p3 = new Player();
	/**玩家出的牌
	 * */
	static ArrayList<Integer> currentCards = new ArrayList<Integer>();
	/**主函数
	 * */
	public static void main(String[] args) {
		initializeFrame();//初始化窗体
		grant();//发牌
		control();//进入游戏流程	
	}
	/**控制游玩流程
	 * */
	public static void control(int n) {
		System.out.println("count is: "+Player.count);
		if(Player.count == 2) {
			//如果一个人出牌两个人都不要，则这个人再随便出牌
			currentCards.clear();
			Player.count = 0;
		}
		//n代表接下来该谁出牌，1代表npc1，2代表npc2，3代表玩家
		if(n == 1) {
			//该npc1出牌
			ArrayList<Integer> replyCards = p1.reply(currentCards);
			if(p1.cards.isEmpty()) {
				jf.setVisible(false);
				if(p1.isLandlord || p3.isLandlord) {
					createDialog("警告", "你输了！",true);
				}else if(p2.isLandlord){
					createDialog("恭喜", "你赢了！",true);
				}	
			}
			//npc1出牌
			if(!replyCards.isEmpty()) {
				//npc1要了
				currentCards = replyCards;
				Player.count = 0;
			}else {
				Player.count++;
			}
			System.out.println("npc1出"+replyCards.size()+"张牌");
			control(2);//npc1出牌后让npc2出牌
		}else if(n == 2) {
			//该npc2出牌
			ArrayList<Integer> replyCards = p2.reply(currentCards);
			if(p2.cards.isEmpty()) {
				jf.setVisible(false);
				if(p2.isLandlord || p3.isLandlord) {
					createDialog("警告", "你输了！",true);
				}else {
					createDialog("恭喜", "你赢了！",true);
				}				
			}
			if(!replyCards.isEmpty()) {
				//npc2要了npc1的牌
				currentCards = replyCards;
				Player.count = 0;
			}else {
				Player.count++;
			}
			System.out.println("npc2出"+replyCards.size()+"张牌");
			control(3);//npc2出牌后让玩家出牌
		}else {
			new Main().showCards();//显示牌，接下来玩家出牌
		}
	}
	/**控制游玩流程
	 * */
	public static void control() {
		if(p1.isLandlord) 
			control(1);//npc1是地主，进入1
		else if(p2.isLandlord) 
			control(2);//npc2是地主，进入2
		else 
			control(3);//玩家是地主，进入3		
	}
	/**初始化主窗体
	 * */
	public static void initializeFrame() {
		jf = new JFrame("fight against landlords");
		jf.setSize(1500,500);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);		
		System.out.println("主窗体创建正常");
		Container con = jf.getContentPane();
		con.setLayout(new GridLayout(3,1));
		JPanel jp1 = new JPanel();
		JPanel jp2 = new JPanel();
		JPanel jp3 = new JPanel();
		jp1.setLayout(new GridLayout(1,20));
		jp2.setLayout(new GridLayout(1,20));
		jp3.setLayout(new GridLayout(1,3));
		con.add(jp1);
		con.add(jp2);
		con.add(jp3);
		JButton jb11 = new JButton("出牌");
		JButton jb12 = new JButton("不要");
		JButton jb13 = new JButton("退出");
		jp3.add(jb11);
		jp3.add(jb12);
		jp3.add(jb13);
		jf.setVisible(true);
		//给发牌按钮添加鼠标事件
		jb11.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Integer> selectedCards = new ArrayList<Integer>();
				for(int i = 0;i < p3.cards.size();i++) {
					if(p3.isSelected.get(i))
						selectedCards.add(p3.cards.get(i));
				}
				if(Player.isCorrect(selectedCards)) {
					if(Player.isBigger(selectedCards, currentCards)) {
						//如果玩家出的牌符合规则且能压过电脑出的牌
						for(int i = 0;i < selectedCards.size();i++) {
							//把发出去的牌去掉
							int index = p3.cards.indexOf(selectedCards.get(i));
							p3.cards.remove(index);
							p3.isSelected.remove(index);
						}
						//把已选中的牌的序列清空
						currentCards = selectedCards;
						System.out.println("出牌成功");
						if(p3.cards.isEmpty()) {
							jf.setVisible(false);
							createDialog("恭喜", "你赢了",true);
						}else {
							Player.count = 0;
							control(1);//玩家出牌后由npc1出牌
						}
					}else {
						createDialog("警告", "发的牌压不过",false);
					}
				}else {
					createDialog("警告", "出牌不符合规则",false);
				}	
			}
		});
		//给跳过按钮添加鼠标事件
		jb12.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 0;i < p3.isSelected.size();i++) {
					p3.isSelected.set(i, false);
				}
				Player.count++;
				control(1);//玩家跳过后由npc1出牌
			}
		});
		//给退出按钮添加鼠标事件
		jb13.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);//退出程序
			}
		});
		//初始化按钮数组
		for(int i = 0;i < 20;i++) {
			jb1[i] = new JButton();
			jb2[i] = new JButton();
			jp1.add(jb1[i]);
			jp2.add(jb2[i]);
		}
		jiaoDizhu();//进入叫地主程序
	}
	/**发牌
	 * */
	public static void grant() {
		System.out.println("进入发牌程序");
		//储存所有牌的数组
		ArrayList<Integer> totalCards = new ArrayList<Integer>();
		for(int i = 0;i < 54;i++) 
			totalCards.add(i);		
		//地主的3张牌
		ArrayList<Integer> pk = new ArrayList<Integer>();
		for(int i = 0;i < 3;i++) {
			//从总牌中随机抽出3张
			int n = (int)Math.random()*totalCards.size();
			pk.add(totalCards.get(n));
			totalCards.remove(n);
		}
		//随机发牌
		for(int i = 0;i < 17;i++) {
			int n = (int)(Math.random()*totalCards.size());
			p1.cards.add(totalCards.get(n));
			p1.isSelected.add(false);
			totalCards.remove(n);
			n = (int)(Math.random()*totalCards.size());
			p2.cards.add(totalCards.get(n));
			p2.isSelected.add(false);
			totalCards.remove(n);
			n = (int)(Math.random()*totalCards.size());
			p3.cards.add(totalCards.get(n));
			p3.isSelected.add(false);
			totalCards.remove(n);
		}
		//把预留的三张牌给地主
		if(p1.isLandlord) {
			for(int i = 0;i < pk.size();i++) {
				p1.cards.add(pk.get(i));
				p1.isSelected.add(false);
			}
		}else if(p2.isLandlord) {
			for(int i = 0;i < pk.size();i++) {
				p2.cards.add(pk.get(i));
				p2.isSelected.add(false);
			}
		}else {
			for(int i = 0;i < pk.size();i++) {
				p3.cards.add(pk.get(i));
				p3.isSelected.add(false);
			}
		}
		//对牌进行排序
		p1.sort();
		p2.sort();
		p3.sort();
		System.out.println("发牌结束");		
		System.out.println("玩家有"+p3.cards.size()+"张牌");
		new Main().initializeButtons();//初始化按钮
		new Main().showCards();//把牌显示在屏幕上
		
	}
	/**初始化按钮
	 * */
	public void initializeButtons() {
		for(int i = 0;i < 20;i++) {
			if(ac[i] != null) 
				//若该按钮已设置鼠标事件，则移除重新设置
				jb2[i].removeActionListener(ac[i]);			
		}
		for(int i = 0;i < p3.cards.size();i++) {			
			URL url = getClass().
					getResource("/res/"+p3.cards.get(i)+".jpg");
			//获取图片路径
			ImageIcon ic = new ImageIcon(url);//创建icon对象
			ic.setImage(ic.getImage().
					getScaledInstance(70, 100,Image.SCALE_DEFAULT));
			jb2[i].setIcon(ic);//给按钮设置图像
			final int loction = i;
			final ImageIcon imc = new ImageIcon(url);
			ac[i] = new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {					
					if(p3.isSelected.get(loction)) {
						//如果该牌已被选中，则取消选中
						p3.isSelected.set(loction, false);						
						imc.setImage(imc.getImage().
								getScaledInstance
								(70, 100, Image.SCALE_DEFAULT));
						jb2[loction].setIcon(imc);//更改图标缩放
						System.out.println(loction+"取消选中成功");
					}else {
						//如果该牌没有选中，则选中
						p3.isSelected.set(loction, true);						
						imc.setImage(imc.getImage().
								getScaledInstance
								(70, 120, Image.SCALE_DEFAULT));
						jb2[loction].setIcon(imc);//更改图标缩放
						System.out.println(loction+"选中成功");						
					}		
				}
			};		
			jb2[i].addActionListener(ac[i]);
		}
	}
	/**将玩家的牌显示在屏幕上
	 * */
	public void showCards() {
		System.out.println("进入showcards程序");
		initializeButtons();//重置按钮
		for(int i = 0;i < p3.cards.size();i++) {
			jb1[i].setIcon(null);
		}
		for(int i = p3.cards.size();i < 20;i++) {
			//清除发出去的牌
			jb1[i].setIcon(null);
			jb2[i].setIcon(null);
		}
		for(int i = 0;i < currentCards.size();i++) {
			//显示对方出的牌
			URL url = getClass().
					getResource("/res/"+currentCards.get(i)+".jpg");
			//获取图片路径
			ImageIcon ic = new ImageIcon(url);//创建icon对象
			ic.setImage(ic.getImage().
					getScaledInstance(70, 100,Image.SCALE_DEFAULT));
			jb1[i].setIcon(ic);//给按钮设置图像			
		}		
	}
	/**叫地主
	 * */
	public static void jiaoDizhu() {
		//npc1的分数
		p1.score = (int)(Math.random()*3)+1;
		System.out.println("npc1分数为"+p1.score);
		//npc2的分数
		p2.score = (int)(Math.random()*3)+1;
		System.out.println("npc2分数为"+p2.score);		
		jd = new JDialog(jf,"叫地主，若不叫请直接关闭对话框",true);//创建对话框
		jd.setResizable(false);
		jd.setLocationRelativeTo(jf);
		jd.setSize(500, 200);
		Container cc = jd.getContentPane();//获取容器
		cc.setLayout(new GridLayout(1,3));//设置布局
		//创建3个按钮
		JButton jb1 = new JButton("1分");
		JButton jb2 = new JButton("2分");
		JButton jb3 = new JButton("3分");
		//按钮加入容器
		cc.add(jb1);
		cc.add(jb2);
		cc.add(jb3);
		//添加键盘事件
		jb1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p3.score = 1;
				jd.setVisible(false);
			}
		});
		//添加键盘事件
		jb2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p3.score = 2;
				jd.setVisible(false);
			}
		});
		//添加键盘事件
		jb3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p3.score = 3;
				jd.setVisible(false);
			}
		});
		jd.setVisible(true);//使窗体可见
		System.out.println("对话框创建正常");
		int largest = 0;
		//比出叫分最大的确定地主
		largest = p1.score > p2.score?p1.score:p2.score;
		if(p3.score > largest || p3.score == 3) {
			p3.isLandlord = true;
			createDialog("地主情况", "你是地主", false);
		}else {
			if(p1.score > p2.score) {
				p1.isLandlord = true;
				createDialog("地主情况", "你是农民", false);
			}else {
				p2.isLandlord = true;
				createDialog("地主情况", "你是农民", false);
			}
		}
	}
	/**根据标题和内容创建对话框
	 * */
	public static void createDialog(String title,String text,boolean isExit) {
		jd = null;
		jd = new JDialog(jf,title,true);//重置对话框
		jd.setResizable(false);
		jd.setLocationRelativeTo(jf);
		jd.setSize(200, 200);
		Container c = jd.getContentPane();
		c.setLayout(new GridLayout(2,1));
		JLabel jl = new JLabel(text);
		jl.setFont(new Font("宋体", 0, 20));
		c.add(jl);
		JButton jb1 = new JButton("确定");
		jb1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isExit) {
					System.exit(0);
				}
				jd.setVisible(false);				
			}
		});
		c.add(jb1);
		jd.setVisible(true);//使对话框可见
		System.out.println("对话框创建正常");
	}
}
