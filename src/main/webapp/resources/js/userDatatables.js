var ajaxUrl = "ajax/admin/users/";
var datatableApi;

$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
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
                "asc"
            ]
        ]
    });
    makeEditable();
});

function enable(chkbox, id) {
    var enabled = chkbox.is(":checked");
    $.ajax({
        type: "POST",
        url: ajaxUrl + id,
        data: "enabled=" + enabled,
        success: function () {
            chkbox.closest("tr").attr("data-enabled", enabled)
            successNoty(enabled ? "Enabled" : "Disabled")
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}