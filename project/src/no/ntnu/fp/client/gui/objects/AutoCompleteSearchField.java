package no.ntnu.fp.client.gui.objects;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionListener;
import no.ntnu.fp.client.gui.GuiConstants;

public class AutoCompleteSearchField<E extends Comparable> extends JTextField implements KeyListener {
	
	public static final int STD_ROW_HEIGHT = 20;
	
	private SortedSet<E> set = new TreeSet<E>();
	private DefaultListModel model = new DefaultListModel();
	private JList results = new JList(model);
	private Iterator<E> it;
	private int numDisplayableResults;
	private JPopupMenu resultPanel;
	private String srchStr = "";
	private int x = 0, y = STD_ROW_HEIGHT+5;
	
	static class LOL implements Comparable<LOL>{
		public String toString() {
			String a = "abcdefghijklmnopqrstuvwxyzæøå";
			String s = "";
			for(int i = 0; i < 40; i++) {
				s += a.charAt((int)(Math.random()*a.length()));
			}
			return s;
		}
		public int compareTo(LOL o) {
			return this.toString().compareTo(o.toString());
		}
	}
	
	public AutoCompleteSearchField(int columns, int visibleResults) {
		this(new ArrayList<E>(), visibleResults);
		setColumns(columns);
	}
	
	public AutoCompleteSearchField(ArrayList<E> list, int visibleResults) {
		set.addAll(list);
		it = set.iterator();
		this.numDisplayableResults = visibleResults;
		results.setVisibleRowCount(this.numDisplayableResults);
		results.setBackground(GuiConstants.SWING_FRAME_GRAY);
		resultPanel = new JPopupMenu();
		resultPanel.setPreferredSize(new Dimension(190, numDisplayableResults*STD_ROW_HEIGHT+4));
		results.setPreferredSize(resultPanel.getPreferredSize());
		resultPanel.add(results);
		resultPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
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
			if((obj.toString().toLowerCase()).contains(search.toLowerCase())) 
				model.addElement(obj);
		}
		it = set.iterator();
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
		if(Character.isLetter(c))
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
	
	public E getElement(int index) {
		return (E)model.get(index);
	}
	
	public E removeElementAt(int index) {
        E e = (E)model.get(index);
        set.remove(e);
        model.remove(index);
        results.updateUI();
        it = set.iterator();
        return e;
    }
    public E removeElement(E e) {
        set.remove(e);
        int index = model.indexOf(e);
        if(index >= 0) model.remove(index);
        results.updateUI();
        it = set.iterator();
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
		return "SearchFieldSet"  + set;
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