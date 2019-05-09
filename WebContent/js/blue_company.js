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
    monthNames: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
    monthNamesShort: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
    dayNames: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
    showMonthAfterYear: true,
    changeMonth: true,
	  changeYear: true,
});


$(function() {
    $("#datepicker1").datepicker(
    );
});

$(function() {
    /* MonthPicker 옵션 */
    var currentYear = (new Date()).getFullYear();
    var startYear = currentYear-5;

    var options = {
            startYear: startYear,
            finalYear: currentYear,
            pattern: 'yyyy-mm',
            monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
    };

		/* MonthPicker Set */
		$('#monthpicker').monthpicker(options);

		/* 버튼 클릭시 MonthPicker Show */
//		$('#btn_monthpicker').bind('click', function () {
//			$('#monthpicker').monthpicker('show');
//		});
});
