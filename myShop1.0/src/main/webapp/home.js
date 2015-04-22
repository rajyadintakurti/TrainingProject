$.ajax({
        type: "GET",
        url: "http://172.20.105.121:8080/myShop1.0/rest/category/list",
        dataType: "json",
        success: function(resp) {
        $("#category").find('option').remove();
                for(i=0; i < resp.length; i++){
                        $("#category").append('<option value="'+resp[i].categoryId+'">'+resp[i].categoryName+'</option>');
                }
        },
        error: function(e) {
                alert('My error: ' + e);
        }
});

$(function() {
        $("#category").change(function() {
                var categoryId = $(this).val();
                //alert(typeFeed);
                $.ajax({
                        type: "GET",
                        url: "http://172.20.105.121:8080/myShop1.0/rest/brand/list/" + categoryId,
                        dataType: "json",
                        success: function(resp) {
                                $("#brand").find('option').remove();
                                for(i=0; i < resp.length; i++){
                                        $("#brand").append('<option value="'+ resp[i].brandId +'">'+ resp[i].brandName +'</option>');
                                }
                        },
                        error: function(e) {
                                alert('Brand error: ' + e);
                        }
                });
         });
});

$(function() {
        $("#brand").change(function() {
                var brandId=$(this).val();
                //alert(typeFeed);
                $.ajax({
                        type: "GET",
                        url: "http://172.20.105.121:8080/myShop1.0/rest/model/list/" + brandId + "/",
                        dataType: "json",
                        success: function(resp) {
                                $("#model").find('option').remove();
                                for(i=0; i < resp.length; i++){
                                        $("#model").append('<option value="'+ resp[i].modelId +'">'+ resp[i].modelName +'</option>');
                                }
                        },
                        error: function(e) {
                                alert('Model error: ' + e);
                        }
                });
         });
});
