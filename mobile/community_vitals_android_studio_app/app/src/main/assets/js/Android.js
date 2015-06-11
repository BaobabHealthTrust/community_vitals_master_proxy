"use strict"

var myAndroid = function (page_size) {
    this.page_size = page_size;
};

myAndroid.prototype = {

    getPref: function (pref) {
        return this[pref];
    },

    setPref: function (pref, value) {
        this[pref] = value;
    },

    debugPrint: function (message) {
        if (console != undefined) {
            console.log(message);
        }
        ;
    },

    search: function (word) {
        return word;
    },

    showMsg: function (msg) {
        alert(msg);
    },

    getUsers: function (page) {
        var result = [];

        var count = 1;
        var current_page = parseInt(page);
        var next_page = ((current_page * this.page_size) < count ? current_page + 1
            : current_page);

        var previous_page = (current_page > 1 ? current_page - 1 : current_page);

        var last_page = Math.floor((count / this.page_size)) + 1;

        var user = {
            "1": {
                "user_id": String(1),
                "Username": "admin",
                "Name": "Test Admin",
                "Gender": "Male",
                "Status": "ACTIVE",
                "Roles": JSON.stringify(["Superuser", "Village Headman"])
            }
        };

        var start = ((current_page - 1) * this.page_size) + 1;

        result.push(user);

        result.push(previous_page + "");

        result.push(next_page + "");

        result.push(last_page + "");

        result.push(count + "");

        result.push(start + "");

        result.push(count + "");

        return JSON.stringify(result);
    }

}

// var Android = new myAndroid(10);