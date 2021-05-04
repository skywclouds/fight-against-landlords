package game;
/*
 * ������С��Ϸ
 * ���ߣ�����20201863
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

/**����
 * */
public class Main {
	/**������
	 * */
	static JFrame jf = null;
	/**�Ի���
	 * */
	static JDialog jd = null;
	/**��ʾ�Է������Ƶİ�ť����
	 * */
	static JButton[] jb1 = new JButton[20];
	/**��ʾ���ӵ�е��Ƶİ�ť����
	 * */
	static JButton[] jb2 = new JButton[20];
	/**����ѡ���Ƶ�����¼��ӿ�����
	 * */
	static ActionListener ac[] = new ActionListener[20];
	/**npc1
	 * */
	static Player p1 = new Player();
	/**npc2
	 * */
	static Player p2 = new Player();
	/**���
	 * */
	static Player p3 = new Player();
	/**��ҳ�����
	 * */
	static ArrayList<Integer> currentCards = new ArrayList<Integer>();
	/**������
	 * */
	public static void main(String[] args) {
		initializeFrame();//��ʼ������
		grant();//����
		control();//������Ϸ����	
	}
	/**������������
	 * */
	public static void control(int n) {
		System.out.println("count is: "+Player.count);
		if(Player.count == 2) {
			//���һ���˳��������˶���Ҫ�����������������
			currentCards.clear();
			Player.count = 0;
		}
		//n�����������˭���ƣ�1����npc1��2����npc2��3�������
		if(n == 1) {
			//��npc1����
			ArrayList<Integer> replyCards = p1.reply(currentCards);
			if(p1.cards.isEmpty()) {
				jf.setVisible(false);
				if(p1.isLandlord || p3.isLandlord) {
					createDialog("����", "�����ˣ�",true);
				}else if(p2.isLandlord){
					createDialog("��ϲ", "��Ӯ�ˣ�",true);
				}	
			}
			//npc1����
			if(!replyCards.isEmpty()) {
				//npc1Ҫ��
				currentCards = replyCards;
				Player.count = 0;
			}else {
				Player.count++;
			}
			System.out.println("npc1��"+replyCards.size()+"����");
			control(2);//npc1���ƺ���npc2����
		}else if(n == 2) {
			//��npc2����
			ArrayList<Integer> replyCards = p2.reply(currentCards);
			if(p2.cards.isEmpty()) {
				jf.setVisible(false);
				if(p2.isLandlord || p3.isLandlord) {
					createDialog("����", "�����ˣ�",true);
				}else {
					createDialog("��ϲ", "��Ӯ�ˣ�",true);
				}				
			}
			if(!replyCards.isEmpty()) {
				//npc2Ҫ��npc1����
				currentCards = replyCards;
				Player.count = 0;
			}else {
				Player.count++;
			}
			System.out.println("npc2��"+replyCards.size()+"����");
			control(3);//npc2���ƺ�����ҳ���
		}else {
			new Main().showCards();//��ʾ�ƣ���������ҳ���
		}
	}
	/**������������
	 * */
	public static void control() {
		if(p1.isLandlord) 
			control(1);//npc1�ǵ���������1
		else if(p2.isLandlord) 
			control(2);//npc2�ǵ���������2
		else 
			control(3);//����ǵ���������3		
	}
	/**��ʼ��������
	 * */
	public static void initializeFrame() {
		jf = new JFrame("fight against landlords");
		jf.setSize(1500,500);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);		
		System.out.println("�����崴������");
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
		JButton jb11 = new JButton("����");
		JButton jb12 = new JButton("��Ҫ");
		JButton jb13 = new JButton("�˳�");
		jp3.add(jb11);
		jp3.add(jb12);
		jp3.add(jb13);
		jf.setVisible(true);
		//�����ư�ť�������¼�
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
						//�����ҳ����Ʒ��Ϲ�������ѹ�����Գ�����
						for(int i = 0;i < selectedCards.size();i++) {
							//�ѷ���ȥ����ȥ��
							int index = p3.cards.indexOf(selectedCards.get(i));
							p3.cards.remove(index);
							p3.isSelected.remove(index);
						}
						//����ѡ�е��Ƶ��������
						currentCards = selectedCards;
						System.out.println("���Ƴɹ�");
						if(p3.cards.isEmpty()) {
							jf.setVisible(false);
							createDialog("��ϲ", "��Ӯ��",true);
						}else {
							Player.count = 0;
							control(1);//��ҳ��ƺ���npc1����
						}
					}else {
						createDialog("����", "������ѹ����",false);
					}
				}else {
					createDialog("����", "���Ʋ����Ϲ���",false);
				}	
			}
		});
		//��������ť�������¼�
		jb12.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 0;i < p3.isSelected.size();i++) {
					p3.isSelected.set(i, false);
				}
				Player.count++;
				control(1);//�����������npc1����
			}
		});
		//���˳���ť�������¼�
		jb13.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);//�˳�����
			}
		});
		//��ʼ����ť����
		for(int i = 0;i < 20;i++) {
			jb1[i] = new JButton();
			jb2[i] = new JButton();
			jp1.add(jb1[i]);
			jp2.add(jb2[i]);
		}
		jiaoDizhu();//����е�������
	}
	/**����
	 * */
	public static void grant() {
		System.out.println("���뷢�Ƴ���");
		//���������Ƶ�����
		ArrayList<Integer> totalCards = new ArrayList<Integer>();
		for(int i = 0;i < 54;i++) 
			totalCards.add(i);		
		//������3����
		ArrayList<Integer> pk = new ArrayList<Integer>();
		for(int i = 0;i < 3;i++) {
			//��������������3��
			int n = (int)Math.random()*totalCards.size();
			pk.add(totalCards.get(n));
			totalCards.remove(n);
		}
		//�������
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
		//��Ԥ���������Ƹ�����
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
		//���ƽ�������
		p1.sort();
		p2.sort();
		p3.sort();
		System.out.println("���ƽ���");		
		System.out.println("�����"+p3.cards.size()+"����");
		new Main().initializeButtons();//��ʼ����ť
		new Main().showCards();//������ʾ����Ļ��
		
	}
	/**��ʼ����ť
	 * */
	public void initializeButtons() {
		for(int i = 0;i < 20;i++) {
			if(ac[i] != null) 
				//���ð�ť����������¼������Ƴ���������
				jb2[i].removeActionListener(ac[i]);			
		}
		for(int i = 0;i < p3.cards.size();i++) {			
			URL url = getClass().
					getResource("/res/"+p3.cards.get(i)+".jpg");
			//��ȡͼƬ·��
			ImageIcon ic = new ImageIcon(url);//����icon����
			ic.setImage(ic.getImage().
					getScaledInstance(70, 100,Image.SCALE_DEFAULT));
			jb2[i].setIcon(ic);//����ť����ͼ��
			final int loction = i;
			final ImageIcon imc = new ImageIcon(url);
			ac[i] = new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {					
					if(p3.isSelected.get(loction)) {
						//��������ѱ�ѡ�У���ȡ��ѡ��
						p3.isSelected.set(loction, false);						
						imc.setImage(imc.getImage().
								getScaledInstance
								(70, 100, Image.SCALE_DEFAULT));
						jb2[loction].setIcon(imc);//����ͼ������
						System.out.println(loction+"ȡ��ѡ�гɹ�");
					}else {
						//�������û��ѡ�У���ѡ��
						p3.isSelected.set(loction, true);						
						imc.setImage(imc.getImage().
								getScaledInstance
								(70, 120, Image.SCALE_DEFAULT));
						jb2[loction].setIcon(imc);//����ͼ������
						System.out.println(loction+"ѡ�гɹ�");						
					}		
				}
			};		
			jb2[i].addActionListener(ac[i]);
		}
	}
	/**����ҵ�����ʾ����Ļ��
	 * */
	public void showCards() {
		System.out.println("����showcards����");
		initializeButtons();//���ð�ť
		for(int i = 0;i < p3.cards.size();i++) {
			jb1[i].setIcon(null);
		}
		for(int i = p3.cards.size();i < 20;i++) {
			//�������ȥ����
			jb1[i].setIcon(null);
			jb2[i].setIcon(null);
		}
		for(int i = 0;i < currentCards.size();i++) {
			//��ʾ�Է�������
			URL url = getClass().
					getResource("/res/"+currentCards.get(i)+".jpg");
			//��ȡͼƬ·��
			ImageIcon ic = new ImageIcon(url);//����icon����
			ic.setImage(ic.getImage().
					getScaledInstance(70, 100,Image.SCALE_DEFAULT));
			jb1[i].setIcon(ic);//����ť����ͼ��			
		}		
	}
	/**�е���
	 * */
	public static void jiaoDizhu() {
		//npc1�ķ���
		p1.score = (int)(Math.random()*3)+1;
		System.out.println("npc1����Ϊ"+p1.score);
		//npc2�ķ���
		p2.score = (int)(Math.random()*3)+1;
		System.out.println("npc2����Ϊ"+p2.score);		
		jd = new JDialog(jf,"�е�������������ֱ�ӹرնԻ���",true);//�����Ի���
		jd.setResizable(false);
		jd.setLocationRelativeTo(jf);
		jd.setSize(500, 200);
		Container cc = jd.getContentPane();//��ȡ����
		cc.setLayout(new GridLayout(1,3));//���ò���
		//����3����ť
		JButton jb1 = new JButton("1��");
		JButton jb2 = new JButton("2��");
		JButton jb3 = new JButton("3��");
		//��ť��������
		cc.add(jb1);
		cc.add(jb2);
		cc.add(jb3);
		//��Ӽ����¼�
		jb1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p3.score = 1;
				jd.setVisible(false);
			}
		});
		//��Ӽ����¼�
		jb2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p3.score = 2;
				jd.setVisible(false);
			}
		});
		//��Ӽ����¼�
		jb3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p3.score = 3;
				jd.setVisible(false);
			}
		});
		jd.setVisible(true);//ʹ����ɼ�
		System.out.println("�Ի��򴴽�����");
		int largest = 0;
		//�ȳ��з�����ȷ������
		largest = p1.score > p2.score?p1.score:p2.score;
		if(p3.score > largest || p3.score == 3) {
			p3.isLandlord = true;
			createDialog("�������", "���ǵ���", false);
		}else {
			if(p1.score > p2.score) {
				p1.isLandlord = true;
				createDialog("�������", "����ũ��", false);
			}else {
				p2.isLandlord = true;
				createDialog("�������", "����ũ��", false);
			}
		}
	}
	/**���ݱ�������ݴ����Ի���
	 * */
	public static void createDialog(String title,String text,boolean isExit) {
		jd = null;
		jd = new JDialog(jf,title,true);//���öԻ���
		jd.setResizable(false);
		jd.setLocationRelativeTo(jf);
		jd.setSize(200, 200);
		Container c = jd.getContentPane();
		c.setLayout(new GridLayout(2,1));
		JLabel jl = new JLabel(text);
		jl.setFont(new Font("����", 0, 20));
		c.add(jl);
		JButton jb1 = new JButton("ȷ��");
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
		jd.setVisible(true);//ʹ�Ի���ɼ�
		System.out.println("�Ի��򴴽�����");
	}
}
