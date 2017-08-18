var pageNo = 1;
var totalCount = 0;
var totalPages = 0;


$('#upPage').click(function () {
    if (pageNo > 1) {
        pageNo= pageNo -1;
        initTableData(pageNo);
    }
});
$('#downPage').click(function (){
    if (pageNo != totalPages) {
        pageNo= pageNo +1;
        initTableData(pageNo);
    }
});
$('#cx').click(function (){
    initTableData();
});