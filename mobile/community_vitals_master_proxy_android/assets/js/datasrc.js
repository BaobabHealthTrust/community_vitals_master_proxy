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
        (new Date()).getFullYear() - parseInt(__$("1.6").value) : __$("1.5").value);
    var mob = (__$("1.5").value.trim().toLowerCase() == "unknown" ? "Unknown" : __$("1.7").value);
    var dob = (__$("1.5").value.trim().toLowerCase() == "unknown" ? "Unknown" : __$("1.8").value);
    var occ = __$("1.9").value;
    var success = "Person saved!";
    var failed = "Person save failed!";

    Android.savePerson(fName, mName, lName, gender,
        (yob + "-" + (mob.replace(/unknown/i,"?")) + "-" + (dob.replace(/unknown/i,"?"))),
        occ, yob, mob, dob, success, failed);

    window.location = "index.html";
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
            var yoe = (__$("1.6").value.trim().toLowerCase() == "unknown" ?
                "?" : __$("1.6").value);
            var moe = (__$("1.7").value.trim().toLowerCase() == "unknown" ? 
                "?" : months[__$("1.7").value.trim()]);
            var doe = (__$("1.8").value.trim().toLowerCase() == "unknown" ?
                "?" : __$("1.8").value);

            var date = yoe + "-" + moe + "-" + doe;

            Android.updateOutcome(parseInt(__$("1.4").value.trim()), "Dead", date, __$("1.9").value.trim());
            
            break;
        case "transfer out":
            var yoe = (__$("1.10").value.trim().toLowerCase() == "unknown" ?
                "?" : __$("1.10").value);
            var moe = (__$("1.11").value.trim().toLowerCase() == "unknown" ?
                "?" : months[__$("1.11").value.trim()]);
            var doe = (__$("1.12").value.trim().toLowerCase() == "unknown" ?
                "?" : __$("1.12").value);

            var date = yoe + "-" + moe + "-" + doe;

            Android.updateOutcome(parseInt(__$("1.4").value.trim()), "Transfer Out", date, "");
            
            break;
        case "transfer back":
            var yoe = (__$("1.13").value.trim().toLowerCase() == "unknown" ?
                "?" : __$("1.13").value);
            var moe = (__$("1.14").value.trim().toLowerCase() == "unknown" ?
                "?" : months[__$("1.14").value.trim()]);
            var doe = (__$("1.15").value.trim().toLowerCase() == "unknown" ?
                "?" : __$("1.15").value);

            var date = yoe + "-" + moe + "-" + doe;

            Android.updateOutcome(parseInt(__$("1.4").value.trim()), "Transfer Back", date, "");

            break;
    }

    showMessage("Outcome Updated!");

    window.location = "index.html";
}

function savePersonRelationship(){
    Android.savePersonRelationship(parseInt(__$("1.4").value.trim()),
        parseInt(__$("1.8").value.trim()), __$("1.9").value.trim());
    
    showMessage("Relationship Saved!");

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