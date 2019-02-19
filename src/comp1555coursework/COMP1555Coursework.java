
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp1555coursework;

import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;
import java.util.Arrays;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;
//usage of JFreechart for project, install through sourceforge.com into your Netbeans library to use these imports
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.function.LineFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author na8363c
 */
public class COMP1555Coursework extends JFrame implements ActionListener {

    /* These are intitialised and declared variables for the GUI and for the 
    calculations that will be used "globally" throughout this file, 
    this includes textfields, commoncode for r/w, arrays and arraylists to
    store data, tables for representation,etc*/
    JPanel options;
    CommonCode cc = new CommonCode();
    DefaultTableModel dataSetTable = new DefaultTableModel(0, 8);
    DefaultTableModel error = new DefaultTableModel(0, 4);
    DefaultTableModel line = new DefaultTableModel(0, 4);
//Jtables for holding data
    JTable dataTable, dataLine, dataMeasures, dataSums, dataEst;
    //array to store a row of inputted data
    Object[] curarr = new Object[8];
    //textfields to use for referencing a row of data for table
    JTextField ty = new JTextField("0      ");
    JTextField tx1 = new JTextField("0      ");
    JTextField tx2 = new JTextField("0      ");
    JTextField tx3 = new JTextField("0      ");
    JTextField tx4 = new JTextField("0      ");
    JTextField tx5 = new JTextField("0      ");
    JTextField tx6 = new JTextField("0      ");
    JTextField tx7 = new JTextField("0      ");
    //variables used for tables or calculation
    Double meanX, varianceX, B1, B0, ΣXi, ΣXi2, ΣXY, ΣY, ΣY2, STDevX, R2, R, lineslope, y_intercept, tB1, tB0 = 0.0;
    //Titles for X&Y axis for chart
    String AxisX;
    String t_title;
    String AxisY = "Selling Price (£100000s)";
    //chart object and objects for data
    JFreeChart chart;
    XYSeriesCollection dataset;
    XYSeries series1;
    XYSeries series2;
    XYSeries series3;
    //plot instance for chart
    XYPlot plot;

    //arraylist and 2D array for files read into system
    ArrayList<double[]> trainingdataset = new ArrayList<>();
    //array for read from file, the checkbox to display said data from file and it's line renderers/datasets for the graph are declared here
    double[][] trainingtable;
    JCheckBox checkbox;
    XYLineAndShapeRenderer txylineandshaperenderer;
    XYDataset regressionTrainingData;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        COMP1555Coursework prg = new COMP1555Coursework();

        // TODO code application logic here
    }

    public COMP1555Coursework() {
        //GUI method
  
displayGUI();
    }

    public double[][] retrievedataset() {
        //calls readfromtextfile() and makes the trainingdataset arraylist hold the file's values in itself
        trainingdataset = CommonCode.readTextFile(cc.appDir + "\\Trainingdataset.txt");
                    //if the file's nonexistent, do nothing
        if ("file not found".equals(trainingdataset.get(0))) {
        } else {
            //convert the arraylist into an arry with the first dimension
            //initialising with the size of the arraylist and the second being the size of the row
            trainingtable = trainingdataset.toArray(new double[trainingdataset.size()][]);
        }
        return trainingtable;
    }

    private void displayGUI() {

    retrievedataset();     
        //column names for dataset table
        Object[] columnNames = {"Y= Selling Price (£100000s)", "X1=bathrooms", "X2=site area", "X3=living space", "X4=garages", "X5=rooms", "X6=bedrooms", "X7=age"};
//labels for the dataset table value fields
        JLabel tagy = new JLabel("Y= Selling Price (£)");
        JLabel tagx1 = new JLabel("X1= bathrooms");
        JLabel tagx2 = new JLabel("X2=site area");
        JLabel tagx3 = new JLabel("X3=living space");
        JLabel tagx4 = new JLabel("X4=garages");
        JLabel tagx5 = new JLabel("X5=rooms");
        JLabel tagx6 = new JLabel("X6=bedrooms");
        JLabel tagx7 = new JLabel("X7=age");
//creation of main table for dataset values
        dataTable = new JTable();
        dataTable.setBounds(30, 40, 200, 300);

        //set the table dataSetTable for the dataset table, as well as name of table columns
        dataSetTable = (DefaultTableModel) dataTable.getModel();
        dataSetTable.setColumnIdentifiers(columnNames);

//other tables for extra information
        String[] columnNames2 = {"X",
            "Y",};
        Object[][] data2 = {{"Mean", meanX}, {"Variance", varianceX}, {"Std.Dev", STDevX}};
        dataMeasures = new JTable(data2, columnNames2);

        String[] columnNames3 = {"1",
            "2",};
        Object[][] data3 = {{"ΣX", ΣXi},
        {"ΣX2", ΣXi2},
        {"ΣY", ΣY}, {"ΣY2", ΣY2}, {"ΣXY", ΣXY}};
        dataSums = new JTable(data3, columnNames3);
        dataSums.setBounds(30, 40, 200, 300);

        String[] columnNames4 = {"R",
            "R2", "Slope", "Yintercept"};
        Object[][] data4 = {{R2, R, B1, B0}};
        dataLine = new JTable(data4, columnNames4);
        dataLine.setBounds(30, 40, 200, 300);

        String[] columnNames5 = {"X",
            "Y", "Forecasted Y", "Std err of estimate"};
        dataEst = new JTable();
        error = (DefaultTableModel) dataEst.getModel();
        error.setColumnIdentifiers(columnNames5);

        dataEst.setBounds(30, 40, 200, 300);
//initialisation of panels to hold tables and data
        JPanel main = new JPanel(new FlowLayout());
        JPanel options = new JPanel(new FlowLayout());
        main.add(options, BorderLayout.CENTER);
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createLineBorder(Color.black));
        
//checkbox and its action listeners created to control visibility of data
        checkbox = new JCheckBox("Display training data & its line");
        checkbox.setSelected(false);
        checkbox.addActionListener(this);
        
//panel to hold buttons for graph/table
        JPanel flow = new JPanel(new FlowLayout());
        flow.add(makebutton("Submit current values", "submitdataset"));
        flow.add(makebutton("clear table", "cleartable"));
        flow.add(makebutton("show line", "showline"));
        flow.add(makebutton("add predicted value", "addpred"));
        flow.add(checkbox);
//adding tables panels to the mainpanel
        main.add(flow);
        main.add(dataTable.getTableHeader(), BorderLayout.NORTH);
        main.add(dataTable);
//scrollpane created to control displayed data 
        JScrollPane mainPane = new JScrollPane(main);
        mainPane.setPreferredSize(new Dimension(640, 200));

        add(mainPane, BorderLayout.NORTH);
//adding the textfields to the options panel
        options.add(tagy, BorderLayout.EAST);
        options.add(ty, BorderLayout.EAST);
        options.add(tagx1, BorderLayout.EAST);
        options.add(tx1, BorderLayout.EAST);
        options.add(tagx2, BorderLayout.EAST);
        options.add(tx2, BorderLayout.EAST);
        options.add(tagx3, BorderLayout.EAST);
        options.add(tx3, BorderLayout.EAST);
        options.add(tagx4, BorderLayout.EAST);
        options.add(tx4, BorderLayout.EAST);
        options.add(tagx5, BorderLayout.EAST);
        options.add(tx5, BorderLayout.EAST);
        options.add(tagx6, BorderLayout.EAST);
        options.add(tx6, BorderLayout.EAST);
        options.add(tagx7, BorderLayout.EAST);
        options.add(tx7, BorderLayout.EAST);
        options.add(makebutton("add data", "adddata"));

        //adding the dataviewing tables to the secondary panel
        JPanel secondary = new JPanel(new FlowLayout());
        secondary.setLayout(new BoxLayout(secondary, BoxLayout.Y_AXIS));
        secondary.setBorder(BorderFactory.createLineBorder(Color.black));
        secondary.add(dataMeasures.getTableHeader(), BorderLayout.WEST);
        secondary.add(dataMeasures);
        secondary.add(Box.createVerticalStrut(20));

        secondary.add(dataLine.getTableHeader(), BorderLayout.NORTH);
        secondary.add(dataLine);

        secondary.add(Box.createVerticalStrut(20));
        secondary.add(dataSums, BorderLayout.SOUTH);

        secondary.add(Box.createVerticalStrut(20));
        secondary.add(dataEst.getTableHeader(), BorderLayout.CENTER);
        secondary.add(dataEst);
//creating the chart and the dataset that the data will be displayed through
        XYDataset dataset = createDataset();
        chart = ChartFactory.createScatterPlot("Scattergraph showing the relationship between Selling price and",
                "Nonsense", AxisY, dataset);
//getting the created xyplot
        plot = chart.getXYPlot();
//default title created and it's added to the jpanel
        chart.setTitle("Scattergraph showing the relationship between Selling price and");
        ChartPanel regchart = new ChartPanel(chart);
        JScrollPane thirdpane = new JScrollPane(regchart);
        //panel here is used to force the chart to load on startup
        JPanel flow2 = new JPanel(new FlowLayout());

        thirdpane.add(flow2, FlowLayout.LEFT);

        add(thirdpane, BorderLayout.CENTER);
                JScrollPane secondaryPane = new JScrollPane(secondary);
        secondaryPane.setPreferredSize(new Dimension(640, 200));
        
        add(secondaryPane, BorderLayout.SOUTH);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Regression");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //retireve the training data from the file on start up
        retrievedataset();

    }
//creates the three scattergraph series plots to be added to the chart
    private XYDataset createDataset() {
        dataset = new XYSeriesCollection();
        series1 = new XYSeries("X");
        series2 = new XYSeries("X-predict");
        series3 = new XYSeries("X-training");

//adds the x and the predicted x series to the chart
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        // dataset.addSeries(series3);

        return dataset;
    }
//creation of the data plots and line
    private void trainingSeries(int col) {
//get the length of the training table to make Xi and Y arrays
        double[] trainingx = new double[trainingtable.length];
        double[] trainingy = new double[trainingtable.length];
        //gets all elements from selected columns via 2Darray location and adds it into the arrays
        for (int i = 0; i <= trainingx.length - 1; i++) {
            double placeholdx = trainingtable[i][col];
            double placeholdy = trainingtable[i][0];
            trainingx[i] = placeholdx;
            trainingy[i] = placeholdy;
        }
        //checks if series 3 is empty or else it clears it
        if (series3.isEmpty()) {
        } else {
            series3.clear();
        }
        //adds x&y to series as x,y pairs
        for (int i = 0; i < trainingx.length; i++) {
            series3.add(trainingx[i], trainingy[i]);

        }
        //calculates the values needed to find the line calculation, similar to the methods for the table
        double betaOne, betaZero, tsumx2 = 0, tSXiY = 0, tmeany = 0, tmeanx = 0.0;
        for (int i = 0; i < trainingx.length; i++) {
            tmeanx += trainingx[i];
            tmeany += trainingy[i];
        }
        tmeanx = tmeanx / trainingx.length;
        tmeany = tmeany / trainingy.length;

        for (int i = 0; i < trainingx.length; i++) {
            tSXiY += (trainingx[i] - tmeanx) * (trainingy[i] - tmeany);
        }
        for (int i = 0; i < trainingx.length - 1; i++) {
            tsumx2 += Math.pow(trainingx[i] - tmeanx, 2);
        }
        betaOne = Math.round((tSXiY / tsumx2) * 100.000) / 100.000;
        betaZero = Math.round((tmeany - (betaOne * tmeanx)) * 100.000) / 100.000;
        tB1 = betaOne;
        tB0 = betaZero;
        //creates linefunction to hold B1 and B0
        LineFunction2D tlinefunction2d = new LineFunction2D(
                tB0, tB1);
        //initialises the dataset with the values needed to create the regression line via JFreechart
        regressionTrainingData
                = DatasetUtilities.sampleFunction2D(tlinefunction2d, 0,
                        series3.getMaxX(), series3.getItemCount() + 5, "line of regression for "
                        + AxisX + " of Trainingset");
//set up the appearance of the line via JFreechart renderer
        txylineandshaperenderer
                = new XYLineAndShapeRenderer(true, false);
        txylineandshaperenderer.setSeriesPaint(0, Color.GREEN);
//checks if array functions properly
        System.out.println(Arrays.toString(trainingx) + "x"
                + Arrays.toString(trainingy) + "y");

    }
//gets a column of values from a table and converts it to an array
    public double[] getColumnCells(int col) {
        //creates array which is as large as the amount of rows in a table
        double[] selection = new double[dataSetTable.getRowCount()];
        
        for (int i = 0; i <= selection.length - 1; i++) {
            //for each element in the column, it's converted from an object to a double and added to the array
            double placehold = (double) dataSetTable.getValueAt(i, col);
            selection[i] = placehold;
        }

        return selection;
    }

//calculates the sum of  a selected column index

    public double SumCalc(int col) {
        double sum = 0;
        double[] selection = getColumnCells(col);
        for (int i = 0; i < selection.length; i++) {

            sum += selection[i];

        }
        return sum;
    }
//calculates the sum^2 for a selected column index
    public double Sum2Calc(int col) {
        double sum = 0;
        double[] selection = getColumnCells(col);
        for (int i = 0; i < selection.length; i++) {
            //each element in the array is squared before being added to sum
            double doubled = Math.pow(selection[i], 2);

            sum += doubled;
        }
        return sum;
    }

    /*creates mean value of selected row*/
    public double MeanCalc(int col) {
        double sum = SumCalc(col);
        //System.out.println(sum + "sum of mean");
        double mean = (sum / dataSetTable.getRowCount());
        mean = Math.round(mean * 100.00) / 100.00;
        return mean;
    }
//create the variance
    public double VarCalc(int col) {
        double[] selection = getColumnCells(col);
        double mean = MeanCalc(col);
        double sum = 0;
        double variancecolumn = 0;
        //for each element in the array representing x, it has the mean subtracted from itself then squared before being added into the sum
        for (int i = 0; i < dataSetTable.getRowCount(); i++) {
            sum += Math.pow(selection[i] - mean, 2);
        }
        //variance column is created when you divide the sum value by the number of items in the list
        variancecolumn = sum / selection.length;
        variancecolumn = Math.round(variancecolumn * 100.00) / 100.00;
        return variancecolumn;
    }

    public double SumDoubled(int col) {
        double[] selection = getColumnCells(col);
        double mean = MeanCalc(col);
        double sum = 0;
        for (int i = 0; i < dataSetTable.getRowCount() - 1; i++) {
            sum += Math.pow(selection[i] - mean, 2);
        }
        sum = Math.round(sum * 100.00) / 100.00;
        return sum;
    }
//creation of the sum of X*Y
    public double SumXY(int col) {
        double[] selection = getColumnCells(col);
        double[] selectiony = getColumnCells(0);

        double sum = 0;
        //each element with the same index for the xi&y columns are multiplied by eachother then added to the sum
        for (int i = 0; i < dataSetTable.getRowCount() - 1; i++) {
            sum += (selection[i]) * (selectiony[i]);
        }
        sum = Math.round(sum * 100.00) / 100.00;
        return sum;
    }

    public double SXiY(int col) {
        double[] selection = getColumnCells(col);
        double[] selectiony = getColumnCells(0);
        double mean = MeanCalc(col);
        double meany = MeanCalc(0);

        double sum = 0;
        for (int i = 0; i < dataSetTable.getRowCount(); i++) {
            sum += (selection[i] - mean) * (selectiony[i] - meany);
        }

        return sum;
    }

    public double Beta1Calc(int col) {
        double[] selectionx = getColumnCells(col);

        double mean = MeanCalc(col);
        double meany = MeanCalc(0);
//use of sXiY method to create this value + initialisation
        double sXiY = SXiY(col);
        double sumx2 = 0;
        double betaOne = 0;
//sumx2 has each element from the array representing the column get the mean subtracted from itself then squared before being added
        for (int i = 0; i < dataSetTable.getRowCount() - 1; i++) {
            sumx2 += Math.pow(selectionx[i] - mean, 2);
        }
        //divides sxy by the sum of x^2
        betaOne = Math.round((sXiY / sumx2) * 100.000) / 100.000;
        return betaOne;
    }

    public double Beta0Calc(int col) {
        double[] selectionx = getColumnCells(col);

        double mean = MeanCalc(col);
        double meany = MeanCalc(0);

        double sXiY = SXiY(col);
        double sumx2 = 0;
        double betaOne = 0;
        double betaZero = 0;

        for (int i = 0; i < dataSetTable.getRowCount() - 1; i++) {
            sumx2 += Math.pow(selectionx[i] - mean, 2);
        }
        betaOne = Math.round((sXiY / sumx2) * 100.000) / 100.000;
        betaZero = Math.round((meany - (betaOne * mean)) * 100.000) / 100.000;
        return betaZero;

    }
//calculate sthe Standard Deviation
    public double STDevCalc(int col) {
        double mean = MeanCalc(col);
        double[] selection = getColumnCells(col);
        double sum = 0;
        //each element in the array has the column's mean subtracted from it, then is squared before being added into the sum
        for (int i = 0; i < selection.length; i++) {
            sum += Math.pow(selection[i] - mean, 2);
        }
        //sum is rounded and square rooted
        sum = Math.round(Math.sqrt(sum / selection.length) * 100.00) / 100.00;

        return (sum);
    }

    public double[] estY(int col) {
        double selection[] = getColumnCells(col);
        double selectionyest[] = getColumnCells(col);
        //for the amount of values in the array
        for (int i = 0; i < selection.length; i++) {
            //each value of this new array is equal to this predicted value calculated through this formula
            selectionyest[i] = Math.round(((B1 * selection[i]) + B0) * 100.00)
                    / 100.00;

        }
//return the value
        return selectionyest;
    }
//creates error difference to be added into the table of predicted values
    public void XnYerr(int col) {

        double[] xselection = getColumnCells(col);

        double[] yselection = getColumnCells(0);
        //gets the estimated y values through the estY method
        double[] selectionyest = estY(col);

        for (int i = 0; i < dataSetTable.getRowCount(); i++) {
            double x = xselection[i];
            double y = yselection[i];
            //creates array which holds a row of values
            Object[] errrow = new Object[4];
            errrow[0] = xselection[i];

            errrow[1] = yselection[i];
            errrow[2] = selectionyest[i];
            errrow[3] = yselection[i] - selectionyest[i];

            error.addRow(errrow);
        }
    }
//creates a coefficent value of R^2
    public double coeficcientcalc(int col) {
        double SXiY = SXiY(col);
        double SXiY2 = Math.pow(SXiY, 2);
        double[] selectionx = getColumnCells(col);
        double[] selectiony = getColumnCells(0);
//calls mean
        double mean = MeanCalc(col);
        double meany = MeanCalc(0);

        double sxx = 0;
        double syy = 0;

        for (int i = 0; i < dataSetTable.getRowCount() - 1; i++) {
            sxx += (selectionx[i] - mean);
        }

        for (int i = 0; i < dataSetTable.getRowCount() - 1; i++) {
            syy += Math.pow(selectiony[i] - meany, 2);
        }
        double r2 = (SXiY2 / (sxx * syy));
//creates R2 and returns the value
        return r2;
    }
//methods takes the data from the column's tables to be implemented as graph plots

    public void graphtoPlot(int col) {
        //obtains Xi and Y data
        double[] selectionx = getColumnCells(col);
        double[] selectiony = getColumnCells(0);
        /*checks if series 1 and 2 are empty before adding data in, if not empty, 
 the series is cleared, this is useful for series2 as to make sure that the 
        predicted plots aren't persistent when changing X values*/
        if (series1.isEmpty()) {
        } else {
            series1.clear();
        }
        if (series2.isEmpty()) {
        } else {
            series2.clear();
        }
        //adds each row of data with the specified table columns into an x,y pair 
        //which is then added to series1
        for (int i = 0; i < selectionx.length; i++) {
            series1.add(selectionx[i], selectiony[i]);

        }

    }
//creates the X axis title through switch cases depending on the column index no

    public void getXname(int n) {
        //switch value depend on the parameter value, this will be used with colval to set the names
        switch (n) {
            case 0:
                AxisX = "Selling Price";
                break;
            case 1:
                AxisX = "No of Bathrooms";
                break;
            case 2:
                AxisX = "Site Area (1000 sq.ft)";
                break;
            case 3:
                AxisX = "Living Space (1000 sq.ft)";
                break;
            case 4:
                AxisX = "No of Garages";

                break;
            case 5:
                AxisX = "No of Rooms";

                break;
            case 6:
                AxisX = "No of Bedrooms";

                break;
            case 7:
                AxisX = "Age of Property (Years)";

                break;
            default:
                AxisX = null;
        }

    }

    public void getTitlename(int n) {
        //switch value depend on the parameter value, this will be used with column index no to set the names
        switch (n) {
            case 0:
                t_title = "Selling Price";
                break;
            case 1:
                t_title = "No of Bathrooms";
                break;
            case 2:
                t_title = "Site Area";
                break;
            case 3:
                t_title = "Living Space";
                break;
            case 4:
                t_title = "No of Garages";

                break;
            case 5:
                t_title = "No of Rooms";

                break;
            case 6:
                t_title = "No of Bedrooms";

                break;
            case 7:
                t_title = "Age of Property";

                break;
            default:
                t_title = null;
        }

    }
//String to create the same format of JButton, quickens the process and takes up much less code

    protected JButton makebutton(String name, String ac) {
        JButton b = new JButton();
        b.setActionCommand(ac);
        b.setText(name);
        b.addActionListener(this);
        return b;
    }
//action events for the buttons + checkbox

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkbox) {
            //if clicked on
            if (checkbox.isSelected()) {
//series3 is added to the dataset to be displayed
                dataset.addSeries(series3);
//and the dataset+ line renderer functions, displaying the training set regressionline
                plot.setDataset(5, regressionTrainingData);

                plot.setRenderer(5, txylineandshaperenderer);
            } else {
//when false, the renderer and spot in the dataset are made empty so it doesn't display
                plot.setDataset(5, null);
                plot.setRenderer(5, null);
//the series also is removed from the the dataset
                dataset.removeSeries(series3);

            }
        }
//clears the table
        if ("cleartable" == e.getActionCommand()) {
            dataSetTable.setRowCount(0);

        }

        if ("showline" == e.getActionCommand()) {
            //asks for the column index via dialog box
            int colval = Integer.parseInt(JOptionPane.showInputDialog("Enter X "
                    + "column index (1-7- 0th is Y column) to be used as Xi for "
                    + "all other tables: "));
 
//ca
            double[] x = getColumnCells(colval);
            double[] estY = estY(0);
            double xmax = 0;
            double xmin = x[0];
//find the min/max values of X. Mostly unused due to changes with the object below and the usage of sorting not being implemented
            for (int i = 0; i < x.length; i++) {
                if (x[i] > xmax) {
                    xmax = x[i];
                }
                if (x[i] < xmin) {
                    xmin = x[i];
                }
            }
            //creates a linefunction object storing the calculated B0&B1
            LineFunction2D linefunction2d = new LineFunction2D(
                    B0, B1);
            //creates a new dataset which is used to display a line on the chart
            XYDataset regressionData
                    = DatasetUtilities.sampleFunction2D(linefunction2d, 0, xmax, x.length + 5, "line of regression for " + AxisX);
            //checks if series2 is empty
            if (series2.isEmpty()) {
            } else {
                //if not, line is altered to extend to the last Xvalue from the predicted X series
                regressionData = DatasetUtilities.sampleFunction2D(linefunction2d, 0, series2.getMaxX(), x.length + 5, "line of regression for " + AxisX);

            }
            //sets where it is with the XYplot/dataset, and renders the colour of the line when represented on the chart
            //Based on JFreechart tutorials
            plot.setDataset(4, regressionData);
            XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(true, false);
            xylineandshaperenderer.setSeriesPaint(0, Color.BLUE);
            plot.setRenderer(4, xylineandshaperenderer);

        }
        if ("addpred" == e.getActionCommand()) {
            //when selected, it takes an input from the user, turns it into a double,
            //calculate an estimated y value for it and adds it onto the graph in series2
            double newx = Double.parseDouble(JOptionPane.showInputDialog("Add value to predict"));
            double newestY = Math.round(((B1 * newx) + B0) * 100.000) / 100.000;
            series2.add(newx, newestY);

        }

        if ("submitdataset" == e.getActionCommand()) {
            //clears the table for the error/estimates
            error.setRowCount(0);
            //asks for a value to be used to find a specific row index
            int colval = Integer.parseInt(JOptionPane.showInputDialog("Enter X column index (1-7- 0th is Y column) to be used as Xi for all other tables: "));

//finds the name of the xaxis, title and training series column index via the colval
            getXname(colval);
            getTitlename(colval);
            trainingSeries(colval);
            //sets xaxis label to the selected value
            plot.getDomainAxis().setLabel(AxisX);
            //updates title with the current columnindex 
            TextTitle title = new TextTitle("Scattergraph showing the relationship between Selling price and " + t_title);
            chart.setTitle(title);
            //updates these variables based off of their respective methods used to calculate their values
            B1 = Beta1Calc(colval);
            B0 = Beta0Calc(colval);
            long start = System.currentTimeMillis();
            R2 = coeficcientcalc(colval);
long time = System.currentTimeMillis() - start;
            R = Math.round(Math.sqrt(R2)*100.000)/100.000;
            meanX = MeanCalc(colval);
            STDevX = STDevCalc(colval);
            ΣY = SumCalc(0);
            ΣY2 = Sum2Calc(0);
            ΣXi = SumCalc(colval);
            ΣXY = SumXY(colval);
            ΣXi2 = Sum2Calc(colval);
            //adds said values to JTables
            dataSums.setValueAt(ΣY, 2, 1);
            dataSums.setValueAt(ΣY2, 3, 1);
            dataSums.setValueAt(ΣXi, 0, 1);
            dataSums.setValueAt(ΣXi2, 1, 1);
            dataSums.setValueAt(ΣXY, 4, 1);
            varianceX = VarCalc(colval);
            dataMeasures.setValueAt(meanX, 0, 1);
            dataMeasures.setValueAt(varianceX, 1, 1);
            dataMeasures.setValueAt(STDevX, 2, 1);

            //calls method to create array of error estimations
            XnYerr(colval);
            //sets the value of the dataline table
            dataLine.setValueAt(R, 0, 0);
            dataLine.setValueAt(R2, 0, 1);
            dataLine.setValueAt(B1, 0, 2);
            dataLine.setValueAt(B0, 0, 3);
//calculares the plots for the graph

            graphtoPlot(colval);

        }

        if ("adddata" == e.getActionCommand()) {
            //Get the values from the textfields and convert into double values
            double y = Double.parseDouble(ty.getText());
            double x1 = Double.parseDouble(tx1.getText());
            double x2 = Double.parseDouble(tx2.getText());
            double x3 = Double.parseDouble(tx3.getText());
            double x4 = Double.parseDouble(tx4.getText());
            double x5 = Double.parseDouble(tx5.getText());
            double x6 = Double.parseDouble(tx6.getText());
            double x7 = Double.parseDouble(tx7.getText());
            //add each value to the array
            curarr[0] = y;
            curarr[1] = x1;
            curarr[2] = x2;
            curarr[3] = x3;
            curarr[4] = x4;
            curarr[5] = x5;
            curarr[6] = x6;
            curarr[7] = x7;
            //add current values to the table
            dataSetTable.addRow(curarr);
            //test function
            //clears the values
            ty.setText("0   ");
            tx1.setText("0  ");
            tx2.setText("0  ");
            tx3.setText("0  ");
            tx4.setText("0  ");
            tx5.setText("0  ");
            tx6.setText("0  ");
            tx7.setText("0  ");

        }

    }

}
