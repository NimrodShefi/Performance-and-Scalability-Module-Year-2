/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 100.0, "KoPercent": 0.0};
    var dataset = [
        {
            "label" : "KO",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "OK",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [1.0, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [1.0, 500, 1500, "Add Item Post-1"], "isController": false}, {"data": [1.0, 500, 1500, "Add Item Post-0"], "isController": false}, {"data": [1.0, 500, 1500, "Add Item Get"], "isController": false}, {"data": [1.0, 500, 1500, "Login"], "isController": false}, {"data": [1.0, 500, 1500, "Login-0"], "isController": false}, {"data": [1.0, 500, 1500, "Login-1"], "isController": false}, {"data": [1.0, 500, 1500, "About Us"], "isController": false}, {"data": [1.0, 500, 1500, "Add Item Get-1"], "isController": false}, {"data": [1.0, 500, 1500, "Add Item Get-0"], "isController": false}, {"data": [1.0, 500, 1500, "HTTP RequestLogout"], "isController": false}, {"data": [1.0, 500, 1500, "Add Item Post"], "isController": false}, {"data": [1.0, 500, 1500, "Menu"], "isController": false}, {"data": [1.0, 500, 1500, "Home"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 130, 0, 0.0, 96.16923076923082, 8, 359, 74.0, 184.60000000000002, 256.24999999999994, 343.1899999999999, 88.5558583106267, 372.28850902588556, 30.595410422343324], "isController": false}, "titles": ["Label", "#Samples", "KO", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions\/s", "Received", "Sent"], "items": [{"data": ["Add Item Post-1", 10, 0, 0.0, 72.60000000000001, 25, 157, 53.5, 156.4, 157.0, 157.0, 11.049723756906078, 91.54113432320442, 2.740849447513812], "isController": false}, {"data": ["Add Item Post-0", 10, 0, 0.0, 120.90000000000002, 36, 239, 126.0, 233.20000000000002, 239.0, 239.0, 9.615384615384617, 3.0517578125, 4.010479266826923], "isController": false}, {"data": ["Add Item Get", 10, 0, 0.0, 143.9, 45, 264, 114.0, 263.0, 264.0, 264.0, 9.746588693957115, 50.46524731968811, 4.797149122807017], "isController": false}, {"data": ["Login", 10, 0, 0.0, 152.49999999999997, 65, 307, 135.0, 296.70000000000005, 307.0, 307.0, 9.671179883945841, 50.04646699709865, 6.271155705996131], "isController": false}, {"data": ["Login-0", 10, 0, 0.0, 65.7, 8, 137, 64.5, 136.1, 137.0, 137.0, 9.84251968503937, 3.623662032480315, 3.921628937007874], "isController": false}, {"data": ["Login-1", 10, 0, 0.0, 85.5, 18, 167, 84.0, 163.8, 167.0, 167.0, 9.910802775024777, 47.63766724479683, 2.4777006937561943], "isController": false}, {"data": ["About Us", 10, 0, 0.0, 64.3, 32, 159, 52.0, 150.60000000000002, 159.0, 159.0, 12.004801920768308, 60.2233080732293, 2.3915816326530615], "isController": false}, {"data": ["Add Item Get-1", 10, 0, 0.0, 63.9, 24, 160, 48.0, 155.10000000000002, 160.0, 160.0, 9.950248756218905, 48.66293532338309, 2.4098258706467663], "isController": false}, {"data": ["Add Item Get-0", 10, 0, 0.0, 79.2, 17, 162, 73.0, 161.1, 162.0, 162.0, 10.15228426395939, 2.9148159898477157, 2.5380710659898478], "isController": false}, {"data": ["HTTP RequestLogout", 10, 0, 0.0, 54.699999999999996, 10, 116, 45.5, 115.7, 116.0, 116.0, 11.210762331838565, 23.023665218609864, 2.6603664517937218], "isController": false}, {"data": ["Add Item Post", 10, 0, 0.0, 194.4, 62, 359, 173.0, 353.90000000000003, 359.0, 359.0, 9.31098696461825, 80.09176414106145, 6.193079317970205], "isController": false}, {"data": ["Menu", 10, 0, 0.0, 96.1, 41, 259, 70.5, 246.20000000000005, 259.0, 259.0, 11.415525114155251, 87.41126212899543, 2.831585331050228], "isController": false}, {"data": ["Home", 10, 0, 0.0, 56.50000000000001, 16, 89, 54.0, 89.0, 89.0, 89.0, 12.970168612191959, 26.142996108949415, 2.5332360570687418], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": []}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 130, 0, null, null, null, null, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
