let data = [[${viewCountByDate}]];
//let xData = new Array();
//let yData = new Array();
//for(let i=0; i<data.length; i++){
//    yData.push(data[i].view_);
//    xData.push(data[i].date_);
//}
chart = LineChart(data,{
    x: d => d.data_,
    y: d => d.view_,
    width :800,
    height:500
})