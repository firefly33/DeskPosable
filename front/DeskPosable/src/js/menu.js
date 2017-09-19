function selectMenuItem(item) {
    $('nav ul li').removeClass('active');
    $('nav ul '+item).addClass('active');
}
$(document).ready(function(){
   $('.submit-btn').click(function () {
       alert("ok");
       $('#form00').submit();
   }) ;
});
