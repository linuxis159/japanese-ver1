function updatePost(){
    let data = {
        title : document.getElementById("postTitle").value,
        content : document.getElementById("postArticle").value,
        id : document.getElementById("postId").value
    }
    fetch("/board/auth/post/update",{
    method:"PUT",
    headers:{
    "Content-Type" : "application/json"
    },
    body : JSON.stringify(data)
    }).then((res) => {
        if(res.ok){
            return res.json();
        } else {
            throw new Error('network error');
        }
    }).
    then(data => {
            alert("작성완료");
            location.href="http://localhost:8080/board/view/"+data;
    }).catch((error) => {
        alert("server error")
    });

}