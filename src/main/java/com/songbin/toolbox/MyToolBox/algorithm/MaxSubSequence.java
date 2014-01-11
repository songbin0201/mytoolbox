package com.songbin.toolbox.MyToolBox.algorithm;

public class MaxSubSequence {

	public static Long maxsub(Integer[] seq) {
		long maxSum = 0, thisSum = 0;
		for (int j = 0; j < seq.length; j++) {
			thisSum += seq[j];
			if (thisSum > maxSum)
				maxSum = thisSum;
			else if (thisSum < 0)
				thisSum = 0;
		}
		return maxSum;
	}
	
	public static void main(String args[]){
		Integer[] a = {-2,3,-4,23,3,-1,4,5};
		System.out.println(MaxSubSequence.maxsub(a));
	}
}
