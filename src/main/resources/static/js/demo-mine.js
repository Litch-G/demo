function post() {
    var val = $("#publish-id").val();
    var text = $("#comment_content").val();

    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json",
        data:JSON.stringify({
            "parentId":val,
            "content":text,
            "type":1
        }),
        success:function (response) {
            if(response.code == 200){
             $("#comment-div").hide();
            }else {
                if(response.code == 2003){
                    var isLogin = confirm(response.message);
                    if(isLogin){
                        window.open("localhost:8808","登录","height=600, width=600, top=300, left=400, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no")
                    }
                }
                else alert(response.message);
            }
            console.log(response)
        },
        dataType:"json"
    });
}