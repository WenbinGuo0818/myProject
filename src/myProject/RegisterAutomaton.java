package myProject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import myProject.RegisterAutomaton.Node;


public class RegisterAutomaton {
	
	File file = new File("results.txt");	
	String word;
	Node root;
	ArrayList<Node> container;
	ArrayList<Node> tmp;
	ArrayList<String[]> ulist;
	ArrayList<String[]> slist;
	
	//regarding each state(s, ¦Ø) as a node
    public static class Node{  
        ArrayList<Node> children;
        Node parent;
        String state;
        char [] register;
        
        public Node(String state, int registerNum){  
        	children = new ArrayList<Node>();
        	register = new char[registerNum];
            this.parent = null;
            for (int i = 0; i < registerNum; i++) {
            	register[i] = '#';
			}
            this.state = state;
        }  
    }

	public RegisterAutomaton() {

		container = new ArrayList<Node>();
		tmp = new ArrayList<Node>();

	}
	
	int getState(ArrayList<String[]> slist, String state){
		int x = 0;
		for (int i = 0; i < slist.size(); i++) {
			if (slist.get(i)[0].equals(state)) {
				x = Integer.parseInt(slist.get(i)[1]);
			}
		}
		return x;
	}
		
	
	//execution of ¦Ì for this register automata, and use an ArrayList to store all the possible states
	void transition(Node node, char c, int registerNum, ArrayList<String[]> ulist, ArrayList<String[]> slist){
		int index=0;
		for (int i = 0; i < ulist.size(); i++) {
			if (ulist.get(i)[0].equals(node.state)) {
				int number = Integer.parseInt(ulist.get(i)[1]);
				if (node.register[number] == c) {
					node.children.add(new Node(ulist.get(i)[2], registerNum));
					index = node.children.size()-1;
					for (int j = 0; j < registerNum; j++) {
						node.children.get(index).register[j] = node.register[j];
					}
					node.children.get(index).parent = node;
					tmp.add(node.children.get(index));
				}
			}
		}
		int number = getState(slist, node.state);
		for (int k = 0; k < registerNum; k++) {
			if (node.register[k] == c) {
				return;
			}
		}
		for (int m = 0; m < ulist.size(); m++) {
			if (ulist.get(m)[0].equals(node.state) & Integer.parseInt(ulist.get(m)[1]) == number) {
				node.children.add(new Node(ulist.get(m)[2], registerNum));
				index = node.children.size()-1;
				for (int j = 0; j < registerNum; j++) {
					node.children.get(index).register[j] = node.register[j];
				}
				node.children.get(index).register[number] = c;
				node.children.get(index).parent = node;
				tmp.add(node.children.get(index));
			}
		}
	}
	
	//print all the possible sequences
	void printSequence(String word, ArrayList<Node> container, int registerNum, ArrayList<String> finalStates) throws IOException {
		file.delete();
		file.createNewFile();
		FileWriter fw = new FileWriter("results.txt", true);
		PrintWriter pw = new PrintWriter(fw);
		System.out.println("The possible sequences are");
		pw.println("The possible sequences are");
		pw.flush();
		int cnt = 0;
		Node tmp = new Node(null, registerNum);
		ArrayList<Node> list = new ArrayList<Node>();
		for (int j = 0; j < container.size(); j++) {
			list.clear();
			if (container.get(j).children.size() == 0) {
				tmp = container.get(j);
				list.add(tmp);
				while (tmp.parent != null) {
					tmp = tmp.parent;
					list.add(tmp);
				}
			}
			if (list.size() == word.length()+1) {
				cnt++;
				System.out.println(cnt+"------------");
				pw.println(cnt+"------------");
				pw.flush();
				for(String str: finalStates){
					if (list.get(0).state.equals(str) ) {
						System.out.println("This is an accepting sequence.");
						pw.println("This is an accepting sequence.");
						pw.flush();
					}
				}
				
				for (int i = list.size()-1; i >= 0; i--) {
					System.out.println("("+list.get(i).state+", "+list.get(i).register[0]+list.get(i).register[1]+")");
					pw.println(list.get(i).state+",       "+list.get(i).register[0]+list.get(i).register[1]);
					pw.flush();
				}
			}
		}
		fw.close();
	}
	
	//an example for the register automaton (transitions and states are given)
	void test_example(){
		String temp;
		String [] str;
		ulist = new ArrayList<String[]>() ;
		str = new String[] {"q0","0","q0"};
		ulist.add(str);
		str = new String[] {"q0","0","q"};
		ulist.add(str);
		str = new String[] {"q","0","f"};
		ulist.add(str);
		str = new String[] {"q","1","q"};
		ulist.add(str);
		str = new String[] {"f","0","f"};
		ulist.add(str);
		str = new String[] {"f","1","f"};
		ulist.add(str);
		
		slist = new ArrayList<String[]>() ;
		str = new String[] {"q0","0"};
		slist.add(str);
		str = new String[] {"q","1"};
		slist.add(str);
		str = new String[] {"f","1"};
		slist.add(str);
	}
	
	//test the simulator engine by using the before example
	void test() throws IOException{

		System.out.println("Please enter a word:");
		do{
			word = new Scanner(System.in).nextLine();
		}while(word == null);		
		test_example();
		
	   root = new Node("q0", 2);
	   container.add(root);
	
	    for (int i = 0; i < word.length(); i++) {
	    	tmp.clear();
	    	for (int j = 0; j < container.size(); j++) {    		
	    		if (container.get(j).children.size() == 0) {
		    		transition(container.get(j), word.charAt(i), 2, ulist, slist);
				}    		
			}
			for (int z = 0; z < tmp.size(); z++) {
				container.add(tmp.get(z));
			}
		}
		ArrayList<String> finalstring = new ArrayList<>();
		finalstring.add("f");
	    printSequence(word, container, 2, finalstring);
	}

	
	public static void main(String[] args) throws IOException {
		RegisterAutomaton demo = new RegisterAutomaton();
		demo.test();
	}
	
}
