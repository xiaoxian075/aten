<html>
<script type="text/javascript" src="/include/code/jquery-1.7.js"></script>
<body>
<div class="page-content">
    <div >
        <div  >
            <div >
                <div  id="wrapper">
                    <iframe id="mainframe" src="/tools/customizedPage/viewEdit?pageUnique=${info.pageUnique}" style="width:100%;height:100%;margin-left:-5px;margin-right:5px;overflow-x:hidden; " name="main" marginheight=0 frameborder="0"  scrolling="no"  ></iframe>
                </div>

            </div>
        </div>
    </div>
</div>
</body>
</html>
<script>
    //注意：下面的代码是放在和iframe同一个页面调用,放在iframe下面
    $("#mainframe").load(function () {
        var mainheight = $("#wrapper").height() - 55;
        alert(mainheight);
        $(this).height(mainheight);
    });
</script>