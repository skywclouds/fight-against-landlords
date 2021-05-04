package game;
/*
 * ������С��Ϸ
 * ���ߣ�����20201863
 * */
import java.util.ArrayList;

/*�����
 * */
public class Player {
	/**��ǰ���ӵ�е���
	 * */
	ArrayList<Integer> cards = new ArrayList<Integer>();
	/**��ǰ���ӵ�е����Ƿ�ѡ��
	 * */
	ArrayList<Boolean> isSelected = new ArrayList<Boolean>();
	/**�жϻغ��Ƿ����
	 * */
	static int count = 0;
	/**��ǰ����Ƿ�Ϊ������Ĭ��ֵΪfalse
	 * */
	boolean isLandlord = false;
	/**�е���ʱ���ķ���
	 * */
	int score = 0;
	/**��������е�������
	 * */
	public void sort() {
		for(int i = 0;i < this.cards.size();i++) {
			for(int j = 0;j < this.cards.size()-i-1;j++) {
				if(compare(this.cards.get(j),this.cards.get(j+1)) == 1){
					int temp = this.cards.get(j);
					this.cards.set(j, this.cards.get(j+1));
					this.cards.set(j+1, temp);
				}
			}
		}
	}
	/**�Ƚ������ƵĴ�С
	 * */
	static int compare(int a,int b) {
		if(a >= 52 && b >= 52) 
			if(a > b) 
				return 1;
			else if(a == b)
				return 0;
			else
				return -1;
		else if(a >= 52) 
			return 1;
		else if(b >= 52) 
			return -1;
		else {
			a = a%13;
			b = b%13;
			if(a > b)
				return 1;
			else if(a == b)
				return 0;
			else
				return -1;
		}	
	}
	/**�ж���ҳ������ܷ�ѹ�����Գ�����
	 * */
	public static boolean isBigger(ArrayList<Integer> cards1,
			ArrayList<Integer> cards2) {
		if(cards2.isEmpty()) {
			//�Է���Ҫ
			return true;
		}else if(cards1.size() == 5) {
			//��ҳ���������˳
			if(cards2.size() == 5) {
				if(cards1.get(4)%13 - cards2.get(0)%13 == 4) {
					//��˳�����
					return cards1.get(4)%13 > cards2.get(4)%13?true:false;
				}else {
					//�����������
					int num1 = cards1.get(0)%13 == cards1.get(2)%13?
						cards1.get(0)%13:cards1.get(2)%13;
					int num2 = cards2.get(0)%13 == cards2.get(2)%13?
							cards2.get(0)%13:cards2.get(2)%13;
					//��¼���Ե������Ƶ�ֵ							
					return num1 > num2?true:false;				
				}
			}else {
				//�����ҳ�5�ţ��Է����Ĳ���5�ţ���ѹ����
				return false;
			}	
		}else if(cards1.size() == 4) {
			//��ҳ�ը������һ
			if(cards1.get(0)%13 == cards1.get(3)%13) {
				//��ҳ�ը
				if(cards2.size() == 2) {	
					return cards2.get(0) == 52?false:true;
					//�Է�����ը���϶�ѹ����			
				}else if(cards2.size() == 4) {
					//��ҺͶԷ��������ţ��Է�������ըҲ����������һ
					if(cards2.get(0)%13 == cards2.get(3)%13) {
						//�Է���ը
						return cards1.get(0)%13 > cards2.get(0)%13?true:false;
					}else {
						//�Է�������һ
						return true;
					}					
				}else {
					return true;
				}
			}else {
				//��ҳ�����һ
				if(cards2.size() == 4) {
					//�Է�Ҳ������һ
					int num1 = cards1.get(0) == cards1.get(2)?
							cards1.get(0)%13:cards1.get(1)%13;
					int num2 = cards2.get(0) == cards2.get(2)?
							cards2.get(0)%13:cards2.get(1)%13;
					//�ҳ�����ֵ			
					return num1 > num2?true:false;						
				}else {
					//��ҳ�����һ���Է���������һ
					return false;
				}
			}
		}else if(cards1.size() == 3) {
			//��ҳ�����
			if(cards2.size() == 3) {
				return cards1.get(0)%13 > cards2.get(0)%13?true:false;		
			}else {
				return false;
			}
		}else if(cards1.size() == 2) {
			if(cards1.get(0) >= 52) {
				//��ը�϶���ѹ
				return true;
			}else if(cards2.size() == 2) {
				if(cards2.get(0) >= 52) {
					//�Է�����ը���϶�ѹ����
					return false;
				}else {
					return cards1.get(0)%13 > cards2.get(0)%13?true:false;			
				}
			}else {
				//�Է����Ĳ�������
				return false;
			}
		}else if(cards1.size() == 1) {
			if(cards2.size() == 1) {
				if(cards1.get(0) >= 52) {
					//����С�������
					return cards1.get(0) > cards2.get(0)?true:false;
				}else if(cards2.get(0) >= 52) {
					//��Ҳ��Ǵ�С�����ҶԷ��Ǵ�С��
					return false;
				}else if(cards1.get(0)%13 > cards2.get(0)%13){
					return true;
				}else {
					return false;
				}
			}else {
				//��ҳ�һ�ţ��Է�������һ��
				return false;
			}
		}else {
			//��������ĵ�˳��˫˳����˳
			if(cards1.size() == cards2.size()) {
				//������ͬ�����
				if(cards1.get(cards1.size() - 1)%13 - cards1.get(0)%13 == 
						cards2.get(cards2.size() - 1)%13 - 
						cards2.get(0)%13) {
					//�ж϶��߳���˳�������Ƿ���ͬ
					return cards1.get(0)%13 > cards2.get(0)%13?true:false;
				}else {
					//˳�����಻ͬ��һ��ѹ����
					return false;
				}
			}else {
				//����������ͬ��һ��ѹ����
				return false;
			}
		}
	}
	/**�жϳ������Ƿ���Ϲ���
	 * */
	public static boolean isCorrect(ArrayList<Integer> cards) {
		if(cards.isEmpty()) 
			//������һ�������Ϲ���
			return false;
		else if(cards.size() == 1) 
			//����һ�����Ϲ���
			return true;
		else if(cards.size() == 2) {
			//�ж�˫�Ƶ����
			if(cards.get(0) >= 52 && cards.get(0) >= 52) 
				//���Ǵ�С�������Ϲ���
				return true;
			else if(cards.get(0) >= 52 && cards.get(0) < 52) 
				//һ���Ǵ�С����һ�����ǣ�������
				return false;
			else if(cards.get(0) < 52 && cards.get(0) >= 52) 
				//һ���Ǵ�С����һ�����ǣ�������
				return false;
			else if(cards.get(0)%13 == cards.get(1)%13)
				//�����Ǵ�С������������ͬ������Ϲ���
				return true;
			else 
				//�����Ǵ�С���������Ʋ�ͬ���򲻷��Ϲ���
				return false;
		}else if(cards.size() == 3) {
			//�ж����Ƶ����
			return cards.get(0)%13 == cards.get(2)%13?true:false;
		}else if(cards.size() == 4) {
			if(cards.get(0)%13 == cards.get(3)%13) 
				//����������ͬ�����Ϲ���
				return true;
			else if(cards.get(0)%13 == cards.get(2)%13)
				//��������ȫ��ͬ����ǰ������ͬ������һ������Ϲ���
				return true;
			else if(cards.get(1)%13 == cards.get(3)%13) 
				//��������ȫ��ͬ������������ͬ������һ������Ϲ���
				return true;
		}else if(cards.size() == 5) {
			//��������˳
			if(cards.get(4)%13 - cards.get(0)%13 == 4 && 
					isDistinct(cards, 1, 0, cards.size() - 1)) 
				//��˳
				return true;
			else if(cards.get(0)%13 == cards.get(2)%13 && 
					cards.get(3)%13 == cards.get(4)%13) 
				//��Ϊǰ��������ͬ��������������Ϲ���
				return true;
			else if(cards.get(0)%13 == cards.get(1)%13 && 
					cards.get(2)%13 == cards.get(4)%13) 
				//��Ϊ����������ͬ��������������Ϲ���
				return true;			
		}else {
			//���������壬����˳��˫˳����˳
			if(cards.get(cards.size()-1)%13 - cards.get(0)%13 
					== cards.size() - 1 && 
							isDistinct(cards, 1, 0, cards.size() - 1)) 
				//��˳�����Ϲ���
				return true;
			else if(cards.get(cards.size()-1)%13 - cards.get(0)%13 
					== cards.size()/2 - 1 && 
							isDistinct(cards, 2, 0, cards.size() - 1)) 
				//˫˳�����Ϲ���
				return true;				
			else if(cards.get(cards.size()-1)%13 - cards.get(0)%13 
					== cards.size()/3 - 1 && 
							isDistinct(cards, 3, 0, cards.size() - 1)) 
				//��˳�����Ϲ���
				return true;			
			//�Ĵ������ɻ������̫���ӣ�����д
		}
		return false;
	}
	/**�ж�һ�������Ƿ񻥲���ͬ�����������жϵ�˫��˳
	 * */
	public static boolean isDistinct(ArrayList<Integer> cards,int count
			,int start,int end) {		
		if((end - start + 1)%count != 0)
				return false;
		//˫����˳�ļ�����������ض��ĳ���
		for(int j = 0;j < count;j++) {
			for(int i = start + j;i <= end - count + j;i += count) {
				if(cards.get(i)%13 == cards.get(i + count)%13) {
					return false;
				}
			}
		}		
		return true;
	}
	/**���Ի���
	 * */
	public ArrayList<Integer> reply(ArrayList<Integer> cards){
		ArrayList<Integer> replyCards = new ArrayList<Integer>();
		if(cards.isEmpty()) {
			//�Է������Ƶ�������������
			replyCards = this.replySanshun(cards);
			if(replyCards.isEmpty())
				replyCards = this.replyShuangshun(cards);
			if(replyCards.isEmpty())
				replyCards = this.replyDanshun(cards);
			if(replyCards.isEmpty())
				replyCards = this.replySandaier(cards);
			if(replyCards.isEmpty())
				replyCards = this.replySandaiyi(cards);
			if(replyCards.isEmpty())
				replyCards = this.replySan(cards);
			if(replyCards.isEmpty())
				replyCards = this.replyShuang(cards);
			if(replyCards.isEmpty())
				replyCards = this.replyDan(cards);
		}else if(cards.size() == 1) {
			replyCards = this.replyDan(cards);
		}else if(cards.size() == 2) {
			//�Է��������Ƶ����
			replyCards = this.replyShuang(cards);		
		}else if(cards.size() == 3) {
			//��ҳ������Ƶ����
			replyCards = this.replySan(cards);
		}else if(cards.size() == 4) {
			//��ҳ������Ƶ����
			if(cards.get(0)%13 == cards.get(3)%13) {
				//ը�����
				replyCards = this.replyZha(cards);
				//������ը����ѹ����ͨը���������滹��Ҫ������ը�����
				if(replyCards.isEmpty()) {
					//���û���ҵ���ͨը���Ϳ�����ը
					replyCards = this.replyWangzha(cards);
				}
			}else {
				//����һ�����
				replyCards = this.replySandaiyi(cards);
			}
		}else if(cards.size() == 5) {
			//��ҳ������Ƶ����������������˳
			replyCards = cards.get(4)%13 - cards.get(0)%13 == 4?
					this.replyDanshun(cards):this.replySandaier(cards);
		}else {
			//��˳��˫˳����˳�����
			if(cards.get(cards.size()-1)%13 - cards.get(0)%13 
					== cards.size() - 1) {
				//��˳				
				replyCards = this.replyDanshun(cards);
			}else if(cards.get(cards.size()-1)%13 - cards.get(0)%13 
					== cards.size()/2 - 1) {
				//˫˳
				replyCards = this.replyShuangshun(cards);
			}else if(cards.get(cards.size()-1)%13 - cards.get(0)%13 
					== cards.size()/3 - 1) {
				//��˳
				replyCards = this.replySanshun(cards);
			}
		}
		if(replyCards.isEmpty()) {
			//�������һȦû�ҵ��ظ����ƣ�����ը����ը
			replyCards = this.replyZha(cards);
			if(replyCards.isEmpty()) {
				//���û���ҵ���ͨը���Ϳ�����ը
				replyCards = this.replyWangzha(cards);
			}
		}
		return replyCards;
	}
	/**���Իظ�����
	 * */
	public ArrayList<Integer> replyDan(ArrayList<Integer> cards){
		ArrayList<Integer> replyCards = new ArrayList<Integer>();
		if(cards.isEmpty()) {
			//��Ҳ����Ƶ����
			replyCards.add(this.cards.get(0));
			this.cards.remove(0);
		}else {
			//��ҳ�һ���Ƶ����
			if(cards.get(0) == 52) {
				//�����С��
				if(this.cards.get(this.cards.size()-1) == 53) {
					replyCards.add(53);
					this.cards.remove(this.cards.size()-1);
				}
			}else if(cards.get(0) < 52){
				//���Ǵ�С��
				for(int i = 0;i < this.cards.size();i++) {
					if(this.cards.get(i)%13 > cards.get(0)%13 ||
							this.cards.get(i) >= 52) {
						replyCards.add(this.cards.get(i));
						//���Ƽ�������
						this.cards.remove(i);
						//���ƴ���������ȥ��
						break;
						//�������������ҵ��ȶԷ����һ���ƣ��ͳ���
					}
				}
			}
		}
		return replyCards;
	}
	/**���Իظ�����
	 * */
	public ArrayList<Integer> replyShuang(ArrayList<Integer> cards){
		ArrayList<Integer> replyCards = new ArrayList<Integer>();
		int num = cards.isEmpty()?-1:cards.get(0)%13;
		//�ҳ����Ӷ�Ӧ��ֵ
		if(cards.isEmpty()) {
			for(int i = 0;i < this.cards.size() - 1;i++) {
				if(this.cards.get(i)%13 > num && 
						this.cards.get(i)%13 == this.cards.get(i+1)%13) {
					replyCards.add(this.cards.get(i));
					replyCards.add(this.cards.get(i+1));
					//���Ƽ�������
					this.cards.remove(i);
					this.cards.remove(i);
					//���ƴ���������ȥ��
					break;
					//�������������ҵ��ȶԷ���������ƣ��ͳ���
				}
			}
		}else {
			if(cards.get(0)!=52) {
				//���Ƶ����
				for(int i = 0;i < this.cards.size() - 1;i++) {
					if(this.cards.get(i)%13 > num && 
							this.cards.get(i)%13 == this.cards.get(i+1)%13) {
						replyCards.add(this.cards.get(i));
						replyCards.add(this.cards.get(i+1));
						//���Ƽ�������
						this.cards.remove(i);
						this.cards.remove(i);
						//���ƴ���������ȥ��
						break;
						//�������������ҵ��ȶԷ���������ƣ��ͳ���
					}
				}
			}//��ը�޷��ӣ�����ֱ������
		}			
		return replyCards;
	}
	/**���Իظ�������
	 * */
	public ArrayList<Integer> replySan(ArrayList<Integer> cards){
		ArrayList<Integer> replyCards = new ArrayList<Integer>();
		int num = cards.isEmpty()?-1:cards.get(0)%13;
		for(int i = 0;i < this.cards.size() - 2;i++) {
			if(this.cards.get(i)%13 > num && 
					this.cards.get(i)%13 == this.cards.get(i+2)%13) {
				replyCards.add(this.cards.get(i));
				replyCards.add(this.cards.get(i+1));
				replyCards.add(this.cards.get(i+2));
				//���Ƽ�������
				this.cards.remove(i);
				this.cards.remove(i);
				this.cards.remove(i);
				//���ƴ���������ȥ��
				break;
				//�������������ҵ��ȶԷ���������ƣ��ͳ���
			}
		}
		return replyCards;
	}
	/**���Իظ�����һ
	 * */
	public ArrayList<Integer> replySandaiyi(ArrayList<Integer> cards){
		ArrayList<Integer> replyCards = new ArrayList<Integer>();
		int num = 0;//��¼����һ������ֵ
		if(cards.isEmpty()) 
			num = -1;
		else if(cards.get(0)%13 == cards.get(2)%13) 
			//ǰ��������ͬ�����
			num = cards.get(0);
		else 
			//����������ͬ�����
			num = cards.get(1);	
		for(int i = 0;i < this.cards.size() - 2;i++) {
			if(this.cards.get(i)%13 > num && 
					this.cards.get(i)%13 == this.cards.get(i+2)%13) {
				replyCards.add(this.cards.get(i));
				replyCards.add(this.cards.get(i+1));
				replyCards.add(this.cards.get(i+2));
				//����ͬ�������Ƽ�������
				if(i+3 < this.cards.size()) {
					//���뵥��
					replyCards.add(this.cards.get(i+3));
					this.cards.remove(i);
					this.cards.remove(i);
					this.cards.remove(i);
					this.cards.remove(i);
					//���ƴ���������ȥ��
				}else {
					if(i > 0) {
						//���뵥��
						replyCards.add(0,this.cards.get(i-1));
						//��ǰ�����
						this.cards.remove(i-1);
						this.cards.remove(i-1);
						this.cards.remove(i-1);
						this.cards.remove(i-1);
						//���ƴ���������ȥ��
					}else {
						//����Ҳ������ƣ����޷��ظ�
						replyCards.clear();
					}
				}						
				break;
				//�������������ҵ��ȶԷ��������һ���ͳ���
			}
		}
		return replyCards;
	}
	/**���Իظ�������
	 * */
	public ArrayList<Integer> replySandaier(ArrayList<Integer> cards){
		ArrayList<Integer> replyCards = new ArrayList<Integer>();
		int num = 0;
		if(cards.isEmpty()) 
			num = -1;
		else if(cards.get(0)%13 == cards.get(2)%13) 
			num = cards.get(0);
		else 
			num = cards.get(2);		
		for(int i = 0;i < this.cards.size() - 2;i++) {
			if(this.cards.get(i)%13 > num && 
					this.cards.get(i)%13 == this.cards.get(i+2)%13) {
				replyCards.add(this.cards.get(i));
				replyCards.add(this.cards.get(i+1));
				replyCards.add(this.cards.get(i+2));
				//����ͬ�������Ƽ�������
				if(i+4 < this.cards.size()) {
					//�������Ŵ�����
					for(int j = i+3;j < this.cards.size()-1;j++) {
						if(this.cards.get(j)%13 == this.cards.get(j+1)%13) {
							replyCards.add(this.cards.get(j));
							replyCards.add(this.cards.get(j+1));
							//������
							this.cards.remove(j);
							this.cards.remove(j);
							this.cards.remove(i);
							this.cards.remove(i);
							this.cards.remove(i);
							//���ƴ���������ȥ��
							break;
						}
					}
					if(replyCards.size() == 3) 
						//��û�ҵ����������ƣ����޷��ظ�
						replyCards.clear();					
				}else {
					if(i > 1) {
						for(int j = 0;j < i-1;j++) {
							if(this.cards.get(j)%13 == this.cards.get(j+1)%13) {
								replyCards.add(0,this.cards.get(j));
								replyCards.add(0,this.cards.get(j+1));
								//������
								this.cards.remove(i);
								this.cards.remove(i);
								this.cards.remove(i);
								this.cards.remove(j);
								this.cards.remove(j);
								//���ƴ���������ȥ��
								break;
							}
						}
						if(replyCards.size() == 3) 
							//��û�ҵ����������ƣ����޷��ظ�
							replyCards.clear();						
					}else {
						//��û�ҵ����������ƣ����޷��ظ�
						replyCards.clear();
					}
				}						
				break;
			}
		}
		return replyCards;
	}
	/**���Իظ���˳
	 * */
	public ArrayList<Integer> replyDanshun(ArrayList<Integer> cards){
		return this.replyShunzi(cards, 1);
	}
	/**���Իظ�˫˳
	 * */
	public ArrayList<Integer> replyShuangshun(ArrayList<Integer> cards){
		return this.replyShunzi(cards, 2);
	}
	/**���Իظ���˳
	 * */
	public ArrayList<Integer> replySanshun(ArrayList<Integer> cards){
		return this.replyShunzi(cards, 3);
	}
	/**�ظ�����˫����˳�Ļ�������
	 * */
	public ArrayList<Integer> replyShunzi(ArrayList<Integer> cards, 
			int length){
		ArrayList<Integer> replyCards = new ArrayList<Integer>();
		int num = cards.isEmpty()?-1:cards.get(0);
		//�ҵ��Է�����˳����Сֵ�����գ���Ϊ-1
		int k = cards.isEmpty()?this.cards.size():cards.size();
		//����˳�ӵĳ���
		for(int l = k;l >= 5 + length/2;l--) {
			//�����ʼ��˳��
			for(int i = 0;i < this.cards.size() - l + 1;i++) {
				if(this.cards.get(i)%13 > num &&
						this.cards.get(i + l - 1)%13 - 
						this.cards.get(i)%13 == l/length - 1 && 
								isDistinct(this.cards, length, i, 
										i + l - 1)) {
					//���ҵ���˳���ͼ���
					for(int j = i;j < i + l;j++) {
						replyCards.add(this.cards.get(i));
						this.cards.remove(i);
					}
					break;
				}
			}
			if(!cards.isEmpty()) 
				break;
				//����Է����ǿգ��򲻹���û�ҵ���ֱ������			
			if(!replyCards.isEmpty()) 
				break;
				//����ҵ���˳�ӣ�������			
		}
		return replyCards;
	}
	/**���Իظ�ը
	 * */
	public ArrayList<Integer> replyZha(ArrayList<Integer> cards){
		ArrayList<Integer> replyCards = new ArrayList<Integer>();
		int num = cards.isEmpty()?-1:cards.get(0);
		for(int i = 0;i < this.cards.size() - 3;i++) {
			if(this.cards.get(i)%13 > num && 
					this.cards.get(i)%13 == this.cards.get(i+3)%13) {
				replyCards.add(this.cards.get(i));
				replyCards.add(this.cards.get(i+1));
				replyCards.add(this.cards.get(i+2));
				replyCards.add(this.cards.get(i+3));
				//���Ƽ�������
				this.cards.remove(i);
				this.cards.remove(i);
				this.cards.remove(i);
				this.cards.remove(i);
				//���ƴ���������ȥ��
				break;
				//�������������ҵ��ȶԷ���������ƣ��ͳ���
			}
		}
		return replyCards;
	}
	/**���Իظ���ը
	 * */
	public ArrayList<Integer> replyWangzha(ArrayList<Integer> cards){
		ArrayList<Integer> replyCards = new ArrayList<Integer>();
		if(this.cards.size() >= 2) {
			if(this.cards.get(this.cards.size()-2) == 52 && 
					this.cards.get(this.cards.size()-1) == 53) {
				replyCards.add(52);
				replyCards.add(53);
				//�Ѵ�С����������
				this.cards.remove(this.cards.size()-1);
				this.cards.remove(this.cards.size()-1);
				//�Ѵ�С���Ƴ����е���
			}
		}		
		return replyCards;
	}
	/**������
	 * */
	public static void main(String[] args) {
		Main.main(args);
	}
}
