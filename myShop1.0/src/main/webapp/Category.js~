$(function() {

    var dialog, form,
        categoryName = $( "#categoryName" ),
        categoryDescription = $( "#categoryDescription" ),
        allFields = $( [] ).add( categoryName ).add( categoryDescription ),
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

    function addCategory() {
        var valid = true;
        allFields.removeClass( "ui-state-error" );

        valid = valid && checkLength(categoryName, "Name", 3, 15);
        valid = valid && checkLength(categoryDescription, "Description", 4, 100 );
        alert(categoryName.val());
        if ( valid ) {
            var $form = $( this ),
                url = "http://172.20.105.121:8080/myShop1.0/rest/category/insert";
            $.post( url, { categoryName: categoryName.val(), categoryDescription: categoryDescription.val() })
                .done(function( data ) {
                        alert( "Data Loaded: " + data );
                        });

            dialog.dialog( "close" );
        }
        return valid;
    }

    dialog = $( "#dialog-form" ).dialog({
autoOpen: false,
height: 300,
width: 350,
modal: true,
buttons: {
"Create": addCategory,
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
        $( "#add-category" ).button().on( "click", function() {
            dialog.dialog( "open" );
            });
        });
});

