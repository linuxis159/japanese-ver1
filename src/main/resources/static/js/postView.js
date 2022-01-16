function postDelete(){
    const postId =  document.getElementById("postDelete").value;
    fetch(
    "/board/auth/post/delete/"+postId,{
    method: "DELETE"}).
    then(response => response.json()).
    then(data => {
        if(data === 0)
            alert("삭제 실패");
        else if(data === 1){
            alert("삭제 성공");
             location.href="/main";
            }
        else
            alert("unknown error");

    })
}

function writeComment(){
   let data = {
        postId : document.getElementById("postId").value,
        content : document.getElementById("content").value

    }
    fetch('/board/auth/comment/write',{
    method:"POST",
    headers:{ "Content-Type" : "application/json",
                   "Accept" : "application/json" },
    body: JSON.stringify(data)
    }).then(response => response.json()).then(data => {
        console.log(data);
        if(data != null){
            alert("작성성공");
            let commentArea = document.getElementById("commentArea");
            let comment = document.createElement("li");
            let username = document.createElement("div");
            let deleteButton = document.createElement("button");
            let createdDate = document.createElement("div");
            let content = document.createElement("div");

            commentArea.append(comment);
            comment.append(deleteButton);
            comment.append(username);
            comment.append(createdDate);
            comment.append(content);


            comment.setAttribute('id',data.id);
            comment.setAttribute('class','comment');
            deleteButton.setAttribute('class','btn btn-sm btn-primary btn-comment-delete');
            deleteButton.setAttribute('onclick','deleteComment()');
            deleteButton.setAttribute('value',data.id);
            content.setAttribute('class','comment-article');


            username.innerText = data.user.name;
            createdDate.innerText = data.createdDate;
            content.innerText = data.content;
            deleteButton.innerText = "삭제";
        }
        else
            alert("ServerError");


    })
 }

function deleteComment(){
    let deleteButton = event.target;
    let commentId = deleteButton.value;
    fetch("/board/auth/comment/delete/"+commentId,{
        method : "DELETE",
        headers : {
            "Content-Type" : "application/json",
            "Accept" : "application/json"
        }
    }).then(res =>{
    if(res.status == 200){
        let commentArea = document.getElementById('commentArea');
        let comment = document.getElementById(commentId);
        commentArea.removeChild(comment);
        alert("성공");
    }
    else{
        alert("실패");
    }
    })
}

function recommend(){
    const id = document.getElementById("recommend").value;



    fetch("/board/auth/recommend/vote/"+id,{
        method: "POST",
        headers:{ "Content-Type" : "application/json",
                    "Accept" : "application/json"}
    }).then(response => response.json()).
    then(data =>{
        console.log(data.resultState);
        if(data.resultState == 1){

                alert("성공");
                document.getElementById("recommend").innerText = "추천:"+data.recommendCount;
        }
        else{
            alert("ServerError");
        }
    })
}

