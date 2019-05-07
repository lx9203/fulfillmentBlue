$(document).ready(function(){
  $('.view-radio-group-object').hide();
  $('.view-radio-group').change(
    function (){
  		if($('input:radio[id=transport]').is(':checked')){
      	$('.view-radio-group-object').show();
    	}else{
    		$('.view-radio-group-object').hide();
    	}
  	}
  );
});


$.datepicker.setDefaults({
    dateFormat: 'yy-mm-dd',
    prevText: '이전 달',
    nextText: '다음 달',
    monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    dayNames: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
    showMonthAfterYear: true,
    yearSuffix: '년'
});
$(function() {
    $("#datepicker1").datepicker();
});