package com.communal.thirdservice.oss;

public class T {
	

	public static void main(String[] args) {
		Test test = new Test();
		Test res = doSomething(test);
		System.out.println(test.getNum());
		System.out.println(res.getText());
		System.out.println(test == res);
	}
	
	
	public static Test doSomething(Test t){
		t.setNum(1);
		t.setText("ni hao");
		return t;
	}
	
	public static class Test{
		private int num = 0;
		private String text = "hai";
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		
	}
	
	
	

}
