P.1. Add Person [program: COMMUNITY VITALS REGISTRATION $$ action => javascript:savePerson()]
C.1. Community Vitals Registration person registration
C.1. Special characters are:
C.1. "=>" for association
C.1. "$$" for separation of entries

Q.1.1. First Name [pos => 0 $$ dynamicLoader => listFirstNames(__$('inputField').value.trim()) $$ tt_onLoad => listFirstNames('') $$ tt_showToggleKeyboard => true]

Q.1.2. Middle Name [pos => 1 $$ dynamicLoader => listFirstNames(__$('inputField').value.trim()) $$ tt_onLoad => listFirstNames('') $$ tt_showToggleKeyboard => true $$ condition => (typeof(localStorage.askMiddleName) != "undefined" ? (localStorage.askMiddleName.toLowerCase() == "true" ? true : false ) : false ) == true]

Q.1.3. Last Name [pos => 2 $$ dynamicLoader => listLastNames(__$('inputField').value.trim()) $$ tt_onLoad => listLastNames('') $$ tt_showToggleKeyboard => true]

Q.1.4. Gender [pos => 3 $$ dynamicLoader => loadGender(__$('inputField').value.trim()) $$ tt_onLoad => loadGender('') $$ tt_showToggleKeyboard => false $$ showKeys => false]

Q.1.5. Year of birth [pos => 4 $$ field_type => number $$ validationRule => ^\d{4}$|^Unknown$ $$ validationMessage => Wrong value. Expecting a 4 digit number!]

Q.1.6. Age [pos => 5 $$ condition => __$("1.5").value.trim().toLowerCase() == "unknown" $$ field_type => number]

Q.1.7. Month of birth [pos => 6 $$ tt_showToggleKeyboard => true $$ dynamicLoader => loadMonths(__$('inputField').value.trim()) $$ tt_onLoad => loadMonths('') $$ condition => __$("1.5").value.trim().toLowerCase() != "unknown"]

Q.1.8. Date of birth [pos => 7 $$ tt_customKeyboard => true $$ condition => __$("1.7").value.trim().toLowerCase() != "unknown" && __$("1.5").value.trim().toLowerCase() != "unknown" $$ tt_onLoad => generateDays('1.5', '1.7') ]


