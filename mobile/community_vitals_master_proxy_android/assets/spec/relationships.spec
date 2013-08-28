P.1. Relationships [program: COMMUNITY VITALS REGISTRATION $$ action => javascript:savePersonRelationship()]
C.1. Community Vitals Registration person registration
C.1. Special characters are:
C.1. "=>" for association
C.1. "$$" for separation of entries

Q.1.1. Person First Name [pos => 0 $$ dynamicLoader => listFirstNames(__$('inputField').value.trim()) $$ tt_onLoad => listFirstNames(''); showKeys = false]

Q.1.2. Person Last Name [pos => 1 $$ dynamicLoader => listLastNames(__$('inputField').value.trim()) $$ tt_onLoad => listLastNames(''); showKeys = false]

Q.1.3. Person Gender [pos => 2]
O.1.3.1. Male
O.1.3.2. Female

Q.1.4. Select Person From The Following: [pos => 3 $$ tt_onLoad => getPeopleNames(__$('1.1').value.trim(), __$('1.2').value.trim(), __$('1.3').value.trim()); showKeys = false]
C.1.4.1. List of matching people 

Q.1.5. Relation First Name [pos => 4 $$ dynamicLoader => listFirstNames(__$('inputField').value.trim()) $$ tt_onLoad => listFirstNames(''); showKeys = false]

Q.1.6. Relation Last Name [pos => 5 $$ dynamicLoader => listLastNames(__$('inputField').value.trim()) $$ tt_onLoad => listLastNames(''); showKeys = false]

Q.1.7. Relation Gender [pos => 6]
O.1.7.1. Male
O.1.7.2. Female

Q.1.8. Select Relation From The Following: [pos => 7 $$ tt_onLoad => getPeopleNames(__$('1.5').value.trim(), __$('1.6').value.trim(), __$('1.7').value.trim()); showKeys = false]
C.1.8.1. List of matching people 

Q.1.9. Relationship Type [pos => 8]
O.1.9.1. Parent
O.1.9.2. Child
O.1.9.3. Spouse
