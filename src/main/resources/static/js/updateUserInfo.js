let code = 0;
function updateName(){
    let name = document.getElementById('name').value;
    fetch('/user/auth/info/name/update/'+name,{
    method : "PUT",
    headers : {
        "Content-Type" : "application/json",
        "Accept" : "application/json"
    },
    }).then(res => {
        if(res.status == 200)
            alert("성공");
        else
            alert("ServerError");

    })

}
function emailCheck(){
    let data = document.getElementById("email").value;
    fetch("/user/auth/info/email/check/"+data,{
        method : "POST",
        headers : { "Content-Type" : "application/json"}
    }).then(res => res.json())
    .then(data =>{
        if(data === 1)
            alert("이미 등록된 이메일입니다");
        else if(data === 2){
            alert("인증코드 전송 *코드를 받지 못했을 경우 이메일을 확인해주세요");

            document.getElementById("codeCheck").style.display="block";
            document.getElementById("submit").style.display="block";
            document.getElementById("submit").setAttribute("onclick","codeSubmit()");

        }
        else{
            alert("알 수 없는 오류");
        }
    })

}
function codeSubmit(){
    let data = document.getElementById("codeCheck").value;
    fetch("/user/auth/info/code/check/"+data,{
        method : "POST",
        headers :{
            "Content-Type" : "application/json"
        }
    }).then(res => res.json())
    .then(data =>{
         if(data.result === 1){
            alert("인증 성공");
            let emailControl = document.getElementById("emailControl");
            emailControl.innerText = "변경";
            emailControl.setAttribute("onclick","emailUpdate()");
            document.getElementById("submit").style.display="none";
            document.getElementById("codeCheck").style.display="none";
            code = data.code;
            }

    })
}
function emailUpdate(){
    let data = {
        email : document.getElementById('email').value,
        code : code
    }
    fetch("/user/auth/info/email/update",{
        method : "PUT",
        headers : {
            "Content-Type" : "application/json"
        },
        body : JSON.stringify(data)
    }).then(res => res.json())
    .then(data => {
        if(data === 1)
            alert("변경성공")
        else
            alert("코드를 확인해주세요");

    })


}




