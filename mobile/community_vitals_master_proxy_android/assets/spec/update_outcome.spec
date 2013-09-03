P.1. Update Outcome [program: COMMUNITY VITALS REGISTRATION $$ action => javascript:updateOutcome()]
C.1. Community Vitals Registration person registration
C.1. Special characters are:
C.1. "=>" for association
C.1. "$$" for separation of entries

Q.1.1. First Name [pos => 'a' $$ dynamicLoader => listFirstNames(__$('inputField').value.trim()) $$ tt_onLoad => listFirstNames('') $$ tt_showToggleKeyboard => true]

Q.1.2. Last Name [pos => 'b' $$ dynamicLoader => listLastNames(__$('inputField').value.trim()) $$ tt_onLoad => listLastNames('') $$ tt_showToggleKeyboard => true]

Q.1.3. Gender [pos => 'c' $$ dynamicLoader => loadGender(__$('inputField').value.trim()) $$ tt_onLoad => loadGender('') $$ tt_showToggleKeyboard => false $$ showKeys => false]

Q.1.4. Select Person From The Following: [pos => 'd' $$ tt_onLoad => listPeopleNames(__$('1.1').value.trim(), __$('1.2').value.trim(), __$('1.3').value.trim()); hideInput() $$ showKeys => false]

Q.1.5. Outcome [pos => 'e' $$ tt_onLoad => listOutcomeTypes() $$ showKeys => false]

Q.1.6. Year of death [condition => __$('1.5').value.toLowerCase() == 'dead' $$ pos => 'f' $$ field_type => number $$ validationRule => ^\d{4}$|^Unknown$ $$ validationMessage => Wrong value. Expecting a 4 digit number!]

Q.1.7. Month of death [condition => __$('1.5').value.toLowerCase() == 'dead' && __$("1.6").value.trim().toLowerCase() != "unknown" $$ pos => 'g' $$ tt_showToggleKeyboard => true $$ dynamicLoader => loadMonths(__$('inputField').value.trim()) $$ tt_onLoad => loadMonths('')]

Q.1.8. Day of death [condition => __$('1.5').value.toLowerCase() == 'dead' && __$("1.7").value.trim().toLowerCase() != "unknown" && __$("1.6").value.trim().toLowerCase() != "unknown" $$ pos => 'h' $$ tt_onLoad => generateDays('1.6', '1.7')]

Q.1.9. Cause of death [condition => __$('1.5').value.toLowerCase() == 'dead' $$ pos => 'i']
O.1.9.1. Natural causes
O.1.9.2. Accident
O.1.9.3. Old age
O.1.9.4. Sickness
O.1.9.5. Other

Q.1.10. Transfer out year [condition => __$('1.5').value.toLowerCase() == 'transfer out' $$ pos => 'j' $$ field_type => number $$ field_type => number $$ validationRule => ^\d{4}$|^Unknown$ $$ validationMessage => Wrong value. Expecting a 4 digit number!]

Q.1.11. Transfer out month [condition => __$('1.5').value.toLowerCase() == 'transfer out' && __$("1.10").value.trim().toLowerCase() != "unknown" $$ pos => 'k' $$ tt_showToggleKeyboard => true $$ dynamicLoader => loadMonths(__$('inputField').value.trim()) $$ tt_onLoad => loadMonths('')]

Q.1.12. Transfer out day [condition => __$('1.5').value.toLowerCase() == 'transfer out' && __$("1.10").value.trim().toLowerCase() != "unknown" && __$("1.11").value.trim().toLowerCase() != "unknown" $$ pos => 'l' $$ tt_onLoad => generateDays('1.10', '1.11')]

Q.1.13. Transfer back year [condition => __$('1.5').value.toLowerCase() == 'transfer back' $$ pos => 'm' $$ field_type => number $$ field_type => number $$ validationRule => ^\d{4}$|^Unknown$ $$ validationMessage => Wrong value. Expecting a 4 digit number!]

Q.1.14. Transfer back month [condition => __$('1.5').value.toLowerCase() == 'transfer back' && __$("1.13").value.trim().toLowerCase() != "unknown" $$ pos => 'n' $$ tt_showToggleKeyboard => true $$ dynamicLoader => loadMonths(__$('inputField').value.trim()) $$ tt_onLoad => loadMonths('')]

Q.1.15. Transfer back day [condition => __$('1.5').value.toLowerCase() == 'transfer back' && __$("1.13").value.trim().toLowerCase() != "unknown" && __$("1.14").value.trim().toLowerCase() != "unknown" $$ pos => 'o' $$ tt_onLoad => generateDays('1.13', '1.14')]


