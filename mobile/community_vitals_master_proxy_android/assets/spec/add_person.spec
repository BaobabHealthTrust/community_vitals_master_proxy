P.1. Add Person [program: COMMUNITY VITALS REGISTRATION $$ action => javascript:savePerson()]
C.1. Community Vitals Registration person registration
C.1. Special characters are:
C.1. "=>" for association
C.1. "$$" for separation of entries

Q.1.1. First Name [pos => a $$ dynamicLoader => listFirstNames(__$('inputField').value.trim()) $$ tt_onLoad => listFirstNames('') $$ tt_showToggleKeyboard => true]

Q.1.2. Middle Name [pos => b $$ dynamicLoader => listFirstNames(__$('inputField').value.trim()) $$ tt_onLoad => listFirstNames('') $$ tt_showToggleKeyboard => true $$ condition => (typeof(localStorage.askMiddleName) != "undefined" ? (localStorage.askMiddleName.toLowerCase() == "true" ? true : false ) : false ) == true]

Q.1.3. Last Name [pos => c $$ dynamicLoader => listLastNames(__$('inputField').value.trim()) $$ tt_onLoad => listLastNames('') $$ tt_showToggleKeyboard => true]

Q.1.4. Gender [pos => d $$ dynamicLoader => loadGender(__$('inputField').value.trim()) $$ tt_onLoad => loadGender('') $$ tt_showToggleKeyboard => false $$ showKeys => false]

Q.1.5. Year of birth [pos => e $$ field_type => number $$ validationRule => ^\d{4}$|^Unknown$ $$ validationMessage => Wrong value. Expecting a 4 digit number!]

Q.1.6. Age [pos => f $$ condition => __$("1.5").value.trim().toLowerCase() == "unknown" $$ field_type => number]

Q.1.7. Month of birth [pos => g $$ tt_showToggleKeyboard => true $$ dynamicLoader => loadMonths(__$('inputField').value.trim()) $$ tt_onLoad => loadMonths('') $$ condition => __$("1.5").value.trim().toLowerCase() != "unknown"]

Q.1.8. Date of birth [pos => h $$ tt_customKeyboard => true $$ condition => __$("1.7").value.trim().toLowerCase() != "unknown" && __$("1.5").value.trim().toLowerCase() != "unknown" $$ tt_onLoad => generateDays('1.5', '1.7') ]

Q.1.9. Do you want to add relationships now? [pos => i $$ dynamicLoader => listValues(__$('inputField').value.trim(), {"Yes":"Yes", "No":"No"}) $$ tt_onLoad => listValues('', {"Yes":"Yes", "No":"No"}) $$ tt_showToggleKeyboard => false $$ showKeys => false]

Q.1.10. Relation First Name [pos => j $$ dynamicLoader => listFirstNames(__$('inputField').value.trim()) $$ tt_onLoad => listFirstNames('') $$ tt_showToggleKeyboard => true $$ showKeys => true $$ condition => __$("1.9").value == "Yes"]

Q.1.11. Relation Last Name [pos => k $$ dynamicLoader => listLastNames(__$('inputField').value.trim()) $$ tt_onLoad => listLastNames('') $$ tt_showToggleKeyboard => true $$ showKeys => true $$ condition => __$("1.9").value == "Yes"]

Q.1.12. Relation Gender [pos => l $$ dynamicLoader => loadGender(__$('inputField').value.trim()) $$ tt_onLoad => loadGender('') $$ tt_showToggleKeyboard => false $$ showKeys => false $$ condition => __$("1.9").value == "Yes"]

Q.1.13. Select Relation From The Following: [pos => m $$ tt_onLoad => listPeopleNames(__$('1.10').value.trim(), __$('1.11').value.trim(), __$('1.12').value.trim(), true); hideInput() $$ showKeys => false $$ condition => __$("1.9").value == "Yes"]

Q.1.14. Relationship Type [pos => n $$ tt_onLoad => listRelationshipTypes() $$ showKeys => false $$ condition => __$("1.9").value == "Yes"]

