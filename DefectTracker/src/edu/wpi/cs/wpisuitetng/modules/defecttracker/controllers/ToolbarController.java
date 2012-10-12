package edu.wpi.cs.wpisuitetng.modules.defecttracker.controllers;

import java.awt.Component;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.wpi.cs.wpisuitetng.janeway.controllers.local.DefaultToolbarController;
import edu.wpi.cs.wpisuitetng.janeway.views.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.janeway.views.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.views.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.JanewayModule;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.views.MainTabView;


/**
 * Controller for the defect tracker toolbar.
 * Keeps track of the displayed tab in the module's MainTabController and displays the
 * group of controls provided by the displayed components' getGroup method, if it
 * is an instance of IToolbarGroupProvider.
 * If the current tab has no associated toolbar group, no additional group is shown in the toolbar.
 */
public class ToolbarController extends DefaultToolbarController implements ChangeListener {

	private ToolbarGroupView relevantTabGroup;
	
	/**
	 * Control the given DefaultToolbarView based on the state of the tabs in module.mainTabController.
	 * @param toolbarView The toolbar to add/remove groups from
	 * @param module The JanewayModule to get the tab controller from
	 */
	public ToolbarController(DefaultToolbarView toolbarView, JanewayModule module) {
		super(toolbarView);
		module.mainTabController.addChangeListener(this);
	}

	private void setRelevantTabGroup(ToolbarGroupView group) {
		// keep track of only one toolbar group for the active tab
		if(relevantTabGroup != null) {
			setRelevant(relevantTabGroup, false);
		}
		relevantTabGroup = group;
		if(relevantTabGroup != null) {
			setRelevant(relevantTabGroup, true);
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO: there has to be a cleaner way to do this
		if(e.getSource() instanceof MainTabView) {
			MainTabView view = (MainTabView) e.getSource();
			Component selectedComponent = view.getSelectedComponent();
			if(selectedComponent instanceof IToolbarGroupProvider) {
				IToolbarGroupProvider provider = (IToolbarGroupProvider) selectedComponent;
				setRelevantTabGroup(provider.getGroup());
			} else {
				setRelevantTabGroup(null);
			}
		}
	}

}