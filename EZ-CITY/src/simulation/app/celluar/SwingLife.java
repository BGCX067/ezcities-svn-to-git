package simulation.app.celluar;
/*
Copyright (c) 2010, Brett Alistair Kromkamp - brettkromkamp@gmail.com
All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of the copyright holder nor the names of the contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.event.MouseListener;
import java.util.Hashtable;

public class SwingLife extends JApplet {
    LifePanel lifePanel;
    
    public void init() {
        Container contentPane = getContentPane();
        lifePanel = new LifePanel();
        contentPane.add(lifePanel);
        lifePanel.startAnimation();
    }
    
    public String getAppletInfo() {
        return "Title: Swing Game of Life Applet v1.0b, 20 Oct 2000. "
            + "Author: Brett Alistair Kromkamp. "
            + "Copyright (C) 2000 - Brett Alistair Kromkamp. "
            + "A simple Swing applet demonstrating Conway's Game of Life.";
    }
    
    public String[][] getParameterInfo() {
        String [][] info = {{"NONE", "NONE", "This applet takes no HTML-parameters"}};
        return info;
    }
}

class LifeGridComponent extends JComponent {
    final static int XSIZE = 362; // class constants
    final static int YSIZE = 162;
    private Dimension preferredSize = new Dimension(XSIZE, YSIZE);
    private Cursor cursor;
    private int row;
    private int col;
    private int size;
    private LifePanel lp;
    
    public LifeGridComponent(LifePanel newPanel, int newRow, int newCol, int newSize) {
        setPreferredSize(preferredSize);
        setMinimumSize(preferredSize);
        setCursor(cursor.getPredefinedCursor(cursor.CROSSHAIR_CURSOR));
        this.row = newRow;
        this.col = newCol;
        this.size = newSize;
        this.lp = newPanel;
    }
    
    public void paint(Graphics g) {
        int i, j;
        for (i = 0; i < row; i++) {
            for (j = 0; j < col; j++) {
                if (lp.grid[i][j] != 0) {
                    switch(lp.cellShape) {
                        case 0: 
                            g.drawRoundRect((j * size) +1, 
                                (i * size) +1, size, size, 2, 2);
                            g.drawRect((j * size) +1, 
                                (i * size) +1, size -2, size -2); 
                            break;
                        case 1: 
                            g.drawRect((j * size) +1, 
                                (i * size) +1, size -2, size -2); 
                            break;
                        case 2: // debug: should be drawPolyLine
                            g.drawLine((j * size) + (size / 2), 
                                (i * size) +1, (j * size), 
                                (i * size) + (size / 2) +1);
                            g.drawLine((j * size), (i * size) + (size / 2) +1, 
                                (j * size) + size, (i * size) + (size / 2) +1);
                            g.drawLine((j * size) + size, 
                                (i * size) + (size / 2) +1, 
                                (j * size) + (size / 2), 
                                (i * size) +1);
                            break;
                    }
                }
            }
        }
        g.drawRect(0, 0, XSIZE -1, YSIZE -1); // debug
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // additional paint code
    }
}

class LifePanel extends JPanel implements ActionListener {
    
    class SliderListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider)e.getSource();
            if (source.getValueIsAdjusting()) {
                if (source == minSlider) {
                    min = (int)source.getValue();
                } else if (source == maxSlider) {
                    max = (int)source.getValue();
                } else if (source == hitSlider) {
                    hit = (int)source.getValue();
                } else if (source == animSlider) {
                    int fps = (int)source.getValue();
                    if (fps == 0) {
                        if (!frozen) stopAnimation();
                    } else {
                        delay = 4000 / fps;
                        timer.setDelay(delay);
                        timer.setInitialDelay(4000 / INIT_FPS);
                        if (frozen) {
                            startAnimation();
                        }
                    }
                }
            }
        }
    }
    
    static final int ROW = 20; // class constants
    static final int COL = 45;
    static final int SIZE = 8;
    static final int INIT_FPS = 10;
    
    public int[][] grid;
    public int cellShape = 0;
    public boolean colorCoded = false;
    
    private int[][] gridCopy;
    private int min = 2;
    private int max = 3;
    private int hit = 3;
    private int generationCount = 0;
    private String[] shapeStrings = {"3D-block", "Rectangle", "Triangle"};
    
    private Timer timer;
    private int delay = 4000 / INIT_FPS;
    private boolean frozen = false;
    
    private GridBagLayout gridbag = new GridBagLayout();
    private GridBagConstraints c = new GridBagConstraints();
    
    JLabel titleLabel; // components
    JLabel minSliderLabel;
    JLabel maxSliderLabel;
    JLabel hitSliderLabel;
    JLabel animSliderLabel;
    JLabel shapeLabel;
    JLabel countLabel;
    JLabel counter;
    JSlider minSlider;
    JSlider maxSlider;
    JSlider hitSlider;
    JSlider animSlider;
    JComboBox shapeCombo;
    LifeGridComponent gridComponent;
    JCheckBox colorCheckBox;
    
    LifePanel() { // constructor
        timer = new Timer(delay, this);
        timer.setInitialDelay(delay * 10);
        timer.setCoalesce(true); // ???
    
        grid = new int [ROW][COL]; // initialize data structures
        gridCopy = new int [ROW][COL];
    
        initGrid(grid);
        grid[8][(COL / 2) -1] = 1;
        grid[8][(COL / 2) +1] = 1;
        grid[9][(COL / 2) -1] = 1;
        grid[9][(COL / 2) +1] = 1;
        grid[10][(COL / 2) -1] = 1;
        grid[10][(COL / 2)] = 1;
        grid[10][(COL / 2) +1] = 1;
    
        setLayout(gridbag);
    
        // create and initialize components
        titleLabel = new JLabel("Java 2 (Swing) Game of Life Applet");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets (2, 0, 0, 0);
        c.anchor = GridBagConstraints.WEST;
        gridbag.setConstraints(titleLabel, c);
        add(titleLabel);
    
        gridComponent = new LifeGridComponent(this, ROW, COL, SIZE);
        c.gridx = 0;
        c.gridy = 1;
        gridbag.setConstraints(gridComponent, c);
        add(gridComponent);
    
        minSliderLabel = new JLabel("MIN-variable: ");
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        gridbag.setConstraints(minSliderLabel, c);
        add(minSliderLabel);
    
        minSlider = new JSlider(JSlider.HORIZONTAL, 0, 8, min);
        minSlider.addChangeListener(new SliderListener());
        minSlider.setToolTipText("Under-population threshold (deathrate)");
        minSlider.setMajorTickSpacing(2);
        minSlider.setMinorTickSpacing(1);
        minSlider.setPaintTicks(true);
        minSlider.setPaintLabels(true);
        minSlider.setSnapToTicks(true);
        c.gridx = 1;
        c.gridy = 2;
        gridbag.setConstraints(minSlider, c);
        add(minSlider);
    
        maxSliderLabel = new JLabel("MAX-variable: ");
        c.gridx = 0;
        c.gridy = 3;
        gridbag.setConstraints(maxSliderLabel, c);
        add(maxSliderLabel);
    
        maxSlider = new JSlider(JSlider.HORIZONTAL, 0, 8, max);
        maxSlider.addChangeListener(new SliderListener());
        maxSlider.setToolTipText("Over-population threshold (deathrate)");
        maxSlider.setMajorTickSpacing(2);
        maxSlider.setMinorTickSpacing(1);
        maxSlider.setPaintTicks(true);
        maxSlider.setPaintLabels(true);
        maxSlider.setSnapToTicks(true);
        c.gridx = 1;
        c.gridy = 3;
        gridbag.setConstraints(maxSlider, c);
        add(maxSlider);
    
        hitSliderLabel = new JLabel("HIT-variable: ");
        c.gridx = 0;
        c.gridy = 4;
        gridbag.setConstraints(hitSliderLabel, c);
        add(hitSliderLabel);

        hitSlider = new JSlider(JSlider.HORIZONTAL, 0, 8, hit);
        hitSlider.addChangeListener(new SliderListener());
        hitSlider.setToolTipText("Hit threshold (birthrate)");
        hitSlider.setMajorTickSpacing(2);
        hitSlider.setMinorTickSpacing(1);
        hitSlider.setPaintTicks(true);
        hitSlider.setPaintLabels(true);
        hitSlider.setSnapToTicks(true);
        c.gridx = 1;
        c.gridy = 4;
        gridbag.setConstraints(hitSlider, c);
        add(hitSlider);
    
        animSliderLabel = new JLabel("Animation speed: ");
        c.gridx = 0;
        c.gridy = 5;
        gridbag.setConstraints(animSliderLabel, c);
        add(animSliderLabel);
    
        animSlider = new JSlider(JSlider.HORIZONTAL, 0, 30, INIT_FPS);
        animSlider.addChangeListener(new SliderListener());
        Hashtable animLabelTable = new Hashtable();
        animLabelTable.put(new Integer(0), new JLabel("Stop"));
        animLabelTable.put(new Integer(10), new JLabel("Slow"));
        animLabelTable.put(new Integer(30), new JLabel("Fast"));
        animSlider.setLabelTable(animLabelTable);
        animSlider.setToolTipText("Calculation/animation speed");
        animSlider.setMajorTickSpacing(10);
        animSlider.setMinorTickSpacing(5);
        animSlider.setPaintTicks(true);
        animSlider.setPaintLabels(true);
        c.gridx = 1;
        c.gridy = 5;
        gridbag.setConstraints(animSlider, c);
        add(animSlider);
    
        shapeLabel = new JLabel("Cell shape: ");
        c.gridx = 0;
        c.gridy = 6;
        gridbag.setConstraints(shapeLabel, c);
        add(shapeLabel);
    
        shapeCombo = new JComboBox(shapeStrings);
        shapeCombo.setToolTipText("Cells' visual shape");
        c.gridx = 1;
        c.gridy = 6;
        gridbag.setConstraints(shapeCombo, c);
        add(shapeCombo);
    
        shapeCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                cellShape = cb.getSelectedIndex();
            }
        });
    
        colorCheckBox = new JCheckBox("Color coded");
        colorCheckBox.setSelected(false);
        colorCheckBox.setToolTipText("Population density color coding (not implemented)");
        c.gridx = 1;
        c.gridy = 12;
        gridbag.setConstraints(colorCheckBox, c);
        add(colorCheckBox);
    
        colorCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                JCheckBox cb = (JCheckBox)e.getSource();
                colorCoded = !colorCoded;
            }
        });

        countLabel = new JLabel("Generation #: ");
        c.gridx = 0;
        c.gridy = 14;
        gridbag.setConstraints(countLabel, c);
        add(countLabel);
    
        counter = new JLabel("0");
        counter.setToolTipText("Current generation count");
        c.gridx = 1;
        c.gridy = 14;
        gridbag.setConstraints(counter, c);
        add(counter);
    }
    
    public void actionPerformed(ActionEvent e) {
        nextGeneration();
        gridComponent.repaint();
        generationCount++;
        counter.setText(Integer.toString(generationCount));
    }
    
    public void startAnimation() {
        timer.start();
        frozen = false;
    }
    
    public void stopAnimation() {
        timer.stop();
        frozen = true;
    }
    
    private int calc(int y, int x) {
        int m, n, total;
    
        total = (grid[y][x] != 0) ? -1 : 0;
        for (m = -1; m <= +1; m++) {
            for (n = -1; n <= +1; n++) {
                if (grid[(ROW + (y + m)) % ROW][(COL + (x + n)) % COL] != 0) {
                    total++;
                }
            }
        }
        return total;
    }
    
    private void duplicateGrid(int[][] source, int[][] dest) {
        int i, j;
        for (i = 0; i < ROW; i++) {
            for (j = 0; j < COL; j++) {
                dest[i][j] = source[i][j];
            }
        }    
    }
    
    public void nextGeneration() {
        int i, j, neighbors;
        initGrid(gridCopy);
        for (i = 0; i < ROW; i++) {
            for (j = 0; j < COL; j++) {
                neighbors = calc(i, j);
            
                if (grid[i][j] != 0) {
                    if ((neighbors >= min) && (neighbors <= max)) {
                        gridCopy[i][j] = neighbors;
                    }
                } else {
                    if (neighbors == hit) {
                        gridCopy[i][j] = hit;
                    }
                }
            }
        }
        initGrid(grid);
        duplicateGrid(gridCopy, grid);
    }
    
    private void initGrid(int[][] matrix) {
        int i, j;
        for (i = 0; i < ROW; i++) {
            for (j = 0; j < COL; j++) {
                matrix[i][j] = 0;
            }
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // additional paint code
    }
}