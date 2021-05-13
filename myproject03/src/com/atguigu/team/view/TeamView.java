package com.atguigu.team.view;

import org.junit.Test;
import com.atguigu.team.domain.Employee;
import com.atguigu.team.domain.Programmer;
import com.atguigu.team.service.*;

public class TeamView {
	private NameListService listSvc = new NameListService();
	private TeamService teamSvc = new TeamService();
	
	public void enterMainMenu() {
		boolean loopFlag = true;
		char select = '0';
		while(loopFlag) {
			if (select != '1') {
				listAllEmployees();
			}			
			System.out.println("1-團隊列表    2-添加團隊成員    3-刪除團隊成員    4-退出    請選擇(1-4)");
			select = TSUtility.readMenuSelection();
			switch (select) {
				case '1':
					getTeam();
					break;
				case '2':
					addMember();
					break;
				case '3':
					deleteMember();
					break;
				case '4':
					System.out.println("確認是否退出(Y/N)：");
					char isExit = TSUtility.readConfirmSelection();
					if (isExit == 'Y') {
						loopFlag = false;
					}
					break;
			}
		}
		/*
		do {
			System.out.println("-------------------------------------開發團隊調度軟件--------------------------------------");
			listAllEmployees();
			System.out.println("-------------------------------------------------------------------------------------");
			System.out.println("1-團隊列表    2-添加團隊成員    3-刪除團隊成員    4-退出    請選擇(1-4)");
			// 讀取使用者輸入內容
			int select = TSUtility.readMenuSelection();
			switch (select) {
				case 1:
					getTeam();
			}
		}while(loopFlag);
		*/
	}
	
	private void listAllEmployees() {
		Employee[] employees = listSvc.getAllEmployee();
		System.out.println("-------------------------------------開發團隊調度軟件--------------------------------------");
		if (employees.length == 0) {
			System.out.println("沒有員工資料");
		} else {
			System.out.println("ID\t姓名\t年齡\t工資\t職位\t狀態\t獎金\t股票\t\t領用設備");
		}
		
		// 根據多態性調用 toString 方法
		for(Employee e : employees) {
			/*
			if (e instanceof Programmer) {
				System.out.print("[memberid=" + ((Programmer) e).getMemberId() + "]");
			}
			*/
			System.out.println(e); // 多態, 調用 Override 後的 toString() 
		}
		
		System.out.println("-------------------------------------------------------------------------------------");
		
		/*
		for(int i = 0; i < employees.length; ++i) {
			// 先印出共有的屬性
			System.out.print(employees[i].getId() + "\t" + employees[i].getName() + "\t" + employees[i].getSalary());
			if (employees[i] instanceof Architect) {
				Architect architect = (Architect)employees[i];
				System.out.println("\t架構師" + architect.getStatus() + "\t" + architect.getStock());

			} else if () {}
		}
		*/
	}
	
	private void getTeam() {
		Programmer[] team = teamSvc.getTeam();
		System.out.println("-------------------------------------團隊成員列表--------------------------------------");
		if (team == null || team.length == 0) {
			System.out.println("團隊中沒有人！");
		} else {
			System.out.println("TID/ID\t姓名\t年齡\t工資\t職位\t狀態\t獎金\t股票\t\t領用設備");
			for(Programmer p : team) {
				System.out.println(p.getDetailsForTeam());
			}
		}
		System.out.println("-------------------------------------------------------------------------------------");
	}
	
	private void addMember() {
		System.out.println("請輸入要添加的員工ID：");
		int id = TSUtility.readInt();
		try {
			teamSvc.addMember(listSvc.getEmployee(id));
			System.out.println("添加成功");
			TSUtility.readReturn();
		} catch (TeamException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void deleteMember() {
		System.out.println("請輸入要刪除員工的TID：");
		int tid = TSUtility.readInt();
		System.out.println("確認是否刪除(Y/N)：");
		char y_n = TSUtility.readConfirmSelection();
		if (y_n == 'Y') {
			try {
				teamSvc.removeMember(tid);
				System.out.println("刪除成功");
				TSUtility.readReturn();
			} catch (TeamException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static void main(String[] args) {
		TeamView teamview = new TeamView();
		teamview.enterMainMenu();
		
	}
	
	@Test
	public void test1() {
		NameListService nl = new NameListService();
		TeamService t = new TeamService();
		
		// 將所有成員的訊息打印，並將期都添加至團隊中
		for(Employee e : nl.getAllEmployee()) {
			System.out.println(e.getId() + " " + e.getAge() + " " + e.getName() + " " + e.getSalary());
			try {
				t.addMember(e);
			} catch (TeamException ex) {
				System.out.println(ex.getMessage());
			}		
		}
		
		// 刪除測試
		try {
			t.removeMember(2);
		} catch (TeamException ex) {
			System.out.println(ex.getMessage());
		}
		
		// 列出團隊中的成員
		Programmer[] ps = t.getTeam();
		for(int i = 0; i < t.getTotal(); ++i) {
			Programmer p = ps[i];
			System.out.println(p.getMemberId() + " " + p.getId() + " " + p.getAge() + " " + p.getName() + " " + p.getSalary());
		}
	}
}
