let code=null;
let tid;
let time = 0;
function joinRequest(){
    const data = {
            id : document.getElementById("username").value,
            password : document.getElementById("password").value,
            passwordConfirm : document.getElementById("passwordCheck").value,
            name : document.getElementById("name").value,
            email :  document.getElementById("email").value,
            code : code
        }
    fetch("/user/join",{
        method : "post",
        headers : { "Content-Type" : "application/json",
                    "Accept" : "application/json"},
        body : JSON.stringify(data)
    }).then(res => res.json())
    .then(data => {
        if(data.errors == null){
             alert("success");
             window.location.href="/";
        }
        else{
             document.getElementById("idValidation").innerText="";
             document.getElementById("nameValidation").innerText="";
             document.getElementById("passwordValidation").innerText="";
             document.getElementById("passwordConfirmValidation").innerText="";
             alert("server error");
             data.errors.forEach(error =>{
             if(error.field == 'username' )
                document.getElementById("idValidation").innerText=error.defaultMessage;
             if(error.field == 'name' )
                document.getElementById("nameValidation").innerText=error.defaultMessage;
             if(error.field == 'password')
                document.getElementById("passwordValidation").innerText=error.defaultMessage;
             if(error.field == 'passwordConfirm')
                document.getElementById("passwordConfirmValidation").innerText=error.defaultMessage;
             if(error.field == 'email')
                document.getElementById("emailValidation").innerText=error.defaultMessage;
             if(error.field == 'code')
                document.getElementById("emailValidation").innerText=error.defaultMessage;
     })
     }
  })
}
function codeSubmit(){
    let data =  {
        code : document.getElementById("codeCheck").value
     }
     fetch("/user/join/code/check",{
        method : "post",
        headers : {
            "Content-Type" : "application/json",
            "Accept" : "application/json"
        },
        body : JSON.stringify(data)
     }).then(res => res.json())
     .then(data => {
           if(data.result === 1){
                alert("코드인증 성공");
                code  = data.code;
           }
           else if(data.result === 0)
                alert("코드인증 실패 코드를 확인해주세요");

       })
}



function emailCheck(){
    let data ={
        email : document.getElementById("email").value
    }
    fetch("/user/join/email/send",{
        method : "post",
        headers :{
            "Content-Type" : "application/json"

        },
        body : JSON.stringify(data)
    }).then(res => res.json())
    .then(data => {
            if(data === 1)
                alert("이미 등록된 이메일입니다");
            else if(data === 0){
                alert("인증번호 전송");
                document.getElementById("codeCheck").style.display="block";
                document.getElementById("submit").style.display="block";
                TimerStart();
            }
    })
}

function TimerStart(){
time = 300;
if(tid != null)
    clearInterval(tid);
tid = setInterval("timer()",1000)};
function timer(){

    let msg = "<font color='red'>"+time+"</font>";
    document.getElementById("ViewTimer").innerHTML = msg;
    time--;
    if(time < 0){
        clearInterval(tid);
    }
}