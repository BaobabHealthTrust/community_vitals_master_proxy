P.1. Relationships [program: COMMUNITY VITALS REGISTRATION $$ action => javascript:savePersonRelationship()]
C.1. Community Vitals Registration person registration
C.1. Special characters are:
C.1. "=>" for association
C.1. "$$" for separation of entries

Q.1.1. Relation First Name [pos => 0 $$ dynamicLoader => listFirstNames(__$('inputField').value.trim()) $$ tt_onLoad => listFirstNames('') $$ tt_showToggleKeyboard => true $$ showKeys => true]

Q.1.2. Relation Last Name [pos => 1 $$ dynamicLoader => listLastNames(__$('inputField').value.trim()) $$ tt_onLoad => listLastNames('') $$ tt_showToggleKeyboard => true $$ showKeys => true]

Q.1.3. Relation Gender [pos => 2 $$ dynamicLoader => loadGender(__$('inputField').value.trim()) $$ tt_onLoad => loadGender('') $$ tt_showToggleKeyboard => false $$ showKeys => false]]

Q.1.4. Select Relation From The Following: [pos => 3 $$ tt_onLoad => listPeopleNames(__$('1.1').value.trim(), __$('1.2').value.trim(), __$('1.3').value.trim(), true); hideInput() $$ showKeys => false]

Q.1.5. Relationship Type [pos => 4 $$ tt_onLoad => listRelationshipTypes() $$ showKeys => false]
