//register 버튼 제어
//  1. 지역 항목 활성화
$(document).ready(function() {
  $('.view-radio-group').change(
    function (){
  		if($('input:radio[id=transport]').is(':checked')){
  			$('.view-radio-group-object').show();
    	}else{
    		$('.view-radio-group-object').hide();
    	}
  	})
});

//  2. 운송사 버튼 비활성화 : 서버에서 값을 받아 로딩 시 처리
var transSwitch = $('.on-off-control').attr('value');
if(transSwitch == 'off') {
	$('#transport').attr('disabled',true);
};
  
//파일 업로드 버튼 설정--------------
  $('input[type="file"]').change(function(e){
	  var fileName = e.target.files[0].name;
	  $('.displayFileName').attr('value',fileName); // input text에 가져온 파일이름 표시
  });
//-----------------------------------

$.datepicker.setDefaults({
    dateFormat: 'yy-mm-dd',
    prevText: '이전 달',
    nextText: '다음 달',
    monthNames: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
    monthNamesShort: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
    dayNames: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
    showMonthAfterYear: true,
    changeMonth: true,
	changeYear: true,
	yearRange: "-5:+0",
});

$(function() {
    $("#datepicker1").datepicker(
    );
});