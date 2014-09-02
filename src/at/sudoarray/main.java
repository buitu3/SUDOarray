package at.sudoarray;

import java.util.ArrayList;;

public class main {

	// ===========================================================
	// Constants
	// ===========================================================
	
	private static final int NUMBER_OF_ROWS = 9;
	private static final int NUMBER_OF_COLUMNS = 9;
	private static final int NUMBER_OF_UNITS = NUMBER_OF_ROWS * NUMBER_OF_COLUMNS;
	
	// SUDOKU value Array
	private static final SudoBtn[][] Sudo_ARR = new SudoBtn[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
	
	// Hidden SUDOKU Array
	private static final SudoBtn[][] hiddenSudo_ARR = new SudoBtn[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
	
	// Test unique SUDOKU Array
	private static final SudoBtn[][] testUniqueSudo_ARR = new SudoBtn[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS]; 
	
	// ===========================================================
	// Fields
	// ===========================================================
	
	private static int hideAmount = 60;
	
	private static double resetCounter = 0;
	
	private static boolean isUnique = true; 
	
	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	// ===========================================================
	// Methods
	// ===========================================================


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		initSudo_ARR();
		// init a Sudo array for unique testing
		initTestUniqueSudo_ARR();
		// copy Sudo_ARR units value to hiddenSudo_ARR
		copySudo_ARRValue();
		// hide random units from hiddenSudo_ARR
		hideHiddenSudo_ARRValue();
		
		ArrayList<SudoBtn> Test_ARR = new ArrayList<SudoBtn>();
		Test_ARR.add(Sudo_ARR[1][1]);
		System.out.println(Test_ARR.get(0).getValue());

		for (int Row = 0;Row < NUMBER_OF_ROWS;Row++){
			for (int Col = 0;Col < NUMBER_OF_COLUMNS;Col++){
				if (hiddenSudo_ARR[Row][Col].getValue() == 0){
					System.out.print(".");
				}else{
					System.out.print(hiddenSudo_ARR[Row][Col].getValue());
				}
			}
			//System.out.print("\n");
		}
		
	}
	
	private static void initSudo_ARR(){
		for (int Row = 0;Row < NUMBER_OF_ROWS;Row++){
			for (int Col = 0;Col < NUMBER_OF_COLUMNS;Col++){
				// Init a SUDOKU unit
				Sudo_ARR[Row][Col] = new SudoBtn();
				Sudo_ARR[Row][Col].setRowIndex(Row);
				Sudo_ARR[Row][Col].setColIndex(Col);
				
				// List of numbers that taken by previous units
				ArrayList<Integer> Check_ARR = new ArrayList<Integer>();
				// List of available numbers to be chosen randomly
				ArrayList<Integer> Random_ARR = new ArrayList<Integer>();
				
				// Scan previous units's value and add to Check_ARR
				for (int SubRow = 0;SubRow < Row;SubRow++){
					Check_ARR.add(Sudo_ARR[SubRow][Col].getValue());
				}
				
				for (int SubCol = 0;SubCol < Col;SubCol++){
					int count = 0;
					for (int n = 0;n < Check_ARR.size();n++){
						if (Sudo_ARR[Row][SubCol].getValue() != (int)Check_ARR.get(n))
							count++;
						else
							break;
					}
					if (count == Check_ARR.size()){
						Check_ARR.add(Sudo_ARR[Row][SubCol].getValue());
					}
				}
				/*
				for (int tried = 0;tried < Sudo_ARR[Row][Col].Tried_ARR.size();tried++){
					int count = 0;
					for (int n = 0;n < Check_ARR.size();n++){
						if (Sudo_ARR[Row][Col].Tried_ARR.get(tried) != (int)Check_ARR.get(n))
							count++;
						else
							break;
					}
					if (count == Check_ARR.size()){
						Check_ARR.add(Sudo_ARR[Row][Col].Tried_ARR.get(tried));
					}
				}
				*/
				switch (Row%3){
				case 1:
					switch (Col%3){
					case 0:
						checkUnit(Sudo_ARR[Row - 1][Col + 1],Check_ARR);
						checkUnit(Sudo_ARR[Row - 1][Col + 2],Check_ARR);
						break;
					case 1:
						checkUnit(Sudo_ARR[Row - 1][Col - 1],Check_ARR);
						checkUnit(Sudo_ARR[Row - 1][Col + 1],Check_ARR);
						break;
					case 2:
						checkUnit(Sudo_ARR[Row - 1][Col - 2],Check_ARR);
						checkUnit(Sudo_ARR[Row - 1][Col - 1],Check_ARR);
						break;
					}
					break;
				case 2:
					switch (Col%3){
					case 0:
						checkUnit(Sudo_ARR[Row - 2][Col + 1],Check_ARR);
						checkUnit(Sudo_ARR[Row - 2][Col + 2],Check_ARR);
						checkUnit(Sudo_ARR[Row - 1][Col + 1],Check_ARR);
						checkUnit(Sudo_ARR[Row - 1][Col + 2],Check_ARR);
						break;
					case 1:
						checkUnit(Sudo_ARR[Row - 2][Col - 1],Check_ARR);
						checkUnit(Sudo_ARR[Row - 2][Col + 1],Check_ARR);
						checkUnit(Sudo_ARR[Row - 1][Col - 1],Check_ARR);
						checkUnit(Sudo_ARR[Row - 1][Col + 1],Check_ARR);
						break;
					case 2:
						checkUnit(Sudo_ARR[Row - 2][Col - 2],Check_ARR);
						checkUnit(Sudo_ARR[Row - 2][Col - 1],Check_ARR);
						checkUnit(Sudo_ARR[Row - 1][Col - 2],Check_ARR);
						checkUnit(Sudo_ARR[Row - 1][Col - 1],Check_ARR);
						break;
					}
					break;
				}
				
				// add available values to Random_ARR
				for (int j = 1;j < 10;j++){
					int count = 0;
					for (int n = 0;n < Check_ARR.size();n++){
						if(j != (int)Check_ARR.get(n))
							count++;
						else 
							break;
					}
					if (count == Check_ARR.size()){
						Random_ARR.add(j);
					}
				}
				
				// backtrack if there are no available value
				if(Random_ARR.size() == 0){
					if(Col == 0){
						//Sudo_ARR[Row][Col].Tried_ARR.clear();
						//Row = Row - 1;
						//Col = 8;
						Row = 0;
						Col = 0;
					}
					else {
						//Sudo_ARR[Row][Col].Tried_ARR.clear();
						//Col = Col - 1;
						Row = 0;
						Col = 0;
					}	
				}
				// choose a random value from Random_ARR
				else{
					//Sudo_ARR[Row][Col].value = (int) Random_ARR.get((int) (Math.random()*Random_ARR.size()));
					int number = (int) (Random_ARR.get((int)(Math.random()*Random_ARR.size())));
					Sudo_ARR[Row][Col].setValue(number);
					Sudo_ARR[Row][Col].Tried_ARR.add(number);
				}
				Check_ARR.clear();
				Random_ARR.clear();
			}
		}
		
	}
	
	private static void initTestUniqueSudo_ARR(){
		for (int Row = 0;Row < NUMBER_OF_ROWS;Row++){
			for (int Col = 0;Col < NUMBER_OF_COLUMNS;Col++){
				testUniqueSudo_ARR[Row][Col] = new SudoBtn();
			}
		}
	}
	private static void checkUnit(SudoBtn SudoBtn,ArrayList<Integer> Check_ARR){
		int count = 0;
		for (int n = 0;n < Check_ARR.size();n++){
			if (SudoBtn.getValue() != (int)Check_ARR.get(n))
				count++;
			else
				break;
		}
		if (count == Check_ARR.size()){
			Check_ARR.add(SudoBtn.getValue());
		}
	}
	
	private static void copySudo_ARRValue(){
		for (int Row = 0;Row < NUMBER_OF_ROWS;Row++){
			for (int Col = 0;Col < NUMBER_OF_COLUMNS;Col++){
				hiddenSudo_ARR[Row][Col] = new SudoBtn();
				hiddenSudo_ARR[Row][Col].setRowIndex(Row);
				hiddenSudo_ARR[Row][Col].setColIndex(Col);
				hiddenSudo_ARR[Row][Col].setValue(Sudo_ARR[Row][Col].getValue());
			}
		}
	}
	
	private static void copyTestUniqueSudo_ARRValue(){
		for (int Row = 0;Row < NUMBER_OF_ROWS;Row++){
			for (int Col = 0;Col < NUMBER_OF_COLUMNS;Col++){
				//testUniqueSudo_ARR[Row][Col] = new SudoBtn();
				testUniqueSudo_ARR[Row][Col].setValue(hiddenSudo_ARR[Row][Col].getValue());
			}
		}
	}
	
	private static void checkUnitSolution(SudoBtn SudoBtn,ArrayList<Integer> Check_ARR){
		int count = 0;
		if (SudoBtn.getValue() != 0){
			for (int n = 0;n < Check_ARR.size();n++){
				if (SudoBtn.getValue() != (int)Check_ARR.get(n))
					count++;
				else
					break;
			}
		}
		if (count == Check_ARR.size()){
			Check_ARR.add(SudoBtn.getValue());
		}
	}
	
	private static void hideHiddenSudo_ARRValue(){
		ArrayList<SudoBtn> hiddenUnitList = new ArrayList<SudoBtn>();
		for (int i = 0;i < hideAmount;i++){
			ArrayList<SudoBtn> randomUnitList = new ArrayList<SudoBtn>();
			
			for (int Row = 0;Row < NUMBER_OF_ROWS;Row++){
				for (int Col = 0;Col < NUMBER_OF_COLUMNS;Col++){
					if (hiddenSudo_ARR[Row][Col].getValue() != 0 && hiddenSudo_ARR[Row][Col].isHideAble()){
						randomUnitList.add(hiddenSudo_ARR[Row][Col]);
					}
				}
			}
			
			if (randomUnitList.size() == 0){
				int mRowIndex = hiddenUnitList.get(hiddenUnitList.size() - 1).getRowIndex();
				int mColIndex = hiddenUnitList.get(hiddenUnitList.size() - 1).getColIndex();
				hiddenSudo_ARR[mRowIndex][mColIndex].setValue(Sudo_ARR[mRowIndex][mColIndex].getValue());
				for (int Row = 0;Row < NUMBER_OF_ROWS;Row++){
					for (int Col = 0;Col < NUMBER_OF_COLUMNS;Col++){
						if (hiddenSudo_ARR[Row][Col].getValue() != 0 && !hiddenSudo_ARR[Row][Col].isHideAble()){
							hiddenSudo_ARR[Row][Col].setHideAble(true);
						}
					}
				}
				hiddenSudo_ARR[mRowIndex][mColIndex].setHideAble(false);
				hiddenUnitList.remove(hiddenUnitList.size() - 1);
				i--;
				continue;
			}
			
			int randUnit = (int)(Math.random() * randomUnitList.size());
			randomUnitList.get(randUnit).setValue(0);
			copyTestUniqueSudo_ARRValue();
			checkSolution();
			
			if (!isUnique){
				int mRowIndex = randomUnitList.get(randUnit).getRowIndex();
				int mColIndex = randomUnitList.get(randUnit).getColIndex();
				randomUnitList.get(randUnit).setValue(Sudo_ARR[mRowIndex][mColIndex].getValue());
				randomUnitList.get(randUnit).setHideAble(false);
				isUnique = true;
				i--;
				continue;
			}
			if (isUnique){
				hiddenUnitList.add(randomUnitList.get(randUnit));
				for (int Row = 0;Row < NUMBER_OF_ROWS;Row++){
					for (int Col = 0;Col < NUMBER_OF_COLUMNS;Col++){
						if (hiddenSudo_ARR[Row][Col].getValue() != 0 && !hiddenSudo_ARR[Row][Col].isHideAble()){
							hiddenSudo_ARR[Row][Col].setHideAble(true);
						}
					}
				}
			}
			
		}	
		/*
		for (int i = 0;i < hideAmount;i++){
			int randRow = (int)(Math.random() * 9);
			int randCol = (int)(Math.random() * 9);
			if (hiddenSudo_ARR[randRow][randCol].getValue() != 0){
				hiddenSudo_ARR[randRow][randCol].setValue(0);
				copyTestUniqueSudo_ARRValue();
				checkSolution();
				if (!isUnique){
					hiddenSudo_ARR[randRow][randCol].setValue(Sudo_ARR[randRow][randCol].getValue());
					isUnique = true;
					i--;
				}
			}
			else{
				i--;						
			}
		}
		*/
	}
	
	private static void checkSolution(){
		for (int Row = 0;Row < NUMBER_OF_ROWS;Row++){
			for (int Col = 0;Col < NUMBER_OF_COLUMNS;Col++){
				if(testUniqueSudo_ARR[Row][Col].getValue() == 0){
					ArrayList<Integer> Check_ARR = new ArrayList<Integer>();
					ArrayList<Integer> Solution_ARR = new ArrayList<Integer>();
					
					for (int SubRow = 0;SubRow < NUMBER_OF_ROWS;SubRow++){
						if (testUniqueSudo_ARR[SubRow][Col].getValue() != 0){
							Check_ARR.add(testUniqueSudo_ARR[SubRow][Col].getValue());
						}
					}
					
					for (int SubCol = 0;SubCol < NUMBER_OF_COLUMNS;SubCol++){
						int count = 0;
						if (testUniqueSudo_ARR[Row][SubCol].getValue() != 0){
							for (int n = 0;n < Check_ARR.size();n++){
								if (testUniqueSudo_ARR[Row][SubCol].getValue() != (int)Check_ARR.get(n))
									count++;
								else
									break;
							}
						}
						
						if (count == Check_ARR.size()){
							Check_ARR.add(testUniqueSudo_ARR[Row][SubCol].getValue());
						}
					}
					
					switch (Row%3){
					case 0:
						switch (Col%3){
						case 0:
							checkUnitSolution(testUniqueSudo_ARR[Row + 1][Col + 1],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row + 1][Col + 2],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row + 2][Col + 1],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row + 2][Col + 2],Check_ARR);
							break;
						case 1:
							checkUnitSolution(testUniqueSudo_ARR[Row + 1][Col - 1],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row + 1][Col + 1],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row + 2][Col - 1],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row + 2][Col + 1],Check_ARR);
							break;
						case 2:
							checkUnitSolution(testUniqueSudo_ARR[Row + 1][Col - 2],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row + 1][Col - 1],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row + 2][Col - 2],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row + 2][Col - 1],Check_ARR);
							break;
						}
						break;
					case 1:
						switch (Col%3){
						case 0:
							checkUnitSolution(testUniqueSudo_ARR[Row - 1][Col + 1],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row - 1][Col + 2],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row + 1][Col + 1],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row + 1][Col + 2],Check_ARR);
							break;
						case 1:
							checkUnitSolution(testUniqueSudo_ARR[Row - 1][Col - 1],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row - 1][Col + 1],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row + 1][Col - 1],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row + 1][Col + 1],Check_ARR);
							break;
						case 2:
							checkUnitSolution(testUniqueSudo_ARR[Row - 1][Col - 2],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row - 1][Col - 1],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row + 1][Col - 2],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row + 1][Col - 1],Check_ARR);
							break;
						}
						break;
					case 2:
						switch (Col%3){
						case 0:
							checkUnitSolution(testUniqueSudo_ARR[Row - 2][Col + 1],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row - 2][Col + 2],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row - 1][Col + 1],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row - 1][Col + 2],Check_ARR);
							break;
						case 1:
							checkUnitSolution(testUniqueSudo_ARR[Row - 2][Col - 1],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row - 2][Col + 1],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row - 1][Col - 1],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row - 1][Col + 1],Check_ARR);
							break;
						case 2:
							checkUnitSolution(testUniqueSudo_ARR[Row - 2][Col - 2],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row - 2][Col - 1],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row - 1][Col - 2],Check_ARR);
							checkUnitSolution(testUniqueSudo_ARR[Row - 1][Col - 1],Check_ARR);
							break;
						}
						break;
					}
					
					for (int j = 1;j < 10;j++){
						int count = 0;
						for (int n = 0;n < Check_ARR.size();n++){
							if(j != (int)Check_ARR.get(n))
								count++;
							else 
								break;
						}
						if (count == Check_ARR.size()){
							Solution_ARR.add(j);
						}
					}
					
					if (Solution_ARR.size() == 1){ 
						testUniqueSudo_ARR[Row][Col].setValue(Solution_ARR.get(0));
						checkSolution();
					}
					//System.out.println(Check_ARR.size());
					//Check_ARR.clear();
					//Solution_ARR.clear();
				}
			}
		}
		checkUnique();
	}

	private static void checkUnique(){
		//int unitCounter = 0;
		for (int Row = 0;Row < NUMBER_OF_ROWS;Row++){
			for (int Col = 0;Col < NUMBER_OF_COLUMNS;Col++){
				if (testUniqueSudo_ARR[Row][Col].getValue() == 0){
					//unitCounter++;
					isUnique = false;
				}
			}
		}
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}
