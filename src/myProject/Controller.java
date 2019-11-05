package myProject;

import java.io.IOException;

import myProject.RegisterAutomaton.Node;

public class Controller {
	RegisterAutomaton ra;
	UserInterface ui;
	
	public Controller(){
		ra = new RegisterAutomaton();
		ui = new UserInterface();
	}
	
	public static void main(String[] args) throws IOException {
		String tmp;
		
		Controller demo = new Controller();
		do{
			demo.ra.word = demo.ui.get_input();
		}while(demo.ra.word == null);		
		demo.ra.ulist = demo.ui.get_ulist();
		demo.ra.slist = demo.ui.get_slist();
		do{
		   demo.ra.root = new Node(demo.ui.get_startState(), demo.ui.get_registerNum());
		   demo.ra.container.add(demo.ra.root);
		    for (int i = 0; i < demo.ra.word.length(); i++) {
		    	demo.ra.tmp.clear();
		    	for (int j = 0; j < demo.ra.container.size(); j++) {    		
		    		if (demo.ra.container.get(j).children.size() == 0) {
			    		demo.ra.transition(demo.ra.container.get(j), demo.ra.word.charAt(i), demo.ui.get_registerNum(), demo.ra.ulist, demo.ra.slist);
					}    		
				}
				for (int z = 0; z < demo.ra.tmp.size(); z++) {
					demo.ra.container.add(demo.ra.tmp.get(z));
				}
			}

		    demo.ra.printSequence(demo.ra.word, demo.ra.container, demo.ui.get_registerNum(), demo.ui.get_finalStates());
		    demo.ui.display_result();
	        tmp = demo.ra.word;
	        do{
	        	demo.ra.word = demo.ui.get_input();
	        }while(demo.ra.word.equals(tmp));

	        demo.ra.container.clear();
	        
		}while(true);
	}
}
