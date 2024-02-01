package com.charts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.donut.DonutChartOptions;

public abstract class DonutChart implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private DonutChartModel donutModel;
	
	
	
	
	public abstract List<Number> getDataSet(String type, int year);
	public abstract List<String> getFields(String type);
	
	  public DonutChartModel getDonutModel(String type, int year) {
	        donutModel = new DonutChartModel();
	        ChartData data = new ChartData();
	        DonutChartOptions options = new DonutChartOptions();
	        options.setMaintainAspectRatio(false);
	        options.isAnimateRotate();
	        donutModel.setOptions(options);

	        DonutChartDataSet dataSet = new DonutChartDataSet();
	      
	        dataSet.setData(getDataSet(type, year));

	        List<String> bgColors = new ArrayList<>();
	        List<String> borderColor = new ArrayList<>();
	       // Dark blue color
	        bgColors.add("rgba(0, 0, 255, 0.3)");
	        // Dark red color
	        bgColors.add("rgba(255, 0, 0, 0.3)");
	        // Dark gray color
	        borderColor.add("rgba(0, 0, 50, 0.3)");
	        dataSet.setBackgroundColor(bgColors);
	        dataSet.setBorderColor(borderColor);

	        data.addChartDataSet(dataSet);
	        data.setLabels(getFields(type));

	        donutModel.setData(data);
	        return donutModel;
	    }
}
