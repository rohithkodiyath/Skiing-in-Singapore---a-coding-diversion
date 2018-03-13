package mainclass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

import bean.MatrixElement;


public class Driver {
	//Digital form of map
	static int[][] mapMatrix;
	//m and n are numbers of rows and columns of map matrix
	static int m,n;
	//MaxLevels of longestPath
	static int maxLevels=0;
	//Difference Between highest and lowest elevation in the skying path
	static int depth=0;
	//paths with same depth and difference comes here
	static HashSet<LinkedList<MatrixElement>>longestSkiingPaths=new HashSet<LinkedList<MatrixElement>>();
	public static void main(String[] args) {
		//initializing number of columns and rows
		
		m=1000;n=1000;
		mapMatrix=new int[m][n];
		String fileName="matrix.txt";
		//reading into Array
		try {
			//Read matrix points from file
			mapMatrix=formMatrixFileFile(fileName);
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					// looking each element inside matrix and forming skiing path
					MatrixElement currentElement = new MatrixElement(i,j,mapMatrix[i][j]);
					//starting a skiing path from the current location
					LinkedList<MatrixElement> skiingPoints=new LinkedList<MatrixElement>();
					skiingPoints.add(currentElement);
					lookForAdjuscentPoints(currentElement,skiingPoints);	
				}
			}
			System.out.println("Depath "+depth);
			System.out.println("Levels "+maxLevels);
			//skiing path poins are printedBelow
			//if multiple paths are available with same drop and lenth will come here
			for (LinkedList<MatrixElement> skiingPath : longestSkiingPaths) {
				for (MatrixElement matrixElement : skiingPath) {
					System.out.print(matrixElement.getVal()+"->");
				}
				System.out.println("\n");
			}
			
		} catch (IOException e) {
			System.out.println("wrror while Reading file");
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
    //find the adjacent elements from the matrix in north south east and west directions.
	private static void lookForAdjuscentPoints(MatrixElement currentElement,LinkedList<MatrixElement>skiingPath) {
		//Getting adjacent elements in four directions
		MatrixElement north=getNorthElement(m,currentElement.getI(),currentElement.getJ());
		MatrixElement south=getSouthElement(n,currentElement.getI(),currentElement.getJ());
		MatrixElement east=getEastElement(n,currentElement.getI(),currentElement.getJ());
		MatrixElement west=getWestElement(m,currentElement.getI(),currentElement.getJ());
		
		HashSet<MatrixElement> sorroundedElemnts=new HashSet<MatrixElement>(Arrays.asList(north,south,east,west));
		//lowerVerticalElements are adjacent points which are in lower elevation 
		HashSet<MatrixElement>pointsWithLowerElevation=getElementsWithLowerElevation(sorroundedElemnts,currentElement.getVal());
	    if(pointsWithLowerElevation.isEmpty()) {
	    	//here skiing path ends
	    	//find the difference between highest and lowest elevation in the skiing path
	    	int differenceOfHighLowLevel=skiingPath.getFirst().getVal()-skiingPath.getLast().getVal();
	    	if(skiingPath.size()>maxLevels) {
	    		//if number of levels are more replace it with new skiingPath
	    		maxLevels=skiingPath.size();
	    		longestSkiingPaths.clear();
	    		longestSkiingPaths.add(skiingPath);
	    		depth=differenceOfHighLowLevel;
	    		
	    	}else if(skiingPath.size()==maxLevels) {
	    		//if skiing paths have same levels compare the depth
	    		if(depth<differenceOfHighLowLevel) {
	    			longestSkiingPaths.clear();
		    		longestSkiingPaths.add(skiingPath);
		    		//highest Depth
		    		depth=differenceOfHighLowLevel;
	    		}
	    	}
	    }else {
	    	//if lower elevation points surround the current element create new skiing paths adding  these points
	    	for (MatrixElement matrixElement : pointsWithLowerElevation) {
	    		LinkedList<MatrixElement> newSkiingPath=new LinkedList<MatrixElement>(skiingPath);
	    		newSkiingPath.add(matrixElement);
	    		//loop again to find the next elements in the path
	    		lookForAdjuscentPoints(matrixElement,newSkiingPath);
			}
	    }   
	}
	//get pints which are in lower elevation
	private static HashSet<MatrixElement> getElementsWithLowerElevation(HashSet<MatrixElement> sorroundedElemnts, int currentValue) {
		HashSet<MatrixElement> returnValue=new HashSet<MatrixElement>();
		for (MatrixElement matrixElement : sorroundedElemnts) {
			if(matrixElement.getVal()==-1) {
				//no element exist in this direction
				continue;
			}else if(currentValue>matrixElement.getVal()){
				//got a point with lower elevation
				returnValue.add(matrixElement);
			}	
		}
		return returnValue;
	}
	
	private static MatrixElement getWestElement(int n, int i, int j) {
		if(j==0) {
			return new MatrixElement();
		}
		return new MatrixElement(i, j-1, mapMatrix[i][j-1]);
	}
	
	private static MatrixElement getEastElement(int n, int i, int j) {
		if(j==n-1) {
			return new MatrixElement();
		}
		return new MatrixElement(i, j+1, mapMatrix[i][j+1]);	
	}
	private static MatrixElement getSouthElement(int m, int i, int j) {
		if(i==m-1) {
			return new MatrixElement();
		}
		return new MatrixElement(i+1, j, mapMatrix[i+1][j]);
		
	}
	
	private static MatrixElement getNorthElement(int m, int i, int j) {
		if(i==0) {
			return new MatrixElement();
		}
		return new MatrixElement(i-1, j, mapMatrix[i-1][j]);
		
	}
	
    //forming 2d array[Matrix] from the points
	private static int[][] formMatrixFileFile(String fileName) throws Exception {
		int[][] matrix=new int[m][n];
		BufferedReader br;
		try {
			br=new BufferedReader(new FileReader(new File(fileName)));
			String line=null;
			int i=0;
			while((line=br.readLine()) != null) {
				if(line.split(" ").length==1000) {
					int[] rowElements=new int[1000];
					int j=0;
					for (String points : line.split(" ")) {
						rowElements[j]=Integer.parseInt(points);
						j++;
					}
					matrix[i]=rowElements;
					i++;
				}else {
					continue;
				}
			}
			br.close();
		} catch (Exception e) {
			throw e;
		}
		return matrix;
	}
}
