function addBoard(){
    let selected = document.getElementById("selected");
    const data = {
        boardName : document.getElementById("boardname").value,
        id : selected.options[selected.selectedIndex].value
    }
    fetch("/admin/board/add",{
        method : "post",
        headers : {
            "Content-Type" : "application/json",
            "Accept" : "application/json"
        },
        body : JSON.stringify(data)
    }).then(res => res.json())
    .then(data => {
            if(data === 1 )
                alert("success");
            else
                alert("서버쪽에서 실패");
        })
}
