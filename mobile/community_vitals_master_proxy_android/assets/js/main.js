var db = null;
var current_case_upper = true;

var current_question = 0;
var order = [];
var options = {};
var kybdnumeric = false;
var showKeys = true;
var selected = "";
         
function html5_storage_support() {
    try {
        return 'localStorage' in window && window['localStorage'] !== null;
    } catch (e) {
        return false;
    }
}

function doLogout(){
    if(navigator.userAgent.toLowerCase().match(/android/)){
        var token = Android.getToken();

        Android.doLogout(token);
    } else {
        sessionStorage.clear();
    }

    window.location = "login.html";
}

function doLogin(){

    if(navigator.userAgent.toLowerCase().match(/android/)){
        
        var token = Android.doLogin(__$("1.2").value.trim(), __$("1.3").value.trim());

        if(token.trim().length > 0){
            Android.setPref("username", __$("1.2").value.trim());
            Android.setPref("usertype", __$("1.4").value.trim());
            Android.setPref("location", __$("1.5").value.trim());
            Android.setPref("locale", __$("1.1").value.trim());

            Android.setPref("current_category", __$("1.4").selectedIndex);

            window.location = "index.html";
            
            return;
        }

    } else {
        if(typeof(localStorage.users) != "undefined"){
            var tmpUsers = localStorage.users.split("|");

            for(var i = 0; i < tmpUsers.length; i++){
                var terms = tmpUsers[i].split(":");

                users[terms[0]] = terms[1];
            }
        }

        if(typeof(users[__$("1.2").value]) == "undefined"){

            showMessage(words[(typeof(sessionStorage["locale"]) != "undefined" ?
                sessionStorage["locale"] : "en")]["user_does_not_exist"]);

        } else if(users[__$("1.2").value.trim()] != MD5(__$("1.3").value.trim())){

            showMessage(words[(typeof(sessionStorage["locale"]) != "undefined" ?
                sessionStorage["locale"] : "en")]["wrong_password"]);

        } else {
            sessionStorage.username = __$("1.2").value.trim();
            sessionStorage.password = MD5(__$("1.3").value.trim());
            sessionStorage.usertype = __$("1.4").value.trim();
            sessionStorage.location = __$("1.5").value.trim();

            sessionStorage.current_category = __$("1.4").selectedIndex;

            document.forms[0].action += "?l=" + (typeof(sessionStorage["locale"]) != "undefined" ?
                sessionStorage["locale"] : "en");

            document.forms[0].submit();
        }
    }
}

function changeLang(){
    if(navigator.userAgent.toLowerCase().match(/android/)){
        Android.setPref("locale", __$("language").value.trim());

        window.location = "login.html?l=" + __$("language").value;
    } else {
        sessionStorage["locale"] = __$("language").value.trim();

        window.location = "login.html?l=" + (typeof(sessionStorage["locale"]) != "undefined" ?
            sessionStorage["locale"] : "en");
    }
}

function checkLogin(){

    if(window.location.href.match(/login/)){
        if(navigator.userAgent.toLowerCase().match(/android/)){
            var token = Android.getToken();

            if(token.trim().length > 0){
                window.location = "index.html";
            }
        }
        return;
    }

    if(navigator.userAgent.toLowerCase().match(/android/)){
        // var token = window.location.href.match(/t=([^\&]+)/);
        var token = Android.getToken();
        
        if(token.trim().length > 0){
            
            var authenticated = Android.validToken(token);

            if(authenticated){
                return;
            } else {
                window.location = "login.html";
                
                return;
            }
        } else {
            window.location = "login.html";

            return;
        }
        
    } else {
        if(typeof(sessionStorage["username"]) == "undefined" || typeof(sessionStorage["password"]) == "undefined"){
            window.location = "login.html?l=" + (typeof(sessionStorage["locale"]) != "undefined" ?
                sessionStorage["locale"] : "en");
        } else if(sessionStorage["username"].trim() == ""){
            window.location = "login.html?l=" + (typeof(sessionStorage["locale"]) != "undefined" ?
                (sessionStorage["locale"].trim() != "" ? sessionStorage["locale"] : "") : "en");
        }
    }
}

function __$(id){
    return document.getElementById(id);
}

function loadLocale(){
    
    if(navigator.userAgent.toLowerCase().match(/android/)){
        
        var lang = Android.getPref("locale");

        lang = (lang.trim().length > 0 ? lang : "en");
        
    } else {
        var params = window.location.href.match(/\?(.+)/);
        var lang = (typeof(sessionStorage["locale"]) != "undefined" ?
            (sessionStorage["locale"].trim() != "" ? sessionStorage["locale"] : "") : "en");
    
        if(params != null){
            var paramslist = params[1].split("&");
        
            for(var i = 0; i < paramslist.length; i++){
                var fld = paramslist[i].split("=");

                if(fld[0].toLowerCase() == "l" && fld[1].trim() != ""){
                    lang = fld[1].toLowerCase();
                    break;
                }
            }
        }
    }
    
    if(lang.trim() != ""){
        if(navigator.userAgent.toLowerCase().match(/android/)){
            Android.setPref("locale", lang);
        } else {
            sessionStorage["locale"] = lang;
        }

        if(typeof(words) != "undefined"){
            for(var word in words[lang]){
                if(__$("lbl_" + word)){
                    __$("lbl_" + word).innerHTML = words[lang][word];

                    if(word.toLowerCase() == "user_type"){
                        __$("1.4").innerHTML = "<option></option>";

                        for(var o = 0; o < words[lang]["roles"].length; o++){
                            var opt = document.createElement("option");
                            opt.innerHTML = words[lang]["roles"][o];

                            __$("1.4").appendChild(opt);
                        }
                    } else if(word.toLowerCase() == "login"){
                        if(__$("fld_submit")){
                            __$("fld_submit").innerHTML = words[lang][word];
                        }
                    }
                } else if(__$("fld_" + word)){
                    __$("fld_" + word).innerHTML = words[lang][word];
                }
            }
        }

        if(navigator.userAgent.toLowerCase().match(/android/)){
            var category = words[lang]["roles"][parseInt(Android.getPref("current_category"))];
            
            Android.setPref("usertype", category);

            if(__$("lbl_usertype"))
                __$("lbl_usertype").innerHTML = category;

        } else {
            var category = words[sessionStorage.locale]["roles"][parseInt(sessionStorage.current_category) - 1];

            sessionStorage.usertype = category;

            if(__$("lbl_usertype"))
                __$("lbl_usertype").innerHTML = (typeof(sessionStorage["usertype"]) != "undefined" ?
                    sessionStorage["usertype"] : "");

        }

        if(navigator.userAgent.toLowerCase().match(/android/)){
            var cat = parseInt(Android.getPref("current_category"));
        } else {
            var cat = parseInt((typeof(sessionStorage["current_category"]) != "undefined" ?
                sessionStorage["current_category"] : 2));
        }

        switch(cat){
            case 0:
                if(__$("ta"))
                    __$("ta").style.display = "row";

                if(__$("gvh"))
                    __$("gvh").style.display = "none";

                if(__$("vh"))
                    __$("vh").style.display = "none";
                break;
            case 1:
                if(__$("ta"))
                    __$("ta").style.display = "none";

                if(__$("gvh"))
                    __$("gvh").style.display = "row";

                if(__$("vh"))
                    __$("vh").style.display = "none";
                break;
            case 2:
                if(__$("ta"))
                    __$("ta").style.display = "none";

                if(__$("gvh"))
                    __$("gvh").style.display = "none";

                if(__$("vh"))
                    __$("vh").style.display = "row";
                break;
        }

    }

}

function getCurrentCategory(){
    if(navigator.userAgent.toLowerCase().match(/android/)){
        var cat = parseInt(Android.getPref("current_category"));
    } else {
        var cat = parseInt((typeof(sessionStorage["current_category"]) != "undefined" ?
            sessionStorage["current_category"] : 0));
    }

    return cat;
}

function showKeyboard(numeric, showAgain){
    if(typeof(showAgain) == "undefined")
        showAgain = false;

    if(__$("keyboard")){
        __$("keyboard_container").removeChild(__$("keyboard"));

        if(!showAgain)
            return;
    } else {        
        if(!showAgain)
            return;
    }
 
    var keyboard = document.createElement("div");
    keyboard.className = "keyboard";
    keyboard.id = "keyboard";

    __$("keyboard_container").appendChild(keyboard);

    var rows = [["Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "ABC"],
    ["A", "S", "D", "F", "G", "H", "J", "K", "L", "Enter", "cap"],
    ["Z", "X", "C", "V", "B", "N", "M", ",", ".", "Space", "Unknown"],
    ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Del"]];

    if(navigator.userAgent.toLowerCase().match(/android/)){
        var prefered_keyboard = Android.getPref("prefered_keyboard");
    } else {
        var prefered_keyboard = (typeof(sessionStorage.prefered_keyboard) != "undefined" ?
            sessionStorage.prefered_keyboard : "")
    }

    if(prefered_keyboard == "abc"){
        rows = [["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "QWERTY"],
        ["K", "L", "M", "N", "O", "P", "Q", "R", "S", "Enter", "cap"],
        ["T", "U", "V", "W", "X", "Y", "Z", ",", ".", "Space", "Unknown"],
        ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Del"]];
    }

    if(typeof(numeric) == "undefined")
        numeric = false;
        
    if(numeric){
        
        var groups = [
        ["1", "2", "3", "Unknown"],
        ["4", "5", "6", ""],
        ["7", "8", "9", ""],
        ["", "0", ".", "Del"]
        ];

        for(var j = 0; j < groups.length; j++){
            var krow = document.createElement("div");
            krow.className = "row";

            keyboard.appendChild(krow);

            for(var i = 0; i < groups[j].length; i++){
                
                var cell = document.createElement("div");
                cell.style.display = "table-cell";
                cell.style.minWidth = "50px";
                cell.style.minHeight = "50px";

                krow.appendChild(cell);

                if(groups[j][i].trim().length == 0)
                    continue;
                
                var btn = document.createElement("button");
                btn.className = "keyboard_button blue";
                btn.id = "lbl_" + groups[j][i].trim().toLowerCase();

                // btn.innerHTML = groups[j][i];

                if(groups[j][i].toLowerCase() != "cap" && groups[j][i].toLowerCase() != "unknown" &&
                    groups[j][i].toLowerCase() != "del" && groups[j][i].toLowerCase() != "abc" &&
                    groups[j][i].toLowerCase() != "qwerty" && groups[j][i].toLowerCase() != "space"){

                    btn.innerHTML = (current_case_upper ? groups[j][i] : groups[j][i].toLowerCase());
                    btn.style.minWidth = "70px";

                } else if(groups[j][i].toLowerCase() == "space"){
                    btn.innerHTML = "<img src='images/space.png' height='32' alt='Space' style='margin: -6px;' />";
                    btn.setAttribute("tag", "space");
                    cell.style.verticalAlign = "middle";
                } else if(groups[j][i].toLowerCase() == "enter"){
                    btn.innerHTML = "<img src='images/enter.png' height='32' alt='Enter' style='margin: -6px;' />";
                    btn.setAttribute("tag", "enter");
                    cell.style.verticalAlign = "middle";
                } else if(groups[j][i].toLowerCase() == "cap"){
                    btn.innerHTML = (!current_case_upper ? groups[j][i].toUpperCase() : groups[j][i].toLowerCase());
                    btn.style.minWidth = "120px";
                } else {
                    btn.innerHTML = groups[j][i];
                    btn.style.minWidth = "120px";
                }


                btn.onmousedown = function(){
                    if(this.innerHTML == "Del"){
                        if(__$("inputField").value.trim().toLowerCase() == "unknown"){
                            __$("inputField").value = "";
                        } else {
                            __$("inputField").value = __$("inputField").value.trim().substr(0,
                                __$("inputField").value.trim().length - 1);
                        }
                    } else {
                        if(__$("inputField").value.trim().toLowerCase() == "unknown"){
                            __$("inputField").value = this.innerHTML;
                        } else if(this.innerHTML.trim().toLowerCase() == "unknown"){
                            __$("inputField").value = this.innerHTML;
                        } else {
                            __$("inputField").value += this.innerHTML;
                        }
                    }
                }

                cell.appendChild(btn);
            }
        }
    } else {

        for(var j = 0; j < rows.length; j++){
            var krow = document.createElement("div");
            krow.className = "row";

            keyboard.appendChild(krow);

            for(var i = 0; i < rows[j].length; i++){

                var cell = document.createElement("div");
                cell.style.display = "table-cell";
                cell.style.minWidth = "50px";
                cell.style.minHeight = "50px";

                krow.appendChild(cell);

                if(rows[j][i].trim().length == 0)
                    continue;

                var btn = document.createElement("button");
                btn.className = "keyboard_button blue";
                btn.id = "lbl_" + rows[j][i].trim().toLowerCase();

                if(rows[j][i].toLowerCase() != "cap" && rows[j][i].toLowerCase() != "unknown" &&
                    rows[j][i].toLowerCase() != "del" && rows[j][i].toLowerCase() != "abc" &&
                    rows[j][i].toLowerCase() != "qwerty" && rows[j][i].toLowerCase() != "space" &&
                    rows[j][i].toLowerCase() != "enter"){

                    btn.innerHTML = (current_case_upper ? rows[j][i] : rows[j][i].toLowerCase());
                    btn.style.minWidth = "70px";
                    
                } else if(rows[j][i].toLowerCase() == "space"){
                    btn.innerHTML = "<img src='images/space.png' height='32' alt='Space' style='margin: -6px;' />";
                    btn.setAttribute("tag", "space");
                    cell.style.verticalAlign = "middle";
                } else if(rows[j][i].toLowerCase() == "enter"){
                    btn.innerHTML = "<img src='images/enter.png' height='32' alt='Enter' style='margin: -6px;' />";
                    btn.setAttribute("tag", "enter");
                    cell.style.verticalAlign = "middle";
                } else if(rows[j][i].toLowerCase() == "cap"){
                    btn.innerHTML = (!current_case_upper ? rows[j][i].toUpperCase() : rows[j][i].toLowerCase());
                    btn.style.minWidth = "120px";
                } else {
                    btn.innerHTML = rows[j][i];
                    btn.style.minWidth = "120px";
                }

                btn.onmousedown = function(){

                    if(this.innerHTML == "Del"){
                        if(__$("inputField").value.trim().toLowerCase() == "unknown"){
                            __$("inputField").value = "";
                        } else {
                            __$("inputField").value = __$("inputField").value.trim().substr(0,
                                __$("inputField").value.trim().length - 1);
                        }
                    } else if(this.innerHTML.toLowerCase() == "cap"){
                        current_case_upper = !current_case_upper;

                        showKeyboard(false, true);
                    } else if(this.innerHTML.toLowerCase() == "abc"){
                        if(navigator.userAgent.toLowerCase().match(/android/)){
                            Android.setPref("prefered_keyboard", "abc");
                        } else {
                            sessionStorage.prefered_keyboard = "abc";
                        }

                        showKeyboard(false, true);
                    } else if(this.innerHTML.toLowerCase() == "qwerty"){
                        if(navigator.userAgent.toLowerCase().match(/android/)){
                            Android.setPref("prefered_keyboard", "qwerty");
                        } else {
                            sessionStorage.prefered_keyboard = "qwerty";
                        }

                        showKeyboard(false, true);
                    } else if(this.getAttribute("tag") == "space") {
                        if(__$("inputField").value.trim().toLowerCase() == "unknown"){
                            __$("inputField").value = "";
                        } else {
                            __$("inputField").value +=  " ";
                        }
                    } else if(this.getAttribute("tag") == "enter") {
                        if(__$("inputField").value.trim().toLowerCase() == "unknown"){
                            __$("inputField").value = "";
                        } else {
                            __$("inputField").value +=  "\n";
                        }
                    } else {
                        if(__$("inputField").value.trim().toLowerCase() == "unknown"){
                            __$("inputField").value = this.innerHTML;
                        } else if(this.innerHTML.trim().toLowerCase() == "unknown"){
                            __$("inputField").value = this.innerHTML;
                        } else {
                            __$("inputField").value += this.innerHTML;
                        }
                    }

                    if(current_case_upper && this.id != "lbl_cap"){
                        current_case_upper = !current_case_upper;

                        showKeyboard(false, true);
                    }

                    var dynamicLoader = __$("inputField").getAttribute("dynamicLoader");
                    
                    if(dynamicLoader){
                        eval(dynamicLoader);
                    }
                }

                cell.appendChild(btn);
            }
        }

    }
}

function expandSpec(){
    var lines = ajaxGeneralRequestResult.split("\n");

    order = [];
    form_props = {};

    // ["Q.1.3. Last Name [pos => 2", "1.3.", "Last Name ", "[pos => 2", "pos => 2"]
    for(var i = 0; i < lines.length; i++){
        if(lines[i].trim().match(/^Q./i)){
            var label = lines[i].trim().match(/^Q\.([^\s]+)\s([^\[]+)(\[([^\]]+))?/);

            if(label){
                var id = label[1].trim().substr(0, label[1].trim().length - 1);
                var lbl = label[2].trim();
                var params = {};

                if(typeof(label[4]) != "undefined"){
                    var inputs = label[4].trim().split("$$");

                    for(var k = 0; k < inputs.length; k++){
                        var terms = inputs[k].split("=>");

                        params[terms[0].trim()] = terms[1].trim().replace(/"/g, "'");
                    }
                }

                var pos = (typeof(params["pos"]) != "undefined" ? (!isNaN(params["pos"]) ?
                    eval(params["pos"]) : params["pos"]) : 0);

                order.push([pos, id, lbl, params]);
            }
        } else if(lines[i].trim().match(/^O./i)){
            var opts = lines[i].trim().match(/^O\.([^\s]+)\s(.+)/);

            // O.1.4.1. Male,1.4.1.,Male
            if(opts){
                var id = opts[1].trim().replace(/\.$/, "");
                var parent_id = opts[1].trim().replace(/\.\d+\.$/, "");
                var value = opts[2].trim().trim().match(/([^\{]+)(\{([^\}]+))/);

                if(typeof(options[parent_id]) == "undefined"){
                    options[parent_id] = [(value ? [value[1], value[3]] : [opts[2].trim()])];
                } else {
                    options[parent_id].push((value ? [value[1], value[3]] : [opts[2].trim()]));
                }

            }
        } else if(lines[i].trim().match(/^P./i)){
            var title = lines[i].trim().match(/^P\.([^\s]+)\s([^\[]+)(\[([^\]]+))?/);

            if(title){
                if(typeof(title[4]) != "undefined"){
                    var params = title[4].split("$$");

                    for(var e = 0; e < params.length; e++){
                        var parts = params[e].split("=>");

                        if(parts[0].trim().toLowerCase() == "method" || parts[0].trim().toLowerCase() == "action"){
                            form_props[parts[0].trim().toLowerCase()] = parts[1].trim();
                        }
                    }
                }
            }
        }
    }

    order = order.sort();

    showPage(current_question);

    var frm = document.createElement("form");
    frm.style.display = "none";

    for(var p in form_props){
        frm.setAttribute(p, form_props[p])
    }
    
    document.body.appendChild(frm);

    for(var s = 0; s < order.length; s++){
        var div = document.createElement("div");
        div.style.padding = "10px";
        div.style.border = "solid 1px #ccc";
        div.style.margin = "5px";
        div.style.display = "table";
        div.style.borderSpacing = "10px";

        frm.appendChild(div);

        var row = document.createElement("div");
        row.style.display = "table-row";

        div.appendChild(row);

        var cell1 = document.createElement("div");
        cell1.style.display = "table-cell";
        cell1.innerHTML = order[s][2];
        cell1.id = "lbl_" + order[s][1];

        row.appendChild(cell1);

        var cell2 = document.createElement("div");
        cell2.style.display = "table-cell";

        var field_type = (typeof(order[s][3]["field_type"]) != "undefined" ? order[s][3]["field_type"] : "text");

        if(typeof(options[order[s][1]]) != "undefined"){
            var select = document.createElement("select");
            select.id = order[s][1];
            select.setAttribute("helpText", order[s][2]);

            cell2.appendChild(select);

            for(var o = 0; o < options[order[s][1]].length; o++){
                var opt = document.createElement("option");

                if(options[order[s][1]][o].length > 1){
                    opt.innerHTML = options[order[s][1]][o][0];
                    opt.setAttribute("value", options[order[s][1]][o][1]);
                } else {
                    opt.innerHTML = options[order[s][1]][o][0];
                }

                select.appendChild(opt);
            }
        } else if(field_type.toLowerCase() == "textarea") {
            input = document.createElement("textarea");
            input.setAttribute("helpText", order[s][2]);
            input.id = order[s][1];

            for(var a in order[s][3]){
                input.setAttribute(a, order[s][3][a]);
            }

            cell2.appendChild(input);

        } else {
            var input = document.createElement("input");
            input.type = "input";
            input.id = order[s][1];
            input.setAttribute("helpText", order[s][2]);

            cell2.appendChild(input);
        }

        row.appendChild(cell2);
        
    }
}

function showPage(s, back){

    if(typeof(order[s]) == "undefined"){
        return;
    }

    if(typeof(order[s][3]) != "undefined"){
        if(typeof(order[s][3]["condition"]) != "undefined"){
            if(eval(order[s][3]["condition"]) == false){
                if((typeof(back) != "undefined" ? back : false) == false){
                    current_question += 1;
                } else {
                    current_question -= 1;
                }
                return showPage(current_question, (typeof(back) != "undefined" ? back : false));
            }
        }
    }

    if(typeof(order[s][3]["field_type"]) != "undefined"){
        if(order[s][3]["field_type"].toLowerCase() == "number"){
            kybdnumeric = true;
        } else {
            kybdnumeric = false
        }
    } else {
        kybdnumeric = false
    }

    __$("parent").style.minHeight = "500px";

    __$("parent").innerHTML = "";

    var div = document.createElement("div");
    div.style.margin = "5px";
    div.style.display = "table";
    div.style.borderSpacing = "10px";
    div.style.width = "100%";

    __$("parent").appendChild(div);

    var row = document.createElement("div");
    row.style.display = "table-row";

    div.appendChild(row);

    var row2 = document.createElement("div");
    row2.style.display = "table-row";

    div.appendChild(row2);

    var row3 = document.createElement("div");
    row3.style.display = "table-row";

    div.appendChild(row3);

    var cell3 = document.createElement("div");
    cell3.style.display = "table-cell";
    cell3.style.color = "#254061";
    cell3.style.fontSize = "1.5em";
    cell3.style.padding = "10px";
    cell3.style.paddingTop = "0px";
    cell3.id = "dropdowns"

    row3.appendChild(cell3);

    var cell1 = document.createElement("div");
    cell1.style.display = "table-cell";
    cell1.innerHTML = order[s][2];
    cell1.style.color = "#254061";
    cell1.style.fontSize = "1.8em";
    cell1.style.padding = "10px";
    cell1.style.paddingBottom = "0px";
    cell1.style.textAlign = "left";

    row.appendChild(cell1);

    var cell2 = document.createElement("div");
    cell2.style.display = "table-cell";
    cell2.style.color = "#6281A7";
    cell2.style.padding = "10px";
    cell2.style.paddingTop = "0px";
    cell2.style.paddingBottom = "0px";

    var input;    

    var field_type = (typeof(order[s][3]["field_type"]) != "undefined" ? order[s][3]["field_type"] : "text");

    if(typeof(options[order[s][1]]) != "undefined"){
        input = document.createElement("input");
        input.setAttribute("srcControl", order[s][1]);
        input.setAttribute("helpText", order[s][2]);
        input.id = "inputField";

        for(var a in order[s][3]){
            input.setAttribute(a, order[s][3][a]);
        }

        cell2.appendChild(input);

        if(typeof(order[s][3]["multiple"]) != "undefined"){
            if(order[s][3]["multiple"] == "multiple" || order[s][3]["multiple"] == "true"){
                loadMultipleSelect(options[order[s][1]]);
            } else {
                loadSingleSelect(options[order[s][1]]);
            }
        } else {
            loadSingleSelect(options[order[s][1]]);
        }

        showKeys = false;
    } else if(field_type.toLowerCase() == "textarea") {
        input = document.createElement("textarea");
        input.setAttribute("srcControl", order[s][1]);
        input.setAttribute("helpText", order[s][2]);
        input.id = "inputField";
        input.style.height = "200px";

        for(var a in order[s][3]){
            input.setAttribute(a, order[s][3][a]);
        }

        cell2.appendChild(input);

        showKeys = true;
    } else if(field_type.toLowerCase() == "password") {

        input = document.createElement("input");
        input.type = "password";
        input.setAttribute("srcControl", order[s][1]);
        input.setAttribute("helpText", order[s][2]);
        input.id = "inputField";

        for(var a in order[s][3]){
            input.setAttribute(a, order[s][3][a]);
        }

        cell2.appendChild(input);

        showKeys = true;
        
    } else {

        input = document.createElement("input");
        input.type = "input";
        input.setAttribute("srcControl", order[s][1]);
        input.setAttribute("helpText", order[s][2]);
        input.id = "inputField";

        for(var a in order[s][3]){
            input.setAttribute(a, order[s][3][a]);
        }

        cell2.appendChild(input);

        showKeys = true;
    }

    if(navigator.userAgent.toLowerCase().match(/android/)){
        var sk = Android.getPref("showKeys");

        showKeys = (sk.trim().length > 0 ? sk : showKeys);
    } else {
        showKeys = (typeof(localStorage.showKeys) != "undefined" ? localStorage.showKeys : showKeys);
    }

    input.onkeydown = function(event){
        if (event.keyCode == 13)
            document.getElementById('next').click()
    }

    input.style.color = "#254061";
    input.style.fontSize = "1.5em";
    input.style.padding = "10px";
    input.style.borderRadius = "10px";
    input.style.border = "1px solid #6281A7";
    input.style.width = "100%";

    row2.appendChild(cell2);

    if(input.getAttribute("tt_onLoad") != null){
        eval(input.getAttribute("tt_onLoad"));
    }

    if(__$(input.getAttribute('srcControl')))
        input.value = __$(input.getAttribute('srcControl')).value;

    input.focus();

    showKeyboard(kybdnumeric, showKeys);
    
    return "";
}

function ajaxGeneralRequest(aUrl, method) {
    if(navigator.userAgent.toLowerCase().match(/android/)){

        var result = Android.ajaxRead(aUrl);

        ajaxGeneralRequestResult = result;

        eval(method);

    } else {
        var httpRequest = new XMLHttpRequest();
        httpRequest.onreadystatechange = function() {
            handleGeneralResult(httpRequest, method);
        };
        try {
            httpRequest.open('GET', aUrl, true);
            httpRequest.send(null);
        } catch(e){
        }
    }
}

function handleGeneralResult(aXMLHttpRequest, method) { 

    if (!aXMLHttpRequest) return "error";

    if (aXMLHttpRequest.readyState == 4 && (aXMLHttpRequest.status == 200 || aXMLHttpRequest.status == 304)) {
        var result = aXMLHttpRequest.responseText;

        ajaxGeneralRequestResult = result;

        eval(method);

        return ajaxGeneralRequestResult;
    }
    return "";
}

function generateDays(year, month){
    if(__$("calendar")){
        __$("parent").removeChild(__$("calendar"));
    }

    var calendar = document.createElement("div");
    calendar.id = "calendar";
    calendar.style.position = "absolute";
    calendar.style.bottom = "95px";
    calendar.style.width = "98%";

    __$("parent").appendChild(calendar);

    var keyboard = document.createElement("div");
    keyboard.style.margin = "auto";
    keyboard.style.display = "table";
    keyboard.style.backgroundColor = "#fff";
    keyboard.style.border = "1px solid #ccc";
    keyboard.style.borderRadius = "10px";
    keyboard.style.padding = "10px";

    calendar.appendChild(keyboard);

    var months = {
        "January":[31, 0],
        "February":[(parseInt(__$(year).value) % 4 > 0 ? 28 : 29), 1],
        "March":[31, 2],
        "April":[30, 3],
        "May":[31, 4],
        "June":[30, 5],
        "July":[31, 6],
        "August":[31, 7],
        "September":[30, 8],
        "October":[31, 9],
        "November":[30, 10],
        "December":[31, 11]
    }

    var date = new Date(parseInt(__$(year).value), parseInt(months[__$(month).value][1]), 1);

    var row1 = document.createElement("div");
    row1.style.display = "table-row";

    keyboard.appendChild(row1);

    var days = ["S", "M", "T", "W", "T", "F", "S", ""];

    for(var d = 0; d < days.length; d++){
        var day = document.createElement("div");
        day.style.display = "table-cell";
        day.style.padding = "20px";
        day.innerHTML = days[d];

        row1.appendChild(day);
    }

    var p = date.getDay();
    var count = 1;

    for(var w = 0; w <= 6; w++){
        var row = document.createElement("div");
        row.style.display = "table-row";

        keyboard.appendChild(row);

        for(var d = 0; d < days.length - 1; d++){
            var day = document.createElement("div");
            day.style.display = "table-cell";

            if((p == d || count > 1) && count <= months[__$(month).value][0]){
                // day.innerHTML = count;
                var btn = document.createElement("button");
                btn.innerHTML = count;
                btn.className = "keyboard_button blue";
                btn.style.minWidth = "70px";

                btn.onmousedown = function(){
                    if(__$("inputField")){
                        __$("inputField").value = this.innerHTML;
                    }
                }

                btn.onkeydown = function(event){
                    if (event.keyCode == 13)
                        document.getElementById('next').click()
                }

                day.appendChild(btn);

                p++;
                count++;
            }

            row.appendChild(day);
        }
        var day = document.createElement("div");
        day.style.display = "table-cell";
        
        row.appendChild(day);
            
        if(count > months[__$(month).value][0]){
            var btn = document.createElement("button");
            btn.innerHTML = "Unknown";
            btn.className = "keyboard_button blue";
            btn.style.minWidth = "70px";

            btn.onmousedown = function(){
                if(__$("inputField")){
                    __$("inputField").value = this.innerHTML;
                }
            }

            btn.onkeydown = function(event){
                if (event.keyCode == 13)
                    document.getElementById('next').click()
            }

            day.appendChild(btn);

            break;
        }
    }
    
    showKeys = false;
}

function loadNumbers(startYr, endYr){
    __$("dropdowns").innerHTML = "";
    
    var panel = document.createElement("div");
    panel.id = "panel";
    panel.style.width = "100%";

    __$("dropdowns").appendChild(panel);

    var contain = document.createElement("div");
    contain.style.width = "100%";
    contain.style.height = "250px";
    contain.style.overflow = "auto";
    contain.style.border = "1px solid #6281A7"
    contain.style.borderRadius = "10px";
    contain.style.backgroundColor = "#fff";

    panel.appendChild(contain);

    var ul = document.createElement("ul");
    ul.style.padding = "0px";
    ul.id = "ulOptions";

    contain.appendChild(ul);
    var li = document.createElement("li");
    li.innerHTML = "<div style='display: table;'><div style='display: table-row;'>" +
    "<div style='display: table-cell;'><img src='images/unchecked.png' height='32' /></div>" +
    "<div style='display: table-cell; vertical-align: middle; padding-left: 10px;'><span>Unknown" +
    "</span></div></div></div>";
    li.setAttribute("tag", (0 % 2 > 0 ? "odd" : "even"));

    if(y % 2 > 0){
        li.style.backgroundColor = "#eee";
    } else {
        li.style.backgroundColor = "#ccc";
    }

    li.onkeydown = function(event){
        if (event.keyCode == 13)
            document.getElementById('next').click()
    }

    li.onmousedown = function(){
        if(__$("inputField"))
            __$("inputField").value = this.getElementsByTagName("span")[0].innerHTML;

        var lis = __$("ulOptions").getElementsByTagName("li");

        for(var l = 0; l < lis.length; l++){
            lis[l].style.color = "#254061";
            lis[l].getElementsByTagName("img")[0].src = "images/unchecked.png";
            if(lis[l].getAttribute("tag") == "odd"){
                lis[l].style.backgroundColor = "#eee";
            } else {
                lis[l].style.backgroundColor = "#ccc";
            }
        }

        this.style.backgroundColor = "steelblue";
        this.style.color = "#fff";
        this.getElementsByTagName("img")[0].src = "images/checked.png";
    }

    ul.appendChild(li);
        
    for(var y = endYr; y >= startYr; y--){
        var li = document.createElement("li");
        li.innerHTML = "<div style='display: table;'><div style='display: table-row;'>" +
        "<div style='display: table-cell;'><img src='images/unchecked.png' height='32' /></div>" +
        "<div style='display: table-cell; vertical-align: middle; padding-left: 10px;'><span>" + y +
        "</span></div></div></div>";
        li.setAttribute("tag", (y % 2 > 0 ? "odd" : "even"));

        if(y % 2 > 0){
            li.style.backgroundColor = "#eee";
        } else {
            li.style.backgroundColor = "#ccc";
        }

        li.onkeydown = function(event){
            if (event.keyCode == 13)
                document.getElementById('next').click()
        }

        li.onmousedown = function(){
            if(__$("inputField"))
                __$("inputField").value = this.getElementsByTagName("span")[0].innerHTML;

            var lis = __$("ulOptions").getElementsByTagName("li");

            for(var l = 0; l < lis.length; l++){
                lis[l].style.color = "#254061";
                lis[l].getElementsByTagName("img")[0].src = "images/unchecked.png";
                if(lis[l].getAttribute("tag") == "odd"){
                    lis[l].style.backgroundColor = "#eee";
                } else {
                    lis[l].style.backgroundColor = "#ccc";
                }
            }

            this.style.backgroundColor = "steelblue";
            this.style.color = "#fff";
            this.getElementsByTagName("img")[0].src = "images/checked.png";
        }

        ul.appendChild(li);
    }
}

function loadSingleSelect(values, selected, initialtext){
    __$("dropdowns").innerHTML = "";
    
    var panel = document.createElement("div");
    panel.id = "panel";
    panel.style.width = "100%";

    __$("dropdowns").appendChild(panel);

    var contain = document.createElement("div");
    contain.style.width = "100%";
    contain.style.height = "250px";
    contain.style.overflow = "auto";
    contain.style.border = "1px solid #6281A7"
    contain.style.borderRadius = "10px";
    contain.style.backgroundColor = "#fff";

    panel.appendChild(contain);

    var ul = document.createElement("ul");
    ul.style.padding = "0px";
    ul.id = "ulOptions";

    contain.appendChild(ul);
    
    var li = document.createElement("li");
    li.innerHTML = "<div style='display: table;'><div style='display: table-row;'>" +
    "<div style='display: table-cell;'><img src='images/unchecked.png' height='32' /></div>" +
    "<div style='display: table-cell; vertical-align: middle; padding-left: 10px;'><span></span></div></div></div>";
    li.setAttribute("tag", "odd");

    li.style.backgroundColor = "#eee";

    li.onkeydown = function(event){
        if (event.keyCode == 13)
            document.getElementById('next').click()
    }

    li.onmousedown = function(){
        /*if(__$("inputField"))
            __$("inputField").value = this.getElementsByTagName("span")[0].innerHTML;*/

        var lis = __$("ulOptions").getElementsByTagName("li");

        for(var l = 0; l < lis.length; l++){
            lis[l].style.color = "#254061";
            lis[l].getElementsByTagName("img")[0].src = "images/unchecked.png";
            if(lis[l].getAttribute("tag") == "odd"){
                lis[l].style.backgroundColor = "#eee";
            } else {
                lis[l].style.backgroundColor = "#ccc";
            }
        }

        this.style.backgroundColor = "steelblue";
        this.style.color = "#fff";
        this.getElementsByTagName("img")[0].src = "images/checked.png";
    }

    ul.appendChild(li);
        
    for(var y = 0; y < values.length; y++){
        var li = document.createElement("li");
        li.innerHTML = "<div style='display: table;'><div style='display: table-row;'>" +
        "<div style='display: table-cell;'><img src='images/unchecked.png' height='32' /></div>" +
        "<div style='display: table-cell; vertical-align: middle; padding-left: 10px;'><span>" +
        values[y][0] + "</span></div></div></div>";
        li.setAttribute("tag", (y % 2 > 0 ? "odd" : "even"));
        li.setAttribute("tstValue", (values[y][1] ? values[y][1] : values[y][0]));

        if(y % 2 > 0){
            li.style.backgroundColor = "#eee";
        } else {
            li.style.backgroundColor = "#ccc";
        }

        li.onkeydown = function(event){
            if (event.keyCode == 13)
                document.getElementById('next').click()
        }

        li.onmousedown = function(){
            if(__$("inputField")){
                __$("inputField").value = this.getElementsByTagName("span")[0].innerHTML;
                __$("inputField").setAttribute("tstValue", this.getAttribute("tstValue"));
            }

            var lis = __$("ulOptions").getElementsByTagName("li");

            for(var l = 0; l < lis.length; l++){
                lis[l].style.color = "#254061";
                lis[l].getElementsByTagName("img")[0].src = "images/unchecked.png";
                if(lis[l].getAttribute("tag") == "odd"){
                    lis[l].style.backgroundColor = "#eee";
                } else {
                    lis[l].style.backgroundColor = "#ccc";
                }
            }

            this.style.backgroundColor = "steelblue";
            this.style.color = "#fff";
            this.getElementsByTagName("img")[0].src = "images/checked.png";
        }

        ul.appendChild(li);
    }

    if(typeof(initialtext) == "undefined"){
// setTimeout("clear()", 10);
}
}

function loadMultipleSelect(values, selected){
    __$("dropdowns").innerHTML = "";
    
    var panel = document.createElement("div");
    panel.id = "panel";
    panel.style.width = "100%";

    __$("dropdowns").appendChild(panel);

    var contain = document.createElement("div");
    contain.style.width = "100%";
    contain.style.height = "250px";
    contain.style.overflow = "auto";
    contain.style.border = "1px solid #6281A7"
    contain.style.borderRadius = "10px";
    contain.style.backgroundColor = "#fff";

    panel.appendChild(contain);

    var ul = document.createElement("ul");
    ul.style.padding = "0px";
    ul.id = "ulOptions";

    contain.appendChild(ul);

    var li = document.createElement("li");
    li.innerHTML = "<div style='display: table;'><div style='display: table-row;'>" +
    "<div style='display: table-cell;'></div></div></div>";
    li.setAttribute("tag", "odd");

    li.style.backgroundColor = "#eee";

    ul.appendChild(li);

    for(var y = 0; y < values.length; y++){
        var li = document.createElement("li");
        li.innerHTML = "<div style='display: table;'><div style='display: table-row;'>" +
        "<div style='display: table-cell;'><img src='images/unticked.jpg' height='32' /></div>" +
        "<div style='display: table-cell; vertical-align: middle; padding-left: 10px;'><span>" +
        values[y] + "</span></div></div></div>";
        li.setAttribute("tag", (y % 2 > 0 ? "odd" : "even"));

        if(y % 2 > 0){
            li.style.backgroundColor = "#eee";
        } else {
            li.style.backgroundColor = "#ccc";
        }

        li.onkeydown = function(event){
            if (event.keyCode == 13)
                document.getElementById('next').click()
        }

        li.onmousedown = function(){
            if(__$("inputField")){
                if(this.getElementsByTagName("img")[0].src.match("images/ticked.jpg")){
                    this.getElementsByTagName("img")[0].src = "images/unticked.jpg";
                    __$("inputField").value = __$("inputField").value.replace(
                        this.getElementsByTagName("span")[0].innerHTML + ";", "");
                    this.style.color = "#254061";
                    if(this.getAttribute("tag") == "odd"){
                        this.style.backgroundColor = "#eee";
                    } else {
                        this.style.backgroundColor = "#ccc";
                    }
                } else {
                    __$("inputField").value += this.getElementsByTagName("span")[0].innerHTML + ";";

                    this.style.backgroundColor = "steelblue";
                    this.style.color = "#fff";
                    this.getElementsByTagName("img")[0].src = "images/ticked.jpg";
                }
                selected = __$("inputField").value;
            }

        }

        ul.appendChild(li);
    }

    setTimeout("clear()", 10);
}

function goForward(){
    current_case_upper = true;
       
    if(__$('inputField')){
        if(__$('inputField').getAttribute('optional') == null &&
            __$('inputField').value.trim().length == 0){
            showMessage('This field is required. Please enter a value!');
            return;
        } else if(__$('inputField').getAttribute('validationRule') != null){
            if(!__$('inputField').value.trim().match(__$('inputField').getAttribute('validationRule'))){
                if(__$('inputField').getAttribute('validationMessage') != null){
                    showMessage(__$('inputField').getAttribute('validationMessage'));
                } else {
                    showMessage('Wrong value!');
                }
                return;
            }
        }
    }
    
    if(current_question + 1 < order.length){

        if(__$('inputField')){

            __$(__$('inputField').getAttribute('srcControl')).value = 
            (__$('inputField').getAttribute("tstValue") ?
                __$('inputField').getAttribute("tstValue") : __$('inputField').value);

        }
        
        current_question += 1;
        showPage(current_question);
        if(current_question > 0){
            __$('prev').style.display = 'block';
        } else {
            __$('prev').style.display = 'none';
        }
    } else {

        if(__$('inputField')){

            __$(__$('inputField').getAttribute('srcControl')).value =
            (__$('inputField').getAttribute("tstValue") ?
                __$('inputField').getAttribute("tstValue") : __$('inputField').value);

        }

        document.forms[0].submit();
    }
}

function goBackwards(){
    if(current_question - 1 >= 0){
        if(__$('inputField'))
            __$(__$('inputField').getAttribute('srcControl')).value = __$('inputField').value;
        
        current_question -= 1;
        showPage(current_question, true);
        if(current_question > 0){
            __$('prev').style.display = 'block';
        } else {
            __$('prev').style.display = 'none';
        }
    } 
}

function listMessages(){
    __$("parent").innerHTML = "";
}

function displayMessage(){
    __$("parent").innerHTML = "";
}

function clear(){
    __$("inputField").value = "";
}

function loadPage(file){
    ajaxGeneralRequest(file, "expandSpec()");
}

function showMessage(msg){
    if(navigator.userAgent.toLowerCase().match(/android/)){
        Android.showMsg(msg);
    } else {
        alert(msg);
    }
}

loadLocale();

if (!html5_storage_support) {
    showMessage("This Might Be a Good Time to Upgrade Your Browser or Turn On Javascript");
} else {
    checkLogin();
}
