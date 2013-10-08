// Data source adapter

var pid = (window.location.href.match(/\id=([^&]+)/) ?
    window.location.href.match(/\id=([^&]+)/)[1] : null);
            
function __$(id){
    return document.getElementById(id);
}

function savePerson(){
    var fName = __$("1.1").value;
    var mName = __$("1.2").value;
    var lName = __$("1.3").value;
    var gender = __$("1.4").value;
    var yob = (__$("1.5").value.trim().toLowerCase() == "unknown" ?
        "Unknown" : __$("1.5").value);
    var age = (__$("1.5").value.trim().toLowerCase() == "unknown" ?
        parseInt(__$("1.6").value) : "0");
    
    var relate = (__$("1.9").value.trim().toLowerCase() == "yes" ? "yes" : "no");
    var person_b = (__$("1.9").value.trim().toLowerCase() == "yes" ? __$("1.13").value.trim() : "");
    var relation = (__$("1.9").value.trim().toLowerCase() == "yes" ? __$("1.14").value.trim() : "");
        
    var months = {
        "January":"1",
        "February":"2",
        "March":"3",
        "April":"4",
        "May":"5",
        "June":"6",
        "July":"7",
        "August":"8",
        "September":"9",
        "October":"10",
        "November":"11",
        "December":"12"
    };
    
    var mob = (__$("1.5").value.trim().toLowerCase() == "unknown" ? "Unknown" : months[__$("1.7").value]);
    var dob = (__$("1.5").value.trim().toLowerCase() == "unknown" ? "Unknown" : __$("1.8").value);
    var occ = ""; // __$("1.9").value;
    var success = "Person saved!";
    var failed = "Person save failed!";

    var result = Android.savePerson(fName, mName, lName, gender, age,
        occ, yob, mob, dob, success, failed, relate, person_b, relation);

    window.location = "person_summary.html";
    
}

function listFirstNames(fname){
    var result = Android.listFirstNames(fname);

    var list = JSON.parse(result);
    var arr = [];

    for(var el in list){
        arr.push([el, list[el]]);
    }

    loadSingleSelect(arr);
}

function listLastNames(lname){
    var result = Android.listLastNames(lname);

    var list = JSON.parse(result);
    var arr = [];

    for(var el in list){
        arr.push([el, list[el]]);
    }

    loadSingleSelect(arr);
}

function getPeopleNames(fname, lname, gender){

    var result = Android.listMatchingNames(fname, lname, gender);

    var list = JSON.parse(result);
    var arr = [];

    for(var el in list){
        arr.push([list[el]["details"], el]);
    }

    loadSingleSelect(arr);
}

function updateOutcome(){
    var months = {
        "January":1,
        "February":2,
        "March":3,
        "April":4,
        "May":5,
        "June":6,
        "July":7,
        "August":8,
        "September":9,
        "October":10,
        "November":11,
        "December":12
    };
    switch(__$("1.5").value.trim().toLowerCase()){
        case "dead":
            var yoe = (__$("1.6").value.trim().toLowerCase() == "unknown" || __$("1.6").value.trim().length == 0 ?
                "0000" : __$("1.6").value);
            var moe = (__$("1.7").value.trim().toLowerCase() == "unknown" || __$("1.7").value.trim().length == 0 ? 
                "00" : months[__$("1.7").value.trim()]);
            var doe = (__$("1.8").value.trim().toLowerCase() == "unknown" || __$("1.8").value.trim().length == 0 ?
                "00" : __$("1.8").value);

            var date = yoe + "-" + moe + "-" + doe;

            Android.updateOutcome(parseInt(__$("1.4").value.trim()), "Dead", date, __$("1.9").value.trim());
            
            break;
        case "transfer out":
            var yoe = (__$("1.10").value.trim().toLowerCase() == "unknown" || __$("1.10").value.trim().length == 0 ?
                "0000" : __$("1.10").value);
            var moe = (__$("1.11").value.trim().toLowerCase() == "unknown" || __$("1.11").value.trim().length == 0 ?
                "00" : months[__$("1.11").value.trim()]);
            var doe = (__$("1.12").value.trim().toLowerCase() == "unknown" || __$("1.12").value.trim().length == 0 ?
                "00" : __$("1.12").value);

            var date = yoe + "-" + moe + "-" + doe;

            Android.updateOutcome(parseInt(__$("1.4").value.trim()), "Transfer Out", date, "");
            
            break;
        case "transfer back":
            var yoe = (__$("1.13").value.trim().toLowerCase() == "unknown" || __$("1.13").value.trim().length == 0 ?
                "0000" : __$("1.13").value);
            var moe = (__$("1.14").value.trim().toLowerCase() == "unknown" || __$("1.14").value.trim().length == 0 ?
                "00" : months[__$("1.14").value.trim()]);
            var doe = (__$("1.15").value.trim().toLowerCase() == "unknown" || __$("1.15").value.trim().length == 0 ?
                "00" : __$("1.15").value);

            var date = yoe + "-" + moe + "-" + doe;

            Android.updateOutcome(parseInt(__$("1.4").value.trim()), "Transfer Back", date, "");

            break;
    }

    showMessage("Outcome Updated!");

    window.location = "index.html";
}

function savePersonRelationship(){
    Android.savePersonRelationship(__$("1.4").value.trim(),
        __$("1.8").value.trim(), __$("1.9").value.trim());
    
    var msg = search("Relationship Saved!");
    
    showMessage(msg);

    window.location = "index.html";
}

function confirmDelete(msg, id, cat){
    if(navigator.userAgent.toLowerCase().match(/android/)){
        Android.confirmDeletion(msg, id, cat);
    } else {
        var response = confirm("Delete?");        
    }
}

function editPerson(id){
    window.location = "edit_person.html?id=" + id;
}

function saveEditedPerson(){
    var person_id = (window.location.href.match(/\id=([^&]+)/) ?
        window.location.href.match(/\id=([^&]+)/)[1] : null);

    if(person_id != null){
        
        Android.addPersonID(parseInt(person_id), __$('1.2').value.trim(), __$('1.1').value.trim())
        showMessage("Identifier saved!");

        window.location = "reports.html";
    } else {
        showMessage("Failed to create identifier!");
    }
}

function loadPanel(list, cat){
    var table = document.createElement("div");
    table.style.display = "table";
    
    __$("parent").appendChild(table);

    var row = document.createElement("div");
    row.style.display = "table-row";

    table.appendChild(row);

    var cell = document.createElement("div");
    cell.style.display = "table-cell";
    cell.innerHTML = cat + " (" + ")";
    cell.style.fontSize = "32px";
    cell.style.backgroundColor = "#6281A7";
    cell.style.padding = "10px";
    cell.style.color = "#fff";

    row.appendChild(cell);

    var row2 = document.createElement("div");
    row2.style.display = "table-row";

    table.appendChild(row2);

    var cell2 = document.createElement("div");
    cell2.style.display = "table-cell";
    cell2.style.border = "1px solid #000";
    cell2.style.height = "300px";
    cell2.style.overflow = "auto";

    row2.appendChild(cell2);

    var cols = ["id", "first_name", "last_name", "middle_name", "gender", "dob",
    "occupation", "outcome", "outcome_date", "date_created", "identifiers",
    "relations", "edit", "delete"];

    var tbl = document.createElement("div");
    tbl.style.display = "table";
    tbl.style.borderSpacing = "2px";
    
    cell2.appendChild(tbl);
    
    var tblrow = document.createElement("div");
    tblrow.style.display = "table-row";

    tbl.appendChild(tblrow);

    for(var c = 0; c < cols.length; c++){
        var tblcell = document.createElement("div");
        tblcell.style.display = "table-cell";
        tblcell.innerHTML = cols[c].replace(/_/, " ").toUpperCase();
        tblcell.style.fontWeight = "bold";
        tblcell.style.backgroundColor = "#ccc";
        tblcell.style.padding = "10px";

        tblrow.appendChild(tblcell);
    }

    var count = 0;
    for(var i in list){
        var tblrow2 = document.createElement("div");
        tblrow2.style.display = "table-row";

        tbl.appendChild(tblrow2);

        count++;
        
        for(var c = 0; c < cols.length - 2; c++){
            var tblcell2 = document.createElement("div");
            tblcell2.style.display = "table-cell";
            tblcell2.style.textAlign = "left";
            tblcell2.innerHTML = (typeof(list[i][cols[c]]) != "undefined" ? list[i][cols[c]] : "&nbsp;");
            tblcell2.style.border = "1px solid #ccc";
            tblcell2.style.padding = "10px";
            tblcell2.style.verticalAlign = "top";

            tblrow2.appendChild(tblcell2);
        }

        var tblcell2 = document.createElement("div");
        tblcell2.style.display = "table-cell";
        tblcell2.style.textAlign = "center";
        tblcell2.style.verticalAlign = "middle";
        tblcell2.innerHTML =  "<img src='images/pencil.png' alt='E' />";
        tblcell2.style.border = "1px solid #ccc";
        tblcell2.style.padding = "10px";
        tblcell2.setAttribute("id", list[i]["id"]);

        tblcell2.onmousedown = function(){
            editPerson(this.getAttribute("id"));
        }

        tblrow2.appendChild(tblcell2);

        var tblcell2 = document.createElement("div");
        tblcell2.style.display = "table-cell";
        tblcell2.style.textAlign = "center";
        tblcell2.style.verticalAlign = "middle";
        tblcell2.innerHTML = "<img src='images/cancel_flat_red.png' alt='X' />";
        tblcell2.style.border = "1px solid #ccc";
        tblcell2.style.padding = "10px";
        tblcell2.setAttribute("id", list[i]["id"]);

        tblcell2.onmousedown = function(){
            confirmDelete("Do you really want to delete this person?", parseInt(this.getAttribute("id")), 1);
        }

        tblrow2.appendChild(tblcell2);

    }

    var tblrowbottom = document.createElement("div");
    tblrowbottom.style.display = "table-row";

    tbl.appendChild(tblrowbottom);

    var tblcell = document.createElement("div");
    tblcell.style.display = "table-cell";
    tblcell.innerHTML = "&nbsp;";
    tblcell.style.fontWeight = "bold";
    tblcell.style.padding = "10px";
    tblcell.style.height = "100px";

    tblrowbottom.appendChild(tblcell);
        
    cell.innerHTML = cat + " (" + count + ")";
}

function loadAllPeople(){
    __$("btnKeyboard").style.display = "none";
    
    showKeys = false;

    __$("parent").innerHTML = "";

    var result = Android.getPeople(0);

    var list = JSON.parse(result);

    loadPanel(list, "All People");
}

function loadAllFemale(){
    __$("btnKeyboard").style.display = "none";

    showKeys = false;

    __$("parent").innerHTML = "";

    var result = Android.getPeople(2);

    var list = JSON.parse(result);

    loadPanel(list, "All Female");
}

function loadAllMale(){
    __$("btnKeyboard").style.display = "none";

    showKeys = false;

    __$("parent").innerHTML = "";

    var result = Android.getPeople(1);

    var list = JSON.parse(result);

    loadPanel(list, "All Male");
}

function loadAllAdults(){
    __$("btnKeyboard").style.display = "none";

    showKeys = false;

    __$("parent").innerHTML = "";

    var result = Android.getPeople(3);

    var list = JSON.parse(result);

    loadPanel(list, "All Adults");
}

function loadAllChildren(){
    __$("btnKeyboard").style.display = "none";

    showKeys = false;

    __$("parent").innerHTML = "";

    var result = Android.getPeople(4);

    var list = JSON.parse(result);

    loadPanel(list, "All Children");
}

function loadAllAdultFemale(){
    __$("btnKeyboard").style.display = "none";

    showKeys = false;

    __$("parent").innerHTML = "";

    var result = Android.getPeople(5);

    var list = JSON.parse(result);

    loadPanel(list, "All Adult Female");
}

function loadAllAdultMale(){
    __$("btnKeyboard").style.display = "none";

    showKeys = false;

    __$("parent").innerHTML = "";

    var result = Android.getPeople(6);

    var list = JSON.parse(result);

    loadPanel(list, "All Adult Male");
}

function loadAllBoys(){
    __$("btnKeyboard").style.display = "none";

    showKeys = false;

    __$("parent").innerHTML = "";

    var result = Android.getPeople(7);

    var list = JSON.parse(result);

    loadPanel(list, "All Boy Children");
}

function loadAllGirls(){
    __$("btnKeyboard").style.display = "none";

    showKeys = false;

    __$("parent").innerHTML = "";

    var result = Android.getPeople(8);

    var list = JSON.parse(result);

    loadPanel(list, "All People");
}

function loadMonths(value){
    var list = {
        "January":"January",
        "February":"February",
        "March":"March",
        "April":"April",
        "May":"May",
        "June":"June",
        "July":"July",
        "August":"August",
        "September":"September",
        "October":"October",
        "November":"November",
        "December":"December",
        "Unknown":"Unknown"
    };
    
    var arr = [];

    value = search(value);

    for(var el in list){
        var word = search(list[el]);
      
        if(word.trim().toLowerCase().match(value.toLowerCase().trim())){
            arr.push([word, el]);
        } else if (value.toLowerCase().trim().length == 0){
            arr.push([word, el]);
        }
    }

    loadSingleSelect(arr);
}

function loadGender(value){
    var list = {
        "Male":"Male",
        "Female":"Female"
    };
    
    var arr = [];

    for(var el in list){
        if(list[el].trim().toLowerCase().match(value.toLowerCase().trim())){
            arr.push([search(list[el]), el]);
        } else if (value.toLowerCase().trim().length == 0){
            arr.push([search(list[el]), el]);
        }
    }

    loadSingleSelect(arr);
}

function loadJobs(value){
    var list = {
        "Driver":"Driver",
        "Housewife":"Housewife",
        "Messenger":"Messenger",
        "Business":"Business",
        "Farmer":"Farmer",
        "Salesperson":"Salesperson",
        "Teacher":"Teacher",
        "Student":"Student",
        "Security guard":"Security guard",
        "Domestic worker":"Domestic worker",
        "Police":"Police",
        "Office worker":"Office worker",
        "Preschool child":"Preschool child",
        "Mechanic":"Mechanic",
        "Prisoner":"Prisoner",
        "Craftsman":"Craftsman",
        "Healthcare Worker":"Healthcare Worker",
        "Soldier":"Soldier",
        "Other":"Other",
        "Unknown":"Unknown"
    };
    
    var arr = [];

    for(var el in list){
        if(list[el].trim().toLowerCase().match(value.toLowerCase().trim())){
            arr.push([search(list[el]), el]);
        } else if (value.toLowerCase().trim().length == 0){
            arr.push([search(list[el]), el]);
        }
    }

    loadSingleSelect(arr);
}

function listTAs(value){
    var list = {
        "Mtema":"Mtema",
        "Mphonde":"Mphonde",
        "Thandazya":"Thandazya",
        "Maselero":"Maselero"
    };
    
    var arr = [];

    for(var el in list){
        if(list[el].trim().toLowerCase().match(value.toLowerCase().trim())){
            arr.push([list[el], el]);
        } else if (value.toLowerCase().trim().length == 0){
            arr.push([list[el], el]);
        }
    }

    loadSingleSelect(arr);
}

function listGVHs(value){
    var list = {
        "Mtema":"Mtema",
        "Mphonde":"Mphonde",
        "Thandazya":"Thandazya",
        "Maselero":"Maselero"
    };
    
    var arr = [];

    for(var el in list){
        if(list[el].trim().toLowerCase().match(value.toLowerCase().trim())){
            arr.push([list[el], el]);
        } else if (value.toLowerCase().trim().length == 0){
            arr.push([list[el], el]);
        }
    }

    loadSingleSelect(arr);
}

function listVH(value){
    var list = {
        "Mtema":"Mtema",
        "Mphonde":"Mphonde",
        "Thandazya":"Thandazya",
        "Maselero":"Maselero"
    };
    
    var arr = [];

    for(var el in list){
        if(list[el].trim().toLowerCase().match(value.toLowerCase().trim())){
            arr.push([list[el], el]);
        } else if (value.toLowerCase().trim().length == 0){
            arr.push([list[el], el]);
        }
    }

    loadSingleSelect(arr);
}

function loadChiefs(value){
    var list = {
        "gvh":"Group Village Headman",
        "vh":"Village Headman"
    };
    
    var arr = [];

    for(var el in list){
        if(list[el].trim().toLowerCase().match(value.toLowerCase().trim())){
            arr.push([search(list[el]), el]);
        } else if (value.toLowerCase().trim().length == 0){
            arr.push([search(list[el]), el]);
        }
    }

    loadSingleSelect(arr);
}

function listPeopleNames(fname, lname, gender, bynpid){
    var result = Android.listPeopleNames(fname, lname, gender);

    var list = JSON.parse(result);
    var arr = [];

    for(var el in list){
        if(typeof(bynpid) != "undefined"){
            arr.push([list[el]["details"], list[el]["npid"]]);
        } else {
            arr.push([list[el]["details"], el]);
        }
    }

    loadSingleSelect(arr);
}

function searchPerson(){
    var id = parseInt(__$("1.4").value);
  
    if(id > 0){
        var result = Android.searchPerson(id);
    
        if(result){
            window.location = "person_summary.html";
        } else {
            window.location = "index.html";
        }
    } else {
        window.location = "index.html";
    }
  
}

function listRelationshipTypes(){
    var list = {
        "parent":"Parent",
        "child":"Child",
        "spouse":"Spouse"
    };
    
    var arr = [];

    for(var el in list){
        arr.push([search(list[el].toLowerCase()), el]);
    }

    loadSingleSelect(arr);
}

function listOutcomeTypes(){
    var list = {
        "Dead":"Dead",
        "Transfer Out":"Transfer Out",
        "Transfer Back":"Transfer Back"
    };
    
    var arr = [];

    for(var el in list){
        arr.push([search(list[el]), el]);
    }

    loadSingleSelect(arr);
}

function setupSite(){

    if(__$("1.1") && __$("1.2") && __$("1.3") && __$("1.4") && __$("1.5") && __$("1.6") && __$("1.7")){
    
        Android.setPref("target_username", __$("1.1").value.trim());
        Android.setPref("target_password", __$("1.2").value.trim());
        Android.setPref("target_server", __$("1.3").value.trim());
        Android.setPref("target_port", __$("1.4").value.trim());
        Android.setPref("site_code", __$("1.5").value.trim());
        Android.setPref("batch_count", __$("1.6").value.trim());
        Android.setPref("threshold", __$("1.7").value.trim());
    
        Android.setSettings(__$("1.1").value.trim(), __$("1.2").value.trim(), 
            __$("1.3").value.trim(), __$("1.4").value.trim(), __$("1.5").value.trim(), 
            __$("1.6").value.trim(), __$("1.7").value.trim());
    
        showMessage("Settings Saved!");
    }
  
    window.location = "index.html";
}

function getNationalIDs(){
    Android.getNationalIds();
}

function getDDESetting(key){
    var field = "";
  
    switch(key){
        case 0:
            field = "mode";
            break;
        case 1:
            field = "dde_username";
            break;
        case 2:
            field = "dde_password";
            break;
        case 3:
            field = "dde_ip";
            break;
        case 4:
            field = "dde_port";
            break;
        case 5:
            field = "dde_site_code";
            break;
        case 6:
            field = "dde_batch_size";
            break;
        case 7:
            field = "dde_threshold_size";
            break;
    }
	
    var setting = Android.getDDESetting(field);
  
    return setting;
}

function selectMonth( month){

    var months = {
        "January":"01",
        "February":"02",
        "March":"03",
        "April":"04",
        "May":"05",
        "June":"06",
        "July":"07",
        "August":"08",
        "September":"09",
        "October":"10",
        "November":"11",
        "December":"12"
    };

    return months[month]
}

function selectOutcomeMonth(){

    var year = __$("1.1").value;
    var month = __$("1.2").value;

    var rep_month = year.trim()+"-"+selectMonth(month);
    var dis_month =month.trim() +" "+ year.trim();

    Android.setReportMonth(rep_month, dis_month);
    if (Android.getPref("dde_mode") == 'vh')
    {
        window.location = "monthly_outcomes_report.html";
    }
    else
    {
        window.location = "monthly_outcomes_report_senior.html";
    }
}

function selectBirthMonth(){

    var year = __$("1.1").value;
    var month = __$("1.2").value;

    var rep_month = year.trim()+"-"+selectMonth(month);
    var dis_month =month.trim() +" "+ year.trim();

    Android.setReportMonth(rep_month, dis_month);
    if (Android.getPref("dde_mode") == 'vh')
    {
        window.location = "monthly_birth_report.html";
    }
    else
    {
        window.location = "monthly_birth_report_senior.html";
    }

}

function selectDate(){
    var year = __$("1.1").value;
    var month = __$("1.2").value;
    var day = __$("1.3").value;



    var months = {
        "January":"1",
        "February":"2",
        "March":"3",
        "April":"4",
        "May":"5",
        "June":"6",
        "July":"7",
        "August":"8",
        "September":"9",
        "October":"10",
        "November":"11",
        "December":"12"
    };
    var display_date = day.trim() + ' ' + __$("1.2").value.trim() +' ' + year.trim();

    if (parseInt(day) < 10)
    {
        day = "0"+ day;
    }
    var date = year.trim() + '-' + months[month].trim() +'-' + day.trim();
    Android.setReportDate(date,display_date)
    window.location = "daily_summary.html"
}

function updateUser(){
    var fname = (__$("1.1") ? __$("1.1").value : "");
    var lname = (__$("1.2") ? __$("1.2").value : "");
    var gender = (__$("1.3") ? __$("1.3").value : "");
    var username = (__$("1.4") ? __$("1.4").value : "");
    var password = (__$("1.5") ? __$("1.5").value : "");
    
    Android.updateUser(fname, lname, gender, username, password);
    
    window.location = "select_user_task.html";
}

function updateSelectedUser(){
    var fname = (__$("1.1") ? __$("1.1").value : "");
    var lname = (__$("1.2") ? __$("1.2").value : "");
    var gender = (__$("1.3") ? __$("1.3").value : "");
    var username = (__$("1.4") ? __$("1.4").value : "");
    var password = (__$("1.5") ? __$("1.5").value : "");
    var role = (__$("1.7") ? __$("1.7").value : "");
    
    if(Android.checkUsername(username)){
        showMessage("Username already taken!");
        
        window.location = "update_user.html";
        return;
    }
    
    Android.updateSelectedUser(fname, lname, gender, username, password, role);
    
    window.location = "user_list.html";
}

function addUser(){
    var fname = (__$("1.1") ? __$("1.1").value : "");
    var lname = (__$("1.2") ? __$("1.2").value : "");
    var gender = (__$("1.3") ? __$("1.3").value : "");
    var username = (__$("1.4") ? __$("1.4").value : "");
    var password = (__$("1.5") ? __$("1.5").value : "");
    var role = (__$("1.7") ? __$("1.7").value : "");
    
    var exists = Android.checkUsername(username);
    
    if(exists == true){
        showMessage("Username already taken!");
        
        window.location = "add_user.html";
        return;
    }
    
    Android.addUser(fname, lname, gender, username, password, role);
    
    window.location = "user_list.html";
}

function listUserRoles(value){
    var list = {
        "Superuser":"Superuser",
        "Group Village Headman":"Group Village Headman",
        "Village Headman":"Village Headman"
    };
    
    var arr = [];

    for(var el in list){
        if(list[el].trim().toLowerCase().match(value.toLowerCase().trim())){
            arr.push([search(list[el]), el]);
        } else if (value.toLowerCase().trim().length == 0){
            arr.push([search(list[el]), el]);
        }
    }

    loadSingleSelect(arr);
}

function revokeUserRole(){
    var role = (__$("1.1") ? __$("1.1").value : "");
    
    Android.revokeUserRole(role);
    
    window.location = "user_list.html";
}

function listCurrentUserRoles(value, exclude){
    var list = {
        "Superuser":"Superuser",
        "Group Village Headman":"Group Village Headman",
        "Village Headman":"Village Headman"
    };
    
    var result = {};
    
    var exceptions = Android.getPref("exceptions");
    
    if(typeof(exceptions) != "undefined"){
        var ex = JSON.parse(exceptions);
        
        for(var i = 0; i < ex.length; i++){
            if(list[ex[i]] && list[ex[i]] != exclude){
                result[ex[i]] = list[ex[i]];
            }
        }
    }
    
    var arr = [];

    for(var el in result){
        if(result[el].trim().toLowerCase().match(value.toLowerCase().trim())){
            arr.push([search(result[el]), el]);
        } else if (value.toLowerCase().trim().length == 0){
            arr.push([search(result[el]), el]);
        }
    }

    loadSingleSelect(arr);
}

function listFilteredUserRoles(value){
    var list = {
        "Superuser":"Superuser",
        "Group Village Headman":"Group Village Headman",
        "Village Headman":"Village Headman"
    };
    
    var exceptions = Android.getPref("exceptions");
    
    if(typeof(exceptions) != "undefined"){
        var ex = JSON.parse(exceptions);
        
        for(var i = 0; i < ex.length; i++){
            if(list[ex[i]]){
                delete list[ex[i]];
            }
        }
    }
    
    var arr = [];

    for(var el in list){
        if(list[el].trim().toLowerCase().match(value.toLowerCase().trim())){
            arr.push([search(list[el]), el]);
        } else if (value.toLowerCase().trim().length == 0){
            arr.push([search(list[el]), el]);
        }
    }

    loadSingleSelect(arr);
}

function listUserStatus(value){
    var list = {
        "Active":"Active",
        "Suspended":"Suspended",
        "Blocked":"Blocked"
    };
    
    var arr = [];

    for(var el in list){
        arr.push([search(list[el]), el]);
    }

    loadSingleSelect(arr);
}

function updateUserStatus(){
    var status = (__$("1.1") ? __$("1.1").value : "");
    
    Android.updateUserStatus(status);
    
    window.location = "user_list.html";
}

function setCategory(){
    Android.setPref("usertype", __$("1.1").value.trim());
            
    var mode = "";
    
    if(__$("1.2").value.trim().toLowerCase().length > 0){
        switch(__$("1.2").value.trim().toLowerCase()){
            case "traditional authority":
                mode = "ta";
                break;
            case "group village headman":
                mode = "gvh";
                break;
            case "village headman":
                mode = "vh";
                break;
        }
    } else {
        switch(__$("1.1").value.trim().toLowerCase()){
            case "traditional authority":
                mode = "ta";
                break;
            case "group village headman":
                mode = "gvh";
                break;
            case "village headman":
                mode = "vh";
                break;
        }
    }
         
    Android.debugPrint("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ " + mode);
         
    Android.setPref("dde_mode", mode);
    Android.setPref("ta", __$("1.3").value.trim());
    Android.setPref("gvh", __$("1.4").value.trim());
    Android.setPref("vh", __$("1.5").value.trim());
            
    if(__$("1.1").value.trim().toLowerCase() == "superuser"){
        Android.setPref("advanced", true);
    } else {
        Android.setPref("advanced", false);
    }

    Android.setPref("current_category", __$("1.1").value);

    window.location = "index.html"; 
}

function listValues(value, list){    
    var arr = [];

    for(var el in list){
        if(list[el].trim().toLowerCase().match(value.toLowerCase().trim())){
            arr.push([search(list[el]), el]);
        } else if (value.toLowerCase().trim().length == 0){
            arr.push([search(list[el]), el]);
        }
    }

    loadSingleSelect(arr);
}
