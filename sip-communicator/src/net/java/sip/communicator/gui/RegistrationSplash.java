/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2000 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * Portions of this software are based upon public domain software
 * originally written at the National Center for Supercomputing Applications,
 * University of Illinois, Urbana-Champaign.
 */

package net.java.sip.communicator.gui;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import net.java.sip.communicator.common.*;

//import samples.accessory.StringGridBagLayout;

/**
 * Sample login splash screen
 */
public class RegistrationSplash extends JDialog implements ActionListener{
	String userName = null;
	char[] password = null;
	String mail = null;
	String creditCardNo = null;
	JTextField userNameTextField = null;
	JTextField mailTextField = null;
	JTextField creditCardTextField = null;
	JPasswordField passwordTextField = null;
	String plan = "General";
	/**
	 * Command string for a cancel action (e.g., a button). This string is never
	 * presented to the user and should not be internationalized.
	 */
	private String CMD_CANCEL = "cmd.cancel" /* NOI18N */;

	private String CMD_REGISTER = "cmd.register" /* NOI18N */;

	// Components we need to manipulate after creation
	private JButton registerButton = null;
    private JButton cancelButton = null;

	/**
	 * Creates new form RegistrationSplash
	 */
	public RegistrationSplash(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		pack();
		centerWindow();
	}


	/**
	 * Centers the window on the screen.
	 */
	private void centerWindow() {
		Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit()
				.getScreenSize());
		Point center = new Point((int) screen.getCenterX(),
				(int) screen.getCenterY());
		Point newLocation = new Point(center.x - this.getWidth() / 2, center.y
				- this.getHeight() / 2);
		if (screen.contains(newLocation.x, newLocation.y, this.getWidth(),
				this.getHeight())) {
			this.setLocation(newLocation);
		}
	} // centerWindow()

	/**
	 * 
	 * We use dynamic layout managers, so that layout is dynamic and will adapt
	 * properly to user-customized fonts and localized text. The GridBagLayout
	 * makes it easy to line up components of varying sizes along invisible
	 * vertical and horizontal grid lines. It is important to sketch the layout
	 * of the interface and decide on the grid before writing the layout code.
	 * 
	 * Here we actually use our own subclass of GridBagLayout called
	 * StringGridBagLayout, which allows us to use strings to specify
	 * constraints, rather than having to create GridBagConstraints objects
	 * manually.
	 * 
	 * 
	 * We use the JLabel.setLabelFor() method to connect labels to what they are
	 * labeling. This allows mnemonics to work and assistive to technologies
	 * used by persons with disabilities to provide much more useful information
	 * to the user.
	 */
	private void initComponents() {
		Container contents = getContentPane();
		contents.setLayout(new BorderLayout());

		String title = "Register Panel";

		setTitle(title);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				dialogDone(CMD_CANCEL);
			}
		});

		// Accessibility -- all frames, dialogs, and applets should
		// have a description
		getAccessibleContext()
				.setAccessibleDescription("Registration Splash");

		String authPromptLabelValue  = "Please enter user name and password to use when registering!";

		JLabel splashLabel = new JLabel(authPromptLabelValue);
		splashLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		splashLabel.setHorizontalAlignment(SwingConstants.CENTER);
		splashLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		contents.add(splashLabel, BorderLayout.NORTH);

		JPanel centerPane = new JPanel();
		centerPane.setLayout(new GridBagLayout());

		userNameTextField = new JTextField(); // needed below

		// user name label
		JLabel userNameLabel = new JLabel();
		userNameLabel.setDisplayedMnemonic('U');
		// setLabelFor() allows the mnemonic to work
		userNameLabel.setLabelFor(userNameTextField);

		String userNameLabelValue = Utils
				.getProperty("net.java.sip.communicator.gui.USER_NAME_LABEL");

		if (userNameLabelValue == null)
			userNameLabelValue = "User name";

		int gridy = 0;

		userNameLabel.setText(userNameLabelValue);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = gridy;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(12, 12, 0, 0);
		centerPane.add(userNameLabel, c);

		// user name text
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = gridy++;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.insets = new Insets(12, 7, 0, 11);
		centerPane.add(userNameTextField, c);

		// username example
		if (GuiManager.isThisSipphoneAnywhere) {

			String egValue = Utils
					.getProperty("net.java.sip.communicator.sipphone.USER_NAME_EXAMPLE");

			if (egValue == null)
				egValue = "Example: 1-747-555-1212";

			JLabel userNameExampleLabel = new JLabel();

			userNameExampleLabel.setText(egValue);
			c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = gridy++;
			c.anchor = GridBagConstraints.WEST;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(12, 12, 0, 0);
			centerPane.add(userNameExampleLabel, c);

		}

		passwordTextField = new JPasswordField(); // needed below

		// password label
		JLabel passwordLabel = new JLabel();
		passwordLabel.setDisplayedMnemonic('P');
		passwordLabel.setLabelFor(passwordTextField);
		String pLabelStr = PropertiesDepot
				.getProperty("net.java.sip.communicator.gui.PASSWORD_LABEL");
		passwordLabel.setText(pLabelStr == null ? pLabelStr : "Password");
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = gridy;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(11, 12, 0, 0);

		centerPane.add(passwordLabel, c);

		// password text
		passwordTextField.setEchoChar('\u2022');
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = gridy++;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.insets = new Insets(11, 7, 0, 11);
		centerPane.add(passwordTextField, c);


		mailTextField = new JTextField(); // needed below

		// mail label
		JLabel mailLabel = new JLabel();
		mailLabel.setDisplayedMnemonic('M');
		// setLabelFor() allows the mnemonic to work
		mailLabel.setLabelFor(mailTextField);

		String mailLabelValue; 

		mailLabelValue = "E-mail";

		
		mailLabel.setText(mailLabelValue);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = gridy;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(12, 12, 0, 0);
		centerPane.add(mailLabel, c);

		// mail text
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = gridy++;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.insets = new Insets(12, 7, 0, 11);
		centerPane.add(mailTextField, c);
		
		creditCardTextField = new JTextField();
		
		// credit label
		JLabel cardLabel = new JLabel();
		cardLabel.setDisplayedMnemonic('C');
		// setLabelFor() allows the mnemonic to work
		cardLabel.setLabelFor(creditCardTextField);

		String cardLabelValue = "Credit card No";

		cardLabel.setText(cardLabelValue);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = gridy;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(12, 12, 0, 0);
		centerPane.add(cardLabel, c);

		// credit card text
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = gridy++;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.insets = new Insets(12, 7, 0, 11);
		centerPane.add(creditCardTextField, c);
		
		//Billing plans combo
		
		JLabel plans = new JLabel("Billing Plan");
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = gridy;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(12, 12, 0, 0);
		centerPane.add(plans, c);
				
		String[] planStrings = { "General", "Student", "FreeWeekend" };

		//Create the combo box, select item at index 4.
		//Indices start at 0, so 4 specifies the pig.
		JComboBox plansList = new JComboBox(planStrings);
		plansList.setSelectedIndex(0);
		plansList.addActionListener(this);
				
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = gridy++;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(12, 12, 0, 0);
		centerPane.add(plansList, c);
		
		// Buttons along bottom of window
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, 0));
		// register
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));

		registerButton = new JButton();
		registerButton.setText("Register");
		registerButton.setActionCommand(CMD_REGISTER);
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dialogDone(event);
			}
		});
		buttonPanel.add(registerButton);

		cancelButton = new JButton();
	    cancelButton.setText("Cancel");
	    cancelButton.setActionCommand(CMD_CANCEL);
	    cancelButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent event) {
	    		dialogDone(event);
	        }});
	    buttonPanel.add(cancelButton);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
		c.insets = new Insets(11, 12, 11, 11);

		centerPane.add(buttonPanel, c);

		contents.add(centerPane, BorderLayout.CENTER);
		getRootPane().setDefaultButton(registerButton);

		setFocusTraversalPolicy(new FocusTraversalPol());

	} // initComponents()


	/**
	 * The user has selected an option. Here we close and dispose the dialog. If
	 * actionCommand is an ActionEvent, getCommandString() is called, otherwise
	 * toString() is used to get the action command.
	 * 
	 * @param actionCommand
	 *            may be null
	 */
	private void dialogDone(Object actionCommand) {
		String cmd = null;
		if (actionCommand != null) {
			if (actionCommand instanceof ActionEvent) {
				cmd = ((ActionEvent) actionCommand).getActionCommand();
			} else {
				cmd = actionCommand.toString();
			}
		}
		if (cmd == null) {
			// do nothing
		} else if (cmd.equals(CMD_CANCEL)) {
			userName = null;
			password = null;
		} else if (cmd.equals(CMD_REGISTER)) {
			userName = userNameTextField.getText();
			password = passwordTextField.getPassword();
			mail = mailTextField.getText();
			creditCardNo = creditCardTextField.getText();
		}
		setVisible(false);
		dispose();
	} // dialogDone()

	private class FocusTraversalPol extends LayoutFocusTraversalPolicy {
		public Component getDefaultComponent(Container cont) {
			if (userNameTextField.getText() == null
					|| userNameTextField.getText().trim().length() == 0)
				return super.getFirstComponent(cont);
			else
				return passwordTextField;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox)e.getSource();
		this.plan = (String) cb.getSelectedItem();
		System.out.println(plan);
		
	}
} // class LoginSplash
