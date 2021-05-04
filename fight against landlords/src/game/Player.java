package game;
/*
 * 斗地主小游戏
 * 作者：王赫20201863
 * */
import java.util.ArrayList;

/*玩家类
 * */
public class Player {
	/**当前玩家拥有的牌
	 * */
	ArrayList<Integer> cards = new ArrayList<Integer>();
	/**当前玩家拥有的牌是否杯选中
	 * */
	ArrayList<Boolean> isSelected = new ArrayList<Boolean>();
	/**判断回合是否结束
	 * */
	static int count = 0;
	/**当前玩家是否为地主，默认值为false
	 * */
	boolean isLandlord = false;
	/**叫地主时出的分数
	 * */
	int score = 0;
	/**给玩家手中的牌排序
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
	/**比较两张牌的大小
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
	/**判断玩家出的牌能否压过电脑出的牌
	 * */
	public static boolean isBigger(ArrayList<Integer> cards1,
			ArrayList<Integer> cards2) {
		if(cards2.isEmpty()) {
			//对方不要
			return true;
		}else if(cards1.size() == 5) {
			//玩家出三带二或单顺
			if(cards2.size() == 5) {
				if(cards1.get(4)%13 - cards2.get(0)%13 == 4) {
					//单顺的情况
					return cards1.get(4)%13 > cards2.get(4)%13?true:false;
				}else {
					//三带二的情况
					int num1 = cards1.get(0)%13 == cards1.get(2)%13?
						cards1.get(0)%13:cards1.get(2)%13;
					int num2 = cards2.get(0)%13 == cards2.get(2)%13?
							cards2.get(0)%13:cards2.get(2)%13;
					//记录各自的三张牌的值							
					return num1 > num2?true:false;				
				}
			}else {
				//如果玩家出5张，对方出的不是5张，则压不过
				return false;
			}	
		}else if(cards1.size() == 4) {
			//玩家出炸或三带一
			if(cards1.get(0)%13 == cards1.get(3)%13) {
				//玩家出炸
				if(cards2.size() == 2) {	
					return cards2.get(0) == 52?false:true;
					//对方出王炸，肯定压不过			
				}else if(cards2.size() == 4) {
					//玩家和对方都出四张，对方可能是炸也可能是三带一
					if(cards2.get(0)%13 == cards2.get(3)%13) {
						//对方出炸
						return cards1.get(0)%13 > cards2.get(0)%13?true:false;
					}else {
						//对方出三带一
						return true;
					}					
				}else {
					return true;
				}
			}else {
				//玩家出三带一
				if(cards2.size() == 4) {
					//对方也出三带一
					int num1 = cards1.get(0) == cards1.get(2)?
							cards1.get(0)%13:cards1.get(1)%13;
					int num2 = cards2.get(0) == cards2.get(2)?
							cards2.get(0)%13:cards2.get(1)%13;
					//找出三的值			
					return num1 > num2?true:false;						
				}else {
					//玩家出三带一，对方不是三带一
					return false;
				}
			}
		}else if(cards1.size() == 3) {
			//玩家出三张
			if(cards2.size() == 3) {
				return cards1.get(0)%13 > cards2.get(0)%13?true:false;		
			}else {
				return false;
			}
		}else if(cards1.size() == 2) {
			if(cards1.get(0) >= 52) {
				//王炸肯定能压
				return true;
			}else if(cards2.size() == 2) {
				if(cards2.get(0) >= 52) {
					//对方是王炸，肯定压不过
					return false;
				}else {
					return cards1.get(0)%13 > cards2.get(0)%13?true:false;			
				}
			}else {
				//对方出的不是两张
				return false;
			}
		}else if(cards1.size() == 1) {
			if(cards2.size() == 1) {
				if(cards1.get(0) >= 52) {
					//出大小王的情况
					return cards1.get(0) > cards2.get(0)?true:false;
				}else if(cards2.get(0) >= 52) {
					//玩家不是大小王，且对方是大小王
					return false;
				}else if(cards1.get(0)%13 > cards2.get(0)%13){
					return true;
				}else {
					return false;
				}
			}else {
				//玩家出一张，对方出多于一张
				return false;
			}
		}else {
			//牌数更多的单顺，双顺，三顺
			if(cards1.size() == cards2.size()) {
				//出牌相同的情况
				if(cards1.get(cards1.size() - 1)%13 - cards1.get(0)%13 == 
						cards2.get(cards2.size() - 1)%13 - 
						cards2.get(0)%13) {
					//判断二者出的顺子种类是否相同
					return cards1.get(0)%13 > cards2.get(0)%13?true:false;
				}else {
					//顺子种类不同，一定压不过
					return false;
				}
			}else {
				//出牌数量不同，一定压不过
				return false;
			}
		}
	}
	/**判断出的牌是否符合规则
	 * */
	public static boolean isCorrect(ArrayList<Integer> cards) {
		if(cards.isEmpty()) 
			//不出牌一定不符合规则
			return false;
		else if(cards.size() == 1) 
			//单牌一定符合规则
			return true;
		else if(cards.size() == 2) {
			//判断双牌的情况
			if(cards.get(0) >= 52 && cards.get(0) >= 52) 
				//都是大小王，符合规则
				return true;
			else if(cards.get(0) >= 52 && cards.get(0) < 52) 
				//一个是大小王，一个不是，不符合
				return false;
			else if(cards.get(0) < 52 && cards.get(0) >= 52) 
				//一个是大小王，一个不是，不符合
				return false;
			else if(cards.get(0)%13 == cards.get(1)%13)
				//都不是大小王，若两牌相同，则符合规则
				return true;
			else 
				//都不是大小王，若两牌不同，则不符合规则
				return false;
		}else if(cards.size() == 3) {
			//判断三牌的情况
			return cards.get(0)%13 == cards.get(2)%13?true:false;
		}else if(cards.size() == 4) {
			if(cards.get(0)%13 == cards.get(3)%13) 
				//若四张牌相同，符合规则
				return true;
			else if(cards.get(0)%13 == cards.get(2)%13)
				//若四张牌全不同，但前三张相同（三带一）则符合规则
				return true;
			else if(cards.get(1)%13 == cards.get(3)%13) 
				//若四张牌全不同，但后三张相同（三带一）则符合规则
				return true;
		}else if(cards.size() == 5) {
			//三带二或单顺
			if(cards.get(4)%13 - cards.get(0)%13 == 4 && 
					isDistinct(cards, 1, 0, cards.size() - 1)) 
				//单顺
				return true;
			else if(cards.get(0)%13 == cards.get(2)%13 && 
					cards.get(3)%13 == cards.get(4)%13) 
				//若为前三张牌相同的三带二，则符合规则
				return true;
			else if(cards.get(0)%13 == cards.get(1)%13 && 
					cards.get(2)%13 == cards.get(4)%13) 
				//若为后三张牌相同的三带二，则符合规则
				return true;			
		}else {
			//牌数大于五，即单顺，双顺，三顺
			if(cards.get(cards.size()-1)%13 - cards.get(0)%13 
					== cards.size() - 1 && 
							isDistinct(cards, 1, 0, cards.size() - 1)) 
				//单顺，符合规则
				return true;
			else if(cards.get(cards.size()-1)%13 - cards.get(0)%13 
					== cards.size()/2 - 1 && 
							isDistinct(cards, 2, 0, cards.size() - 1)) 
				//双顺，符合规则
				return true;				
			else if(cards.get(cards.size()-1)%13 - cards.get(0)%13 
					== cards.size()/3 - 1 && 
							isDistinct(cards, 3, 0, cards.size() - 1)) 
				//三顺，符合规则
				return true;			
			//四带二，飞机带翅膀太复杂，懒得写
		}
		return false;
	}
	/**判断一组牌中是否互不相同，用来辅助判断单双三顺
	 * */
	public static boolean isDistinct(ArrayList<Integer> cards,int count
			,int start,int end) {		
		if((end - start + 1)%count != 0)
				return false;
		//双，三顺的检验必须满足特定的长度
		for(int j = 0;j < count;j++) {
			for(int i = start + j;i <= end - count + j;i += count) {
				if(cards.get(i)%13 == cards.get(i + count)%13) {
					return false;
				}
			}
		}		
		return true;
	}
	/**电脑回牌
	 * */
	public ArrayList<Integer> reply(ArrayList<Integer> cards){
		ArrayList<Integer> replyCards = new ArrayList<Integer>();
		if(cards.isEmpty()) {
			//对方不出牌的情况，随机出牌
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
			//对方出两张牌的情况
			replyCards = this.replyShuang(cards);		
		}else if(cards.size() == 3) {
			//玩家出三张牌的情况
			replyCards = this.replySan(cards);
		}else if(cards.size() == 4) {
			//玩家出四张牌的情况
			if(cards.get(0)%13 == cards.get(3)%13) {
				//炸的情况
				replyCards = this.replyZha(cards);
				//由于王炸可以压过普通炸，所以下面还需要考虑王炸的情况
				if(replyCards.isEmpty()) {
					//如果没有找到普通炸，就考虑王炸
					replyCards = this.replyWangzha(cards);
				}
			}else {
				//三带一的情况
				replyCards = this.replySandaiyi(cards);
			}
		}else if(cards.size() == 5) {
			//玩家出五张牌的情况，即三带二或单顺
			replyCards = cards.get(4)%13 - cards.get(0)%13 == 4?
					this.replyDanshun(cards):this.replySandaier(cards);
		}else {
			//单顺，双顺，三顺的情况
			if(cards.get(cards.size()-1)%13 - cards.get(0)%13 
					== cards.size() - 1) {
				//单顺				
				replyCards = this.replyDanshun(cards);
			}else if(cards.get(cards.size()-1)%13 - cards.get(0)%13 
					== cards.size()/2 - 1) {
				//双顺
				replyCards = this.replyShuangshun(cards);
			}else if(cards.get(cards.size()-1)%13 - cards.get(0)%13 
					== cards.size()/3 - 1) {
				//三顺
				replyCards = this.replySanshun(cards);
			}
		}
		if(replyCards.isEmpty()) {
			//如果找了一圈没找到回复的牌，就找炸或王炸
			replyCards = this.replyZha(cards);
			if(replyCards.isEmpty()) {
				//如果没有找到普通炸，就考虑王炸
				replyCards = this.replyWangzha(cards);
			}
		}
		return replyCards;
	}
	/**电脑回复单牌
	 * */
	public ArrayList<Integer> replyDan(ArrayList<Integer> cards){
		ArrayList<Integer> replyCards = new ArrayList<Integer>();
		if(cards.isEmpty()) {
			//玩家不出牌的情况
			replyCards.add(this.cards.get(0));
			this.cards.remove(0);
		}else {
			//玩家出一张牌的情况
			if(cards.get(0) == 52) {
				//如果出小王
				if(this.cards.get(this.cards.size()-1) == 53) {
					replyCards.add(53);
					this.cards.remove(this.cards.size()-1);
				}
			}else if(cards.get(0) < 52){
				//不是大小王
				for(int i = 0;i < this.cards.size();i++) {
					if(this.cards.get(i)%13 > cards.get(0)%13 ||
							this.cards.get(i) >= 52) {
						replyCards.add(this.cards.get(i));
						//把牌加入序列
						this.cards.remove(i);
						//把牌从现有牌中去掉
						break;
						//若在现有牌中找到比对方大的一张牌，就出牌
					}
				}
			}
		}
		return replyCards;
	}
	/**电脑回复对子
	 * */
	public ArrayList<Integer> replyShuang(ArrayList<Integer> cards){
		ArrayList<Integer> replyCards = new ArrayList<Integer>();
		int num = cards.isEmpty()?-1:cards.get(0)%13;
		//找出对子对应的值
		if(cards.isEmpty()) {
			for(int i = 0;i < this.cards.size() - 1;i++) {
				if(this.cards.get(i)%13 > num && 
						this.cards.get(i)%13 == this.cards.get(i+1)%13) {
					replyCards.add(this.cards.get(i));
					replyCards.add(this.cards.get(i+1));
					//把牌加入序列
					this.cards.remove(i);
					this.cards.remove(i);
					//把牌从现有牌中去掉
					break;
					//若在现有牌中找到比对方大的两张牌，就出牌
				}
			}
		}else {
			if(cards.get(0)!=52) {
				//对牌的情况
				for(int i = 0;i < this.cards.size() - 1;i++) {
					if(this.cards.get(i)%13 > num && 
							this.cards.get(i)%13 == this.cards.get(i+1)%13) {
						replyCards.add(this.cards.get(i));
						replyCards.add(this.cards.get(i+1));
						//把牌加入序列
						this.cards.remove(i);
						this.cards.remove(i);
						//把牌从现有牌中去掉
						break;
						//若在现有牌中找到比对方大的两张牌，就出牌
					}
				}
			}//王炸无法接，所以直接跳过
		}			
		return replyCards;
	}
	/**电脑回复三张牌
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
				//把牌加入序列
				this.cards.remove(i);
				this.cards.remove(i);
				this.cards.remove(i);
				//把牌从现有牌中去掉
				break;
				//若在现有牌中找到比对方大的三张牌，就出牌
			}
		}
		return replyCards;
	}
	/**电脑回复三带一
	 * */
	public ArrayList<Integer> replySandaiyi(ArrayList<Integer> cards){
		ArrayList<Integer> replyCards = new ArrayList<Integer>();
		int num = 0;//记录三带一中三的值
		if(cards.isEmpty()) 
			num = -1;
		else if(cards.get(0)%13 == cards.get(2)%13) 
			//前三张牌相同的情况
			num = cards.get(0);
		else 
			//后三张牌相同的情况
			num = cards.get(1);	
		for(int i = 0;i < this.cards.size() - 2;i++) {
			if(this.cards.get(i)%13 > num && 
					this.cards.get(i)%13 == this.cards.get(i+2)%13) {
				replyCards.add(this.cards.get(i));
				replyCards.add(this.cards.get(i+1));
				replyCards.add(this.cards.get(i+2));
				//把相同的三张牌加入序列
				if(i+3 < this.cards.size()) {
					//加入单牌
					replyCards.add(this.cards.get(i+3));
					this.cards.remove(i);
					this.cards.remove(i);
					this.cards.remove(i);
					this.cards.remove(i);
					//把牌从现有牌中去掉
				}else {
					if(i > 0) {
						//加入单牌
						replyCards.add(0,this.cards.get(i-1));
						//在前面加入
						this.cards.remove(i-1);
						this.cards.remove(i-1);
						this.cards.remove(i-1);
						this.cards.remove(i-1);
						//把牌从现有牌中去掉
					}else {
						//如果找不到单牌，就无法回复
						replyCards.clear();
					}
				}						
				break;
				//若在现有牌中找到比对方大的三带一，就出牌
			}
		}
		return replyCards;
	}
	/**电脑回复三带二
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
				//把相同的三张牌加入序列
				if(i+4 < this.cards.size()) {
					//加入两张带的牌
					for(int j = i+3;j < this.cards.size()-1;j++) {
						if(this.cards.get(j)%13 == this.cards.get(j+1)%13) {
							replyCards.add(this.cards.get(j));
							replyCards.add(this.cards.get(j+1));
							//加入牌
							this.cards.remove(j);
							this.cards.remove(j);
							this.cards.remove(i);
							this.cards.remove(i);
							this.cards.remove(i);
							//把牌从现有牌中去掉
							break;
						}
					}
					if(replyCards.size() == 3) 
						//若没找到带的两张牌，则无法回复
						replyCards.clear();					
				}else {
					if(i > 1) {
						for(int j = 0;j < i-1;j++) {
							if(this.cards.get(j)%13 == this.cards.get(j+1)%13) {
								replyCards.add(0,this.cards.get(j));
								replyCards.add(0,this.cards.get(j+1));
								//加入牌
								this.cards.remove(i);
								this.cards.remove(i);
								this.cards.remove(i);
								this.cards.remove(j);
								this.cards.remove(j);
								//把牌从现有牌中去掉
								break;
							}
						}
						if(replyCards.size() == 3) 
							//若没找到带的两张牌，则无法回复
							replyCards.clear();						
					}else {
						//若没找到带的两张牌，则无法回复
						replyCards.clear();
					}
				}						
				break;
			}
		}
		return replyCards;
	}
	/**电脑回复单顺
	 * */
	public ArrayList<Integer> replyDanshun(ArrayList<Integer> cards){
		return this.replyShunzi(cards, 1);
	}
	/**电脑回复双顺
	 * */
	public ArrayList<Integer> replyShuangshun(ArrayList<Integer> cards){
		return this.replyShunzi(cards, 2);
	}
	/**电脑回复三顺
	 * */
	public ArrayList<Integer> replySanshun(ArrayList<Integer> cards){
		return this.replyShunzi(cards, 3);
	}
	/**回复单，双，三顺的基础方法
	 * */
	public ArrayList<Integer> replyShunzi(ArrayList<Integer> cards, 
			int length){
		ArrayList<Integer> replyCards = new ArrayList<Integer>();
		int num = cards.isEmpty()?-1:cards.get(0);
		//找到对方出的顺的最小值，若空，则定为-1
		int k = cards.isEmpty()?this.cards.size():cards.size();
		//控制顺子的长度
		for(int l = k;l >= 5 + length/2;l--) {
			//从最长开始找顺子
			for(int i = 0;i < this.cards.size() - l + 1;i++) {
				if(this.cards.get(i)%13 > num &&
						this.cards.get(i + l - 1)%13 - 
						this.cards.get(i)%13 == l/length - 1 && 
								isDistinct(this.cards, length, i, 
										i + l - 1)) {
					//若找到了顺，就加入
					for(int j = i;j < i + l;j++) {
						replyCards.add(this.cards.get(i));
						this.cards.remove(i);
					}
					break;
				}
			}
			if(!cards.isEmpty()) 
				break;
				//如果对方不是空，则不管找没找到，直接跳出			
			if(!replyCards.isEmpty()) 
				break;
				//如果找到了顺子，则跳出			
		}
		return replyCards;
	}
	/**电脑回复炸
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
				//把牌加入序列
				this.cards.remove(i);
				this.cards.remove(i);
				this.cards.remove(i);
				this.cards.remove(i);
				//把牌从现有牌中去掉
				break;
				//若在现有牌中找到比对方大的四张牌，就出牌
			}
		}
		return replyCards;
	}
	/**电脑回复王炸
	 * */
	public ArrayList<Integer> replyWangzha(ArrayList<Integer> cards){
		ArrayList<Integer> replyCards = new ArrayList<Integer>();
		if(this.cards.size() >= 2) {
			if(this.cards.get(this.cards.size()-2) == 52 && 
					this.cards.get(this.cards.size()-1) == 53) {
				replyCards.add(52);
				replyCards.add(53);
				//把大小王加入序列
				this.cards.remove(this.cards.size()-1);
				this.cards.remove(this.cards.size()-1);
				//把大小王移出现有的牌
			}
		}		
		return replyCards;
	}
	/**主函数
	 * */
	public static void main(String[] args) {
		Main.main(args);
	}
}
