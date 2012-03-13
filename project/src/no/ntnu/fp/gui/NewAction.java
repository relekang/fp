/*
 * Created on Feb 21, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import no.ntnu.fp.model.Workgroup;
import no.ntnu.fp.model.Employee;

/**
 * Implements the action for creating a new group of persons.
 * 
 * @author Thomas Oslash;sterlie
 * 
 * @version $Revision: 1.1 $ - $Date: 2005/02/21 12:53:18 $
 */
public class NewAction extends AbstractAction {

	/**
	 * Parent component.
	 */
	private ProjectPanel projectPanel;
	
	/**
	 * Default constructor.  Initialises member variables.
	 * 
	 * @param projectPanel Parent component.
	 */
	public NewAction(ProjectPanel projectPanel) {
		super();
		this.projectPanel = projectPanel;
	}

	/**
	 * Invoked when an action occurs.
	 * 
	 * @param e The action event.
	 */
	public void actionPerformed(ActionEvent arg0) {
		Workgroup project = new Workgroup();
		project.addPerson(new Employee());
		projectPanel.setModel(new PersonListModel(project, null));
	}

}
