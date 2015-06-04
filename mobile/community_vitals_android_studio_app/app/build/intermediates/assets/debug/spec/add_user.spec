P.1. Site Setup [program => COMMUNITY VITALS REGISTRATION $$ action => javascript:addUser()]
C.1. Community Vitals Registration person registration
C.1. Special characters are:
C.1. "=>" for association
C.1. "$$" for separation of entries

Q.1.1. First Name [pos => 0 $$ dynamicLoader => listFirstNames(__$('inputField').value.trim()) $$ condition => (window.location.href.match(/\?/) ? (window.location.href.match(/firstname/i) ? true : false) : true) $$ tt_onLoad => listFirstNames('')]

Q.1.2. Last Name [pos => 1 $$ dynamicLoader => listLastNames(__$('inputField').value.trim()) $$ condition => (window.location.href.match(/\?/) ? (window.location.href.match(/lastname/i) ? true : false) : true) $$ tt_onLoad => listLastNames('')]

Q.1.3. Gender [pos => 3 $$ dynamicLoader => loadGender(__$('inputField').value.trim()) $$ tt_onLoad => loadGender('') $$ tt_showToggleKeyboard => false $$ showKeys => false $$ condition => (window.location.href.match(/\?/) ? (window.location.href.match(/gender/i) ? true : false) : true)]

Q.1.4. Username [pos => 4 $$ tt_onLoad => current_case_upper = false; $$ condition => (window.location.href.match(/\?/) ? (window.location.href.match(/username/i) ? true : false) : true)]

Q.1.5. Password [pos => 5 $$ field_type => password $$ condition => (window.location.href.match(/\?/) ? (window.location.href.match(/password/i) ? true : false) : true) $$ tt_onLoad => if(Android.checkUsername(__$('1.4').value.trim())){showMessage("Username already taken!"); showPage(3);} current_case_upper = false; $$ tt_onUnLoad => __$("1.6").setAttribute("validationRule", __$("inputField").value.trim())]

Q.1.6. Confirm Password [pos => 6 $$ field_type => password $$ condition => (window.location.href.match(/\?/) ? (window.location.href.match(/password/i) ? true : false) : true) $$ tt_onLoad => current_case_upper = false; $$ validationMessage => Password mismatch!]

Q.1.7. User Roles [pos => 7 $$ dynamicLoader => listUserRoles(__$('inputField').value.trim()) $$ condition => (window.location.href.match(/\?/) ? (window.location.href.match(/role/i) ? true : false) : true) $$ tt_onLoad => listUserRoles('') $$ tt_showToggleKeyboard => false $$ showKeys => false]

