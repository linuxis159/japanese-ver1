function postDelete(){
    const postId =  event.target.id;
    fetch(
        "/board/auth/post/delete/"+postId,{
        method: "DELETE"}).
    then(response => response.json()).
    then(data => {
        if(data === 0)
            alert("삭제 실패");
        else if(data === 1){
            alert("삭제 성공");
            window.location.reload();
            }
        else
            alert("unknown error");

    })
}
