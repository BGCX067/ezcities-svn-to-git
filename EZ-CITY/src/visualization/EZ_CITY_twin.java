package visualization;

import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Event;
import java.awt.BorderLayout;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.KeyStroke;
import java.awt.Point;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JDialog;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import simulation.obj.Trajectory;
import data.FileIO.ExcelLoader;
import data.FileIO.NetworkLoader;
import data.FileIO.QueryTrajectory;
import data.FileIO.ShapefileLoader;
import draw.simpleRender.RenderMan;
import draw.simpleRender.RenderManSmall;

import javax.swing.BoxLayout;
import javax.swing.JRadioButton;

import javax.swing.JComboBox;
import javax.swing.JTextArea;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class EZ_CITY_twin {

	private JFrame jFrame = null; // @jve:decl-index=0:visual-constraint="10,10"
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;
	private JMenu fileMenu = null;
	private JMenu editMenu = null;
	private JMenu helpMenu = null;
	private JMenuItem exitMenuItem = null;
	private JMenuItem aboutMenuItem = null;
	private JMenuItem cutMenuItem = null;
	private JMenuItem copyMenuItem = null;
	private JMenuItem pasteMenuItem = null;
	private JMenuItem saveMenuItem = null;
	private JMenuItem openMenuItem = null;
	private JDialog aboutDialog = null;
	private JPanel aboutContentPane = null;
	private JLabel aboutVersionLabel = null;
	private JPanel jPanel3d = null;
	private JPanel jPanelsmall3d = null;
	private JSlider jSlider = null;

	// private FPSAnimator animator;
	// boolean isAnimate = false;
	// scene data management
	// database connection
	/**
	 * for DB connection
	 */
	public QueryTrajectory m_db = null; // @jve:decl-index=0:

	/*
	 * for the functionpoints
	 */

	public ExcelLoader m_ex = null;
	/*
	 * DataManage
	 */
	public NetworkLoader m_ld = null;
	private JButton jButtonup;
	private JButton jButtondown;
	private JPanel jPanelslider;
	private JPanel jPanel3;
	private JPanel jPanelbutton;
	private JTabbedPane jTabbedPane1;
	static/*
		 * load models
		 */
	RenderMan m_glr = null;
	static RenderMan m_glr1 = null;
	public ShapefileLoader m_shloader = null;
    public static GManager m_gm = null;


	public String m_modellist = ""; // @jve:decl-index=0:
	private JButton jButton = null;
	private JButton jButton2 = null;
	private JRadioButton jRadioButton1 = null;
	private JRadioButton jRadioButton = null;
	private JRadioButton jRadioButton2 = null;
	private JButton jButtonReset = null;
	private JRadioButton jRadioButton3 = null;
	private JRadioButton jRadioButton4 = null;
	private JFrame jFrame1 = null; // @jve:decl-index=0:visual-constraint="779,120"
	private JPanel jContentPane1 = null;
	private JComboBox jComboBoxRegion = null;
	private JComboBox jComboBoxBus = null;
	private JButton jButtonQuery = null;
	private JComboBox jComboBox = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel11 = null;
	private JLabel jLabel111 = null;
	private JComboBox jComboBox1 = null;
	private JTextArea jTextArea = null;

	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setJMenuBar(getJJMenuBar());
			jFrame.setSize(1383, 682);
			jFrame.setContentPane(getJContentPane());
			jFrame.setTitle("Application");
			jFrame.setPreferredSize(new java.awt.Dimension(1383, 682));
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {

		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBorder(BorderFactory.createCompoundBorder(null,
					null));
			jContentPane.setPreferredSize(new java.awt.Dimension(1368, 571));
			jContentPane.add(getJPanelsmall3d());
			jContentPane.add(getJPanel3d());
			jContentPane.add(getJTabbedPane1());
			jContentPane.add(getJPanelslider());

		}
		return jContentPane;
	}

	/**
	 * This method initializes jJMenuBar
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getFileMenu());
			jJMenuBar.add(getEditMenu());
			jJMenuBar.add(getHelpMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getOpenMenuItem());
			fileMenu.add(getSaveMenuItem());
			fileMenu.add(getExitMenuItem());

		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getEditMenu() {
		if (editMenu == null) {
			editMenu = new JMenu();
			editMenu.setText("Edit");
			editMenu.add(getCutMenuItem());
			editMenu.add(getCopyMenuItem());
			editMenu.add(getPasteMenuItem());
		}
		return editMenu;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("Help");
			helpMenu.add(getAboutMenuItem());
		}
		return helpMenu;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Exit");
			exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exitMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("About");
			aboutMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog aboutDialog = getAboutDialog();
					aboutDialog.pack();
					Point loc = getJFrame().getLocation();
					loc.translate(20, 20);
					aboutDialog.setLocation(loc);
					aboutDialog.setVisible(true);
				}
			});
		}
		return aboutMenuItem;
	}

	/**
	 * This method initializes aboutDialog
	 * 
	 * @return javax.swing.JDialog
	 */
	private JDialog getAboutDialog() {
		if (aboutDialog == null) {
			aboutDialog = new JDialog(getJFrame(), true);
			aboutDialog.setTitle("About");
			aboutDialog.setContentPane(getAboutContentPane());
		}
		return aboutDialog;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getOpenMenuItem() {
		// TODO Auto-generated method stub

		if (openMenuItem == null) {
			openMenuItem = new JMenuItem();
			openMenuItem.setText("Open");
			openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					Event.CTRL_MASK, true));
		}
		/*
		 * Open Dialog
		 */
		openMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser fileopen = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"shape files", "shp");
				fileopen.addChoosableFileFilter(filter);

				int returned = fileopen.showDialog(fileMenu, "Open file");

				if (returned == JFileChooser.APPROVE_OPTION) {
					File file = fileopen.getSelectedFile();
					// Adding the Model to load
					if (m_shloader == null) {
						try {
							// m_shloader = new
							// ShapefileLoader("F://cecile_develpment//workspace//shapefiler//data//",
							// "MP08_LAND_USE_PL_ETH2");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			}
		});
		return openMenuItem;

	}

	public void addModel(String filepath) {
		this.m_modellist = filepath;
	}

	/**
	 * This method initializes aboutContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAboutContentPane() {
		if (aboutContentPane == null) {
			aboutContentPane = new JPanel();
			aboutContentPane.setLayout(new BorderLayout());
			aboutContentPane.add(getAboutVersionLabel(), BorderLayout.CENTER);
		}
		return aboutContentPane;
	}

	/**
	 * This method initializes aboutVersionLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getAboutVersionLabel() {
		if (aboutVersionLabel == null) {
			aboutVersionLabel = new JLabel();
			aboutVersionLabel.setText("Version 1.0");
			aboutVersionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return aboutVersionLabel;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCutMenuItem() {
		if (cutMenuItem == null) {
			cutMenuItem = new JMenuItem();
			cutMenuItem.setText("Cut");
			cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
					Event.CTRL_MASK, true));
		}
		return cutMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCopyMenuItem() {
		if (copyMenuItem == null) {
			copyMenuItem = new JMenuItem();
			copyMenuItem.setText("Copy");
			copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
					Event.CTRL_MASK, true));
		}
		return copyMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getPasteMenuItem() {
		if (pasteMenuItem == null) {
			pasteMenuItem = new JMenuItem();
			pasteMenuItem.setText("Paste");
			pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
					Event.CTRL_MASK, true));
		}
		return pasteMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSaveMenuItem() {
		if (saveMenuItem == null) {
			saveMenuItem = new JMenuItem();
			saveMenuItem.setText("Save");
			saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					Event.CTRL_MASK, true));
		}
		return saveMenuItem;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JPanel getJPanel3d() {
		if (jPanel3d == null) {
			jPanel3d = new JPanel();
			jPanel3d.setLayout(new BorderLayout());
			jPanel3d.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null,
					null, null, null));
			jPanel3d.setBounds(3, 0, 580, 577);
		}
		return jPanel3d;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JPanel getJPanelsmall3d() {
		if (jPanelsmall3d == null) {
			jPanelsmall3d = new JPanel();
			BorderLayout jPanelsmall3dLayout = new BorderLayout();
			jPanelsmall3d.setLayout(jPanelsmall3dLayout);
			jPanelsmall3d.setBorder(new SoftBevelBorder(BevelBorder.LOWERED,
					null, null, null, null));
			jPanelsmall3d.setBounds(590, 0, 580, 577);
		}
		return jPanelsmall3d;
	}

	/**
	 * This method initializes jSlider
	 * 
	 * @return javax.swing.JSlider
	 */
	private JSlider getJSlider() {
		if (jSlider == null) {
			jSlider = new JSlider();
			jSlider.setBounds(new Rectangle(25, 5, 314, 16));
			jSlider.setPreferredSize(new java.awt.Dimension(1122, 16));
		}
		return jSlider;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("DB Connection");
			jButton2.setBounds(new Rectangle(282, 279, 131, 26));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO
																// Auto-generated
																// Event stub
																// actionPerformed()
					// addDatabase();
					try {
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return jButton2;
	}

	// this is the database connection operation
	public void addDatabase() {
		if (m_db == null) {
			try {
				m_db = new QueryTrajectory();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				ArrayList<Trajectory> ts = m_db.getAll();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setName("");
			jRadioButton1.setText("Bus stop");
			jRadioButton1.setSelected(true);
			jRadioButton1
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							System.out.println("stateChanged()"); // TODO
																	// Auto-generated
																	// Event
																	// stub
																	// stateChanged()

							if (jRadioButton1.isSelected()) {
								m_glr.m_transRender.enableVisible();
								m_glr.repaint();
							} else {
								m_glr.m_transRender.disableVisible();
								m_glr.repaint();
							}
						}
					});
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setText("Grid");
			jRadioButton.setSelected(true);
			jRadioButton
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							System.out.println("stateChanged()"); // TODO
																	// Auto-generated
																	// Event
																	// stub
																	// stateChanged()
							if (jRadioButton.isSelected()) {
								m_glr.m_cellularRender.enableVisible();
								m_glr.repaint();
							} else {
								m_glr.m_cellularRender.disableVisible();
								m_glr.repaint();
							}
						}
					});
		}
		return jRadioButton;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setText("mixed use buildings");
			jRadioButton2.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					System.out.println("itemStateChanged()"); // TODO
																// Auto-generated
																// Event stub
																// itemStateChanged()
					if (jRadioButton2.isSelected()) {
						m_glr.m_buildings.enableVisible();
						m_glr.repaint();
					} else {
						m_glr.m_buildings.disableVisible();
						m_glr.repaint();
					}
				}
			});
		}
		return jRadioButton2;
	}

	/**
	 * This method initializes jButtonReset
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonReset() {
		if (jButtonReset == null) {
			jButtonReset = new JButton();
			jButtonReset.setBounds(new Rectangle(54, 88, 118, 27));
			jButtonReset.setText("reset");
			jButtonReset.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO
																// Auto-generated
																// Event stub
																// actionPerformed()
					m_glr.reset();
					m_glr.repaint();
				}
			});

		}
		return jButtonReset;
	}

	/**
	 * This method initializes jRadioButton3
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton3() {
		if (jRadioButton3 == null) {
			jRadioButton3 = new JRadioButton();
			jRadioButton3.setActionCommand("");
			jRadioButton3.setSelected(false);
			jRadioButton3.setText("urban activites");
			jRadioButton3.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					System.out.println("itemStateChanged()"); // TODO
																// Auto-generated
																// Event stub
																// itemStateChanged()
					if (jRadioButton3.isSelected()) {
						m_glr.m_tripTreeRender.enableVisible();
						m_glr.repaint();
					} else {
						m_glr.m_tripTreeRender.disableVisible();
						m_glr.repaint();
					}
				}
			});
		}
		return jRadioButton3;
	}

	/**
	 * This method initializes jRadioButton4
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton4() {
		if (jRadioButton4 == null) {
			jRadioButton4 = new JRadioButton();
			jRadioButton4.setText("Urban flow");
			jRadioButton4.setSelected(false);
			jRadioButton4.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					System.out.println("itemStateChanged()"); // TODO
																// Auto-generated
																// Event stub
																// itemStateChanged()
					if (jRadioButton4.isSelected()) {
						m_glr.m_flowVis.enableVisible();
						m_glr.repaint();
					} else {
						m_glr.m_flowVis.disableVisible();
						m_glr.repaint();
					}
				}
			});
		}
		return jRadioButton4;
	}

	/**
	 * This method initializes jFrame1
	 * 
	 * @return javax.swing.JFrame
	 */
	private JFrame getJFrame1() {
		if (jFrame1 == null) {
			jFrame1 = new JFrame();
			jFrame1.setSize(new Dimension(429, 343));
			jFrame1.setTitle("extra control");
			jFrame1.setContentPane(getJContentPane1());
		}
		return jFrame1;
	}

	/**
	 * This method initializes jContentPane1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane1() {
		if (jContentPane1 == null) {
			jLabel111 = new JLabel();
			jLabel111.setBounds(new Rectangle(73, 164, 49, 16));
			jLabel111.setText("Bus No:");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(72, 114, 44, 20));
			jLabel11.setText("People:");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(71, 64, 46, 20));
			jLabel1.setText("Regin:");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(70, 14, 48, 20));
			jLabel.setText("Type:");
			jContentPane1 = new JPanel();
			jContentPane1.setLayout(null);
			jContentPane1.add(getJButton2(), null);
			jContentPane1.add(getJComboBoxRegion(), null);
			jContentPane1.add(getJComboBoxBus(), null);
			jContentPane1.add(getJButtonQuery(), null);
			jContentPane1.add(getJComboBox(), null);
			jContentPane1.add(jLabel, null);
			jContentPane1.add(jLabel1, null);
			jContentPane1.add(jLabel11, null);
			jContentPane1.add(jLabel111, null);
			jContentPane1.add(getJComboBox1(), null);
			jContentPane1.add(getJTextArea(), null);
		}
		return jContentPane1;
	}

	/**
	 * This method initializes jComboBoxRegion
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBoxRegion() {
		if (jComboBoxRegion == null) {
			jComboBoxRegion = new JComboBox();
			jComboBoxRegion.setName("region");
			jComboBoxRegion.setBounds(new Rectangle(144, 61, 181, 25));

		}
		return jComboBoxRegion;
	}

	/**
	 * This method initializes jComboBoxBus
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBoxBus() {
		if (jComboBoxBus == null) {
			jComboBoxBus = new JComboBox();
			jComboBoxBus.setBounds(new Rectangle(143, 11, 182, 25));

			jComboBoxBus.addItem("Bus");
			jComboBoxBus.addItem("RTS");
		}
		return jComboBoxBus;
	}

	/**
	 * This method initializes jButtonQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonQuery() {
		if (jButtonQuery == null) {
			jButtonQuery = new JButton();
			jButtonQuery.setBounds(new Rectangle(282, 249, 131, 30));
			jButtonQuery.setText("Query");
		}
		return jButtonQuery;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(145, 111, 178, 25));
		}
		return jComboBox;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setBounds(new Rectangle(147, 161, 175, 25));
		}
		return jComboBox1;
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setBounds(new Rectangle(81, 220, 107, 53));
		}
		return jTextArea;
	}

	/**
	 * Launches this application
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				EZ_CITY_twin application = new EZ_CITY_twin();
				application.getJFrame().setVisible(true);
				// application.getJFrame1().setVisible(true);
				// setup OpenGL Version 2
				GLProfile profile = GLProfile.get(GLProfile.GL2);
				GLCapabilities capabilities = new GLCapabilities(profile);

				// The canvas is the widget that's drawn in the JFrame
				GLCanvas glcanvas = new GLCanvas(capabilities);
				GLCanvas glcanvas1 = new GLCanvas(capabilities);

				// application.animator = new FPSAnimator(glcanvas, 30);
				// application.animator.start();

				if (m_glr == null) {
					m_glr = new RenderMan();
				}
				if (m_glr1 == null) {
					m_glr1 = new RenderMan();
				}

				if (m_gm == null) {
					m_gm = new GManager();

					m_gm.register(m_glr, 1);
					m_gm.register(m_glr1, 2);

					m_gm.loadData();
					m_gm.assignData();

					m_glr.setCanvas(glcanvas);
					m_glr1.setCanvas(glcanvas1);
				}

				// glcanvas.setSize(application.getJPanel3d().getWidth(),
				// application.getJPanel3d().getHeight());
				application.getJPanel3d().add(glcanvas, BorderLayout.CENTER);
				application.getJPanelsmall3d().add(glcanvas1,
						BorderLayout.CENTER);

				if (!application.m_modellist.equalsIgnoreCase("")) {
					// m_glr.addModel(application.m_modellist);
					application.m_modellist = "";
				}

			}
		});
	}

	private JTabbedPane getJTabbedPane1() {
		if (jTabbedPane1 == null) {
			jTabbedPane1 = new JTabbedPane();
			jTabbedPane1.setBounds(1172, 3, 196, 574);
			jTabbedPane1.addTab("slider", null, getJPanel3(), null);
			jTabbedPane1.addTab("layer", null, getJPanelbutton(), null);
		}
		return jTabbedPane1;
	}

	private JPanel getJPanelbutton() {
		if (jPanelbutton == null) {
			jPanelbutton = new JPanel();
			BoxLayout jPanelbuttonLayout = new BoxLayout(jPanelbutton,
					javax.swing.BoxLayout.Y_AXIS);
			jPanelbutton.setLayout(jPanelbuttonLayout);
			jPanelbutton.setPreferredSize(new java.awt.Dimension(196, 232));
			jPanelbutton.add(getJRadioButton4());
			jPanelbutton.add(getJRadioButton3());
			jPanelbutton.add(getJRadioButton());
			jPanelbutton.add(getJRadioButton2());
			jPanelbutton.add(getJRadioButton1());
			jPanelbutton.add(getJButtonReset());
		}
		return jPanelbutton;
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setPreferredSize(new java.awt.Dimension(222, 191));
		}
		return jPanel3;
	}

	private JPanel getJPanelslider() {
		if (jPanelslider == null) {
			jPanelslider = new JPanel();
			jPanelslider.setBounds(3, 583, 1364, 36);
			jPanelslider.add(getJSlider());
			jPanelslider.add(getJButtonup());
			jPanelslider.add(getJButtondown());
		}
		return jPanelslider;
	}

	private JButton getJButtonup() {
		if (jButtonup == null) {
			jButtonup = new JButton();
			jButtonup.setText("time+");
			jButtonup.setBounds(1207, 587, 55, 21);
		}
		return jButtonup;
	}

	private JButton getJButtondown() {
		if (jButtondown == null) {
			jButtondown = new JButton();
			jButtondown.setText("time-");
			jButtondown.setBounds(1285, 587, 38, 21);
		}
		return jButtondown;
	}

}
