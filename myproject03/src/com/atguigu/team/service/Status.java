package com.atguigu.team.service;

public class Status {
	private final String NAME;   // 當擁有 constructor 去賦值時, 其不用在聲明時就要初始化	
	
	/*
	 * 這裡 constructor 是 private 的，這點與單例(singleton)很相像，
	 * 但這裡有三個實例，實際上應稱作枚舉。(只要是固定的有限個對象都算是枚舉)
	 * 
	 * 構造函數是 private 的，意味著無法於類的外部再創建新的實例(對象)，
	 * 原因是，因為我們只需要 FREE, BUSY, VOCATION 三種狀態，
	 * 因此不需要類的使用者新增其他的實例(對象)
	 */
	
	// 問題1：我有需要一個帶參數的構造器嗎？還是只需要一個無參數的即可？
	// 這種情況意味著我們也必須拋棄屬性 NAME
	// 但是當我們要在確認當前員工的狀態時，就會無法確認！
	// 問題2：即便只有空參且 private 的構造函數，一樣可以調用 Programmer 中的 setStatus 方法耶？
	// 別忘了 FREE, BUSY, VOCATION 本身都是一個 Status 對象(實例)
	// Status 賦值給 Status(setStatus)並沒有甚麼問題，
	// 問題還是在於賦完值後，我們無法辨別三者(FREE, BUSY...)的差異，換句話說依舊無法確認員工的狀態。
	// 因此提供一個帶參數的構造函數，並將參數保存於 NAME 中，即可方便我們將其三者區分出差異
	
	// 總而言之，每個 Status 對象中都用有三個 Status 對象分別是 FREE, BUSY, VOCATION
	// 它們三個都是 static 的，意味著它們三者都將一直保存於內存中，只會在類加載時加載，類卸載時卸載
	// 所以也不會導致無限循環(對象不斷創建對象)
	// 由於每個 Status 都有這三個對象，因此我們透過屬性 NAME 來區分當前 Status 對象的狀態是三者中的哪一個
	// 否則這三個對象(實例)在沒有屬性的情況下，本質上沒有甚麼區別，都只是類的對象罷了，只是擁有不同的名稱(變量名)
	
	
	private Status(String name) {
		this.NAME = name;
	}
	
	public static final Status FREE = new Status("FREE");
	public static final Status BUSY = new Status("BUSY");
	public static final Status VOCATION = new Status("VOCATION");
	
	public String getName() {
		return NAME;
	}
	
	@Override
	public String toString() {
		return NAME;
	}
	
}
