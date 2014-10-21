P.1. Add Person [program: COMMUNITY VITALS REGISTRATION $$ action => javascript:updatePerson()]
C.1. Community Vitals Registration person registration
C.1. Special characters are:
C.1. "=>" for association
C.1. "$$" for separation of entries

Q.1.1. First Name [pos => a $$ dynamicLoader => listFirstNames(__$('inputField').value.trim()) $$ tt_onLoad => listFirstNames('') $$ tt_showToggleKeyboard => true $$ condition => (window.location.href.match(/fname/i)  ? true : false)]

Q.1.2. Middle Name [pos => b $$ dynamicLoader => listFirstNames(__$('inputField').value.trim()) $$ tt_onLoad => listFirstNames('') $$ tt_showToggleKeyboard => true $$ condition => $$ condition => (window.location.href.match(/mname/i) ? true : false)]

Q.1.3. Last Name [pos => c $$ dynamicLoader => listLastNames(__$('inputField').value.trim()) $$ tt_onLoad => listLastNames('') $$ tt_showToggleKeyboard => true $$ condition => (window.location.href.match(/lname/i)  ? true : false)]

Q.1.4. Gender [pos => d $$ dynamicLoader => loadGender(__$('inputField').value.trim()) $$ tt_onLoad => loadGender('') $$ tt_showToggleKeyboard => false $$ showKeys => false $$ condition => (window.location.href.match(/gender/i)  ? true : false)]

Q.1.5. Place of birth [pos => i $$ dynamicLoader => loadPlaceOfBirth(__$('inputField').value.trim()) $$ tt_onLoad => loadPlaceOfBirth('') $$ tt_showToggleKeyboard => false $$ showKeys => false $$ condition => (window.location.href.match(/placeofbirth/i)  ? true : false)]

Q.1.6. Current Region [pos => j $$ dynamicLoader => loadRegion(__$('inputField').value.trim()) $$ tt_onLoad => loadRegion('') $$ tt_showToggleKeyboard => true $$ showKeys => false $$ condition => (window.location.href.match(/location/i)  ? true : false)]

Q.1.7. Current District [pos => k $$ dynamicLoader => loadDistrict(__$('inputField').value.trim(),__$("1.6").value.trim().toLowerCase()) $$ tt_onLoad => loadDistrict('',__$("1.6").value.trim().toLowerCase()) $$ tt_showToggleKeyboard => false $$ showKeys => false $$ condition => (window.location.href.match(/location/i)  ? true : false)]


Q.1.8. Current Traditional Authority [pos => l $$ dynamicLoader => loadTA(__$('inputField').value.trim(),__$("1.7").value.trim().toLowerCase()) $$ tt_onLoad => loadTA('',__$("1.7").value.trim().toLowerCase()) $$ tt_showToggleKeyboard => true $$ showKeys => true $$ condition => (window.location.href.match(/location/i)  ? true : false)]

Q.1.9. Current Village [pos => m $$ dynamicLoader => loadVillage(__$('inputField').value.trim(),__$("1.8").value.trim().toLowerCase()) $$ tt_onLoad => loadVillage('',__$("1.8").value.trim().toLowerCase()) $$ tt_showToggleKeyboard => true $$ showKeys => false $$ condition => (window.location.href.match(/location/i)  ? true : false)]

