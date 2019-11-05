package myProject;

import java.util.ArrayList;
import java.util.Scanner;

public class RAutomata {
	
	private String word;
	private Node root;
	private ArrayList<Node> container;
	private ArrayList<Node> tmp;
	
	//regarding each state(s, ¦Ø) as a node
    private class Node{  
        private Node left;  
        private Node right;   
        private Node parent;
        private String state;
        char [] register;
        
        public Node(String state){  
            this.left = null;  
            this.right = null;  
            this.parent = null;
            register = new char[]{'#', '#'};
            this.state = state;
        }  
    }

	public RAutomata() {
		
		root = new Node("q0");
		container = new ArrayList<Node>();
		container.add(root);
		tmp = new ArrayList<Node>();
	}
	
	//execution of ¦Ì for this register automata, and use an ArrayList to store all the possible states
	void transition(Node node, char c){

		if (node.state == "q0") {
			node.left = new Node("q0");
			node.left.parent = node;
			node.left.register[0] = c;
			node.left.register[1] = node.register[1];
			node.right = new Node("q");
			node.right.parent = node;
			node.right.register[0] = c;
			node.right.register[1] = node.register[1];
			tmp.add(node.left);
			tmp.add(node.right);
		}
		else if (node.state == "q") {
			if (node.register[0] == c) {
				node.right = new Node("f");
				node.right.parent = node;
				node.right.register[0] = node.register[0];
				node.right.register[1] = node.register[1];
				tmp.add(node.right);
			}
			else {
				node.left = new Node("q");
				node.left.parent = node;
				node.left.register[0] = node.register[0];
				node.left.register[1] = c;
				tmp.add(node.left);

			}
		}
		else if (node.state == "f") {
			if (node.register[0] == c) {
				node.left = new Node("f");
				node.left.parent = node;
				node.left.register[0] = node.register[0];
				node.left.register[1] = node.register[1];
				tmp.add(node.left);
			}else {
				node.right = new Node("f");
				node.right.parent = node;
				node.right.register[0] = node.register[0];
				node.right.register[1] = c;
				tmp.add(node.right);
			}
		}
		else{
			return;  
		}		
	}
	
	//print all the possible sequences
	void printSequence(String word, ArrayList<Node> container) {
		System.out.println("The possible sequences are");
		int cnt = 0;
		Node tmp = new Node(null);
		ArrayList<Node> list = new ArrayList<Node>();
		for (int j = 0; j < container.size(); j++) {
			list.clear();
			if (container.get(j).left == null & container.get(j).right == null) {
				cnt++;
				System.out.println(cnt+"------------");
				tmp = container.get(j);
				list.add(tmp);
				while (tmp.parent != null) {
					tmp = tmp.parent;
					list.add(tmp);
				}
				if (list.size() == word.length()+1) {
					if (list.get(0).state.equals("f") ) {
						System.out.println("This is an accepting sequence.");
					}
					for (int i = list.size()-1; i >= 0; i--) {
						System.out.println("("+list.get(i).state+", "+list.get(i).register[0]+list.get(i).register[1]+")");
					}
				}
			}
		}
	}

   //scan a string from the keyboard
   String getInput() {
	   System.out.println("Please input a word");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        return s;
   }
	
	public static void main(String[] args) {
		
		RAutomata demo = new RAutomata();
		demo.word = demo.getInput();
	    for (int i = 0; i < demo.word.length(); i++) {
	    	demo.tmp.clear();
	    	for (int j = 0; j < demo.container.size(); j++) {
				if (demo.container.get(j).left == null & demo.container.get(j).right == null) {
					demo.transition(demo.container.get(j), demo.word.charAt(i));
					
				}
			}
			for (int z = 0; z < demo.tmp.size(); z++) {
				demo.container.add(demo.tmp.get(z));
			}

		}
	    demo.printSequence(demo.word, demo.container);
	}
}
