package com.atguigu.team.service;
import com.atguigu.team.domain.*;

public class TeamService {
	private static int counter = 1;
	private final int MAX_MEMBER = 5;
	private Programmer[] team = new Programmer[MAX_MEMBER];
	private int total = 0;

	public Programmer[] getTeam() {
		Programmer[] team_ = new Programmer[total];
		for(int i = 0; i < team_.length; ++i) {
			team_[i] = this.team[i];
		}
		return team_;
	}

	public void addMember(Employee e) throws TeamException {
		/*
		 * 失败信息包含以下几种： 
		 * – 成员已满，无法添加 
		 * – 该成员不是开发人员，无法添加 
		 * – 该员工已在本开发团队中 
		 * – 该员工已是某团队 成员 
		 * – 该员正在休假，无法添加 
		 * – 团队中至多只能 有一名架构师 
		 * – 团队中至多只能 有两名设计师 
		 * – 团队中至多只能 有三名程序员
		 */

		// 確認是否已經滿人
		if (total == MAX_MEMBER) {
			throw new TeamException("人員已滿(最多 " + MAX_MEMBER + " 人)");
		}
		// 確認是否為開發人員
		if (!(e instanceof Programmer)) {
			throw new TeamException("非開發人員，無法添加！");
		}
		// 確認是否已在當前開發團隊中
		if (isExist(e)) {
			throw new TeamException("已經在當前的開發團隊中！");
		}
		// 確認員工是否正在休假，或是忙碌
		// 只有 Programmer 及其子類才擁有 Status，因此必須向下轉型
		Programmer p = (Programmer)e;	// 前面已有 instanceof Programmer 判斷, 因此不會發生錯誤
		if ("BUSY".equalsIgnoreCase(p.getStatus().getName())) {
			throw new TeamException("該員工已是某團隊員工！");
		} else if(p.getStatus().getName().equalsIgnoreCase("VOCATION")) {
			throw new TeamException("該員工正在休假，無法添加！");
		}
		// 判斷每個職位的人數
		// Architect 1
		// Designer 2
		// Programmer 3
		int numOfArch = 0, numOfDes = 0, numOfPro = 0;
		// 找出 team 中的所有職位的人數
		for(int i = 0; i < team.length; ++i) {
			if (team[i] instanceof Architect) {
				++numOfArch;
			} else if (team[i] instanceof Designer) {
				++numOfDes;
			} else if (team[i] instanceof Programmer) {
				++numOfPro;
			}
		}
		// 根據 p 來判斷
		if (p instanceof Architect) {
			if (numOfArch >= 1) {
				throw new TeamException("最多只能有一名架構師");
			}
		} else if (p instanceof Designer) {
			if (numOfDes >= 2) {
				throw new TeamException("最多只能有兩名設計師");
			}
		} else if (p instanceof Programmer) {
			if (numOfPro >= 3) {
				throw new TeamException("最多只能有三名程序員");
			}
		}
		// if (p instanceof Architect && numOfArch >= 1) {
		// 		throw ...
		// } else if (p instanceof Designer && numOfDes >= 2) {
		// } ... etc
		// 這種寫法為何是錯誤的？
		// 單純的邏輯錯誤，由於是以 instanceof 來判斷職位(架構師, 設計師...)
		// 因此假設當前 team 只有兩名設計師，現在 p 為一名架構師要加入 team 中(numOfArch == 0, numOfDes == 2)
		// 此時 "if (p instanceof Architect && numOfArch >= 1)" 會因為不成立
		// 進而去判斷 "else if (p instanceof Designer && numOfDes >= 2)", 
		// 但一個 Architect 也是一個 Designer 的實例, 因此兩種情況皆為 true 報出的異常為 "最多只能有兩名設計師"
		
		// 添加 p 或 e(二選一)
		// 由於 p 或 e 都是一個對象(代表其為一個地址值，只是兩者類型不同)，
		// 因此先 setStatus, setMemberId 或是先將其添加至 team 中都可以
		team[total++] = p;
		p.setStatus(Status.BUSY);
		p.setMemberId(counter++);
	}
	
	// 為何不使用 e.getId() == employee.getId() 也能得到相同的結果?
	// 因為 Data 內員工的地址值與其 id 皆沒有重複，因此在這邊兩種寫法都可以。
	// 但需要注意的是：由於我們的員工(對象、實例)都是以 new 的方式創建，因此其地址值才會皆不相同，
	// 若是像 String pool 這種情況：
	// String s1 = "jajaja"
	// String s2 = "jajaja"
	// 此時 s1 == s2 ，這就是因為 String pool 的原因讓兩個對象指向同一個地址值。
	// (注意上述 String 的這種寫法也沒有用 new，若改用 new 則結果會是 false)
	// 那麼可以使用 e.getMemberID() == ((Programmer)e).getMemberId() 判斷嗎？
	// 可以，由於是判斷這個 memberId 是否存在於 team 的數組中，
	// (memberId 不會因為成員被移除後而重置，而是會繼續保存於 Programmer 的實例中(包含其子類))
	// 因此即便一個原本在團隊中但是後來被移除的人，使用 memberId 來判斷其是否存在於 team 中也不會有問題
	// 
	private boolean isExist(Employee e) {
		for(Employee employee : team) {
			if (e == employee) {
				return true;
			}
		}
		return false;
	}
	
	public void removeMember(int memberId) throws TeamException {
		// 1.成員必須位於團隊中
		// 2.成員數量必須 > 0
		
		// 成員數量必須 > 0
		if (team.length == 0 || total == 0) {
			throw new TeamException("當前團隊中沒有成員！");
		}
		// 成員必須位於團隊中
		boolean is_exist = false;
		int i = 0;
		for(; i < total; ++i) {
			if (team[i].getMemberId() == memberId) {
				is_exist = true;
				team[i].setStatus(Status.FREE);
				
				//后面的元素覆盖前面的元素
				for(int j = i; j < total - 1; ++j) {
					team[j] = team[j + 1];
				}
				team[--total] = null;
				break;
			}
		}
		// 方式一：使用 flag 的方式判斷是否有找到指定的成員
		/*
		if (is_exist == false) {
			throw new TeamException("找不到指定的成員，無法刪除");
		}
		*/
		
		// 方式二：使用 i 的數值來判斷是否有找到指定的成員
		// 若 i < total 時，即代表找到指定的成員(找到會直接 break)
		// 因此若一直沒找到指定成員，則 i 會不滿足進入循環的條件(i == total)
		if (i == total) {
			throw new TeamException("找不到指定的成員，無法刪除");
		}
	}
	
	public int getTotal() {
		return total;
	}
}
