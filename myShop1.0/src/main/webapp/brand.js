$(function() {

        var dialog, form,
        brandName = $( "#brandName" ),
        categoryId = $( "#categoryId" ),
        allFields = $( [] ).add( brandName ).add(categoryId),
        tips = $( ".validateTips" );

        function updateTips( t ) {
        tips
        .text( t )
        .addClass( "ui-state-highlight" );
        setTimeout(function() {
            tips.removeClass( "ui-state-highlight", 1500 );
            }, 500 );
        }

        function checkLength( o, n, min, max ) {
        if ( o.val().length > max || o.val().length < min ) {
        o.addClass( "ui-state-error" );
        updateTips( "Length of " + n + " must be between " +
            min + " and " + max + "." );
        return false;
        } else {
            return true;
        }
        }

        function addBrand() {
            var valid = true;
            allFields.removeClass( "ui-state-error" );

            valid = valid && checkLength(brandName, "Name", 3, 15);

            alert(brandName.val());
            if ( valid ) {
                var $form = $( this ),
                    url = "http://172.20.105.121:8080/myShop1.0/rest/brand/insert";
                $.post( url, { brandName: brandName.val(),categoryId: categoryId.val()})
                      .done(function( data ) {
                                  alert( "Data Loaded: " + data );
                                    });

                dialog.dialog( "close" );
            }
            return valid;
        }

        dialog = $( "#dlog-form" ).dialog({
autoOpen: false,
height: 300,
width: 350,
modal: true,
buttons: {
"Create": addBrand,
Cancel: function() {
dialog.dialog( "close" );
}
},
close: function() {
form[ 0 ].reset();
allFields.removeClass( "ui-state-error" );
}
});

form = dialog.find( "form" ).on( "submit", function( event ) {
        event.preventDefault();
        });
$(function(){
$( "#add-brand" ).button().on( "click", function() {
        dialog.dialog( "open" );
        $.ajax({
            type: "GET",
            url: "http://172.20.105.121:8080/myShop1.0/rest/category/list",
            dataType: "json",
            success: function(resp) {
                $("#categoryId").find('option').remove();
                for(i=0; i < resp.length; i++){
                    $("#categoryId").append('<option value="'+resp[i].categoryId+'">'+resp[i].categoryName+'</option>');
                    }
                },
            error: function(e) {
                alert('My error: ' + e);
                }
            });
        });
});
});
