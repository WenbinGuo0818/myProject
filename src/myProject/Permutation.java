package myProject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;


public class Permutation {
	private String inputWord;
	private ArrayList<Integer> newSymbol;
	private ArrayList<Integer> symbolIndex;
	
	public Permutation(String word){
		inputWord = word;
		newSymbol = new ArrayList<Integer>();
		symbolIndex = new ArrayList<Integer>();
		getPermuteWord();
	}
	
	void getPermuteWord(){
		if (inputWord == null || inputWord == "") {
			System.out.println("Input String is wrong...");
			return;
		}
		int j = 0;
		int x = 0;
		Set set=new HashSet();
		Set set1=new HashSet();
		Set set2=new HashSet();
		for (int l = 0; l < inputWord.length(); l++) {
			set1.add(inputWord.charAt(l));
		}
		int n = getRandomNumList(1, 1, inputWord.length()+1).get(0);
		symbolIndex = getRandomNumList(n, 0, inputWord.length());
	//	System.out.println(symbolIndex);
		for (int i = 0; i < n; i++) {
			set.add(inputWord.charAt(symbolIndex.get(i)));
		}
		newSymbol = getRandomNumList(set.size(), 33, 127);
		for (int k = 0; k < newSymbol.size(); k++) {
			int y = newSymbol.get(k);
			set2.add((char)y);
		}
	//	System.out.println(set);
        Iterator it = set.iterator();
        while(it.hasNext()){
        	char c = (char) it.next();
        	int m = newSymbol.get(j);
        	if (set1.contains((char)m)) {
				do{
					x = getRandomNumList(1, 33, 127).get(0);
				}while(set1.contains((char)x)||set2.contains((char)x));
				inputWord = inputWord.replace((char)m, (char)x);
	        	inputWord = inputWord.replace(c, (char)m);
	        	inputWord = inputWord.replace((char)x, c);
			}

        	else{
        		inputWord = inputWord.replace(c, (char)m);
        	}
        	
 //       	System.out.println(c+";"+(char)m);
        	j++;       
        }		
	}
	
	ArrayList<Integer> getRandomNumList(int nums,int start,int end){
		ArrayList<Integer> list = new ArrayList<Integer>();
        Random r = new Random();
        while(list.size() != nums){
            int num = r.nextInt(end-start) + start;
            if(!list.contains(num) & num!=35){
                list.add(num);
            }
        }
        return list;
	}
	
	String get_PermuteWord(){
		return inputWord;
	}
	
	void test(){
		String s = "abcbd";
		for (int i = 0; i < 20; i++) {
			Permutation demo = new Permutation(s);
		    s = demo.get_PermuteWord();
			System.out.println(demo.inputWord);
		}
	}

	public static void main(String[] args) {
		Permutation demo1 = new Permutation("..");
		demo1.test();
	}
}


















