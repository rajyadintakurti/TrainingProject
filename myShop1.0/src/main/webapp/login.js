$(function() {

    var dialog, form,
        emailRegex = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/,
        email = $( "#email" ),
        password = $( "#password" ),
        allFields = $( [] ).add( email ).add( password ),
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


    function checkRegexp( o, regexp, n ) {
    if ( !( regexp.test( o.val() ) ) ) {
      o.addClass( "ui-state-error" );
      updateTips( n );
      return false;
    } else {
      return true;
    }
  }

    function retrieveUsers() {
        var valid = true;
        allFields.removeClass( "ui-state-error" );

        valid = valid && checkRegexp( email, emailRegex, "email should be in this format: ui@jquery.com" );
        //valid = valid && checkLength(email, "email", 6, 80 );
        valid = valid && checkLength(password, "password", 6, 20);
        valid = valid && checkRegexp( password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );
        if ( valid ) {
            var $form = $( this ),
                url = "http://172.20.105.121:8080/myShop1.0/rest/signup/list";
            $.post( url, {email: email.val(), password: password.val()})
                .done(function( data ) {
                         if(data['userName'] != null){
                                   alert("Logged in successfully");
                                   //$('#jspUser').val(data.userName);  
                                   //window.location.assign("index1.jsp","_self","false");
                              }
                              else
                              {
                                   alert("Login failed");
                              }
                        });
            dialog.dialog( "close" );
        }
        return valid;
    }

    dialog = $( "#login-form" ).dialog({
autoOpen: false,
height: 300,
width: 350,
modal: true,
open: function (event, ui) { $(this).closest('.ui-dialog').find('.ui-dialog-titlebar-close').hide(); },
buttons: {
"Login": retrieveUsers,
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
        $( "#login" ).button().on( "click", function() {
            dialog.dialog( "open" );
            });
        });
});

