$(function () {
    $.ajax({
        type:'POST',
        url:'${pageContext.request.contextPath}/gateway/listall.action',
        dataType:'json',
        async:'true',
        data:'',
        success:function (result) {

        }
    })
});

function manageGateway() {
}

function manageLock() {

}