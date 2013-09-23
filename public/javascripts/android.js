var AndroidThreshold = 0;
var AndroidAvailableIDS = 0;

var Android = function(){};

Android.prototype = {
    getThreshold: function(){
        ajaxRequest("/people/below_threshold", "updateThreshhold");
    },
    
    getAvailableIds: function(){
        ajaxRequest("/people/available_ids", "updateAvailableIDs");
    },
    
    checkConnection: function(){
        ajaxRequest("/people/connected", "updateConnection");
    }
}


function ajaxRequest(url, action) {
    var httpRequest = new XMLHttpRequest();
    httpRequest.onreadystatechange = function() {
        if (httpRequest.readyState == 4 && httpRequest.status == 200) {
            if(typeof(action) != "undefined"){
                eval(action + "(" + httpRequest.responseText + ")");
            }
        }
    };
    
    try {
        httpRequest.open('GET', url, true);
        httpRequest.send(null);
    } catch(e){
    }
}

function updateThreshhold(below){
    if(__$("threshold")){
        if(eval(below)){
            __$("threshold").style.border = "2px solid red";
            __$("threshold").style.color = "red";
        } else {
            __$("threshold").style.border = "2px solid #38b038";
            __$("threshold").style.color = "#38b038";
        }
    }
}

function updateConnection(connected){
    if(eval(connected)){
        __$("network").setAttribute("src", "images/nup.png");
    } else {
        __$("network").setAttribute("src", "images/ndown.png");
    }
}

function updateAvailableIDs(value){
    __$("threshold").innerHTML = value;
}