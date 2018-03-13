package bean;

public class MatrixElement {
	private int val=-1;
	private int i;
	private int j;
	public int getVal() {
		return val;
	}
	public MatrixElement(int i,int j,int val){
		this.i=i;
		this.j=j;
		this.val=val;
	}
	public MatrixElement(){
		
	}

	public void setVal(int val) {
		this.val = val;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public int getJ() {
		return j;
	}
	public void setJ(int j) {
		this.j = j;
	}
	

}
