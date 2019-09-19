package mainframe.popup;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import constants.Constants;
import net.miginfocom.swing.MigLayout;

// Export dialog, determine how many of each card to print
public class ExportDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel top;
	private JButton set0, set1, set3, minus, plus;
	
	private JPanel contents;
	private JScrollPane scroll;
	private List<JLabel> labels;
	private List<JSpinner> spins;
	
	private JPanel bottom;
	private JButton okay, cancel;
	
	// Data to be read
	private boolean toPrint;
	public boolean willPrint(){
		return toPrint;
	}
	
	public ExportDialog(JFrame parent, List<String> data){
		super(parent, true);
		toPrint = false;
    	setLayout(new MigLayout("wrap 1, insets 0 0 0 0","[grow,fill]","[][grow,fill][]"));
        setLocationByPlatform(true);
		setTitle("Export");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		initTop();
		initContents(data);
		initBottom();
		
		this.setPreferredSize(new Dimension(320, (int)(parent.getHeight() * 0.7)));
		
		pack();
	}
	
	private void initTop(){
		top = new JPanel();
		top.setLayout(new MigLayout("wrap 5","[][][][][]","[]"));
		
		set0 = new JButton("Set 0");
		set0.setFont(Constants.DEFAULT_FONT);
		set0.addActionListener(new Set0Clicked());
		top.add(set0);
		
		set1 = new JButton("Set 1");
		set1.setFont(Constants.DEFAULT_FONT);
		set1.addActionListener(new Set1Clicked());
		top.add(set1);
		
		set3 = new JButton("Set 3");
		set3.setFont(Constants.DEFAULT_FONT);
		set3.addActionListener(new Set3Clicked());
		top.add(set3);
		
		minus = new JButton("-1");
		minus.setFont(Constants.DEFAULT_FONT);
		minus.addActionListener(new MinusClicked());
		top.add(minus);
		
		plus = new JButton("+1");
		plus.setFont(Constants.DEFAULT_FONT);
		plus.addActionListener(new PlusClicked());
		top.add(plus);
		
		add(top);
	}
	
	private void initContents(List<String> data){
		contents = new JPanel();
		contents.setLayout(new MigLayout("wrap 2","[grow,fill][]",""));
		
		labels = new ArrayList<JLabel>();
		spins = new ArrayList<JSpinner>();
		for(int i = 0; i < data.size(); i++){
			labels.add(new JLabel(data.get(i)));
			labels.get(i).setFont(Constants.DEFAULT_FONT);
			contents.add(labels.get(i));
			spins.add(new JSpinner(new SpinnerNumberModel(0, 0, 999, 1)));
			spins.get(i).setEditor(new JSpinner.DefaultEditor(spins.get(i)));
			spins.get(i).setFont(Constants.DEFAULT_FONT);
			contents.add(spins.get(i),"width 90:90:90");
		}
		
		scroll = new JScrollPane(contents);
		add(scroll);
	}
	
	private void initBottom(){
		bottom = new JPanel();
		bottom.setLayout(new MigLayout("wrap 2","[grow,fill][grow,fill]","[]"));

		cancel = new JButton("Cancel");
		cancel.setFont(Constants.DEFAULT_FONT);
		cancel.addActionListener(new CancelClicked());
		bottom.add(cancel);
		
		okay = new JButton("Export");
		okay.setFont(Constants.DEFAULT_FONT);
		okay.addActionListener(new OkayClicked());
		bottom.add(okay);
		
		add(bottom);
	}
	
	class Set0Clicked implements ActionListener {
		public void actionPerformed(ActionEvent e){
			for(JSpinner i : spins) i.setValue(0);
		}
	}
	
	class Set1Clicked implements ActionListener {
		public void actionPerformed(ActionEvent e){
			for(JSpinner i : spins) i.setValue(1);
		}
	}
	
	class Set3Clicked implements ActionListener {
		public void actionPerformed(ActionEvent e){
			for(JSpinner i : spins) i.setValue(3);
		}
	}
	
	class MinusClicked implements ActionListener {
		public void actionPerformed(ActionEvent e){
			for(JSpinner i : spins) i.setValue((int)i.getValue() - 1);
		}
	}
	
	class PlusClicked implements ActionListener {
		public void actionPerformed(ActionEvent e){
			for(JSpinner i : spins) i.setValue((int)i.getValue() + 1);
		}
	}
	
	class OkayClicked implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			toPrint = true;
			dispose();
		}
	}
	
	class CancelClicked implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	
	public List<Integer> getQuantities(){
		List<Integer> out = new ArrayList<Integer>();
		for(JSpinner i : spins) out.add((Integer)i.getValue());
		return out;
	}
}
