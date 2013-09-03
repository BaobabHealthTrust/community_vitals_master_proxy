P.1. Search Person [program: COMMUNITY VITALS REGISTRATION $$ action => javascript:searchPerson()]
C.1. Community Vitals Registration person registration
C.1. Special characters are:
C.1. "=>" for association
C.1. "$$" for separation of entries

Q.1.1. First Name [pos => 0 $$ dynamicLoader => listFirstNames(__$('inputField').value.trim()) $$ tt_onLoad => listFirstNames('') $$ tt_showToggleKeyboard => true]

Q.1.2. Last Name [pos => 2 $$ dynamicLoader => listLastNames(__$('inputField').value.trim()) $$ tt_onLoad => listLastNames('') $$ tt_showToggleKeyboard => true]

Q.1.3. Gender [pos => 3 $$ dynamicLoader => loadGender(__$('inputField').value.trim()) $$ tt_onLoad => loadGender('') $$ tt_showToggleKeyboard => false $$ showKeys => false]

Q.1.4. Select Person From The Following: [pos => 3 $$ tt_onLoad => listPeopleNames(__$('1.1').value.trim(), __$('1.2').value.trim(), __$('1.3').value.trim()); hideInput() $$ showKeys => false]
C.1.4.1. List of matching people 

