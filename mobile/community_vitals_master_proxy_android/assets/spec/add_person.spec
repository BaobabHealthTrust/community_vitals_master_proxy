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

Q.1.9. Where were you born? [pos => i $$ dynamicLoader => loadPlaceOfBirth(__$('inputField').value.trim()) $$ tt_onLoad => loadPlaceOfBirth('') $$ tt_showToggleKeyboard => false $$ showKeys => false]

Q.1.10. Place of Birth [pos => j $$ dynamicLoader => loadFacilities(__$('inputField').value.trim()) $$ tt_onLoad => loadFacilities('') $$ tt_showToggleKeyboard => true]

Q.1.11. Current Region [pos => k $$ dynamicLoader => loadRegion(__$('inputField').value.trim()) $$ tt_onLoad => loadRegion('') $$ tt_showToggleKeyboard => true $$ showKeys => false]

Q.1.12. Current District [pos => l $$ dynamicLoader => loadDistrict(__$('inputField').value.trim(),__$("1.11").value.trim().toLowerCase()) $$ tt_onLoad => loadDistrict('',__$("1.11").value.trim().toLowerCase()) $$ tt_showToggleKeyboard => false $$ showKeys => false]

Q.1.13. Current Traditional Authority [pos => m $$ dynamicLoader => loadTA(__$('inputField').value.trim(),__$("1.12").value.trim().toLowerCase()) $$ tt_onLoad => loadTA('',__$("1.12").value.trim().toLowerCase()) $$ tt_showToggleKeyboard => true $$ showKeys => true]

Q.1.14. Current Village [pos => n $$ dynamicLoader => loadVillage(__$('inputField').value.trim(),__$("1.13").value.trim().toLowerCase()) $$ tt_onLoad => loadVillage('',__$("1.13").value.trim().toLowerCase()) $$ tt_showToggleKeyboard => true $$ showKeys => false]

Q.1.15. Remarks [pos => o $$ tt_showToggleKeyboard => true $$ optional => true]

Q.1.16. Do you want to add relationships now? [pos => p $$ dynamicLoader => listValues(__$('inputField').value.trim(), {"Yes":"Yes", "No":"No"}) $$ tt_onLoad => listValues('', {"Yes":"Yes", "No":"No"}) $$ tt_showToggleKeyboard => false $$ showKeys => false]

Q.1.17. Relation First Name [pos => q $$ dynamicLoader => listFirstNames(__$('inputField').value.trim()) $$ tt_onLoad => listFirstNames('') $$ tt_showToggleKeyboard => true $$ showKeys => true $$ condition => __$("1.16").value == "Yes"]

Q.1.18. Relation Last Name [pos => r $$ dynamicLoader => listLastNames(__$('inputField').value.trim()) $$ tt_onLoad => listLastNames('') $$ tt_showToggleKeyboard => true $$ showKeys => true $$ condition => __$("1.16").value == "Yes"]

Q.1.19. Relation Gender [pos => s $$ dynamicLoader => loadGender(__$('inputField').value.trim()) $$ tt_onLoad => loadGender('') $$ tt_showToggleKeyboard => false $$ showKeys => false $$ condition => __$("1.16").value == "Yes"]

Q.1.20. Select Relation From The Following [pos => t $$ tt_onLoad => listPeopleNames(__$('1.17').value.trim(), __$('1.18').value.trim(), __$('1.19').value.trim(), true); hideInput() $$ showKeys => false $$ condition => __$("1.16").value == "Yes"]

Q.1.21. Relationship Type [pos => u $$ tt_onLoad => listRelationshipTypes() $$ showKeys => false $$ condition => __$("1.16").value == "Yes"]

