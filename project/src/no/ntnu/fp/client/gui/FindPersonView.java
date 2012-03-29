package no.ntnu.fp.client.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import no.ntnu.fp.client.gui.objects.AutoCompleteSearchField;
import no.ntnu.fp.common.model.Employee;

public class FindPersonView extends MainView {
	
	private AutoCompleteSearchField<Employee> searchField;
	private GridBagConstraints gbc;
	
	public FindPersonView() {
		super.remove(super.createEventBtn);
		super.remove(super.findPersonBtn);
		super.remove(super.signOutBtn);
		super.remove(super.notificationPanel);
		
		initPanel();
	}
	
	private void initPanel() {
		gbc = new GridBagConstraints();
		JLabel label = new JLabel("Search for employee:");
		label.setFont(GuiConstants.NOTIFICATIONPANE_FONT);
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.gridwidth = 2;
		gbc.gridheight = 2;
		add(label, gbc);
		
		setSearchField(new AutoCompleteSearchField<Employee>(Employee.getAllEmployees(), 8));
		getSearchField().setPreferredSize(new Dimension(150, 30));
		getSearchField().setSize(new Dimension(200, 30));
		getSearchField().setMinimumSize(new Dimension(150, 30));
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		add(getSearchField(), gbc);
	}

	public AutoCompleteSearchField<Employee> getSearchField() {
		return searchField;
	}

	public void setSearchField(AutoCompleteSearchField<Employee> searchField) {
		this.searchField = searchField;
	}

}
