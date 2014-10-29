P.1. Add Person [program: COMMUNITY VITALS REGISTRATION $$ action => javascript:updatePerson()]
C.1. Community Vitals Registration person registration
C.1. Special characters are:
C.1. "=>" for association
C.1. "$$" for separation of entries

Q.1.1. First Name [pos => a $$ dynamicLoader => listFirstNames(__$('inputField').value.trim()) $$ tt_onLoad => listFirstNames('') $$ tt_showToggleKeyboard => true $$ condition => (window.location.href.match(/fname/i)  ? true : false)]

Q.1.2. Middle Name [pos => b $$ dynamicLoader => listFirstNames(__$('inputField').value.trim()) $$ tt_onLoad => listFirstNames('') $$ tt_showToggleKeyboard => true $$ condition => $$ condition => (window.location.href.match(/mname/i) ? true : false)]

Q.1.3. Last Name [pos => c $$ dynamicLoader => listLastNames(__$('inputField').value.trim()) $$ tt_onLoad => listLastNames('') $$ tt_showToggleKeyboard => true $$ condition => (window.location.href.match(/lname/i)  ? true : false)]

Q.1.4. Gender [pos => d $$ dynamicLoader => loadGender(__$('inputField').value.trim()) $$ tt_onLoad => loadGender('') $$ tt_showToggleKeyboard => false $$ showKeys => false $$ condition => (window.location.href.match(/gender/i)  ? true : false)]

Q.1.5. Where were you born? [pos => i $$ dynamicLoader => loadPlaceOfBirth(__$('inputField').value.trim()) $$ tt_onLoad => loadPlaceOfBirth('') $$ tt_showToggleKeyboard => false $$ showKeys => false $$ condition => (window.location.href.match(/placeofbirth/i)  ? true : false)]

Q.1.6. Place of birth [pos => j $$ dynamicLoader => loadFacilities(__$('inputField').value.trim()) $$ tt_onLoad => loadFacilities('') $$ tt_showToggleKeyboard => true $$ showKeys => false $$ condition => (window.location.href.match(/placeofbirth/i)  ? true : false)]

Q.1.7. Current Region [pos => k $$ dynamicLoader => loadRegion(__$('inputField').value.trim()) $$ tt_onLoad => loadRegion('') $$ tt_showToggleKeyboard => true $$ showKeys => false $$ condition => (window.location.href.match(/location/i)  ? true : false)]

Q.1.8. Current District [pos => l $$ dynamicLoader => loadDistrict(__$('inputField').value.trim(),__$("1.6").value.trim().toLowerCase()) $$ tt_onLoad => loadDistrict('',__$("1.6").value.trim().toLowerCase()) $$ tt_showToggleKeyboard => false $$ showKeys => false $$ condition => (window.location.href.match(/location/i)  ? true : false)]

Q.1.9. Current Traditional Authority [pos => m $$ dynamicLoader => loadTA(__$('inputField').value.trim(),__$("1.7").value.trim().toLowerCase()) $$ tt_onLoad => loadTA('',__$("1.7").value.trim().toLowerCase()) $$ tt_showToggleKeyboard => true $$ showKeys => true $$ condition => (window.location.href.match(/location/i)  ? true : false)]

Q.1.10. Current Village [pos => n $$ dynamicLoader => loadVillage(__$('inputField').value.trim(),__$("1.8").value.trim().toLowerCase()) $$ tt_onLoad => loadVillage('',__$("1.8").value.trim().toLowerCase()) $$ tt_showToggleKeyboard => true $$ showKeys => false $$ condition => (window.location.href.match(/location/i)  ? true : false)]

Q.1.11. Year of birth [pos => o $$ field_type => number $$ validationRule => ^\d{4}$|^Unknown$ $$ validationMessage => Wrong value. Expecting a 4 digit number! (window.location.href.match(/dob/i)  ? true : false)]

Q.1.12. Age [pos => p $$ condition => __$("1.11").value.trim().toLowerCase() == "unknown" && (window.location.href.match(/dob/i)  ? true : false) $$ field_type => number]

Q.1.13. Month of birth [pos => q $$ tt_showToggleKeyboard => true $$ dynamicLoader => loadMonths(__$('inputField').value.trim()) $$ tt_onLoad => loadMonths('') $$ condition => __$("1.11").value.trim().toLowerCase() != "unknown" && (window.location.href.match(/dob/i)  ? true : false)]

Q.1.14. Date of birth [pos => r $$ tt_customKeyboard => true $$ condition => __$("1.11").value.trim().toLowerCase() != "unknown" && __$("1.13").value.trim().toLowerCase() != "unknown" && (window.location.href.match(/dob/i)  ? true : false) $$ tt_onLoad => generateDays('1.11', '1.13') ]
