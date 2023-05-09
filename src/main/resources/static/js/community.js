/*
*提交回复   (问题的评论)
* */
function postQuestion() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    commentByParentId(questionId, 1, content);
}

/*
*提交二级回复  (评论的回复)
* */
function postComment(e) {
    var commentId = $("#comment_id").val();
    var content = $("#comment_id_content").val();
    commentByParentId(commentId, 2, content);
}

function commentByParentId(parentId, type, content) {
    if (!content) {
        alert("拜托，回复内容是空的？");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": parentId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://gitee.com/oauth/authorize?client_id=2026808b153886ae6c2c1a6bbabbf2de7f75e03588fcc6ce707e46c2c1e7d3be&redirect_uri=http://localhost:8887/callback&response_type=code&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                    console.log(response);
                }
            }
        },
        dataType: "json"
    });
}

/*
*展开二级评论
* */
function collapseComments() {
    // var id = $(e).data("id");
    // $("#comment-"+id).addClass("in");
    //在ul下添加li元素——从"/comment/{id}"地址获取后端传过来的JSON(data)
    var id = $("#comment_id").val();
    $.getJSON("/comment/" + id, function (data) {
        console.log(data);
        // var items = [];
        // $.each(data.data, function (key, val) {
        //     var li
        //     items.push("<li id='" + key + "'>" + val + "</li>");
        // });
        //
        // $("<ul/>", {
        //     "class": "my-new-list",
        //     html: items.join("")
        // }).appendTo("ul");
    });


}