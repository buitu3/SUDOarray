package at.sudoarray;

import java.util.ArrayList;

public class SudoBtn {
	
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int value;
	private int mRowIndex,mColIndex;
	private boolean mHideAble;
	ArrayList<Integer> Tried_ARR = new ArrayList<Integer>();
	
	// ===========================================================
	// Constructors
	// ===========================================================

	public SudoBtn(){
		this.value = 0;
		this.mHideAble = true;
	}
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getRowIndex() {
		return mRowIndex;
	}
	public void setRowIndex(int mRowIndex) {
		this.mRowIndex = mRowIndex;
	}
	public int getColIndex() {
		return mColIndex;
	}
	public void setColIndex(int mColIndex) {
		this.mColIndex = mColIndex;
	}
	public boolean isHideAble() {
		return mHideAble;
	}
	public void setHideAble(boolean mHideAble) {
		this.mHideAble = mHideAble;
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================


}
