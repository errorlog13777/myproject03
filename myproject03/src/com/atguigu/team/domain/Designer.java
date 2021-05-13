package com.atguigu.team.domain;

public class Designer extends Programmer {
	private double bonus;
	
	public Designer() {}
	public Designer(int id, String name, int age, double salary, Equipment equipment, double bonus) {
		super(id, name, age, salary, equipment);
		this.bonus = bonus;
	}
	public double getBonus() {
		return bonus;
	}
	public void setBonus(double bonus) {
		this.bonus = bonus;
	}
	
	@Override
	public String getMemberDetails() {
		return getMemberId() + "/" + getDetails();
	}
	
	@Override
	public String getDetailsForTeam() {
		return getMemberDetails() + "\t設計師";
	}
	
	@Override
	public String toString() {
		return getDetails() + "\t設計師\t" + this.getStatus() + "\t" + bonus + "\t\t" + this.getEquipment().getDescription();  
	}
}
