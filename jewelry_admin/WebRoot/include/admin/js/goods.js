$(function(){
	 changePattern()
	 changePsPattern()
				
})
function changePattern () {
	var radioData = $('input:radio[name="pattern"]:checked').val();
    if (radioData === '0') {
    	// $("#onesale_container").css('display','block');
    	$("#onesale_container").removeAttr("style"); 
    	$("#presale_container").css('display','none'); 
    }else{
       $("#onesale_container").css('display','none'); 
       $("#presale_container").css('display','block');
       $("#presale_container").removeAttr("style");  
    }	
}
function changePsPattern () {
var radioData = $('input:radio[name="pre_sale_pattern"]:checked').val();
if (radioData === '2') {
    $("#all_sale_container").removeAttr("style"); 
	$("#some_sale_container").css('display','none'); 
}else{
     $("#all_sale_container").css('display','none'); 
	$("#some_sale_container").removeAttr("style"); 
}	

}								