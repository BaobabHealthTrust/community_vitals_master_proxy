P.1. Update Outcome [program: COMMUNITY VITALS REGISTRATION $$ action => javascript:updateOutcome()]
C.1. Community Vitals Registration person registration
C.1. Special characters are:
C.1. "=>" for association
C.1. "$$" for separation of entries

Q.1.1. Outcome [pos => 'a' $$ tt_onLoad => listOutcomeTypes() $$ showKeys => false]

Q.1.2. Year of death [condition => __$('1.1').value.toLowerCase() == 'dead' $$ pos => 'b' $$ field_type => number $$ validationRule => ^\d{4}$|^Unknown$ $$ validationMessage => Wrong value. Expecting a 4 digit number!]

Q.1.3. Month of death [condition => __$('1.1').value.toLowerCase() == 'dead' && __$("1.2").value.trim().toLowerCase() != "unknown" $$ pos => 'c' $$ tt_showToggleKeyboard => true $$ dynamicLoader => loadMonths(__$('inputField').value.trim()) $$ tt_onLoad => loadMonths('')]

Q.1.4. Day of death [condition => __$('1.1').value.toLowerCase() == 'dead' && __$("1.2").value.trim().toLowerCase() != "unknown" && __$("1.3").value.trim().toLowerCase() != "unknown" $$ pos => 'd' $$ tt_onLoad => generateDays('1.2', '1.3')]

Q.1.5. Cause of death [condition => __$('1.1').value.toLowerCase() == 'dead' $$ pos => 'e']
O.1.5.1. Natural causes
O.1.5.2. Accident
O.1.5.3. Old age
O.1.5.4. Sickness
O.1.5.5. Other

Q.1.6. Transfer out year [condition => __$('1.1').value.toLowerCase() == 'transfer out' $$ pos => 'f' $$ field_type => number $$ field_type => number $$ validationRule => ^\d{4}$|^Unknown$ $$ validationMessage => Wrong value. Expecting a 4 digit number!]

Q.1.7. Transfer out month [condition => __$('1.1').value.toLowerCase() == 'transfer out' && __$("1.6").value.trim().toLowerCase() != "unknown" $$ pos => 'i' $$ tt_showToggleKeyboard => true $$ dynamicLoader => loadMonths(__$('inputField').value.trim()) $$ tt_onLoad => loadMonths('')]

Q.1.8. Transfer out day [condition => __$('1.1').value.toLowerCase() == 'transfer out' && __$("1.6").value.trim().toLowerCase() != "unknown" && __$("1.7").value.trim().toLowerCase() != "unknown" $$ pos => 'j' $$ tt_onLoad => generateDays('1.6', '1.7')]

Q.1.9. Transfer back year [condition => __$('1.1').value.toLowerCase() == 'transfer back' $$ pos => 'k' $$ field_type => number $$ field_type => number $$ validationRule => ^\d{4}$|^Unknown$ $$ validationMessage => Wrong value. Expecting a 4 digit number!]

Q.1.10. Transfer back month [condition => __$('1.1').value.toLowerCase() == 'transfer back' && __$("1.9").value.trim().toLowerCase() != "unknown" $$ pos => 'l' $$ tt_showToggleKeyboard => true $$ dynamicLoader => loadMonths(__$('inputField').value.trim()) $$ tt_onLoad => loadMonths('')]

Q.1.11. Transfer back day [condition => __$('1.1').value.toLowerCase() == 'transfer back' && __$("1.10").value.trim().toLowerCase() != "unknown" && __$("1.11").value.trim().toLowerCase() != "unknown" $$ pos => 'm' $$ tt_onLoad => generateDays('1.9', '1.10')]


