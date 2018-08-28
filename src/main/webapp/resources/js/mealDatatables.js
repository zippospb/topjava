var ajaxUrl = "ajax/profile/meals/";
var datatableApi;
var filterData;

function clearFilter(){
    filterData = undefined;
    document.getElementById("filterForm").reset();
    updateTable();
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ]
    });
    makeEditable();

    $(".filter").click(function () {
        filterData = "filter?" + $("#filterForm").serialize();
        updateTable();
    })
});

function updateTable() {
    $.get(ajaxUrl + (filterData || ""), function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}