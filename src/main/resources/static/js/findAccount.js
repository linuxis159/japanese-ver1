
function emailCheck(){
    let data ={
        email : document.getElementById("email").value
    }
    fetch("/user/findId/email/send",{
        method : "post",
        headers : {
            "Content-Type" : "application/json",
            "Accept" : "application/json"
        },
        body : JSON.stringify(data)
    }).then(res => res.json())
    .then(data => {
           if(data === 1){
                 alert("인증번호 전송");
                 document.getElementById("codeCheck").style.display="block";
                 document.getElementById("submit").style.display="block";
          }
           else if(data === 0){
              alert("이메일정보가 없습니다");
          }
            else
              alert("알 수 없는 오류");
            })

    }



function codeSubmit(){
    let data =  {
        code : document.getElementById("codeCheck").value
     }
     fetch("/user/findId/code/check",{
        method: "post",
        headers :   {
            "Content-Type" : "application/json"
        },
        body : JSON.stringify(data)})
        .then(res => res.json())
        .then(data => {
        if(data.result === 1){
            alert("코드인증 성공");
            code = result.code;
            location.href="/user/find/pass/"+result.sessionId;
        }
        else if(data.result === 0)
            alert("코드를 확인해주세요");
        })
 }



 function findPass(){
    const userId = {
        userId : document.getElementById("userId").value
    }
    fetch("/user/findPass/code/send",{
        method : "post",
        headers : {
            "Content-Type" : "application/json"
        },
        body : JSON.stringify(userId)
    }).then(res => res.json())
      .then(data => {
        if(data === 1){
            alert("코드전송");
            $("#codeCheck").css('display','block');
            $("#submit").css('display','block');
        }
        else if(data === 0)
            alert("존재하지 않는 ID입니다");

    })
 }


