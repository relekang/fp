package no.ntnu.fp.client.gui.objects;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import no.ntnu.fp.client.gui.GuiConstants;
import no.ntnu.fp.common.model.Employee;
import no.ntnu.fp.common.model.Employee.Gender;

@SuppressWarnings("serial")
public class AutoCompleteSearchField<E> extends JTextField implements KeyListener {
	
	public static final int STD_ROW_HEIGHT = 20;
	
	private SortedSet<E> set = new TreeSet<E>();
	private DefaultListModel model = new DefaultListModel();
	private JList results = new JList(model);
	private Iterator<E> it;
	private int numDisplayableResults;
	private JPopupMenu resultPanel;
//	private PropertyChangeSupport pcs;
	private String srchStr = "";
	private int x = 0, y = STD_ROW_HEIGHT;
	
	public static void main(String[] args) {
		ArrayList<Employee> l = new ArrayList<Employee>();
		char[] chars = {'v', 'a', 'g', 'q', 'k'};
		for(int i = 0; i < chars.length; i++) {
			l.add(new Employee(""+chars[i]+""+chars[chars.length-1-i]+"andra", "g@mail.com", Calendar.getInstance().getTime(), Gender.MALE));
		}
		AutoCompleteSearchField<Employee> s = new AutoCompleteSearchField<Employee>(l, 5);
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setContentPane(s);
		f.setVisible(true);
		f.setSize(new Dimension(200, 50));
	}
	
	public AutoCompleteSearchField(ArrayList<E> list, int numDisplayedResults) {
//		pcs = new PropertyChangeSupport(this);
		set.addAll(list);
		System.out.println(set);
		it = set.iterator();
		this.numDisplayableResults = numDisplayedResults;
		results.setVisibleRowCount(this.numDisplayableResults);
		results.setBorder(GuiConstants.getEmptyBorder(4));
		results.setBackground(GuiConstants.SWING_FRAME_GRAY);
		resultPanel = new JPopupMenu();
		resultPanel.setPreferredSize(new Dimension(100, numDisplayableResults*STD_ROW_HEIGHT));
		results.setPreferredSize(resultPanel.getPreferredSize());
		resultPanel.add(results);
		results.setCellRenderer(new AutoCompleteCellRenderer());
		addKeyListener(this);
	}
	
	public void setPrefferedPopupWidth(int width) {
		resultPanel.setPreferredSize(new Dimension(width, numDisplayableResults*STD_ROW_HEIGHT));
	}
	
	public void setPrefferedPopupSize(Dimension d) {
		resultPanel.setPreferredSize(d);
	}
	
	private void search(String search) {
		model.clear();
		while(it.hasNext()) {
			E obj =  it.next();
			if((obj.toString()).contains(search))
				model.addElement(obj);
		}
		it = set.iterator();
		results.setVisibleRowCount(model.getSize() > numDisplayableResults ? model.getSize() : numDisplayableResults);
		resultPanel.setPreferredSize(new Dimension(100, results.getVisibleRowCount()*STD_ROW_HEIGHT));
		results.setPreferredSize(resultPanel.getPreferredSize());
		displayResults();
		grabFocus();
	}
	
	private void displayResults() {
		resultPanel.show(this, x, y);
		results.updateUI();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if(c >= 'A' && c <= 'z')
			srchStr += c;
		if(c == KeyEvent.VK_BACK_SPACE)
			srchStr = getText();
		search(srchStr);
	}
	
	public void setPopupOffset(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void addElement(E element) {
		set.add(element);
	}
	
	public int getFirstVisibleIndex() {
		return results.getFirstVisibleIndex();
	}
	
	public int getLastVisibleIndex() {
		return results.getLastVisibleIndex();
	}
	
	public int getSelectedIndex() {
		return results.getSelectedIndex();
	}
	
	public E getELemnt(int index) {
		return (E)model.get(index);
	}
	
	public E removeElementAt(int index) {
		E e = (E)model.get(index);
		set.remove(e);
		results.updateUI();
		return e;
	}
	
	public void addListSelectionListener(ListSelectionListener listener) {
		this.results.addListSelectionListener(listener);
	}
	
	public E getSelectedValue() {
		return (E)results.getSelectedValue();
	}
	
	@Override
	public String toString() {
		return "SearchFieldSet: " + set;
	}
	
	private class AutoCompleteCellRenderer extends DefaultListCellRenderer implements ListCellRenderer {

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			JLabel lab = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			setSelectedTextColor(Color.BLUE);
			setText(((E)value).toString());
			lab.setBackground(GuiConstants.SWING_FRAME_GRAY);
			lab.setSize(100, 30);
			return this;
		}
		
	}

	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}
