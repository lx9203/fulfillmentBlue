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
