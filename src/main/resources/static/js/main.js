


let scrHig = $('section').height();

$(window).on('scroll',function(){

    let scr = $(window).scrollTop();
      
    if(scr >= 0 && scr < scrHig){
      $('nav').stop().animate({'right':'-100%'})
    }   
    if(scr >= scrHig){
      $('nav').stop().animate({'right':0})
    }


    function liClass(e){
      $('nav li').eq(e).addClass('on').siblings().removeClass('on')
  }

  if(scr >= 0 && scr < scrHig){
      liClass(0)
  }

  if(scr >= scrHig && scr < scrHig*2){
      liClass(1)
  }

  if(scr >= scrHig*2 && scr < scrHig*3){
      liClass(2)
  }

  if(scr >= scrHig*3 && scr < scrHig*4){
      liClass(3)
  } 

  if(scr >= scrHig*4){
      liClass(4)
  }

});


$('#Up').click(function(){

  $('html, body').stop().animate({'scrollTop' : scrHig * 1})

  
// https://westzero.tistory.com/112
String.prototype.toKorChars = function() { 
  var cCho = [ 'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' ], 
  cJung = [ 'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ' ], 
  cJong = [ '', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' ], cho, jung, jong; 
   
  var str = this, 
      cnt = str.length, 
      chars = [], 
      cCode; 
  for (var i = 0; i < cnt; i++) { 
    cCode = str.charCodeAt(i); 
    if (cCode == 32) { 
      chars.push(" ");
      continue;
    } 
    if (cCode < 0xAC00 || cCode > 0xD7A3) { 
      chars.push(str.charAt(i)); continue; 
    } 
    cCode = str.charCodeAt(i) - 0xAC00; 
 
    jong = cCode % 28; // 종성 
    jung = ((cCode - jong) / 28 ) % 21 // 중성 
    cho = (((cCode - jong) / 28 ) - jung ) / 21 // 초성 
 

    chars.push(cCho[cho]);
    chars.push(String.fromCharCode( 44032 + (cho * 588) + (jung  * 28)));
    if (cJong[jong] !== '') { 
       chars.push(String.fromCharCode( 44032 + (cho * 588) + (jung  * 28) + jong ));
    }
            
  } 
  return chars; 
 }
 
 
 //타이핑할 문장
    var result  = "나만의 이모티콘 생성 중...♥️";
    var typeing1=[];
    result = result.split(''); // 한글자씩자름
 
    //각글자 초성,중성,종성으로 나눔
    for(var i =0; i<result.length; i++){
      typeing1[i]=result[i].toKorChars();
    }
 
    //출력할 엘리먼트요소 가져옴 
    var resultDiv = document.getElementsByClassName("result")[0];
 
    var text = "";
    var i=0; 
    var j=0; 
 
    //총글자수
    var imax = typeing1.length;
 
    //setInterval을 이용해 반복적으로 출력 
    var inter = setInterval(typi,150);
 
 
    function typi(){
      //글자수만큼 반복후 종료 
      if(i<=imax-1){
        //각 글자가 초성 중성 종성 순서대로 추가되도록 
        var jmax = typeing1[i].length;
        resultDiv.innerHTML = text + typeing1[i][j];
        j++;
        if(j==jmax){
          text+=typeing1[i][j-1];
          //초성중성종성 순서대로 출력된 글자는 저장 ( 다음 글자와 이어붙이기 위해서 )
          i++;
          j=0;
         }
       } else{
         clearInterval(inter);
       }
     }

});

const $drop = document.querySelector(".dropBox");
const $title = document.querySelector(".dropBox h1");

$drop.ondrop = (e) => {
  e.preventDefault();
  $drop.className = "dropBox";
   
  const files = [...e.dataTransfer?.files];

  $title.innerHTML = files.map(v => v.name).join("<br>");
}

$drop.ondragover = (e) => {
  e.preventDefault();
}

$drop.ondragenter = (e) => {
  e.preventDefault();
 
  $drop.classList.add("active");
}

$drop.ondragleave = (e) => {
  e.preventDefault();
  
  $drop.classList.remove("active");
}

var typingBool = false; 
var typingIdx=0; 

