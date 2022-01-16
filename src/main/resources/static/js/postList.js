function writePost(){
    let data = {
        title : document.getElementById("postTitle").value,
        content : document.getElementById("postArticle").value,
        boardId : document.getElementById("postBoardId").value
    }
    fetch("/board/auth/post/write",{
    method:"POST",
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
            if(data.error == null){
                alert("작성완료");
                location.href="http://localhost:8080/board/view/"+data.postId;
            }
            else{
                alert(data.error);
            }
    }).catch((error) => {
        alert("server error")
    });

}




window.onload = function(){
    let classes = document.getElementsByClassName("pageNum");
    let url = location.search;
    let parameters = new URLSearchParams(url);
    let pageNum = parameters.get('page');
    if(pageNum === null)
        pageNum = 0;

    if(pageNum != 0)
        pageNum = pageNum%5;
    classes[pageNum].classList.add('active');

}

