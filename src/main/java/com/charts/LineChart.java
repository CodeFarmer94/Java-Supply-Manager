package com.charts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;


public abstract class LineChart implements Serializable{
		
	    private static final long serialVersionUID = 1L;

	    public abstract List<Number> getChartData();
	
		public abstract String getLabel();
		public abstract List<String> getFields();
	    
		 public LineChartModel getModel() {
		        LineChartModel lineModel = new LineChartModel();
		        ChartData data = new ChartData();

		        LineChartDataSet dataSet = new LineChartDataSet();
		        List<Object> values = new ArrayList<>();
		        
		        values.addAll(getChartData());
		        
		        
		        
		        dataSet.setData(values);
		        dataSet.setFill(true);
		        dataSet.setLabel("Cash Holdings");
		        dataSet.setBorderColor("rgb(51, 153, 51)");
		        dataSet.setTension(0.1);
		        data.addChartDataSet(dataSet);

		     
		        data.setLabels(getFields());

		        //Options
		        LineChartOptions options = new LineChartOptions();
		        options.setMaintainAspectRatio(false);
		        Title title = new Title();
		        title.setDisplay(true);
		        title.setText("Balace Line Chart");
		        options.setTitle(title);

		        Title subtitle = new Title();
		        subtitle.setDisplay(false);
		        options.setSubtitle(subtitle);

		        lineModel.setOptions(options);
		        lineModel.setData(data);
		        return lineModel;

    }

}
