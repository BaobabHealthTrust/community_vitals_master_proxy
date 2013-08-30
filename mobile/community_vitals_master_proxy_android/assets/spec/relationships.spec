P.1. Relationships [program: COMMUNITY VITALS REGISTRATION $$ action => javascript:savePersonRelationship()]
C.1. Community Vitals Registration person registration
C.1. Special characters are:
C.1. "=>" for association
C.1. "$$" for separation of entries

Q.1.1. First Name [pos => 0 $$ dynamicLoader => listFirstNames(__$('inputField').value.trim()) $$ tt_onLoad => listFirstNames('') $$ tt_showToggleKeyboard => true]

Q.1.2. Last Name [pos => 2 $$ dynamicLoader => listLastNames(__$('inputField').value.trim()) $$ tt_onLoad => listLastNames('') $$ tt_showToggleKeyboard => true]

Q.1.3. Gender [pos => 3 $$ dynamicLoader => loadGender(__$('inputField').value.trim()) $$ tt_onLoad => loadGender('') $$ tt_showToggleKeyboard => false $$ showKeys => false]

Q.1.4. Select Person From The Following: [pos => 3 $$ tt_onLoad => listPeopleNames(__$('1.1').value.trim(), __$('1.2').value.trim(), __$('1.3').value.trim(), true); hideInput() $$ showKeys => false]

Q.1.5. Relation First Name [pos => 4 $$ dynamicLoader => listFirstNames(__$('inputField').value.trim()) $$ tt_onLoad => listFirstNames('') $$ tt_showToggleKeyboard => true $$ showKeys => true]

Q.1.6. Relation Last Name [pos => 5 $$ dynamicLoader => listLastNames(__$('inputField').value.trim()) $$ tt_onLoad => listLastNames('') $$ tt_showToggleKeyboard => true $$ showKeys => true]

Q.1.7. Relation Gender [pos => 6 $$ dynamicLoader => loadGender(__$('inputField').value.trim()) $$ tt_onLoad => loadGender('') $$ tt_showToggleKeyboard => false $$ showKeys => false]]

Q.1.8. Select Relation From The Following: [pos => 7 $$ tt_onLoad => listPeopleNames(__$('1.5').value.trim(), __$('1.6').value.trim(), __$('1.7').value.trim(), true); hideInput() $$ showKeys => false]

Q.1.9. Relationship Type [pos => 8 $$ tt_onLoad => listRelationshipTypes() $$ showKeys => false]
