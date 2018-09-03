const ajaxUrl = "ajax/profile/meals/";
let datatableApi;

function updateTable() {
    $.ajax({
        type: "GET",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

function convertAndSave(){
    let dateTime = form.find("input[name='dateTime']").val();
    if(dateTime){
        form.find("input[name='dateTime']").val(dateTime.replace(' ', 'T'));
    }
    save();
}

$.ajaxSetup({
    converters: {
        "text json" : function(result){
            let json = JSON.parse(result);
            $(json).each(function () {
               this.dateTime = this.dateTime.replace('T', ' ');
            });
            return json;
    }}});

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "",
                "orderable": false,
                "render": renderEditBtn
            },
            {
                "defaultContent": "",
                "orderable": false,
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            $(row).attr("data-mealExceed", data.exceed);
        },
        "initComplete": makeEditable
    });

    $('#startTime, #endTime').datetimepicker({
        datepicker:false,
        format:'H:i'
    });

    $('#startDate, #endDate').datetimepicker({
        timepicker:false,
        format:'Y-m-d'
    });

    $('#dateTime').datetimepicker({
        format:'Y-m-d H:i'
    })
});