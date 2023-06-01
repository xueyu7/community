/*
*未读
* */
$().ready(function () {

    $.ajax({
        type: "POST",
        url: "/deleteQuestion",
        contentType: "application/json",
        data: JSON.stringify({
            "id": id
        }),
        success: function (response) {
            console.log(response)
        },
        dataType: "json"
    });
})