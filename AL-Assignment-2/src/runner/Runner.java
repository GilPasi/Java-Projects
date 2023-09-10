package runner;

import java.util.LinkedList;

import  answers.*;
public class Runner {
	public static void main ( String [] args ) {
		//Task 1 - space-vehicle drops
//		System.out.println("=============Task 1 - space-vehicle drops==================");
//		SpaceVehicleDrop.heightSequence(100, 3 , 90);
		SpaceVehicleDrop.leastTries(4, 100);
		
		
		//Task 3 - grid beasts
		int[][][] grid = {
				
				{{1,2,3,4},
				{4,5,6,5},
				{1,2,3,6},
				{1,2,3,6}},
				
				{{1,2,3,4},
				{4,5,6,5},
				{16,17,18,6},
				{16,17,18,6}},
					
				{{1,2,3,7},
				{22,5,6,5},
				{1,2,3,4},
				{25,26,27,9},},
				
				{{4,5,6,7},
				{22,5,6,5},
				{5,2,3,4},
				{25,26,27,9},}
		};	
		int [][][] grid2 = {
				{{1,1,3},
				{4,5,6},
				{7,8,9}},
				
				{{1,1,3},
				{4,5,6},
				{7,8,9}},
				
				{{1,2,3},
				{4,3,2},
				{7,8,5}},
		};
//		System.out.println("==============Task 3 - grid beasts==================");
//		GridBeasts.print3D(grid2);
//		GridBeasts.findAllBeasts(2, grid2);

		GraphToBinaryTree.initGraph();
		GraphToBinaryTree.printGraph();
		GraphToBinaryTree.toBinaryTree();
		System.out.println();
		GraphToBinaryTree.printGraph();
			
	}
}
    

