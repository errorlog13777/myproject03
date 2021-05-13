package com.atguigu.team.domain;

public class Architect extends Designer {
	private int stock;
	
	public Architect(int id, String name, int age, double salary, Equipment equipment, double bonus, int stock) {
		super(id, name, age, salary, equipment, bonus);
		this.stock = stock;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	@Override
	public String getMemberDetails() {
		return getMemberId() + "/" + getDetails();
	}
	
	@Override
	public String getDetailsForTeam() {
		return getMemberDetails() + "\t架構師";
	}
	
	@Override
	public String toString() {
		return getDetails() + "\t架構師\t" + this.getStatus() + "\t" + this.getBonus() + "\t" + stock + "\t" + this.getEquipment().getDescription();  
	}
}
