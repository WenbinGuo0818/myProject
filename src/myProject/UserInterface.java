package myProject;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class UserInterface extends JFrame{
	private GraphViz gViz;
	private Permutation per;
	private ArrayList<String[]> ulist = new ArrayList<String[]>();
	private ArrayList<String[]> slist = new ArrayList<String[]>();
	private int registerNum;
	private Container c;
	private String startState;
	private ArrayList<String> finalStates = new ArrayList<String>();
	private String input;
	private JTextArea resultarea = new JTextArea();
	private JLabel jgraph;
	private ArrayList<String[]> state;
	private int emptygraph;
	
	public UserInterface(){
		emptygraph = 0;
		gViz=new GraphViz("C:\\Users\\guowenbin\\Desktop\\myProject\\bin\\myProject", "E:\\Java\\bin\\dot.exe");
		state = new ArrayList<String[]>();
		setTitle("Register Automaton");
		setBounds(100, 10, 800, 740);
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		c = getContentPane();
		c.setLayout(null);	
		jgraph = new JLabel();
		jgraph.setBounds(250, 30, 500, 400);
		jgraph.setFont(new Font("Times New Roman", Font.BOLD, 16));
		jgraph.setForeground(Color.BLUE);
		ulist_layout();
		slist_layout();
		startState_layout();
		finalState_layout();
		registerNum_layout();
		input_layout();
		results_layout();
		get_graph();
		setVisible(true);
	}
	
	void graphic_layout(){
		
    	do{
    		
    	}while(registerNum == 0);

    	gViz.start_graph();
    	gViz.addln("rankdir=LR;");
    	gViz.addln("size=\"5,5\";");
    	gViz.addln("node [color=lightblue2, style=filled];");
   
    	for (int m = 0; m < ulist.size(); m++) {
			for (int n = 0; n <slist.size(); n++) {
				if (ulist.get(m)[0].equals(slist.get(n)[0])) {
					String[] str = {slist.get(n)[0], slist.get(n)[1], 
							ulist.get(m)[1], "#", "#"};
					state.add(str);
				}
			}
			for (int p = 0; p <slist.size(); p++) {
				if (ulist.get(m)[2].equals(slist.get(p)[0])) {

					state.get(m)[3] = slist.get(p)[0];
					state.get(m)[4] = slist.get(p)[1];
				}
			}
		}
    	for (int i = 0; i < ulist.size(); i++) {
    		String s1 = ("\""+state.get(i)[0]+','+state.get(i)[1]+"\"");
    		String s2 = ("\""+state.get(i)[3]+','+state.get(i)[4]+"\"");
    		String s3 = state.get(i)[2];
    		for(String str1:finalStates){
    			
    			if (str1.equals(state.get(i)[0])) {
    				
    				gViz.addln("node [shape = doublecircle];"+s1+";");
    				gViz.addln("node [shape = circle];");
				}
    			if (str1.equals(state.get(i)[3])) {
   
    				gViz.addln("node [shape = doublecircle];"+s2+";");	
    				gViz.addln("node [shape = circle];");
				}
    		}
    		gViz.addln(s1+"->"+s2+"[label="+s3+"];");

		}
    	
    	for (int k = 0; k < state.size(); k++) {
			if (startState.equals(state.get(k)[0])) {
				String s3 = ("\""+state.get(k)[0]+','+state.get(k)[1]+"\"");
				gViz.addln("start->"+s3+";");
				gViz.addln("start [shape=Mdiamond style=dashed];");
				break;
			}
		}
      gViz.end_graph();
      try {
          gViz.run();
      } catch (Exception e) {
          e.printStackTrace();
      }		
	}
	
	void get_graph(){
		JButton b5 = new JButton("Show State diagram (double click)");
		b5.setBounds(350, 10, 300, 30);
		b5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				URL url;
				if (emptygraph == 5) {
					url = getClass().getResource("stateGraph.png");
				}
				else{
					url = getClass().getResource("empty.png");
				}
				System.out.println(url);	
				Icon icon = new ImageIcon(url);
				jgraph.setIcon(icon);			
				c.add(jgraph);
		
			}
		});
		c.add(b5);
	}
	
	void ulist_layout() {
		
		JLabel l = new JLabel("Transitions");
		l.setBounds(10, 10, 80, 20);
		l.setFont(new Font("Times New Roman", Font.BOLD, 16));
		l.setForeground(Color.BLUE);
		c.add(l);
		
		final JTextArea area = new JTextArea();	
		area.setText("q0,0,q0"+"\n"+"q0,0,q"+"\n"+"q,0,f"+"\n"+"q,1,q"+"\n"+"f,1,f"+"\n"+"f,0,f");
		area.setRows(5);
		area.setColumns(20);

		area.setFont(new Font("Times New Roman", Font.BOLD, 16));
		JScrollPane sp = new JScrollPane(area);
		sp.setBounds(10, 40, 160, 100);
		c.add(sp);
			
		JButton b = new JButton("Confirm");
		b.setBounds(40, 150, 100, 20);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				
				String[] strs = area.getText().split("\n");	
				String word[] = new String[3];
                for (int i = 0; i < strs.length; i++) {
                	word = strs[i].split(",");
			
					ulist.add(word);
				}

				area.requestFocus();//set the cursor
				emptygraph ++;
			}
		});
		c.add(b);
		
	}
	
	void slist_layout(){
		JLabel l1 = new JLabel("States");
		l1.setBounds(10, 180, 80, 20);
		l1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		l1.setForeground(Color.BLUE);
		c.add(l1);
		
		final JTextArea area1 = new JTextArea();	
		area1.setText("q0,0"+"\n"+"q,1"+"\n"+"f,1");
		area1.setRows(5);
		area1.setColumns(20);

		area1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		JScrollPane sp1 = new JScrollPane(area1);
		sp1.setBounds(10, 210, 160, 80);
		c.add(sp1);
			
		JButton b1 = new JButton("Confirm");
		b1.setBounds(40, 300, 100, 20);
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				slist.clear();
				String[] strs1 = area1.getText().split("\n");	
				String word1[] = new String[2];
                for (int j = 0; j < strs1.length; j++) {
                	word1 = strs1[j].split(",");
		
					slist.add(word1);
				}

				area1.requestFocus();//set the cursor
				emptygraph ++;
			}
		});
		c.add(b1);
	
	}
	

	
	void startState_layout(){
		JLabel l = new JLabel("Start State");
		l.setBounds(10, 340, 80, 20);
		l.setFont(new Font("Times New Roman", Font.BOLD, 16));
		l.setForeground(Color.BLUE);
		c.add(l);
		
		final JTextArea area = new JTextArea();	
		area.setText("q0");
		area.setRows(1);
		area.setColumns(20);

		area.setFont(new Font("Times New Roman", Font.BOLD, 16));
		JScrollPane sp = new JScrollPane(area);
		sp.setBounds(10, 370, 160, 40);
		c.add(sp);
			
		JButton b = new JButton("Confirm");
		b.setBounds(40, 420, 100, 20);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				
				startState = area.getText();	
				
				area.requestFocus();//set the cursor
				emptygraph ++;
			}
		});
		c.add(b);
	}
	
	void finalState_layout() {
		
		JLabel l = new JLabel("Final States");
		l.setBounds(10, 460, 80, 20);
		l.setFont(new Font("Times New Roman", Font.BOLD, 16));
		l.setForeground(Color.BLUE);
		c.add(l);
		
		final JTextArea area = new JTextArea();	
		area.setText("f");
		area.setRows(5);
		area.setColumns(20);

		area.setFont(new Font("Times New Roman", Font.BOLD, 16));
		JScrollPane sp = new JScrollPane(area);
		sp.setBounds(10, 490, 160, 40);
		c.add(sp);
			
		JButton b = new JButton("Confirm");
		b.setBounds(40, 540, 100, 20);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				finalStates.clear();
				String[] word = area.getText().split(",");	
                for (int i = 0; i < word.length; i++) {     
					finalStates.add(word[i]);
				}
				area.requestFocus();//set the cursor
				emptygraph ++;
			}
		});
		c.add(b);
		
	}
	
	void registerNum_layout(){
		JLabel l4 = new JLabel("Register Number");
		l4.setBounds(10, 580, 150, 20);
		l4.setFont(new Font("Times New Roman", Font.BOLD, 16));
		l4.setForeground(Color.BLUE);
		c.add(l4);
		
		final JTextArea area4 = new JTextArea();	
		area4.setText("2");
		area4.setRows(1);
		area4.setColumns(20);

		area4.setFont(new Font("Times New Roman", Font.BOLD, 16));
		JScrollPane sp4 = new JScrollPane(area4);
		sp4.setBounds(10, 610, 160, 40);
		c.add(sp4);
			
		JButton b4 = new JButton("Confirm");
		b4.setBounds(40, 660, 100, 20);
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				registerNum = Integer.parseInt(area4.getText());
				graphic_layout();
				area4.requestFocus();//set the cursor
				emptygraph ++;
			}
		});
		
		c.add(b4);
	}
	

	
	void input_layout(){
		JLabel l = new JLabel("Input word");
		l.setBounds(240, 490, 80, 20);
		l.setFont(new Font("Times New Roman", Font.BOLD, 16));
		l.setForeground(Color.BLUE);
		c.add(l);
		
		final JTextArea area = new JTextArea();	
		area.setText("abcbd");
		area.setRows(1);
		area.setColumns(20);

		area.setFont(new Font("Times New Roman", Font.BOLD, 16));
		JScrollPane sp = new JScrollPane(area);
		sp.setBounds(240, 520, 200, 130);
		c.add(sp);
			
		JButton b = new JButton("Test");
		b.setBounds(240, 660, 60, 20);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				
				input = area.getText();	
		
				area.requestFocus();//set the cursor
			}
		});
		c.add(b);
		JButton b1 = new JButton("Permutation");
		b1.setBounds(330, 660, 110, 20);
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				per = new Permutation(input);
				area.setText(per.get_PermuteWord());
				input = area.getText();	

				area.requestFocus();//set the cursor
			}
		});
		c.add(b1);
	}
	
	void results_layout() {
		JLabel l = new JLabel("Results");
		l.setBounds(460, 490, 200, 20);
		l.setFont(new Font("Times New Roman", Font.BOLD, 16));
		l.setForeground(Color.BLUE);
		c.add(l);
		
			
		resultarea.setText("");
		resultarea.setRows(50);
		resultarea.setColumns(20);

		resultarea.setFont(new Font("Times New Roman", Font.BOLD, 16));
		JScrollPane sp = new JScrollPane(resultarea);
		sp.setBounds(460, 520, 300, 150);
		c.add(sp);
		
	}
	
	void display_result(){
		resultarea.setText("");
		try {    
	        String encoding = "GBK"; 
	        File file = new File("C:\\Users\\guowenbin\\Desktop\\myProject\\results.txt");    
	         if (file.isFile() && file.exists()) {    
	           InputStreamReader read = new InputStreamReader(    
	                    new FileInputStream(file), encoding);    
	            BufferedReader bufferedReader = new BufferedReader(read);    
	            String line = null;  
				while ((line=bufferedReader.readLine())!=null) {
					resultarea.append(line+"\n");
					Thread.sleep(30);
				}   
	             read.close();    
	        }else{    
	            System.out.println("Cannot find this file!");    
	        }    
	    } catch (Exception e) {    
	         System.out.println("error to read this file");    
	        e.printStackTrace();    
	    }
	}
	

	
	
	int get_registerNum(){
		return registerNum;
	}
	
	
	ArrayList<String[]> get_ulist(){
		
		return ulist;
	}
	
	ArrayList<String[]> get_slist(){
		
		return slist;
	}
	
	String get_startState(){
		
		return startState;
	}
	
	ArrayList<String> get_finalStates(){
		
		return finalStates;
	}
	
	String get_input(){

		return input;
	}
		
	public static void main(String[] args) {
		
		UserInterface demo = new UserInterface();
	}
}








