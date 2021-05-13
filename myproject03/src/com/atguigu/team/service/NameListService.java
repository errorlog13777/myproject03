package com.atguigu.team.service;

import com.atguigu.team.domain.*;

import static com.atguigu.team.service.Data.*;

public class NameListService {
	private Employee[] employees;
	
	public NameListService() {
		employees = new Employee[Data.EMPLOYEES.length];
		//Employee  :  10, id, name, age, salary
	    //Programmer:  11, id, name, age, salary
	    //Designer  :  12, id, name, age, salary, bonus
	    //Architect :  13, id, name, age, salary, bonus, stock
		for(int i = 0; i < employees.length; ++i) {
			// 先獲取共有的屬性
			int type = Integer.parseInt(Data.EMPLOYEES[i][0]);
			int id = Integer.parseInt(Data.EMPLOYEES[i][1]);
			String name = EMPLOYEES[i][2]; // 省略 Data. 必須先 import "team.service.Data.*"
			int age = Integer.parseInt(EMPLOYEES[i][3]);
			double salary = Double.parseDouble(EMPLOYEES[i][4]);
			
			// 不共同擁有的屬性
			Equipment equipment;
			double bonus;
			int stock;
			
			switch(type) {
				case Data.EMPLOYEE:
					employees[i] = new Employee(id, name, age, salary);
					break;
				case Data.PROGRAMMER:
					equipment = createEquipment(i);
					employees[i] = new Programmer(id, name, age, salary, equipment);
					break;
				case Data.DESIGNER:
					equipment = createEquipment(i);
					bonus = Double.parseDouble(EMPLOYEES[i][5]);
					employees[i] = new Designer(id, name, age, salary, equipment, bonus);
					break;
				case Data.ARCHITECT:
					equipment = createEquipment(i);
					bonus = Double.parseDouble(EMPLOYEES[i][5]);
					stock = Integer.parseInt(EMPLOYEES[i][6]);
					employees[i] = new Architect(id, name, age, salary, equipment, bonus, stock);
					break;
			}
		}
	}
	
	private Equipment createEquipment(int index) {
		//PC      :21, model, display
	    //NoteBook:22, model, price
	    //Printer :23, name, type 
		int type = Integer.parseInt(Data.EQUIPMENTS[index][0]);
		switch (type) {
			case Data.PC:
				return new PC(Data.EQUIPMENTS[index][1], Data.EQUIPMENTS[index][2]);
			case Data.NOTEBOOK:
				double price = Double.parseDouble(Data.EQUIPMENTS[index][2]);
				return new NoteBook(Data.EQUIPMENTS[index][1], price);
			case Data.PRINTER:
				return new Printer(Data.EQUIPMENTS[index][1], Data.EQUIPMENTS[index][2]);
		}
		return null;
	}
	
	public Employee[] getAllEmployee() {
		return employees;
	}
	
	public Employee getEmployee(int id) throws TeamException {
		for(int i = 0; i < employees.length; ++i) {
			if (id == employees[i].getId()) {
				return employees[i];
			}
		}
		throw new TeamException("invalid id");
	}
}
