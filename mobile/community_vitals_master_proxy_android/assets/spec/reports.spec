P.1. Reports [program: COMMUNITY VITALS REGISTRATION]
C.1. Community Vitals Registration person registration
C.1. Special characters are:
C.1. "=>" for association
C.1. "$$" for separation of entries
Q.1.1. Select T/A Report To View [pos => 'a' $$ condition => getCurrentCategory() == 0]
O.1.1.1. List of report types
Q.1.2. Select GVH Report To View [pos => 'b' $$ condition => getCurrentCategory() == 1]
O.1.2.1. List of report types
Q.1.3. Select Village Report To View [pos => 'c' $$ condition => getCurrentCategory() == 2]
O.1.3.1. All People
O.1.3.2. All Female
O.1.3.3. All Male
O.1.3.4. All Adults above 14
O.1.3.5. All Children under 15
O.1.3.6. All Adult Women above 14
O.1.3.7. All Adult Men above 14
O.1.3.8. All Boys under 15
O.1.3.9. All Girls under 15
Q.1.4. All People [pos => 'd' $$ tt_onLoad => loadAllPeople() $$ condition => __$('1.3').value == 'All People']
Q.1.5. All Female [pos => 'e' $$ tt_onLoad => loadAllFemale() $$ condition => __$('1.3').value == 'All Female']
Q.1.6. All Male [pos => 'f' $$ tt_onLoad => loadAllMale() $$ condition => __$('1.3').value == 'All Male']
Q.1.7. All Adults [pos => 'g' $$ tt_onLoad => loadAllAdults() $$ condition => __$('1.3').value == 'All Adults above 14']
Q.1.8. All Children [pos => 'h' $$ tt_onLoad => loadAllChildren() $$ condition => __$('1.3').value == 'All Children under 15']
Q.1.9. All Adult Women [pos => 'i' $$ tt_onLoad => loadAllAdultFemale() $$ condition => __$('1.3').value == 'All Adult Women above 14']
Q.1.10. All Adult Men [pos => 'j' $$ tt_onLoad => loadAllAdultMale() $$ condition => __$('1.3').value == 'All Adult Men above 14']
Q.1.11. All Boys [pos => 'k' $$ tt_onLoad => loadAllBoys() $$ condition => __$('1.3').value == 'All Boys under 15']
Q.1.12. All Girls [pos => 'l' $$ tt_onLoad => loadAllGirls() $$ condition => __$('1.3').value == 'All Girls under 15']
